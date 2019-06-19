package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.ExperienciaLaboral;
import com.example.findjobsrdv0.ViewHolders_CurriculosCompleto.ExperienciaLaboralViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class cPantallaExperienciaLaboralCurriculo extends AppCompatActivity {
    private TextView TituloExpLab;

    EditText etNombreEmpresa, etCargoOcupado, etTelefono, etFechaEntrada, etFechaSalida;

    String elNombreEmpresa, elCargoOcupado, elTelefono, elFechaEntrada, elFechaSalida, elBuscardorId;

    Button BtnRegistrarExperienciaLab, btnActualizar;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    FirebaseRecyclerAdapter<ExperienciaLaboral, ExperienciaLaboralViewHolder> adapter;

    //---------------------codigo de la vista de la experiencia laboral en el insert----------------------------------------------------------------------------------------------------------------------
    FirebaseDatabase database;
    DatabaseReference experiencialaboralinset;

    RecyclerView recycler_experiencialaboral;
    RecyclerView.LayoutManager layoutManager;
//---------------------codigo de la vista de la experiencia laboral en el insert----------------------------------------------------------------------------------------------------------------------

    String Ukey;

    String usuarioconectado;

    String IDExperienciaLab;

    String detalleexperiencialab = "";


    DatabaseReference databaseReferenceCurriloAct;
    FirebaseDatabase databaseCurriculoAct;

    String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_c_pantalla_experiencia_laboral_curriculo );

       // loadReferencias( detalleexperiencialab  );

        TituloExpLab = (TextView) findViewById( R.id.xmlTituloExperienciaLaboral );
        Typeface face = Typeface.createFromAsset( getAssets(), "fonts/robotoslab.bold.ttf" );
        TituloExpLab.setTypeface( face );



        Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        usuarioconectado = Ukey;

//---------------------codigo de la vista de la experiencia laboral en el insert----------------------------------------------------------------------------------------------------------------------
        database = FirebaseDatabase.getInstance();
        experiencialaboralinset = database.getReference( "Experiencia_Laboral" );

        recycler_experiencialaboral = (RecyclerView) findViewById( R.id.recyclerviewExperienciaLaboral );
        recycler_experiencialaboral.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recycler_experiencialaboral.setLayoutManager( layoutManager );
//---------------------codigo de la vista de la experiencia laboral en el insert----------------------------------------------------------------------------------------------------------------------

        //----------------------query para obtener el id del curriculo-------------------------------------------------------------------------------------

        databaseCurriculoAct = FirebaseDatabase.getInstance();
        databaseReferenceCurriloAct = databaseCurriculoAct.getReference( "Curriculos" );

        Query query = databaseReferenceCurriloAct.orderByChild( "sIdBuscadorEmpleo" ).equalTo( Ukey );
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



        mDatabase = FirebaseDatabase.getInstance().getReference( "Experiencia_Laboral" );
        mAuth = FirebaseAuth.getInstance();

        etNombreEmpresa = (EditText) findViewById( R.id.xmlbeditRegistrarEmpresaEL );
        etCargoOcupado = (EditText) findViewById( R.id.xmlbeditRegistrarCargoEL );
        etTelefono = (EditText) findViewById( R.id.xmlbeditRegistrarTelefonoEL );
        etFechaEntrada = (EditText) findViewById( R.id.xmlbeditRegistrarFechainicioEL );
        etFechaSalida = (EditText) findViewById( R.id.xmlbeditRegistrarFechafinEL );



        btnActualizar = (Button) findViewById( R.id.xmlBtnActualizarExperienciaLab );
        btnActualizar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActualizarExperienciaLaboral(IDExperienciaLab, id);
            }
        } );

        BtnRegistrarExperienciaLab = (Button) findViewById( R.id.xmlBtnRegitrarExperienciaLab );
        BtnRegistrarExperienciaLab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarExperienciaLaboral( id );
            }
        } );

        if (getIntent() != null)
            detalleexperiencialab = getIntent().getStringExtra( "DetalleExperienciaLaboralID" );

        if (!detalleexperiencialab.isEmpty()) {
            RegistrarExperienciaLaboral( id );
        }


//---------------------codigo de la vista de la experiencia laboral en el insert----------------------------------------------------------------------------------------------------------------------
        loadReferencias( Ukey  );

//---------------------codigo de la vista de la experiencia laboral en el insert----------------------------------------------------------------------------------------------------------------------
    }
