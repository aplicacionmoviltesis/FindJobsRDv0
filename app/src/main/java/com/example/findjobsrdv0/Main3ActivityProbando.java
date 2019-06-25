package com.example.findjobsrdv0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.findjobsrdv0.Clases_EmpleoCompleto.AdapterEmpleo;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.Empleos;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaDetallesEmpleo;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaListaEmpleosFavoritos;
import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main3ActivityProbando extends AppCompatActivity {

    private FirebaseDatabase databaseEmpleosFavoritos;
    private DatabaseReference DBEmpleosFav, EmpleosFavoritosDB;

    private AdapterEmpleo adapterEmpleoFav;
    private ArrayList<Empleos> mDatasetEmpleos = new ArrayList<Empleos>();

    private String sIdUserBuscEmp = "";
    private RecyclerView recyclerViewEmpleosFav;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseAuth mAuthBuscEmp;
    private FirebaseUser user;

    private String sIDEmpleoFav, sNombreEmpleoFav, sNombreEmpresaFav, sProvinciaFav,
            sDireccionFav, sTelefonoFav, sPaginaWebFav, sEmailFav, sSalarioFav, sOtrosDatosFav,
            sHorarioFav, sFechaPublicacionFav, sMostrarIdiomaFav, sAreaFav,
            sFormacionAcademicaFav, sAnosExperienciaFav, sSexoRequeridoFav, sRangoFav,
            sJornadaFav, sCantidadVacantesFav, sTipoContratoFav, sEstadoEmpleoFav,
            sPersonasAplicaronFav, sImagenEmpleoFav, sIdEmpleadorFav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3_probando);
        databaseEmpleosFavoritos = FirebaseDatabase.getInstance();
        DBEmpleosFav = databaseEmpleosFavoritos.getReference();
        EmpleosFavoritosDB = databaseEmpleosFavoritos.getReference();

        recyclerViewEmpleosFav = (RecyclerView) findViewById(R.id.ListaEmpleosPruebasR);

        recyclerViewEmpleosFav.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewEmpleosFav.setLayoutManager(layoutManager);

        adapterEmpleoFav = new AdapterEmpleo(Main3ActivityProbando.this, mDatasetEmpleos);
        recyclerViewEmpleosFav.setAdapter(adapterEmpleoFav);

        mAuthBuscEmp = FirebaseAuth.getInstance();
        user = mAuthBuscEmp.getCurrentUser();
        sIdUserBuscEmp= user.getUid();

        if(sIdUserBuscEmp != null){
            if(!sIdUserBuscEmp.isEmpty()){
                Log.d("datafavoritoidpersona", String.valueOf(sIdUserBuscEmp));

                TraerEmpleosFavoritos(sIdUserBuscEmp);
            }
        }
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public void TraerEmpleosFavoritos(String sPersonaIdE) {

        Query q = EmpleosFavoritosDB.child("BuscadoresEmpleos")
                .child(sPersonaIdE)
                .child("likes");//referencia likes

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("datafavoritoEmpleo", String.valueOf(dataSnapshot));

                for (DataSnapshot CurriculosSnapshot : dataSnapshot.getChildren()) {

                    String IdEmpleoAplico = CurriculosSnapshot.child("IdEmpleoLike").getValue(String.class);
                    loadEmpleosFav(IdEmpleoAplico);
                    Log.d("dataidEmpleos", IdEmpleoAplico);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadEmpleosFav(final String sFavIdEmpleo) {
        DBEmpleosFav.child("empleos").orderByChild("sIDEmpleo").equalTo(sFavIdEmpleo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    Empleos DatosEmpleosFav = datasnapshot.getValue(Empleos.class);

                    sIDEmpleoFav = DatosEmpleosFav.getsIDEmpleo();
                    sNombreEmpleoFav = DatosEmpleosFav.getsNombreEmpleoE();
                    sNombreEmpresaFav = DatosEmpleosFav.getsNombreEmpresaE();
                    sProvinciaFav = DatosEmpleosFav.getsProvinciaE();
                    sDireccionFav = DatosEmpleosFav.getsDireccionE();
                    sTelefonoFav = DatosEmpleosFav.getsTelefonoE();
                    sPaginaWebFav = DatosEmpleosFav.getsPaginaWebE();
                    sEmailFav = DatosEmpleosFav.getsEmailE();
                    sSalarioFav = DatosEmpleosFav.getsSalarioE();
                    sOtrosDatosFav = DatosEmpleosFav.getsOtrosDatosE();
                    sHorarioFav = DatosEmpleosFav.getsHorarioE();
                    sFechaPublicacionFav = DatosEmpleosFav.getsFechaPublicacionE();
                    sMostrarIdiomaFav = DatosEmpleosFav.getsMostrarIdiomaE();
                    sAreaFav = DatosEmpleosFav.getsAreaE();
                    sFormacionAcademicaFav = DatosEmpleosFav.getsFormacionAcademicaE();
                    sAnosExperienciaFav = DatosEmpleosFav.getsAnosExperienciaE();
                    sSexoRequeridoFav = DatosEmpleosFav.getsSexoRequeridoE();
                    sRangoFav = DatosEmpleosFav.getsRangoE();
                    sJornadaFav = DatosEmpleosFav.getsJornadaE();
                    sCantidadVacantesFav = DatosEmpleosFav.getsCantidadVacantesE();
                    sEstadoEmpleoFav = DatosEmpleosFav.getsEstadoEmpleoE();
                    sPersonasAplicaronFav = DatosEmpleosFav.getsPersonasAplicaronE();
                    sIdEmpleadorFav = DatosEmpleosFav.getsIdEmpleadorE();
                    sImagenEmpleoFav = DatosEmpleosFav.getsImagenEmpleoE();
                    sTipoContratoFav = DatosEmpleosFav.getsTipoContratoE();

                    Log.d("DATOSFAV::::", datasnapshot.child("sNombreEmpleoE").getValue(String.class));
                    Log.d("DATOSFAV::::", sNombreEmpleoFav);

                    if(sProvinciaFav.equals("Distrito Nacional")) {

                        final Empleos empleos = new Empleos(sIDEmpleoFav, sNombreEmpleoFav, sNombreEmpresaFav, sProvinciaFav,
                                sDireccionFav, sTelefonoFav, sPaginaWebFav, sEmailFav, sSalarioFav, sOtrosDatosFav,
                                sHorarioFav, sFechaPublicacionFav, sMostrarIdiomaFav, sAreaFav,
                                sFormacionAcademicaFav, sAnosExperienciaFav, sSexoRequeridoFav, sRangoFav,
                                sJornadaFav, sCantidadVacantesFav, sTipoContratoFav, sEstadoEmpleoFav,
                                sPersonasAplicaronFav, sImagenEmpleoFav, sIdEmpleadorFav);


                        Main3ActivityProbando.this.mDatasetEmpleos.add(empleos);
                        adapterEmpleoFav.notifyDataSetChanged();

                        final Empleos clickItem = empleos;
                        adapterEmpleoFav.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongClick) {
                                Intent intent = new Intent(Main3ActivityProbando.this, PantallaDetallesEmpleo.class);
                                intent.putExtra("sEmpleoIdBuscado", adapterEmpleoFav.mDatasetEmpleo.get(position).getsIDEmpleo());
                                startActivity(intent);
                            }
                        });
                    }else {
                        Log.d("CVEMPLEOFAV::::no es ", String.valueOf(dataSnapshot));

                    }
                }
                Log.d("CVEMPLEOFAV::::", String.valueOf(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
