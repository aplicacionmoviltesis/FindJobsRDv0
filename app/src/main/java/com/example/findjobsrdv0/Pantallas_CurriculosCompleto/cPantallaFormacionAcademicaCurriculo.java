package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Curriculos;
import com.example.findjobsrdv0.R;

import com.example.findjobsrdv0.Modelos_CurriculoCompleto.FormacionAcademica;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class cPantallaFormacionAcademicaCurriculo extends AppCompatActivity {
    private TextView TituloFormacionAcademica;

    EditText etCarrera, etNivelPrimario, etNivelSecundario, etNivelSuperior;
    private Button BtnRegistrarFormAcad;

    String codigoc, idCurriculo, carrerac, nivelprimarioc, nivelsecundarioc, nivelsuperiorc;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    String detalleformacad = "";
   // String Ukey;
    String idusuariosregistrado;

    String idActualizar;

    String Ukey;


    DatabaseReference databaseReferenceCurrilo;
    FirebaseDatabase database;

   String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_c_pantalla_formacion_academica_curriculo );

        database = FirebaseDatabase.getInstance();
        databaseReferenceCurrilo = database.getReference( "Curriculos" );



        Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        TituloFormacionAcademica = (TextView) findViewById( R.id.xmlTituloFormacionAcademica );
        Typeface face = Typeface.createFromAsset( getAssets(), "fonts/robotoslab.bold.ttf" );
        TituloFormacionAcademica.setTypeface( face );

        mDatabase = FirebaseDatabase.getInstance().getReference( "Formacion_Academica" );
        mAuth = FirebaseAuth.getInstance();

        etCarrera = (EditText) findViewById( R.id.xmlEditCarreraNameFA );
        etNivelSuperior = (EditText) findViewById( R.id.xmlEditNivelSuperiorNameFA );
        etNivelSecundario = (EditText) findViewById( R.id.xmlEditNivelSecundarioNameFA );
        etNivelPrimario = (EditText) findViewById( R.id.xmlEditNivelPrimarioNameFA );

     //   CargarformacadActualizar( detalleformacad );


//----------------------query para obtener el id del curriculo-------------------------------------------------------------------------------------


        Query query = databaseReferenceCurrilo.orderByChild( "sIdBuscadorEmpleo" ).equalTo( Ukey );
        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot FavdataSnapshot : dataSnapshot.getChildren()) {
                    Log.d( "datosdatasnapsht", String.valueOf( dataSnapshot ) );

                   id = FavdataSnapshot.child( "sIdCurriculo" ).getValue( String.class );
                    Log.d( "datoscurriculos", String.valueOf( id ) );
//                Curriculos datoscurriculos = dataSnapshot.getValue(Curriculos.class);
//                id = perro.getsIdCurriculo();

//                Log.d( "datoscurriculos", String.valueOf( datoscurriculos ) );
//                Log.d( "datosid", String.valueOf( id ) );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

//----------------------query para obtener el id del curriculo-------------------------------------------------------------------------------------



        BtnRegistrarFormAcad = (Button) findViewById( R.id.xmlbtnAnadirformacionAcademica );
        BtnRegistrarFormAcad.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegistrarFormacionAcademica( id );
            }
        } );

        Button btnactualizarformacionacad = findViewById( R.id.xmlbtnActualizarformacionAcademica );
        btnactualizarformacionacad.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActualizarFormacionAcad(Ukey, id);
            }
        } );


        if (getIntent() != null)
            detalleformacad = getIntent().getStringExtra( "DetalleFormacionAcademicaID" );

        if (!detalleformacad.isEmpty()) {
            RegistrarFormacionAcademica( id );
        }

        cardarcamposformacionacad( Ukey );

    }

    private void cardarcamposformacionacad(String ukey) {

        mDatabase = FirebaseDatabase.getInstance().getReference( "Formacion_Academica" );
        Query q = mDatabase.orderByChild( "sIdBuscadorEmpleoFormAcad" ).equalTo( ukey );

        q.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Log.d( "holapdddddd", String.valueOf( dataSnapshot ) );

                    FormacionAcademica formacionAcademica = datasnapshot.getValue( FormacionAcademica.class);
                    etNivelPrimario.setText( formacionAcademica.getsNivelPrimarioFormAcad() );
                    etNivelSecundario.setText( formacionAcademica.getsNivelSecundarioFormAcad() );
                    etNivelSuperior.setText( formacionAcademica.getsNivelSuperiorFormAcad() );
                    etCarrera.setText( formacionAcademica.getsCarreraFormAcad() );

                    idActualizar = formacionAcademica.getsIdFormacionAcademica();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }

    public void limpiarCampor() {
        etNivelPrimario.setText( "" );
        etNivelSecundario.setText( "" );
        etNivelSuperior.setText( "" );
        etCarrera.setText( "" );

    }

    private void ActualizarFormacionAcad(final String Ukey, final String id) {


        Log.d( "cuando lo recive", String.valueOf(detalleformacad) );
        carrerac = etCarrera.getText().toString().trim();
        nivelprimarioc = etNivelPrimario.getText().toString().trim();
        nivelsecundarioc = etNivelSecundario.getText().toString().trim();
        nivelsuperiorc = etNivelSuperior.getText().toString().trim();
        idCurriculo = id;

        Log.d( "cuando ya lo tiene", String.valueOf(idCurriculo) );



        if (TextUtils.isEmpty( carrerac )) {
            etCarrera.setError( "Campo vacío, por favor escriba el nombre " );
            return;
        }
     /*   if (TextUtils.isEmpty( cApellido )) {
            etApellido.setError( "Campo vacío, por favor escriba el apellido" );
            return;
        }
   */

        idusuariosregistrado = Ukey;

        //String IdFormacionAcademica = mDatabase.push().getKey();

        FormacionAcademica formacionAcademica = new FormacionAcademica( idActualizar, idCurriculo, idusuariosregistrado, carrerac, nivelprimarioc, nivelsecundarioc, nivelsuperiorc );

        mDatabase.child( idActualizar ).setValue( formacionAcademica );

        //  limpiarCampor();

    }

    public void RegistrarFormacionAcademica(String id) {

        carrerac = etCarrera.getText().toString().trim();
        nivelprimarioc = etNivelPrimario.getText().toString().trim();
        nivelsecundarioc = etNivelSecundario.getText().toString().trim();
        nivelsuperiorc = etNivelSuperior.getText().toString().trim();
        idCurriculo = id;

        if (TextUtils.isEmpty( carrerac )) {
            etCarrera.setError( "Campo vacío, por favor escriba el nombre " );
            return;
        }
     /*   if (TextUtils.isEmpty( cApellido )) {
            etApellido.setError( "Campo vacío, por favor escriba el apellido" );
            return;
        }
   */

        idusuariosregistrado = Ukey;

        String IdFormacionAcademica = mDatabase.push().getKey();

        FormacionAcademica formacionAcademica = new FormacionAcademica( IdFormacionAcademica, idCurriculo, idusuariosregistrado, carrerac, nivelprimarioc, nivelsecundarioc, nivelsuperiorc );

        mDatabase.child( IdFormacionAcademica ).setValue( formacionAcademica );

        limpiarCampor();

        //DBReferenceCurriculos.child("empleos").child(IDEmpleo).setValue(empleos);
        // mDatabase.child(Ukey).push().setValue(formacionAcademica);//para registrarlo dentro del usuario que inicio sesion

    }

}
