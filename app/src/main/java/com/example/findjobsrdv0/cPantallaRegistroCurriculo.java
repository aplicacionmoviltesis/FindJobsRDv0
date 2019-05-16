package com.example.findjobsrdv0;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.findjobsrdv0.Modelo.Curriculos;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class cPantallaRegistroCurriculo extends AppCompatActivity  {

    EditText etNombre, etApellido, etCedula, etEmail, etTelefono, etCelular, etDireccion, etOcupacion, etHabilidades;
    Button btnregistrarC;
    String Provincia, estadoCivil, GradoMayor, EstadoActual;

    private DatabaseReference Curriculo;



    TextView muestraidioma;


    String[] SPINNERLIST = {"La Vega", "Salcedo", "Espaillat", "Santo Domingo", "Santiago"};

    String[] SPINNERLIST1 = {"soltero", "casado", "Spinner Using Material Library", "Material Spinner Example"};
    String[] SPINNERLIST2 = {"Maestria","Doctorado"};
    String[] SPINNERLIST3 = {"Disponible", "Ocupado"};


    private Button btnFormacionAcademica, btnReferenciCurriculo,btnExperienciaLaboralCurriculo, bntIdioma;

    private String[] listItems = {"Español", "Ingles", "Frances", "Mandarin", "Ruso"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_pantalla_registro_curriculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final MaterialSpinner provincia = (MaterialSpinner) findViewById(R.id.spinnerprovincias);
        provincia.setItems(SPINNERLIST);
        provincia.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                Provincia = item.toString().trim();

                Log.d( "Provincia", Provincia );
            }
        });

        final MaterialSpinner estadocivil = (MaterialSpinner) findViewById(R.id.spinnerestadocivil);
        estadocivil.setItems(SPINNERLIST1);
        estadocivil.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                estadoCivil = item.toString().trim();

                Log.d( "EstadoCivil", estadoCivil );
            }
        });

        final MaterialSpinner gradomayor = (MaterialSpinner) findViewById(R.id.spinnergradomayor);
        gradomayor.setItems(SPINNERLIST2);
        gradomayor.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                GradoMayor = item.toString().trim();

                Log.d( "GradoMayor", GradoMayor );
            }
        });

        final MaterialSpinner estadoactual = (MaterialSpinner) findViewById(R.id.spinnerestadoactual);
        estadoactual.setItems(SPINNERLIST3);
        estadoactual.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                EstadoActual = item.toString().trim();

                Log.d( "EstadoActual", EstadoActual );
            }
        });


//----------------------------------------------------------------------------------------------------------------------------


        Curriculo = FirebaseDatabase.getInstance().getReference( "Curriculo" );

        etNombre = (EditText)findViewById( R.id.etnombre );
        etApellido = (EditText)findViewById( R.id.etapellido );
        etCedula = (EditText)findViewById( R.id.etcedula );
        etEmail = (EditText)findViewById( R.id.etemail );
        etTelefono = (EditText)findViewById( R.id.ettelefono );
        etCelular = (EditText)findViewById( R.id.etcelula );
        etDireccion = (EditText)findViewById( R.id.etdireccion );
        etOcupacion = (EditText)findViewById( R.id.etocupacion );
        etHabilidades = (EditText)findViewById( R.id.ethabilidades );



        btnregistrarC= findViewById(R.id.xmlBtnRegistrarDatosGC);

        btnregistrarC.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarcurriculo();

            }
        } );
//-------------------------------------------------------------------------------------------------------------------------------


        btnFormacionAcademica = findViewById(R.id.xmlBtnFormacionAcademicaC);
        btnReferenciCurriculo = findViewById(R.id.xmlBtnReferenciaCurriculoC);
        btnExperienciaLaboralCurriculo = findViewById(R.id.xmlBntExperienciaLaboralCurriculo);
        bntIdioma = findViewById(R.id.xmlBtnIdioma);


        muestraidioma = findViewById(R.id.tvop);


        btnFormacionAcademica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cPantallaRegistroCurriculo.this, cPantallaFormacionAcademicaCurriculo.class);
                startActivity(intent);
            }
        });

        btnReferenciCurriculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cPantallaRegistroCurriculo.this, cPantallaReferenciasCurriculos.class);
                startActivity(intent);
            }
        });

        btnExperienciaLaboralCurriculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cPantallaRegistroCurriculo.this, cPantallaExperienciaLaboralCurriculo.class);
                startActivity(intent);



            }
        });

        bntIdioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(cPantallaRegistroCurriculo.this);
                builder.setTitle("Choose items");

                boolean[] checkedItems = new boolean[]{true, false, false, false, false}; //this will checked the items when user open the dialog
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {


                        Toast.makeText(cPantallaRegistroCurriculo.this, "Position: " + which + " Value: " + listItems[which] + " State: " + (isChecked ? "checked" : "unchecked"), Toast.LENGTH_LONG).show();
                        String hola=listItems[which];
                        muestraidioma.setText(hola);

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


    }

    public void onButtonClicked(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"Date Picker");
    }



//------------------------------------------------------------------------------------------------------------------------------------------------
    public void registrarcurriculo(){
        String nombre=etNombre.getText().toString();
        String apellido=etApellido.getText().toString();
        String cedula=etCedula.getText().toString();
        String email=etEmail.getText().toString();
        String telefono=etTelefono.getText().toString();
        String celular=etCedula.getText().toString();
        String direccion=etDireccion.getText().toString();
        String ocupacion=etOcupacion.getText().toString();
        String habilidades=etHabilidades.getText().toString();


        if (!TextUtils.isEmpty( nombre )){
            String id = Curriculo.push().getKey();
            Curriculos leccion = new Curriculos( id, nombre, apellido, cedula, email, telefono, celular, Provincia, estadoCivil,   direccion,ocupacion,  GradoMayor, EstadoActual, habilidades   );
            Curriculo.child( "Leccion" ).child( id ).setValue( leccion );

            Toast.makeText( this, "Curriculo registrado", Toast.LENGTH_SHORT ).show();

        }
        else{
            Toast.makeText( this, "No se pudo registrar curriculo", Toast.LENGTH_SHORT ).show();

        }
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------------


}




