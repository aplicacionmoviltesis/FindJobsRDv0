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
import com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.ExperienciaLaboral;
import com.example.findjobsrdv0.Vista_recycler_en_los_insert.RecyclerExperienciaLaboral.Modelo.modeloExperienciaLaboral;
import com.example.findjobsrdv0.Vista_recycler_en_los_insert.RecyclerExperienciaLaboral.ViewHolder.ExperienciaLaboralViewHolder;
import com.example.findjobsrdv0.Vista_recycler_en_los_insert.RecyclerReferencias.Modelo.modeloReferencias;
import com.example.findjobsrdv0.Vista_recycler_en_los_insert.RecyclerReferencias.ViewHolder.ReferenciasViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class cPantallaExperienciaLaboralCurriculo extends AppCompatActivity {
    private TextView TituloExpLab;

    EditText etNombreEmpresa, etCargoOcupado, etTelefono, etFechaEntrada, etFechaSalida;

    String elNombreEmpresa, elCargoOcupado, elTelefono, elFechaEntrada, elFechaSalida, elBuscardorId;

    Button BtnRegistrarExperienciaLab;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    //---------------------codigo de la vista de la experiencia laboral en el insert----------------------------------------------------------------------------------------------------------------------
    FirebaseDatabase database;
    DatabaseReference experiencialaboralinset;

    RecyclerView recycler_experiencialaboral;
    RecyclerView.LayoutManager layoutManager;
//---------------------codigo de la vista de la experiencia laboral en el insert----------------------------------------------------------------------------------------------------------------------


    String detalleexperiencialab = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_c_pantalla_experiencia_laboral_curriculo );

        TituloExpLab = (TextView) findViewById( R.id.xmlTituloExperienciaLaboral );
        Typeface face = Typeface.createFromAsset( getAssets(), "fonts/Chomsky.otf" );
        TituloExpLab.setTypeface( face );

//---------------------codigo de la vista de la experiencia laboral en el insert----------------------------------------------------------------------------------------------------------------------
        database = FirebaseDatabase.getInstance();
        experiencialaboralinset = database.getReference( "Experiencia_Laboral" );

        recycler_experiencialaboral = (RecyclerView) findViewById( R.id.recyclerviewExperienciaLaboral );
        recycler_experiencialaboral.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recycler_experiencialaboral.setLayoutManager( layoutManager );
//---------------------codigo de la vista de la experiencia laboral en el insert----------------------------------------------------------------------------------------------------------------------



        mDatabase = FirebaseDatabase.getInstance().getReference( "Experiencia_Laboral" );
        mAuth = FirebaseAuth.getInstance();

        etNombreEmpresa = (EditText) findViewById( R.id.xmlbeditRegistrarEmpresaEL );
        etCargoOcupado = (EditText) findViewById( R.id.xmlbeditRegistrarCargoEL );
        etTelefono = (EditText) findViewById( R.id.xmlbeditRegistrarTelefonoEL );
        etFechaEntrada = (EditText) findViewById( R.id.xmlbeditRegistrarFechainicioEL );
        etFechaSalida = (EditText) findViewById( R.id.xmlbeditRegistrarFechafinEL );




        BtnRegistrarExperienciaLab = (Button) findViewById( R.id.xmlBtnRegitrarExperienciaLab );
        BtnRegistrarExperienciaLab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarExperienciaLaboral(detalleexperiencialab);
            }
        } );

        if (getIntent() != null)
            detalleexperiencialab = getIntent().getStringExtra( "DetalleExperienciaLaboralID" );

        if (!detalleexperiencialab.isEmpty()) {
            RegistrarExperienciaLaboral( detalleexperiencialab );
        }






//---------------------codigo de la vista de la experiencia laboral en el insert----------------------------------------------------------------------------------------------------------------------
        loadReferencias(detalleexperiencialab);
//---------------------codigo de la vista de la experiencia laboral en el insert----------------------------------------------------------------------------------------------------------------------

    }
//---------------------codigo de la vista de la experiencia laboral en el insert----------------------------------------------------------------------------------------------------------------------

    private void loadReferencias(String detalleexperiencialab) {
        final FirebaseRecyclerAdapter<modeloExperienciaLaboral, ExperienciaLaboralViewHolder> adapter = new FirebaseRecyclerAdapter<modeloExperienciaLaboral, ExperienciaLaboralViewHolder>( modeloExperienciaLaboral.class, R.layout.cardview_experienci_laboral_insert, ExperienciaLaboralViewHolder.class,
                experiencialaboralinset.orderByChild( "elBuscadorId" ).equalTo( detalleexperiencialab ) ) {
            @Override
            protected void populateViewHolder(ExperienciaLaboralViewHolder ViewHolder, modeloExperienciaLaboral model, int position) {

                ViewHolder.txtNombreEmpresa.setText( model.getElNombreEmpresa() );
                ViewHolder.txtCargoOcupado.setText( model.getElCargoOcupado() );
                ViewHolder.txtTelefono.setText( model.getElTelefono() );
                ViewHolder.txtFechaEntrada.setText( model.getElFechaEntrada() );
                ViewHolder.txtFechaSalida.setText( model.getElFechaSalida() );

                Log.d( "hola", String.valueOf( ViewHolder ) );

                final modeloExperienciaLaboral clickItem = model;

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
        recycler_experiencialaboral.setAdapter( adapter );
    }

        public void RegistrarExperienciaLaboral(String detalleexperiencialab){


            elNombreEmpresa = etNombreEmpresa.getText().toString();
            elCargoOcupado = etCargoOcupado.getText().toString();
            elFechaEntrada = etFechaEntrada.getText().toString();
            elFechaSalida = etFechaSalida.getText().toString();
            elBuscardorId = detalleexperiencialab;


            if (TextUtils.isEmpty( elNombreEmpresa )) {
                etNombreEmpresa.setError( "Campo vacío, por favor escriba el nombre " );
                return;
            }
     /*   if (TextUtils.isEmpty( cApellido )) {
            etApellido.setError( "Campo vacío, por favor escriba el apellido" );
            return;
        }
   */

            String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();



            String IdExperienciaLab = mDatabase.push().getKey();

            ExperienciaLaboral experienciaLaboral = new ExperienciaLaboral( IdExperienciaLab, elBuscardorId, elNombreEmpresa, elCargoOcupado, elTelefono, elFechaEntrada, elFechaSalida);


            mDatabase.child( IdExperienciaLab ).setValue( experienciaLaboral );

            //DBReferenceCurriculos.child("empleos").child(IDEmpleo).setValue(empleos);
            //mDatabase.child(Ukey).push().setValue(experienciaLaboral);//para registrarlo dentro del usuario que inicio sesion



        }


}