//---------------------codigo de la vista de la experiencia laboral en el insert----------------------------------------------------------------------------------------------------------------------

    private void loadReferencias(String Ukey ) {
        adapter = new FirebaseRecyclerAdapter<ExperienciaLaboral, ExperienciaLaboralViewHolder>( ExperienciaLaboral.class, R.layout.cardview_experienci_laboral_insert, ExperienciaLaboralViewHolder.class,
                experiencialaboralinset.orderByChild( "sIdBuscadorEmpleoExpLab" ).equalTo( Ukey ) ) {
            @Override
            protected void populateViewHolder(ExperienciaLaboralViewHolder ViewHolder, ExperienciaLaboral model, int position) {

                ViewHolder.txtNombreEmpresa.setText( model.getsNombreEmpresaExpLab() );
                ViewHolder.txtCargoOcupado.setText( model.getsCargoOcupadoExpLab() );
                ViewHolder.txtTelefono.setText( model.getsTelefonoExpLab() );
                ViewHolder.txtFechaEntrada.setText( model.getsFechaEntradaExpLab() );
                ViewHolder.txtFechaSalida.setText( model.getsFechaSalidaExpLab() );

                Log.d( "hola", String.valueOf( ViewHolder ) );

                final ExperienciaLaboral clickItem = model;

                ViewHolder.setItemClickListener( new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        IDExperienciaLab = adapter.getRef(position).getKey();
                        goActualizarExperiencia(IDExperienciaLab); }
                } );
            }
        };
        recycler_experiencialaboral.setAdapter( adapter );
    }

    private void goActualizarExperiencia(String ExpLab) {
        experiencialaboralinset.child(ExpLab).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ExperienciaLaboral experienciaLaboralklk= dataSnapshot.getValue(ExperienciaLaboral.class);
                //Log.d("holap", String.valueOf(empleos));

                //Picasso.get().load(empleos.getsImagenEmpleoE()).into(MostImagen);

                etNombreEmpresa.setText(experienciaLaboralklk.getsNombreEmpresaExpLab());
                etCargoOcupado.setText(experienciaLaboralklk.getsCargoOcupadoExpLab());
                etTelefono.setText( experienciaLaboralklk.getsTelefonoExpLab());
                etFechaEntrada.setText( experienciaLaboralklk.getsFechaEntradaExpLab() );
                etFechaSalida.setText( experienciaLaboralklk.getsFechaSalidaExpLab() );



//String klk= empleos.getsProvinciaE();
                //Log.d("pagina",empleos.getsPaginaWebE());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void RegistrarExperienciaLaboral(String id) {

        elNombreEmpresa = etNombreEmpresa.getText().toString();
        elCargoOcupado = etCargoOcupado.getText().toString();
        elFechaEntrada = etFechaEntrada.getText().toString();
        elFechaSalida = etFechaSalida.getText().toString();
        elTelefono = etTelefono.getText().toString();
        elBuscardorId = id;


        if (TextUtils.isEmpty( elNombreEmpresa )) {
            etNombreEmpresa.setError( "Campo vacío, por favor escriba el nombre " );
            return;
        }

        String IdExperienciaLab = mDatabase.push().getKey();

        ExperienciaLaboral experienciaLaboral = new ExperienciaLaboral
                ( IdExperienciaLab, elBuscardorId, elNombreEmpresa,
                        elCargoOcupado, elTelefono, elFechaEntrada, elFechaSalida,usuarioconectado );


        mDatabase.child( IdExperienciaLab ).setValue( experienciaLaboral );


        limpiarCampor();

    }


    public void limpiarCampor() {
        etNombreEmpresa.setText( "" );
        etCargoOcupado.setText( "" );
        etTelefono.setText( "" );
        etFechaEntrada.setText( "" );
        etFechaSalida.setText( "" );

    }


    public void ActualizarExperienciaLaboral(String IDExperienciaLab, String id) {

        elNombreEmpresa = etNombreEmpresa.getText().toString();
        elCargoOcupado = etCargoOcupado.getText().toString();
        elFechaEntrada = etFechaEntrada.getText().toString();
        elFechaSalida = etFechaSalida.getText().toString();
        elTelefono = etTelefono.getText().toString();
        elBuscardorId = id;


        if (TextUtils.isEmpty( elNombreEmpresa )) {
            etNombreEmpresa.setError( "Campo vacío, por favor escriba el nombre " );
            return;
        }
     /*   if (TextUtils.isEmpty( cApellido )) {
            etApellido.setError( "Campo vacío, por favor escriba el apellido" );
            return;
        }
   */


        String IdExperienciaLab = IDExperienciaLab;

        ExperienciaLaboral experienciaLaboral = new ExperienciaLaboral
                ( IdExperienciaLab, elBuscardorId, elNombreEmpresa,
                        elCargoOcupado, elTelefono, elFechaEntrada, elFechaSalida,usuarioconectado );


        mDatabase.child( IdExperienciaLab ).setValue( experienciaLaboral );

        //DBReferenceCurriculos.child("empleos").child(IDEmpleo).setValue(empleos);
        //mDatabase.child(Ukey).push().setValue(experienciaLaboral);//para registrarlo dentro del usuario que inicio sesion


        limpiarCampor();
    }
}
