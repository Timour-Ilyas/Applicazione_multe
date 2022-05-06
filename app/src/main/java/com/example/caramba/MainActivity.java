package com.example.caramba;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static String token = "NULL Artificiale", codiceMatricola = "NULL Artificiale";

    private Button button;
    private EditText campoCodiceMatricola, campoPassword;

    private final String url = "http://pagu.ddns.net/api.php";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.pulsanteEntra);
        campoCodiceMatricola = findViewById(R.id.editTextCodiceMatricola);
        campoPassword = findViewById(R.id.editTextPassword);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codiceMatricola = campoCodiceMatricola.getText().toString().trim();

                //Controllo che le credenziali siano corrette
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(MainActivity.this,"Successo", Toast.LENGTH_LONG).show();

                                try {
                                    response = response.substring(35, response.length() - 2);
                                    token = response;

                                    //Se l'accesso è corretto cambia
                                    Intent i = new Intent(getApplicationContext(),MenuPrincipale.class);
                                    startActivity(i);
                                }catch (StringIndexOutOfBoundsException e){
                                    System.out.println("Credenziali errate");
                                    Toast.makeText(MainActivity.this,"Credenziali errate", Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        error -> Toast.makeText(MainActivity.this,"Errore", Toast.LENGTH_LONG).show()) {
                    
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<>();
                        params.put("function", "login");
                        params.put("codiceMatricola", codiceMatricola);
                        params.put("password", campoPassword.getText().toString().trim());
                        return params;
                    }
                };

                requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);
            }
        });
    }//Fine onCreate
}