package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.findjobsrdv0.Adaptadores_Empleador.Empleos;
import com.example.findjobsrdv0.Adaptadores_Empleador.EmpleosFav;
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

public class PantallaCompararEmpleos extends AppCompatActivity {

    private FirebaseDatabase databaseEmpleosFavoritos;
    private DatabaseReference DBEmpleosFav, EmpleosFavoritosDB;

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

    private ArrayList<EmpleosFav> empleosFavsArray = new ArrayList<>();
    private MultiAdapter adapter;
    private AppCompatButton btnGetSelected;

    private String sIdEmpleoE1 = "-Lg4alOTsAzGLKms6tmu";
    private String sIdEmpleoE2 = "-LgdHslsVo394m0k53Il";

    private ArrayList<String> ListaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_comparar_empleos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.titulo_CompararEmpleos));

        databaseEmpleosFavoritos = FirebaseDatabase.getInstance();
        DBEmpleosFav = databaseEmpleosFavoritos.getReference();
        EmpleosFavoritosDB = databaseEmpleosFavoritos.getReference();

        this.btnGetSelected = (AppCompatButton) findViewById(R.id.btnGetSelected);

        recyclerViewEmpleosFav = (RecyclerView) findViewById(R.id.ListaEmpleosPruebasR);

        recyclerViewEmpleosFav.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewEmpleosFav.setLayoutManager(layoutManager);

        adapter = new MultiAdapter(PantallaCompararEmpleos.this, empleosFavsArray);
        recyclerViewEmpleosFav.setAdapter(adapter);

        ListaId = new ArrayList<>();
        mAuthBuscEmp = FirebaseAuth.getInstance();
        user = mAuthBuscEmp.getCurrentUser();
        sIdUserBuscEmp = user.getUid();

        if (sIdUserBuscEmp != null) {
            if (!sIdUserBuscEmp.isEmpty()) {
                Log.d("datafavoritoidpersona", sIdUserBuscEmp);
                TraerEmpleosFavoritos(sIdUserBuscEmp);
            }
        }

        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected().size() > 1 && adapter.getSelected().size() < 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    ListaId.clear();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        stringBuilder.append(adapter.getSelected().get(i).getsIDEmpleo());
                        stringBuilder.append("\n");
                        if (i < 2) {
                            ListaId.add(adapter.getSelected().get(i).getsIDEmpleo());
                            Log.d("idempleo--0", String.valueOf(i));
                        }
                    }
                    Log.d("idempleoposicion", String.valueOf(ListaId));
                    goVistaComparacionEmpleo(ListaId.get(0), ListaId.get(1));
                    showToast(stringBuilder.toString());
                } else {
                    showToast("No Selection");
                }
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void TraerEmpleosFavoritos(String sPersonaIdE) {

        Query q = EmpleosFavoritosDB.child(getResources().getString(R.string.Ref_BuscadoresEmpleosConFavoritos))
                .child(sPersonaIdE)
                .child(getResources().getString(R.string.Favoritos_LIKES));//referencia likes

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("datafavoritoEmpleo", String.valueOf(dataSnapshot));
                for (DataSnapshot CurriculosSnapshot : dataSnapshot.getChildren()) {

                    String IdEmpleoAplico = CurriculosSnapshot.child(getResources().getString(R.string.Favoritos_IdEmpleoLike)).getValue(String.class);
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
        DBEmpleosFav.child(getResources().getString(R.string.Ref_Empleos)).orderByChild(getResources().getString(R.string.Campo_sIDEmpleo)).equalTo(sFavIdEmpleo).addValueEventListener(new ValueEventListener() {
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

                    Log.d("DATOSFAV::::", datasnapshot.child(getResources().getString(R.string.Campo_sNombreEmpleoE)).getValue(String.class));
                    Log.d("DATOSFAV::::", sNombreEmpleoFav);

                    final EmpleosFav empleosFav = new EmpleosFav(sIDEmpleoFav, sNombreEmpleoFav, sNombreEmpresaFav, sProvinciaFav,
                            sDireccionFav, sTelefonoFav, sPaginaWebFav, sEmailFav, sSalarioFav, sOtrosDatosFav,
                            sHorarioFav, sFechaPublicacionFav, sMostrarIdiomaFav, sAreaFav,
                            sFormacionAcademicaFav, sAnosExperienciaFav, sSexoRequeridoFav, sRangoFav,
                            sJornadaFav, sCantidadVacantesFav, sTipoContratoFav, sEstadoEmpleoFav,
                            sPersonasAplicaronFav, sImagenEmpleoFav, sIdEmpleadorFav);

                    PantallaCompararEmpleos.this.empleosFavsArray.add(empleosFav);

                    adapter.notifyDataSetChanged();

//                        final EmpleosFav clickItem = empleosFav;
//                        adapter.setEmpleosFavs(new ItemClickListener() {
//                            @Override
//                            public void onClick(View view, int position, boolean isLongClick) {
//                                Intent intent = new Intent(PantallaCompararEmpleos.this, PantallaDetallesEmpleo.class);
//                                intent.putExtra("sEmpleoIdBuscado", adapter.empleosFavs.get(position).getsIDEmpleo());
//                                startActivity(intent);
//                            }
//                        });
//                    }else {
//                        Log.d("CVEMPLEOFAV::::no es ", String.valueOf(dataSnapshot));
//
//                    }
                }
                Log.d("CVEMPLEOFAV::::", String.valueOf(dataSnapshot));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void goVistaComparacionEmpleo(String sIdPrimerEmpleo, String sIdSegundoEmpleo) {

        Intent intent = new Intent(PantallaCompararEmpleos.this, PantallaVistaComparacionEmpleos.class);
        intent.putExtra("sIdEmpleoComparar1", sIdPrimerEmpleo);
        intent.putExtra("sIdEmpleoComparar2", sIdSegundoEmpleo);
        startActivity(intent);
    }
    public ArrayList<EmpleosFav> getAll() {
        return empleosFavsArray;
    }
}
