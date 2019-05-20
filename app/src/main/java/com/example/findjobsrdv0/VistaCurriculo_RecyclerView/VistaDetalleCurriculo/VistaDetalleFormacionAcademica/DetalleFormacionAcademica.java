package com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleFormacionAcademica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleFormacionAcademica.ViewHolder.DetalleFormAcadViewHolder;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleFormacionAcademica.modelo.modeloFormacionacad;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetalleFormacionAcademica extends AppCompatActivity {

  //  TextView txtNivelPrimario, txtNivelSecundario, txtNivelSuperir, txtCarrera;


    FirebaseDatabase database;
    DatabaseReference detFormacionAcad;

    RecyclerView recycler_detalle_formacad;
    RecyclerView.LayoutManager layoutManager;

    String detalleformacad = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detalle_formacion_academica );

        database = FirebaseDatabase.getInstance();
        detFormacionAcad = database.getReference( "Formacion_Academica" );

        recycler_detalle_formacad = (RecyclerView) findViewById( R.id.recyclerViewDetalleFormAcad );
        recycler_detalle_formacad.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recycler_detalle_formacad.setLayoutManager( layoutManager );
/*
        txtNivelPrimario = (TextView) findViewById( R.id.xmlTVprimerNivelDetalleFormAcad );
        txtNivelSecundario = (TextView) findViewById( R.id.xmlTVSegundoNivelDetalleFormAcad );
        txtNivelSuperir = (TextView) findViewById( R.id.xmlTVNivelSuperiorDetalleFormAcad );
        txtCarrera = (TextView) findViewById( R.id.xmlTVCarreraDetalleFormAcad );
*/
        if (getIntent() != null)
            detalleformacad = getIntent().getStringExtra( "DetalleFormacionAcademicaID" );

        if (!detalleformacad.isEmpty()) {
            loadFormacionacad( detalleformacad );
        }

        loadFormacionacad(detalleformacad);


    }

    private void loadFormacionacad(String detalleformacad) {
        final FirebaseRecyclerAdapter<modeloFormacionacad, DetalleFormAcadViewHolder> adapter = new FirebaseRecyclerAdapter<modeloFormacionacad, DetalleFormAcadViewHolder>(modeloFormacionacad.class,
                R.layout.cardview_detalle_formacion_academica, DetalleFormAcadViewHolder.class,
                detFormacionAcad.orderByChild( "idbuscadorc" ).equalTo( detalleformacad )  ) {
            @Override
            protected void populateViewHolder(DetalleFormAcadViewHolder ViewHolder, modeloFormacionacad modelo, int position) {
                ViewHolder.txtNivelPrimario.setText( modelo.getNivelprimarioc() );
                ViewHolder.txtNivelSecundario.setText( modelo.getNivelsecundarioc() );
                ViewHolder.txtNivelSuperior.setText( modelo.getNivelsuperiorc() );
                ViewHolder.txtCarrera.setText( modelo.getCarrerac() );

                final modeloFormacionacad clickItem = modelo;

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

        recycler_detalle_formacad.setAdapter( adapter );
    }



}