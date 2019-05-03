package com.example.findjobsrdv0;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class PantallaRegistrarEmpleos extends AppCompatActivity {

    EditText editNombreEmpleoE,editNombreEmpresaE, editDireccionE,editTelefonoE,
             editPaginaWebE,editEmailE,editSalarioE,editOtrosDatosE;

    TextView tvHorarioE, tvFechaPublicacionE, tvMostrarIDioma;

    Button btnGuardarEmpleoE, btnVerPersonasApliE, btnIdiomasE;

    MaterialSpinner spinAreaE,spinSexoE,spinRangoE,spinJornadaE,spinCantidadVacantesE,spinTipoContratoE,
                    spinProvinciaE,spinFormacionAcademicaE,spinAnoExpE,spinEstadoEmpleoE;

    ImageView fFotoEmpresa;
    Boolean sVerificacionE=false;
    String sIDEmpleo,sNombreEmpleoE, sNombreEmpresaE,sDireccionE, sTelefonoE,sPaginaWebE,sEmailE,sSalarioE,sOtrosDatosE,
            sHorarioE,sFechaPublicacionE, sMostrarIdioma,sAreaE,sSexoRequeridoE,sRangoE,sJornadaE,sCantidadVacantesE,
            sTipoContratoE,sEstadoEmpleoE;

    String[] infoEstadoE = {"Disponible", "No Disponible"};
    String[] infoSexoE = {"Cualquiera", "Masculino","Femenino"};
    String[] infoCantidadVacantesE = {"1","2","3","4","5","6","7","8","10+"};
    String[] infoJornadaE = {"Matutina", "Vespertina","Nocturna","Matutina/Vespertina","Vespertina/Nocturna","Nocturna/Matutina"};
    String[] infoTipoContratoE = {"Fijo", "Temporal"};
    String[] infoIdiomasE = {"Español", "English","Frances","Ruso","Aleman"};



    private DatabaseReference DBReferenceEmplos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registrar_empleos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarprueba);
        setSupportActionBar(toolbar);

        DBReferenceEmplos = FirebaseDatabase.getInstance().getReference("empleos");

        editNombreEmpleoE= (EditText) findViewById(R.id.xmlEditNombreEmpleoE);
        editNombreEmpresaE= (EditText) findViewById(R.id.xmlEditNombreEmpresaE);
        editDireccionE= (EditText) findViewById(R.id.xmlEditDireccionE);
        editTelefonoE= (EditText) findViewById(R.id.xmlEditTelefonoE);
        editEmailE= (EditText) findViewById(R.id.xmlEditEmailE);
        editPaginaWebE= (EditText) findViewById(R.id.xmlEditPaginaWebE);
        editSalarioE= (EditText) findViewById(R.id.xmlEditSalarioE);
        editOtrosDatosE= (EditText) findViewById(R.id.xmlEditOtrosRequisitosE);

        tvHorarioE= (TextView) findViewById(R.id.xmlTiMostrarHorarioE);
        tvFechaPublicacionE= (TextView) findViewById(R.id.xmlTiFechaPublicacionE);
        tvMostrarIDioma= (TextView) findViewById(R.id.xmlTiMostrarIdiomasE);

        btnGuardarEmpleoE= (Button) findViewById(R.id.xmlBtnGuardarEmpleoE);
        btnVerPersonasApliE= (Button) findViewById(R.id.xmlBtnPersonasAplicaron);
        btnIdiomasE= (Button) findViewById(R.id.xmlBtnSeleccionarIdiomasE);

        btnGuardarEmpleoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarEmpleos();
            }
        });

        btnIdiomasE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PantallaRegistrarEmpleos.this);
                builder.setTitle("Idiomas Requerido");
                boolean[] checkedItems = new boolean[]{true, false, false, false, false}; //this will checked the items when user open the dialog
                builder.setMultiChoiceItems(infoIdiomasE, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        //Toast.makeText(PantallaRegistrarEmpleos.this, "Position: " + which + " Value: " + infoIdiomasE[which] + " State: " + (isChecked ? "checked" : "unchecked"), Toast.LENGTH_LONG).show();
                        String hola=infoIdiomasE[which];
                        tvMostrarIDioma.setText(hola);
                        //Log.d("klk",hola);
                    }
                });
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        spinEstadoEmpleoE = (MaterialSpinner) findViewById(R.id.xmlspinEstadoEmpleoF);
        spinEstadoEmpleoE.setItems(infoEstadoE);
        spinEstadoEmpleoE.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                sEstadoEmpleoE = item.toString().trim();
            }
        });

        spinSexoE = (MaterialSpinner) findViewById(R.id.xmlspinSexoRequeridoF);
        spinSexoE.setItems(infoSexoE);
        spinSexoE.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                sSexoRequeridoE = item.toString().trim();
            }
        });

        spinCantidadVacantesE = (MaterialSpinner) findViewById(R.id.xmlspinCantidadVacantesF);
        spinCantidadVacantesE.setItems(infoCantidadVacantesE);
        spinCantidadVacantesE.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                sCantidadVacantesE = item.toString().trim();
            }
        });

        spinJornadaE = (MaterialSpinner) findViewById(R.id.xmlspinJornadaF);
        spinJornadaE.setItems(infoJornadaE);
        spinJornadaE.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                sJornadaE = item.toString().trim();
            }
        });

        spinTipoContratoE = (MaterialSpinner) findViewById(R.id.xmlspinTipoContratoF);
        spinTipoContratoE.setItems(infoTipoContratoE);
        spinTipoContratoE.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                sTipoContratoE = item.toString().trim();
            }
        });





        //Dentro del metodo de guardar -->Abajo

