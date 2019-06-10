package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.findjobsrdv0.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PantallaEmpleosAplicado extends AppCompatActivity {

    private FirebaseDatabase databaseEmpleosAplico;
    private DatabaseReference DBEmpleos, EmpleosAplicadoDB;

    private AdapterEmpleo adapterEmpleo;
    private ArrayList<Empleos> mDatasetEmpleos = new ArrayList<Empleos>();

    private String sIdPersonaAplico = "";
    private RecyclerView recyclerViewEmpleos;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseAuth mAuthPersonaAplico;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_empleos_aplicado);

        databaseEmpleosAplico = FirebaseDatabase.getInstance();
        DBEmpleos = databaseEmpleosAplico.getReference();
        EmpleosAplicadoDB = databaseEmpleosAplico.getReference("EmpleosConCandidatos");

        recyclerViewEmpleos = (RecyclerView) findViewById(R.id.ListaEmpleosAplicadoR);

        recyclerViewEmpleos.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewEmpleos.setLayoutManager(layoutManager);

        adapterEmpleo = new AdapterEmpleo(PantallaEmpleosAplicado.this, mDatasetEmpleos);
        recyclerViewEmpleos.setAdapter(adapterEmpleo);

        mAuthPersonaAplico = FirebaseAuth.getInstance();
        user = mAuthPersonaAplico.getCurrentUser();
        sIdPersonaAplico= user.getUid();

        if(sIdPersonaAplico != null){
            if(!sIdPersonaAplico.isEmpty()){
                TraerAplicacionesEmpleo(sIdPersonaAplico);
            }
        }
    }

    public void TraerAplicacionesEmpleo(String sPersonaIdE) {

        Query q = EmpleosAplicadoDB.orderByChild("sIdPersonaAplico").equalTo(sPersonaIdE);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("dataAplicacionesEmpleo", String.valueOf(dataSnapshot));

                for (DataSnapshot CurriculosSnapshot : dataSnapshot.getChildren()) {

                    String IdEmpleoAplico = CurriculosSnapshot.child("sIdEmpleoAplico").getValue(String.class);
                    //loadCurriculo(IdCurriculoAplico);
                    Log.d("dataidEmpleos", IdEmpleoAplico);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
