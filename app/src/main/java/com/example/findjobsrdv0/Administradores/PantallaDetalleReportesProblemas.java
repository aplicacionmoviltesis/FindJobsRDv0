package com.example.findjobsrdv0.Administradores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.findjobsrdv0.GeneralesApp.Areas;
import com.example.findjobsrdv0.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PantallaDetalleReportesProblemas extends AppCompatActivity {

    private TextView TvTituloProblem, TvIdProblemAppReport, TvDecripcionProblem,
            TvFechaProblem,TvIdUserReportProblem;
    private ImageView ImagenProblem;

    private String sIdProblemAppReport = "";

    private FirebaseDatabase databaseProblemReport;
    private DatabaseReference DBProblemasReportados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalle_reportes_problemas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TvTituloProblem = (TextView) findViewById(R.id.xmlTvTituloProblem);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoslab.bold.ttf");
        TvTituloProblem.setTypeface(face);

        databaseProblemReport = FirebaseDatabase.getInstance();
        DBProblemasReportados = databaseProblemReport.getReference("ProblemasReportadosApp");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        ImagenProblem = (ImageView) findViewById(R.id.xmlImagenProblem);
        TvIdProblemAppReport = (TextView) findViewById(R.id.xmlTvIdProblemAppReport);
        TvDecripcionProblem = (TextView) findViewById(R.id.xmlTvDecripcionProblem);
        TvFechaProblem = (TextView) findViewById(R.id.xmlTvFechaProblem);
        TvIdUserReportProblem = (TextView) findViewById(R.id.xmlTvIdUserReportProblem);

        if (getIntent() != null) {
            sIdProblemAppReport = getIntent().getStringExtra("sProblemaReportadoId");
            if (!sIdProblemAppReport.isEmpty()) {
                goDetalleProblemaReportado(sIdProblemAppReport);
            }
        }

    }

    private void goDetalleProblemaReportado(String sIdProblemAppReport) {

        DBProblemasReportados.child(sIdProblemAppReport).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    ProblemasAppReportar problemasAppReportar = dataSnapshot.getValue(ProblemasAppReportar.class);
                    Log.d("holap", String.valueOf(problemasAppReportar));

                    if(!problemasAppReportar.getsImagenProblem().equals(null)){
                        Picasso.get().load(problemasAppReportar.getsImagenProblem()).into(ImagenProblem);
                    }
                    TvTituloProblem.setText(problemasAppReportar.getsTituloProblem());
                    TvIdProblemAppReport.setText(problemasAppReportar.getsIdProblemAppReport());
                    TvDecripcionProblem.setText(problemasAppReportar.getsDecripcionProblem());
                    TvFechaProblem.setText(problemasAppReportar.getsFechaProblem());
                    TvIdUserReportProblem.setText(problemasAppReportar.getsIdUserReportProblem());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
