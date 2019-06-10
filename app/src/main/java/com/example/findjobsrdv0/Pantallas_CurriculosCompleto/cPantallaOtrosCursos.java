package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.OtrosCursos;
import com.example.findjobsrdv0.ViewHolders_CurriculosCompleto.OtrosEstudiosViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class cPantallaOtrosCursos extends AppCompatActivity {

    EditText etInstitucion, etAno, etAreaoTema;
    SearchableSpinner ssTipodeEstudio;
    Button BtnRegistrarOtrosCursos, btnAcualizarOtrosCursos;

    String ocInstitucionC, ocAnoC, ocAreaoTemaC, ocTipoEstudio, ocIdBuscardor;


//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------

    FirebaseRecyclerAdapter<OtrosCursos, OtrosEstudiosViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference otrosestudiosinset;

    RecyclerView recycler_otrosestudios;
    RecyclerView.LayoutManager layoutManager;

    String idusuariosregistrado;
//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------

    private DatabaseReference DBOtrosCursosCurriculos;

    String detalleotroscursos = "";

    String idusuarioregistrado;


    String IDOtrosEstudiosss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_c_pantalla_otros_cursos );

        TextView titulootrosCursos = findViewById( R.id.xmlTituloReferencia );
        Typeface face = Typeface.createFromAsset( getAssets(), "fonts/robotoslab.bold.ttf" );
        titulootrosCursos.setTypeface( face );




        DBOtrosCursosCurriculos = FirebaseDatabase.getInstance().getReference( "Otros_Cursos" );

//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------

        database = FirebaseDatabase.getInstance();
        otrosestudiosinset = database.getReference( "Otros_Cursos" );

        recycler_otrosestudios = (RecyclerView) findViewById( R.id.recyclerviewotroscursos );
        recycler_otrosestudios.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recycler_otrosestudios.setLayoutManager( layoutManager );

//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------

        etInstitucion = (EditText) findViewById( R.id.xmlEditNombreInstitucionOC );
        etAno = (EditText) findViewById( R.id.xmlEditAñoOC );
        etAreaoTema = (EditText) findViewById( R.id.xmlEditAreaOC );
        ssTipodeEstudio = (SearchableSpinner) findViewById( R.id.xmlspinTipoEstudioOC );

        BtnRegistrarOtrosCursos = (Button) findViewById( R.id.xmlbtnGuardarCursosOC );
        BtnRegistrarOtrosCursos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarotroscurriculos(detalleotroscursos);
            }
        } );

        btnAcualizarOtrosCursos = (Button)findViewById( R.id.xmlbtnActualizarCursosOC );
        btnAcualizarOtrosCursos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActualizarOtrosEstudios(IDOtrosEstudiosss);

            }
        } );

        if (getIntent() != null)
            detalleotroscursos = getIntent().getStringExtra( "DetalleOtrosCursosID" );

        if (!detalleotroscursos.isEmpty()) {
           // registrarotroscurriculos( detalleotroscursos );
        }

//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------

        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        idusuariosregistrado = Ukey;

        loadOtrosEstudios(Ukey);
//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------
    }

//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------
    private void loadOtrosEstudios(String Ukey) {
         adapter = new FirebaseRecyclerAdapter<OtrosCursos, OtrosEstudiosViewHolder>( OtrosCursos.class, R.layout.card_view_otros_estudios_en_los_insert, OtrosEstudiosViewHolder.class,
                otrosestudiosinset.orderByChild( "sIdBuscadorEmpleoOtrosCursos" ).equalTo( Ukey )) {
            @Override
            protected void populateViewHolder(OtrosEstudiosViewHolder ViewHolder, OtrosCursos model, int position) {

                ViewHolder.txtInstitucion.setText( model.getsInstitucionOtrosCursos() );
                ViewHolder.txtAno.setText( model.getsAnoOtrosCursos() );
                ViewHolder.txtAreaoTema.setText( model.getsAreaoTemaOtrosCursos() );
                ViewHolder.txtTipoEstudio.setText( model.getsTipoEstudioOtrosCursos() );

             //   Log.d( "hola", String.valueOf( model.getsInstitucionOtrosCursos() ) );

                final OtrosCursos clickItem = model;
                ViewHolder.setItemClickListener( new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        IDOtrosEstudiosss = adapter.getRef( position ).getKey();
                        goActualizarOtrosCursos(IDOtrosEstudiosss);

                    }
                } );
            }
        };
        recycler_otrosestudios.setAdapter( adapter );
    }

