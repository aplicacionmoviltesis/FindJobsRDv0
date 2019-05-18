package com.example.findjobsrdv0.VistaCurriculo_RecyclerView.Vista_Curriculo_Principal.Vista_Curriculo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.DetalleCurriculo;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.Vista_Curriculo_Principal.Modelo.VistaCurriculomodel;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.Vista_Curriculo_Principal.ViewHolder.VistaCurriculoViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VistaCurriculo extends AppCompatActivity {


    RecyclerView recycler_curriculo;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference vistaCurriculo;

    // FirebaseAuth mAuth;

    FirebaseRecyclerAdapter<VistaCurriculomodel, VistaCurriculoViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vista_curriculo );


        database = FirebaseDatabase.getInstance();
        vistaCurriculo = database.getReference( "Curriculos" );

        // mAuth = FirebaseAuth.getInstance();
        recycler_curriculo = (RecyclerView) findViewById( R.id.recyclerViewVistaCurriculo );
        recycler_curriculo.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recycler_curriculo.setLayoutManager( layoutManager );

        loadCurriculo();
    }

    private void loadCurriculo() {
        adapter = new FirebaseRecyclerAdapter<VistaCurriculomodel, VistaCurriculoViewHolder>( VistaCurriculomodel.class, R.layout.cardview_vista_curriculo, VistaCurriculoViewHolder.class, vistaCurriculo ) {
            @Override
            protected void populateViewHolder(VistaCurriculoViewHolder viewHolder, VistaCurriculomodel model, int position) {

                viewHolder.txtNombre.setText( model.getNombre() );
                viewHolder.txtCedula.setText( model.getCedula() );
                viewHolder.txtDireccion.setText( model.getDireccion() );
                viewHolder.txtEstadoActual.setText( model.getEstadoactual() );
                viewHolder.txtProvincia.setText( model.getProvincia() );
                viewHolder.txtGradoMayor.setText( model.getGradomayor() );

                Log.d( "hola", String.valueOf( viewHolder ) );


                final VistaCurriculomodel clickItem = model;
                viewHolder.setItemClickListener( new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent CurriculoDetalle = new Intent( VistaCurriculo.this, DetalleCurriculo.class );
                        CurriculoDetalle.putExtra( "detallecurrID", adapter.getRef( position ).getKey() );
                        startActivity( CurriculoDetalle );

                        //  Log.d("klk id",adapter.getRef( position ).getKey());

                        // Toast.makeText( PantalaVistaCurriculo.this, ""+clickItem.getNombre(), Toast.LENGTH_SHORT ).show();
                    }
                } );
            }
        };

        recycler_curriculo.setAdapter( adapter );
    }
}
