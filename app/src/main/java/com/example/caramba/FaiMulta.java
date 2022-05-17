package com.example.caramba;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class FaiMulta extends AppCompatActivity {
    private Button pulsanteConferma;

    private final Random randomGenerator = new Random();

    private final EditText[] tuttiCampi = new EditText[4];
    private Spinner spinner;

    private static String[] effrazioni;

    private RequestQueue requestQueue;

    private int giorno;
    private int mese;
    private int anno;
    private int ora;
    private int minuti;
    private String orario;
    private String dataInserita;
    DatePickerDialog.OnDateSetListener setListener;
    private DatePickerDialog dpd;

    private TextView dataPicker;
    private Button timeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fai_multa);

        pulsanteConferma = findViewById(R.id.pulsanteConferma);
        spinner = findViewById(R.id.spinner3);
        tuttiCampi[0] = findViewById(R.id.campoTarga);
        tuttiCampi[1] = findViewById(R.id.campoLuogo);
        tuttiCampi[2] = findViewById(R.id.campoCosto);
        tuttiCampi[3] = findViewById(R.id.campoCoordinate);

        dataPicker = findViewById(R.id.tv_date);
        Calendar calendar = Calendar.getInstance();

        giorno = calendar.get(Calendar.DAY_OF_MONTH);
        mese = calendar.get(Calendar.MONTH);
        anno = calendar.get(Calendar.YEAR);

        dataPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd = new DatePickerDialog(FaiMulta.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener, anno, mese, giorno);
                dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
                dpd.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                dataInserita = dayOfMonth + "/" + month + "/" + year;
                giorno = dayOfMonth;
                mese = month;
                anno = year;
                dataPicker.setText(dataInserita);
            }
        };

        timeButton = findViewById(R.id.timeButton);

        effrazioni = new String[]{
                "Circolazione con targa illeggibile", "Circolazione senza assicurazione RC", "Circolazione su corsia di emergenza", "Eccesso di velocità (+ di 60 km/h oltre il limite)",
                "Eccesso di velocità (tra 10 e 40 km/h oltre il limite)", "Eccesso di velocità (tra i 40 e i 60 km/h oltre il limite)", "Eccesso di velocità(fino a 10 km/h oltre il limite)",
                "Gara di velocità non autorizzata", "Guida con uso di cellulare", "Guida senza casco", "guida senza lenti(con obbligo sulla patente)", "Guida stato di ebbrezza (> 1,5 g/l)",
                "Guida stato di ebbrezza (tra 0,8 e 1,5 g/l)", "Guida stato di ebbrezza(tra 0,5 e 0,8 g/l)", "Impennata", "Mancata revisione del veicolo", "Modifiche meccaniche rispetto al certificato di omologazione",
                "Patente non idonea", "Scarico con omologato", "Semaforo rosso", "Sorpasso a destra", "Sorpasso in zone vietate"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner, effrazioni);
        adapter.setDropDownViewResource(R.layout.spinner_scroll);

        spinner.setAdapter(adapter);

        pulsanteConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Impostazioni.accessoFantasmagorico) {
                    /*
                     *
                     */
                    if (tuttiCampi[0].getText().toString().trim().length() <= 7 && tuttiCampi[0].getText().toString().trim().length() > 0
                            && tuttiCampi[1].getText().toString().trim().length() >0 && tuttiCampi[2].getText().toString().trim().length() >0
                            &&  tuttiCampi[3].getText().toString().trim().length() > 0) {

                        //Controllo che le credenziali siano corrette
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.url,
                                response -> Toast.makeText(FaiMulta.this, "Successo", Toast.LENGTH_LONG).show(),
                                error -> Toast.makeText(FaiMulta.this, "Errore", Toast.LENGTH_LONG).show()) {
                            String numeroGenerato = Integer.toString(randomGenerator.nextInt(2000000)).trim();
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("function", "inserisciMulta");
                                params.put("token", MainActivity.token);
                                params.put("codiceMatricola", MainActivity.codiceMatricola);
                                params.put("idMulta", numeroGenerato);

                                params.put("nomeViolazione", spinner.getSelectedItem().toString().trim());
                                params.put("costoViolazione", tuttiCampi[2].getText().toString().trim());

                                params.put("targa", tuttiCampi[0].getText().toString().trim().toUpperCase());
                                params.put("data", anno + "-" + mese + "-" + giorno);
                                params.put("oraMulta", orario);
                                params.put("luogo", tuttiCampi[1].getText().toString().trim());
                                params.put("coordinate", tuttiCampi[3].getText().toString().trim());

                                System.out.println("Stampa");
                                System.out.println(MainActivity.token);
                                System.out.println(MainActivity.codiceMatricola);
                                System.out.println(numeroGenerato);
                                System.out.println(spinner.getSelectedItem().toString().trim());
                                System.out.println(tuttiCampi[2].getText().toString().trim());
                                System.out.println(tuttiCampi[0].getText().toString().trim().toUpperCase());
                                System.out.println(anno + "-" + mese + "-" + giorno);
                                System.out.println(orario);
                                System.out.println(tuttiCampi[1].getText().toString().trim());
                                System.out.println(tuttiCampi[3].getText().toString().trim());

                                return params;
                            }
                        };

                        requestQueue = Volley.newRequestQueue(FaiMulta.this);
                        requestQueue.add(stringRequest);

                        //Se l'invio è corretto
                        Intent i = new Intent(getApplicationContext(), MenuPrincipale.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(FaiMulta.this, "Mancano campi o hai inserito una targa troppo grande", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Intent i = new Intent(getApplicationContext(), MenuPrincipale.class);
                    startActivity(i);
                }
            }
        });
    }//Fine onCreate()

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                ora = selectedHour;
                minuti = selectedMinute;

                orario = String.format(Locale.getDefault(), "%02d:%02d", ora, minuti);

                timeButton.setText(orario);
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, ora, minuti, true);

        timePickerDialog.setTitle("Scegli l'ora");
        timePickerDialog.show();
    }
}