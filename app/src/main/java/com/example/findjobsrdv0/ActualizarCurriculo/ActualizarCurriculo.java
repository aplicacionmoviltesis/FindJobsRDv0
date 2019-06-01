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
import com.example.findjobsrdv0.Registro_del_Curriculo.cPantallaFormacionAcademicaCurriculo;
import com.example.findjobsrdv0.Registro_del_Curriculo.cPantallaOtrosCursos;
import com.example.findjobsrdv0.Registro_del_Curriculo.cPantallaReferenciasCurriculo;
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

public class ActualizarCurriculo extends AppCompatActivity {


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


    EditText etNombre, etApellido, etCedula, etEmail, etTelefono, etCelular,
            etDireccion, etOcupacion, etHabilidades;

    Button btnIdiomasc;
    SearchableSpinner Provincia, spinEstadoCivil, spinGradoMayor, spinEstadoActual;

    TextView mEtxtFecha, mEtxtIdioma;

    String sexo;

    TextView muestraidioma;

    //  FirebaseAuth mAuth;

    String nombre, apellido, cedula, email, telefono, celular, provincia, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual, habilidades, fecha;

    //ImageView imageViewcurriculo;

    String idcurriculoact = "";
    //  curriculoactualizarID

    private Button btnActualizarFormacionAcademica, btnReferenciCurriculo, btnExperienciaLaboralCurriculo, btnOtrosCursosCurriculo, bntIdioma;


    DatabaseReference vistaCurriculoactualizar;


    //---------------imagen en el curriculo----------------------------------------------------------------------------------------------------------------

    private String mStoragePath = "Imagenes Curriculo/";

    ImageView imageViewcurriculoActualizar;

    Uri mFilePathUri;

    StorageReference mStorageReference;

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

        mStorageReference = FirebaseStorage.getInstance().getReference();

        mProgressDialog = new ProgressDialog( ActualizarCurriculo.this );
/*
        database = FirebaseDatabase.getInstance();
        databaseReferenceCurrilo = database.getReference( "Curriculos" );
*/
//----------------imagen en el curriculo----------------------------------------------------------------------------------------------------------------


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
        spinEstadoCivil = (SearchableSpinner) findViewById( R.id.spinnerestadocivil );
        spinGradoMayor = (SearchableSpinner) findViewById( R.id.spinnergradomayor );
        spinEstadoActual = (SearchableSpinner) findViewById( R.id.spinnerestadoactual );

        mEtxtFecha = (TextView) findViewById( R.id.tv );
        mEtxtIdioma = (TextView) findViewById( R.id.tvop );

        provinciasRefCurriculo = FirebaseDatabase.getInstance().getReference();
        spinProvinciaCurriculo = (Spinner) findViewById( R.id.spinnerprovincias );

        btnIdiomasc = (Button) findViewById( R.id.xmlBtnIdioma );


        userActivo = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final String cIdCurriculo = vistaCurriculoactualizar.push().getKey();

//-------------botones para ir a registrar las otras informaciones hacia abajo ↓↓↓↓↓↓↓↓↓↓↓↓↓↓---------------------------------------------------------------------------------------------
        btnActualizarFormacionAcademica = (Button) findViewById( R.id.xmlBtnFormacionAcademicaC );
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

        btnOtrosCursosCurriculo = (Button) findViewById( R.id.AbrirOtrosCursos );
        btnOtrosCursosCurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ActualizarCurriculo.this, cPantallaOtrosCursos.class );
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
                Intent intent = new Intent( ActualizarCurriculo.this, cPantallaExperienciaLaboralCurriculo.class );
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
                Intent intent = new Intent( ActualizarCurriculo.this, cPantallaReferenciasCurriculo.class );
                intent.putExtra( "DetalleReferenciasID", cIdCurriculo );
                //  Log.d( "idcurriculo",IdCurriculo );
                startActivity( intent );
            }
        } );
//-------------botones para ir a registrar las otras informaciones ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑---------------------------------------------------------------------------------------------

        bntIdioma = findViewById( R.id.xmlBtnIdioma );

        muestraidioma = findViewById( R.id.tvop );

        btnIdiomasc.setOnClickListener( new View.OnClickListener() {
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


        Button btnActualizarC = findViewById( R.id.xmlBtnActualizarDatosGC );
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

                etNombre.setText( curriculos.getNombre() );
                etApellido.setText( curriculos.getApellido() );
                etCedula.setText( curriculos.getCedula() );
                etEmail.setText( curriculos.getEmail() );
                etTelefono.setText( curriculos.getTelefono() );
                etCelular.setText( curriculos.getCelular() );

                spinProvinciaCurriculo.setSelection( obtenerPosicionItem( spinProvinciaCurriculo, curriculos.getProvincia() ) );
                spinEstadoCivil.setSelection( obtenerPosicionItem( spinEstadoCivil, curriculos.getEstadoCivil() ) );

                etDireccion.setText( curriculos.getDireccion() );
                etOcupacion.setText( curriculos.getOcupacion() );
                mEtxtIdioma.setText( curriculos.getIdioma() );

                spinGradoMayor.setSelection( obtenerPosicionItem( spinGradoMayor, curriculos.getGradomayor() ) );
                spinEstadoActual.setSelection( obtenerPosicionItem( spinEstadoActual, curriculos.getEstadoactual() ) );

                mEtxtFecha.setText( curriculos.getFecha() );
                etHabilidades.setText( curriculos.getHabilidades() );


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
                imageViewcurriculoActualizar.setImageBitmap( bitmap );
            } catch (Exception e) {

                Toast.makeText( this, e.getMessage(), Toast.LENGTH_LONG ).show();

            }


        }
    }


    private void actualizarcurriculo(final String idcurriculoact) {

        if (mFilePathUri != null) {
            mProgressDialog.setTitle( "Subiendo Actualizacion..." );
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
                        // String imagen = imageViewcurriculo.getImageMatrix().toString();
                        nombre = etNombre.getText().toString().trim().toLowerCase();
                        apellido = etApellido.getText().toString().trim();
                        cedula = etCedula.getText().toString().trim();
                        email = etEmail.getText().toString().trim();
                        celular = etCelular.getText().toString().trim();
                        telefono = etTelefono.getText().toString().trim();
                        provincia = Provincia.getSelectedItem().toString();
                        estadoCivil = spinEstadoCivil.getSelectedItem().toString();
                        direccion = etDireccion.getText().toString().trim();
                        ocupacion = etOcupacion.getText().toString().trim();
                        idioma = mEtxtIdioma.getText().toString().trim();
                        gradomayor = spinGradoMayor.getSelectedItem().toString();
                        estadoactual = spinEstadoActual.getSelectedItem().toString();
                        habilidades = etHabilidades.getText().toString().trim();
                        fecha = mEtxtFecha.getText().toString().trim();

                        mProgressDialog.dismiss();

                        Log.d( "url", nombre );

                        String cIdBuscardor = userActivo;

                        // String idCurriculo  = mDatabaseReference.push().getKey();
                        // String idCurriculo  = cIdCurriculo;

                        // Log.d( "klk", idcurriculoact );

                        Curriculos curriculos = new Curriculos( idcurriculoact, cIdBuscardor, downloadURL, nombre, apellido, cedula, email, telefono, celular, provincia, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual, habilidades, fecha );

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
*/

}