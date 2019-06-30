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
import com.example.findjobsrdv0.GeneralesApp.PantallaReportarProblemas;
import com.example.findjobsrdv0.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PantallaListaReportesProblemas extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference DBProblemasReportados;
    private RecyclerView listaProblemasReportados;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerAdapter<ProblemasAppReportar, Uni_Prov_Area_ViewHolder> adapterProblemasReport;

    private String Ukey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_lista_reportes_problemas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        database = FirebaseDatabase.getInstance();
        DBProblemasReportados= database.getReference("ProblemasReportadosApp");
        DBProblemasReportados.keepSynced(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        listaProblemasReportados = (RecyclerView)findViewById(R.id.ListaProblemasReportadosR);
        listaProblemasReportados.setHasFixedSize(true);
        listaProblemasReportados.setLayoutManager(new LinearLayoutManager(this));


        LoadProblemasReportados();
    }

    private void LoadProblemasReportados() {
        adapterProblemasReport = new FirebaseRecyclerAdapter<ProblemasAppReportar, Uni_Prov_Area_ViewHolder>
                (ProblemasAppReportar.class,R.layout.cardview_area_provincia_universidad, Uni_Prov_Area_ViewHolder.class,
                        DBProblemasReportados) {
            @Override
            protected void populateViewHolder(Uni_Prov_Area_ViewHolder uniProvAreaViewHolder, ProblemasAppReportar problemasAppReportar, int i) {
                uniProvAreaViewHolder.Nombre_Uni_Prov_Area_CardView.setText(problemasAppReportar.getsTituloProblem());
                uniProvAreaViewHolder.OtroDato_Uni_Prov_Area_CardView.setText(problemasAppReportar.getsFechaProblem());

                if(!problemasAppReportar.getsImagenProblem().equals(null)) {
                    Picasso.get().load(problemasAppReportar.getsImagenProblem()).into(uniProvAreaViewHolder.imagen_Uni_Prov_Area_CardView);
                }
                final ProblemasAppReportar clickItem = problemasAppReportar;
                uniProvAreaViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(PantallaListaReportesProblemas.this, PantallaDetalleReportesProblemas.class);
                        intent.putExtra("sProblemaReportadoId",adapterProblemasReport.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        listaProblemasReportados.setAdapter(adapterProblemasReport);
    }
}

