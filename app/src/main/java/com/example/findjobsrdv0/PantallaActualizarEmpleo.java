package com.example.findjobsrdv0;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PantallaActualizarEmpleo extends AppCompatActivity {

    EditText editNombreEmpleoAE, editNombreEmpresaAE, editDireccionAE, editEmailAE,
            editTelefonoAE, editPaginaWebAE, editSalarioAE, editOtrosDatosAE;

    TextView tvMostrarIdiomasAE, tvFechaPublicacionAE;


    String sIDEmpleoEA, sNombreEmpleoAE, sNombreEmpresaAE, sDireccionAE, sEmailAE,
            sTelefonoAE, sPaginaWebAE, sSalarioAE, sOtrosDatosAE, sProvinciaAE,
            sJornadaAE, sHorarioAE, sTipoContratoAE, sCantidadVacantesAE, sAnoExpAE, sAreaAE,
            sFormacionAcademicaAE, sMostrarIdiomaAE, sRangoEdadAE, sSexoRequeridoAE,sEstadoEmpleoAE,
            sFechaPublicacionAE, sIdEmpleadorAE;

    Spinner spinJornadaAE, spinTipoContratoAE, spinCantidadVacantesAE, spinAnoExpAE,
            spinFormacionAcademicaAE, spinRangoEdadAE, spinSexoAE,spinHorarioAE;

    Button btnIdiomasAE, btnActivarCampoAE;

    ImageButton BtnActualizarEmpleoAE;

    RadioButton RdbDisponibleAE,RdbNoDisponibleAE;

    ImageView ImageViewAE;
    String sImagenEmpleoAE;


    String  sImagenEmpleoE;
    private DatabaseReference DBRefEmplosActualizar;
    private FirebaseAuth mAuthEmpleador;
    boolean IsFirstTimeClick = true;

/////Spinner Provincia

    Spinner spinProvinciaAE;
    DatabaseReference provinciasRefActualizar;
    List<Provincias> provincias;

/////Spinner Provincia

/////Spinner Area de Trabajo

    Spinner spinAreaAE;
    DatabaseReference areasRefActualizar,DBpersonasAplicaron;
    List<Provincias> areas;
    boolean IsFirstTimeClickAreas = true;

/////Spinner Area de Trabajo

    ////obtener imagen

    private FirebaseDatabase databaseArea;
    Query DBarea;

    ////obtener imagen

    Button BtnPersonasAplicaronEmpleoDE;

/////Checklist para elegir los idiomas

    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

/////Checklist para elegir los idiomas

    String sEmpleoIdE =" ";

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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        databaseArea = FirebaseDatabase.getInstance();
        DBarea = databaseArea.getReference("Areas");


        DBRefEmplosActualizar = FirebaseDatabase.getInstance().getReference("empleos");
        DBpersonasAplicaron = databaseArea.getReference();

        ImageViewAE = (ImageView) findViewById(R.id.xmlImageViewAE);

        editNombreEmpleoAE = (EditText) findViewById(R.id.xmlEditNombreEmpleoAE);
        editNombreEmpresaAE = (EditText) findViewById(R.id.xmlEditNombreEmpresaAE);
        editDireccionAE = (EditText) findViewById(R.id.xmlEditDireccionAE);
        editEmailAE = (EditText) findViewById(R.id.xmlEditEmailAE);
        editTelefonoAE = (EditText) findViewById(R.id.xmlEditTelefonoAE);
        editPaginaWebAE = (EditText) findViewById(R.id.xmlEditPaginaWebAE);
        editSalarioAE = (EditText) findViewById(R.id.xmlEditSalarioAE);
        editOtrosDatosAE = (EditText) findViewById(R.id.xmlEditOtrosRequisitosAE);

        //tvHorarioAE = (TextView) findViewById(R.id.xmlTiMostrarHorarioAE);
        tvMostrarIdiomasAE = (TextView) findViewById(R.id.xmlTiMostrarIdiomasAE);
        tvFechaPublicacionAE = (TextView) findViewById(R.id.xmlTiFechaPublicacionAE);

        btnIdiomasAE = (Button) findViewById(R.id.xmlBtnSeleccionarIdiomasAE);
        btnActivarCampoAE = (Button) findViewById(R.id.xmlBtnActivarcampos);




        RdbDisponibleAE = (RadioButton) findViewById(R.id.xmlRdDisponibleAE);
        RdbNoDisponibleAE = (RadioButton) findViewById(R.id.xmlRdNoDisponibleAE);

        btnActivarCampoAE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivarCampos();
            }
        });


        /////Spinner Provincia


        provinciasRefActualizar = FirebaseDatabase.getInstance().getReference();
        spinProvinciaAE = (Spinner) findViewById(R.id.xmlspinProvinciaAE);

        spinProvinciaAE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sProvinciaAE = spinProvinciaAE.getSelectedItem().toString();
                    Log.d("valorSpinProv", sProvinciaAE);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        provinciasRefActualizar.child("provincias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                final List<String> ListProvincias = new ArrayList<String>();
                for (DataSnapshot provinciaSnapshot : dataSnapshot.getChildren()) {
                    String provinciaName = provinciaSnapshot.child("Nombre_Provincia").getValue(String.class);
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
        spinProvinciaAE.setEnabled(false);

/////Spinner Provincia

/////Spinner Horario
        spinHorarioAE = findViewById(R.id.xmlspinHorarioAE);
        spinHorarioAE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sHorarioAE = spinHorarioAE.getSelectedItem().toString();
                    Log.d("valorSpinhorario", sHorarioAE);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> adapterHorario = ArrayAdapter.createFromResource(this,
                R.array.InfoHorario, android.R.layout.simple_spinner_item);
        adapterHorario.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinHorarioAE.setAdapter(adapterHorario);
        spinHorarioAE.setEnabled(false);

/////Spinner Horario

/////Spinner Jornada
        spinJornadaAE = findViewById(R.id.xmlspinJornadaAE);
        spinJornadaAE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sJornadaAE = spinJornadaAE.getSelectedItem().toString();
                    Log.d("valorSpinare", sJornadaAE);

                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> adapterJornada = ArrayAdapter.createFromResource(this,
                R.array.InfoJornada, android.R.layout.simple_spinner_item);
        adapterJornada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinJornadaAE.setAdapter(adapterJornada);
        spinJornadaAE.setEnabled(false);
/////Spinner Jornada

/////Spinner Tipo de contrato
        spinTipoContratoAE = findViewById(R.id.xmlspinTipoContratoAE);
        spinTipoContratoAE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sTipoContratoAE = spinTipoContratoAE.getSelectedItem().toString();
                    Log.d("valorSpintipocontrato", sTipoContratoAE);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> adapterTipoContrato = ArrayAdapter.createFromResource(this,
                R.array.infoTipoContratoE, android.R.layout.simple_spinner_item);
        adapterTipoContrato.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTipoContratoAE.setAdapter(adapterTipoContrato);

        spinTipoContratoAE.setEnabled(false);

/////Spinner Tipo de contrato

/////Spinner Cantidad de vacantes
        spinCantidadVacantesAE = findViewById(R.id.xmlspinCantidadVacantesAE);
        spinCantidadVacantesAE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sCantidadVacantesAE = spinCantidadVacantesAE.getSelectedItem().toString();
                    Log.d("valorSpincantvacantes", sCantidadVacantesAE);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> adapterCantidadVacantes = ArrayAdapter.createFromResource(this,
                R.array.InfoNumeros, android.R.layout.simple_spinner_item);
        adapterCantidadVacantes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCantidadVacantesAE.setAdapter(adapterCantidadVacantes);

        spinCantidadVacantesAE.setEnabled(false);

/////Spinner Cantidad de vacantes


/////Spinner Años Experiencia
        spinAnoExpAE = findViewById(R.id.xmlspinAnosExperienciaAE);
        spinAnoExpAE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sAnoExpAE = spinAnoExpAE.getSelectedItem().toString();
                    Log.d("valorSpinadapteranoexp", sAnoExpAE);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> adapterAnoExp = ArrayAdapter.createFromResource(this,
                R.array.InfoAnosExperiencia, android.R.layout.simple_spinner_item);
        adapterAnoExp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAnoExpAE.setAdapter(adapterAnoExp);

        spinAnoExpAE.setEnabled(false);

/////Spinner Años Experiencia


/////Spinner Area

        areasRefActualizar = FirebaseDatabase.getInstance().getReference();
        spinAreaAE = (Spinner) findViewById(R.id.xmlspinAreaAE);

        spinAreaAE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClickAreas) {
                    sAreaAE = spinAreaAE.getSelectedItem().toString();
                    ObtenerImagen(sAreaAE);

                    Log.d("valorSpinArea", sAreaAE);
                } else {
                    IsFirstTimeClickAreas = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        provinciasRefActualizar.child("Areas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListAreas = new ArrayList<String>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("Nombre_Area").getValue(String.class);
                    ListAreas.add(areaName);
                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(PantallaActualizarEmpleo.this, android.R.layout.simple_spinner_item, ListAreas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinAreaAE.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        spinAreaAE.setEnabled(false);

/////Spinner Area


/////Spinner Formacion o grado academico en el area
        spinFormacionAcademicaAE = findViewById(R.id.xmlspinFormacionAcademicaAE);
        spinFormacionAcademicaAE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sFormacionAcademicaAE = spinFormacionAcademicaAE.getSelectedItem().toString();
                    Log.d("valorSpinadapformacion", sFormacionAcademicaAE);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> adapterFormacionAcademicaE = ArrayAdapter.createFromResource(this,
                R.array.InfoGrado, android.R.layout.simple_spinner_item);
        adapterFormacionAcademicaE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinFormacionAcademicaAE.setAdapter(adapterFormacionAcademicaE);

        spinFormacionAcademicaAE.setEnabled(false);

/////Spinner Formacion o grado academico en el area


/////Checklist para elegir los idiomas


        listItems = getResources().getStringArray(R.array.InfoIdiomas);
        checkedItems = new boolean[listItems.length];

        btnIdiomasAE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(PantallaActualizarEmpleo.this);
                mBuilder.setTitle("Elegir Idiomas Requerido");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                        if (isChecked) {
                            mUserItems.add(position);
                        } else {
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        tvMostrarIdiomasAE.setText(item);
                    }
                });

                mBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            tvMostrarIdiomasAE.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

/////Checklist para elegir los idiomas

/////Spinner Sexo requerido
        spinSexoAE = findViewById(R.id.xmlspinSexoRequeridoAE);
        spinSexoAE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sSexoRequeridoAE = spinSexoAE.getSelectedItem().toString();
                    Log.d("valorSpinSexo", sSexoRequeridoAE);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> adapterSexoRequerido = ArrayAdapter.createFromResource(this,
                R.array.InfoSexo, android.R.layout.simple_spinner_item);
        adapterSexoRequerido.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSexoAE.setAdapter(adapterSexoRequerido);

        spinSexoAE.setEnabled(false);

/////Spinner Sexo requerido

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

        spinRangoEdadAE.setEnabled(false);

/////Spinner Rango edad


        RadioGroup RGEstadoActualEmpleoAE = (RadioGroup) findViewById(R.id.xmlRGEstadoActualEmpleoAE);
        RGEstadoActualEmpleoAE.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.xmlRdDisponibleAE:
                        sEstadoEmpleoAE = "Disponible";

                        break;
                    case R.id.xmlRdNoDisponibleAE:
                        sEstadoEmpleoAE = "No Disponible";
                        //Log.d("Valorestado",sEstadoEmpleoE);

                        break;
                }
                Log.d("Valorestado", sEstadoEmpleoAE);

            }
            // Log.d("Valorestado",sEstadoEmpleoE);
        });

