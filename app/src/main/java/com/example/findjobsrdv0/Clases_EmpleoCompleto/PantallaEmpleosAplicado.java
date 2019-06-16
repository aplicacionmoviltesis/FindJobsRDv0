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

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
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

    private String sIDEmpleoPEA,sNombreEmpleoPEA, sNombreEmpresaPEA,sProvinciaPEA,
            sDireccionPEA, sTelefonoPEA,sPaginaWebE,sEmailPEA,sSalarioPEA,sOtrosDatosPEA,
            sHorarioPEA,sFechaPublicacionPEA, sMostrarIdiomaPEA,sAreaPEA,
            sFormacionAcademicaPEA, sAnosExperienciaPEA,sSexoRequeridoPEA,sRangoPEA,
            sJornadaPEA,sCantidadVacantesPEA, sTipoContratoPEA,sEstadoEmpleoPEA,
            sPersonasAplicaronPEA,sImagenEmpleoPEA, sIdEmpleadorPEA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_empleos_aplicado);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

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

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public void TraerAplicacionesEmpleo(String sPersonaIdE) {

        Query q = EmpleosAplicadoDB.orderByChild("sIdPersonaAplico").equalTo(sPersonaIdE);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("dataAplicacionesEmpleo", String.valueOf(dataSnapshot));

                for (DataSnapshot CurriculosSnapshot : dataSnapshot.getChildren()) {

                    String IdEmpleoAplico = CurriculosSnapshot.child("sIdEmpleoAplico").getValue(String.class);
                    loadEmpleo(IdEmpleoAplico);
                    Log.d("dataidEmpleos", IdEmpleoAplico);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadEmpleo(final String sIDEmpleo) {
        DBEmpleos.child("empleos").orderByChild("sIDEmpleo").equalTo(sIDEmpleo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {


                    Empleos DatosEmpleos = datasnapshot.getValue(Empleos.class);


                    sIDEmpleoPEA = datasnapshot.child("nombre").getValue(String.class);
                    sNombreEmpleoPEA = DatosEmpleos.getsNombreEmpleoE();
                    sNombreEmpresaPEA = DatosEmpleos.getsNombreEmpresaE();
                    sProvinciaPEA = DatosEmpleos.getsProvinciaE();
                    sDireccionPEA = DatosEmpleos.getsDireccionE();
                    sTelefonoPEA = DatosEmpleos.getsTelefonoE();
                    sPaginaWebE = DatosEmpleos.getsPaginaWebE();
                    sEmailPEA = DatosEmpleos.getsEmailE();
                    sSalarioPEA = DatosEmpleos.getsSalarioE();
                    sOtrosDatosPEA = DatosEmpleos.getsOtrosDatosE();
                    sHorarioPEA = DatosEmpleos.getsHorarioE();
                    sFechaPublicacionPEA = DatosEmpleos.getsFechaPublicacionE();
                    sMostrarIdiomaPEA = DatosEmpleos.getsMostrarIdiomaE();
                    sAreaPEA = DatosEmpleos.getsAreaE();
                    sFormacionAcademicaPEA = DatosEmpleos.getsFormacionAcademicaE();
                    sAnosExperienciaPEA = DatosEmpleos.getsAnosExperienciaE();
                    sSexoRequeridoPEA = DatosEmpleos.getsSexoRequeridoE();
                    sRangoPEA = DatosEmpleos.getsRangoE();
                    sJornadaPEA = DatosEmpleos.getsJornadaE();
                    sCantidadVacantesPEA = DatosEmpleos.getsCantidadVacantesE();
                    sEstadoEmpleoPEA = DatosEmpleos.getsEstadoEmpleoE();
                    sPersonasAplicaronPEA = DatosEmpleos.getsPersonasAplicaronE();
                    sIdEmpleadorPEA = DatosEmpleos.getsIdEmpleadorE();
                    sImagenEmpleoPEA = DatosEmpleos.getsImagenEmpleoE();


                    Log.d("DATOS::::", datasnapshot.child("sNombreEmpleoE").getValue(String.class));
                    Log.d("DATOS::::", sNombreEmpleoPEA);

                    final Empleos empleos = new Empleos(sIDEmpleoPEA,sNombreEmpleoPEA, sNombreEmpresaPEA,sProvinciaPEA,
                            sDireccionPEA, sTelefonoPEA,sPaginaWebE,sEmailPEA,sSalarioPEA,sOtrosDatosPEA,
                            sHorarioPEA,sFechaPublicacionPEA, sMostrarIdiomaPEA,sAreaPEA,
                            sFormacionAcademicaPEA, sAnosExperienciaPEA,sSexoRequeridoPEA,sRangoPEA,
                            sJornadaPEA,sCantidadVacantesPEA, sTipoContratoPEA,sEstadoEmpleoPEA,
                            sPersonasAplicaronPEA,sImagenEmpleoPEA, sIdEmpleadorPEA);


                    PantallaEmpleosAplicado.this.mDatasetEmpleos.add(empleos);
                    adapterEmpleo.notifyDataSetChanged();

                    final Empleos clickItem = empleos;
                    adapterEmpleo.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Intent intent = new Intent(PantallaEmpleosAplicado.this, PantallaDetallesEmpleo.class);
                            intent.putExtra("sEmpleoIdBuscado", sIDEmpleo);
                            startActivity(intent);
                        }
                    });
                }
                Log.d("CVEMPLEO::::", String.valueOf(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
