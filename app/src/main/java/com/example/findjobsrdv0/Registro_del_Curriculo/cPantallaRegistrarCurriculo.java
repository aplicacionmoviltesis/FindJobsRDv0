package com.example.findjobsrdv0.Registro_del_Curriculo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findjobsrdv0.DatePickerFragment;
import com.example.findjobsrdv0.MultipleSelectionSpinner;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.Curriculos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class cPantallaRegistrarCurriculo extends AppCompatActivity {

    EditText etNombre, etApellido, etCedula, etEmail, etTelefono, etCelular, etDireccion, etOcupacion, etHabilidades;
    Button btnIdiomasc;
    SearchableSpinner Provincia, spinEstadoCivil, spinGradoMayor, spinEstadoActual;

    TextView mEtxtFecha, mEtxtIdioma;

    private DatabaseReference DBReferenceCurriculos;
    String sexo;

    TextView muestraidioma;

    FirebaseAuth mAuth;

    String cCodigoId, cIdCurriculo, nombre, apellido, cedula, email, telefono, celular, provincia, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual, habilidades, fecha;

    private Button btnFormacionAcademica, btnReferenciCurriculo, btnExperienciaLaboralCurriculo,btnOtrosCursosCurriculo, bntIdioma;

    Button mOrder;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    /////Spinner Provincia

    private Spinner spinProvinciaCurriculo;
    private DatabaseReference provinciasRefCurriculo;
    private boolean IsFirstTimeClick = true;
    private String sProvinciaCurriculoB;

/////Spinner Provincia

    String userActivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_pantalla_registrar_curriculo );


        DBReferenceCurriculos = FirebaseDatabase.getInstance().getReference( "Curriculos" );
        mAuth = FirebaseAuth.getInstance();
        userActivo = FirebaseAuth.getInstance().getCurrentUser().getUid();

        etNombre = (EditText) findViewById( R.id.etnombre );
        etApellido = (EditText) findViewById( R.id.etapellido );
        etCedula = (EditText) findViewById( R.id.etcedula );
        etEmail = (EditText) findViewById( R.id.etemail );
        etTelefono = (EditText) findViewById( R.id.ettelefono );
        etCelular = (EditText) findViewById( R.id.etcelula );
        etDireccion = (EditText) findViewById( R.id.etdireccion );
        etOcupacion = (EditText) findViewById( R.id.etocupacion );
        etHabilidades = (EditText) findViewById( R.id.ethabilidades );
        //Provincia = (SearchableSpinner) findViewById( R.id.spinnerprovincias );
        spinEstadoCivil = (SearchableSpinner) findViewById( R.id.spinnerestadocivil );
        spinGradoMayor = (SearchableSpinner) findViewById( R.id.spinnergradomayor );
        spinEstadoActual = (SearchableSpinner) findViewById( R.id.spinnerestadoactual );

        mEtxtFecha = (TextView) findViewById( R.id.tv );
        mEtxtIdioma = (TextView) findViewById( R.id.tvop );


        btnIdiomasc = (Button) findViewById( R.id.xmlBtnIdioma );

        /////Spinner Provincia
        CargarCurriculoActualizar(userActivo);





        provinciasRefCurriculo = FirebaseDatabase.getInstance().getReference();
        spinProvinciaCurriculo = (Spinner) findViewById(R.id.spinnerprovincias);

        spinProvinciaCurriculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sProvinciaCurriculoB = spinProvinciaCurriculo.getSelectedItem().toString();
                    Log.d("valorSpinProv", sProvinciaCurriculoB);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        provinciasRefCurriculo.child("provincias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListProvinciasCurri = new ArrayList<String>();
                for (DataSnapshot provinciaSnapshot : dataSnapshot.getChildren()) {
                    String provinciaName = provinciaSnapshot.child("Nombre_Provincia").getValue(String.class);
                    ListProvinciasCurri.add(provinciaName);
                }

                ArrayAdapter<String> provinciasAdapterCurriculo = new ArrayAdapter<String>(cPantallaRegistrarCurriculo.this, android.R.layout.simple_spinner_item, ListProvinciasCurri);
                provinciasAdapterCurriculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinProvinciaCurriculo.setAdapter(provinciasAdapterCurriculo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/////Spinner Provincia

        listItems = getResources().getStringArray( R.array.InfoIdiomas );
        checkedItems = new boolean[listItems.length];


        final String IdCurriculo = DBReferenceCurriculos.push().getKey();
        Button btnregistrarC = findViewById( R.id.xmlBtnRegistrarDatosGC );
        btnregistrarC.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarcurriculo(IdCurriculo);
            }
        } );


        btnFormacionAcademica = (Button) findViewById( R.id.xmlBtnFormacionAcademicaC );
        btnFormacionAcademica.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( cPantallaRegistrarCurriculo.this, cPantallaFormacionAcademicaCurriculo.class );
                intent.putExtra( "DetalleFormacionAcademicaID", IdCurriculo );
                //  Log.d( "idcurriculo",IdCurriculo );
                startActivity( intent );
            }
        } );
        //btnFormacionAcademica.setEnabled(false);

        btnOtrosCursosCurriculo = ( Button) findViewById( R.id.AbrirOtrosCursos );
        btnOtrosCursosCurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( cPantallaRegistrarCurriculo.this, cPantallaOtrosCursos.class );
                intent.putExtra( "DetalleOtrosCursosID", IdCurriculo );
                //  Log.d( "idcurriculo",IdCurriculo );
                startActivity( intent );
            }
        } );
        //btnOtrosCursosCurriculo.setEnabled(false);

        btnExperienciaLaboralCurriculo = (Button) findViewById( R.id.xmlBntExperienciaLaboralCurriculo );
        btnExperienciaLaboralCurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( cPantallaRegistrarCurriculo.this, cPantallaExperienciaLaboralCurriculo.class );
                intent.putExtra( "DetalleExperienciaLaboralID", IdCurriculo );
                //  Log.d( "idcurriculo",IdCurriculo );
                startActivity( intent );
            }
        } );
        //btnExperienciaLaboralCurriculo.setEnabled(false);


        btnReferenciCurriculo = (Button) findViewById( R.id.xmlBtnReferenciaCurriculoC );
        btnReferenciCurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( cPantallaRegistrarCurriculo.this, cPantallaReferenciasCurriculo.class );
                intent.putExtra( "DetalleReferenciasID", IdCurriculo );
                //  Log.d( "idcurriculo",IdCurriculo );
                startActivity( intent );
            }
        } );
        //btnReferenciCurriculo.setEnabled(false);



