package com.example.findjobsrdv0;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PantallaCarga extends AppCompatActivity {
    private DatabaseReference DBReferenceEmplos;
    private FirebaseDatabase database;
    private Query DBprovincia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_carga);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        DBReferenceEmplos = FirebaseDatabase.getInstance().getReference("empleos");
        database = FirebaseDatabase.getInstance();
        DBprovincia = database.getReference("provincias");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(PantallaCarga.this, PantallaRegistrarEmpleos.class);
                startActivity(intent);
                finish();

            }
        },5000);

    }
}
