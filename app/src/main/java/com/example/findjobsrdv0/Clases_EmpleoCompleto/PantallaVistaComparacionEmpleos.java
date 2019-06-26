package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.findjobsrdv0.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PantallaVistaComparacionEmpleos extends AppCompatActivity {

    private FirebaseDatabase databaseEmpleosComparados;
    private DatabaseReference DBEmpleosComparados;

    private String sNombreE1, sProvinciaE1, sHorarioE1, sTipodeContratoE1, sSalarioE1, sOtrosDatosE1;
    private TextView tvNombreE1, tvProvinciaE1, tvHorarioE1, tvTipodeContratoE1, tvSalarioE1, tvOtrosDatosE1;

    private String sNombreE2, sProvinciaE2, sHorarioE2, sTipodeContratoE2, sSalarioE2, sOtrosDatosE2;
    private TextView tvNombreE2, tvProvinciaE2, tvHorarioE2, tvTipodeContratoE2, tvSalarioE2, tvOtrosDatosE2;

    private String sIdEmpleoE1 = "", sIdEmpleoE2 = "";

    private Button btnVerMasE1,btnVerMasE2;

    private TextView tvTiCompararEmpleos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_vista_comparacion_empleos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvTiCompararEmpleos = (TextView) findViewById(R.id.xmlCompararEmpleos);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoslab.bold.ttf");
        tvTiCompararEmpleos.setTypeface(face);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        databaseEmpleosComparados = FirebaseDatabase.getInstance();
        DBEmpleosComparados = databaseEmpleosComparados.getReference();

        tvNombreE1 = (TextView) findViewById(R.id.xmlNombreE1);
        tvProvinciaE1 = (TextView) findViewById(R.id.xmlProvinciaE1);
        tvHorarioE1 = (TextView) findViewById(R.id.xmlHorarioE1);
        tvTipodeContratoE1 = (TextView) findViewById(R.id.xmlTipodeContratoE1);
        tvSalarioE1 = (TextView) findViewById(R.id.xmlSalarioE1);
        tvOtrosDatosE1 = (TextView) findViewById(R.id.xmlOtrosdatosE1);
        btnVerMasE1 = (Button) findViewById(R.id.xmlVerMasE1);

        tvNombreE2 = (TextView) findViewById(R.id.xmlNombreE2);
        tvProvinciaE2 = (TextView) findViewById(R.id.xmlProvinciaE2);
        tvHorarioE2 = (TextView) findViewById(R.id.xmlHorarioE2);
        tvTipodeContratoE2 = (TextView) findViewById(R.id.xmlTipodeContratoE2);
        tvSalarioE2 = (TextView) findViewById(R.id.xmlSalarioE2);
        tvOtrosDatosE2 = (TextView) findViewById(R.id.xmlOtrosdatosE2);
        btnVerMasE2 = (Button) findViewById(R.id.xmlVerMasE2);


        btnVerMasE1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaVistaComparacionEmpleos.this, PantallaDetallesEmpleo.class);
                intent.putExtra("sEmpleoIdBuscado", sIdEmpleoE1);
                startActivity(intent);
            }
        });

        btnVerMasE2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaVistaComparacionEmpleos.this, PantallaDetallesEmpleo.class);
                intent.putExtra("sEmpleoIdBuscado", sIdEmpleoE2);
                startActivity(intent);
            }
        });


//        sIdEmpleoE1 = "-Lg4alOTsAzGLKms6tmu";
//        sIdEmpleoE2 = "-LgdHslsVo394m0k53Il";
//        SubirEmpleos(sIdEmpleoE1, sIdEmpleoE2);

        if (getIntent() != null) {
            sIdEmpleoE1 = getIntent().getStringExtra("sIdEmpleoComparar1");
            sIdEmpleoE2 = getIntent().getStringExtra("sIdEmpleoComparar2");
            if (!sIdEmpleoE1.isEmpty() && !sIdEmpleoE2.isEmpty() ) {
                SubirEmpleos(sIdEmpleoE1,sIdEmpleoE2);
            }
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void SubirEmpleos(String sIDEmpleoE1, String sIDEmpleoE2) {
        DBEmpleosComparados.child("empleos").orderByChild("sIDEmpleo").equalTo(sIDEmpleoE1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    Empleos DatosEmpleos = datasnapshot.getValue(Empleos.class);

                    sIdEmpleoE1 = DatosEmpleos.getsIDEmpleo();
                    sNombreE1 = DatosEmpleos.getsNombreEmpleoE();
                    sProvinciaE1 = DatosEmpleos.getsProvinciaE();
                    sTipodeContratoE1 = DatosEmpleos.getsTipoContratoE();
                    sHorarioE1 = DatosEmpleos.getsHorarioE();
                    sSalarioE1 = DatosEmpleos.getsSalarioE();
                    sOtrosDatosE1 = DatosEmpleos.getsOtrosDatosE();

                    tvNombreE1.setText(sNombreE1.toUpperCase());
                    tvProvinciaE1.setText(sProvinciaE1);
                    tvHorarioE1.setText(sHorarioE1);
                    tvTipodeContratoE1.setText(sTipodeContratoE1);
                    tvSalarioE1.setText(sSalarioE1);
                    tvOtrosDatosE1.setText(sOtrosDatosE1);
                }
                Log.d("CVEMPLEO::::", String.valueOf(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DBEmpleosComparados.child("empleos").orderByChild("sIDEmpleo").equalTo(sIDEmpleoE2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    Empleos DatosEmpleos = datasnapshot.getValue(Empleos.class);

                    sIdEmpleoE2 = DatosEmpleos.getsIDEmpleo();
                    sNombreE2 = DatosEmpleos.getsNombreEmpleoE();
                    sProvinciaE2 = DatosEmpleos.getsProvinciaE();
                    sTipodeContratoE2 = DatosEmpleos.getsTipoContratoE();
                    sHorarioE2 = DatosEmpleos.getsHorarioE();
                    sSalarioE2 = DatosEmpleos.getsSalarioE();
                    sOtrosDatosE2 = DatosEmpleos.getsOtrosDatosE();

                    tvNombreE2.setText(sNombreE2.toUpperCase());
                    tvProvinciaE2.setText(sProvinciaE2);
                    tvHorarioE2.setText(sHorarioE2);
                    tvTipodeContratoE2.setText(sTipodeContratoE2);
                    tvSalarioE2.setText(sSalarioE2);
                    tvOtrosDatosE2.setText(sOtrosDatosE2);
                }
                Log.d("CVEMPLEO::::", String.valueOf(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
