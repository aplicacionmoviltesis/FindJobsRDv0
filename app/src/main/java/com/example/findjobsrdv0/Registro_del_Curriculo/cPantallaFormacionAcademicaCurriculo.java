package com.example.findjobsrdv0.Registro_del_Curriculo;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class cPantallaFormacionAcademicaCurriculo extends AppCompatActivity  {
    private TextView TituloFormacionAcademica;

    EditText etCarrera, etNivelPrimario, etNivelSecundario, etNivelSuperior;
    private Button BtnRegistrarFormAcad;

    String codigoc, idbuscadorc, carrerac, nivelprimarioc, nivelsecundarioc, nivelsuperiorc;



    TextView textInfo;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;



//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------

    FirebaseDatabase database;
    DatabaseReference otrosestudiosinset;


    RecyclerView recycler_otrosestudios;
    RecyclerView.LayoutManager layoutManager;
//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------


    String detalleformacad = "";
    String Ukey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_c_pantalla_formacion_academica_curriculo);


//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------

        database = FirebaseDatabase.getInstance();
        otrosestudiosinset = database.getReference( "Otros_Cursos" );

        recycler_otrosestudios = (RecyclerView) findViewById( R.id.recyclerviewotroscursos );
        recycler_otrosestudios.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recycler_otrosestudios.setLayoutManager( layoutManager );

//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------


        TituloFormacionAcademica = (TextView) findViewById(R.id.xmlTituloFormacionAcademica);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Chomsky.otf");
        TituloFormacionAcademica.setTypeface(face);

        mDatabase = FirebaseDatabase.getInstance().getReference( "Formacion_Academica" );
        mAuth = FirebaseAuth.getInstance();

        etCarrera = (EditText) findViewById( R.id.xmlEditCarreraNameFA );
        etNivelSuperior = (EditText) findViewById( R.id.xmlEditNivelSuperiorNameFA );
        etNivelSecundario = (EditText) findViewById( R.id.xmlEditNivelSecundarioNameFA );
        etNivelPrimario = (EditText) findViewById( R.id.xmlEditNivelPrimarioNameFA );


/*
        BtnAñadirOtrosCursos = (Button) findViewById( R.id.AbrirOtrosCursos );
        BtnAñadirOtrosCursos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( cPantallaFormacionAcademicaCurriculo.this, cPantallaOtrosCursos.class );
                startActivity( intent );
            }
        } );
*/
        BtnRegistrarFormAcad = (Button) findViewById( R.id.xmlbtnAnadirformacionAcademica );
        BtnRegistrarFormAcad.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegistrarFormacionAcademica( detalleformacad );
            }
        } );


        if (getIntent() != null)
            detalleformacad = getIntent().getStringExtra( "DetalleFormacionAcademicaID" );

        if (!detalleformacad.isEmpty()) {
            RegistrarFormacionAcademica( detalleformacad );
        }



//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------

        loadOtrosEstudios(detalleformacad);
//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------

    }
    //---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------
    private void loadOtrosEstudios(String detalleformacad) {
        final FirebaseRecyclerAdapter<modeloOtrosCursos, OtrosEstudiosViewHolder> adapter = new FirebaseRecyclerAdapter<modeloOtrosCursos, OtrosEstudiosViewHolder>( modeloOtrosCursos.class, R.layout.card_view_otros_estudios_en_los_insert, OtrosEstudiosViewHolder.class,
                otrosestudiosinset.orderByChild( "idbuscador" ).equalTo( detalleformacad )) {
            @Override
            protected void populateViewHolder(OtrosEstudiosViewHolder ViewHolder, modeloOtrosCursos model, int position) {

                ViewHolder.txtInstitucion.setText( model.getOcInstitucionC() );
                ViewHolder.txtAno.setText( model.getOcAno() );
                ViewHolder.txtAreaoTema.setText( model.getOcAreaoTemaC() );
                ViewHolder.txtTipoEstudio.setText( model.getOcTipoEstudio() );

                Log.d( "hola", String.valueOf( model.getOcInstitucionC() ) );

                final modeloOtrosCursos clickItem = model;
                ViewHolder.setItemClickListener( new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        //       Toast.makeText( cPantallaRegistroCurriculo.this, "" + clickItem.getOcInstitucionC(), Toast.LENGTH_SHORT ).show();

                      /*  Intent CurriculoDetalle = new Intent( VistaCurriculo.this, DetalleCurriculo.class );
                        CurriculoDetalle.putExtra( "detallecurrID", adapter.getRef( position ).getKey() );
                        startActivity( CurriculoDetalle );
*/
                        //  Log.d("klk id",adapter.getRef( position ).getKey());

                        // Toast.makeText( PantalaVistaCurriculo.this, ""+clickItem.getNombre(), Toast.LENGTH_SHORT ).show();
                    }
                } );
            }
        };
        recycler_otrosestudios.setAdapter( adapter );
    }
//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------


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

        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String IdFormacionAcademica = mDatabase.push().getKey();

        FormacionAcademica formacionAcademica = new FormacionAcademica( IdFormacionAcademica, idbuscadorc, carrerac, nivelprimarioc, nivelsecundarioc, nivelsuperiorc );

        mDatabase.child( IdFormacionAcademica ).setValue( formacionAcademica );

        //DBReferenceCurriculos.child("empleos").child(IDEmpleo).setValue(empleos);
        // mDatabase.child(Ukey).push().setValue(formacionAcademica);//para registrarlo dentro del usuario que inicio sesion


    }
}
