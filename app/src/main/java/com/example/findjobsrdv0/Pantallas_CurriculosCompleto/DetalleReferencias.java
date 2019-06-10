package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Referencias;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.ViewHolders_CurriculosCompleto.DetalleReferenciaViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetalleReferencias extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference detReferencia;

    RecyclerView recycler_detalle_referencia;
    RecyclerView.LayoutManager layoutManager;

    String detallereferencia = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detalle_referencias );

        database = FirebaseDatabase.getInstance();
        detReferencia = database.getReference( "Referencia" );

        recycler_detalle_referencia = (RecyclerView) findViewById( R.id.recyclerViewDetalleFormAcad );
        recycler_detalle_referencia.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recycler_detalle_referencia.setLayoutManager( layoutManager );

        if (getIntent() != null)
            detallereferencia = getIntent().getStringExtra( "DetalleReferenciaID" );

        if (!detallereferencia.isEmpty()) {
            loadReferencias( detallereferencia );
        }

        loadReferencias(detallereferencia);

    }

    private void loadReferencias(String detallereferencia) {
        final FirebaseRecyclerAdapter<Referencias, DetalleReferenciaViewHolder> adapter = new FirebaseRecyclerAdapter<Referencias, DetalleReferenciaViewHolder>(Referencias.class,
                R.layout.cardview_detalle_referencia, DetalleReferenciaViewHolder.class,
                detReferencia.orderByChild( "sIdCurriculorRef" ).equalTo( detallereferencia ) ) {
            @Override
            protected void populateViewHolder(DetalleReferenciaViewHolder ViewHolder, Referencias modelo, int position) {

                ViewHolder.txtNombre.setText( modelo.getsNombrePersonaRef() );
                ViewHolder.txtCargoOcupado.setText( modelo.getsCargoOcupadoRef() );
                ViewHolder.txtInstitucion.setText( modelo.getsInstitucionRef() );
                ViewHolder.txtTelefono.setText( modelo.getsTelefonoRef() );


                final Referencias clickItem = modelo;

                ViewHolder.setItemClickListener( new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        //       Toast.makeText( cPantallaRegistroCurriculo.this, "" + clickItem.getsInstitucionOtrosCursos(), Toast.LENGTH_SHORT ).show();

                      /*  Intent CurriculoDetalle = new Intent( VistaCurriculo.this, DetalleCurriculo.class );
                        CurriculoDetalle.putExtra( "detallecurrID", adapter.getRef( position ).getKey() );
                        startActivity( CurriculoDetalle );
*/
                        //  Log.d("klk id",adapter.getRef( position ).getKey());

                        // Toast.makeText( PantalaVistaCurriculo.this, ""+clickItem.getsNombreC(), Toast.LENGTH_SHORT ).show();
                    }
                } );


            }
        };

        recycler_detalle_referencia.setAdapter( adapter );

    }
}
