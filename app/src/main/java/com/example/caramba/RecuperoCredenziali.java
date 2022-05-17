package com.example.caramba;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RecuperoCredenziali extends AppCompatActivity {
    private final String url = "http://pagu.ddns.net/api.php";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recupero_credenziali);


    }//Chiusura onCreate

    private void richiestaCredenzialiPerse(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                error -> Toast.makeText(RecuperoCredenziali.this,"Errore", Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("function", "");
                params.put("", "");
                params.put("j", "");
                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(RecuperoCredenziali.this);
        requestQueue.add(stringRequest);
    }
}