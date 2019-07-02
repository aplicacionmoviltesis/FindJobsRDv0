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
import com.example.findjobsrdv0.GeneralesApp.Opiniones_Sugerencias;
import com.example.findjobsrdv0.GeneralesApp.Uni_Prov_Area_ViewHolder;
import com.example.findjobsrdv0.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PantallaListaOpinionesSugerencias extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference DBOpiSug;
    private RecyclerView listaOpiSug;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerAdapter<Opiniones_Sugerencias, Uni_Prov_Area_ViewHolder> adapterOpiSug;

    private String Ukey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_lista_opiniones);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        database = FirebaseDatabase.getInstance();
        DBOpiSug= database.getReference("OpinionesSugerenciasApp");
        DBOpiSug.keepSynced(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Lista de Opiniones y Sugerencias");

        listaOpiSug = (RecyclerView)findViewById(R.id.ListaOpinionesSugerenciasR);
        listaOpiSug.setHasFixedSize(true);
        listaOpiSug.setLayoutManager(new LinearLayoutManager(this));


        LoadOpinionesSugerencias();
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void LoadOpinionesSugerencias() {
        adapterOpiSug = new FirebaseRecyclerAdapter<Opiniones_Sugerencias, Uni_Prov_Area_ViewHolder>
                (Opiniones_Sugerencias.class,R.layout.cardview_area_provincia_universidad, Uni_Prov_Area_ViewHolder.class,
                        DBOpiSug) {
            @Override
            protected void populateViewHolder(Uni_Prov_Area_ViewHolder uniProvAreaViewHolder, Opiniones_Sugerencias opiniones_sugerencias, int i) {
                uniProvAreaViewHolder.Nombre_Uni_Prov_Area_CardView.setText(opiniones_sugerencias.getsTituloOpiSug());
                uniProvAreaViewHolder.OtroDato_Uni_Prov_Area_CardView.setText(opiniones_sugerencias.getsDecripcionOpiSug());

                Picasso.get().load(opiniones_sugerencias.getsImagenOpiSug()).into(uniProvAreaViewHolder.imagen_Uni_Prov_Area_CardView);

                final Opiniones_Sugerencias clickItem = opiniones_sugerencias;
                uniProvAreaViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(PantallaListaOpinionesSugerencias.this, PantallaDetalleOpiniones_Sugerencias.class);
                        intent.putExtra("sOpiSugId",adapterOpiSug.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        listaOpiSug.setAdapter(adapterOpiSug);
    }
}
