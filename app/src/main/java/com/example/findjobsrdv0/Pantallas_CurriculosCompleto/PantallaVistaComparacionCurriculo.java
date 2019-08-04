package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import android.content.Intent;
import android.os.Bundle;

import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.Curriculos;
import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.ExperienciaLaboral;

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

    private String sNombreC1, sProvinciaC1, sCarreraFomAcadC1, sIdiomaC1, sOcupacionC1, sGradoMayorC1;
    private TextView tvNombreC1, tvProvinciaC1, tvCarreraFomAcadC1, tvIdiomaC1, tvOcupacionC1, tvGradoMayorC1;

    private String sNombreC2, sProvinciaC2, sCarreraFomAcadC2, sIdiomaC2, sOcupacionC2, sGradoMayorC2;
    private TextView tvNombreC2, tvProvinciaC2, tvCarreraFomAcadC2, tvIdiomaC2, tvOcupacionC2, tvGradoMayorC2;

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
        tvCarreraFomAcadC1 = (TextView) findViewById( R.id.xmlCarreraC1 );
        tvIdiomaC1 = (TextView) findViewById( R.id.xmlIdiomaC1 );
        tvOcupacionC1 = (TextView) findViewById( R.id.xmlOcupacionC1 );
        tvGradoMayorC1 = (TextView) findViewById( R.id.xmlGradoMayorC1 );
        btnVerMasC1 = (Button) findViewById( R.id.xmlVerMasC1 );

        tvNombreC2 = (TextView) findViewById( R.id.xmlNombreC2 );
        tvProvinciaC2 = (TextView) findViewById( R.id.xmlProvinciaC2 );
        tvCarreraFomAcadC2 = (TextView) findViewById( R.id.xmlCarreraC2 );
        tvIdiomaC2 = (TextView) findViewById( R.id.xmlIdiomaC2 );
        tvOcupacionC2 = (TextView) findViewById( R.id.xmlOcupacionC2 );
        tvGradoMayorC2 = (TextView) findViewById( R.id.xmlGradoMayorC2 );
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
        DBCurriculoComparados.child(getResources().getString(R.string.Ref_Curriculos)).orderByChild( "sIdCurriculo" ).equalTo( sIDCurriculoC1 ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Curriculos DatosCurriculo = datasnapshot.getValue( Curriculos.class );

                    ExperienciaLaboral klk = dataSnapshot.getValue( ExperienciaLaboral.class );

                    sIdCurriculoC1 = DatosCurriculo.getsIdCurriculo();
                    sNombreC1 = DatosCurriculo.getsNombreC();
                    sProvinciaC1 = DatosCurriculo.getsProvinciaC();
                    sCarreraFomAcadC1 = DatosCurriculo.getsCarreraFormAcad();
                    sIdiomaC1 = DatosCurriculo.getsIdiomaC();
                    sOcupacionC1 = DatosCurriculo.getsOcupacionC();
                    sGradoMayorC1 = DatosCurriculo.getsGradoMayorC();

                    tvNombreC1.setText( sNombreC1.toUpperCase() );
                    tvProvinciaC1.setText( sProvinciaC1 );
                    tvCarreraFomAcadC1.setText( sCarreraFomAcadC1 );
                    tvIdiomaC1.setText( sIdiomaC1 );
                    tvOcupacionC1.setText( sOcupacionC1 );
                    tvGradoMayorC1.setText( sGradoMayorC1 );
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

                    sIdCurriculoC2 = DatosCurriculo.getsIdCurriculo();
                    sNombreC2 = DatosCurriculo.getsNombreC();
                    sProvinciaC2 = DatosCurriculo.getsProvinciaC();
                    sCarreraFomAcadC2 = DatosCurriculo.getsCarreraFormAcad();
                    sIdiomaC2 = DatosCurriculo.getsIdiomaC();
                    sOcupacionC2 = DatosCurriculo.getsOcupacionC();
                    sGradoMayorC2 = DatosCurriculo.getsGradoMayorC();

                    tvNombreC2.setText( sNombreC2.toUpperCase() );
                    tvProvinciaC2.setText( sProvinciaC2 );
                    tvCarreraFomAcadC2.setText( sCarreraFomAcadC2 );
                    tvIdiomaC2.setText( sIdiomaC2 );
                    tvOcupacionC2.setText( sOcupacionC2 );
                    tvGradoMayorC2.setText( sGradoMayorC2 );


                }

                Log.d("CVCurriculo::::", String.valueOf(dataSnapshot));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }
}
