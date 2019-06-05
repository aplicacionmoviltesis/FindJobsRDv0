package com.example.findjobsrdv0.Registro_del_Curriculo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findjobsrdv0.DatePickerFragment;
import com.example.findjobsrdv0.PantallaBuscarEmpleos;
import com.example.findjobsrdv0.Provincias;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.Curriculos;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class cPantallaRegistrarCurriculo extends AppCompatActivity {

    EditText etNombre, etApellido, etCedula, etEmail, etTelefono, etCelular,
            etDireccion, etOcupacion, etHabilidades;

    Button btnIdiomasc;
    SearchableSpinner Provincia, spinEstadoCivil, spinGradoMayor, spinEstadoActual;

    RadioButton RdbDisponibleC, RdbNoDisponibleC;


    TextView mEtxtFecha, mEtxtIdioma;


    TextView muestraidioma;

    String CodigoCurriculo;

    FirebaseAuth mAuth;

    String nombre, apellido, cedula, email, telefono, celular, provincia, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual, sexo, habilidades, fecha;
    // cCodigoId, cIdCurriculo, imagen,
    private Button btnFormacionAcademica, btnReferenciCurriculo, btnExperienciaLaboralCurriculo, btnOtrosCursosCurriculo, bntIdioma;

    Button mOrder;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

/////Spinner Provincia

    SearchableSpinner spinProvinciaCurriculo;
    DatabaseReference provinciasRefCurriculo;
    List<Provincias> provincias;
    boolean IsFirstTimeClick = true;

/////Spinner Provincia

    String userActivo;

//---------------imagen en el curriculo----------------------------------------------------------------------------------------------------------------

    private String mStoragePath = "Imagenes Curriculo/";

    ImageView imageViewcurriculo;

    Uri mFilePathUri;

    StorageReference mStorageReference;

    DatabaseReference databaseReferenceCurrilo;
    FirebaseDatabase database;

    ProgressDialog mProgressDialog;

    int IMAGE_REQUEST_CODE = 5;
    DatabaseReference bbdd;


//----------------imagen en el curriculo----------------------------------------------------------------------------------------------------------------


  //  private String cIdBuscardor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_c_pantalla_registrar_curriculo );

       // userActivo = FirebaseAuth.getInstance().getCurrentUser().getUid();

        bbdd = FirebaseDatabase.getInstance().getReference();
//---------------imagen en el curriculo----------------------------------------------------------------------------------------------------------------


        imageViewcurriculo = (ImageView) findViewById( R.id.xmlImageViewimagenenelCurriculo );
        imageViewcurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType( "image/*" );
                galleryIntent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( galleryIntent, "Seleccionar Imagen" ), IMAGE_REQUEST_CODE );

            }
        } );

        mStorageReference = FirebaseStorage.getInstance().getReference();

        //mDatabaseReference = FirebaseDatabase.getInstance().getReference( "Curriculos" );

        mProgressDialog = new ProgressDialog( cPantallaRegistrarCurriculo.this );

        database = FirebaseDatabase.getInstance();
        databaseReferenceCurrilo = database.getReference( "Curriculos" );

