package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.OtrosCursos;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.DetalleOtrosEstudiosViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetalleOtrosEstudios extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference detotrosestudios;

    RecyclerView recycler_detalle_otrosestudios;
    RecyclerView.LayoutManager layoutManager;

    String detalleotrosestudios = "";

    private TextView tvTiOtrosCursos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_otros_estudios);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        tvTiOtrosCursos = (TextView) findViewById(R.id.xmlTituloOtrosCursos);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoslab.bold.ttf");
        tvTiOtrosCursos.setTypeface(face);

        database = FirebaseDatabase.getInstance();
        detotrosestudios = database.getReference(getResources().getString(R.string.Ref_Otros_Cursos));

        recycler_detalle_otrosestudios = (RecyclerView) findViewById(R.id.recyclerViewOtrosEstudiosLab);
        recycler_detalle_otrosestudios.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_detalle_otrosestudios.setLayoutManager(layoutManager);

        if (getIntent() != null) {
            detalleotrosestudios = getIntent().getStringExtra("DetalleOtrosEstudiosID");

            Log.d("hola111", String.valueOf(detalleotrosestudios));
            if (!detalleotrosestudios.isEmpty()) {
                loadOtrosEstudios(detalleotrosestudios);
            }
        }
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void loadOtrosEstudios(String detalleotrosestudios) {
        final FirebaseRecyclerAdapter<OtrosCursos, DetalleOtrosEstudiosViewHolder> adapter = new FirebaseRecyclerAdapter<OtrosCursos, DetalleOtrosEstudiosViewHolder>(OtrosCursos.class,
                R.layout.cardview_detalle_otros_estudios, DetalleOtrosEstudiosViewHolder.class,
                detotrosestudios.orderByChild("sIdCurriculosOtrosCursos").equalTo(detalleotrosestudios)) {
            @Override
            protected void populateViewHolder(DetalleOtrosEstudiosViewHolder ViewHolder, OtrosCursos modelo, int position) {


                ViewHolder.txtInstitucion.setText(modelo.getsInstitucionOtrosCursos());
                ViewHolder.txtAno.setText(modelo.getsAnoOtrosCursos());
                ViewHolder.txtAreaoTema.setText(modelo.getsAreaoTemaOtrosCursos());
                ViewHolder.txtTipodeEstudio.setText(modelo.getsTipoEstudioOtrosCursos());

                final OtrosCursos clickItem = modelo;

                ViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }
        };
        recycler_detalle_otrosestudios.setAdapter(adapter);
    }
}
