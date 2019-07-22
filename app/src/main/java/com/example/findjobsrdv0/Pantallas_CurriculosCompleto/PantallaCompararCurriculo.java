package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.Curriculos;
import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.AdapterCompararCurriculo;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.ViewHolderCompCurriculo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PantallaCompararCurriculo extends AppCompatActivity {

    private FirebaseDatabase databaseCurriculoFavoritos;
    private DatabaseReference DBCurriculoFav, CurriculoFavoritosDB;

    private ArrayList<Curriculos> mDatasetCurriculo = new ArrayList<Curriculos>();

    private String sIdUserBuscCurr = "";
    private RecyclerView recyclerViewCurriculoFav;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseAuth mAuthBuscCurr;
    private FirebaseUser user;

    private String sIdCurriculo, sIdBuscadorEmpleo, sImagenC, sNombreC, sApellidoC, sCedulaC, sEmailC, sTelefonoC, sCelularC,
            sProvinciaC, sEstadoCivilC, sDireccionC, sOcupacionC, sIdiomaC, sGradoMayorC,
            sEstadoActualC, sSexoC, sHabilidadesC, sFechaC, sAreaC;

    private ArrayList<AdapterCompararCurriculo> curriculosFavsArray = new ArrayList<>();
    private ViewHolderCompCurriculo adapter;
    private AppCompatButton btnGetSelected;

    private ArrayList<String> ListaId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_comparar_curriculo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.titulo_CompararCurriculos));

        databaseCurriculoFavoritos = FirebaseDatabase.getInstance();
        DBCurriculoFav = databaseCurriculoFavoritos.getReference();
        CurriculoFavoritosDB = databaseCurriculoFavoritos.getReference();

        this.btnGetSelected = (AppCompatButton) findViewById(R.id.btnGetSelectedComp);

        recyclerViewCurriculoFav = (RecyclerView) findViewById(R.id.ListaCurriculoPruebasR);

        recyclerViewCurriculoFav.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewCurriculoFav.setLayoutManager(layoutManager);

        adapter = new ViewHolderCompCurriculo(PantallaCompararCurriculo.this, curriculosFavsArray);
        recyclerViewCurriculoFav.setAdapter(adapter);

        ListaId = new ArrayList<>();
        mAuthBuscCurr = FirebaseAuth.getInstance();
        user = mAuthBuscCurr.getCurrentUser();
        sIdUserBuscCurr = user.getUid();

        if (sIdUserBuscCurr != null) {
            if (!sIdUserBuscCurr.isEmpty()) {
                Log.d("datafavoritoidpersona", String.valueOf(sIdUserBuscCurr));
                TraerEmpleosFavoritos(sIdUserBuscCurr);
            }
        }

        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected().size() > 1 && adapter.getSelected().size() < 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    ListaId.clear();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        stringBuilder.append(adapter.getSelected().get(i).getsIdCurriculo());
                        stringBuilder.append("\n");
                        if (i < 2) {
                            ListaId.add(adapter.getSelected().get(i).getsIdCurriculo());
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

    private void showToast(String toString) {
        Toast.makeText(this, toString, Toast.LENGTH_SHORT).show();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void TraerEmpleosFavoritos(String sPersonaIdC) {

        Query q = CurriculoFavoritosDB.child(getResources().getString(R.string.Ref_EmpleadoresConFavoritos))
                .child(sPersonaIdC)
                .child(getResources().getString(R.string.Favoritos_LIKES));//referencia likes
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("datafavoritoEmpleo", String.valueOf(dataSnapshot));

                for (DataSnapshot CurriculosSnapshot : dataSnapshot.getChildren()) {
                    String IdCurriculoAplico = CurriculosSnapshot.child(getResources().getString(R.string.Favoritos_IdCurriculoLike)).getValue(String.class);
                    loadCurriculoFav(IdCurriculoAplico);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadCurriculoFav(final String sFavIdCurriculo) {
        DBCurriculoFav.child(getResources().getString(R.string.Ref_Curriculos)).orderByChild(getResources().getString(R.string.Campo_sIdCurriculo)).equalTo(sFavIdCurriculo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    Curriculos DatosCurriculoFav = datasnapshot.getValue(Curriculos.class);

                    sIdCurriculo = DatosCurriculoFav.getsIdCurriculo();
                    sImagenC = DatosCurriculoFav.getsImagenC();
                    sNombreC = DatosCurriculoFav.getsNombreC();
                    sApellidoC = DatosCurriculoFav.getsApellidoC();
                    sCedulaC = DatosCurriculoFav.getsCedulaC();
                    sEmailC = DatosCurriculoFav.getsEmailC();
                    sTelefonoC = DatosCurriculoFav.getsTelefonoC();
                    sCelularC = DatosCurriculoFav.getsCelularC();
                    sProvinciaC = DatosCurriculoFav.getsProvinciaC();
                    sEstadoCivilC = DatosCurriculoFav.getsEstadoCivilC();
                    sDireccionC = DatosCurriculoFav.getsDireccionC();
                    sOcupacionC = DatosCurriculoFav.getsOcupacionC();
                    sIdiomaC = DatosCurriculoFav.getsIdiomaC();
                    sGradoMayorC = DatosCurriculoFav.getsGradoMayorC();
                    sEstadoActualC = DatosCurriculoFav.getsEstadoActualC();
                    sSexoC = DatosCurriculoFav.getsSexoC();
                    sHabilidadesC = DatosCurriculoFav.getsHabilidadesC();
                    sFechaC = DatosCurriculoFav.getsFechaC();
                    sAreaC = DatosCurriculoFav.getsAreaC();

                    final AdapterCompararCurriculo modCompCurriculo = new AdapterCompararCurriculo(sIdCurriculo, sIdBuscadorEmpleo, sImagenC, sNombreC,
                            sApellidoC, sCedulaC, sEmailC, sTelefonoC, sCelularC, sProvinciaC, sEstadoCivilC, sDireccionC, sOcupacionC, sIdiomaC,
                            sGradoMayorC, sEstadoActualC, sSexoC, sHabilidadesC, sFechaC, sAreaC);

                    PantallaCompararCurriculo.this.curriculosFavsArray.add(modCompCurriculo);

                    adapter.notifyDataSetChanged();
                }
                Log.d("CVEMPLEOFAV::::", String.valueOf(dataSnapshot));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void goVistaComparacionEmpleo(String sPrimerCurriculo, String sSegundoCurriculo) {

        Intent intent = new Intent(PantallaCompararCurriculo.this, PantallaVistaComparacionCurriculo.class);
        intent.putExtra("sIdCurriculoComparar1", sPrimerCurriculo);
        intent.putExtra("sIdCurriculoComparar2", sSegundoCurriculo);
        startActivity(intent);
    }
    public ArrayList<AdapterCompararCurriculo> getAll() {
        return curriculosFavsArray;
    }
}
