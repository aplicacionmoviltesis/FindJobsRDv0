package com.example.findjobsrdv0;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PantallaRegistrarEmpleos extends AppCompatActivity {

    ImageView fFotoEmpresa;
    Boolean sVerificacionE = false;

    EditText editNombreEmpleoE, editNombreEmpresaE, editDireccionE, editEmailE,
            editTelefonoE, editPaginaWebE, editSalarioE, editOtrosDatosE;

    TextView tvHorarioE, tvMostrarIdiomasE, tvFechaPublicacionE;


    String sIDEmpleo, sNombreEmpleoE, sNombreEmpresaE, sDireccionE, sEmailE,
            sTelefonoE, sPaginaWebE, sSalarioE, sOtrosDatosE, sProvinciaE,
            sJornadaE, sHorarioE, sTipoContratoE, sCantidadVacantesE, sAnoExpE, sAreaE,
            sFormacionAcademicaE, sMostrarIdioma, sRangoEdadE, sSexoRequeridoE,sEstadoEmpleoE,
            sFechaPublicacionE, sIdEmpleadorE;

    Spinner spinJornadaE, spinTipoContratoE, spinCantidadVacantesE, spinAnoExpE,
            spinFormacionAcademicaE, spinRangoEdadE, spinSexoE;

    Button btnIdiomasE;

    ImageButton btnGuardarEmpleoE;

    RadioButton RdbDisponibleE,RdbNoDisponibleE;

/////Spinner Provincia

    Spinner spinProvinciaE;
    DatabaseReference provinciasRef;
    List<Provincias> provincias;
    boolean IsFirstTimeClick = true;

/////Spinner Provincia

/////Spinner Area de Trabajo

    Spinner spinAreaE;
    DatabaseReference areasRef;
    List<Provincias> areas;
    boolean IsFirstTimeClickAreas = true;

/////Spinner Area de Trabajo

/////Checklist para elegir los idiomas

    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

/////Checklist para elegir los idiomas


    String  sImagenEmpleoE;
    private DatabaseReference DBReferenceEmplos;
    private FirebaseAuth mAuthEmpleador;

    String currentDateandTime;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registrar_empleos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarprueba);
        setSupportActionBar(toolbar);

        DBReferenceEmplos = FirebaseDatabase.getInstance().getReference("empleos");



        editNombreEmpleoE = (EditText) findViewById(R.id.xmlEditNombreEmpleoE);
        editNombreEmpresaE = (EditText) findViewById(R.id.xmlEditNombreEmpresaE);
        editDireccionE = (EditText) findViewById(R.id.xmlEditDireccionE);
        editEmailE = (EditText) findViewById(R.id.xmlEditEmailE);
        editTelefonoE = (EditText) findViewById(R.id.xmlEditTelefonoE);
        editPaginaWebE = (EditText) findViewById(R.id.xmlEditPaginaWebE);
        editSalarioE = (EditText) findViewById(R.id.xmlEditSalarioE);
        editOtrosDatosE = (EditText) findViewById(R.id.xmlEditOtrosRequisitosE);

        tvHorarioE = (TextView) findViewById(R.id.xmlTiMostrarHorarioE);
        tvMostrarIdiomasE = (TextView) findViewById(R.id.xmlTiMostrarIdiomasE);
        tvFechaPublicacionE = (TextView) findViewById(R.id.xmlTiFechaPublicacionE);

        btnIdiomasE = (Button) findViewById(R.id.xmlBtnSeleccionarIdiomasE);

        RdbDisponibleE = (RadioButton) findViewById(R.id.xmlRdDisponibleE);
        RdbNoDisponibleE = (RadioButton) findViewById(R.id.xmlRdNoDisponibleE);



