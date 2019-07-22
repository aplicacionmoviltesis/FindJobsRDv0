package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.findjobsrdv0.Adaptadores_Empleador.Empleadores;
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
import java.util.List;

public class PantallaEmpresasAplicaronMiCurriculo extends AppCompatActivity {

    private FirebaseDatabase dbCurriculosSolicitudes;
    private DatabaseReference DBEmpleadores, dbAplicacionesCurriculo, dbCurriculos;

    private AdapterEmpresa adapterEmpresa;
    private ArrayList<Empleadores> mDatasetEmpleadores = new ArrayList<Empleadores>();

    private String sIdEmpresaAplico = "";

    private String IdCurriculo, sIdCurriculoAplico;
    private RecyclerView recyclerViewEmpleadores;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseAuth mAuthEmpresaAplico;
    private FirebaseUser user;

    private String sNombreEAplicoCurriculo;
    private String sRncEmpleadorAplicoCurriculo;
    private String sPaginaWebEAplicoCurriculo;
    private String sTelefonoEAplicoCurriculo;
    private String sDireccionEAplicoCurriculo;
    private String sCorreoEAplicoCurriculo;
    private String sImagenEAplicoCurriculo;
    private String sProvinciaEAplicoCurriculo;
    private String sDescripcionEmpleadorAplicoCurriculo;
    private Boolean sVerificacionEAplicoCurriculo;
    private String sIdEmpleadorEAplicoCurriculo;
    List<String> CurriculoKlkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_empresas_aplicaron_mi_curriculo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        CurriculoKlkId = new ArrayList<String>();
        dbCurriculosSolicitudes = FirebaseDatabase.getInstance();

        dbAplicacionesCurriculo = dbCurriculosSolicitudes.getReference(getResources().getString(R.string.Ref_CurriculosConSolicitudes));

        dbCurriculos = dbCurriculosSolicitudes.getReference(getResources().getString(R.string.Ref_Curriculos));
        DBEmpleadores = dbCurriculosSolicitudes.getReference();

        recyclerViewEmpleadores = (RecyclerView) findViewById(R.id.ListaEmpresasAplicaronR);

        recyclerViewEmpleadores.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewEmpleadores.setLayoutManager(layoutManager);

        adapterEmpresa = new AdapterEmpresa(PantallaEmpresasAplicaronMiCurriculo.this, mDatasetEmpleadores);
        adapterEmpresa.notifyDataSetChanged();
        recyclerViewEmpleadores.setAdapter(adapterEmpresa);

        mAuthEmpresaAplico = FirebaseAuth.getInstance();
        user = mAuthEmpresaAplico.getCurrentUser();
        sIdEmpresaAplico = user.getUid();

        if (sIdEmpresaAplico != null) {
            if (!sIdEmpresaAplico.isEmpty()) {
                Query q = dbCurriculos.child(sIdEmpresaAplico);
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("dataCurriculo", String.valueOf(dataSnapshot));

                        String IdCurriculoExistente = dataSnapshot.child(getResources().getString(R.string.Campo_sIdCurriculo)).getValue(String.class);
                        Log.d("dataCurriculohhhhhhh", String.valueOf(IdCurriculoExistente));

                        TraerAplicacionesCurriculo(IdCurriculoExistente);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void TraerAplicacionesCurriculo(String sIdEmpresaAplico) {
        Log.d("dataCurriculoklk", String.valueOf(sIdEmpresaAplico));

        Query q = dbAplicacionesCurriculo.orderByChild(getResources().getString(R.string.Aplicacion_sIdCurriculoAplico)).equalTo(sIdEmpresaAplico);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("dataCurriculoAplic", String.valueOf(dataSnapshot));
                for (DataSnapshot EmpresasSnapshot : dataSnapshot.getChildren()) {
                    String IdEmpresaAplico = EmpresasSnapshot.child(getResources().getString(R.string.Aplicacion_sIdEmpresaAplico)).getValue(String.class);
                    loadEmpresa(IdEmpresaAplico);
                    Log.d("dataCEmpresasId", IdEmpresaAplico);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadEmpresa(final String sIDEmpresa) {
        Log.d("dataEmpresaAplic", String.valueOf(sIDEmpresa));
        DBEmpleadores.child(getResources().getString(R.string.Ref_Empleadores)).orderByChild(getResources().getString(R.string.Campo_sIdEmpleador)).equalTo(sIDEmpresa).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    Empleadores DatosEmpleadores = datasnapshot.getValue(Empleadores.class);

                    sNombreEAplicoCurriculo = DatosEmpleadores.getsNombreEmpleador();
                    sRncEmpleadorAplicoCurriculo = DatosEmpleadores.getsRncEmpleador();
                    sPaginaWebEAplicoCurriculo = DatosEmpleadores.getsPaginaWebEmpleador();
                    sTelefonoEAplicoCurriculo = DatosEmpleadores.getsTelefonoEmpleador();
                    sDireccionEAplicoCurriculo = DatosEmpleadores.getsDireccionEmpleador();
                    sCorreoEAplicoCurriculo = DatosEmpleadores.getsCorreoEmpleador();
                    sImagenEAplicoCurriculo = DatosEmpleadores.getsImagenEmpleador();
                    sVerificacionEAplicoCurriculo = DatosEmpleadores.getsVerificacionEmpleador();
                    sIdEmpleadorEAplicoCurriculo = DatosEmpleadores.getsIdEmpleador();
                    sProvinciaEAplicoCurriculo = DatosEmpleadores.getsProvinciaEmpleador();
                    sDescripcionEmpleadorAplicoCurriculo = DatosEmpleadores.getsDescripcionEmpleador();

                    Log.d("DATOS::::", datasnapshot.child(getResources().getString(R.string.Campo_sNombreEmpleador)).getValue(String.class));
                    Log.d("DATOS::::", sNombreEAplicoCurriculo);

                    final Empleadores empleadores = new Empleadores(sNombreEAplicoCurriculo, sRncEmpleadorAplicoCurriculo,
                            sPaginaWebEAplicoCurriculo, sTelefonoEAplicoCurriculo, sDireccionEAplicoCurriculo,
                            sCorreoEAplicoCurriculo, sImagenEAplicoCurriculo, sVerificacionEAplicoCurriculo, sIdEmpleadorEAplicoCurriculo, sDescripcionEmpleadorAplicoCurriculo, sProvinciaEAplicoCurriculo);

                    PantallaEmpresasAplicaronMiCurriculo.this.mDatasetEmpleadores.add(empleadores);
                    adapterEmpresa.notifyDataSetChanged();

                    final Empleadores clickItem = empleadores;
                    adapterEmpresa.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Intent intent = new Intent(PantallaEmpresasAplicaronMiCurriculo.this, PantallaDetallePerfilEmpresa.class);
                            intent.putExtra("sEmpresaIdAplico", adapterEmpresa.mDatasetEmpleadores.get(position).getsIdEmpleador());
                            startActivity(intent);
                        }
                    });
                }
                Log.d("CVEMPLEADORES::::", String.valueOf(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

