package com.example.findjobsrdv0;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.findjobsrdv0.Adaptadores_Administrador.Provincias;
import com.example.findjobsrdv0.Adaptadores_Empleador.AdapterEmpleo;
import com.example.findjobsrdv0.Adaptadores_Empleador.Empleos;
import com.example.findjobsrdv0.Adaptadores_Empleador.EmpleosViewHolder;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaDetallesEmpleo;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaListaEmpleosBuscados;
import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.Pantallas_CurriculosCompleto.PantallaListaBuscarCurriculos;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class PantallaBuscarEmpleosReal extends AppCompatActivity {

    private String sEstadoEmpleoBuscar, sAreaSeleccionada, sProvinciaSeleccionada;

    private AdapterEmpleo adapterEmpleo;
    private ArrayList<Empleos> mDatasetEmpleos = new ArrayList<Empleos>();

    private String sIDEmpleoEmpBusc, sNombreEmpleoEmpBusc, sNombreEmpresaEmpBusc, sProvinciaEmpBusc,
            sDireccionEmpBusc, sTelefonoEmpBusc, sPaginaWebEmpBusc, sEmailEmpBusc, sSalarioEmpBusc, sOtrosDatosEmpBusc,
            sHorarioEmpBusc, sFechaPublicacionEmpBusc, sMostrarIdiomaEmpBusc, sAreaEmpBusc,
            sFormacionAcademicaFav, sAnosExperienciaEmpBusc, sSexoRequeridoEmpBusc, sRangoEmpBusc,
            sJornadaEmpBusc, sCantidadVacantesEmpBusc, sTipoContratoEmpBusc, sEstadoEmpleoEmpBusc,
            sEstadoAdminEmpBusc, sImagenEmpleoEmpBusc, sIdEmpleadorEmpBusc;
    /////Spinner Provincia

    private SearchableSpinner spinProvinciaBuscarEmpleo;
    private DatabaseReference provinciasRef;
    private List<Provincias> provincias;
    private boolean IsFirstTimeClick = false;

    /////Spinner Provincia

    /////Spinner Area de Trabajo

    private SearchableSpinner spinAreaBuscarEmpleo;
    private DatabaseReference areasRef;
    private List<Provincias> areas;
    private boolean IsFirstTimeClickAreas = false;

    /////Spinner Area de Trabajo

    private Spinner spinEstadoEmpleobuscar;

    private FirebaseRecyclerAdapter<Empleos, EmpleosViewHolder> adapterEmpleosBuscados;

    private ActionBar actionBar;
    private FirebaseDatabase databaseEmpleosBuscados;
    private DatabaseReference DBempleosBuscados;
    private RecyclerView listaEmpleosBuscados;
    private RecyclerView.LayoutManager layoutManager;

    private Button btnBuscarEmpleo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_buscar_empleos_real);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Buscar Empleos");

        databaseEmpleosBuscados = FirebaseDatabase.getInstance();
        DBempleosBuscados = databaseEmpleosBuscados.getReference(getResources().getString(R.string.Ref_Empleos));
        DBempleosBuscados.keepSynced(true);

        listaEmpleosBuscados = (RecyclerView) findViewById(R.id.ListaEmpleosBuscadosRealR);
        listaEmpleosBuscados.setHasFixedSize(true);
        listaEmpleosBuscados.setLayoutManager(new LinearLayoutManager(this));


        adapterEmpleo = new AdapterEmpleo(PantallaBuscarEmpleosReal.this, mDatasetEmpleos);
        listaEmpleosBuscados.setAdapter(adapterEmpleo);

        //cargarEmpleosBuscados();

        /////Spinner Estado Empleo

        spinEstadoEmpleobuscar = (Spinner) findViewById(R.id.xmlspinEstadoBuscarEmp);
        ArrayAdapter<CharSequence> adapterEstadoReport = ArrayAdapter.createFromResource(this,
                R.array.EstadoActual, android.R.layout.simple_spinner_item);
        adapterEstadoReport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinEstadoEmpleobuscar.setAdapter(adapterEstadoReport);

        /////Spinner Estado Empleo


        /////Spinner Provincia

        provinciasRef = FirebaseDatabase.getInstance().getReference();
        spinProvinciaBuscarEmpleo = (SearchableSpinner ) findViewById(R.id.xmlspinProvinciaBuscarEmp);

        spinProvinciaBuscarEmpleo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sProvinciaSeleccionada = spinProvinciaBuscarEmpleo.getSelectedItem().toString();
                    Log.d("valorSpinProv", sProvinciaSeleccionada);
                } else {
                    IsFirstTimeClick = false;
                    sProvinciaSeleccionada = spinProvinciaBuscarEmpleo.getSelectedItem().toString();
                    Log.d("valorSpinProv", sProvinciaSeleccionada);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        provinciasRef.child(getResources().getString(R.string.Ref_Provincias)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListProvincias = new ArrayList<String>();
                for (DataSnapshot provinciaSnapshot : dataSnapshot.getChildren()) {
                    String provinciaName = provinciaSnapshot.child("sNombreProvincia").getValue(String.class);
                    ListProvincias.add(provinciaName);
                }

                ArrayAdapter<String> provinciasAdapter = new ArrayAdapter<String>(PantallaBuscarEmpleosReal.this, android.R.layout.simple_spinner_item, ListProvincias);
                provinciasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinProvinciaBuscarEmpleo.setAdapter(provinciasAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /////Spinner Provincia

        /////Spinner Area

        areasRef = FirebaseDatabase.getInstance().getReference();
        spinAreaBuscarEmpleo = (SearchableSpinner) findViewById(R.id.xmlspinAreaBuscarEmp);

        spinAreaBuscarEmpleo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClickAreas) {
                    sAreaSeleccionada = spinAreaBuscarEmpleo.getSelectedItem().toString();
                    Log.d("valorSpinArea", sAreaSeleccionada);

                } else {
                    IsFirstTimeClickAreas = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        areasRef.child(getResources().getString(R.string.Ref_Areas)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListAreas = new ArrayList<String>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("sNombreArea").getValue(String.class);
                    ListAreas.add(areaName);
                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(PantallaBuscarEmpleosReal.this, android.R.layout.simple_spinner_item, ListAreas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinAreaBuscarEmpleo.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /////Spinner Area

        btnBuscarEmpleo = (Button) findViewById(R.id.btnBuscarEmpleo);
        btnBuscarEmpleo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterEstadoReport.notifyDataSetChanged();
                mDatasetEmpleos.clear();
                loadEmpleosBuscados();
                //Log.d("estadoEm",sEstadoAdminEmpBusc);

            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubuscar, menu);
        MenuItem item = menu.findItem(R.id.menuBuscar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menuBuscar) {
            return true;
        }
        if (id == R.id.action_bar_Ajuste) {
            Intent intent = new Intent(PantallaBuscarEmpleosReal.this, PantallaListaEmpleosBuscados.class);
            startActivityForResult(intent, 0);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    private void firebaseSearch(String TextSearch) {

        String query = TextSearch.toLowerCase();
        Query firebaseSearchQuery = DBempleosBuscados.orderByChild(getResources().getString(R.string.Campo_sNombreEmpleoE)).startAt(query).endAt(query + "\uf8ff");
        adapterEmpleosBuscados = new FirebaseRecyclerAdapter<Empleos, EmpleosViewHolder>
                (Empleos.class, R.layout.cardview_mostrar_empleo, EmpleosViewHolder.class, firebaseSearchQuery) {
            @Override
            protected void populateViewHolder(EmpleosViewHolder empleosViewHolder, Empleos empleos, int i) {

                empleosViewHolder.NombreEmpleoCardView.setText(empleos.getsNombreEmpleoE());
                empleosViewHolder.NombreEmpresaCardView.setText(empleos.getsNombreEmpresaE());
                empleosViewHolder.ProvinciaCardView.setText(empleos.getsProvinciaE());
                empleosViewHolder.AreaCardView.setText(empleos.getsAreaE());
                empleosViewHolder.EstadoCardView.setText(empleos.getsEstadoEmpleoE());
                empleosViewHolder.FechaPublicacionCardView.setText("Ultima Actualizacion: " + empleos.getsFechaPublicacionE());

                Picasso.get().load(empleos.getsImagenEmpleoE()).into(empleosViewHolder.imagenEmpleoCardView);
                final Empleos clickItem = empleos;
                empleosViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent intent = new Intent(PantallaBuscarEmpleosReal.this, PantallaDetallesEmpleo.class);
                        intent.putExtra("sEmpleoIdBuscado", adapterEmpleosBuscados.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        listaEmpleosBuscados.setAdapter(adapterEmpleosBuscados);
    }

    private void cargarEmpleosBuscados() {

        adapterEmpleosBuscados = new FirebaseRecyclerAdapter<Empleos, EmpleosViewHolder>
                (Empleos.class, R.layout.cardview_mostrar_empleo, EmpleosViewHolder.class, DBempleosBuscados) {
            @Override
            protected void populateViewHolder(EmpleosViewHolder empleosViewHolder, Empleos empleos, int i) {

                empleosViewHolder.NombreEmpleoCardView.setText(empleos.getsNombreEmpleoE());
                empleosViewHolder.NombreEmpresaCardView.setText(empleos.getsNombreEmpresaE());
                empleosViewHolder.ProvinciaCardView.setText(empleos.getsProvinciaE());
                empleosViewHolder.AreaCardView.setText(empleos.getsAreaE());
                empleosViewHolder.EstadoCardView.setText(empleos.getsEstadoEmpleoE());
                empleosViewHolder.FechaPublicacionCardView.setText("Ultima Actualizacion: " + empleos.getsFechaPublicacionE());

                Picasso.get().load(empleos.getsImagenEmpleoE()).into(empleosViewHolder.imagenEmpleoCardView);

                final Empleos clickItem = empleos;
                empleosViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(PantallaBuscarEmpleosReal.this, PantallaDetallesEmpleo.class);
                        intent.putExtra("sEmpleoIdBuscado", adapterEmpleosBuscados.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        listaEmpleosBuscados.setAdapter(adapterEmpleosBuscados);
    }

    private void loadEmpleosBuscados() {
        sEstadoEmpleoBuscar = spinEstadoEmpleobuscar.getSelectedItem().toString();
        //sEstadoEmpleoBuscar = spinEstadoEmpleobuscar.getSelectedItem().toString();
        DBempleosBuscados.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    Empleos empleosBuscados = datasnapshot.getValue(Empleos.class);

                    sIDEmpleoEmpBusc = empleosBuscados.getsIDEmpleo();
                    sNombreEmpleoEmpBusc = empleosBuscados.getsNombreEmpleoE();
                    sNombreEmpresaEmpBusc = empleosBuscados.getsNombreEmpresaE();
                    sProvinciaEmpBusc = empleosBuscados.getsProvinciaE();
                    sDireccionEmpBusc = empleosBuscados.getsDireccionE();
                    sTelefonoEmpBusc = empleosBuscados.getsTelefonoE();
                    sPaginaWebEmpBusc = empleosBuscados.getsPaginaWebE();
                    sEmailEmpBusc = empleosBuscados.getsEmailE();
                    sSalarioEmpBusc = empleosBuscados.getsSalarioE();
                    sOtrosDatosEmpBusc = empleosBuscados.getsOtrosDatosE();
                    sHorarioEmpBusc = empleosBuscados.getsHorarioE();
                    sFechaPublicacionEmpBusc = empleosBuscados.getsFechaPublicacionE();
                    sMostrarIdiomaEmpBusc = empleosBuscados.getsMostrarIdiomaE();
                    sAreaEmpBusc = empleosBuscados.getsAreaE();
                    sFormacionAcademicaFav = empleosBuscados.getsFormacionAcademicaE();
                    sAnosExperienciaEmpBusc = empleosBuscados.getsAnosExperienciaE();
                    sSexoRequeridoEmpBusc = empleosBuscados.getsSexoRequeridoE();
                    sRangoEmpBusc = empleosBuscados.getsRangoE();
                    sJornadaEmpBusc = empleosBuscados.getsJornadaE();
                    sCantidadVacantesEmpBusc = empleosBuscados.getsCantidadVacantesE();
                    sEstadoEmpleoEmpBusc = empleosBuscados.getsEstadoEmpleoE();
                    sEstadoAdminEmpBusc = empleosBuscados.getsEstadoAdminE();
                    sIdEmpleadorEmpBusc = empleosBuscados.getsIdEmpleadorE();
                    sImagenEmpleoEmpBusc = empleosBuscados.getsImagenEmpleoE();
                    sTipoContratoEmpBusc = empleosBuscados.getsTipoContratoE();

//                    Log.d("DATOSFAV::::", datasnapshot.child(getResources().getString(R.string.Campo_sNombreEmpleoE)).getValue(String.class));
//                    Log.d("DATOSFAV::::", sEstadoEmpleoBuscar);
                    Log.d("estprov",sProvinciaEmpBusc);
                    Log.d("estarea",sAreaSeleccionada);
                    Log.d("estadoEm",sEstadoEmpleoBuscar);

                    if (sProvinciaEmpBusc.equals(sProvinciaSeleccionada) && sAreaEmpBusc.equals(sAreaSeleccionada) && sEstadoEmpleoEmpBusc.equals(sEstadoEmpleoBuscar)) {

                        final Empleos empleos = new Empleos(sIDEmpleoEmpBusc, sNombreEmpleoEmpBusc, sNombreEmpresaEmpBusc, sProvinciaEmpBusc,
                                sDireccionEmpBusc, sTelefonoEmpBusc, sPaginaWebEmpBusc, sEmailEmpBusc, sSalarioEmpBusc, sOtrosDatosEmpBusc,
                                sHorarioEmpBusc, sFechaPublicacionEmpBusc, sMostrarIdiomaEmpBusc, sAreaEmpBusc,
                                sFormacionAcademicaFav, sAnosExperienciaEmpBusc, sSexoRequeridoEmpBusc, sRangoEmpBusc,
                                sJornadaEmpBusc, sCantidadVacantesEmpBusc, sTipoContratoEmpBusc, sEstadoEmpleoEmpBusc,
                                sEstadoAdminEmpBusc, sImagenEmpleoEmpBusc, sIdEmpleadorEmpBusc);

                        PantallaBuscarEmpleosReal.this.mDatasetEmpleos.add(empleos);
                        adapterEmpleo.notifyDataSetChanged();

                        final Empleos clickItem = empleos;
                        adapterEmpleo.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongClick) {
                                Intent intent = new Intent(PantallaBuscarEmpleosReal.this, PantallaDetallesEmpleo.class);
                                intent.putExtra("sEmpleoIdBuscado", adapterEmpleo.mDatasetEmpleo.get(position).getsIDEmpleo());
                                startActivity(intent);
                            }
                        });
                    }
                }
                //Log.d("CVEMPLEOFAV::::", String.valueOf(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
