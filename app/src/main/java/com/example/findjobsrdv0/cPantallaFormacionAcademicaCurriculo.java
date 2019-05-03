package com.example.findjobsrdv0;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


public class cPantallaFormacionAcademicaCurriculo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView TituloFormacionAcademica;

    Button btnOpenDialog, btnGuardaFormacion;
    TextView textInfo;
    EditText NivelPrimarionombreFA, NivelSecundarionombreFA,NivelsuperiornombreFA, CarreranombreFA;
    String sNivelPrimarionombreFA, sNivelSecundarionombreFA,sNivelsuperiornombreFA, sCarreranombreFA;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_pantalla_formacion_academica_curriculo);

        TituloFormacionAcademica = (TextView) findViewById(R.id.xmlTituloFormacionAcademica);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Chomsky.otf");
        TituloFormacionAcademica.setTypeface(face);

        myRef=FirebaseDatabase.getInstance().getReference();



        btnOpenDialog = (Button)findViewById(R.id.AbrirOtrosCursos);
        btnGuardaFormacion = (Button)findViewById(R.id.xmlbtnAnadirformacionAcademica);

        NivelPrimarionombreFA = (EditText) findViewById(R.id.xmlEditNivelPrimarioNameFA);
        NivelSecundarionombreFA = (EditText) findViewById(R.id.xmlEditNivelSecundarioNameFA);
        NivelsuperiornombreFA = (EditText) findViewById(R.id.xmlEditNivelSuperiorNameFA);
        CarreranombreFA = (EditText) findViewById(R.id.xmlEditCarreraNameFA);

        final String sNivelPrimarionombreFA = NivelPrimarionombreFA.getText().toString().trim();
        final String sNivelSecundarionombreFA = NivelSecundarionombreFA.getText().toString().trim();
        final String sNivelsuperiornombreFA = NivelsuperiornombreFA.getText().toString().trim();
        final String sCarreranombreFA = CarreranombreFA.getText().toString().trim();



        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        ////firebase
        btnGuardaFormacion.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                // Read from the database

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        //String value = dataSnapshot.getValue(String.class);
                        //Log.d("Value is: " , value);

                        String Ukey = "1";

                        myRef.child("Formacion_Academica").child(Ukey).child("Nivel_Primario").setValue(sNivelPrimarionombreFA);
                        myRef.child("Formacion_Academica").child(Ukey).child("Nivel_Secundario").setValue(sNivelSecundarionombreFA);
                        myRef.child("Formacion_Academica").child(Ukey).child("Nivel_superior").setValue(sNivelsuperiornombreFA);
                        myRef.child("Formacion_Academica").child(Ukey).child("Carrera").setValue(sCarreranombreFA);
                        //myRef.child("Formacion_Academica").child(Ukey).child("Otros_Cursos").setValue("");


                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Failed to read value.", error.toException());
                    }
                });

            }
        });

        ////firebase





    }

    private void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(cPantallaFormacionAcademicaCurriculo.this);
        View subView = inflater.inflate(R.layout.otroscursoslayout, null);
        final EditText InstitucionOC = (EditText)subView.findViewById(R.id.xmlEditNombreInstitucionOC);
        final EditText AñoOC = (EditText)subView.findViewById(R.id.xmlEditAñoOC);
        final EditText AreaOC = (EditText)subView.findViewById(R.id.xmlEditAreaOC);


        final Button btnGuardarCurso = (Button)subView.findViewById(R.id.xmlbtnGuardarCursosOC);

        final MaterialBetterSpinner spnTipoEstudio = subView.findViewById(R.id.xmlspinTipoEstudioOC);
        ArrayAdapter<CharSequence> adapterTipoEstudio = ArrayAdapter.createFromResource(this,
                R.array.FormacionAcademica, android.R.layout.simple_spinner_item);
        adapterTipoEstudio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoEstudio.setAdapter(adapterTipoEstudio);
        spnTipoEstudio.setOnItemSelectedListener(this);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cursos Realizados");
        builder.setMessage("Añadir otros cursos realizados ");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        btnGuardarCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NombreInstitucion = InstitucionOC.getText().toString().trim();
                String Año = AñoOC.getText().toString().trim();
                String Area = AreaOC.getText().toString().trim();

                //String TipoEstudio="l";// = spnTipoEstudio.toString();
                //String TipoEstudio = spnTipoEstudio.getItemSelectedItem().toString().trim();

                //Log.d("Probando",TipoEstudio);
                String Ukey = "2";

                myRef.child("Formacion_Academica").child(Ukey).child("Otros_Cursos").child(Ukey).child("Institucion").setValue(NombreInstitucion);
                myRef.child("Formacion_Academica").child(Ukey).child("Otros_Cursos").child(Ukey).child("Año").setValue(Año);
                myRef.child("Formacion_Academica").child(Ukey).child("Otros_Cursos").child(Ukey).child("Area").setValue(Area);


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(cPantallaFormacionAcademicaCurriculo.this, "Cancel", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String TipoEstudio = parent.getItemAtPosition(position).toString();
        //tv.setText(item);
        Log.d("Probando",TipoEstudio);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}
