package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Curriculos;
import com.example.findjobsrdv0.Pantallas_CurriculosCompleto.DetalleCurriculo;
import com.example.findjobsrdv0.ViewHolders_CurriculosCompleto.AdapterCurriculo;
import com.example.findjobsrdv0.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PantallaPersonasAplicaronEmpleo extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference vistaCurriculo, DBpersonasAplicaron;

    String sEmpleoIdPersonasAplicaron = "";

    private AdapterCurriculo adapterCurriculo;
    ArrayList<Curriculos> mDatasetCurriculo = new ArrayList<Curriculos>();


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    //private MyAdapter mAdapter;
    //ArrayList<Curriculos> mDataset = new ArrayList<Curriculos>();

    private String IdCurriculoPAE, IdBuscadorCurriculoPAE,ImagenCurriculoPAE,NombreCurriculoPAE, ApellidoCurriculoPAE, CedulaCurriculoPAE,
            EmailCurriculoPAE, TelefonoCurriculoPAE, CelularCurriculoPAE,
            ProvinciaCurriculoPAE, EstadoCivilCurriculoPAE, DireccionCurriculoPAE,
            OcupacionCurriculoPAE, IdiomaCurriculoPAE, GradomayorCurriculoPAE,
            EstadoactualCurriculoPAE, SexoCurriculoPAE, HabilidadesCurriculoPAE, FechaCurriculoPAE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_personas_aplicaron_empleo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        database = FirebaseDatabase.getInstance();
        vistaCurriculo = database.getReference();
        DBpersonasAplicaron = database.getReference("EmpleosConCandidatos");

        recyclerView = (RecyclerView) findViewById(R.id.ListaCurriculosAplicaronR);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapterCurriculo = new AdapterCurriculo(PantallaPersonasAplicaronEmpleo.this, mDatasetCurriculo);
        recyclerView.setAdapter(adapterCurriculo);

        if(getIntent() != null){
            sEmpleoIdPersonasAplicaron = getIntent().getStringExtra("sEmpleoIdEAplicados");
            if(!sEmpleoIdPersonasAplicaron.isEmpty()){

                TraerAplicaciones(sEmpleoIdPersonasAplicaron);
            }
        }
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public void TraerAplicaciones(String sEmpleoIdE) {

        Query q = DBpersonasAplicaron.orderByChild("sIdEmpleoAplico").equalTo(sEmpleoIdE);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("dataAplicaciones", String.valueOf(dataSnapshot));

                for (DataSnapshot CurriculosSnapshot : dataSnapshot.getChildren()) {

                    String IdCurriculoAplico = CurriculosSnapshot.child("sIdCurriculoAplico").getValue(String.class);
                    loadCurriculo(IdCurriculoAplico);
                    Log.d("dataidcurriculo", IdCurriculoAplico);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadCurriculo(final String sPersonasAplicaron) {
        vistaCurriculo.child("Curriculos").orderByChild("sIdCurriculo").equalTo(sPersonasAplicaron).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    Curriculos DatosCurriculo = datasnapshot.getValue(Curriculos.class);

                    ApellidoCurriculoPAE = DatosCurriculo.getsApellidoC();
                    NombreCurriculoPAE = DatosCurriculo.getsNombreC();
                    CedulaCurriculoPAE = DatosCurriculo.getsCedulaC();
                    EmailCurriculoPAE = DatosCurriculo.getsEmailC();
                    TelefonoCurriculoPAE = DatosCurriculo.getsTelefonoC();
                    CelularCurriculoPAE = DatosCurriculo.getsCelularC();
                    ProvinciaCurriculoPAE = DatosCurriculo.getsProvinciaC();
                    EstadoCivilCurriculoPAE = DatosCurriculo.getsEstadoCivilC();
                    DireccionCurriculoPAE = DatosCurriculo.getsDireccionC();
                    OcupacionCurriculoPAE = DatosCurriculo.getsOcupacionC();
                    IdiomaCurriculoPAE = DatosCurriculo.getsIdiomaC();
                    GradomayorCurriculoPAE = DatosCurriculo.getsGradoMayorC();
                    EstadoactualCurriculoPAE = DatosCurriculo.getsEstadoActualC();
                    SexoCurriculoPAE = DatosCurriculo.getsSexoC();
                    HabilidadesCurriculoPAE = DatosCurriculo.getsHabilidadesC();
                    FechaCurriculoPAE = DatosCurriculo.getsFechaC();
                    IdCurriculoPAE = DatosCurriculo.getsIdCurriculo();
                    IdBuscadorCurriculoPAE = DatosCurriculo.getsIdBuscadorEmpleo();
                    ImagenCurriculoPAE = DatosCurriculo.getsImagenC();


                    final Curriculos cv = new Curriculos(IdCurriculoPAE,IdBuscadorCurriculoPAE,ImagenCurriculoPAE,NombreCurriculoPAE, ApellidoCurriculoPAE, CedulaCurriculoPAE,
                            EmailCurriculoPAE, TelefonoCurriculoPAE, CelularCurriculoPAE,
                            ProvinciaCurriculoPAE, EstadoCivilCurriculoPAE, DireccionCurriculoPAE,
                            OcupacionCurriculoPAE, IdiomaCurriculoPAE, GradomayorCurriculoPAE,
                            EstadoactualCurriculoPAE, SexoCurriculoPAE, HabilidadesCurriculoPAE,
                            FechaCurriculoPAE,"aun no funciona");


                    PantallaPersonasAplicaronEmpleo.this.mDatasetCurriculo.add(cv);
                    adapterCurriculo.notifyDataSetChanged();

                    final Curriculos clickItem = cv;
                    adapterCurriculo.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(PantallaPersonasAplicaronEmpleo.this, "Spinner vacío, por favor seleccione una Provincia", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(PantallaPersonasAplicaronEmpleo.this, DetalleCurriculo.class);
                            intent.putExtra("detallecurrID", adapterCurriculo.mDatasetCurriculo.get(position).getsIdCurriculo());
                            //Log.d("klk",dataSnapshot.getRef().getKey());
                            startActivity(intent);
                        }
                    });
                }
                Log.d("CVPERSONAS::::", String.valueOf(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
