package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.OtrosCursos;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.ViewHolders_CurriculosCompleto.DetalleOtrosEstudiosViewHolder;
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
        final FirebaseRecyclerAdapter<OtrosCursos, DetalleOtrosEstudiosViewHolder> adapter = new FirebaseRecyclerAdapter<OtrosCursos, DetalleOtrosEstudiosViewHolder>( OtrosCursos.class,
                R.layout.cardview_detalle_otros_estudios, DetalleOtrosEstudiosViewHolder.class,
                detotrosestudios.orderByChild( "sIdCurriculosOtrosCursos" ).equalTo( detalleotrosestudios ) ) {
            @Override
            protected void populateViewHolder(DetalleOtrosEstudiosViewHolder ViewHolder, OtrosCursos modelo, int position) {

                ViewHolder.txtInstitucion.setText( modelo.getsInstitucionOtrosCursos() );
                ViewHolder.txtAno.setText( modelo.getsAnoOtrosCursos() );
                ViewHolder.txtAreaoTema.setText( modelo.getsAreaoTemaOtrosCursos() );
                ViewHolder.txtTipodeEstudio.setText( modelo.getsTipoEstudioOtrosCursos() );

                final OtrosCursos clickItem = modelo;

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
        recycler_detalle_otrosestudios.setAdapter( adapter );

    }
}
