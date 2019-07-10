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
import android.widget.TextView;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.ExperienciaLaboral;
import com.example.findjobsrdv0.ViewHolders_CurriculosCompleto.DetalleExperienciaLaboralViewHolder;
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

    FirebaseRecyclerAdapter<ExperienciaLaboral, DetalleExperienciaLaboralViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference experiencialaboralinset;

    RecyclerView recycler_experiencialaboral;
    RecyclerView.LayoutManager layoutManager;

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

        TituloExpLab = (TextView) findViewById( R.id.xmlTituloExperienciaLaboral );
        Typeface face = Typeface.createFromAsset( getAssets(), "fonts/robotoslab.bold.ttf" );
        TituloExpLab.setTypeface( face );

        Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        usuarioconectado = Ukey;

        database = FirebaseDatabase.getInstance();
        experiencialaboralinset = database.getReference( "Experiencia_Laboral" );

        recycler_experiencialaboral = (RecyclerView) findViewById( R.id.recyclerviewExperienciaLaboral );
        recycler_experiencialaboral.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recycler_experiencialaboral.setLayoutManager( layoutManager );

        databaseCurriculoAct = FirebaseDatabase.getInstance();
        databaseReferenceCurriloAct = databaseCurriculoAct.getReference( "Curriculos" );

        Query query = databaseReferenceCurriloAct.orderByChild( "sIdCurriculo" ).equalTo( Ukey );
        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot FavdataSnapshot : dataSnapshot.getChildren()) {
//                    Log.d( "datosdatasnapsht", String.valueOf( dataSnapshot ) );

                    id = FavdataSnapshot.child( "sIdCurriculo" ).getValue( String.class );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

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
        loadExperienciaLab( Ukey  );
    }

    private void loadExperienciaLab(String Ukey ) {
        adapter = new FirebaseRecyclerAdapter<ExperienciaLaboral, DetalleExperienciaLaboralViewHolder>( ExperienciaLaboral.class, R.layout.cardview_detalle_experiencia_laboral, DetalleExperienciaLaboralViewHolder.class,
                experiencialaboralinset.orderByChild( "sIdCurriculoExpLab" ).equalTo( Ukey ) ) {
            @Override
            protected void populateViewHolder(DetalleExperienciaLaboralViewHolder ViewHolder, ExperienciaLaboral model, int position) {
                ViewHolder.txtNombreempres.setText( model.getsNombreEmpresaExpLab() );
                ViewHolder.txtCargoocupado.setText( model.getsCargoOcupadoExpLab() );
                ViewHolder.txtTelefono.setText( model.getsTelefonoExpLab() );
                ViewHolder.txtFechaEntrada.setText( model.getsFechaEntradaExpLab() );
                ViewHolder.txtFechaSalida.setText( model.getsFechaSalidaExpLab() );

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

                etNombreEmpresa.setText(experienciaLaboralklk.getsNombreEmpresaExpLab());
                etCargoOcupado.setText(experienciaLaboralklk.getsCargoOcupadoExpLab());
                etTelefono.setText( experienciaLaboralklk.getsTelefonoExpLab());
                etFechaEntrada.setText( experienciaLaboralklk.getsFechaEntradaExpLab() );
                etFechaSalida.setText( experienciaLaboralklk.getsFechaSalidaExpLab() );
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


        if (TextUtils.isEmpty( elNombreEmpresa )) {
            etNombreEmpresa.setError( "Campo vacío, por favor escriba el nombre " );
            return;
        }

        String IdExperienciaLab = mDatabase.push().getKey();

        ExperienciaLaboral experienciaLaboral = new ExperienciaLaboral
                ( IdExperienciaLab, id, elNombreEmpresa,
                        elCargoOcupado, elTelefono, elFechaEntrada, elFechaSalida );
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
                ( IdExperienciaLab, id, elNombreEmpresa,
                        elCargoOcupado, elTelefono, elFechaEntrada, elFechaSalida );
        mDatabase.child( IdExperienciaLab ).setValue( experienciaLaboral );
        limpiarCampor();
    }
}