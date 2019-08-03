package com.example.findjobsrdv0.GeneralesApp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import com.example.findjobsrdv0.Administradores.PantallaAdministradorUsuarios;
import com.example.findjobsrdv0.Administradores.PantallaConfiguracionAdministrador;
import com.example.findjobsrdv0.PantallaBuscarEmpleosReal;
import com.example.findjobsrdv0.R;


public class PantallaCarga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_carga);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PantallaCarga.this, PantallaModoUsuario.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
