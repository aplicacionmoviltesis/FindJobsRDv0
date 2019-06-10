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
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Referencias;
import com.example.findjobsrdv0.ViewHolders_CurriculosCompleto.ReferenciasViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class cPantallaReferenciasCurriculo extends AppCompatActivity {

    EditText etNombre, etCargoOcupado, etInstitucion, etTelefono;

    String rCodigoId, rBuscadorId, rNombreC, rCargoOcupadoC, rInstitucionC, rTelefonoC;

    Button BtnRegistrarReferencia, btnActualizarReferencia, btnBorrarReferencia;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    FirebaseRecyclerAdapter<Referencias, ReferenciasViewHolder> adapter;

//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------
    FirebaseDatabase database;
    DatabaseReference referenciainset;

    RecyclerView recycler_referencia;
    RecyclerView.LayoutManager layoutManager;

    String detallereferencias = "";

    String idusuarioconectado;

    String IdReferenciasss;


//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------

//---------------------codigo para actualizar los datos----------------------------------------------------------------------------------------------------------------------


//---------------------codigo para actualizar los datos----------------------------------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_c_pantalla_referencias_curriculo );

        TextView titulo = findViewById( R.id.xmlTituloReferencia );
        Typeface face = Typeface.createFromAsset( getAssets(), "fonts/robotoslab.bold.ttf" );
        titulo.setTypeface( face );

//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------
        database = FirebaseDatabase.getInstance();
        referenciainset = database.getReference( "Referencia" );

        recycler_referencia = (RecyclerView) findViewById( R.id.recyclerViewReferenciaInsert );
        recycler_referencia.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recycler_referencia.setLayoutManager( layoutManager );
//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------

//---------------------codigo para actualizar los datos----------------------------------------------------------------------------------------------------------------------



        mDatabase = FirebaseDatabase.getInstance().getReference("Referencia");
        mAuth = FirebaseAuth.getInstance();

        etNombre = (EditText)findViewById( R.id.xmlbeditRegistrarEmpresaEL );
        etCargoOcupado = (EditText)findViewById( R.id.xmlbeditRegistrarCargoEL );
        etInstitucion = (EditText)findViewById( R.id.xmlbeditRegistrarinstitucionEL );
        etTelefono = (EditText)findViewById( R.id.xmlbeditRegistrarTelefonoEL );

        BtnRegistrarReferencia = (Button)findViewById( R.id.xmlBtnregistrarReferencia );
        BtnRegistrarReferencia.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarReferencia(detallereferencias);
            }
        } );

        btnActualizarReferencia = (Button) findViewById( R.id.xmlBtnAtualizarReferencia );
        btnActualizarReferencia.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActualizarReferencias( IdReferenciasss);
            }
        } );

      /*  btnBorrarReferencia = (Button)findViewById( R.id.xmlBtnBorrarReferencia );
        btnBorrarReferencia.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BorrarReferencia(IdReferenciasss);
            }
        } );*/



//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------

        if (getIntent() != null)
            detallereferencias = getIntent().getStringExtra( "DetalleReferenciasID" );

        if (!detallereferencias.isEmpty()) {
            RegistrarReferencia( detallereferencias );
        }


        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        idusuarioconectado = Ukey;

        loadReferencias(Ukey);
//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------

    }




