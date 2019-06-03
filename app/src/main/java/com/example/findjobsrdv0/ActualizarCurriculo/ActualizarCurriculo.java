package com.example.findjobsrdv0.ActualizarCurriculo;

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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findjobsrdv0.ActualizarFormacionAcad.ActualizarFormacionAcad;
import com.example.findjobsrdv0.DatePickerFragment;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.Curriculos;
import com.example.findjobsrdv0.Registro_del_Curriculo.cPantallaExperienciaLaboralCurriculo;
import com.example.findjobsrdv0.Registro_del_Curriculo.cPantallaOtrosCursos;
import com.example.findjobsrdv0.Registro_del_Curriculo.cPantallaReferenciasCurriculo;
import com.example.findjobsrdv0.Registro_del_Curriculo.cPantallaRegistrarCurriculo;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class ActualizarCurriculo extends AppCompatActivity {


    Button mOrder;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();


    /////Spinner ProvinciaAct

    private Spinner spinProvinciaCurriculoAct;
    private DatabaseReference provinciasRefCurriculoAct;
    private boolean IsFirstTimeClick = true;
    private String sProvinciaCurriculoBAct;

/////Spinner ProvinciaAct

    String userActivo;


    EditText etNombreAct, etApellidoAct, etCedulaAct, etEmailAct, etTelefonoAct, etCelularAct,
            etDireccionAct, etOcupacionAct, etHabilidadesAct;

    Button btnIdiomascAct;
    SearchableSpinner ProvinciaAct, spinEstadoCivilAct, spinGradoMayorAct, spinEstadoActualAct;

    TextView mEtxtFechaAct, mEtxtIdiomaAct;

    String sexo;

    TextView muestraidiomaAct;

    //  FirebaseAuth mAuth;

    String nombre, apellido, cedula, email, telefono, celular, provincia, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual, habilidades, fecha;

    //ImageView imageViewcurriculo;

    String idcurriculoact = "";
    //  curriculoactualizarID

    private Button btnActualizarFormacionAcademica, btnActualizarReferenciCurriculo, btnActualizarExperienciaLaboralCurriculo, btnActualizarOtrosCursosCurriculo, bntActualizarIdioma;


    DatabaseReference vistaCurriculoactualizar;


    //---------------imagen en el curriculo----------------------------------------------------------------------------------------------------------------

    private String mStoragePath = "Imagenes Curriculo/";

    ImageView imageViewcurriculoActualizar;

    Uri mFilePathUriAct;

    StorageReference mStorageReferenceAct;

   /* DatabaseReference databaseReferenceCurrilo;
    FirebaseDatabase database;*/

    ProgressDialog mProgressDialog;

    int IMAGE_REQUEST_CODE = 5;


//----------------imagen en el curriculo----------------------------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_actualizar_curriculo );

        //imageViewcurriculo = (ImageView) findViewById( R.id.xmlImageViewimagenenelCurriculo );


        vistaCurriculoactualizar = FirebaseDatabase.getInstance().getReference( "Curriculos" );

//---------------imagen en el curriculo----------------------------------------------------------------------------------------------------------------


        imageViewcurriculoActualizar = (ImageView) findViewById( R.id.xmlImageViewimagenenelCurriculoActualizar );
        imageViewcurriculoActualizar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType( "image/*" );
                galleryIntent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( galleryIntent, "Seleccionar Imagen" ), IMAGE_REQUEST_CODE );
            }
        } );

        mStorageReferenceAct = FirebaseStorage.getInstance().getReference();

        mProgressDialog = new ProgressDialog( ActualizarCurriculo.this );
