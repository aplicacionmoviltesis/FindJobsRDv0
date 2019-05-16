package com.example.findjobsrdv0;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PantallaRegistrarEmpleos extends AppCompatActivity{

    EditText editNombreEmpleoE,editNombreEmpresaE, editDireccionE,editTelefonoE,
             editPaginaWebE,editEmailE,editSalarioE,editOtrosDatosE;

    TextView tvHorarioE, tvFechaPublicacionE, tvMostrarIDioma;

    Button btnVerPersonasApliE, btnIdiomasE;

    Spinner spinAreaE,spinSexoE,spinRangoE,spinJornadaE,spinCantidadVacantesE,spinTipoContratoE,
                    spinProvinciaE,spinFormacionAcademicaE,spinAnoExpE,spinEstadoEmpleoE;

    ImageView fFotoEmpresa;
    Boolean sVerificacionE=false;

    String sIDEmpleo,sNombreEmpleoE, sNombreEmpresaE,sProvinciaE,sDireccionE, sTelefonoE,sPaginaWebE,sEmailE,sSalarioE,sOtrosDatosE,
            sHorarioE,sFechaPublicacionE, sMostrarIdioma,sAreaE,sSexoRequeridoE,sRangoE,sJornadaE,sCantidadVacantesE,sIdEmpleadorE,
            sTipoContratoE,sEstadoEmpleoE,sImagenEmpleoE;

    String[] infoEstadoE = {"Disponible", "No Disponible"};
    String[] infoSexoE = {"Cualquiera", "Masculino","Femenino"};
    String[] infoCantidadVacantesE = {"1","2","3","4","5","6","7","8","10+"};
    String[] infoJornadaE = {"Matutina", "Vespertina","Nocturna","Matutina/Vespertina","Vespertina/Nocturna","Nocturna/Matutina"};
    String[] infoTipoContratoE = {"Fijo", "Temporal"};

    private DatabaseReference DBReferenceEmplos;

    Button mOrder;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();


    //////////////////////////////////
    SearchableSpinner searchableSpinner;

    // FirebaseDatabase mFirebaseDatabase;
    DatabaseReference provinciasRef;

    //IFirebaseLoadDone iFirebaseLoadDone;

    //List<Provincia> provincias;

    boolean IsFirstTimeClick=true;

    /////////////////////////////////

    private FirebaseAuth mAuthEmpleador;


    @SuppressLint("WrongViewCast")
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

        ImageButton btnGuardarEmpleoE= (ImageButton) findViewById(R.id.xmlBtnGuardarEmpleoE);
        btnVerPersonasApliE= (Button) findViewById(R.id.xmlBtnPersonasAplicaron);
        btnIdiomasE= (Button) findViewById(R.id.xmlBtnSeleccionarIdiomasE);

        searchableSpinner = (SearchableSpinner)findViewById(R.id.searchable_spinner);


        btnGuardarEmpleoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarEmpleos();
            }
        });

        listItems = getResources().getStringArray(R.array.sIdiomas);
        checkedItems = new boolean[listItems.length];

        btnIdiomasE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(PantallaRegistrarEmpleos.this);
                mBuilder.setTitle("Elegir Idiomas Requerido");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                        if(isChecked){
                            mUserItems.add(position);
                        }else{
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
                        tvMostrarIDioma.setText(item);
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
                            tvMostrarIDioma.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
/*
        spinEstadoEmpleoE = (MaterialSpinner) findViewById(R.id.xmlspinEstadoEmpleoF);
        spinEstadoEmpleoE.setItems(infoEstadoE);
        spinEstadoEmpleoE.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                sEstadoEmpleoE = item.toString().trim();
            }
        });

        spinSexoE = (Spinner) findViewById(R.id.xmlspinSexoRequeridoF);
        spinSexoE.setItems(infoSexoE);
        spinSexoE.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                sSexoRequeridoE = item.toString().trim();
            }
        });

        spinCantidadVacantesE = (Spinner) findViewById(R.id.xmlspinCantidadVacantesF);
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

*/

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
        String currentDateandTime = simpleDateFormat.format(new Date());
        tvFechaPublicacionE.setText("Ultima Actualizacion: "+currentDateandTime);

        //FirebaseUser user = mAuthEmpleador.getCurrentUser();
        //String Ukey = user.getUid();


        /*
        ///////////////////////////////////////////////////////////

        //searchableSpinner = (SearchableSpinner)findViewById(R.id.searchable_spinner);

        searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!IsFirstTimeClick){
                    Provincia provincia= provincias.get(position);

                    String klk= provincia.getName();
                    Log.d("klk",klk);
                }
                else {

                    IsFirstTimeClick=false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //  mFirebaseDatabase = FirebaseDatabase.getInstance();
        //  provinciasRef = mFirebaseDatabase.getReference("Spinner");

        provinciasRef = FirebaseDatabase.getInstance().getReference("empleos");

        iFirebaseLoadDone = this;

        provinciasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Provincia> provincias = new ArrayList<>();
                for (DataSnapshot provinciaSnapShot:dataSnapshot.getChildren())
                {
                    provincias.add(provinciaSnapShot.getValue(Provincia.class));


                }

                iFirebaseLoadDone.onFirebaseLoadSuccess(provincias);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());

            }
        } );





        /////////////////////////////////////////////////////////
        */
    }
