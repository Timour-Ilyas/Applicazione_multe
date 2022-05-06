package com.example.caramba;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MenuPrincipale extends AppCompatActivity {
    private Button pulsanteFaiMulte, pulsanteListaMulte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_principale);

        pulsanteFaiMulte = findViewById(R.id.PulsanteFaiMulte);
        pulsanteListaMulte = findViewById(R.id.PulsanteListaMulte);

        pulsanteFaiMulte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),FaiMulta.class);
                startActivity(i);
            }
        });

        pulsanteListaMulte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ListaMulte.class);
                startActivity(i);
            }
        });
    }
}