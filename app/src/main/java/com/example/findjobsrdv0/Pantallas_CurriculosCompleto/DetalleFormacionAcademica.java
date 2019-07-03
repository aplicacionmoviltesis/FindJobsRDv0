package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.findjobsrdv0.GeneralesApp.PantallaDetallesUniversidad;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Curriculos;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.FormacionAcademica;
import com.example.findjobsrdv0.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetalleFormacionAcademica extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference detFormacionAcad;

    private TextView txtNivelPrimario, txtNivelSecundario, txtNivelSuperior, txtCarrera, txtUniversidad;
    private String sCarreraFormAcad, sNivelPrimarioFormAcad,
            sNivelSecundarioFormAcad, sNivelSuperiorFormAcad, sUniversidadFormAcad;

    String detalleformacad = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detalle_formacion_academica );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayShowHomeEnabled( true );

        database = FirebaseDatabase.getInstance();
        detFormacionAcad = database.getReference( "Formacion_Academica" );

        txtNivelPrimario = (TextView) findViewById( R.id.xmlTVprimerNivelDetalleFormAcad );
        txtNivelSecundario = (TextView) findViewById( R.id.xmlTVSegundoNivelDetalleFormAcad );
        txtNivelSuperior = (TextView) findViewById( R.id.xmlTVNivelSuperiorDetalleFormAcad );
        txtCarrera = (TextView) findViewById( R.id.xmlTVCarreraDetalleFormAcad );
        txtUniversidad = (TextView) findViewById( R.id.xmlTVUniversidadDetalleFormAcad );


        txtUniversidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUniversidad();
            }
        });


        if (getIntent() != null) {
            detalleformacad = getIntent().getStringExtra( "DetalleFormacionAcademicaID" );

            if (!detalleformacad.isEmpty()) {
                cardarcamposformacionacad( detalleformacad );
            }
        }
    }

    private void cardarcamposformacionacad(String detalleformacad) {

        Query query = detFormacionAcad.orderByChild( "sIdCurriculoFormAcad" ).equalTo( detalleformacad );
        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot FavdataSnapshot : dataSnapshot.getChildren()) {

                    sNivelPrimarioFormAcad = FavdataSnapshot.child( "sNivelPrimarioFormAcad" ).getValue( String.class );
                    if (sNivelPrimarioFormAcad != null && sNivelPrimarioFormAcad != "") {
                        txtNivelPrimario.setText( sNivelPrimarioFormAcad );
                    }

                    sNivelSecundarioFormAcad = FavdataSnapshot.child( "sNivelSecundarioFormAcad" ).getValue(String.class);
                    if (sNivelSecundarioFormAcad != null && sNivelSecundarioFormAcad != "") {
                        txtNivelSecundario.setText( sNivelSecundarioFormAcad );
                    }

                    sNivelSuperiorFormAcad = FavdataSnapshot.child( "sNivelSuperiorFormAcad" ).getValue(String.class);
                    if (sNivelSuperiorFormAcad != null && sNivelSuperiorFormAcad != "") {
                        txtNivelSuperior.setText( sNivelSuperiorFormAcad );
                    }

                    sCarreraFormAcad = FavdataSnapshot.child( "sCarreraFormAcad" ).getValue(String.class);
                    if (sCarreraFormAcad != null && sCarreraFormAcad != "") {
                        txtCarrera.setText( sCarreraFormAcad );
                    }

                    sUniversidadFormAcad = FavdataSnapshot.child( "sUniversidadFormAcad" ).getValue(String.class);
                    if (sUniversidadFormAcad != null && sUniversidadFormAcad != "") {
                        txtUniversidad.setText( sUniversidadFormAcad );
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    public void goUniversidad() {

        Intent intent = new Intent( DetalleFormacionAcademica.this, PantallaDetallesUniversidad.class);
        intent.putExtra("sNombreUniDetallekey", txtUniversidad.getText().toString().trim());
        String hola = txtUniversidad.getText().toString().trim();
        Log.d("klkpaginaweb", hola);
        startActivity(intent);

    }
}