package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import java.util.HashSet;
import java.util.List;

public class PantallaEmpresasAplicaronMiCurriculo extends AppCompatActivity {

    private FirebaseDatabase dbCurriculosSolicitudes;
    private DatabaseReference DBEmpleadores, dbAplicacionesCurriculo, dbCurriculos;

    private AdapterEmpresa adapterEmpresa;
    private AdapterEmpresa adapterEmpresaVacio;
    private ArrayList<Empleadores> mDatasetEmpleadores = new ArrayList<Empleadores>();

    private String sIdEmpresaAplico = "";

    private String IdCurriculo,sIdCurriculoAplico;
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
       // dbCurriculosSolicitudes.setPersistenceEnabled(true);

        //DBEmpleos = dbCurriculosSolicitudes.getReference();
        dbAplicacionesCurriculo = dbCurriculosSolicitudes.getReference("CurriculosConSolicitudes");

        dbCurriculos = dbCurriculosSolicitudes.getReference("Curriculos");
        DBEmpleadores = dbCurriculosSolicitudes.getReference();

        //mDatasetEmpleadores.clear();
        //HashSet hs = new HashSet();


        recyclerViewEmpleadores = (RecyclerView) findViewById(R.id.ListaEmpresasAplicaronR);

        recyclerViewEmpleadores.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewEmpleadores.setLayoutManager(layoutManager);

        adapterEmpresa = new AdapterEmpresa(PantallaEmpresasAplicaronMiCurriculo.this, mDatasetEmpleadores);
        adapterEmpresa.notifyDataSetChanged();
        recyclerViewEmpleadores.setAdapter(adapterEmpresa);




        //mDatasetEmpleadores.clear();


        mAuthEmpresaAplico = FirebaseAuth.getInstance();
        user = mAuthEmpresaAplico.getCurrentUser();
        sIdEmpresaAplico = user.getUid();

        /*dbCurriculos.orderByChild("sIdBuscadorEmpleo").equalTo(sIdEmpresaAplico).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> areas = new ArrayList<String>();
                Log.d("holap", String.valueOf(dataSnapshot));

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {

                    String areaName = areaSnapshot.child("sIdCurriculo").getValue(String.class);
                    //areas.add(areaName);
                    Log.d("holap", areaName);
                    sIdCurriculoAplico = areaName;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PantallaEmpresasAplicaronMiCurriculo.this, "Usted No tiene empleo registrados", Toast.LENGTH_LONG).show();


            }
        });*/

        if (sIdEmpresaAplico != null) {
            if (!sIdEmpresaAplico.isEmpty()) {

                Query q = dbCurriculos.orderByChild("sIdBuscadorEmpleo").equalTo(sIdEmpresaAplico);
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("dataCurriculo", String.valueOf(dataSnapshot));

                        for (DataSnapshot CurriculoSnapshot : dataSnapshot.getChildren()) {
                            String IdCurriculoExistente = CurriculoSnapshot.child("sIdCurriculo").getValue(String.class);
                            //recyclerViewEmpleadores.setAdapter(adapterEmpresaVacio);

                            TraerAplicacionesCurriculo(IdCurriculoExistente);


//                            //loadEmpleo(IdEmpleoAplico);
//                            IdCurriculo = IdCurriculoExistente;
//                            CurriculoKlkId.add(IdCurriculoExistente);
//                            Log.d("dataCurriculoIdlllll", String.valueOf(IdCurriculoExistente));
//
//                            Log.d("dataCurriculoId", String.valueOf(IdCurriculo));
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //mDatasetEmpleadores.clear();
                //ObtenerCurriculo(sIdEmpresaAplico);
            }
        }
//CurriculoKlkId.add("deddeded");
//        CurriculoKlkId.add("q4444444444444444q");
//
//        String hola = CurriculoKlkId.get(0);
//
//        //ObtenerCurriculo(sIdEmpresaAplico);
//        //Log.d("Curriculoperr", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
//        Log.d("Curriculoperroooooooo", String.valueOf(CurriculoKlkId));
//        //IdCurriculo = "-Lh1fK1-fPTCrzX8ibwm";
//        Log.d("Curriculoperr", String.valueOf(hola));
//
//        if (IdCurriculo != null) {
//            if (!IdCurriculo.isEmpty()) {
//                Log.d("Curriculoperr9999999999", IdCurriculo);
//
//                TraerAplicacionesCurriculo(IdCurriculo);
//            }
//        }


    }

   /* private String ObtenerCurriculo(String sIdUsuario) {

        Query q = dbCurriculos.orderByChild("sIdBuscadorEmpleo").equalTo(sIdUsuario);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("dataApliCurriculo", String.valueOf(dataSnapshot));

                for (DataSnapshot CurriculoSnapshot : dataSnapshot.getChildren()) {
                    IdCurriculo = CurriculoSnapshot.child("sIdCurriculo").getValue(String.class);
                    //loadEmpleo(IdEmpleoAplico);
                    Log.d("dataidEmpleos", IdCurriculo);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return  IdCurriculo;
    }*/

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void TraerAplicacionesCurriculo(String sIdEmpresaAplico) {
//        mDatasetEmpleadores.clear();
//        adapterEmpresa.notifyDataSetChanged();
        Log.d("dataCurriculoklk", String.valueOf(sIdEmpresaAplico));

        Query q = dbAplicacionesCurriculo.orderByChild("sIdCurriculoAplico").equalTo(sIdEmpresaAplico);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("dataCurriculoAplic", String.valueOf(dataSnapshot));
//                mDatasetEmpleadores.clear();
//                adapterEmpresa.notifyDataSetChanged();
                for (DataSnapshot EmpresasSnapshot : dataSnapshot.getChildren()) {
//                    mDatasetEmpleadores.clear();//----------------
//                    adapterEmpresa.notifyDataSetChanged();
                    String IdEmpresaAplico = EmpresasSnapshot.child("sIdEmpresaAplico").getValue(String.class);
                    loadEmpresa(IdEmpresaAplico);
                    Log.d("dataCEmpresasId", IdEmpresaAplico);
                }
//                mDatasetEmpleadores.clear();//-----------
//                adapterEmpresa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadEmpresa(final String sIDEmpresa) {
        Log.d("dataEmpresaAplic", String.valueOf(sIDEmpresa));
        //adapterEmpresa.notifyDataSetChanged();
//        mDatasetEmpleadores.clear();//----------------
//        adapterEmpresa.notifyDataSetChanged();
        DBEmpleadores.child("Empleadores").orderByChild("sIdEmpleador").equalTo(sIDEmpresa).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                Log.d("dataEmpresaAplic", String.valueOf(dataSnapshot));

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
                    Log.d("DATOS::::", datasnapshot.child("sNombreEmpleador").getValue(String.class));
                    Log.d("DATOS::::", sNombreEAplicoCurriculo);

                    final Empleadores empleadores = new Empleadores(sNombreEAplicoCurriculo,sRncEmpleadorAplicoCurriculo,
                            sPaginaWebEAplicoCurriculo,sTelefonoEAplicoCurriculo,sDireccionEAplicoCurriculo,
                            sCorreoEAplicoCurriculo,sImagenEAplicoCurriculo,sVerificacionEAplicoCurriculo,sIdEmpleadorEAplicoCurriculo,"descripcion","provincia");

                    //mDatasetEmpleadores.clear();
                    PantallaEmpresasAplicaronMiCurriculo.this.mDatasetEmpleadores.add(empleadores);
                    //mDatasetEmpleadores.clear();
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
//mDatasetEmpleadores.clear();
                //mDatasetEmpleadores.clear();//----------------
                //adapterEmpresa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

