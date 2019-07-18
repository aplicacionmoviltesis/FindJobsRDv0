package com.example.findjobsrdv0.Administradores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.findjobsrdv0.GeneralesApp.Opiniones_Sugerencias;
import com.example.findjobsrdv0.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PantallaDetalleOpiniones_Sugerencias extends AppCompatActivity {

    private TextView TvTituloOpiSug, TvIdOpinionSugerencia, TvDecripcionOpiSug,
            TvFechaOpiSug, TvIdUserOpiSug;
    private ImageView ImagenOpiSug;

    private String sIdOpiSug = "";

    private FirebaseDatabase databaseOpiSug;
    private DatabaseReference DBOpiSug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalle_opiniones);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TvTituloOpiSug = (TextView) findViewById(R.id.xmlTvTituloOpiSug);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoslab.bold.ttf");
        TvTituloOpiSug.setTypeface(face);

        databaseOpiSug = FirebaseDatabase.getInstance();
        DBOpiSug = databaseOpiSug.getReference(getResources().getString(R.string.Ref_OpinionesSugerenciasApp));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        ImagenOpiSug = (ImageView) findViewById(R.id.xmlImagenOpiSug);
        TvIdOpinionSugerencia = (TextView) findViewById(R.id.xmlTvIdOpiSug);
        TvDecripcionOpiSug = (TextView) findViewById(R.id.xmlTvDecripcionOpiSug);
        TvFechaOpiSug = (TextView) findViewById(R.id.xmlTvFechaOpiSug);
        TvIdUserOpiSug = (TextView) findViewById(R.id.xmlTvIdUserOpiSug);

        if (getIntent() != null) {
            sIdOpiSug = getIntent().getStringExtra("sOpiSugId");
            if (!sIdOpiSug.isEmpty()) {
                goDetalleOpiSug(sIdOpiSug);
            }
        }
    }

    private void goDetalleOpiSug(String sIdOpiSug) {

        DBOpiSug.child(sIdOpiSug).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Opiniones_Sugerencias opiniones_sugerencias = dataSnapshot.getValue(Opiniones_Sugerencias.class);
                    Log.d("holap", String.valueOf(opiniones_sugerencias));

                    if(!opiniones_sugerencias.getsImagenOpiSug().equals(null)){
                        Picasso.get().load(opiniones_sugerencias.getsImagenOpiSug()).into(ImagenOpiSug);
                    }
                    TvTituloOpiSug.setText(opiniones_sugerencias.getsTituloOpiSug());
                    TvIdOpinionSugerencia.setText(opiniones_sugerencias.getsIdOpinionSugerencia());
                    TvDecripcionOpiSug.setText(opiniones_sugerencias.getsDecripcionOpiSug());
                    TvFechaOpiSug.setText(opiniones_sugerencias.getsFechaOpiSug());
                    TvIdUserOpiSug.setText(opiniones_sugerencias.getsIdUserOpiSug());

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
