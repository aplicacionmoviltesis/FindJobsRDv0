package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.ExperienciaLaboral;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.ViewHolders_CurriculosCompleto.DetalleExperienciaLaboralViewHolder;
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
        final FirebaseRecyclerAdapter<ExperienciaLaboral, DetalleExperienciaLaboralViewHolder> adapter = new FirebaseRecyclerAdapter<ExperienciaLaboral, DetalleExperienciaLaboralViewHolder>(ExperienciaLaboral.class,
                R.layout.cardview_detalle_experiencia_laboral, DetalleExperienciaLaboralViewHolder.class,
                detExperienciaLab.orderByChild( "sIdCurriculoExpLab" ).equalTo( detalleexperiencialaboral )  ) {
            @Override
            protected void populateViewHolder(DetalleExperienciaLaboralViewHolder ViewHolder, ExperienciaLaboral modelo, int position) {
                ViewHolder.txtNombreempres.setText( modelo.getsNombreEmpresaExpLab() );
                ViewHolder.txtCargoocupado.setText( modelo.getsCargoOcupadoExpLab() );
                ViewHolder.txtTelefono.setText( modelo.getsTelefonoExpLab() );
                ViewHolder.txtFechaEntrada.setText( modelo.getsFechaEntradaExpLab() );
                ViewHolder.txtFechaSalida.setText( modelo.getsFechaSalidaExpLab() );

                final ExperienciaLaboral clickItem = modelo;

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

        recycler_detalle_experiencialab.setAdapter( adapter );
    }
}
