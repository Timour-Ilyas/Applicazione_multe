package com.example.caramba;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static String token = "NULL Artificiale", codiceMatricola = "NULL Artificiale";

    private Button button;
    private EditText campoCodiceMatricola, campoPassword;

    private Button scrittaCredenziali;

    private ImageButton impostazioni;

    public static String url = "http://pagu.ddns.net/api.php";
    private RequestQueue requestQueue;

    CheckBox check_ricorda;
    static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        check_ricorda = findViewById(R.id.check_ricorda);
        sp = getSharedPreferences("multe",MODE_PRIVATE);

        button = findViewById(R.id.pulsanteEntra);
        campoCodiceMatricola = findViewById(R.id.editTextCodiceMatricola);
        campoPassword = findViewById(R.id.editTextPassword);

        campoCodiceMatricola.setText(sp.getString("0",""));
        campoPassword.setText(sp.getString("1",""));

        scrittaCredenziali = findViewById(R.id.scrittaCredenziali);

        impostazioni = findViewById(R.id.impostazioni);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Impostazioni.accessoFantasmagorico) {
                    codiceMatricola = campoCodiceMatricola.getText().toString().trim();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(MainActivity.this, "Successo", Toast.LENGTH_LONG).show();

                                    try {
                                        response = response.substring(35, response.length() - 2);
                                        token = response.trim();

                                        //Se l'accesso è corretto controlla se si vogliono salvare le credenziali
                                        if (check_ricorda.isChecked()) {
                                            SharedPreferences.Editor editor = getSharedPreferences("multe", MODE_PRIVATE).edit();
                                            editor.putString("0", codiceMatricola);
                                            editor.putString("1", campoPassword.getText().toString().trim());
                                            editor.apply();
                                        }
                                        //Dopo il salvataggio credenziali si cambia activity
                                        Intent i = new Intent(getApplicationContext(), MenuPrincipale.class);
                                        startActivity(i);
                                    } catch (StringIndexOutOfBoundsException e) {
                                        System.out.println("Credenziali errate");
                                        Toast.makeText(MainActivity.this, "Credenziali errate", Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            error -> Toast.makeText(MainActivity.this, "Errore", Toast.LENGTH_LONG).show()) {

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("function", "login");
                            params.put("codiceMatricola", codiceMatricola);
                            params.put("password", campoPassword.getText().toString().trim());
                            return params;
                        }
                    };

                    requestQueue = Volley.newRequestQueue(MainActivity.this);
                    requestQueue.add(stringRequest);
                }else{
                    Intent i = new Intent(getApplicationContext(), MenuPrincipale.class);
                    startActivity(i);
                }
            }
        });


        scrittaCredenziali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RecuperoCredenziali.class);
                startActivity(i);
            }
        });

        impostazioni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Impostazioni.class);
                startActivity(i);
            }
        });
    }//Fine onCreate
}