//----------------imagen en el curriculo----------------------------------------------------------------------------------------------------------------


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

        mEtxtFecha = (TextView) findViewById( R.id.tv );
        mEtxtIdioma = (TextView) findViewById( R.id.tvop );


        RdbDisponibleC = (RadioButton) findViewById( R.id.maculinoCurri );

        RdbNoDisponibleC = (RadioButton) findViewById( R.id.femeninaCurri );


        btnIdiomasc = (Button) findViewById( R.id.xmlBtnIdioma );

        /////Spinner Provincia
        //CargarCurriculoActualizar(userActivo);


        provinciasRefCurriculo = FirebaseDatabase.getInstance().getReference();
        spinProvinciaCurriculo = (SearchableSpinner) findViewById( R.id.spinnerprovincias );

        spinProvinciaCurriculo.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    provincia = spinProvinciaCurriculo.getSelectedItem().toString();
                    Log.d( "valorSpinProv", provincia );
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        } );

        provinciasRefCurriculo.child( "provincias" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListProvinciasCurri = new ArrayList<String>();
                for (DataSnapshot provinciaSnapshot : dataSnapshot.getChildren()) {
                    String provinciaName = provinciaSnapshot.child( "Nombre_Provincia" ).getValue( String.class );
                    ListProvinciasCurri.add( provinciaName );
                }

                ArrayAdapter<String> provinciasAdapterCurriculo = new ArrayAdapter<String>( cPantallaRegistrarCurriculo.this, android.R.layout.simple_spinner_item, ListProvinciasCurri );
                provinciasAdapterCurriculo.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spinProvinciaCurriculo.setAdapter( provinciasAdapterCurriculo );
                spinProvinciaCurriculo.setTitle("Seleccionar Provincia");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

/////Spinner Provincia
//----------------------Spinner estado civil-------------------------------------------------------------------------------------

        spinEstadoCivil = (SearchableSpinner) findViewById( R.id.spinnerestadocivil );
        spinEstadoCivil.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (!IsFirstTimeClick) {
                    estadoCivil = spinEstadoCivil.getSelectedItem().toString();
                   // Log.d("valorSpintipocontrato", sTipoContratoE);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );

        ArrayAdapter<CharSequence> adapterEstadoCivil = ArrayAdapter.createFromResource(this,
                R.array.EstadoCivil, android.R.layout.simple_spinner_item);
        adapterEstadoCivil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinEstadoCivil.setAdapter(adapterEstadoCivil);
        spinEstadoCivil.setTitle("Seleccionar su Estado Civil");

//----------------------Spinner estado civil-------------------------------------------------------------------------------------

//----------------------Spinner grado mayor-------------------------------------------------------------------------------------

        spinGradoMayor = (SearchableSpinner) findViewById( R.id.spinnergradomayor );
        spinGradoMayor.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (!IsFirstTimeClick) {
                    gradomayor = spinGradoMayor.getSelectedItem().toString();
                    // Log.d("valorSpintipocontrato", sTipoContratoE);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );
        ArrayAdapter<CharSequence> adapterGradoMayor = ArrayAdapter.createFromResource(this,
                R.array.GradoMayor, android.R.layout.simple_spinner_item);
        adapterEstadoCivil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGradoMayor.setAdapter(adapterGradoMayor);
        spinGradoMayor.setTitle("Seleccionar el Grado Mayor");

//----------------------Spinner  grado mayor-------------------------------------------------------------------------------------

//----------------------Spinner  estado actual-------------------------------------------------------------------------------------

        spinEstadoActual = (SearchableSpinner) findViewById( R.id.spinnerestadoactual );
        spinEstadoActual.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (!IsFirstTimeClick) {
                    estadoactual = spinEstadoActual.getSelectedItem().toString();
                    // Log.d("valorSpintipocontrato", sTipoContratoE);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );
        ArrayAdapter<CharSequence> adapterEstadoActual = ArrayAdapter.createFromResource(this,
                R.array.EstadoActual, android.R.layout.simple_spinner_item);
        adapterEstadoCivil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinEstadoActual.setAdapter(adapterEstadoActual);
        spinEstadoActual.setTitle("Seleccionar el Estado Actual");

//----------------------Spinner  estado actual-------------------------------------------------------------------------------------

        listItems = getResources().getStringArray( R.array.InfoIdiomas );
        checkedItems = new boolean[listItems.length];


        final String cIdCurriculo = databaseReferenceCurrilo.push().getKey();

        //  CargarCurriculoActualizar( userActivo );

        Button btnregistrarC = findViewById( R.id.xmlBtnRegistrarDatosGC );
        btnregistrarC.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registrarcurriculo( cIdCurriculo );


            }
        } );


        Button btnActualizarC = findViewById( R.id.xmlBtnActualizarDatosGC );
        btnActualizarC.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                actualizarcurriculo(userActivo);

            }
        } );