/*
        database = FirebaseDatabase.getInstance();
        databaseReferenceCurrilo = database.getReference( "Curriculos" );
*/
//----------------imagen en el curriculo----------------------------------------------------------------------------------------------------------------


        etNombreAct = (EditText) findViewById( R.id.etnombreAct );
        etApellidoAct = (EditText) findViewById( R.id.etapellidoAct );
        etCedulaAct = (EditText) findViewById( R.id.etcedulaAct );
        etEmailAct = (EditText) findViewById( R.id.etemailAct );
        etTelefonoAct = (EditText) findViewById( R.id.ettelefonoAct );
        etCelularAct = (EditText) findViewById( R.id.etcelulaAct );
        etDireccionAct = (EditText) findViewById( R.id.etdireccionAct );
        etOcupacionAct = (EditText) findViewById( R.id.etocupacionAct );
        etHabilidadesAct = (EditText) findViewById( R.id.ethabilidadesAct );
        // ProvinciaAct = (SearchableSpinner) findViewById( R.id.spinnerprovinciasAct );
        spinEstadoCivilAct = (SearchableSpinner) findViewById( R.id.spinnerestadocivilAct );
        spinGradoMayorAct = (SearchableSpinner) findViewById( R.id.spinnergradomayorAct );
        spinEstadoActualAct = (SearchableSpinner) findViewById( R.id.spinnerestadoactualAct );

        mEtxtFechaAct = (TextView) findViewById( R.id.tvAct );
        mEtxtIdiomaAct = (TextView) findViewById( R.id.tvopAct );

        provinciasRefCurriculoAct = FirebaseDatabase.getInstance().getReference();
        spinProvinciaCurriculoAct = (Spinner) findViewById( R.id.spinnerprovinciasAct );

        spinProvinciaCurriculoAct.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!IsFirstTimeClick) {
                    sProvinciaCurriculoBAct = spinProvinciaCurriculoAct.getSelectedItem().toString();
                    Log.d( "valorSpinProv", sProvinciaCurriculoBAct );
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );

        provinciasRefCurriculoAct.child( "provincias" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> ListProvinciasCurriAct = new ArrayList<String>();
                for (DataSnapshot provinciaSnapshot : dataSnapshot.getChildren()) {
                    String provinciaNameAct = provinciaSnapshot.child( "Nombre_Provincia" ).getValue( String.class );
                    ListProvinciasCurriAct.add( provinciaNameAct );
                }

                ArrayAdapter<String> provinciasAdapterCurriculo = new ArrayAdapter<String>( ActualizarCurriculo.this, android.R.layout.simple_spinner_item, ListProvinciasCurriAct );
                provinciasAdapterCurriculo.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spinProvinciaCurriculoAct.setAdapter( provinciasAdapterCurriculo );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );


        btnIdiomascAct = (Button) findViewById( R.id.xmlBtnIdiomaAct );


        userActivo = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final String cIdCurriculo = vistaCurriculoactualizar.push().getKey();

//-------------botones para ir a registrar las otras informaciones hacia abajo ↓↓↓↓↓↓↓↓↓↓↓↓↓↓---------------------------------------------------------------------------------------------
        btnActualizarFormacionAcademica = (Button) findViewById( R.id.xmlBtnFormacionAcademicaCAct );
        btnActualizarFormacionAcademica.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ActualizarCurriculo.this, ActualizarFormacionAcad.class );
                intent.putExtra( "DetalleFormacionAcademicaID", cIdCurriculo );
                //  Log.d( "idcurriculo",IdCurriculo );
                startActivity( intent );
            }
        } );
        //btnFormacionAcademica.setEnabled(false);

        btnActualizarOtrosCursosCurriculo = (Button) findViewById( R.id.AbrirOtrosCursosAct );
        btnActualizarOtrosCursosCurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ActualizarCurriculo.this, cPantallaOtrosCursos.class );
                intent.putExtra( "DetalleOtrosCursosID", cIdCurriculo );
                //  Log.d( "idcurriculo",IdCurriculo );
                startActivity( intent );
            }
        } );
        //btnActualizarOtrosCursosCurriculo.setEnabled(false);

        btnActualizarExperienciaLaboralCurriculo = (Button) findViewById( R.id.xmlBntExperienciaLaboralCurriculoAct );
        btnActualizarExperienciaLaboralCurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ActualizarCurriculo.this, cPantallaExperienciaLaboralCurriculo.class );
                intent.putExtra( "DetalleExperienciaLaboralID", cIdCurriculo );
                //  Log.d( "idcurriculo",IdCurriculo );
                startActivity( intent );
            }
        } );
        //btnActualizarExperienciaLaboralCurriculo.setEnabled(false);


        btnActualizarReferenciCurriculo = (Button) findViewById( R.id.xmlBtnReferenciaCurriculoCAct );
        btnActualizarReferenciCurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ActualizarCurriculo.this, cPantallaReferenciasCurriculo.class );
                intent.putExtra( "DetalleReferenciasID", cIdCurriculo );
                //  Log.d( "idcurriculo",IdCurriculo );
                startActivity( intent );
            }
        } );
