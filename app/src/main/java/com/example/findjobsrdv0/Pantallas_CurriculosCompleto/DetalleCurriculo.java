package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaDetallesEmpleo;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Curriculos;
import com.example.findjobsrdv0.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetalleCurriculo extends AppCompatActivity {

    TextView txtNombreCurr, txtApellidoCurr, txtCedulaCurr, txtEmailCurr, txtTelefonoCurr, txtCelularCurr, txtprovinciaCurr, txtEstadoCivil, txtDireccionCurr, txtOcupacion, txtIdioma,
            txtEstadoActualCur, txtGradoMayorCurr, txtHabilidades, txtFecha;

    FirebaseDatabase database;
    DatabaseReference detalelcurriculo;


    String detallecurrid = "";
    Button btnIrFormacionAcademica, btnIrReferencia, btnIrExperienciaLab, btnOtrosEstudios;

//---------  para los favoritos ---------------------------------------------------------------------------------------------------------------------------------------------------------

    ToggleButton btnfavoritoCurriculo;
    FirebaseDatabase prueba;
    DatabaseReference DbLikesFavCurri;

    FirebaseAuth mFirebaseAuth;
    FirebaseUser userActivo;

    String sIdPersonaMarco;
//---------  para los favoritos ---------------------------------------------------------------------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detalle_curriculo );

//---------  para los favoritos ---------------------------------------------------------------------------------------------------------------------------------------------------------

        prueba = FirebaseDatabase.getInstance();
        DbLikesFavCurri = prueba.getReference();
        DbLikesFavCurri.keepSynced( true );

        btnfavoritoCurriculo = (ToggleButton) findViewById( R.id.Curriculosfavoritos );
        btnfavoritoCurriculo.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if (b) {
                    marcarFavorito();
                    Log.d( "estado activo", String.valueOf( b ) );
                }else {
                    final Query prueba = DbLikesFavCurri.child( "Empleadores" ).child( sIdPersonaMarco )
                            .child( "likes" ).orderByChild( "IdEmpleoLike" ).equalTo( detallecurrid );

                    prueba.addListenerForSingleValueEvent( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot pruebadataSnapshot : dataSnapshot.getChildren()) {
                                pruebadataSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText( DetalleCurriculo.this, "no funciono", Toast.LENGTH_SHORT ).show();
                        }
                    } );

                    Log.d( "estado inactivo", String.valueOf( b ) );

                }


            }
        } );

        mFirebaseAuth = FirebaseAuth.getInstance();
        userActivo = mFirebaseAuth.getCurrentUser();
        sIdPersonaMarco = userActivo.getUid();


//---------  para los favoritos ---------------------------------------------------------------------------------------------------------------------------------------------------------



        database = FirebaseDatabase.getInstance();
        detalelcurriculo = database.getReference( "Curriculos" );

        txtNombreCurr = (TextView) findViewById( R.id.xmlTvNombreDetalleCu );
        txtApellidoCurr = (TextView) findViewById( R.id.xmlTvApellidoDetalleCu );
        txtCedulaCurr = (TextView) findViewById( R.id.xmlTvCedulaDetalleCu );
        txtEmailCurr = (TextView) findViewById( R.id.xmlTvEmailDetalleCu );
        txtTelefonoCurr = (TextView) findViewById( R.id.xmlTvTelefonoDetalleCu );
        txtCelularCurr = (TextView) findViewById( R.id.xmlTvCelularDetalleCu );
        txtprovinciaCurr = (TextView) findViewById( R.id.xmlTvProvinciaDetalleCu );
        txtEstadoCivil = (TextView) findViewById( R.id.xmlTvEstadoCivilDetalleCu );
        txtDireccionCurr = (TextView) findViewById( R.id.xmlTvDireccionDetalleCu );
        txtOcupacion = (TextView) findViewById( R.id.xmlTvOcupacionDetalleCu );
        txtIdioma = (TextView) findViewById( R.id.xmlTvIdiomaDetalleCu );
        txtEstadoActualCur = (TextView) findViewById( R.id.xmlTvDisponibilidadDetalleCu );
        txtGradoMayorCurr = (TextView) findViewById( R.id.xmlTvMaestriaDetalleCu );
        txtHabilidades = (TextView) findViewById( R.id.xmlTvHabilidadesDetalleCu );
        txtFecha = (TextView) findViewById( R.id.xmlTvFechaNacimientoDetalleCu );


        if (getIntent() != null)
            detallecurrid = getIntent().getStringExtra( "detallecurrID" );

        if (!detallecurrid.isEmpty()) {
            goDetailCurriculo( detallecurrid );

            VerificarFavorito();
        }


        btnIrFormacionAcademica = (Button) findViewById( R.id.xmlBtnFormacionAcademicaDetalleCu );
        btnIrFormacionAcademica.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( DetalleCurriculo.this, DetalleFormacionAcademica.class );
                intent.putExtra( "DetalleFormacionAcademicaID", detallecurrid );
                startActivity( intent );
            }
        } );

        btnIrReferencia = (Button) findViewById( R.id.xmlBtnReferenciasDetalleCu );
        btnIrReferencia.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( DetalleCurriculo.this, DetalleReferencias.class );
                intent.putExtra( "DetalleReferenciaID", detallecurrid );
                startActivity( intent );
            }
        } );

        btnIrExperienciaLab = (Button) findViewById( R.id.xmlBtnExperienciaLaboralDetalleCu );
        btnIrExperienciaLab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( DetalleCurriculo.this, DetalleExperienciaLaboral.class );
                intent.putExtra( "DetalleExperienciaLabID", detallecurrid );
                startActivity( intent );
            }
        } );

        btnOtrosEstudios = (Button) findViewById( R.id.xmlBtnOtrosEstudiosDetalleCu );
        btnOtrosEstudios.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( DetalleCurriculo.this, DetalleOtrosEstudios.class );
                intent.putExtra( "DetalleOtrosEstudiosID", detallecurrid );
                startActivity( intent );
            }
        } );