//--------------------------------------cargar curriculo--------------------------------------------------------------------------------------

        CargarCamposCurriculo(userActivo);

//--------------------------------------cargar curriculo--------------------------------------------------------------------------------------
        btnFormacionAcademica = (Button) findViewById( R.id.xmlBtnFormacionAcademicaC );
        btnFormacionAcademica.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( cPantallaRegistrarCurriculo.this, cPantallaFormacionAcademicaCurriculo.class );
                intent.putExtra( "DetalleFormacionAcademicaID", cIdCurriculo );
                //  Log.d( "idcurriculo",IdCurriculo );
                startActivity( intent );
            }
        } );
        //btnFormacionAcademica.setEnabled(false);

        btnOtrosCursosCurriculo = (Button) findViewById( R.id.AbrirOtrosCursos );
        btnOtrosCursosCurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( cPantallaRegistrarCurriculo.this, cPantallaOtrosCursos.class );
                intent.putExtra( "DetalleOtrosCursosID", cIdCurriculo );
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
                intent.putExtra( "DetalleExperienciaLaboralID", cIdCurriculo );
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
                intent.putExtra( "DetalleReferenciasID", cIdCurriculo );
                //  Log.d( "idcurriculo",IdCurriculo );
                startActivity( intent );
            }
        } );
        //btnReferenciCurriculo.setEnabled(false);

        subirEmpleo();
//-------------------------------------------------------------------------------------------------------------------------------


        // btnFormacionAcademica = findViewById( R.id.xmlBtnFormacionAcademicaC );
        //  btnReferenciCurriculo = findViewById( R.id.xmlBtnReferenciaCurriculoC );
        // btnExperienciaLaboralCurriculo = findViewById( R.id.xmlBntExperienciaLaboralCurriculo );
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
       // Log.d( "useer", userActivo );
        //   CargarCurriculoActualizar(userActivo);


        RadioGroup RGRegistrarCurriculo = (RadioGroup) findViewById( R.id.radiobuttonsexo );
        RGRegistrarCurriculo.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.maculinoCurri:
                        sexo = "Masculino";
                        //Log.d("Valorestado",sEstadoEmpleoE);

                        break;
                    case R.id.femeninaCurri:
                        sexo = "Femenino";
                        //Log.d("Valorestado",sEstadoEmpleoE);

                        break;
                }

            }

        } );
    }

    

    public void onButtonClicked(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show( getFragmentManager(), "Date Picker" );
    }


    //-------------------Codigo de la Actualizacion del curriculo--------------------------------------------------------------------------------------------
   /* public void CargarCurriculoActualizar(String userActivo) {
        databaseReferenceCurrilo.orderByChild( "cIdBuscador" ).equalTo( userActivo ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Curriculos curriculos = dataSnapshot.getValue( Curriculos.class );
                Log.d( "hola", String.valueOf( dataSnapshot ) );

                //Picasso.get().load(empleos.getsImagenEmpleoE()).into(ImageViewAE);

                etNombre.setText( curriculos.getNombre() );
                etApellido.setText( curriculos.getApellido() );
                etCedula.setText( curriculos.getCedula() );
                etEmail.setText( curriculos.getEmail() );
                etTelefono.setText( curriculos.getTelefono() );
                etCelular.setText( curriculos.getCelular() );
                etDireccion.setText( curriculos.getDireccion() );
                etOcupacion.setText( curriculos.getEmail() );
                mEtxtIdioma.setText( curriculos.getTelefono() );
                mEtxtFecha.setText( curriculos.getCelular() );
                etHabilidades.setText( curriculos.getDireccion() );

                spinProvinciaCurriculo.setSelection( obtenerPosicionItem( spinProvinciaCurriculo, curriculos.getProvincia() ) );
                spinGradoMayor.setSelection( obtenerPosicionItem( spinGradoMayor, curriculos.getGradomayor() ) );
                spinEstadoCivil.setSelection( obtenerPosicionItem( spinEstadoCivil, curriculos.getEstadoCivil() ) );
                spinEstadoActual.setSelection( obtenerPosicionItem( spinEstadoActual, curriculos.getEstadoactual() ) );

                //Picasso.get().load(curriculos.getImageview()).into(ImageView);

                RadioGroup RGsexo = (RadioGroup) findViewById( R.id.radiobuttonsexo );
                RGsexo.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
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
                        Log.d( "Valorsexo", sexo );
                    }
                } );

            }

            public int obtenerPosicionItem(Spinner spinner, String fruta) {
                //Creamos la variable posicion y lo inicializamos en 0
                int posicion = 0;
                //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
                //que lo pasaremos posteriormente
                for (int i = 0; i < spinner.getCount(); i++) {
                    //Almacena la posición del ítem que coincida con la búsqueda
                    if (spinner.getItemAtPosition( i ).toString().equalsIgnoreCase( fruta )) {
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
        } );

    }*/
