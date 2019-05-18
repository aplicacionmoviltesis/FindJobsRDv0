package com.example.findjobsrdv0.Registro_del_Curriculo;

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

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.Referencias;
import com.example.findjobsrdv0.Vista_recycler_en_los_insert.RecyclerReferencias.Modelo.modeloReferencias;
import com.example.findjobsrdv0.Vista_recycler_en_los_insert.RecyclerReferencias.ViewHolder.ReferenciasViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class cPantallaReferenciasCurriculo extends AppCompatActivity {

    EditText etNombre, etCargoOcupado, etInstitucion, etTelefono;

    String rCodigoId, rBuscadorId, rNombreC, rCargoOcupadoC, rInstitucionC, rTelefonoC;

    Button BtnRegistrarReferencia;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------
    FirebaseDatabase database;
    DatabaseReference referenciainset;

    RecyclerView recycler_referencia;
    RecyclerView.LayoutManager layoutManager;
//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_c_pantalla_referencias_curriculo );

//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------
        database = FirebaseDatabase.getInstance();
        referenciainset = database.getReference( "Referencia" );

        recycler_referencia = (RecyclerView) findViewById( R.id.recyclerViewReferenciaInsert );
        recycler_referencia.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recycler_referencia.setLayoutManager( layoutManager );
//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------


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
                RegistrarReferencia();
            }
        } );


//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------
        loadReferencias();
//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------

    }
//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------

    private void loadReferencias() {
        final FirebaseRecyclerAdapter<modeloReferencias, ReferenciasViewHolder> adapter = new FirebaseRecyclerAdapter<modeloReferencias, ReferenciasViewHolder>( modeloReferencias.class, R.layout.cardview_pantalla_referencias_curriculos_en_los_insert, ReferenciasViewHolder.class, referenciainset ) {
            @Override
            protected void populateViewHolder(ReferenciasViewHolder ViewHolder, modeloReferencias model, int position) {

                ViewHolder.txtInstitucion.setText( model.getrInstitucionC() );
                ViewHolder.txtTelefono.setText( model.getrTelefonoC() );
                ViewHolder.txtCargoOcupado.setText( model.getrCargoOcupadoC() );
                ViewHolder.txtNombre.setText( model.getrNombreC() );

                Log.d( "hola", String.valueOf( ViewHolder ) );

                final modeloReferencias clickItem = model;

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
        recycler_referencia.setAdapter( adapter );
    }
//---------------------codigo de la vista de la referencias en el insert----------------------------------------------------------------------------------------------------------------------


    public void RegistrarReferencia(){

        rNombreC = etNombre.getText().toString();
        rCargoOcupadoC = etCargoOcupado.getText().toString();
        rInstitucionC = etInstitucion.getText().toString();
        rTelefonoC = etTelefono.getText().toString();

        if (TextUtils.isEmpty( rNombreC )) {
            etNombre.setError( "Campo vacío, por favor escriba el nombre " );
            return;
        }
     /*   if (TextUtils.isEmpty( cApellido )) {
            etApellido.setError( "Campo vacío, por favor escriba el apellido" );
            return;
        }
   */
        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String rBuscardorId = Ukey;

        String IdReferencia = mDatabase.push().getKey();

        Referencias referencias = new Referencias( IdReferencia, rBuscardorId, rNombreC, rCargoOcupadoC, rInstitucionC, rTelefonoC);

        mDatabase.child( IdReferencia ).setValue( referencias );

        //DBReferenceCurriculos.child("empleos").child(IDEmpleo).setValue(empleos);
        // mDatabase.child(Ukey).push().setValue(referencias);//para registrarlo dentro del usuario que inicio sesion
    }
}
