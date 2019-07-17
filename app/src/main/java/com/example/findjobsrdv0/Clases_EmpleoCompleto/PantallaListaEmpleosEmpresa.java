package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PantallaListaEmpleosEmpresa extends AppCompatActivity {

    private RecyclerView listaEmpleosEmpresa;
    private DatabaseReference DBlistaEmpleos;
    private FirebaseDatabase databaseEmpleosEmpresa;
    FirebaseRecyclerAdapter<Empleos, EmpleosViewHolder> adapterEmpleosEmpresa;

    private String sIdEmpresa= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_lista_empleos_empresa);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        databaseEmpleosEmpresa = FirebaseDatabase.getInstance();
        DBlistaEmpleos = databaseEmpleosEmpresa.getReference("Empleos");
        DBlistaEmpleos.keepSynced(true);


        listaEmpleosEmpresa = (RecyclerView) findViewById(R.id.ListaEmpleosEmpresaR);
        listaEmpleosEmpresa.setHasFixedSize(true);
        listaEmpleosEmpresa.setLayoutManager(new LinearLayoutManager(this));

        //sIdEmpresa = "HmAtSRSnxdfxb0Z1kM2qoW1OvNo1";

        if(getIntent() != null){
            sIdEmpresa = getIntent().getStringExtra("sEmpresaId");
            if(!sIdEmpresa.isEmpty()){

                cargarEmpleosEmpresa(sIdEmpresa);
            }
        }

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void cargarEmpleosEmpresa(String EmpresaId) {

        adapterEmpleosEmpresa = new FirebaseRecyclerAdapter<Empleos, EmpleosViewHolder>
                (Empleos.class, R.layout.cardview_mostrar_empleo, EmpleosViewHolder.class,
                        DBlistaEmpleos.orderByChild("sIdEmpleadorE").equalTo(EmpresaId)) {
            @Override
            protected void populateViewHolder(EmpleosViewHolder empleosViewHolder, Empleos empleos, int i) {

                empleosViewHolder.NombreEmpleoCardView.setText(empleos.getsNombreEmpleoE());
                empleosViewHolder.NombreEmpresaCardView.setText(empleos.getsNombreEmpresaE());
                empleosViewHolder.ProvinciaCardView.setText(empleos.getsProvinciaE());
                empleosViewHolder.AreaCardView.setText(empleos.getsAreaE());
                empleosViewHolder.EstadoCardView.setText(empleos.getsEstadoEmpleoE());
                empleosViewHolder.FechaPublicacionCardView.setText("Ultima Actualizacion: " + empleos.getsFechaPublicacionE());

                Picasso.get().load(empleos.getsImagenEmpleoE()).into(empleosViewHolder.imagenEmpleoCardView);

                final Empleos clickItem = empleos;
                empleosViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent intent = new Intent(PantallaListaEmpleosEmpresa.this, PantallaDetallesEmpleo.class);
                        intent.putExtra("sEmpleoIdBuscado", adapterEmpleosEmpresa.getRef(position).getKey());
                        startActivity(intent);


                    }
                });
            }
        };
        listaEmpleosEmpresa.setAdapter(adapterEmpleosEmpresa);
    }
}