//-------------botones para ir a registrar las otras informaciones ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑---------------------------------------------------------------------------------------------

        bntActualizarIdioma = findViewById( R.id.xmlBtnIdiomaAct );

        muestraidiomaAct = findViewById( R.id.tvop );

        btnIdiomascAct.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder( ActualizarCurriculo.this );
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
                        mEtxtIdiomaAct.setText( item );
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
                            mEtxtIdiomaAct.setText( "" );
                        }
                    }
                } );

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        } );
//        Log.d( "useer", userActivo );
        //   CargarCurriculoActualizar(userActivo);


        listItems = getResources().getStringArray( R.array.InfoIdiomas );
        checkedItems = new boolean[listItems.length];


        if (getIntent() != null) {
            idcurriculoact = getIntent().getStringExtra( "curriculoactualizarID" );
            if (!idcurriculoact.isEmpty()) {

                //goDetalleEmpleo(sEmpleoIdE);
                CargarCurriculoActualizar( idcurriculoact );

            }
        }


        Button btnActualizarC = findViewById( R.id.xmlBtnActualizarDatosGCAct );
        btnActualizarC.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                actualizarcurriculo( idcurriculoact );


            }
        } );
    }
//---------------------------para el boton de la fecha ↓↓↓↓↓↓↓↓↓↓↓↓↓--------------------------------------------------------------------------------------------

    public void onButtonClicked(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show( getFragmentManager(), "Date Picker" );
    }
