package com.example.caramba;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaMulte extends AppCompatActivity {
    private RequestQueue requestQueue;
    LinearLayout layoutStringhe;

    Button bottoneIndietro2;

    ArrayList<String> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lista_multe);

        bottoneIndietro2 = findViewById(R.id.bottoneIndietro2);
        bottoneIndietro2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(!Impostazioni.accessoFantasmagorico) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(ListaMulte.this, "Successo", Toast.LENGTH_LONG).show();

                            try {
                                response = response.substring(33,response.length());
                                response = response.replaceAll("\"","");
                                response = response.replaceAll("GROUP_CONCAT","Effrazione");
                                response = response.replaceAll("Appartiene.nomeViolazione","Effrazione");
                                String tmp;
                                boolean tpo =true;
                                int contatore = 0;
                                try {
                                    do {
                                        int valoreFine;
                                        System.out.println(response);
                                        if(response.length() < 15){
                                            tpo = false;
                                        }else {
                                            valoreFine = response.indexOf("idMulta", 8);
                                            if(valoreFine != -1) {
                                                tmp = response.substring(0, valoreFine - 3);
                                                response = response.substring(valoreFine, response.length());

                                                lista.add(tmp);
                                            }else{
                                                tpo = false;
                                            }
                                        }
                                        /*
                                         * Si esce dal ciclo dal try-catch, richiamata nel momento
                                         * dell'esecuzione del metodo indexOf()
                                         */
                                    }while (tpo);
                                    System.out.println("Uscita standard");
                                    avvioLista();
                                }catch (StringIndexOutOfBoundsException r){//Quando viene trovata l'ultima multa
                                    r.printStackTrace();
                                    System.out.println("Uscita dal catch");
                                }
                            } catch (StringIndexOutOfBoundsException e) {//Quando non c'è nessuna multa
                                Toast.makeText(ListaMulte.this, "Nessuna multa", Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    error -> Toast.makeText(ListaMulte.this, "Errore", Toast.LENGTH_LONG).show()) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("function", "elencoMulte");
                    params.put("token", MainActivity.token);
                    params.put("codiceMatricola", MainActivity.codiceMatricola);

                    return params;
                }
            };

            requestQueue = Volley.newRequestQueue(ListaMulte.this);
            requestQueue.add(stringRequest);
        }//Fine if controllo fantasma
    }//Fine onCreate

    private void avvioLista(){
        LinearLayout sv = (LinearLayout) findViewById(R.id.contenitoreStringhe);
        sv.setBackgroundColor(Color.TRANSPARENT);
        layoutStringhe = (LinearLayout) findViewById(R.id.contenitoreStringhe);

        String stringaLavorata;
        int valoreIniziale, valoreFinale;
        //Si contano quanti sono
        for(int i = 0; i < lista.size(); i++) {
            System.out.println("diron"+lista.get(lista.size()-1));

            if(i == 0){
                sv.setBackgroundColor(Color.parseColor("#673AB7"));
            }
            TextView tvAdd = new TextView(ListaMulte.this);
            tvAdd.setClickable(true);
            tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ii = new Intent(getApplicationContext(), ActivityInfo.class);
                    ii.putStringArrayListExtra("lista", lista);
                    startActivity(ii);
                }
            });


            valoreIniziale = lista.get(i).indexOf("targa:");
            valoreFinale = lista.get(i).indexOf("importo:");

            stringaLavorata = lista.get(i).substring(valoreIniziale,valoreFinale);
            stringaLavorata = stringaLavorata.replace(",luogo", ",\nluogo");
            tvAdd.setText(stringaLavorata);
            tvAdd.setHeight(220);
            tvAdd.setTextSize(15);
            tvAdd.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tvAdd.setGravity(Gravity.CENTER_VERTICAL);
            tvAdd.setTextColor(Color.WHITE);
            layoutStringhe.addView(tvAdd);
        }
    }
}