//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------

    private void loadReferencias(String Ukey) {
         adapter = new FirebaseRecyclerAdapter<Referencias, ReferenciasViewHolder>( Referencias.class, R.layout.cardview_pantalla_referencias_curriculos_en_los_insert, ReferenciasViewHolder.class,
                referenciainset.orderByChild( "sBuscadorEmpleoRef" ).equalTo( Ukey ) ) {
            @Override
            protected void populateViewHolder(ReferenciasViewHolder ViewHolder, Referencias model, int position) {

                ViewHolder.txtInstitucion.setText( model.getsInstitucionRef() );
                ViewHolder.txtTelefono.setText( model.getsTelefonoRef() );
                ViewHolder.txtCargoOcupado.setText( model.getsCargoOcupadoRef() );
                ViewHolder.txtNombre.setText( model.getsNombrePersonaRef() );

                Log.d( "hola", String.valueOf( ViewHolder ) );

                final Referencias clickItem = model;

                ViewHolder.setItemClickListener( new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        IdReferenciasss = adapter.getRef( position ).getKey();
                        goActualizarReferencia (IdReferenciasss);


                    }
                } );
            }
        };
        recycler_referencia.setAdapter( adapter );
    }

    private void goActualizarReferencia(String Referencia) {
        referenciainset.child(Referencia).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Referencias referencias= dataSnapshot.getValue(Referencias.class);

                etNombre.setText( referencias.getsNombrePersonaRef() );
                etCargoOcupado.setText( referencias.getsCargoOcupadoRef() );
                etInstitucion.setText( referencias.getsInstitucionRef() );
                etTelefono.setText( referencias.getsTelefonoRef() );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------


    public void limpiarCampor() {
        etInstitucion.setText( "" );
        etCargoOcupado.setText( "" );
        etNombre.setText( "" );
        etTelefono.setText( "" );


    }

    public void RegistrarReferencia(String detallereferencias){

        rNombreC = etNombre.getText().toString();
        rCargoOcupadoC = etCargoOcupado.getText().toString();
        rInstitucionC = etInstitucion.getText().toString();
        rTelefonoC = etTelefono.getText().toString();
        rBuscadorId = detallereferencias;

        if (TextUtils.isEmpty( rNombreC )) {
            etNombre.setError( "Campo vacío, por favor escriba el nombre " );
            return;
        }
     /*   if (TextUtils.isEmpty( cApellido )) {
            etApellido.setError( "Campo vacío, por favor escriba el apellido" );
            return;
        }
   */



        String IdReferencia = mDatabase.push().getKey();

        Referencias referencias = new Referencias( IdReferencia, rBuscadorId, idusuarioconectado, rNombreC, rCargoOcupadoC, rInstitucionC, rTelefonoC);

        mDatabase.child( IdReferencia ).setValue( referencias );

        limpiarCampor();

        //DBReferenceCurriculos.child("empleos").child(IDEmpleo).setValue(empleos);
        // mDatabase.child(Ukey).push().setValue(referencias);//para registrarlo dentro del usuario que inicio sesion
    }


    private void ActualizarReferencias(String IdReferenciasss) {
        rNombreC = etNombre.getText().toString();
        rCargoOcupadoC = etCargoOcupado.getText().toString();
        rInstitucionC = etInstitucion.getText().toString();
        rTelefonoC = etTelefono.getText().toString();
        rBuscadorId = detallereferencias;

        if (TextUtils.isEmpty( rNombreC )) {
            etNombre.setError( "Campo vacío, por favor escriba el nombre " );
            return;
        }
     /*   if (TextUtils.isEmpty( cApellido )) {
            etApellido.setError( "Campo vacío, por favor escriba el apellido" );
            return;
        }
   */



        String IdReferencia = IdReferenciasss;

        Referencias referencias = new Referencias( IdReferencia, rBuscadorId, idusuarioconectado, rNombreC, rCargoOcupadoC, rInstitucionC, rTelefonoC);

        mDatabase.child( IdReferencia ).setValue( referencias );

        //DBReferenceCurriculos.child("empleos").child(IDEmpleo).setValue(empleos);
        // mDatabase.child(Ukey).push().setValue(referencias);//para registrarlo dentro del usuario que inicio sesion
    }

   /* private void BorrarReferencia(String idReferenciasss) {

        if (idReferenciasss!=null){

        String IdReferencia = IdReferenciasss;

        Referencias referencias = new Referencias( IdReferencia, rBuscadorId, idusuarioconectado, rNombreC, rCargoOcupadoC, rInstitucionC, rTelefonoC);

        mDatabase.child( IdReferencia ).removeValue(  );

    }}*/
}
