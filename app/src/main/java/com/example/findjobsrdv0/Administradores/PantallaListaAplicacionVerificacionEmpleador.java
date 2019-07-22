package com.example.findjobsrdv0.Administradores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.findjobsrdv0.Adaptadores_Empleador.AplicarVerificacionEmpleador;
import com.example.findjobsrdv0.Adaptadores_Empleador.ViewHolderVerificacionEmpleador;
import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PantallaListaAplicacionVerificacionEmpleador extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabaseVerifEmp;
    private DatabaseReference databaseVerificacionEmp;

    private RecyclerView recyclerViewVerifEmp;
    private RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<AplicarVerificacionEmpleador, ViewHolderVerificacionEmpleador> adapterListaVerifEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pantalla_lista_aplicacion_verificacion_empleador );

        firebaseDatabaseVerifEmp = FirebaseDatabase.getInstance();
        databaseVerificacionEmp = firebaseDatabaseVerifEmp.getReference();

        recyclerViewVerifEmp = (RecyclerView) findViewById(R.id.recycler_Lista_Verif_Emp);

        recyclerViewVerifEmp.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewVerifEmp.setLayoutManager(layoutManager);

        loadsolicitudesverificacion();
    }

    private void loadsolicitudesverificacion() {
        adapterListaVerifEmp = new FirebaseRecyclerAdapter<AplicarVerificacionEmpleador, ViewHolderVerificacionEmpleador>(AplicarVerificacionEmpleador.class, R.layout.cardview_verificacion_empleador,
                ViewHolderVerificacionEmpleador.class, databaseVerificacionEmp.child( "SolicitudVerificacionEmpleador" )) {
            @Override
            protected void populateViewHolder(ViewHolderVerificacionEmpleador viewHolder, AplicarVerificacionEmpleador model, int position) {

                viewHolder.TVNombreDocum.setText( model.getsNombreDocumVerifEmp() );
                viewHolder.TVEstadoEmpleador.setText( model.getsEstado() );
                viewHolder.TVFecha.setText( model.getsFechaVerifEmp() );

                final AplicarVerificacionEmpleador clickItem = model;
                viewHolder.setItemClickListener( new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent Detalle = new Intent( PantallaListaAplicacionVerificacionEmpleador.this, PantallaDetalleAplicacionVerificacionEmpleador.class);
                        Detalle.putExtra("detalleverifID", adapterListaVerifEmp.getRef( position ).getKey());
                        startActivity(Detalle);
                    }
                } );
            }
        };
        recyclerViewVerifEmp.setAdapter(adapterListaVerifEmp);
    }
}
