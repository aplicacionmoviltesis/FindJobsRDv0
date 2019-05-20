package com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleReferencias;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleFormacionAcademica.ViewHolder.DetalleFormAcadViewHolder;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleFormacionAcademica.modelo.modeloFormacionacad;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleReferencias.ViewHolder.DetalleReferenciaViewHolder;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleReferencias.modelo.modelodetalleReferencia;
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
        final FirebaseRecyclerAdapter<modelodetalleReferencia, DetalleReferenciaViewHolder> adapter = new FirebaseRecyclerAdapter<modelodetalleReferencia, DetalleReferenciaViewHolder>(modelodetalleReferencia.class,
                R.layout.cardview_detalle_referencia, DetalleReferenciaViewHolder.class, detReferencia.orderByChild( "rBuscadorId" ).equalTo( detallereferencia ) ) {
            @Override
            protected void populateViewHolder(DetalleReferenciaViewHolder ViewHolder, modelodetalleReferencia modelo, int position) {

                ViewHolder.txtNombre.setText( modelo.getrNombreC() );
                ViewHolder.txtCargoOcupado.setText( modelo.getrCargoOcupadoC() );
                ViewHolder.txtInstitucion.setText( modelo.getrInstitucionC() );
                ViewHolder.txtTelefono.setText( modelo.getrTelefonoC() );


                final modelodetalleReferencia clickItem = modelo;

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

        recycler_detalle_referencia.setAdapter( adapter );

    }
}