//-------------------Codigo de la Actualizacion del curriculo--------------------------------------------------------------------------------------------

//--------------------------------------cargar curriculo--------------------------------------------------------------------------------------

    private void CargarCamposCurriculo(String userActivo) {

        bbdd = FirebaseDatabase.getInstance().getReference("Curriculos");

        Query q = bbdd.orderByChild("cIdBuscador").equalTo(userActivo);
        //Query q = bbdd.orderByChild("cIdBuscador").equalTo("cIdBuscardor");

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;
                ArrayList<String> listado = new ArrayList<String>();

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Log.d("holapdddddd", String.valueOf(dataSnapshot));

                   // Curriculos curriculos = dataSnapshot.getValue( Curriculos.class );
                    com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.Curriculos curriculos = datasnapshot.getValue(com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.Curriculos.class);
/*
                    String titulo = curriculos.getNombre();
                    Log.d("adaptador", titulo);

                    listado.add(titulo);
*/

                    Picasso.get().load( curriculos.getImagen() ).into( imageViewcurriculo );

                    etNombre.setText(curriculos.getNombre());
                    etApellido.setText(curriculos.getApellido());
                    etCedula.setText(curriculos.getCedula());
                    etEmail.setText(curriculos.getEmail());
                    etTelefono.setText(curriculos.getTelefono());
                    etCelular.setText(curriculos.getCelular());

                    spinProvinciaCurriculo.setSelection( obtenerPosicionItem( spinProvinciaCurriculo, curriculos.getProvincia() ) );
                    spinEstadoCivil.setSelection( obtenerPosicionItem( spinEstadoCivil, curriculos.getEstadoCivil() ) );

                    etDireccion.setText(curriculos.getDireccion());
                    etOcupacion.setText(curriculos.getOcupacion());
                    mEtxtIdioma.setText( curriculos.getIdioma() );
                    mEtxtIdioma.setText( curriculos.getIdioma() );

                    spinGradoMayor.setSelection( obtenerPosicionItem( spinGradoMayor, curriculos.getGradomayor() ) );
                    spinEstadoActual.setSelection( obtenerPosicionItem( spinEstadoActual, curriculos.getEstadoactual() ) );

                    mEtxtFecha.setText( curriculos.getFecha() );
                    etHabilidades.setText(curriculos.getHabilidades());


                    CodigoCurriculo = curriculos.getcCodigoId();


                    RadioGroup RGsexo = (RadioGroup) findViewById( R.id.radiobuttonsexo );
                    RGsexo.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            switch (checkedId) {
                                case R.id.maculinoCurri:
                                    sexo = "Masculino";
                                    //Log.d("Valorestado",sEstadoEmpleoAE);
                                    break;
                                case R.id.femeninaCurri:
                                    sexo = "Femenino";
                                    //Log.d("Valorestado",sEstadoEmpleoAE);
                                    break;
                            }
                            Log.d( "Valorsexo", sexo );
                        }
                    } );



                }

          /*      adaptador = new ArrayAdapter<String>(cPantallaRegistrarCurriculo.this, android.R.layout.simple_list_item_1, listado);
                //lista.setAdapter(adaptador);
                Log.d("adaptador", String.valueOf(adaptador));
                */


            }
            public int obtenerPosicionItem(Spinner spinner, String fruta) {
                //Creamos la variable posicion y lo inicializamos en 0
                int posicion = 0;
                //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
                //que lo pasaremos posteriormente
                for (int i = 0; i < spinner.getCount(); i++) {
                    //Almacena la posición del ítem que coincida con la búsqueda
                    if (spinner.getItemAtPosition( i ).toString().equalsIgnoreCase( fruta )) {
                        posicion = i;
                    }
                }
                //Devuelve un valor entero (si encontro una coincidencia devuelve la
                // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
                return posicion;
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//--------------------------------------cargar curriculo--------------------------------------------------------------------------------------


    }


