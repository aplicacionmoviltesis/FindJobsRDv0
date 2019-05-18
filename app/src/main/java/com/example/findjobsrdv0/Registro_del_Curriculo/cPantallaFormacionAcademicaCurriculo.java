package com.example.findjobsrdv0.Registro_del_Curriculo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


public class cPantallaFormacionAcademicaCurriculo extends AppCompatActivity  {
    private TextView TituloFormacionAcademica;

    EditText etCarrera, etNivelPrimario, etNivelSecundario, etNivelSuperior;
    private Button BtnRegistrarFormAcad, BtnAñadirOtrosCursos;

    String faCodigoC, faIdBuscadorC, afCarreraC, afNivelprimarioC, afNivelsecundarioC, afNivelsuperiorC;


    TextView textInfo;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;



//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------

    FirebaseDatabase database;
    DatabaseReference otrosestudiosinset;


    RecyclerView recycler_otrosestudios;
    RecyclerView.LayoutManager layoutManager;
//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------




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

        BtnAñadirOtrosCursos = (Button)findViewById( R.id.AbrirOtrosCursos );
        BtnAñadirOtrosCursos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( cPantallaFormacionAcademicaCurriculo.this, cPantallaOtrosCursos.class );
                    startActivity( intent );
            }
        } );
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

                RegistrarFormacionAcademica();
            }
        } );



//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------
        loadOtrosEstudios();
//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------

    }
    //---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------
    private void loadOtrosEstudios() {
        final FirebaseRecyclerAdapter<modeloOtrosCursos, OtrosEstudiosViewHolder> adapter = new FirebaseRecyclerAdapter<modeloOtrosCursos, OtrosEstudiosViewHolder>( modeloOtrosCursos.class, R.layout.card_view_otros_estudios_en_los_insert, OtrosEstudiosViewHolder.class, otrosestudiosinset ) {
            @Override
            protected void populateViewHolder(OtrosEstudiosViewHolder ViewHolder, modeloOtrosCursos model, int position) {

                ViewHolder.txtInstitucion.setText( model.getOcInstitucionC() );
                ViewHolder.txtAno.setText( model.getOcAno() );
                ViewHolder.txtAreaoTema.setText( model.getOcAreaoTemaC() );
                ViewHolder.txtTipoEstudio.setText( model.getOcTipoEstudio() );

                Log.d( "hola", String.valueOf( ViewHolder ) );

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


    public void RegistrarFormacionAcademica() {

        afCarreraC = etCarrera.getText().toString().trim();
        afNivelprimarioC = etNivelPrimario.getText().toString().trim();
        afNivelsecundarioC = etNivelSecundario.getText().toString().trim();
        afNivelsuperiorC = etNivelSuperior.getText().toString().trim();


        if (TextUtils.isEmpty( afCarreraC )) {
            etCarrera.setError( "Campo vacío, por favor escriba el nombre " );
            return;
        }
     /*   if (TextUtils.isEmpty( cApellido )) {
            etApellido.setError( "Campo vacío, por favor escriba el apellido" );
            return;
        }
   */

        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String afIdBuscardorC = Ukey;


        String IdFormacionAcademica = mDatabase.push().getKey();

        FormacionAcademica formacionAcademica = new FormacionAcademica( IdFormacionAcademica, afIdBuscardorC, afCarreraC, afNivelprimarioC, afNivelsecundarioC, afNivelsuperiorC );


        mDatabase.child( IdFormacionAcademica ).setValue( formacionAcademica );

        //DBReferenceCurriculos.child("empleos").child(IDEmpleo).setValue(empleos);
        // mDatabase.child(Ukey).push().setValue(formacionAcademica);//para registrarlo dentro del usuario que inicio sesion


    }
}