/////Spinner Provincia

        provinciasRef = FirebaseDatabase.getInstance().getReference();
        spinProvinciaE = (Spinner) findViewById(R.id.xmlspinProvinciaE);

        spinProvinciaE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sProvinciaE = spinProvinciaE.getSelectedItem().toString();
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
                    String provinciaName = provinciaSnapshot.child("Nombre_Provincia").getValue(String.class);
                    ListProvincias.add(provinciaName);
                }

                ArrayAdapter<String> provinciasAdapter = new ArrayAdapter<String>(PantallaRegistrarEmpleos.this, android.R.layout.simple_spinner_item, ListProvincias);
                provinciasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinProvinciaE.setAdapter(provinciasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/////Spinner Provincia

/////Spinner Jornada
        spinJornadaE = findViewById(R.id.xmlspinJornadaE);
        spinJornadaE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sJornadaE = spinJornadaE.getSelectedItem().toString();
                    Log.d("valorSpinare", sJornadaE);
                    if (sJornadaE.equals("Matutina")) {
                        sHorarioE = "8:00 AM - 12:00 PM";
                        tvHorarioE.setText(sHorarioE);
                    }
                    if (sJornadaE.equals("Vespertina")) {
                        sHorarioE = "1:00 PM - 6:00 PM";
                        tvHorarioE.setText(sHorarioE);
                    }
                    if (sJornadaE.equals("Nocturna")) {
                        sHorarioE = "6:00 PM - 12:00 AM";
                        tvHorarioE.setText(sHorarioE);
                    }
                    if (sJornadaE.equals("Matutina/Vespertina")) {
                        sHorarioE = "8:00 AM - 6:00 PM";
                        tvHorarioE.setText(sHorarioE);
                    }
                    if (sJornadaE.equals("Vespertina/Nocturna")) {
                        sHorarioE = "1:00 PM - 12:00 AM";
                        tvHorarioE.setText(sHorarioE);
                    }
                    if (sJornadaE.equals("Nocturna/Matutina")) {
                        sHorarioE = "12:00 AM - 6:00 AM";
                        tvHorarioE.setText(sHorarioE);
                    }

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
        spinJornadaE.setAdapter(adapterJornada);
/////Spinner Jornada

/////Spinner Tipo de contrato
        spinTipoContratoE = findViewById(R.id.xmlspinTipoContratoF);
        spinTipoContratoE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sTipoContratoE = spinTipoContratoE.getSelectedItem().toString();
                    Log.d("valorSpintipocontrato", sTipoContratoE);
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
        spinTipoContratoE.setAdapter(adapterTipoContrato);
/////Spinner Tipo de contrato

/////Spinner Cantidad de vacantes
        spinCantidadVacantesE = findViewById(R.id.xmlspinCantidadVacantesE);
        spinCantidadVacantesE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sCantidadVacantesE = spinCantidadVacantesE.getSelectedItem().toString();
                    Log.d("valorSpincantvacantes", sCantidadVacantesE);
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
        spinCantidadVacantesE.setAdapter(adapterCantidadVacantes);
/////Spinner Cantidad de vacantes


/////Spinner Años Experiencia
        spinAnoExpE = findViewById(R.id.xmlspinAnosExperienciaE);
        spinAnoExpE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sAnoExpE = spinAnoExpE.getSelectedItem().toString();
                    Log.d("valorSpinadapteranoexp", sAnoExpE);
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
        spinAnoExpE.setAdapter(adapterAnoExp);
/////Spinner Años Experiencia


/////Spinner Area

        areasRef = FirebaseDatabase.getInstance().getReference();
        spinAreaE = (Spinner) findViewById(R.id.xmlspinAreaE);

        spinAreaE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClickAreas) {
                    sAreaE = spinAreaE.getSelectedItem().toString();
                    Log.d("valorSpinArea", sAreaE);
                } else {
                    IsFirstTimeClickAreas = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        provinciasRef.child("Areas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListAreas = new ArrayList<String>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("Nombre_Area").getValue(String.class);
                    ListAreas.add(areaName);
                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(PantallaRegistrarEmpleos.this, android.R.layout.simple_spinner_item, ListAreas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinAreaE.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/////Spinner Area


/////Spinner Formacion o grado academico en el area
        spinFormacionAcademicaE = findViewById(R.id.xmlspinFormacionAcademicaE);
        spinFormacionAcademicaE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sFormacionAcademicaE = spinFormacionAcademicaE.getSelectedItem().toString();
                    Log.d("valorSpinadapformacion", sFormacionAcademicaE);
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
        spinFormacionAcademicaE.setAdapter(adapterFormacionAcademicaE);
/////Spinner Formacion o grado academico en el area


/////Checklist para elegir los idiomas


        listItems = getResources().getStringArray(R.array.InfoIdiomas);
        checkedItems = new boolean[listItems.length];

        btnIdiomasE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(PantallaRegistrarEmpleos.this);
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
                        tvMostrarIdiomasE.setText(item);
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
                            tvMostrarIdiomasE.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

/////Checklist para elegir los idiomas

/////Spinner Sexo requerido
        spinSexoE = findViewById(R.id.xmlspinSexoRequeridoE);
        spinSexoE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sSexoRequeridoE = spinSexoE.getSelectedItem().toString();
                    Log.d("valorSpinSexo", sSexoRequeridoE);
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
        spinSexoE.setAdapter(adapterSexoRequerido);
/////Spinner Sexo requerido

/////Spinner Rango edad
        spinRangoEdadE = findViewById(R.id.xmlspinRangoEdadE);
        spinRangoEdadE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sRangoEdadE = spinRangoEdadE.getSelectedItem().toString();
                    Log.d("valorSpinEdad", sRangoEdadE);
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
        spinRangoEdadE.setAdapter(adapterRangoEdadE);
/////Spinner Rango edad

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
        currentDateandTime = simpleDateFormat.format(new Date());
        tvFechaPublicacionE.setText("Ultima Actualizacion: " + currentDateandTime);




        btnGuardarEmpleoE = (ImageButton) findViewById(R.id.xmlBtnGuardarEmpleoE);
        btnGuardarEmpleoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarEmpleos(currentDateandTime);
            }
        });
