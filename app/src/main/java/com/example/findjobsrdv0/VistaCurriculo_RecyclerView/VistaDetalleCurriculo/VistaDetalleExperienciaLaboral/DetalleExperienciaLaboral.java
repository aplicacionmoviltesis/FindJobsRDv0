package com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleExperienciaLaboral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleExperienciaLaboral.ViewHolder.DetalleExperienciaLaboralViewHolder;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleExperienciaLaboral.modelo.modeloExperienciaLaboraldetalle;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleFormacionAcademica.ViewHolder.DetalleFormAcadViewHolder;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleFormacionAcademica.modelo.modeloFormacionacad;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetalleExperienciaLaboral extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference detExperienciaLab;

    RecyclerView recycler_detalle_experiencialab;
    RecyclerView.LayoutManager layoutManager;

    String detalleexperiencialaboral = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detalle_experiencia_laboral );

        database = FirebaseDatabase.getInstance();
        detExperienciaLab = database.getReference( "Experiencia_Laboral" );

        recycler_detalle_experiencialab = (RecyclerView) findViewById( R.id.recyclerViewDetalleExperienciaLab );
        recycler_detalle_experiencialab.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recycler_detalle_experiencialab.setLayoutManager( layoutManager );

        if (getIntent() != null)
            detalleexperiencialaboral = getIntent().getStringExtra( "DetalleExperienciaLabID" );

        if (!detalleexperiencialaboral.isEmpty()) {
            loadFormacionacad( detalleexperiencialaboral );
        }

        loadFormacionacad(detalleexperiencialaboral);
    }

    private void loadFormacionacad(String detalleexperiencialaboral) {
        final FirebaseRecyclerAdapter<modeloExperienciaLaboraldetalle, DetalleExperienciaLaboralViewHolder> adapter = new FirebaseRecyclerAdapter<modeloExperienciaLaboraldetalle, DetalleExperienciaLaboralViewHolder>(modeloExperienciaLaboraldetalle.class,
                R.layout.cardview_detalle_experiencia_laboral, DetalleExperienciaLaboralViewHolder.class,
                detExperienciaLab.orderByChild( "elBuscadorId" ).equalTo( detalleexperiencialaboral )  ) {
            @Override
            protected void populateViewHolder(DetalleExperienciaLaboralViewHolder ViewHolder, modeloExperienciaLaboraldetalle modelo, int position) {
                ViewHolder.txtNombreempres.setText( modelo.getElNombreEmpresa() );
                ViewHolder.txtCargoocupado.setText( modelo.getElCargoOcupado() );
                ViewHolder.txtTelefono.setText( modelo.getElTelefono() );
                ViewHolder.txtFechaEntrada.setText( modelo.getElFechaEntrada() );
                ViewHolder.txtFechaSalida.setText( modelo.getElFechaSalida() );

                final modeloExperienciaLaboraldetalle clickItem = modelo;

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

        recycler_detalle_experiencialab.setAdapter( adapter );
    }
}
