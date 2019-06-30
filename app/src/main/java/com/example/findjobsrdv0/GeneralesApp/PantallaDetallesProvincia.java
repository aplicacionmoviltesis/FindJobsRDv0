package com.example.findjobsrdv0.GeneralesApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.findjobsrdv0.Administradores.Universidades;
import com.example.findjobsrdv0.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PantallaDetallesProvincia extends AppCompatActivity {

    TextView TvNombreProvincia, TvDescripcionProvincia, TvDivisionProvincia,
            TvPoblacionProvincia,TvEconomiaProvincia,TvClimaProvincia,
            TvAtractivosProvincia;
    ImageView MostImagenProvincia;
    String sNombreProvinciakey = "";
    FirebaseDatabase database;
    Query DBprovincia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalles_provincia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        database = FirebaseDatabase.getInstance();
        DBprovincia = database.getReference("Provincias");

        TvNombreProvincia = (TextView) findViewById(R.id.xmlTvNombreProvincia);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoslab.bold.ttf");
        TvNombreProvincia.setTypeface(face);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        MostImagenProvincia = (ImageView) findViewById(R.id.xmlImagenProvincia);
        TvDescripcionProvincia = (TextView) findViewById(R.id.xmlTvDescripcionProvincia);
        TvDivisionProvincia = (TextView) findViewById(R.id.xmlTvDivisionProvincia);

        TvDivisionProvincia.setMovementMethod(new ScrollingMovementMethod());
        TvDescripcionProvincia.setMovementMethod(new ScrollingMovementMethod());

        TvPoblacionProvincia = (TextView) findViewById(R.id.xmlTvPoblacionProvincia);
        TvEconomiaProvincia = (TextView) findViewById(R.id.xmlTvEconomiaProvincia);
        TvClimaProvincia = (TextView) findViewById(R.id.xmlTvClimaProvincia);
        TvAtractivosProvincia = (TextView) findViewById(R.id.xmlTvAtractivosProvincia);

//        if (getIntent() != null) {
//            sNombreProvinciakey = getIntent().getStringExtra("sProvinciaDE");
//            if (!sNombreProvinciakey.isEmpty()) {
//                goDetalleProvincia(sNombreProvinciakey);
//            }
//        }

        sNombreProvinciakey = "La vega";
        goDetalleProvincia(sNombreProvinciakey);

    }


    private void goDetalleProvincia(String sNombreProvinciakey) {

        Query q = DBprovincia.orderByChild("sNombreProvincia").equalTo(sNombreProvinciakey);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

//                    Log.d("hola", String.valueOf(dataSnapshot));
//
//                    Provincias Dprovincia = new Provincias();
//                    Dprovincia.setsNombreProvincia(snapshot.child("Nombre_Provincia").getValue().toString());
//                    Dprovincia.setsImagenProvincia(snapshot.child("Imagen_Provincia").getValue().toString());
//                    Dprovincia.setsDescripcionProvincia(snapshot.child("Descripcion_Provincia").getValue().toString());
//                    Dprovincia.setsPoblacionProvincia(snapshot.child("Division_Provincia").getValue().toString());
//
//                    Picasso.get().load(Dprovincia.getsImagenProvincia()).into(MostImagenProvincia);
//
//                    TvNombreProvincia.setText(Dprovincia.getsNombreProvincia());
//                    TvDescripcionProvincia.setText(Dprovincia.getsDescripcionProvincia());
//                    TvDivisionProvincia.setText(Dprovincia.getsPoblacionProvincia());
//
//                    Log.d("probando", Dprovincia.getsImagenProvincia());

                    Provincias provincias = snapshot.getValue(Provincias.class);

                    Picasso.get().load(provincias.getsImagenProvincia()).into(MostImagenProvincia);

                    TvNombreProvincia.setText(provincias.getsNombreProvincia());
                    TvDescripcionProvincia.setText(provincias.getsDescripcionProvincia());
                    TvDivisionProvincia.setText(provincias.getsDivisionTerritorialProvincia());
                    TvPoblacionProvincia.setText(provincias.getsPoblacionProvincia());
                    TvEconomiaProvincia.setText(provincias.getsEconomiaProvincia());
                    TvClimaProvincia.setText(provincias.getsClimaProvincia());
                    TvAtractivosProvincia.setText(provincias.getsAtractivosProvincia());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

