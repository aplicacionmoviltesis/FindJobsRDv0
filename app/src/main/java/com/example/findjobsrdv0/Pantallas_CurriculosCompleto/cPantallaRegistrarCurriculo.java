package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

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
import android.graphics.drawable.BitmapDrawable;
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

import com.example.findjobsrdv0.Administradores.PantallaActualizarProvincia;
import com.example.findjobsrdv0.GeneralesApp.DatePickerFragment;

import com.example.findjobsrdv0.GeneralesApp.MultipleSelectionSpinner;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Curriculos;
import com.example.findjobsrdv0.GeneralesApp.Provincias;
import com.example.findjobsrdv0.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class cPantallaRegistrarCurriculo extends AppCompatActivity {

    private EditText etNombre, etApellido, etCedula, etEmail, etTelefono, etCelular,
            etDireccion, etOcupacion, etHabilidades;

    private String nombre, apellido, cedula, email, telefono, celular, provincia, estadoCivil, direccion, ocupacion,
            idioma, gradomayor, estadoactual, sexo, habilidades, fecha, sAreaC, sImagenC;

    private Button btnIdiomasc;
    private SearchableSpinner spinEstadoCivil, spinGradoMayor, spinEstadoActual;

    private RadioButton RdbDisponibleC, RdbNoDisponibleC;

    private TextView mEtxtFecha, mEtxtIdioma;

    private TextView muestraidioma;

    private String CodigoCurriculo;

    private Button btnFormacionAcademica, btnReferenciCurriculo, btnExperienciaLaboralCurriculo, btnOtrosCursosCurriculo, bntIdioma;

    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    private SearchableSpinner spinProvinciaCurriculo;
    private DatabaseReference provinciasRefCurriculo;
    List<Provincias> provincias;
    boolean IsFirstTimeClick = true;

    private String userActivo;

    private String mStoragePath = "Imagenes Curriculo/";
    private ImageView imageViewcurriculo;
    private Uri mFilePathUri;
    private StorageReference mStorageReference;

    private DatabaseReference databaseReferenceCurrilo;
    private FirebaseDatabase database;

    ProgressDialog mProgressDialog;

    int IMAGE_REQUEST_CODE = 5;

    private DatabaseReference cargarCurriculo;

    private MultipleSelectionSpinner spinAreaC;
    private boolean isLikeChecked = false;
    private DatabaseReference areasRef;

    //List which hold multiple selection spinner values
    List<String> list = new ArrayList<String>();


    Button btnregistrarC;
    Button btnActualizarC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_c_pantalla_registrar_curriculo );

        cargarCurriculo = FirebaseDatabase.getInstance().getReference();

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

        mProgressDialog = new ProgressDialog( cPantallaRegistrarCurriculo.this );

        database = FirebaseDatabase.getInstance();
        databaseReferenceCurrilo = database.getReference( "Curriculos" );

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

        provinciasRefCurriculo = FirebaseDatabase.getInstance().getReference();
        spinProvinciaCurriculo = (SearchableSpinner) findViewById( R.id.spinnerprovincias );

        areasRef = FirebaseDatabase.getInstance().getReference();
        spinAreaC = findViewById( R.id.spinnerAreaC );

        areasRef.child( "Areas" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> ListAreas = new ArrayList<String>();
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child( "sNombreArea" ).getValue( String.class );
                    ListAreas.add( areaName );
                    list.add( areaName );
                }
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>( cPantallaRegistrarCurriculo.this, android.R.layout.simple_spinner_item, ListAreas );
                areasAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spinAreaC.setItems( list );
                list.clear();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

        spinProvinciaCurriculo.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    provincia = spinProvinciaCurriculo.getSelectedItem().toString();
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        } );

        provinciasRefCurriculo.child( "Provincias" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListProvinciasCurri = new ArrayList<String>();
                for (DataSnapshot provinciaSnapshot : dataSnapshot.getChildren()) {
                    String provinciaName = provinciaSnapshot.child( "sNombreProvincia" ).getValue( String.class );
                    ListProvinciasCurri.add( provinciaName );
                }

                ArrayAdapter<String> provinciasAdapterCurriculo = new ArrayAdapter<String>( cPantallaRegistrarCurriculo.this, android.R.layout.simple_spinner_item, ListProvinciasCurri );
                provinciasAdapterCurriculo.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spinProvinciaCurriculo.setAdapter( provinciasAdapterCurriculo );
                spinProvinciaCurriculo.setTitle( "Seleccionar Provincia" );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

        spinEstadoCivil = (SearchableSpinner) findViewById( R.id.spinnerestadocivil );
        spinEstadoCivil.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (!IsFirstTimeClick) {
                    estadoCivil = spinEstadoCivil.getSelectedItem().toString();
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );

        ArrayAdapter<CharSequence> adapterEstadoCivil = ArrayAdapter.createFromResource( this,
                R.array.EstadoCivil, android.R.layout.simple_spinner_item );
        adapterEstadoCivil.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinEstadoCivil.setAdapter( adapterEstadoCivil );
        spinEstadoCivil.setTitle( "Seleccionar su Estado Civil" );


        spinGradoMayor = (SearchableSpinner) findViewById( R.id.spinnergradomayor );
        spinGradoMayor.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (!IsFirstTimeClick) {
                    gradomayor = spinGradoMayor.getSelectedItem().toString();
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );
        ArrayAdapter<CharSequence> adapterGradoMayor = ArrayAdapter.createFromResource( this,
                R.array.GradoMayor, android.R.layout.simple_spinner_item );
        adapterEstadoCivil.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinGradoMayor.setAdapter( adapterGradoMayor );
        spinGradoMayor.setTitle( "Seleccionar el Grado Mayor" );


        spinEstadoActual = (SearchableSpinner) findViewById( R.id.spinnerestadoactual );
        spinEstadoActual.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (!IsFirstTimeClick) {
                    estadoactual = spinEstadoActual.getSelectedItem().toString();
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );
        ArrayAdapter<CharSequence> adapterEstadoActual = ArrayAdapter.createFromResource( this,
                R.array.EstadoActual, android.R.layout.simple_spinner_item );
        adapterEstadoCivil.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinEstadoActual.setAdapter( adapterEstadoActual );
        spinEstadoActual.setTitle( "Seleccionar el Estado Actual" );


        listItems = getResources().getStringArray( R.array.InfoIdiomas );
        checkedItems = new boolean[listItems.length];

        final String cIdCurriculo = databaseReferenceCurrilo.push().getKey();

        btnregistrarC = findViewById( R.id.xmlBtnRegistrarDatosGC );
        btnregistrarC.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cedula = etCedula.getText().toString().trim();
                Log.d( " todo", String.valueOf( cedula ) );

                Query q = databaseReferenceCurrilo.orderByChild( "sCedulaC" ).equalTo( cedula );
                q.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            Log.d( "rnc no existe", String.valueOf( dataSnapshot ) );
                            //Toast.makeText(PantallaRegistroEmpleador.this, "Ir a registrar", Toast.LENGTH_LONG).show();
                            sAreaC = spinAreaC.getSelectedItemsAsString();
                            registrarcurriculo( cIdCurriculo );

                        } else {
                            etCedula.setError( "Esta cedula ya a sido registrado" );
                            Log.d( "rnc si existe", String.valueOf( dataSnapshot ) );
                            //Toast.makeText(PantallaRegistroEmpleador.this, "El RNC escrito ya existe", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                } );
            }
        } );

        btnActualizarC = findViewById( R.id.xmlBtnActualizarDatosGC );
        btnActualizarC.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sAreaC = spinAreaC.getSelectedItemsAsString();

                DeleteImagenAnterior();

            }
        } );

        CargarCamposCurriculo( userActivo );

        btnFormacionAcademica = (Button) findViewById( R.id.xmlBtnFormacionAcademicaC );
        btnFormacionAcademica.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( cPantallaRegistrarCurriculo.this, cPantallaFormacionAcademicaCurriculo.class );
                intent.putExtra( "DetalleFormacionAcademicaID", cIdCurriculo );
                startActivity( intent );
            }
        } );

        btnOtrosCursosCurriculo = (Button) findViewById( R.id.AbrirOtrosCursos );
        btnOtrosCursosCurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( cPantallaRegistrarCurriculo.this, cPantallaOtrosCursos.class );
                intent.putExtra( "DetalleOtrosCursosID", cIdCurriculo );
                startActivity( intent );
            }
        } );

        btnExperienciaLaboralCurriculo = (Button) findViewById( R.id.xmlBntExperienciaLaboralCurriculo );
        btnExperienciaLaboralCurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( cPantallaRegistrarCurriculo.this, cPantallaExperienciaLaboralCurriculo.class );
                intent.putExtra( "DetalleExperienciaLaboralID", cIdCurriculo );
                startActivity( intent );
            }
        } );

        btnReferenciCurriculo = (Button) findViewById( R.id.xmlBtnReferenciaCurriculoC );
        btnReferenciCurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( cPantallaRegistrarCurriculo.this, cPantallaReferenciasCurriculo.class );
                intent.putExtra( "DetalleReferenciasID", cIdCurriculo );
                startActivity( intent );
            }
        } );

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

        RadioGroup RGRegistrarCurriculo = (RadioGroup) findViewById( R.id.radiobuttonsexo );
        RGRegistrarCurriculo.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.maculinoCurri:
                        sexo = "Masculino";
                        break;
                    case R.id.femeninaCurri:
                        sexo = "Femenino";
                        break;
                }
            }
        } );
    }

    public void onButtonClicked(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show( getFragmentManager(), "Date Picker" );
    }

    private void CargarCamposCurriculo(String userActivo) {

        cargarCurriculo = FirebaseDatabase.getInstance().getReference( "Curriculos" );

        Query q = cargarCurriculo.orderByChild( "sIdBuscadorEmpleo" ).equalTo( userActivo );
        q.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Toast.makeText( cPantallaRegistrarCurriculo.this, "entro", Toast.LENGTH_SHORT ).show();

                Log.d( "entro", String.valueOf( dataSnapshot ) );

                if (dataSnapshot.exists()) {
                    btnregistrarC.setEnabled( false );
//                        btnActualizarC.setEnabled( true );
                }else {
                    btnActualizarC.setEnabled( false );
                }

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    sImagenC = datasnapshot.child( "sImagenC" ).getValue( String.class );

                    Curriculos curriculos = datasnapshot.getValue( Curriculos.class );

                    Picasso.get().load( sImagenC ).into( imageViewcurriculo );
                    etNombre.setText( curriculos.getsNombreC() );
                    etApellido.setText( curriculos.getsApellidoC() );
                    etCedula.setText( curriculos.getsCedulaC() );
                    etEmail.setText( curriculos.getsEmailC() );
                    etTelefono.setText( curriculos.getsTelefonoC() );
                    etCelular.setText( curriculos.getsCelularC() );

                    spinProvinciaCurriculo.setSelection( obtenerPosicionItem( spinProvinciaCurriculo, curriculos.getsProvinciaC() ) );
                    spinEstadoCivil.setSelection( obtenerPosicionItem( spinEstadoCivil, curriculos.getsEstadoCivilC() ) );

                    etDireccion.setText( curriculos.getsDireccionC() );
                    etOcupacion.setText( curriculos.getsOcupacionC() );
                    mEtxtIdioma.setText( curriculos.getsIdiomaC() );
                    mEtxtIdioma.setText( curriculos.getsIdiomaC() );

                    spinGradoMayor.setSelection( obtenerPosicionItem( spinGradoMayor, curriculos.getsGradoMayorC() ) );
                    spinEstadoActual.setSelection( obtenerPosicionItem( spinEstadoActual, curriculos.getsEstadoActualC() ) );

                    mEtxtFecha.setText( curriculos.getsFechaC() );
                    etHabilidades.setText( curriculos.getsHabilidadesC() );

                    CodigoCurriculo = curriculos.getsIdCurriculo();

                    RadioGroup RGsexo = (RadioGroup) findViewById( R.id.radiobuttonsexo );
                    RGsexo.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            switch (checkedId) {
                                case R.id.maculinoCurri:
                                    sexo = "Masculino";
                                    break;
                                case R.id.femeninaCurri:
                                    sexo = "Femenino";
                                    break;
                            }
                            Log.d( "Valorsexo", sexo );
                        }
                    } );
                }
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
        } );
    }

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
//    public void limpiarCampor() {
//        etNombre.setText( "" );
//        etApellido.setText( "" );
//        etCedula.setText( "" );
//        etEmail.setText( "" );
//        etCelular.setText( "" );
//        etTelefono.setText( "" );
//        etDireccion.setText( "" );
//
//        etOcupacion.setText( "" );
//        mEtxtIdioma.setText( "" );
//        etHabilidades.setText( "" );
//        mEtxtFecha.setText( "" );
//    }
//
//    public void ActivarCampor() {
//        btnFormacionAcademica.setEnabled( true );
//        btnOtrosCursosCurriculo.setEnabled( true );
//        btnExperienciaLaboralCurriculo.setEnabled( true );
//        btnReferenciCurriculo.setEnabled( true );
//
//
//    }


    private void registrarcurriculo(final String cIdCurriculo) {

        if (mFilePathUri != null) {
            mProgressDialog.setTitle( "Subiendo Curriculo..." );
            mProgressDialog.show();

            final StorageReference StorageReference2nd = mStorageReference.child( mStoragePath + System.currentTimeMillis() + "." + getFileExtension( mFilePathUri ) );

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

                        mProgressDialog.dismiss();

                        String cIdBuscardor = userActivo;

                        Curriculos curriculos = new Curriculos( cIdCurriculo, cIdBuscardor,
                                downloadURL, nombre, apellido, cedula, email, telefono, celular,
                                provincia, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual,
                                sexo, habilidades, fecha, sAreaC );

                        databaseReferenceCurrilo.child( cIdCurriculo ).setValue( curriculos );
                        Toast.makeText( cPantallaRegistrarCurriculo.this, "Curriculo subida exitosamente...", Toast.LENGTH_LONG ).show();

                    } else {

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

    private void DeleteImagenAnterior() {
        if (sImagenC != null && !sImagenC.isEmpty()) {
            final StorageReference mPitureRef = getInstance().getReferenceFromUrl( sImagenC );
            mPitureRef.delete().addOnSuccessListener( new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText( cPantallaRegistrarCurriculo.this, "Eliminando Imagen...", Toast.LENGTH_LONG ).show();
                    Log.d( "link foto", String.valueOf( mPitureRef ) );
                    SubirNuevaImagen();
                }
            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( cPantallaRegistrarCurriculo.this, "No hay imagen agregada", Toast.LENGTH_LONG ).show();

                    mProgressDialog.dismiss();
                    SubirNuevaImagen();
                }
            } );
        } else {
            Toast.makeText( cPantallaRegistrarCurriculo.this, "No hay imagen agregada", Toast.LENGTH_LONG ).show();
            SubirNuevaImagen();
        }
    }

    private void SubirNuevaImagen() {
        String imageName = System.currentTimeMillis() + ".png";
        //String imageName = System.currentTimeMillis() + getFileExtension(mFilePathUri);

        StorageReference storageReference2do = mStorageReference.child( mStoragePath + imageName );

        Bitmap bitmap = ((BitmapDrawable) imageViewcurriculo.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.PNG, 100, baos );

        byte[] data = baos.toByteArray();
        UploadTask uploadTask = storageReference2do.putBytes( data );
        uploadTask.addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText( cPantallaRegistrarCurriculo.this, "Nueva Imagen Subida...", Toast.LENGTH_LONG ).show();
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri downloadUri = uriTask.getResult();
                actualizarcurriculo( userActivo, downloadUri.toString() );

            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( cPantallaRegistrarCurriculo.this, e.getMessage(), Toast.LENGTH_LONG ).show();
                mProgressDialog.dismiss();
            }
        } );
    }

    private void actualizarcurriculo(final String userActivo, String foto) {
        if (mFilePathUri != null) {
            mProgressDialog.setTitle( "Actualizando Curriculo..." );
            mProgressDialog.show();

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

            mProgressDialog.dismiss();

            String cIdBuscardor = userActivo;

            Curriculos curriculos = new Curriculos( CodigoCurriculo, cIdBuscardor,
                    foto, nombre, apellido, cedula, email, telefono, celular,
                    provincia, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual,
                    sexo, habilidades, fecha, sAreaC );

            databaseReferenceCurrilo.child( CodigoCurriculo ).setValue( curriculos );
            Toast.makeText( cPantallaRegistrarCurriculo.this, "Curriculo Actualizado exitosamente...", Toast.LENGTH_LONG ).show();

        }
    }
}