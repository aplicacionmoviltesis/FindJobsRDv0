package com.example.findjobsrdv0.GeneralesApp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.findjobsrdv0.Administradores.PantallaDetalleOpiniones_Sugerencias;
import com.example.findjobsrdv0.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PantallaListaSugOpiAnadidas extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference DBOpiSug;
    private RecyclerView listaOpiSugAnadidos;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerAdapter<Opiniones_Sugerencias, Uni_Prov_Area_ViewHolder> adapterOpiSug;

    private String Ukey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_lista_sug_opi_anadidas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        database = FirebaseDatabase.getInstance();
        DBOpiSug= database.getReference(getResources().getString(R.string.Ref_OpinionesSugerenciasApp));
        DBOpiSug.keepSynced(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        listaOpiSugAnadidos = (RecyclerView)findViewById(R.id.ListaOpiSugAnadidasR);
        listaOpiSugAnadidos.setHasFixedSize(true);
        listaOpiSugAnadidos.setLayoutManager(new LinearLayoutManager(this));


        //Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Ukey ="yo";
        cargarOpiSug(Ukey);
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void cargarOpiSug(String ukey) {
        adapterOpiSug = new FirebaseRecyclerAdapter<Opiniones_Sugerencias, Uni_Prov_Area_ViewHolder>
                (Opiniones_Sugerencias.class,R.layout.cardview_area_provincia_universidad, Uni_Prov_Area_ViewHolder.class,
                        DBOpiSug.orderByChild("sIdUserOpiSug").equalTo(Ukey)) {
            @Override
            protected void populateViewHolder(Uni_Prov_Area_ViewHolder uniProvAreaViewHolder, Opiniones_Sugerencias opinionesSugerencias, int i) {
                uniProvAreaViewHolder.Nombre_Uni_Prov_Area_CardView.setText(opinionesSugerencias.getsTituloOpiSug());
                uniProvAreaViewHolder.OtroDato_Uni_Prov_Area_CardView.setText(opinionesSugerencias.getsDecripcionOpiSug());

                if(!opinionesSugerencias.getsImagenOpiSug().equals(null)) {
                    Picasso.get().load(opinionesSugerencias.getsImagenOpiSug()).into(uniProvAreaViewHolder.imagen_Uni_Prov_Area_CardView);
                }
                final Opiniones_Sugerencias clickItem = opinionesSugerencias;
                uniProvAreaViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent intent = new Intent(PantallaListaSugOpiAnadidas.this, PantallaDetalleOpiniones_Sugerencias.class);
                        intent.putExtra("sOpiSugId",adapterOpiSug.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        listaOpiSugAnadidos.setAdapter(adapterOpiSug);
    }
}
