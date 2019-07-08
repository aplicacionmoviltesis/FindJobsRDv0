package com.example.findjobsrdv0.Administradores;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.findjobsrdv0.Clases_EmpleoCompleto.Empleos;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.EmpleosViewHolder;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaDetallesEmpleo;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaListaEmpleosBuscados;
import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.GeneralesApp.Provincias;
import com.example.findjobsrdv0.GeneralesApp.Uni_Prov_Area_ViewHolder;
import com.example.findjobsrdv0.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menubuscar, menu);
        MenuItem item = menu.findItem(R.id.menuBuscar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menuBuscar) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void firebaseSearch(String TextSearch) {

        String query = TextSearch.toLowerCase();

        Query firebaseSearchQueryProv = DBProvincia.orderByChild("sNombreProvincia").startAt(query).endAt(query + "\uf8ff");


        adapterProvincia = new FirebaseRecyclerAdapter<Provincias, Uni_Prov_Area_ViewHolder>
                (Provincias.class, R.layout.cardview_area_provincia_universidad, Uni_Prov_Area_ViewHolder.class,
                        firebaseSearchQueryProv) {
            @Override
            protected void populateViewHolder(Uni_Prov_Area_ViewHolder uniProvAreaViewHolder, Provincias provincias, int i) {
                uniProvAreaViewHolder.Nombre_Uni_Prov_Area_CardView.setText(provincias.getsNombreProvincia().toUpperCase());
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

    private void LoadProvincias() {
        adapterProvincia = new FirebaseRecyclerAdapter<Provincias, Uni_Prov_Area_ViewHolder>
                (Provincias.class, R.layout.cardview_area_provincia_universidad, Uni_Prov_Area_ViewHolder.class,
                        DBProvincia) {
            @Override
            protected void populateViewHolder(Uni_Prov_Area_ViewHolder uniProvAreaViewHolder, Provincias provincias, int i) {
                uniProvAreaViewHolder.Nombre_Uni_Prov_Area_CardView.setText(provincias.getsNombreProvincia().toUpperCase());
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
