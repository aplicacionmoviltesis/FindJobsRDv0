package com.example.findjobsrdv0.Administradores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findjobsrdv0.GeneralesApp.PantallaReportarProblemas;
import com.example.findjobsrdv0.GeneralesApp.ProblemasAppReportar;
import com.example.findjobsrdv0.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PantallaDetalleReportesProblemas extends AppCompatActivity {

    private TextView TvTituloProblem, TvIdProblemAppReport, TvDecripcionProblem,
            TvFechaProblem,TvIdUserReportProblem;

    private String sTituloProblem, sDecripcionProblem,
            sFechaProblem,sIdUserReportProblem,sEstadoReportProblemAct, sImagenProblem;

    private Button btnCambiarEstadoProblema;

    private ImageView ImagenProblem;

    private String sIdProblemAppReport = "";
    private Spinner spinEstadoReporte;

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
        DBProblemasReportados = databaseProblemReport.getReference(getResources().getString(R.string.Ref_ProblemasReportadosApp));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        spinEstadoReporte = (Spinner) findViewById(R.id.xmlspinEstadoReporte);
        ArrayAdapter<CharSequence> adapterEstadoReport = ArrayAdapter.createFromResource(this,
                R.array.EstadoReporteAdmin, android.R.layout.simple_spinner_item);
        adapterEstadoReport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinEstadoReporte.setAdapter(adapterEstadoReport);

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

        btnCambiarEstadoProblema = (Button) findViewById(R.id.xmlbtnCambiarEstadoReporte);
        btnCambiarEstadoProblema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CambiarEstadoReporte(sIdProblemAppReport);
            }
        });

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
                    sImagenProblem = problemasAppReportar.getsImagenProblem();
                    spinEstadoReporte.setSelection(obtenerPosicionItem(spinEstadoReporte, problemasAppReportar.getsEstadoReporte()));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void CambiarEstadoReporte(String sIdProblemAppReport){
        sTituloProblem = TvTituloProblem.getText().toString().trim();
        sDecripcionProblem = TvDecripcionProblem.getText().toString().trim();

        sFechaProblem = TvFechaProblem.getText().toString().trim();
        sEstadoReportProblemAct = spinEstadoReporte.getSelectedItem().toString();

        sIdUserReportProblem = TvIdUserReportProblem.getText().toString().trim();
        //sImagenProblem =

        ProblemasAppReportar problemasAppReportar = new ProblemasAppReportar( sIdProblemAppReport, sTituloProblem, sDecripcionProblem, sFechaProblem, sImagenProblem, sIdUserReportProblem,sEstadoReportProblemAct);
        DBProblemasReportados.child( sIdProblemAppReport ).setValue( problemasAppReportar );
        Toast.makeText( PantallaDetalleReportesProblemas.this, "El Problema cambio de estado, exitosamente", Toast.LENGTH_LONG ).show();

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public int obtenerPosicionItem(Spinner spinner, String fruta) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(fruta)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }
}
