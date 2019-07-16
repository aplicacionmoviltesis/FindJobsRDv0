package com.example.findjobsrdv0;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.GeneralesApp.Provincias;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.AreasCurriculos;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Curriculos;
import com.example.findjobsrdv0.Pantallas_CurriculosCompleto.DetalleCurriculo;
import com.example.findjobsrdv0.Pantallas_CurriculosCompleto.PantallaListaCurriculosBuscados;
import com.example.findjobsrdv0.ViewHolders_CurriculosCompleto.AdapterCurriculo;
import com.example.findjobsrdv0.ViewHolders_CurriculosCompleto.VistaCurriculoViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class PantallaListaBuscarCurriculos extends AppCompatActivity {

    private FirebaseDatabase databaseCurriculoAplico;
    private DatabaseReference DBCurriculos, AreaCurriculoDB;

    private AdapterCurriculo adapterCurriculo;
    private ArrayList<Curriculos> mDatasetCurriculo = new ArrayList<Curriculos>();

    private RecyclerView recyclerViewCurriculo;
    private RecyclerView.LayoutManager layoutManager;

    private String sIdCurriculoArea, sAreaPrincipal, sAreaSecundaria, sAreaTerciaria, sAreaSeleccionada, sProvinciaSeleccionada;

    private String IdCurriculoSolicitado, IdBuscadorCurriculoS, ImagenCurriculoS,
            NombreCurriculoS, ApellidoCurriculoS, CedulaCurriculoS,
            EmailCurriculoS, TelefonoCurriculoS, CelularCurriculoS,
            ProvinciaCurriculoS, EstadoCivilCurriculoS, DireccionCurriculoS,
            OcupacionCurriculoS, IdiomaCurriculoS, GradomayorCurriculoS,
            EstadoactualCurriculoS, SexoCurriculoS, HabilidadesCurriculoS, FechaCurriculoS, NivelPrimarioFormAcad, NivelSecundarioFormAcad, CarreraFormAcad, UniversidadesFormAcad;

    /////Spinner Provincia

    private SearchableSpinner spinProvinciaE;
    private DatabaseReference provinciasRef;
    private List<Provincias> provincias;
    private boolean IsFirstTimeClick = false;

    /////Spinner Provincia

    /////Spinner Area de Trabajo

    private SearchableSpinner spinAreaE;
    private DatabaseReference areasRef;
    private List<Provincias> areas;
    private boolean IsFirstTimeClickAreas = false;

/////Spinner Area de Trabajo

    private Button btnBuscarCurriculo;

    private FirebaseRecyclerAdapter<Curriculos, VistaCurriculoViewHolder> adapterCurriculoLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_lista_buscar_curriculos);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Buscar Curriculo");

        databaseCurriculoAplico = FirebaseDatabase.getInstance();
        DBCurriculos = databaseCurriculoAplico.getReference();
        AreaCurriculoDB = databaseCurriculoAplico.getReference("AreasCurriculos");

        recyclerViewCurriculo = (RecyclerView) findViewById(R.id.ListaCurriculosBuscadosR);

        recyclerViewCurriculo.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewCurriculo.setLayoutManager(layoutManager);

        adapterCurriculo = new AdapterCurriculo(PantallaListaBuscarCurriculos.this, mDatasetCurriculo);
        recyclerViewCurriculo.setAdapter(adapterCurriculo);

        btnBuscarCurriculo = (Button) findViewById(R.id.btnBuscarCurriculos);

        //sAreaSeleccionada = "Telecomunicaciones";
        //sProvinciaSeleccionada = "San Cristóbal";


        //loadCurriculo();

        //loadCurriculo("-Liop9JgcTZ9qKTxoUvp");

        /////Spinner Provincia

        provinciasRef = FirebaseDatabase.getInstance().getReference();
        spinProvinciaE = (SearchableSpinner ) findViewById(R.id.xmlspinProvinciaBuscar);

        spinProvinciaE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sProvinciaSeleccionada = spinProvinciaE.getSelectedItem().toString();
                    Log.d("valorSpinProv", sProvinciaSeleccionada);
                } else {
                    IsFirstTimeClick = false;
                    sProvinciaSeleccionada = spinProvinciaE.getSelectedItem().toString();
                    Log.d("valorSpinProv", sProvinciaSeleccionada);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        provinciasRef.child("Provincias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListProvincias = new ArrayList<String>();
                for (DataSnapshot provinciaSnapshot : dataSnapshot.getChildren()) {
                    String provinciaName = provinciaSnapshot.child("sNombreProvincia").getValue(String.class);
                    ListProvincias.add(provinciaName);
                }

                ArrayAdapter<String> provinciasAdapter = new ArrayAdapter<String>(PantallaListaBuscarCurriculos.this, android.R.layout.simple_spinner_item, ListProvincias);
                provinciasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinProvinciaE.setAdapter(provinciasAdapter);
                //spinProvinciaE.setTitle("Seleccionar Provincia");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/////Spinner Provincia

        /////Spinner Area

        areasRef = FirebaseDatabase.getInstance().getReference();
        spinAreaE = (SearchableSpinner) findViewById(R.id.xmlspinAreaBuscar);

        spinAreaE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClickAreas) {
                    sAreaSeleccionada = spinAreaE.getSelectedItem().toString();
                    Log.d("valorSpinArea", sAreaSeleccionada);

                } else {
                    IsFirstTimeClickAreas = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        areasRef.child("Areas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListAreas = new ArrayList<String>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
//                    String areaName = areaSnapshot.child("Nombre_Area").getValue(String.class);
                    String areaName = areaSnapshot.child("sNombreArea").getValue(String.class);
                    ListAreas.add(areaName);
                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(PantallaListaBuscarCurriculos.this, android.R.layout.simple_spinner_item, ListAreas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinAreaE.setAdapter(areasAdapter);
                //spinAreaE.setTitle("Seleccionar Area");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        SubirCurriculos();
/////Spinner Area

        btnBuscarCurriculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("AreaSeleccionada",sAreaSeleccionada);
                adapterCurriculo.notifyDataSetChanged();
                mDatasetCurriculo.clear();
               // recyclerViewCurriculo.setAdapter(null);
                TraerAreas(sAreaSeleccionada);

            }
        });

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void TraerAreas(String sAreaSeleccionada) {

        //Query q = AreaCurriculoDB.orderByChild("sIdPersonaAplico").equalTo(sPersonaIdE);
        AreaCurriculoDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("dataAplicacionesCurri", String.valueOf(dataSnapshot));

                for (DataSnapshot AreaCurriculoSnapshot : dataSnapshot.getChildren()) {
                    AreasCurriculos areasCurriculos = AreaCurriculoSnapshot.getValue(AreasCurriculos.class);

                    sAreaPrincipal = areasCurriculos.getsAreaPrincipalCurr();
                    sAreaSecundaria = areasCurriculos.getsAreaSecundariaCurr();
                    sAreaTerciaria = areasCurriculos.getsAreaTerciaria();

                    if (sAreaPrincipal.equals(sAreaSeleccionada) || sAreaSecundaria.equals(sAreaSeleccionada) || sAreaTerciaria.equals(sAreaSeleccionada)) {
                        sIdCurriculoArea = areasCurriculos.getsIdCurriculo();
                        Log.d("dataAreaId", sIdCurriculoArea);
                        loadCurriculo(sIdCurriculoArea);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadCurriculo(final String sPersonasAplicaron) {
        DBCurriculos.child("Curriculos").orderByChild("sIdCurriculo").equalTo(sPersonasAplicaron).addValueEventListener(new ValueEventListener() {
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
                    ImagenCurriculoS = DatosCurriculo.getsImagenC();
                    NivelPrimarioFormAcad = DatosCurriculo.getsNivelPrimarioFormAcad();
                    NivelSecundarioFormAcad = DatosCurriculo.getsNivelSecundarioFormAcad();
                    CarreraFormAcad = DatosCurriculo.getsCarreraFormAcad();
                    UniversidadesFormAcad = DatosCurriculo.getsUniversidadFormAcad();

                    if (ProvinciaCurriculoS.equals(sProvinciaSeleccionada)) {

                        final Curriculos cv = new Curriculos(IdCurriculoSolicitado, ImagenCurriculoS, NombreCurriculoS, ApellidoCurriculoS, CedulaCurriculoS,
                                EmailCurriculoS, TelefonoCurriculoS, CelularCurriculoS,
                                ProvinciaCurriculoS, EstadoCivilCurriculoS, DireccionCurriculoS,
                                OcupacionCurriculoS, IdiomaCurriculoS, GradomayorCurriculoS,
                                EstadoactualCurriculoS, SexoCurriculoS, HabilidadesCurriculoS,
                                FechaCurriculoS, "aun no funciona", NivelPrimarioFormAcad, NivelSecundarioFormAcad, CarreraFormAcad, UniversidadesFormAcad);


                        PantallaListaBuscarCurriculos.this.mDatasetCurriculo.add(cv);
                        adapterCurriculo.notifyDataSetChanged();

                        final Curriculos clickItem = cv;
                        adapterCurriculo.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongClick) {
                                Toast.makeText(PantallaListaBuscarCurriculos.this, "Spinner vacío, por favor seleccione una Provincia", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(PantallaListaBuscarCurriculos.this, DetalleCurriculo.class);
                                intent.putExtra("detallecurrID", adapterCurriculo.mDatasetCurriculo.get(position).getsIdCurriculo());

                                startActivity(intent);
                            }
                        });
                    }
                }
                Log.d("CVPERSONAS::::", String.valueOf(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void SubirCurriculos() {
        DBCurriculos.child("Curriculos").addValueEventListener(new ValueEventListener() {
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
                    ImagenCurriculoS = DatosCurriculo.getsImagenC();
                    NivelPrimarioFormAcad = DatosCurriculo.getsNivelPrimarioFormAcad();
                    NivelSecundarioFormAcad = DatosCurriculo.getsNivelSecundarioFormAcad();
                    CarreraFormAcad = DatosCurriculo.getsCarreraFormAcad();
                    UniversidadesFormAcad = DatosCurriculo.getsUniversidadFormAcad();


                        final Curriculos cv = new Curriculos(IdCurriculoSolicitado, ImagenCurriculoS, NombreCurriculoS, ApellidoCurriculoS, CedulaCurriculoS,
                                EmailCurriculoS, TelefonoCurriculoS, CelularCurriculoS,
                                ProvinciaCurriculoS, EstadoCivilCurriculoS, DireccionCurriculoS,
                                OcupacionCurriculoS, IdiomaCurriculoS, GradomayorCurriculoS,
                                EstadoactualCurriculoS, SexoCurriculoS, HabilidadesCurriculoS,
                                FechaCurriculoS, "aun no funciona", NivelPrimarioFormAcad, NivelSecundarioFormAcad, CarreraFormAcad, UniversidadesFormAcad);


                        PantallaListaBuscarCurriculos.this.mDatasetCurriculo.add(cv);
                        adapterCurriculo.notifyDataSetChanged();

                        final Curriculos clickItem = cv;
                        adapterCurriculo.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongClick) {
                                Toast.makeText(PantallaListaBuscarCurriculos.this, "Spinner vacío, por favor seleccione una Provincia", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(PantallaListaBuscarCurriculos.this, DetalleCurriculo.class);
                                intent.putExtra("detallecurrID", adapterCurriculo.mDatasetCurriculo.get(position).getsIdCurriculo());

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
    private void loadCurriculoklk() {
        adapterCurriculoLoad = new FirebaseRecyclerAdapter<Curriculos, VistaCurriculoViewHolder>(Curriculos.class, R.layout.cardview_vista_curriculo,
                VistaCurriculoViewHolder.class, DBCurriculos.child("Curriculos")) {
            @Override
            protected void populateViewHolder(VistaCurriculoViewHolder viewHolder, Curriculos model, int position) {

                Picasso.get().load(model.getsImagenC()).into(viewHolder.imagen);
                viewHolder.txtNombre.setText(model.getsNombreC());
                viewHolder.txtCedula.setText(model.getsCedulaC());
                viewHolder.txtDireccion.setText(model.getsDireccionC());
                viewHolder.txtEstadoActual.setText(model.getsEstadoActualC());
                viewHolder.txtProvincia.setText(model.getsProvinciaC());
                viewHolder.txtGradoMayor.setText(model.getsGradoMayorC());

                //    Log.d( "hola", String.valueOf( viewHolder ) );

                final Curriculos clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent CurriculoDetalle = new Intent(PantallaListaBuscarCurriculos.this, DetalleCurriculo.class);
                        CurriculoDetalle.putExtra("detallecurrID", adapterCurriculoLoad.getRef(position).getKey());
                        startActivity(CurriculoDetalle);

                        //  Log.d("klk id",adapterCurriculoLoad.getRef( position ).getKey());

                        // Toast.makeText( PantalaVistaCurriculo.this, ""+clickItem.getsNombreC(), Toast.LENGTH_SHORT ).show();
                    }
                });
            }
        };
        recyclerViewCurriculo.setAdapter(adapterCurriculoLoad);
    }
}