//-------------------------------------------------------------------------------------------------------------------------------






        btnFormacionAcademica = findViewById( R.id.xmlBtnFormacionAcademicaC );
        btnReferenciCurriculo = findViewById( R.id.xmlBtnReferenciaCurriculoC );
        btnExperienciaLaboralCurriculo = findViewById( R.id.xmlBntExperienciaLaboralCurriculo );
        bntIdioma = findViewById( R.id.xmlBtnIdioma );


        muestraidioma = findViewById( R.id.tvop );


        btnIdiomasc.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder( cPantallaRegistrarCurriculo.this );
                mBuilder.setTitle( "Elegir Idiomas Requerido" );
                mBuilder.setMultiChoiceItems( listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                        if (isChecked) {
                            mUserItems.add( position );
                        } else {
                            mUserItems.remove( (Integer.valueOf( position )) );
                        }
                    }
                } );

                mBuilder.setCancelable( false );
                mBuilder.setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get( i )];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        mEtxtIdioma.setText( item );
                    }
                } );

                mBuilder.setNegativeButton( "Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                } );

                mBuilder.setNeutralButton( "Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mEtxtIdioma.setText( "" );
                        }
                    }
                } );

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        } );
Log.d("useer",userActivo);
        CargarCurriculoActualizar(userActivo);
    }

    public void onButtonClicked(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show( getFragmentManager(), "Date Picker" );
    }

    public void CargarCurriculoActualizar(String sCurriculoId){
        DBReferenceCurriculos.orderByChild("cIdBuscador").equalTo(sCurriculoId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Curriculos curriculos = dataSnapshot.getValue(Curriculos.class);
                Log.d("holap", String.valueOf(dataSnapshot));

                //Picasso.get().load(empleos.getsImagenEmpleoE()).into(ImageViewAE);

                etNombre.setText(curriculos.getNombre());
                etApellido.setText(curriculos.getApellido());
                etCedula.setText(curriculos.getCedula());
                etEmail.setText(curriculos.getEmail());
                etTelefono.setText(curriculos.getTelefono());
                etCelular.setText(curriculos.getCelular());
                etDireccion.setText(curriculos.getDireccion());
                etOcupacion.setText(curriculos.getEmail());
                mEtxtIdioma.setText(curriculos.getTelefono());
                mEtxtFecha.setText(curriculos.getCelular());
                etHabilidades.setText(curriculos.getDireccion());

                spinProvinciaCurriculo.setSelection(obtenerPosicionItem(spinProvinciaCurriculo, curriculos.getProvincia()));
                spinGradoMayor.setSelection(obtenerPosicionItem(spinGradoMayor, curriculos.getGradomayor()));
                spinEstadoCivil.setSelection(obtenerPosicionItem(spinEstadoCivil, curriculos.getEstadoCivil()));
                spinEstadoActual.setSelection(obtenerPosicionItem(spinEstadoActual, curriculos.getEstadoactual()));



                RadioGroup RGsexo = (RadioGroup) findViewById(R.id.radiobuttonsexo);
                RGsexo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.first:
                                sexo = "Masculino";
                                //Log.d("Valorestado",sEstadoEmpleoAE);
                                break;
                            case R.id.second:
                                sexo = "Femenino";
                                //Log.d("Valorestado",sEstadoEmpleoAE);
                                break;
                        }
                        Log.d("Valorsexo", sexo);
                    }
                });

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
    public void limpiarCampor(){
        etNombre.setText("");
        etApellido.setText("");
        etCedula.setText("");
        etEmail.setText("");
        etCelular.setText("");
        etTelefono.setText("");
        etDireccion.setText("");

        etOcupacion.setText("");
        mEtxtIdioma.setText("");
        etHabilidades.setText("");
        mEtxtFecha.setText("");
    }

    public void ActivarCampor(){
        btnFormacionAcademica.setEnabled(true);
        btnOtrosCursosCurriculo.setEnabled(true);
        btnExperienciaLaboralCurriculo.setEnabled(true);
        btnReferenciCurriculo.setEnabled(true);


    }

    public void registrarcurriculo(String IdCurriculo ) {


        nombre = etNombre.getText().toString().trim().toLowerCase();
        apellido = etApellido.getText().toString().trim();
        cedula = etCedula.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        celular = etCelular.getText().toString().trim();
        telefono = etTelefono.getText().toString().trim();
        //provincia = Provincia.getSelectedItem().toString();
        estadoCivil = spinEstadoCivil.getSelectedItem().toString();
        direccion = etDireccion.getText().toString().trim();
        ocupacion = etOcupacion.getText().toString().trim();
        idioma = mEtxtIdioma.getText().toString().trim();
        gradomayor = spinGradoMayor.getSelectedItem().toString();
        estadoactual = spinEstadoActual.getSelectedItem().toString();
        habilidades = etHabilidades.getText().toString().trim();
        fecha = mEtxtFecha.getText().toString().trim();

        if (TextUtils.isEmpty( nombre )) {
            etNombre.setError( "Campo vacío, por favor escriba el nombre " );
            return;
        }




    /*    if (TextUtils.isEmpty( cApellido )) {
            etApellido.setError( "Campo vacío, por favor escriba el apellido" );
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

*/


        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String cIdBuscardor = Ukey;




        Curriculos curriculos = new Curriculos( IdCurriculo, cIdBuscardor, nombre, apellido, cedula, email, telefono, celular, sProvinciaCurriculoB, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual, habilidades, fecha );


        DBReferenceCurriculos.child( IdCurriculo ).setValue( curriculos );

        Toast.makeText(this, "El Curriculo se registro exitosamente", Toast.LENGTH_LONG).show();
        ActivarCampor();
        limpiarCampor();

        //DBReferenceCurriculos.child("empleos").child(IDEmpleo).setValue(empleos);
        //   DBReferenceCurriculos.child(Ukey).setValue(curriculos);//para registrarlo dentro del usuario que inicio sesion


    }
}
