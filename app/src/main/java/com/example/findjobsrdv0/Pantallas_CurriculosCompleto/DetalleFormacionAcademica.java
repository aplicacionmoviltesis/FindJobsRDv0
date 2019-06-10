package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.FormacionAcademica;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.ViewHolders_CurriculosCompleto.DetalleFormAcadViewHolder;
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
        final FirebaseRecyclerAdapter<FormacionAcademica, DetalleFormAcadViewHolder> adapter = new FirebaseRecyclerAdapter<FormacionAcademica, DetalleFormAcadViewHolder>(FormacionAcademica.class,
                R.layout.cardview_detalle_formacion_academica, DetalleFormAcadViewHolder.class,
                detFormacionAcad.orderByChild( "sIdCurriculoFormAcad" ).equalTo( detalleformacad )  ) {
            @Override
            protected void populateViewHolder(DetalleFormAcadViewHolder ViewHolder, FormacionAcademica modelo, int position) {
                ViewHolder.txtNivelPrimario.setText( modelo.getsNivelPrimarioFormAcad() );
                ViewHolder.txtNivelSecundario.setText( modelo.getsNivelSecundarioFormAcad() );
                ViewHolder.txtNivelSuperior.setText( modelo.getsNivelSuperiorFormAcad() );
                ViewHolder.txtCarrera.setText( modelo.getsCarreraFormAcad() );

                final FormacionAcademica clickItem = modelo;

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

        recycler_detalle_formacad.setAdapter( adapter );
    }



}