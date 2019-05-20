package com.example.findjobsrdv0.Registro_del_Curriculo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.findjobsrdv0.DatePickerFragment;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.Curriculos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class cPantallaRegistrarCurriculo extends AppCompatActivity {

    EditText etNombre, etApellido, etCedula, etEmail, etTelefono, etCelular, etDireccion, etOcupacion, etHabilidades;
    Button btnIdiomasc;
    SearchableSpinner Provincia, EstadoCivil, GradoMayor, EstadoActual;

    TextView mEtxtFecha, mEtxtIdioma;

    private DatabaseReference DBReferenceCurriculos;

    TextView muestraidioma;

    FirebaseAuth mAuth;

    String cCodigoId, cIdCurriculo, nombre, apellido, cedula, email, telefono, celular, provincia, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual, habilidades, fecha;

    private Button btnFormacionAcademica, btnReferenciCurriculo, btnExperienciaLaboralCurriculo,btnOtrosCursosCurriculo, bntIdioma;

    Button mOrder;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_c_pantalla_registrar_curriculo );


        DBReferenceCurriculos = FirebaseDatabase.getInstance().getReference( "Curriculos" );
        mAuth = FirebaseAuth.getInstance();

        etNombre = (EditText) findViewById( R.id.etnombre );
        etApellido = (EditText) findViewById( R.id.etapellido );
        etCedula = (EditText) findViewById( R.id.etcedula );
        etEmail = (EditText) findViewById( R.id.etemail );
        etTelefono = (EditText) findViewById( R.id.ettelefono );
        etCelular = (EditText) findViewById( R.id.etcelula );
        etDireccion = (EditText) findViewById( R.id.etdireccion );
        etOcupacion = (EditText) findViewById( R.id.etocupacion );
        etHabilidades = (EditText) findViewById( R.id.ethabilidades );
        Provincia = (SearchableSpinner) findViewById( R.id.spinnerprovincias );
        EstadoCivil = (SearchableSpinner) findViewById( R.id.spinnerestadocivil );
        GradoMayor = (SearchableSpinner) findViewById( R.id.spinnergradomayor );
        EstadoActual = (SearchableSpinner) findViewById( R.id.spinnerestadoactual );

        mEtxtFecha = (TextView) findViewById( R.id.tv );
        mEtxtIdioma = (TextView) findViewById( R.id.tvop );


        btnIdiomasc = (Button) findViewById( R.id.xmlBtnIdioma );

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

    }

    public void onButtonClicked(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show( getFragmentManager(), "Date Picker" );
    }

    public void registrarcurriculo(String IdCurriculo ) {


        nombre = etNombre.getText().toString().trim();
        apellido = etApellido.getText().toString().trim();
        cedula = etCedula.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        celular = etCelular.getText().toString().trim();
        telefono = etTelefono.getText().toString().trim();
        provincia = Provincia.getSelectedItem().toString();
        estadoCivil = EstadoCivil.getSelectedItem().toString();
        direccion = etDireccion.getText().toString().trim();
        ocupacion = etOcupacion.getText().toString().trim();
        idioma = mEtxtIdioma.getText().toString().trim();
        gradomayor = GradoMayor.getSelectedItem().toString();
        estadoactual = EstadoActual.getSelectedItem().toString();
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




        Curriculos curriculos = new Curriculos( IdCurriculo, cIdBuscardor, nombre, apellido, cedula, email, telefono, celular, provincia, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual, habilidades, fecha );


        DBReferenceCurriculos.child( IdCurriculo ).setValue( curriculos );



        //DBReferenceCurriculos.child("empleos").child(IDEmpleo).setValue(empleos);
        //   DBReferenceCurriculos.child(Ukey).setValue(curriculos);//para registrarlo dentro del usuario que inicio sesion


    }
}
