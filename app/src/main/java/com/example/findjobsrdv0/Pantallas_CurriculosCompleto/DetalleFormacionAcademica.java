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
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.FormacionAcademica;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.ViewHolders_CurriculosCompleto.DetalleFormAcadViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetalleFormacionAcademica extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference detFormacionAcad;

    private RecyclerView recycler_detalle_formacad;
    private RecyclerView.LayoutManager layoutManager;

    private String detalleformacad = "";
    private TextView tvTiFormAcad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_formacion_academica);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        tvTiFormAcad = (TextView) findViewById(R.id.xmlTituloFormAcad);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoslab.bold.ttf");
        tvTiFormAcad.setTypeface(face);

        database = FirebaseDatabase.getInstance();
        detFormacionAcad = database.getReference("Formacion_Academica");

        recycler_detalle_formacad = (RecyclerView) findViewById(R.id.recyclerViewDetalleFormAcad);
        recycler_detalle_formacad.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_detalle_formacad.setLayoutManager(layoutManager);

        if (getIntent() != null) {
            detalleformacad = getIntent().getStringExtra("DetalleFormacionAcademicaID");

            if (!detalleformacad.isEmpty()) {
                loadFormacionacad(detalleformacad);
            }
        }

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void loadFormacionacad(String detalleformacad) {
        final FirebaseRecyclerAdapter<FormacionAcademica, DetalleFormAcadViewHolder> adapter = new FirebaseRecyclerAdapter<FormacionAcademica, DetalleFormAcadViewHolder>(FormacionAcademica.class,
                R.layout.cardview_detalle_formacion_academica, DetalleFormAcadViewHolder.class,
                detFormacionAcad.orderByChild("sIdCurriculoFormAcad").equalTo(detalleformacad)) {
            @Override
            protected void populateViewHolder(DetalleFormAcadViewHolder ViewHolder, FormacionAcademica modelo, int position) {
                ViewHolder.txtNivelPrimario.setText(modelo.getsNivelPrimarioFormAcad());
                ViewHolder.txtNivelSecundario.setText(modelo.getsNivelSecundarioFormAcad());
                ViewHolder.txtNivelSuperior.setText(modelo.getsNivelSuperiorFormAcad());
                ViewHolder.txtCarrera.setText(modelo.getsCarreraFormAcad());

                final FormacionAcademica clickItem = modelo;

                ViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                    }
                });
            }
        };
        recycler_detalle_formacad.setAdapter(adapter);
    }
}