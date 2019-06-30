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
import com.example.findjobsrdv0.GeneralesApp.Provincias;
import com.example.findjobsrdv0.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PantallaListaProvincias extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference DBProvincia;
    private RecyclerView listaProvincia;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerAdapter<Provincias, Uni_Prov_Area_ViewHolder> adapterProvincia;

    private String Ukey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_lista_provincias);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        database = FirebaseDatabase.getInstance();
        DBProvincia = database.getReference("Provincias");
        DBProvincia.keepSynced(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        listaProvincia = (RecyclerView) findViewById(R.id.ListaProvinciasR);
        listaProvincia.setHasFixedSize(true);
        listaProvincia.setLayoutManager(new LinearLayoutManager(this));


        LoadProvincias();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void LoadProvincias() {
        adapterProvincia = new FirebaseRecyclerAdapter<Provincias, Uni_Prov_Area_ViewHolder>
                (Provincias.class, R.layout.cardview_area_provincia_universidad, Uni_Prov_Area_ViewHolder.class,
                        DBProvincia) {
            @Override
            protected void populateViewHolder(Uni_Prov_Area_ViewHolder uniProvAreaViewHolder, Provincias provincias, int i) {
                uniProvAreaViewHolder.Nombre_Uni_Prov_Area_CardView.setText(provincias.getsNombreProvincia());
                uniProvAreaViewHolder.OtroDato_Uni_Prov_Area_CardView.setText(provincias.getsDescripcionProvincia());

                Picasso.get().load(provincias.getsImagenProvincia()).into(uniProvAreaViewHolder.imagen_Uni_Prov_Area_CardView);

                final Provincias clickItem = provincias;
                uniProvAreaViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(PantallaListaProvincias.this, PantallaActualizarProvincia.class);
                        intent.putExtra("sProvinciaId", adapterProvincia.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        listaProvincia.setAdapter(adapterProvincia);
    }
}