/*
        if(RdbDisponibleE.isChecked())
        {
            sEstadoEmpleoE = "Disponible";
        }
        if(RdbNoDisponibleE.isChecked())
        {
            sEstadoEmpleoE = "No Disponible";

        }*/

        RadioGroup RGEstadoActualEmpleoE = (RadioGroup)findViewById(R.id.xmlRGEstadoActualEmpleoE);
        RGEstadoActualEmpleoE.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.xmlRdDisponibleE:
                        sEstadoEmpleoE = "Disponible";
                         //Log.d("Valorestado",sEstadoEmpleoE);

                        break;
                    case R.id.xmlRdNoDisponibleE:
                        sEstadoEmpleoE = "No Disponible";
                         //Log.d("Valorestado",sEstadoEmpleoE);

                        break;
                }
                 Log.d("Valorestado",sEstadoEmpleoE);

            }

            // Log.d("Valorestado",sEstadoEmpleoE);

        });

       // Log.d("Valorestado",sEstadoEmpleoE);


        //para obtener el nombre de la empresa automatico
        mAuthEmpleador = FirebaseAuth.getInstance();
        FirebaseUser user = mAuthEmpleador.getCurrentUser();
        SharedPreferences preferences= this.getSharedPreferences("UserPrefEmpleador", Context.MODE_PRIVATE);
        String Nombre= preferences.getString("Nombre", "Nombre");
        editNombreEmpresaE.setText(Nombre);
        editNombreEmpresaE.setText(user.getDisplayName());


    }



