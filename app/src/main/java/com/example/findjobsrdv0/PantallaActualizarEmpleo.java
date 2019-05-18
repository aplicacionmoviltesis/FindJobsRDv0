package com.example.findjobsrdv0;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PantallaActualizarEmpleo extends AppCompatActivity {

    EditText editNombreEmpleoAE, editNombreEmpresaAE, editDireccionAE, editEmailAE,
            editTelefonoAE, editPaginaWebAE, editSalarioAE, editOtrosDatosAE;

    TextView tvHorarioAE, tvMostrarIdiomasAE, tvFechaPublicacionAE;


    String sIDEmpleoEA, sNombreEmpleoAE, sNombreEmpresaAE, sDireccionAE, sEmailAE,
            sTelefonoAE, sPaginaWebAE, sSalarioAE, sOtrosDatosAE, sProvinciaAE,
            sJornadaAE, sHorarioAE, sTipoContratoAE, sCantidadVacantesAE, sAnoExpAE, sAreaAE,
            sFormacionAcademicaAE, sMostrarIdiomaAE, sRangoEdadAE, sSexoRequeridoAE,sEstadoEmpleoAE,
            sFechaPublicacionAE, sIdEmpleadorAE;

    Spinner spinJornadaAE, spinTipoContratoAE, spinCantidadVacantesAE, spinAnoExpAE,
            spinFormacionAcademicaAE, spinRangoEdadAE, spinSexoAE;

    Button btnIdiomasAE;

    ImageButton btnGuardarEmpleoE;

    RadioButton RdbDisponibleAE,RdbNoDisponibleAE;


    String  sImagenEmpleoE;
    private DatabaseReference DBRefEmplosActualizar;
    private FirebaseAuth mAuthEmpleador;
    boolean IsFirstTimeClick = true;

    /////Spinner Provincia

    Spinner spinProvinciaAE;
    DatabaseReference provinciasRef;
    List<Provincias> provincias;
    String sProvinciaE;
   // boolean IsFirstTimeClick = true;

/////Spinner Provincia

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_actualizar_empleo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DBRefEmplosActualizar = FirebaseDatabase.getInstance().getReference("empleos");

        editNombreEmpleoAE = (EditText) findViewById(R.id.xmlEditNombreEmpleoAE);
        editNombreEmpresaAE = (EditText) findViewById(R.id.xmlEditNombreEmpresaAE);
        editDireccionAE = (EditText) findViewById(R.id.xmlEditDireccionAE);
        editEmailAE = (EditText) findViewById(R.id.xmlEditEmailAE);
        editTelefonoAE = (EditText) findViewById(R.id.xmlEditTelefonoAE);
        editPaginaWebAE = (EditText) findViewById(R.id.xmlEditPaginaWebAE);
        editSalarioAE = (EditText) findViewById(R.id.xmlEditSalarioAE);
        editOtrosDatosAE = (EditText) findViewById(R.id.xmlEditOtrosRequisitosAE);

        tvHorarioAE = (TextView) findViewById(R.id.xmlTiMostrarHorarioAE);
        tvMostrarIdiomasAE = (TextView) findViewById(R.id.xmlTiMostrarIdiomasAE);
        tvFechaPublicacionAE = (TextView) findViewById(R.id.xmlTiFechaPublicacionAE);

        btnIdiomasAE = (Button) findViewById(R.id.xmlBtnSeleccionarIdiomasAE);

        RdbDisponibleAE = (RadioButton) findViewById(R.id.xmlRdDisponibleAE);
        RdbNoDisponibleAE = (RadioButton) findViewById(R.id.xmlRdNoDisponibleAE);


        /////Spinner Rango edad
        spinRangoEdadAE = findViewById(R.id.xmlspinRangoEdadAE);
        spinRangoEdadAE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sRangoEdadAE = spinRangoEdadAE.getSelectedItem().toString();
                    Log.d("valorSpinEdad", sRangoEdadAE);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> adapterRangoEdadE = ArrayAdapter.createFromResource(this,
                R.array.InfoRangoEdad, android.R.layout.simple_spinner_item);
        adapterRangoEdadE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinRangoEdadAE.setAdapter(adapterRangoEdadE);
/////Spinner Rango edad

        /////Spinner Provincia

        provinciasRef = FirebaseDatabase.getInstance().getReference();
        spinProvinciaAE = (Spinner) findViewById(R.id.xmlspinProvinciaAE);

        spinProvinciaAE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sProvinciaE = spinProvinciaAE.getSelectedItem().toString();
                    Log.d("valorSpinProv", sProvinciaE);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        provinciasRef.child("provincias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListProvincias = new ArrayList<String>();
                for (DataSnapshot provinciaSnapshot : dataSnapshot.getChildren()) {
                    String provinciaName = provinciaSnapshot.child("nombre").getValue(String.class);
                    ListProvincias.add(provinciaName);
                }

                ArrayAdapter<String> provinciasAdapter = new ArrayAdapter<String>(PantallaActualizarEmpleo.this, android.R.layout.simple_spinner_item, ListProvincias);
                provinciasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinProvinciaAE.setAdapter(provinciasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/////Spinner Provincia

        String sEmpleoIdE ="-Lf6IjbeXPOkbdU2sYi7";

        DBRefEmplosActualizar.child(sEmpleoIdE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Empleos empleos= dataSnapshot.getValue(Empleos.class);
                //Log.d("holap", String.valueOf(empleos));

                //Picasso.get().load(empleos.getsImagenEmpleoE()).into(MostImagen);

                editNombreEmpleoAE.setText(empleos.getsNombreEmpleoE());
                editNombreEmpresaAE.setText(empleos.getsNombreEmpresaE());
                spinProvinciaAE.setSelection(obtenerPosicionItem(spinProvinciaAE,empleos.getsProvinciaE()));
                //spinProvinciaAE.setSelection(obtenerPosicionItem(spinRangoEdadAE,empleos.getsRangoE()));

                editDireccionAE.setText(empleos.getsDireccionE());
                editTelefonoAE.setText(empleos.getsTelefonoE());
                editPaginaWebAE.setText(empleos.getsPaginaWebE());
                //TvJornadaDE.setText(empleos.getsJornadaE());
                //TvMostrarHorarioDE.setText(empleos.getsHorarioE());
                //TvTipoContratoDE.setText(empleos.getsTipoContratoE());
                //TvCantidadVacantesDE.setText(empleos.getsCantidadVacantesE());
                editSalarioAE.setText(empleos.getsSalarioE());
                //TvAreaDE.setText(empleos.getsAreaE());
                //TvAnosExperienciaDE.setText(empleos.getsAnosExperienciaDE());
               // TvFormacionAcademicaDE.setText(empleos.getsFormacionAcademica());
               // TvIdiomasDE.setText(empleos.getsMostrarIdioma());
               // TvSexoRequeridoDE.setText(empleos.getsSexoRequeridoE());
                spinRangoEdadAE.setSelection(obtenerPosicionItem(spinRangoEdadAE,empleos.getsRangoE()));
                editOtrosDatosAE.setText(empleos.getsOtrosDatosE());
                //TvEstadoEmpleoDE.setText(empleos.getsEstadoEmpleoE());
                tvFechaPublicacionAE.setText(empleos.getsFechaPublicacionE());

//String klk= empleos.getsProvinciaE();
                //Log.d("pagina",empleos.getsPaginaWebE());

            }

            public int obtenerPosicionItem(Spinner spinner, String fruta) {
                //Creamos la variable posicion y lo inicializamos en 0
                int posicion = 0;
                //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
                //que lo pasaremos posteriormente
                for (int i = 0; i < spinner.getCount(); i++) {
                    //Almacena la posición del ítem que coincida con la búsqueda
                    if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(fruta)) {
                        posicion = i;
                    }
                }
                //Devuelve un valor entero (si encontro una coincidencia devuelve la
                // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
                return posicion;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