//---------------------------para el boton de la fecha ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑---------------------------------------------------------------------------------------------

    private void CargarCurriculoActualizar(String idcurriculoact) {
        vistaCurriculoactualizar.child( idcurriculoact ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Curriculos curriculos = dataSnapshot.getValue( Curriculos.class );
                Log.d( "hola", String.valueOf( dataSnapshot ) );

                Picasso.get().load( curriculos.getImagen() ).into( imageViewcurriculoActualizar );

                etNombreAct.setText( curriculos.getNombre() );
                etApellidoAct.setText( curriculos.getApellido() );
                etCedulaAct.setText( curriculos.getCedula() );
                etEmailAct.setText( curriculos.getEmail() );
                etTelefonoAct.setText( curriculos.getTelefono() );
                etCelularAct.setText( curriculos.getCelular() );

                spinProvinciaCurriculoAct.setSelection( obtenerPosicionItem( spinProvinciaCurriculoAct, curriculos.getProvincia() ) );
                spinEstadoCivilAct.setSelection( obtenerPosicionItem( spinEstadoCivilAct, curriculos.getEstadoCivil() ) );

                etDireccionAct.setText( curriculos.getDireccion() );
                etOcupacionAct.setText( curriculos.getOcupacion() );
                mEtxtIdiomaAct.setText( curriculos.getIdioma() );

                spinGradoMayorAct.setSelection( obtenerPosicionItem( spinGradoMayorAct, curriculos.getGradomayor() ) );
                spinEstadoActualAct.setSelection( obtenerPosicionItem( spinEstadoActualAct, curriculos.getEstadoactual() ) );

                mEtxtFechaAct.setText( curriculos.getFecha() );
                etHabilidadesAct.setText( curriculos.getHabilidades() );


                //Picasso.get().load(curriculos.getImageview()).into(ImageView);

                RadioGroup RGsexo = (RadioGroup) findViewById( R.id.radiobuttonsexoAct );
                RGsexo.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.maculinoCurriAct:
                                sexo = "Masculino";
                                //Log.d("Valorestado",sEstadoEmpleoAE);
                                break;
                            case R.id.femeninaCurriAct:
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == IMAGE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            mFilePathUriAct = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap( getContentResolver(), mFilePathUriAct );
                imageViewcurriculoActualizar.setImageBitmap( bitmap );
            } catch (Exception e) {

                Toast.makeText( this, e.getMessage(), Toast.LENGTH_LONG ).show();

            }


        }
    }


    private void actualizarcurriculo(final String idcurriculoact) {

        if (mFilePathUriAct != null) {
            mProgressDialog.setTitle( "Subiendo Actualizacion..." );
            mProgressDialog.show();

            final StorageReference StorageReference2nd = mStorageReferenceAct.child( mStoragePath + System.currentTimeMillis() + "." + getFileExtension( mFilePathUriAct ) );

            UploadTask uploadTask = StorageReference2nd.putFile( mFilePathUriAct );

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
                        nombre = etNombreAct.getText().toString().trim().toLowerCase();
                        apellido = etApellidoAct.getText().toString().trim();
                        cedula = etCedulaAct.getText().toString().trim();
                        email = etEmailAct.getText().toString().trim();
                        celular = etCelularAct.getText().toString().trim();
                        telefono = etTelefonoAct.getText().toString().trim();
                        provincia = spinProvinciaCurriculoAct.getSelectedItem().toString();
                        estadoCivil = spinEstadoCivilAct.getSelectedItem().toString();
                        direccion = etDireccionAct.getText().toString().trim();
                        ocupacion = etOcupacionAct.getText().toString().trim();
                        idioma = mEtxtIdiomaAct.getText().toString().trim();
                        gradomayor = spinGradoMayorAct.getSelectedItem().toString();
                        estadoactual = spinEstadoActualAct.getSelectedItem().toString();
                        habilidades = etHabilidadesAct.getText().toString().trim();
                        fecha = mEtxtFechaAct.getText().toString().trim();

                        mProgressDialog.dismiss();

                        Log.d( "url", nombre );

                        String cIdBuscardor = userActivo;

                        // String idCurriculo  = mDatabaseReference.push().getKey();
                        // String idCurriculo  = cIdCurriculo;

                        // Log.d( "klk", idcurriculoact );

                        Curriculos curriculos = new Curriculos( idcurriculoact, cIdBuscardor, downloadURL, nombre, apellido, cedula, email, telefono, celular, provincia, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual, sexo, habilidades, fecha );

                        vistaCurriculoactualizar.child( idcurriculoact ).setValue( curriculos );

                        //  ActivarCampor();
                        // limpiarCampo();
                        Toast.makeText( ActualizarCurriculo.this, "Actualizacion subida exitosamente...", Toast.LENGTH_LONG ).show();


                    } else {
                        // Handle failures
                        // ...
                    }
                }
            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressDialog.dismiss();
                    Toast.makeText( ActualizarCurriculo.this, e.getMessage(), Toast.LENGTH_LONG ).show();

                }
            } );
        } else {
            Toast.makeText( ActualizarCurriculo.this, "Por favor, Seleccionar una Imagen", Toast.LENGTH_LONG ).show();

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( contentResolver.getType( uri ) );
    }

/*
    public void limpiarCampo() {
        etNombreAct.setText( "" );
        etApellidoAct.setText( "" );
        etCedulaAct.setText( "" );
        etEmailAct.setText( "" );
        etCelularAct.setText( "" );
        etTelefonoAct.setText( "" );
        etDireccionAct.setText( "" );

        etOcupacionAct.setText( "" );
        mEtxtIdiomaAct.setText( "" );
        etHabilidadesAct.setText( "" );
        mEtxtFechaAct.setText( "" );
    }
*/

}