//---------------------codigo de la vista de otros estudios en el insert----------------------------------------------------------------------------------------------------------------------

    public void limpiarCampor() {
        etInstitucion.setText( "" );
        etAreaoTema.setText( "" );
        etAno.setText( "" );

    }

    private void registrarotroscurriculos(String detalleotroscursos) {
        ocInstitucionC = etInstitucion.getText().toString().trim();
        ocAnoC = etAno.getText().toString().trim();
        ocAreaoTemaC = etAreaoTema.getText().toString().trim();
        ocTipoEstudio = ssTipodeEstudio.getSelectedItem().toString().trim();
        ocIdBuscardor = detalleotroscursos;

        if (TextUtils.isEmpty( ocInstitucionC )) {
            etInstitucion.setError( "Campo vacío, por favor escriba la institucion" );
            return;
        }

        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        idusuarioregistrado = Ukey;

        String IdCurriculo = DBOtrosCursosCurriculos.push().getKey();

        OtrosCursos otrosCursos = new OtrosCursos( IdCurriculo, ocIdBuscardor, idusuarioregistrado, ocInstitucionC, ocAnoC, ocAreaoTemaC, ocTipoEstudio );

        DBOtrosCursosCurriculos.child( IdCurriculo ).setValue( otrosCursos );

        limpiarCampor();

    }

    private void goActualizarOtrosCursos(String idOtrosEstudiosss) {
        otrosestudiosinset.child( idOtrosEstudiosss ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OtrosCursos otrosCursos = dataSnapshot.getValue(OtrosCursos.class);

                etInstitucion.setText( otrosCursos.getsInstitucionOtrosCursos() );
                etAno.setText( otrosCursos.getsAnoOtrosCursos() );
                etAreaoTema.setText( otrosCursos.getsAreaoTemaOtrosCursos() );
                ssTipodeEstudio.setSelection( obtenerPosicionItem( ssTipodeEstudio, otrosCursos.getsTipoEstudioOtrosCursos() ) );

            }

            public int obtenerPosicionItem(Spinner spinner, String fruta) {
                //Creamos la variable posicion y lo inicializamos en 0
                int posicion = 0;
                //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
                //que lo pasaremos posteriormente
                for (int i = 0; i < spinner.getCount(); i++) {
                    //Almacena la posición del ítem que coincida con la búsqueda
                    if (spinner.getItemAtPosition( i ).toString().equalsIgnoreCase( fruta )) {
                        posicion = i;
                    }
                }
                //Devuelve un valor entero (si encontro una coincidencia devuelve la
                // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
                return posicion;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ActualizarOtrosEstudios(String IDOtrosEstudiosss) {

        ocInstitucionC = etInstitucion.getText().toString().trim();
        ocAnoC = etAno.getText().toString().trim();
        ocAreaoTemaC = etAreaoTema.getText().toString().trim();
        ocTipoEstudio = ssTipodeEstudio.getSelectedItem().toString().trim();
        ocIdBuscardor = detalleotroscursos;

        if (TextUtils.isEmpty( ocInstitucionC )) {
            etInstitucion.setError( "Campo vacío, por favor escriba la institucion" );
            return;
        }

        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        idusuarioregistrado = Ukey;

        String IdCurriculo = IDOtrosEstudiosss;

        OtrosCursos otrosCursos = new OtrosCursos( IdCurriculo, ocIdBuscardor, idusuarioregistrado, ocInstitucionC, ocAnoC, ocAreaoTemaC, ocTipoEstudio );

        DBOtrosCursosCurriculos.child( IdCurriculo ).setValue( otrosCursos );

    }
}