//        btnfavoritoCurriculo = (ToggleButton) findViewById( R.id.curriculosfavoritos );
//        btnfavoritoCurriculo.setChecked( true );
//        btnfavoritoCurriculo.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    lanzaM( "ON" );
//                    cambiarColorTB( android.R.color.holo_red_dark, android.R.color.holo_red_dark );
//                } else {
//                    lanzaM( "OFF" );
//                    cambiarColorTB( android.R.color.holo_red_dark, android.R.color.black );
//                }
//
//            }
//        } );
//

    }

//
//    private void cambiarColorTB(int color1, int color2) {
//        this.btnfavoritoCurriculo.setBackgroundColor( ContextCompat.getColor( getBaseContext(), color1 ) );
//        this.btnfavoritoCurriculo.setBackgroundColor( ContextCompat.getColor( getBaseContext(), color2 ) );
//    }
//
//    private void lanzaM(String m) {
//        Toast.makeText( getBaseContext(), m, Toast.LENGTH_SHORT ).show();
//
//    }


    private void goDetailCurriculo(String detallecurrid) {
        detalelcurriculo.child( detallecurrid ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d( "hola", String.valueOf( dataSnapshot ) );
                Curriculos vistaCurriculomodel = dataSnapshot.getValue( Curriculos.class );

                txtNombreCurr.setText( vistaCurriculomodel.getsNombreC() );
                txtApellidoCurr.setText( vistaCurriculomodel.getsApellidoC() );
                txtCedulaCurr.setText( vistaCurriculomodel.getsCedulaC() );
                txtEmailCurr.setText( vistaCurriculomodel.getsEmailC() );
                txtTelefonoCurr.setText( vistaCurriculomodel.getsTelefonoC() );
                txtCelularCurr.setText( vistaCurriculomodel.getsCelularC() );
                txtprovinciaCurr.setText( vistaCurriculomodel.getsProvinciaC() );
                txtEstadoCivil.setText( vistaCurriculomodel.getsEstadoCivilC() );
                txtDireccionCurr.setText( vistaCurriculomodel.getsDireccionC() );
                txtOcupacion.setText( vistaCurriculomodel.getsOcupacionC() );
                txtEstadoActualCur.setText( vistaCurriculomodel.getsEstadoActualC() );
                txtGradoMayorCurr.setText( vistaCurriculomodel.getsGradoMayorC() );
                txtHabilidades.setText( vistaCurriculomodel.getsHabilidadesC() );
                txtFecha.setText( vistaCurriculomodel.getsFechaC() );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void VerificarFavorito() {
        final Query q = DbLikesFavCurri.child( "Empleadores" )//referencia empleadores
                .child( sIdPersonaMarco )//referencia usuario
                .child( "likes" )//referencia likes
                .orderByChild( "IdEmpleoLike" ).equalTo( detallecurrid );
//        Log.d( "fav", String.valueOf( sEmpleoIdE ) );
//        Log.d( "fav", String.valueOf( sIdPersonaAplico ) );

        q.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d( "fav", String.valueOf( dataSnapshot ) );
                for (DataSnapshot FavdataSnapshot : dataSnapshot.getChildren()) {

                    if (FavdataSnapshot != null) {
                        btnfavoritoCurriculo.setChecked( true );

                    } else {
                        btnfavoritoCurriculo.setChecked( false );
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    private void marcarFavorito() {
        String newLikeID = DbLikesFavCurri.push().getKey();

        DbLikesFavCurri.child( "Empleadores" )//referencia empleadores
                .child( sIdPersonaMarco )//referencia usuario
                .child( "likes" )//referencia likes
                .child( newLikeID )
                .child( "IdEmpleoLike" )
                .setValue( detallecurrid );
    }


}
