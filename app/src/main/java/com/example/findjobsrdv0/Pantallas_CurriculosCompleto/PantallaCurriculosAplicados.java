package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

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
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.AdapterCurriculo;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Curriculos;
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

public class PantallaCurriculosAplicados extends AppCompatActivity {

    FirebaseDatabase databaseCurriculo;
    DatabaseReference vistaCurriculoSolicitados, DBCurriculoAplicado;

    String sEmpresaId = "";

    private AdapterCurriculo adapterCurriculoAplico;
    ArrayList<Curriculos> mDatasetCurriculo = new ArrayList<Curriculos>();


    private RecyclerView recyclerViewCurriculosA;
    private RecyclerView.LayoutManager layoutManager;


    private String IdCurriculoSolicitado, IdBuscadorCurriculoS, ImagenCurriculoS,
            NombreCurriculoS, ApellidoCurriculoS, CedulaCurriculoS,
            EmailCurriculoS, TelefonoCurriculoS, CelularCurriculoS,
            ProvinciaCurriculoS, EstadoCivilCurriculoS, DireccionCurriculoS,
            OcupacionCurriculoS, IdiomaCurriculoS, GradomayorCurriculoS,
            EstadoactualCurriculoS, SexoCurriculoS, HabilidadesCurriculoS, FechaCurriculoS;

    private FirebaseAuth mAuthEmpresa;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_curriculos_aplicados);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        databaseCurriculo = FirebaseDatabase.getInstance();
        vistaCurriculoSolicitados = databaseCurriculo.getReference();
        DBCurriculoAplicado = databaseCurriculo.getReference("CurriculosConSolicitudes");

        recyclerViewCurriculosA = (RecyclerView) findViewById(R.id.ListaCurriculosSolicitadosR);

        recyclerViewCurriculosA.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewCurriculosA.setLayoutManager(layoutManager);

        adapterCurriculoAplico = new AdapterCurriculo(PantallaCurriculosAplicados.this, mDatasetCurriculo);
        recyclerViewCurriculosA.setAdapter(adapterCurriculoAplico);

        mAuthEmpresa = FirebaseAuth.getInstance();
        user = mAuthEmpresa.getCurrentUser();
        sEmpresaId = user.getUid();

        if (sEmpresaId != null) {
            if (!sEmpresaId.isEmpty()) {

                TraerAplicaciones(sEmpresaId);
            }
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void TraerAplicaciones(String sEmpresaId) {

        Query q = DBCurriculoAplicado.orderByChild("sIdEmpresaAplico").equalTo(sEmpresaId);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("dataAplicacionesempresa", String.valueOf(dataSnapshot));

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
        vistaCurriculoSolicitados.child("Curriculos").orderByChild("sIdCurriculo").equalTo(sPersonasAplicaron).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    Curriculos DatosCurriculo = datasnapshot.getValue(Curriculos.class);

                    ApellidoCurriculoS = DatosCurriculo.getsApellidoC();
                    NombreCurriculoS = DatosCurriculo.getsNombreC();
                    CedulaCurriculoS = DatosCurriculo.getsCedulaC();
                    EmailCurriculoS = DatosCurriculo.getsEmailC();
                    TelefonoCurriculoS = DatosCurriculo.getsTelefonoC();
                    ProvinciaCurriculoS = DatosCurriculo.getsProvinciaC();
                    EstadoCivilCurriculoS = DatosCurriculo.getsEstadoCivilC();
                    DireccionCurriculoS = DatosCurriculo.getsDireccionC();
                    OcupacionCurriculoS = DatosCurriculo.getsOcupacionC();
                    IdiomaCurriculoS = DatosCurriculo.getsIdiomaC();
                    GradomayorCurriculoS = DatosCurriculo.getsGradoMayorC();
                    CelularCurriculoS = DatosCurriculo.getsCelularC();
                    EstadoactualCurriculoS = DatosCurriculo.getsEstadoActualC();
                    SexoCurriculoS = DatosCurriculo.getsSexoC();
                    HabilidadesCurriculoS = DatosCurriculo.getsHabilidadesC();
                    FechaCurriculoS = DatosCurriculo.getsFechaC();
                    IdCurriculoSolicitado = DatosCurriculo.getsIdCurriculo();
                    IdBuscadorCurriculoS = DatosCurriculo.getsIdBuscadorEmpleo();
                    ImagenCurriculoS = DatosCurriculo.getsImagenC();


                    final Curriculos cv = new Curriculos(IdCurriculoSolicitado, IdBuscadorCurriculoS, ImagenCurriculoS, NombreCurriculoS, ApellidoCurriculoS, CedulaCurriculoS,
                            EmailCurriculoS, TelefonoCurriculoS, CelularCurriculoS,
                            ProvinciaCurriculoS, EstadoCivilCurriculoS, DireccionCurriculoS,
                            OcupacionCurriculoS, IdiomaCurriculoS, GradomayorCurriculoS,
                            EstadoactualCurriculoS, SexoCurriculoS, HabilidadesCurriculoS,
                            FechaCurriculoS, "aun no funciona");


                    PantallaCurriculosAplicados.this.mDatasetCurriculo.add(cv);
                    adapterCurriculoAplico.notifyDataSetChanged();

                    final Curriculos clickItem = cv;
                    adapterCurriculoAplico.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(PantallaCurriculosAplicados.this, "Spinner vacío, por favor seleccione una Provincia", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(PantallaCurriculosAplicados.this, DetalleCurriculo.class);
                            intent.putExtra("detallecurrID",adapterCurriculoAplico.mDatasetCurriculo.get(position).getsIdCurriculo());

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
