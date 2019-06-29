package com.example.findjobsrdv0.Administradores;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.findjobsrdv0.GeneralesApp.Areas;
import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PantallaListaAreas extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference DBAreas;
    private RecyclerView listaAreas;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerAdapter<Areas, Uni_Prov_Area_ViewHolder> adapterAreas;

    private String Ukey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_lista_areas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        database = FirebaseDatabase.getInstance();
        DBAreas= database.getReference("Areas");
        DBAreas.keepSynced(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        listaAreas = (RecyclerView)findViewById(R.id.ListaAreasR);
        listaAreas.setHasFixedSize(true);
        listaAreas.setLayoutManager(new LinearLayoutManager(this));


        LoadAreas();
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void LoadAreas() {
        adapterAreas = new FirebaseRecyclerAdapter<Areas, Uni_Prov_Area_ViewHolder>
                (Areas.class,R.layout.cardview_area_provincia_universidad, Uni_Prov_Area_ViewHolder.class,
                        DBAreas) {
            @Override
            protected void populateViewHolder(Uni_Prov_Area_ViewHolder uniProvAreaViewHolder, Areas areas, int i) {
                uniProvAreaViewHolder.Nombre_Uni_Prov_Area_CardView.setText(areas.getsNombreArea());
                uniProvAreaViewHolder.OtroDato_Uni_Prov_Area_CardView.setText(areas.getsDescripcionArea());

                Picasso.get().load(areas.getsImagenArea()).into(uniProvAreaViewHolder.imagen_Uni_Prov_Area_CardView);

                final Areas clickItem = areas;
                uniProvAreaViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(PantallaListaAreas.this, PantallaActualizarArea.class);
                        intent.putExtra("sAreaId",adapterAreas.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        listaAreas.setAdapter(adapterAreas);
    }
}