///el metodp

        if(getIntent() != null){
            sEmpleoIdE = getIntent().getStringExtra("sEmpleoIdAnadidos");
            if(!sEmpleoIdE.isEmpty()){

                //goDetalleEmpleo(sEmpleoIdE);
                CargarEmpleoActualizar(sEmpleoIdE);

            }
        }
        BtnActualizarEmpleoAE = (ImageButton) findViewById(R.id.xmlBtnActualizarEmpleoAE);
        BtnActualizarEmpleoAE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent() != null){
                    sEmpleoIdE = getIntent().getStringExtra("sEmpleoIdAnadidos");
                    if(!sEmpleoIdE.isEmpty()){

                        //goDetalleEmpleo(sEmpleoIdE);
                        ActualizarEmpleo(sEmpleoIdE);

                    }
                }
            }
        });

        BtnPersonasAplicaronEmpleoDE = (Button) findViewById(R.id.xmlBtnPersonasAplicaron);
        BtnPersonasAplicaronEmpleoDE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerPersonasAplicaron(sEmpleoIdE);
            }
        });



    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    public void ActivarCampos(){
        editNombreEmpleoAE.setEnabled(true);
        editNombreEmpresaAE.setEnabled(true);
        editDireccionAE.setEnabled(true);
        editEmailAE.setEnabled(true);
        editTelefonoAE.setEnabled(true);
        editPaginaWebAE.setEnabled(true);
        editSalarioAE.setEnabled(true);
        editOtrosDatosAE.setEnabled(true);

        RdbDisponibleAE.setEnabled(true);
        RdbNoDisponibleAE.setEnabled(true);

        btnIdiomasAE.setEnabled(true);
        BtnActualizarEmpleoAE.setEnabled(true);

        spinProvinciaAE.setEnabled(true);
        spinAreaAE.setEnabled(true);
        spinHorarioAE.setEnabled(true);
        spinJornadaAE.setEnabled(true);
        spinTipoContratoAE.setEnabled(true);
        spinCantidadVacantesAE.setEnabled(true);
        spinAnoExpAE.setEnabled(true);
        spinFormacionAcademicaAE.setEnabled(true);
        spinRangoEdadAE.setEnabled(true);
        spinSexoAE.setEnabled(true);
    }

    public void CargarEmpleoActualizar(String sEmpleoIdE){
        DBRefEmplosActualizar.child(sEmpleoIdE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Empleos empleos = dataSnapshot.getValue(Empleos.class);
                Log.d("holap", String.valueOf(empleos));

                Picasso.get().load(empleos.getsImagenEmpleoE()).into(ImageViewAE);

                editNombreEmpleoAE.setText(empleos.getsNombreEmpleoE());
                editNombreEmpresaAE.setText(empleos.getsNombreEmpresaE());
                spinProvinciaAE.setSelection(obtenerPosicionItem(spinProvinciaAE, empleos.getsProvinciaE()));
                editDireccionAE.setText(empleos.getsDireccionE());
                editEmailAE.setText(empleos.getsEmailE());
                editTelefonoAE.setText(empleos.getsTelefonoE());
                editPaginaWebAE.setText(empleos.getsPaginaWebE());
                spinJornadaAE.setSelection(obtenerPosicionItem(spinJornadaAE, empleos.getsJornadaE()));
                spinHorarioAE.setSelection(obtenerPosicionItem(spinHorarioAE, empleos.getsHorarioE()));
                spinTipoContratoAE.setSelection(obtenerPosicionItem(spinTipoContratoAE, empleos.getsTipoContratoE()));
                spinCantidadVacantesAE.setSelection(obtenerPosicionItem(spinCantidadVacantesAE, empleos.getsCantidadVacantesE()));
                editSalarioAE.setText(empleos.getsSalarioE());
                spinAreaAE.setSelection(obtenerPosicionItem(spinAreaAE, empleos.getsAreaE()));
                spinAnoExpAE.setSelection(obtenerPosicionItem(spinAnoExpAE, empleos.getsAnosExperienciaDE()));
                spinFormacionAcademicaAE.setSelection(obtenerPosicionItem(spinFormacionAcademicaAE, empleos.getsFormacionAcademica()));
                tvMostrarIdiomasAE.setText(empleos.getsMostrarIdioma());
                spinSexoAE.setSelection(obtenerPosicionItem(spinSexoAE, empleos.getsRangoE()));
                spinRangoEdadAE.setSelection(obtenerPosicionItem(spinRangoEdadAE, empleos.getsRangoE()));
                editOtrosDatosAE.setText(empleos.getsOtrosDatosE());
                sImagenEmpleoAE = empleos.getsImagenEmpleoE();

               /* if(empleos.getsEstadoEmpleoE().equals("No Disponible")){

                    RdbNoDisponibleAE.setChecked(true);
                }*/

                RadioGroup RGEstadoActualEmpleoE = (RadioGroup) findViewById(R.id.xmlRGEstadoActualEmpleoAE);
                RGEstadoActualEmpleoE.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.xmlRdDisponibleAE:
                                sEstadoEmpleoAE = "Disponible";
                                //Log.d("Valorestado",sEstadoEmpleoAE);
                                break;
                            case R.id.xmlRdNoDisponibleAE:
                                sEstadoEmpleoAE = "No Disponible";
                                //Log.d("Valorestado",sEstadoEmpleoAE);
                                break;
                        }
                        Log.d("Valorestado", sEstadoEmpleoAE);
                    }
                });
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
                String currentDateandTime = simpleDateFormat.format(new Date());
                tvFechaPublicacionAE.setText("Ultima Actualizacion: " + currentDateandTime);
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


    public void ActualizarEmpleo(String sEmpleoIdE) {

        // FirebaseUser user = mAuthEmpleador.getCurrentUser();

        sNombreEmpleoAE = editNombreEmpleoAE.getText().toString().trim();
        sNombreEmpresaAE = editNombreEmpresaAE.getText().toString().trim();
        sDireccionAE = editDireccionAE.getText().toString().trim();
        sEmailAE = editEmailAE.getText().toString().trim();
        sTelefonoAE = editTelefonoAE.getText().toString().trim();
        sPaginaWebAE = editPaginaWebAE.getText().toString().trim();
        sSalarioAE = editSalarioAE.getText().toString().trim();
        sOtrosDatosAE = editOtrosDatosAE.getText().toString().trim();

        //sHorarioAE = tvHorarioAE.getText().toString().trim();
        sFechaPublicacionAE = tvFechaPublicacionAE.getText().toString().trim();
        sMostrarIdiomaAE = tvMostrarIdiomasAE.getText().toString().trim();

//spinners
        //sProvinciaE = "La Vega";
        //sJornadaE = "Matutina/Vespertina";
        //sHorarioE="";
        //sTipoContratoE = "Temporal";
        //sCantidadVacantesE = "3";
        //sAnoExpE = "";
        //sAreaE = "Contabilidad";
        //sFormacionAcademicaE = "";
        //sSexoRequeridoE = "Hombre";
        //sRangoEdadE = "25-35";
        //sHorarioE = "Diurno";

//RadioButton
        //sEstadoEmpleoE = "";



        //String Ukey = "klk45";
        //sIdEmpleadorE=Ukey;
        //sIDEmpleo = "444";

        ///sImagenEmpleoAE = "".toString().trim();
        String sPersonasAplicaronE = "".toString().trim();


        if (TextUtils.isEmpty(sNombreEmpleoAE)) {
            editNombreEmpleoAE.setError("Campo vacío, por favor escriba el nombre del empleo");
            return;
        }
        if (TextUtils.isEmpty(sNombreEmpresaAE)) {
            editNombreEmpresaAE.setError("Campo vacío, por favor escriba el nombre del empleo");
            return;
        }
        if (TextUtils.isEmpty(sDireccionAE)) {
            editDireccionAE.setError("Campo vacío, por favor escriba el nombre del empleo");
            return;
        }
        if (TextUtils.isEmpty(sTelefonoAE)) {
            editTelefonoAE.setError("Campo vacío, por favor escriba el nombre del empleo");
            return;
        }
        if (TextUtils.isEmpty(sEmailAE)) {
            editEmailAE.setError("Campo vacío, por favor escriba el nombre del empleo");
            return;
        }
//        FirebaseUser user = mAuthEmpleador.getCurrentUser();
        //String Ukey = user.getUid();
        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        sIdEmpleadorAE=Ukey;
        //String IDEmpleo = provinciasRefActualizar.push().getKey();
        String IDEmpleo = sEmpleoIdE;
        Empleos empleos = new Empleos(sEmpleoIdE, sNombreEmpleoAE, sNombreEmpresaAE,sDireccionAE,sProvinciaAE,
                sTelefonoAE, sPaginaWebAE, sEmailAE, sSalarioAE, sOtrosDatosAE,
                sHorarioAE, sFechaPublicacionAE, sMostrarIdiomaAE, sAreaAE,sAnoExpAE,
                sFormacionAcademicaAE,sSexoRequeridoAE, sRangoEdadAE, sJornadaAE, sCantidadVacantesAE,
                sTipoContratoAE, sEstadoEmpleoAE, sPersonasAplicaronE, sImagenEmpleoE, sIdEmpleadorAE);
        DBRefEmplosActualizar.child(sEmpleoIdE).setValue(empleos);
                //setValue(empleos);
        Toast.makeText(this, "El Empleo se Actualizo exitosamente", Toast.LENGTH_LONG).show();

        //DBReferenceEmplos.child("empleos").child(IDEmpleo).setValue(empleos);
        //DBReferenceEmplos.child(Ukey).child(IDEmpleo).setValue(empleos);//para registrarlo dentro del usuario que inicio sesion
        //DBReferenceEmplos.child(Ukey).child(IDEmpleo).child("PersonasAplicaron").setValue("1");//para registrarlo dentro del usuario que inicio sesion



    }

    private void ObtenerImagen(String sNombreAreakey) {

        Query queryArea = DBarea.orderByChild("Nombre_Area").equalTo(sNombreAreakey);
        queryArea.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("hola", String.valueOf(dataSnapshot));

                    Areas Dareas = new Areas();
                    Dareas.setsImagenArea(snapshot.child("Imagen_Area").getValue().toString());
                    sImagenEmpleoE = Dareas.getsImagenArea();
                    Log.d("foto", Dareas.getsImagenArea());
                    //Picasso.get().load(Dareas.getsImagenArea()).into(MostImagenArea);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void VerPersonasAplicaron(String sEmpleoIdE){
        DBpersonasAplicaron.child("EmpleosConCandidatos").orderByChild("sIdEmpleoAplico").equalTo(sEmpleoIdE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> CurriculosAplico = new ArrayList<String>();
                Log.d("holaperro", String.valueOf(dataSnapshot));

                for (DataSnapshot CurriculosSnapshot: dataSnapshot.getChildren()) {

                    String IdCurriculoAplico = CurriculosSnapshot.child("sIdCurriculoAplico").getValue(String.class);
                    //areas.add(areaName);
                    Log.d("holaperro", IdCurriculoAplico);
                    //sIdCurriculoAplico = areaName;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
