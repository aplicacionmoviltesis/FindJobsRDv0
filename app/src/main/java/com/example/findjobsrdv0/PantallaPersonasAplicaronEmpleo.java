package com.example.findjobsrdv0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.Curriculos;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleCurriculo.DetalleCurriculo;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.Vista_Curriculo_Principal.Vista_Curriculo.VistaCurriculo;
import com.example.findjobsrdv0.adaptador.AdapterCurriculo;
import com.example.findjobsrdv0.adaptador.MyAdapter;
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

    private MyAdapter mAdapter;
    ArrayList<Curriculos> mDataset = new ArrayList<Curriculos>();

    private String IdCurriculoPAE, IdBuscadorCurriculoPAE,ImagenCurriculoPAE,NombreCurriculoPAE, ApellidoCurriculoPAE, CedulaCurriculoPAE,
            EmailCurriculoPAE, TelefonoCurriculoPAE, CelularCurriculoPAE,
            ProvinciaCurriculoPAE, EstadoCivilCurriculoPAE, DireccionCurriculoPAE,
            OcupacionCurriculoPAE, IdiomaCurriculoPAE, GradomayorCurriculoPAE,
            EstadoactualCurriculoPAE, SexoCurriculoPAE, HabilidadesCurriculoPAE, FechaCurriculoPAE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_personas_aplicaron_empleo);

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

        //TraerAplicaciones(this.klkempleo);

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
        vistaCurriculo.child("Curriculos").orderByChild("cCodigoId").equalTo(sPersonasAplicaron).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    NombreCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);
                    ApellidoCurriculoPAE = datasnapshot.child("apellido").getValue(String.class);
                    CedulaCurriculoPAE = datasnapshot.child("estadoCivil").getValue(String.class);
                    EmailCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);
                    TelefonoCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);
                    CedulaCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);
                    ProvinciaCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);
                    EstadoCivilCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);
                    DireccionCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);
                    OcupacionCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);
                    IdiomaCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);
                    GradomayorCurriculoPAE = datasnapshot.child("gradomayor").getValue(String.class);
                    EstadoactualCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);
                    SexoCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);
                    HabilidadesCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);
                    FechaCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);

                    IdCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);
                    IdBuscadorCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);
                    ImagenCurriculoPAE = datasnapshot.child("nombre").getValue(String.class);


                    Log.d("DATOS::::", datasnapshot.child("cedula").getValue(String.class));
                    final Curriculos cv = new Curriculos(IdCurriculoPAE,IdBuscadorCurriculoPAE,"imgen",NombreCurriculoPAE, ApellidoCurriculoPAE, CedulaCurriculoPAE,
                            EmailCurriculoPAE, TelefonoCurriculoPAE, CelularCurriculoPAE,
                            ProvinciaCurriculoPAE, EstadoCivilCurriculoPAE, DireccionCurriculoPAE,
                            OcupacionCurriculoPAE, IdiomaCurriculoPAE, GradomayorCurriculoPAE,
                            EstadoactualCurriculoPAE, SexoCurriculoPAE, HabilidadesCurriculoPAE, FechaCurriculoPAE);


                    PantallaPersonasAplicaronEmpleo.this.mDatasetCurriculo.add(cv);
                    adapterCurriculo.notifyDataSetChanged();

                    final Curriculos clickItem = cv;
                    adapterCurriculo.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(PantallaPersonasAplicaronEmpleo.this, "Spinner vacío, por favor seleccione una Provincia", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(PantallaPersonasAplicaronEmpleo.this, DetalleCurriculo.class);
                            intent.putExtra("detallecurrID", sPersonasAplicaron);
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
