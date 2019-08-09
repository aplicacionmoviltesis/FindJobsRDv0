package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.ExperienciaLaboral;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.DetalleExperienciaLaboralViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetalleExperienciaLaboral extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference detExperienciaLab;

    private RecyclerView recycler_detalle_experiencialab;
    private RecyclerView.LayoutManager layoutManager;

    private String detalleexperiencialaboral = "";

    private TextView tvTiExpLab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_experiencia_laboral);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Experiencias Laborales");

        tvTiExpLab = (TextView) findViewById(R.id.xmlTituloExpLab);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoslab.bold.ttf");
        tvTiExpLab.setTypeface(face);

        database = FirebaseDatabase.getInstance();
        detExperienciaLab = database.getReference(getResources().getString(R.string.Ref_Experiencia_Laboral));

        recycler_detalle_experiencialab = (RecyclerView) findViewById(R.id.recyclerViewDetalleExperienciaLab);
        recycler_detalle_experiencialab.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_detalle_experiencialab.setLayoutManager(layoutManager);

        if (getIntent() != null) {
            detalleexperiencialaboral = getIntent().getStringExtra("DetalleExperienciaLabID");
            if (!detalleexperiencialaboral.isEmpty()) {
                loadExperienciaLaboralDetalle(detalleexperiencialaboral);
            }
        }
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void loadExperienciaLaboralDetalle(String detalleexperiencialaboral) {
        final FirebaseRecyclerAdapter<ExperienciaLaboral, DetalleExperienciaLaboralViewHolder> adapter = new FirebaseRecyclerAdapter<ExperienciaLaboral, DetalleExperienciaLaboralViewHolder>(ExperienciaLaboral.class,
                R.layout.cardview_detalle_experiencia_laboral, DetalleExperienciaLaboralViewHolder.class,
                detExperienciaLab.orderByChild("sIdCurriculoExpLab").equalTo(detalleexperiencialaboral)) {
            @Override
            protected void populateViewHolder(DetalleExperienciaLaboralViewHolder ViewHolder, ExperienciaLaboral modelo, int position) {
                ViewHolder.txtNombreempres.setText(modelo.getsNombreEmpresaExpLab());
                ViewHolder.txtCargoocupado.setText(modelo.getsCargoOcupadoExpLab());
                ViewHolder.txtTelefono.setText(modelo.getsTelefonoExpLab());
                ViewHolder.txtFechaEntrada.setText(modelo.getsFechaEntradaExpLab());
                ViewHolder.txtFechaSalida.setText(modelo.getsFechaSalidaExpLab());

                final ExperienciaLaboral clickItem = modelo;

                ViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }
        };
        recycler_detalle_experiencialab.setAdapter(adapter);
    }
}
