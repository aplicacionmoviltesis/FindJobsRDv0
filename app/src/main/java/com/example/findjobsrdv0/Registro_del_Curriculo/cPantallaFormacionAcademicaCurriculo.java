package com.example.findjobsrdv0.Registro_del_Curriculo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.R;

import com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.FormacionAcademica;
import com.example.findjobsrdv0.Vista_recycler_en_los_insert.recyclerOtrosEstudios.Modelo.modeloOtrosCursos;
import com.example.findjobsrdv0.Vista_recycler_en_los_insert.recyclerOtrosEstudios.ViewHolder.OtrosEstudiosViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;


public class cPantallaFormacionAcademicaCurriculo extends AppCompatActivity {
    private TextView TituloFormacionAcademica;

    EditText etCarrera, etNivelPrimario, etNivelSecundario, etNivelSuperior;
    private Button BtnRegistrarFormAcad;

    String codigoc, idbuscadorc, carrerac, nivelprimarioc, nivelsecundarioc, nivelsuperiorc;


    TextView textInfo;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    String detalleformacad = "";
   // String Ukey;
    String idusuariosregistrado;


    String idActualizar;

    String Ukey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_c_pantalla_formacion_academica_curriculo );

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


        cardarcamposformacionacad( Ukey );


        BtnRegistrarFormAcad = (Button) findViewById( R.id.xmlbtnAnadirformacionAcademica );
        BtnRegistrarFormAcad.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegistrarFormacionAcademica( detalleformacad );
            }
        } );

        Button btnactualizarformacionacad = findViewById( R.id.xmlbtnActualizarformacionAcademica );
        btnactualizarformacionacad.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcrualizarFormacionAcad(detalleformacad);
            }
        } );


        if (getIntent() != null)
            detalleformacad = getIntent().getStringExtra( "DetalleFormacionAcademicaID" );

        if (!detalleformacad.isEmpty()) {
            RegistrarFormacionAcademica( detalleformacad );
        }


    }


    private void cardarcamposformacionacad(String ukey) {

        mDatabase = FirebaseDatabase.getInstance().getReference( "Formacion_Academica" );
        Query q = mDatabase.orderByChild( "idusuarioregistrado" ).equalTo( ukey );

        q.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Log.d( "holapdddddd", String.valueOf( dataSnapshot ) );

                    com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.FormacionAcademica formacionAcademica = datasnapshot.getValue(com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.FormacionAcademica.class);
                    etNivelPrimario.setText( formacionAcademica.getNivelprimarioc() );
                    etNivelSecundario.setText( formacionAcademica.getNivelsecundarioc() );
                    etNivelSuperior.setText( formacionAcademica.getNivelsuperiorc() );
                    etCarrera.setText( formacionAcademica.getCarrerac() );

                    idActualizar = formacionAcademica.getCodigoc();


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

    public void RegistrarFormacionAcademica(String detalleformacad) {

        carrerac = etCarrera.getText().toString().trim();
        nivelprimarioc = etNivelPrimario.getText().toString().trim();
        nivelsecundarioc = etNivelSecundario.getText().toString().trim();
        nivelsuperiorc = etNivelSuperior.getText().toString().trim();
        idbuscadorc = detalleformacad;

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

        FormacionAcademica formacionAcademica = new FormacionAcademica( IdFormacionAcademica, idbuscadorc, idusuariosregistrado, carrerac, nivelprimarioc, nivelsecundarioc, nivelsuperiorc );

        mDatabase.child( IdFormacionAcademica ).setValue( formacionAcademica );

        limpiarCampor();

        //DBReferenceCurriculos.child("empleos").child(IDEmpleo).setValue(empleos);
        // mDatabase.child(Ukey).push().setValue(formacionAcademica);//para registrarlo dentro del usuario que inicio sesion


    }


    private void AcrualizarFormacionAcad(String ukey) {

        carrerac = etCarrera.getText().toString().trim();
        nivelprimarioc = etNivelPrimario.getText().toString().trim();
        nivelsecundarioc = etNivelSecundario.getText().toString().trim();
        nivelsuperiorc = etNivelSuperior.getText().toString().trim();
        idbuscadorc = ukey;

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

        FormacionAcademica formacionAcademica = new FormacionAcademica( idActualizar, idbuscadorc, idusuariosregistrado, carrerac, nivelprimarioc, nivelsecundarioc, nivelsuperiorc );

        mDatabase.child( idActualizar ).setValue( formacionAcademica );

      //  limpiarCampor();

    }


}
