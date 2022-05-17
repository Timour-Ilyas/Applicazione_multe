package com.example.caramba;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuPrincipale extends AppCompatActivity {
    private Button pulsanteFaiMulte, pulsanteListaMulte;
    private ImageButton impostazioniButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_principale);

        impostazioniButton = findViewById(R.id.impostazioniButton);
        impostazioniButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Impostazioni.class);
                startActivity(i);
            }
        });

        pulsanteFaiMulte = findViewById(R.id.PulsanteFaiMulte);
        pulsanteFaiMulte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),FaiMulta.class);
                startActivity(i);
            }
        });

        pulsanteListaMulte = findViewById(R.id.PulsanteListaMulte);
        pulsanteListaMulte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaMulte.class);
                startActivity(i);
            }
        });
    }
}