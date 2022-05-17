package com.example.caramba;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Impostazioni extends AppCompatActivity {
    Button rimozione, cambio, accessoFantasma;
    ImageButton indietro;
    TextView testoToken, textCambiaLink;

    public static Boolean accessoFantasmagorico = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_impostazioni);

        testoToken = findViewById(R.id.testoToken);
        testoToken.setText("Token: " + MainActivity.token);

        textCambiaLink = findViewById(R.id.textCambiaLink);
        textCambiaLink.setText(MainActivity.url);

        cambio = findViewById(R.id.cambio);
        cambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Impostazioni.this,"Link cambiato", Toast.LENGTH_LONG).show();
                MainActivity.url = textCambiaLink.getText().toString().trim();
            }
        });

        indietro = findViewById(R.id.indietro);
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rimozione = findViewById(R.id.rimozione);
        rimozione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Impostazioni.this,"Credenziali rimosse", Toast.LENGTH_LONG).show();
                MainActivity.sp.edit().clear().apply();
            }
        });

        accessoFantasma = findViewById(R.id.accessoFantasma);
        accessoFantasma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessoFantasmagorico=!accessoFantasmagorico;
                if(accessoFantasmagorico)
                    Toast.makeText(Impostazioni.this,"Modalità fantasma attivato", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Impostazioni.this,"Modalità fantasma disattivato", Toast.LENGTH_LONG).show();
            }
        });
    }
}