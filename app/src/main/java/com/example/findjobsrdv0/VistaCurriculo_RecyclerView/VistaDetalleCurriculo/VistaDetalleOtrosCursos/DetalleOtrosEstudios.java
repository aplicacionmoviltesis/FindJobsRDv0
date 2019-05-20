package com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleOtrosCursos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleExperienciaLaboral.ViewHolder.DetalleExperienciaLaboralViewHolder;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleExperienciaLaboral.modelo.modeloExperienciaLaboraldetalle;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleOtrosCursos.ViewHolder.DetalleOtrosEstudiosViewHolder;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleOtrosCursos.modelo.modeloOtrosEstudiosdetalle;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetalleOtrosEstudios extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference detotrosestudios;

    RecyclerView recycler_detalle_otrosestudios;
    RecyclerView.LayoutManager layoutManager;

    String detalleotrosestudios = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detalle_otros_estudios );

        database = FirebaseDatabase.getInstance();
        detotrosestudios = database.getReference( "Otros_Cursos" );

        recycler_detalle_otrosestudios = (RecyclerView) findViewById( R.id.recyclerViewOtrosEstudiosLab );
        recycler_detalle_otrosestudios.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recycler_detalle_otrosestudios.setLayoutManager( layoutManager );

        if (getIntent() != null)
            detalleotrosestudios = getIntent().getStringExtra( "DetalleOtrosEstudiosID" );

        if (!detalleotrosestudios.isEmpty()) {
            loadOtrosEstudios( detalleotrosestudios );
        }

        loadOtrosEstudios( detalleotrosestudios );
    }

    private void loadOtrosEstudios(String detalleotrosestudios) {
        final FirebaseRecyclerAdapter<modeloOtrosEstudiosdetalle, DetalleOtrosEstudiosViewHolder> adapter = new FirebaseRecyclerAdapter<modeloOtrosEstudiosdetalle, DetalleOtrosEstudiosViewHolder>( modeloOtrosEstudiosdetalle.class,
                R.layout.cardview_detalle_otros_estudios, DetalleOtrosEstudiosViewHolder.class,
                detotrosestudios.orderByChild( "idbuscador" ).equalTo( detalleotrosestudios ) ) {
            @Override
            protected void populateViewHolder(DetalleOtrosEstudiosViewHolder ViewHolder, modeloOtrosEstudiosdetalle modelo, int position) {

                ViewHolder.txtInstitucion.setText( modelo.getOcInstitucionC() );
                ViewHolder.txtAno.setText( modelo.getOcAnoC() );
                ViewHolder.txtAreaoTema.setText( modelo.getOcAreaoTemaC() );
                ViewHolder.txtTipodeEstudio.setText( modelo.getOcTipoEstudio() );

                final modeloOtrosEstudiosdetalle clickItem = modelo;

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
        recycler_detalle_otrosestudios.setAdapter( adapter );

    }
}
