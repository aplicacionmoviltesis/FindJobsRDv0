package com.example.findjobsrdv0.Administradores;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import android.content.pm.ActivityInfo;

import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.findjobsrdv0.GeneralesApp.Provincias;
import com.example.findjobsrdv0.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class PantallaActualizarUniversidad extends AppCompatActivity {

    private String sIdUniversidad = "";
    private String sNombreUniversidad, sUbicacionUniversidad, sImagenUniversidad, sIdUserAdminUni, sTelefonoUni, sDireccionUni, sPaginaWebUni;
    private EditText editNombreUni, editTelefonoUni, editDireccionUni, editPaginaWebUni;
    private ImageView ImageUniversidad;
    private DatabaseReference universidadesRefActualizar;
    private FirebaseDatabase UniDatabase;
    private ProgressDialog mProgressDialog;


    /////Spinner Provincia
    private SearchableSpinner spinProvUniversidad;
    private DatabaseReference provinciasRefUniActualizar;
    private List<Provincias> provincias;
    boolean IsFirstTimeClick = true;

    /////Spinner Provincia

    /////Imagen
    private String mStoragePath = "Imagenes Universidades/";
    Uri mFilePathUri;
    StorageReference mStorageReference;
    int IMAGE_REQUEST_CODE = 5;

    //String userActivo;
    /////Imagen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_actualizar_univesidad);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayShowHomeEnabled( true );

        mProgressDialog = new ProgressDialog( PantallaActualizarUniversidad.this );

        ImageUniversidad = (ImageView) findViewById( R.id.xmlImagenUniversidad );

        editNombreUni = (EditText) findViewById( R.id.xmlEditNombreUniversidad );
        editTelefonoUni = (EditText) findViewById( R.id.xmlEditTelefonoUniversidad );
        editDireccionUni = (EditText) findViewById( R.id.xmlEditDireccionUniversidad );
        editPaginaWebUni = (EditText) findViewById( R.id.xmlEditPaginaWebUniversidad );


        spinProvUniversidad = (SearchableSpinner) findViewById( R.id.xmlSpinUbicacionUniversidad );

        mStorageReference = getInstance().getReference();

        ImageUniversidad.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType( "image/*" );
                galleryIntent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( galleryIntent, "Seleccionar Imagen" ), IMAGE_REQUEST_CODE );
            }
        } );

       // userActivo = FirebaseAuth.getInstance().getCurrentUser().getUid();


        editNombreUni.setEnabled( false );
        spinProvUniversidad.setEnabled( false );
        editTelefonoUni.setEnabled( false );
        editDireccionUni.setEnabled( false );
        editPaginaWebUni.setEnabled( false );

        UniDatabase = FirebaseDatabase.getInstance();
        universidadesRefActualizar = UniDatabase.getReference( "Universidades" );

        /////Spinner Provincia

        provinciasRefUniActualizar = FirebaseDatabase.getInstance().getReference();

        spinProvUniversidad.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sUbicacionUniversidad = spinProvUniversidad.getSelectedItem().toString();
                    Log.d( "valorSpinProv", sUbicacionUniversidad );
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        } );

        provinciasRefUniActualizar.child( "provincias" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListProvincias = new ArrayList<String>();
                for (DataSnapshot provinciaSnapshot : dataSnapshot.getChildren()) {
                    String provinciaName = provinciaSnapshot.child( "Nombre_Provincia" ).getValue( String.class );
                    ListProvincias.add( provinciaName );
                }

                ArrayAdapter<String> provinciasAdapter = new ArrayAdapter<String>( PantallaActualizarUniversidad.this, android.R.layout.simple_spinner_item, ListProvincias );
                provinciasAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spinProvUniversidad.setAdapter( provinciasAdapter );
                spinProvUniversidad.setTitle( "Seleccionar Provincia" );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

/////Spinner Provincia

        if (getIntent() != null) {
            sIdUniversidad = getIntent().getStringExtra( "sUniversidadId" );
            if (!sIdUniversidad.isEmpty()) {
                Log.d( "holap", String.valueOf( sIdUniversidad ) );

                CargarUniversidad( sIdUniversidad );
            }
        }
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
                ImageUniversidad.setImageBitmap( bitmap );
            } catch (Exception e) {

                Toast.makeText( this, e.getMessage(), Toast.LENGTH_LONG ).show();

            }
        }
    }

    private void CargarUniversidad(String sIdUniversidad) {
        Log.d( "holap", String.valueOf( sIdUniversidad ) );

        universidadesRefActualizar.child( sIdUniversidad ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d( "holap", String.valueOf( dataSnapshot ) );
                sImagenUniversidad = dataSnapshot.child( "sImagenUniversidad" ).getValue( String.class );
                if (sImagenUniversidad != null && !sImagenUniversidad.isEmpty()) {
                    Picasso.get().load( sImagenUniversidad ).into( ImageUniversidad );


                    Universidades universidades = dataSnapshot.getValue( Universidades.class );
                    Log.d( "holap", String.valueOf( universidades ) );
                    //Log.d( "holap", String.valueOf( universidades ) );

                    //Picasso.get().load( universidades.getsImagenUniversidad() ).into( ImageUniversidad );
                    editNombreUni.setText( universidades.getsNombreUniversidad() );
                    spinProvUniversidad.setSelection( obtenerPosicionItem( spinProvUniversidad, universidades.getsUbicacionUniversidad() ) );
                    editTelefonoUni.setText( universidades.getsTelefonoUniversidad() );
                    editDireccionUni.setText( universidades.getsDireccionUniversidad() );
                    editPaginaWebUni.setText( universidades.getsPaginaWebUniversidad() );
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
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menuperfil, menu );
        return true;
    }

//    private String getFileExtension(Uri uri) {
//        ContentResolver contentResolver = getContentResolver();
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//        return mimeTypeMap.getExtensionFromMimeType( contentResolver.getType( uri ) );
//    }


    private void DeleteImagenAnterior() {
        if (sImagenUniversidad != null && !sImagenUniversidad.isEmpty()) {
            final StorageReference mPitureRef = getInstance().getReferenceFromUrl( sImagenUniversidad );
            mPitureRef.delete().addOnSuccessListener( new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText( PantallaActualizarUniversidad.this, "Eliminando Imagen...", Toast.LENGTH_LONG ).show();
                    Log.d( "link foto", String.valueOf( mPitureRef ) );
                    SubirNuevaImagen();
                }
            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( PantallaActualizarUniversidad.this, e.getMessage(), Toast.LENGTH_LONG ).show();
                    mProgressDialog.dismiss();
                }
            } );
        } else {
            Toast.makeText( PantallaActualizarUniversidad.this, "No hay imagen agregada", Toast.LENGTH_LONG ).show();
            SubirNuevaImagen();
        }
    }

    private void SubirNuevaImagen() {
        String imageName = System.currentTimeMillis() + ".png";
        //String imageName = System.currentTimeMillis() + getFileExtension(mFilePathUri);

        StorageReference storageReference2do = mStorageReference.child( mStoragePath + imageName );

        Bitmap bitmap = ((BitmapDrawable) ImageUniversidad.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.PNG, 100, baos );

        byte[] data = baos.toByteArray();
        UploadTask uploadTask = storageReference2do.putBytes( data );
        uploadTask.addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText( PantallaActualizarUniversidad.this, "Nueva Imagen Subida...", Toast.LENGTH_LONG ).show();
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri downloadUri = uriTask.getResult();
                ActualizarUniversidad( downloadUri.toString() );

            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( PantallaActualizarUniversidad.this, e.getMessage(), Toast.LENGTH_LONG ).show();
                mProgressDialog.dismiss();
            }
        } );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_EditarPerfil) {
            //process your onClick here
            ActivarCampos();
            return true;
        }
        if (id == R.id.menu_ActualizarPerfil) {
            //process your onClick here
            DeleteImagenAnterior();

            return true;
        }

        return super.onOptionsItemSelected( item );
    }


    private void ActualizarUniversidad(String foto) {
        mProgressDialog.setTitle( "Actualizando..." );
        mProgressDialog.show();

        sNombreUniversidad = editNombreUni.getText().toString().trim();
        sTelefonoUni = editTelefonoUni.getText().toString().trim();
        sDireccionUni = editDireccionUni.getText().toString().trim();
        sPaginaWebUni = editPaginaWebUni.getText().toString().trim();
        //sUbicacionUniversidad = "";
        sIdUserAdminUni = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (TextUtils.isEmpty( sNombreUniversidad )) {
            Toast.makeText( this, "Por favor, Ingrese el nombre", Toast.LENGTH_LONG ).show();
            mProgressDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty( sUbicacionUniversidad )) {
            Toast.makeText( this, "Spinner vacío, por favor seleccione la Ubicacion", Toast.LENGTH_LONG ).show();
            mProgressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty( sTelefonoUni )) {
            Toast.makeText( this, "Por favor, Ingrese el numero de telefono", Toast.LENGTH_LONG ).show();
            mProgressDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty( sDireccionUni )) {
            Toast.makeText( this, "Por favor, Ingrese la direccion de la institucion", Toast.LENGTH_LONG ).show();
            mProgressDialog.dismiss();

            return;
        }
        if (TextUtils.isEmpty( sPaginaWebUni )) {
            Toast.makeText( this, "Por favor, Ingrese la pagina web de la institucion", Toast.LENGTH_LONG ).show();
            mProgressDialog.dismiss();

            return;
        }

//
//<<<<<<< HEAD
//        Universidades universidad = new Universidades(sIdUniversidad,sNombreUniversidad,sUbicacionUniversidad,sImagenUniversidad, sDireccionUni,sTelefonoUni,sPaginaWebUni,sIdUserAdminUni);
//        universidadesRefActualizar.child(sIdUniversidad).setValue(universidad);
//        Toast.makeText(this, "La Actualizacion se realizo exitosamente", Toast.LENGTH_LONG).show();
//=======
        Universidades universidad = new Universidades( sIdUniversidad, sNombreUniversidad, sUbicacionUniversidad, foto, sDireccionUni, sTelefonoUni, sPaginaWebUni, sIdUserAdminUni );
        universidadesRefActualizar.child( sIdUniversidad ).setValue( universidad );
        Toast.makeText( this, "La Actualizacion se Actualizo exitosamente", Toast.LENGTH_LONG ).show();
//>>>>>>> origin/master
        mProgressDialog.dismiss();

        Intent intent = new Intent( PantallaActualizarUniversidad.this, PantallaListaUniversidades.class );
        startActivity( intent );

    }

    private void ActivarCampos() {

        spinProvUniversidad.setEnabled( true );
        editNombreUni.setEnabled( true );
        editTelefonoUni.setEnabled( true );
        editDireccionUni.setEnabled( true );
        editPaginaWebUni.setEnabled( true );
    }

//    private void VerificarBuscador(String userActivo) {
//        universidadesRefActualizar.child( userActivo ).addListenerForSingleValueEvent( new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                sImagenUniversidad = dataSnapshot.child( "imagenperfilB" ).getValue( String.class );
//                if (sImagenUniversidad != null && !sImagenUniversidad.isEmpty()) {
//                    Picasso.get().load( sImagenUniversidad ).into( ImageUniversidad );
//
//
////                sImagenUniversidad = dataSnapshot.child("sImagenUniversidad").getValue(String.class);
////                if (sImagenUniversidad != null && !sImagenUniversidad.isEmpty()) {
////                    Picasso.get().load( sImagenUniversidad ).into( ImageUniversidad );
////                }else
////                {
////                    Glide.with( PantallaActualizarUniversidad.this ).load( FotoPerfilCorreo ).into( ImageUniversidad );
////                }
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        } );
//    }
}
