package com.example.caramba;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityInfo extends AppCompatActivity {
    private TextView textTitolo;
    private Spinner spinner;
    private static String[] multe;

    private RequestQueue requestQueue;

    private String idMulta;

    private Button pulsanteRimuovi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_info);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> lista = bundle.getStringArrayList("lista");

        spinner = findViewById(R.id.spinner);

        multe = new String[lista.size()];
        for(int i = 0; i < lista.size();i++){
            multe[i] = lista.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner, multe);
        adapter.setDropDownViewResource(R.layout.spinner_scroll);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                textTitolo.setText(lista.get(spinner.getSelectedItemPosition()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        textTitolo = findViewById(R.id.textTitolo);
        textTitolo.setText(lista.get(0));

        pulsanteRimuovi = findViewById(R.id.pulsanteRimuovi);
        pulsanteRimuovi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.url,
                        response -> Toast.makeText(ActivityInfo.this, "Successo", Toast.LENGTH_LONG).show(),
                        error -> Toast.makeText(ActivityInfo.this, "Errore", Toast.LENGTH_LONG).show()) {

                    @Override
                    protected Map<String, String> getParams() {
                        presaIdmulta();

                        Map<String, String> params = new HashMap<>();
                        params.put("function", "eliminaMulta");
                        params.put("token", MainActivity.token);
                        params.put("idMulta", idMulta);
                        params.put("codiceMatricola", MainActivity.codiceMatricola);
                        System.out.println("Maremma"+idMulta);
                        return params;
                    }
                };

                requestQueue = Volley.newRequestQueue(ActivityInfo.this);
                requestQueue.add(stringRequest);


            }//Fine click pulsante rimozione
        });
    }//Fine onCreate()

    private void presaIdmulta(){
        int posizioneIniziale = multe[spinner.getSelectedItemPosition()].indexOf(":") + 1;
        int posizioneFinale = multe[spinner.getSelectedItemPosition()].indexOf(",");
        idMulta = multe[spinner.getSelectedItemPosition()].substring(posizioneIniziale,posizioneFinale);
    }
}