/*
        String Ukey = "1";

        DBReferenceEmplos.child("empleos").child(Ukey).child("nombre_empleo").setValue(sNombreEmpleoE);
        DBReferenceEmplos.child("empleos").child(Ukey).child("nombre_empresa").setValue(sNombreEmpresaE);
        DBReferenceEmplos.child("empleos").child(Ukey).child("direccion").setValue(sDireccionE);
        DBReferenceEmplos.child("empleos").child(Ukey).child("telefono").setValue(sTelefonoE);
        DBReferenceEmplos.child("empleos").child(Ukey).child("email").setValue(sEmailE);
        DBReferenceEmplos.child("empleos").child(Ukey).child("pagina_web").setValue(sPaginaWebE);
        DBReferenceEmplos.child("empleos").child(Ukey).child("salario").setValue(sSalarioE);
        DBReferenceEmplos.child("empleos").child(Ukey).child("otros_datos").setValue(sOtrosDatosE);

        DBReferenceEmplos.child("empleos").child(Ukey).child("horario").setValue(sHorarioE);
        DBReferenceEmplos.child("empleos").child(Ukey).child("fecha_publicacion").setValue(sFechaPublicacionE);
        DBReferenceEmplos.child("empleos").child(Ukey).child("mostrar_idiomas").setValue(sMostrarIdioma);

        DBReferenceEmplos.child("empleos").child(Ukey).child("estado_empleo").setValue(sEstadoEmpleoE);
        DBReferenceEmplos.child("empleos").child(Ukey).child("sexo_requerido").setValue(sSexoRequeridoE);
        DBReferenceEmplos.child("empleos").child(Ukey).child("cantidad_vacantes").setValue(sCantidadVacantesE);
        DBReferenceEmplos.child("empleos").child(Ukey).child("jornada").setValue(sJornadaE);
        DBReferenceEmplos.child("empleos").child(Ukey).child("tipo_contrato").setValue(sTipoContratoE);

        DBReferenceEmplos.child("empleos").child(Ukey).child("verificacion").setValue(sVerificacionE);



*/

        //Dentro del metodo de guardar -->Arriba
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
        String currentDateandTime = simpleDateFormat.format(new Date());
        tvFechaPublicacionE.setText("Ultima Actualizacion: "+currentDateandTime);

    }

    public void RegistrarEmpleos(){

        sNombreEmpleoE = editNombreEmpleoE.getText().toString().trim();
        sNombreEmpresaE = editNombreEmpresaE.getText().toString().trim();
        sDireccionE = editDireccionE.getText().toString().trim();
        sTelefonoE = editTelefonoE.getText().toString().trim();
        sEmailE = editEmailE.getText().toString().trim();
        sPaginaWebE = editPaginaWebE.getText().toString().trim();
        sSalarioE = editSalarioE.getText().toString().trim();
        sOtrosDatosE = editOtrosDatosE.getText().toString().trim();

        sHorarioE = tvHorarioE.getText().toString().trim();
        sFechaPublicacionE = tvFechaPublicacionE.getText().toString().trim();
        sMostrarIdioma = tvMostrarIDioma.getText().toString().trim();
        String sPersonasAplicaronE="".toString().trim();

        String IDEmpleo = DBReferenceEmplos.push().getKey();
        Empleos empleos = new Empleos(IDEmpleo,sNombreEmpleoE,  sNombreEmpresaE,  sDireccionE,
                sTelefonoE,  sPaginaWebE,  sEmailE,  sSalarioE,  sOtrosDatosE,
                sHorarioE,  sFechaPublicacionE,  sMostrarIdioma,  sAreaE,
                sSexoRequeridoE,  sRangoE,  sJornadaE,  sCantidadVacantesE,
                sTipoContratoE,  sEstadoEmpleoE,sPersonasAplicaronE);
        //DBReferenceEmplos.child("empleos").child(IDEmpleo).setValue(empleos);
        DBReferenceEmplos.child(IDEmpleo).setValue(empleos);


    }
}
