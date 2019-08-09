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
import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.Referencias;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.DetalleReferenciaViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetalleReferencias extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference detReferencia;

    private RecyclerView recycler_detalle_referencia;
    private RecyclerView.LayoutManager layoutManager;

    private String detallereferencia = "";
    private TextView tvTiReferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_referencias);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Referencias");

        tvTiReferencia = (TextView) findViewById(R.id.xmlTituloReferenciaDetalle);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoslab.bold.ttf");
        tvTiReferencia.setTypeface(face);

        database = FirebaseDatabase.getInstance();
        detReferencia = database.getReference(getResources().getString(R.string.Ref_Referencia));

        recycler_detalle_referencia = (RecyclerView) findViewById(R.id.recyclerViewDetalleFormAcad);
        recycler_detalle_referencia.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_detalle_referencia.setLayoutManager(layoutManager);

        if (getIntent() != null) {
            detallereferencia = getIntent().getStringExtra("DetalleReferenciaID");

            if (!detallereferencia.isEmpty()) {
                loadReferencias(detallereferencia);
            }
        }
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void loadReferencias(String detallereferencia) {
        final FirebaseRecyclerAdapter<Referencias, DetalleReferenciaViewHolder> adapter = new FirebaseRecyclerAdapter<Referencias, DetalleReferenciaViewHolder>(Referencias.class,
                R.layout.cardview_detalle_referencia, DetalleReferenciaViewHolder.class,
                detReferencia.orderByChild("sIdCurriculorRef").equalTo(detallereferencia)) {
            @Override
            protected void populateViewHolder(DetalleReferenciaViewHolder ViewHolder, Referencias modelo, int position) {

                ViewHolder.txtNombre.setText(modelo.getsNombrePersonaRef());
                ViewHolder.txtCargoOcupado.setText(modelo.getsCargoOcupadoRef());
                ViewHolder.txtInstitucion.setText(modelo.getsInstitucionRef());
                ViewHolder.txtTelefono.setText(modelo.getsTelefonoRef());

                final Referencias clickItem = modelo;

                ViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }
        };
        recycler_detalle_referencia.setAdapter(adapter);
    }
}
