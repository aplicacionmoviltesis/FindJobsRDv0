package com.example.findjobsrdv0.Administradores;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.GeneralesApp.Uni_Prov_Area_ViewHolder;
import com.example.findjobsrdv0.GeneralesApp.Universidades;
import com.example.findjobsrdv0.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PantallaListaUniversidades extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference DBUniversidades;
    private RecyclerView listaUniversidades;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerAdapter<Universidades, Uni_Prov_Area_ViewHolder> adapterUniversidades;

    private String Ukey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_lista_universidades);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        database = FirebaseDatabase.getInstance();
        DBUniversidades= database.getReference("Universidades");
        DBUniversidades.keepSynced(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        listaUniversidades = (RecyclerView)findViewById(R.id.ListaUniversidadesR);
        listaUniversidades.setHasFixedSize(true);
        listaUniversidades.setLayoutManager(new LinearLayoutManager(this));


        LoadUniversidades();
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void LoadUniversidades() {
        adapterUniversidades = new FirebaseRecyclerAdapter<Universidades, Uni_Prov_Area_ViewHolder>
                (Universidades.class,R.layout.cardview_area_provincia_universidad, Uni_Prov_Area_ViewHolder.class,
                        DBUniversidades) {
            @Override
            protected void populateViewHolder(Uni_Prov_Area_ViewHolder uniProvAreaViewHolder, Universidades universidades, int i) {
                uniProvAreaViewHolder.Nombre_Uni_Prov_Area_CardView.setText(universidades.getsNombreUniversidad());
                uniProvAreaViewHolder.OtroDato_Uni_Prov_Area_CardView.setText(universidades.getsUbicacionUniversidad());

                Picasso.get().load(universidades.getsImagenUniversidad()).into(uniProvAreaViewHolder.imagen_Uni_Prov_Area_CardView);

                final Universidades clickItem = universidades;
                uniProvAreaViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(PantallaListaUniversidades.this, PantallaActualizarUniversidad.class);
                        intent.putExtra("sUniversidadId",adapterUniversidades.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        listaUniversidades.setAdapter(adapterUniversidades);
    }
}
