package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PantallaListaEmpleosAnadidos extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference DBempleos;
    private RecyclerView listaEmpleosAnadidos;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerAdapter<Empleos, EmpleosViewHolder> adapter;

    private String Ukey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_lista_empleos_anadidos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        database = FirebaseDatabase.getInstance();
        DBempleos= database.getReference("empleos");
        DBempleos.keepSynced(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        listaEmpleosAnadidos = (RecyclerView)findViewById(R.id.ListaEmpleosAnadidosR);
        listaEmpleosAnadidos.setHasFixedSize(true);
        listaEmpleosAnadidos.setLayoutManager(new LinearLayoutManager(this));


        Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        cargarEmpleos(Ukey);

    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void cargarEmpleos(String Ukey) {
        adapter = new FirebaseRecyclerAdapter<Empleos, EmpleosViewHolder>
                (Empleos.class,R.layout.cardview_mostrar_empleo, EmpleosViewHolder.class,
                        DBempleos.orderByChild("sIdEmpleadorE").equalTo(Ukey)) {
            @Override
            protected void populateViewHolder(EmpleosViewHolder empleosViewHolder, Empleos empleos, int i) {
                empleosViewHolder.NombreEmpleoCardView.setText(empleos.getsNombreEmpleoE());
                empleosViewHolder.NombreEmpresaCardView.setText(empleos.getsNombreEmpresaE());
                empleosViewHolder.ProvinciaCardView.setText(empleos.getsProvinciaE());
                empleosViewHolder.AreaCardView.setText(empleos.getsAreaE());
                empleosViewHolder.EstadoCardView.setText(empleos.getsEstadoEmpleoE());
                empleosViewHolder.FechaPublicacionCardView.setText("Ultima Actualizacion: "+empleos.getsFechaPublicacionE());
                Picasso.get().load(empleos.getsImagenEmpleoE()).into(empleosViewHolder.imagenEmpleoCardView);

                final Empleos clickItem = empleos;
                empleosViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent intent = new Intent(PantallaListaEmpleosAnadidos.this, PantallaActualizarEmpleo.class);
                        intent.putExtra("sEmpleoIdAnadidos",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        listaEmpleosAnadidos.setAdapter(adapter);
    }
}