public void limpiarCampor(){
    editNombreEmpleoE.setText("");
    editNombreEmpresaE.setText("");
    editDireccionE.setText("");
    editEmailE.setText("");
    editTelefonoE.setText("");
    editPaginaWebE.setText("");
    editSalarioE.setText("");
    editOtrosDatosE.setText("");

    //sHorarioE = tvHorarioE.getText().toString().trim();
    //sFechaPublicacionE = tvFechaPublicacionE.getText().toString().trim();
    tvMostrarIdiomasE.setText("");



}



    public void RegistrarEmpleos(String currentDateandTime) {

        // FirebaseUser user = mAuthEmpleador.getCurrentUser();



        sNombreEmpleoE = editNombreEmpleoE.getText().toString().trim();
        sNombreEmpresaE = editNombreEmpresaE.getText().toString().trim();
        sDireccionE = editDireccionE.getText().toString().trim();
        sEmailE = editEmailE.getText().toString().trim();
        sTelefonoE = editTelefonoE.getText().toString().trim();
        sPaginaWebE = editPaginaWebE.getText().toString().trim();
        sSalarioE = editSalarioE.getText().toString().trim();
        sOtrosDatosE = editOtrosDatosE.getText().toString().trim();

        sHorarioE = tvHorarioE.getText().toString().trim();
        sFechaPublicacionE = currentDateandTime;
        sMostrarIdioma = tvMostrarIdiomasE.getText().toString().trim();

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

//RadioButton
        //sEstadoEmpleoE = "";



        //String Ukey = "klk45";
        //sIdEmpleadorE=Ukey;
        //sIDEmpleo = "444";

        sImagenEmpleoE = "".toString().trim();
        String sPersonasAplicaronE = "".toString().trim();


        if (TextUtils.isEmpty(sNombreEmpleoE)) {
            editNombreEmpleoE.setError("Campo vacío, por favor escriba el nombre del empleo");
            return;
        }
        if (TextUtils.isEmpty(sNombreEmpresaE)) {
            editNombreEmpresaE.setError("Campo vacío, por favor escriba el nombre del empleo");
            return;
        }
        if (TextUtils.isEmpty(sDireccionE)) {
            editDireccionE.setError("Campo vacío, por favor escriba el nombre del empleo");
            return;
        }
        if (TextUtils.isEmpty(sTelefonoE)) {
            editTelefonoE.setError("Campo vacío, por favor escriba el nombre del empleo");
            return;
        }
        if (TextUtils.isEmpty(sEmailE)) {
            editEmailE.setError("Campo vacío, por favor escriba el nombre del empleo");
            return;
        }
        FirebaseUser user = mAuthEmpleador.getCurrentUser();
        //String Ukey = user.getUid();
        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        sIdEmpleadorE=Ukey;
        String IDEmpleo = DBReferenceEmplos.push().getKey();
        Empleos empleos = new Empleos(IDEmpleo, sNombreEmpleoE, sNombreEmpresaE,sDireccionE,sProvinciaE,
                sTelefonoE, sPaginaWebE, sEmailE, sSalarioE, sOtrosDatosE,
                sHorarioE, sFechaPublicacionE, sMostrarIdioma, sAreaE,sFormacionAcademicaE,
                sAnoExpE,sSexoRequeridoE, sRangoEdadE, sJornadaE, sCantidadVacantesE,
                sTipoContratoE, sEstadoEmpleoE, sPersonasAplicaronE, sImagenEmpleoE, sIdEmpleadorE);
        DBReferenceEmplos.child(IDEmpleo).setValue(empleos);

        Toast.makeText(this, "El Empleo se registro exitosamente", Toast.LENGTH_LONG).show();
        limpiarCampor();
        //DBReferenceEmplos.child("empleos").child(IDEmpleo).setValue(empleos);
        //DBReferenceEmplos.child(Ukey).child(IDEmpleo).setValue(empleos);//para registrarlo dentro del usuario que inicio sesion
        //DBReferenceEmplos.child(Ukey).child(IDEmpleo).child("PersonasAplicaron").setValue("1");//para registrarlo dentro del usuario que inicio sesion

    }
}