/*
    ////////////////////////////////////////////////////////////////////////


    @Override
    public void onFirebaseLoadSuccess(List<Provincia> provinciaList) {
        provincias = provinciaList;

        List<String> name_list = new ArrayList<>();

        for (Provincia provincia : provinciaList)
            name_list.add(provincia.getName());

        ArrayAdapter<String> adapter = new ArrayAdapter<>( this,android.R.layout.simple_list_item_1,name_list);
        searchableSpinner.setAdapter(adapter);



    }

    @Override
    public void onFirebaseLoadFailed(String message) {

    }


    ///////////////////////////////////////////////////////////////////////
*/
    public void RegistrarEmpleos(){

       // FirebaseUser user = mAuthEmpleador.getCurrentUser();
        //String Ukey = "klk45";

        //sIDEmpleo = "444";
        sNombreEmpleoE = editNombreEmpleoE.getText().toString().trim();
        sNombreEmpresaE = editNombreEmpresaE.getText().toString().trim();
        sProvinciaE = "La Vega";
        sDireccionE = editDireccionE.getText().toString().trim();
        sTelefonoE = editTelefonoE.getText().toString().trim();
        sPaginaWebE = editPaginaWebE.getText().toString().trim();
        sEmailE = editEmailE.getText().toString().trim();
        sSalarioE = editSalarioE.getText().toString().trim();
        sOtrosDatosE = editOtrosDatosE.getText().toString().trim();
        sHorarioE = tvHorarioE.getText().toString().trim();
        sFechaPublicacionE = tvFechaPublicacionE.getText().toString().trim();
        sMostrarIdioma = tvMostrarIDioma.getText().toString().trim();

        sAreaE = "Contabilidad";
        sSexoRequeridoE = "Hombre";
        sRangoE = "25-35";
        sJornadaE = "Matutina/Vespertina";
        sCantidadVacantesE = "3";
        sTipoContratoE = "Temporal";
        sEstadoEmpleoE = "Disponible";
        sImagenEmpleoE="".toString().trim();
        //sIdEmpleadorE=Ukey;

        String sPersonasAplicaronE="".toString().trim();


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
        //FirebaseUser user = mAuthEmpleador.getCurrentUser();
        //String Ukey = user.getUid();
        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String IDEmpleo = DBReferenceEmplos.push().getKey();
        Empleos empleos = new Empleos(IDEmpleo,sNombreEmpleoE,  sNombreEmpresaE,sProvinciaE,  sDireccionE,
                sTelefonoE,  sPaginaWebE,  sEmailE,  sSalarioE,  sOtrosDatosE,
                sHorarioE,  sFechaPublicacionE,  sMostrarIdioma,  sAreaE,
                sSexoRequeridoE,  sRangoE,  sJornadaE,  sCantidadVacantesE,
                sTipoContratoE,  sEstadoEmpleoE,sPersonasAplicaronE,sImagenEmpleoE,sIdEmpleadorE);
        DBReferenceEmplos.child(IDEmpleo).setValue(empleos);

        //DBReferenceEmplos.child("empleos").child(IDEmpleo).setValue(empleos);
        //DBReferenceEmplos.child(Ukey).child(IDEmpleo).setValue(empleos);//para registrarlo dentro del usuario que inicio sesion

    }
}
