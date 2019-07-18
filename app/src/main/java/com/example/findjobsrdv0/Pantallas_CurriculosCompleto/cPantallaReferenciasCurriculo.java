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
import com.example.findjobsrdv0.ViewHolders_CurriculosCompleto.DetalleReferenciaViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class cPantallaReferenciasCurriculo extends AppCompatActivity {

    private EditText etNombre, etCargoOcupado, etInstitucion, etTelefono;

    private String rBuscadorId, rNombreC, rCargoOcupadoC, rInstitucionC, rTelefonoC;

    private Button BtnRegistrarReferencia, btnActualizarReferencia;

    private DatabaseReference mDatabase;

    FirebaseRecyclerAdapter<Referencias, DetalleReferenciaViewHolder> adapter;

    private RecyclerView recycler_referencia;
    private RecyclerView.LayoutManager layoutManager;

    private String detallereferencias = "";

    private String IdReferenciasss;

    private DatabaseReference databaseReferenceCurriloAct;
    private FirebaseDatabase databaseCurriculoAct;

    private String id;

    private String Ukey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_c_pantalla_referencias_curriculo );

        TextView titulo = findViewById( R.id.xmlTituloReferencia );
        Typeface face = Typeface.createFromAsset( getAssets(), "fonts/robotoslab.bold.ttf" );
        titulo.setTypeface( face );

        recycler_referencia = (RecyclerView) findViewById( R.id.recyclerViewReferenciaInsert );
        recycler_referencia.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recycler_referencia.setLayoutManager( layoutManager );

        Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseCurriculoAct = FirebaseDatabase.getInstance();
        databaseReferenceCurriloAct = databaseCurriculoAct.getReference(getResources().getString(R.string.Ref_Curriculos));

        Query query = databaseReferenceCurriloAct.orderByChild( "sIdCurriculo" ).equalTo( Ukey );
        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot FavdataSnapshot : dataSnapshot.getChildren()) {
                    id = FavdataSnapshot.child( "sIdCurriculo" ).getValue( String.class );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        mDatabase = FirebaseDatabase.getInstance().getReference( "Referencia" );

        etNombre = (EditText) findViewById( R.id.xmlbeditRegistrarEmpresaEL );
        etCargoOcupado = (EditText) findViewById( R.id.xmlbeditRegistrarCargoEL );
        etInstitucion = (EditText) findViewById( R.id.xmlbeditRegistrarinstitucionEL );
        etTelefono = (EditText) findViewById( R.id.xmlbeditRegistrarTelefonoEL );

        BtnRegistrarReferencia = (Button) findViewById( R.id.xmlBtnregistrarReferencia );
        BtnRegistrarReferencia.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarReferencia( id );
            }
        } );

        btnActualizarReferencia = (Button) findViewById( R.id.xmlBtnAtualizarReferencia );
        btnActualizarReferencia.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActualizarReferencias( IdReferenciasss, id );
            }
        } );

//        if (getIntent() != null)
//            detallereferencias = getIntent().getStringExtra( "DetalleReferenciasID" );
//
//        if (!detallereferencias.isEmpty()) {
//            RegistrarReferencia( detallereferencias );
//        }

        loadReferencias( Ukey );
    }

    private void loadReferencias(String Ukey) {
        adapter = new FirebaseRecyclerAdapter<Referencias, DetalleReferenciaViewHolder>( Referencias.class, R.layout.cardview_detalle_referencia, DetalleReferenciaViewHolder.class,
                mDatabase.orderByChild( "sIdCurriculorRef" ).equalTo( Ukey ) ) {
            @Override
            protected void populateViewHolder(DetalleReferenciaViewHolder ViewHolder, Referencias model, int position) {
                ViewHolder.txtInstitucion.setText( model.getsInstitucionRef() );
                ViewHolder.txtTelefono.setText( model.getsTelefonoRef() );
                ViewHolder.txtCargoOcupado.setText( model.getsCargoOcupadoRef() );
                ViewHolder.txtNombre.setText( model.getsNombrePersonaRef() );

                final Referencias clickItem = model;

                ViewHolder.setItemClickListener( new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        IdReferenciasss = adapter.getRef( position ).getKey();
                        goActualizarReferencia( IdReferenciasss );
                    }
                } );
            }
        };
        recycler_referencia.setAdapter( adapter );
    }

    private void goActualizarReferencia(String Referencia) {
        mDatabase.child( Referencia ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Referencias referencias = dataSnapshot.getValue( Referencias.class );

                etNombre.setText( referencias.getsNombrePersonaRef() );
                etCargoOcupado.setText( referencias.getsCargoOcupadoRef() );
                etInstitucion.setText( referencias.getsInstitucionRef() );
                etTelefono.setText( referencias.getsTelefonoRef() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    public void limpiarCampor() {
        etInstitucion.setText( "" );
        etCargoOcupado.setText( "" );
        etNombre.setText( "" );
        etTelefono.setText( "" );
    }

    public void RegistrarReferencia(String id) {

        rNombreC = etNombre.getText().toString();
        rCargoOcupadoC = etCargoOcupado.getText().toString();
        rInstitucionC = etInstitucion.getText().toString();
        rTelefonoC = etTelefono.getText().toString();
        rBuscadorId = id;

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

        Referencias referencias = new Referencias( IdReferencia, Ukey, rNombreC, rCargoOcupadoC, rInstitucionC, rTelefonoC );
        mDatabase.child( IdReferencia ).setValue( referencias );
        limpiarCampor();
    }

    private void ActualizarReferencias(String IdReferenciasss, String id) {
        rNombreC = etNombre.getText().toString();
        rCargoOcupadoC = etCargoOcupado.getText().toString();
        rInstitucionC = etInstitucion.getText().toString();
        rTelefonoC = etTelefono.getText().toString();
        rBuscadorId = id;

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

        Referencias referencias = new Referencias( IdReferencia, Ukey, rNombreC, rCargoOcupadoC, rInstitucionC, rTelefonoC );
        mDatabase.child( IdReferencia ).setValue( referencias );
    }
}