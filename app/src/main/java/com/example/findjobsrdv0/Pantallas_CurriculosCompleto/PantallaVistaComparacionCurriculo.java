package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import android.content.Intent;
import android.os.Bundle;

import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Curriculos;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.ExperienciaLaboral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.findjobsrdv0.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PantallaVistaComparacionCurriculo extends AppCompatActivity {

    private FirebaseDatabase databaseCurriculoComparados;
    private DatabaseReference DBCurriculoComparados;

    private String sNombreC1, sProvinciaC1, sAreaC1, sIdiomaC1, sOcupacionC1, sExperienciaLabC1;
    private TextView tvNombreC1, tvProvinciaC1, tvAreaC1, tvIdiomaC1, tvOcupacionC1, tvExperienciaLabC1;

    private String sNombreC2, sProvinciaC2, sAreaC2, sIdiomaC2, sOcupacionC2, sExperienciaLabC2;
    private TextView tvNombreC2, tvProvinciaC2, tvAreaC2, tvIdiomaC2, tvOcupacionC2, tvExperienciaLabC2;

    private String sIdCurriculoC1 = "", sIdCurriculoC2 = "";

    private Button btnVerMasC1, btnVerMasC2;

    private TextView tvTiCompararCurriculos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pantalla_vista_comparacion_curriculo );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

//        tvTiCompararCurriculos = (TextView) findViewById( R.id.xmlCompararEmpleos );
//        Typeface face = Typeface.createFromAsset( getAssets(), "fonts/robotoslab.bold.ttf" );
//        tvTiCompararCurriculos.setTypeface( face );

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayShowHomeEnabled( true );

        databaseCurriculoComparados = FirebaseDatabase.getInstance();
        DBCurriculoComparados = databaseCurriculoComparados.getReference();

        tvNombreC1 = (TextView) findViewById( R.id.xmlNombreC1 );
        tvProvinciaC1 = (TextView) findViewById( R.id.xmlProvinciaC1 );
        tvAreaC1 = (TextView) findViewById( R.id.xmlAreaC1 );
        tvIdiomaC1 = (TextView) findViewById( R.id.xmlIdiomaC1 );
        tvOcupacionC1 = (TextView) findViewById( R.id.xmlOcupacionC1 );
        tvExperienciaLabC1 = (TextView) findViewById( R.id.xmlExperienciaLboC1 );
        btnVerMasC1 = (Button) findViewById( R.id.xmlVerMasC1 );

        tvNombreC2 = (TextView) findViewById( R.id.xmlNombreC2 );
        tvProvinciaC2 = (TextView) findViewById( R.id.xmlProvinciaC2 );
        tvAreaC2 = (TextView) findViewById( R.id.xmlAreaC2 );
        tvIdiomaC2 = (TextView) findViewById( R.id.xmlIdiomaC2 );
        tvOcupacionC2 = (TextView) findViewById( R.id.xmlOcupacionC2 );
        tvExperienciaLabC2 = (TextView) findViewById( R.id.xmlExperienciaLboC2 );
        btnVerMasC2 = (Button) findViewById( R.id.xmlVerMasC2 );


        btnVerMasC1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( PantallaVistaComparacionCurriculo.this, DetalleCurriculo.class );
                intent.putExtra( "detallecurrID", sIdCurriculoC1 );
                startActivity( intent );
            }
        } );

        btnVerMasC2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( PantallaVistaComparacionCurriculo.this, DetalleCurriculo.class );
                intent.putExtra( "detallecurrID", sIdCurriculoC2 );
                startActivity( intent );
            }
        } );

        if (getIntent() != null) {
            sIdCurriculoC1 = getIntent().getStringExtra( "sIdCurriculoComparar1" );
            sIdCurriculoC2 = getIntent().getStringExtra( "sIdCurriculoComparar2" );
            if (!sIdCurriculoC1.isEmpty() && !sIdCurriculoC2.isEmpty()) {
                SubirEmpleos( sIdCurriculoC1, sIdCurriculoC2 );
            }
        }

//        FloatingActionButton fab = findViewById( R.id.fab );
//        fab.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
//                        .setAction( "Action", null ).show();
//            }
//        } );
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void SubirEmpleos(String sIDCurriculoC1, String sIDCurriculoC2) {
        DBCurriculoComparados.child( "Curriculos" ).orderByChild( "sIdCurriculo" ).equalTo( sIDCurriculoC1 ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Curriculos DatosCurriculo = datasnapshot.getValue( Curriculos.class );

                    ExperienciaLaboral klk = dataSnapshot.getValue( ExperienciaLaboral.class );

                    sIdCurriculoC1 = DatosCurriculo.getsIdCurriculo();
                    sNombreC1 = DatosCurriculo.getsNombreC();
                    sProvinciaC1 = DatosCurriculo.getsProvinciaC();
                    sAreaC1 = DatosCurriculo.getsAreaC();
                    sIdiomaC1 = DatosCurriculo.getsIdiomaC();
                    sOcupacionC1 = DatosCurriculo.getsOcupacionC();
                    sExperienciaLabC1 = klk.getsNombreEmpresaExpLab();

                    tvNombreC1.setText( sNombreC1.toUpperCase() );
                    tvProvinciaC1.setText( sProvinciaC1 );
                    tvAreaC1.setText( sAreaC1 );
                    tvIdiomaC1.setText( sIdiomaC1 );
                    tvOcupacionC1.setText( sOcupacionC1 );
                    tvExperienciaLabC1.setText( sExperienciaLabC1 );
                }

                Log.d("CVCurriculo::::", String.valueOf(dataSnapshot));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        DBCurriculoComparados.child( "Curriculos" ).orderByChild( "sIdCurriculo" ).equalTo( sIDCurriculoC2 ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Curriculos DatosCurriculo = datasnapshot.getValue( Curriculos.class );

                    ExperienciaLaboral klk = dataSnapshot.getValue( ExperienciaLaboral.class );

                    sIdCurriculoC2 = DatosCurriculo.getsIdCurriculo();
                    sNombreC2 = DatosCurriculo.getsNombreC();
                    sProvinciaC2 = DatosCurriculo.getsProvinciaC();
                    sAreaC2 = DatosCurriculo.getsAreaC();
                    sIdiomaC2 = DatosCurriculo.getsIdiomaC();
                    sOcupacionC2 = DatosCurriculo.getsOcupacionC();
                    sExperienciaLabC2 = klk.getsNombreEmpresaExpLab();

                    tvNombreC2.setText( sNombreC2.toUpperCase() );
                    tvProvinciaC2.setText( sProvinciaC2 );
                    tvAreaC2.setText( sAreaC2 );
                    tvIdiomaC2.setText( sIdiomaC2 );
                    tvOcupacionC2.setText( sOcupacionC2 );
                    tvExperienciaLabC2.setText( sExperienciaLabC2 );


                }

                Log.d("CVCurriculo::::", String.valueOf(dataSnapshot));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }
}