//--------------------------------------cargar curriculo--------------------------------------------------------------------------------------


//--------------------------------------cargar curriculo--------------------------------------------------------------------------------------


//----------------imagen en el curriculo----------------------------------------------------------------------------------------------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == IMAGE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            mFilePathUri = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap( getContentResolver(), mFilePathUri );
                imageViewcurriculo.setImageBitmap( bitmap );
            } catch (Exception e) {

                Toast.makeText( this, e.getMessage(), Toast.LENGTH_LONG ).show();

            }


        }
    }

    private void subirEmpleo() {

        bbdd = FirebaseDatabase.getInstance().getReference("Curriculos");

        Query q = bbdd.orderByChild("cIdBuscador").equalTo("probandousuario");

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;
                ArrayList<String> listado = new ArrayList<String>();

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Log.d("holapdddddd", String.valueOf(dataSnapshot));


                    com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.Curriculos curriculos = datasnapshot.getValue(com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.Curriculos.class);
                    String titulo = curriculos.getNombre();
                    Log.d("adaptador", titulo);

                    listado.add(titulo);

                    etNombre.setText(curriculos.getNombre());
                    etApellido.setText(curriculos.getApellido());
                    etCedula.setText(curriculos.getCedula());
                    etEmail.setText(curriculos.getEmail());
                    etTelefono.setText(curriculos.getTelefono());
                    etCelular.setText(curriculos.getCelular());

                    //spinProvinciaCurriculoAct.setSelection( obtenerPosicionItem( spinProvinciaCurriculoAct, curriculos.getProvincia() ) );
                    //spinEstadoCivilAct.setSelection( obtenerPosicionItem( spinEstadoCivilAct, curriculos.getEstadoCivil() ) );

                    etDireccion.setText(curriculos.getDireccion());
                    etOcupacion.setText(curriculos.getOcupacion());
                    //mEtxtIdiomaAct.setText( curriculos.getIdioma() );

                    //spinGradoMayorAct.setSelection( obtenerPosicionItem( spinGradoMayorAct, curriculos.getGradomayor() ) );
                    //spinEstadoActualAct.setSelection( obtenerPosicionItem( spinEstadoActualAct, curriculos.getEstadoactual() ) );

                    //mEtxtFechaAct.setText( curriculos.getFecha() );
                    etHabilidades.setText(curriculos.getHabilidades());
                }

                adaptador = new ArrayAdapter<String>(cPantallaRegistrarCurriculo.this, android.R.layout.simple_list_item_1, listado);
                //lista.setAdapter(adaptador);
                Log.d("adaptador", String.valueOf(adaptador));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void limpiarCampor() {
        etNombre.setText( "" );
        etApellido.setText( "" );
        etCedula.setText( "" );
        etEmail.setText( "" );
        etCelular.setText( "" );
        etTelefono.setText( "" );
        etDireccion.setText( "" );

        etOcupacion.setText( "" );
        mEtxtIdioma.setText( "" );
        etHabilidades.setText( "" );
        mEtxtFecha.setText( "" );
    }

    public void ActivarCampor() {
        btnFormacionAcademica.setEnabled( true );
        btnOtrosCursosCurriculo.setEnabled( true );
        btnExperienciaLaboralCurriculo.setEnabled( true );
        btnReferenciCurriculo.setEnabled( true );


    }

  /*  private void camposvacios(){
        if (TextUtils.isEmpty(nombre)) {
            etNombre.setError("Campo vacío, por favor escriba el nombre ");
            return;
        }

        if (TextUtils.isEmpty(provincia)) {
            Toast.makeText(this, "Spinner vacío, por favor seleccione una Provincia", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(estadoCivil)) {
            Toast.makeText(this, "Spinner vacío, por favor seleccione una Provincia", Toast.LENGTH_LONG).show();
            return;
        }

    }
*/


    private void actualizarcurriculo(final String userActivo) {

        if (mFilePathUri != null) {
            mProgressDialog.setTitle( "Subiendo Curriculo..." );
            mProgressDialog.show();

            //klk = sIdEmpleador;
            //  mProgressDialog.setTitle( "imagen subiendo..." );
            // mProgressDialog.dismiss();
            final StorageReference StorageReference2nd = mStorageReference.child( mStoragePath + System.currentTimeMillis() + "." + getFileExtension( mFilePathUri ) );


            ////////////////////////////////////////


            //final StorageReference storageReference2do = storageReference2do.child("your_REF");
            UploadTask uploadTask = StorageReference2nd.putFile( mFilePathUri );

            Task<Uri> urlTask = uploadTask.continueWithTask( new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return StorageReference2nd.getDownloadUrl();
                }
            } ).addOnCompleteListener( new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String downloadURL = downloadUri.toString();
                        Log.d( "url", downloadURL );
                        // String imagen = imageViewcurriculo.getImageMatrix().toString();
                        nombre = etNombre.getText().toString().trim().toLowerCase();
                        apellido = etApellido.getText().toString().trim();
                        cedula = etCedula.getText().toString().trim();
                        email = etEmail.getText().toString().trim();
                        celular = etCelular.getText().toString().trim();
                        telefono = etTelefono.getText().toString().trim();
                        direccion = etDireccion.getText().toString().trim();
                        ocupacion = etOcupacion.getText().toString().trim();
                        idioma = mEtxtIdioma.getText().toString().trim();
                        habilidades = etHabilidades.getText().toString().trim();
                        fecha = mEtxtFecha.getText().toString().trim();


                        //  camposvacios();






                        mProgressDialog.dismiss();



                        Log.d( "url", nombre );


                        //        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        String cIdBuscardor = userActivo;




                       // String idCurriculo  = databaseReferenceCurrilo.push().getKey();
                       //  idCurriculo  = cIdCurriculo;
                       // String cIdCurriculo;


                      //  Log.d( "klk", cIdCurriculo );

                        Curriculos curriculos = new Curriculos( CodigoCurriculo, cIdBuscardor,
                                downloadURL, nombre, apellido, cedula, email, telefono, celular,
                                provincia, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual,
                                sexo, habilidades, fecha );

                        databaseReferenceCurrilo.child( CodigoCurriculo ).setValue( curriculos );

                        ActivarCampor();
                        limpiarCampor();
                        Toast.makeText( cPantallaRegistrarCurriculo.this, "Curriculo subida exitosamente...", Toast.LENGTH_LONG ).show();


                    } else {
                        // Handle failures
                        // ...
                    }
                }
            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressDialog.dismiss();
                    Toast.makeText( cPantallaRegistrarCurriculo.this, e.getMessage(), Toast.LENGTH_LONG ).show();

                }
            } );
        } else {
            Toast.makeText( cPantallaRegistrarCurriculo.this, "Por favor, Seleccionar una Imagen", Toast.LENGTH_LONG ).show();

        }


    }
  
  

    private void registrarcurriculo(final String cIdCurriculo) {

        if (mFilePathUri != null) {
            mProgressDialog.setTitle( "Subiendo Curriculo..." );
            mProgressDialog.show();

            //klk = sIdEmpleador;
            //  mProgressDialog.setTitle( "imagen subiendo..." );
            // mProgressDialog.dismiss();
            final StorageReference StorageReference2nd = mStorageReference.child( mStoragePath + System.currentTimeMillis() + "." + getFileExtension( mFilePathUri ) );


            ////////////////////////////////////////


            //final StorageReference storageReference2do = storageReference2do.child("your_REF");
            UploadTask uploadTask = StorageReference2nd.putFile( mFilePathUri );

            Task<Uri> urlTask = uploadTask.continueWithTask( new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return StorageReference2nd.getDownloadUrl();
                }
            } ).addOnCompleteListener( new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String downloadURL = downloadUri.toString();
                        Log.d( "url", downloadURL );
                        // String imagen = imageViewcurriculo.getImageMatrix().toString();
                        nombre = etNombre.getText().toString().trim().toLowerCase();
                        apellido = etApellido.getText().toString().trim();
                        cedula = etCedula.getText().toString().trim();
                        email = etEmail.getText().toString().trim();
                        celular = etCelular.getText().toString().trim();
                        telefono = etTelefono.getText().toString().trim();
                        direccion = etDireccion.getText().toString().trim();
                        ocupacion = etOcupacion.getText().toString().trim();
                        idioma = mEtxtIdioma.getText().toString().trim();
                        habilidades = etHabilidades.getText().toString().trim();
                        fecha = mEtxtFecha.getText().toString().trim();


                      //  camposvacios();






                        mProgressDialog.dismiss();



                        Log.d( "url", nombre );


                        //        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        String cIdBuscardor = userActivo;

                        // String idCurriculo  = mDatabaseReference.push().getKey();
                        //String idCurriculo  = cIdCurriculo;

                        Log.d( "klk", cIdCurriculo );

                        Curriculos curriculos = new Curriculos( cIdCurriculo, cIdBuscardor,
                                downloadURL, nombre, apellido, cedula, email, telefono, celular,
                                provincia, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual,
                                sexo, habilidades, fecha );

                        databaseReferenceCurrilo.child( cIdCurriculo ).setValue( curriculos );

                        ActivarCampor();
                        limpiarCampor();
                        Toast.makeText( cPantallaRegistrarCurriculo.this, "Curriculo subida exitosamente...", Toast.LENGTH_LONG ).show();


                    } else {
                        // Handle failures
                        // ...
                    }
                }
            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressDialog.dismiss();
                    Toast.makeText( cPantallaRegistrarCurriculo.this, e.getMessage(), Toast.LENGTH_LONG ).show();

                }
            } );
        } else {
            Toast.makeText( cPantallaRegistrarCurriculo.this, "Por favor, Seleccionar una Imagen", Toast.LENGTH_LONG ).show();

        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( contentResolver.getType( uri ) );
    }

//----------------imagen en el curriculo----------------------------------------------------------------------------------------------------------------






 /*   public void registrarcurriculo(String IdCurriculo) {


        imagen = imageViewcurriculo.getImageMatrix().toString();
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




        if (TextUtils.isEmpty( cApellido )) {
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

/*
        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String cIdBuscardor = Ukey;


        Curriculos curriculos = new Curriculos( IdCurriculo, cIdBuscardor, imagen, nombre, apellido,
                cedula, email, telefono, celular, sProvinciaCurriculoB, estadoCivil, direccion, ocupacion,
                idioma, gradomayor, estadoactual, habilidades, fecha );


        mDatabaseReference.child( IdCurriculo ).setValue( curriculos );

        Toast.makeText( this, "El Curriculo se registro exitosamente", Toast.LENGTH_LONG ).show();
        ActivarCampor();
        limpiarCampor();

        //DBReferenceCurriculos.child("empleos").child(IDEmpleo).setValue(empleos);
        //   DBReferenceCurriculos.child(Ukey).setValue(curriculos);//para registrarlo dentro del usuario que inicio sesion


    }*/
}
