package com.example.findjobsrdv0.Administradores;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import android.content.pm.ActivityInfo;

import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.findjobsrdv0.GeneralesApp.Provincias;
import com.example.findjobsrdv0.Pantallas_CurriculosCompleto.cPantallaRegistrarCurriculo;
import com.example.findjobsrdv0.R;
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
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class PantallaAgregarUniversidades extends AppCompatActivity {

    private String sIdUniversidadR = "";
    private String sNombreUniRegistrar, sUbicacionUniRegistrar, sImagenUniRegistrar, sIdUserAdminUniRegistrar, sTelefonoUniRegistrar, sDireccionUniRegistrar, sPaginaWebUniRegistrar;
    private EditText editNombreUniR, editTelefonoUniR, editDireccionUniR, editPaginaWebUniR;
    private ImageView ImageUniRegistrar;
    private DatabaseReference universidadesRefRegistrar;
    private FirebaseDatabase UniDatabase;
    private ProgressDialog mProgressDialogR;


    /////Spinner Provincia
    private SearchableSpinner spinProvUniversidadR;
    private DatabaseReference provinciasRefUniRegistrar;
    private List<Provincias> provincias;
    boolean IsFirstTimeClick = true;

    /////Spinner Provincia

    /////Imagen
    private String mStoragePath = "Imagenes Universidades/";
    private Uri mFilePathUri;
    private StorageReference mStorageReference;
    int IMAGE_REQUEST_CODE = 5;
    /////Imagen


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_agregar_universidades);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);




        mProgressDialogR = new ProgressDialog(PantallaAgregarUniversidades.this);

        ImageUniRegistrar = (ImageView) findViewById(R.id.xmlImagenUniRegistrar);

        editNombreUniR = (EditText) findViewById(R.id.xmlEditNombreUniRegistrar);
        editTelefonoUniR = (EditText) findViewById(R.id.xmlEditTelefonoUniRegistrar);
        editDireccionUniR = (EditText) findViewById(R.id.xmlEditDireccionUniRegistrar);
        editPaginaWebUniR = (EditText) findViewById(R.id.xmlEditPaginaWebUniRegistrar);

        spinProvUniversidadR = (SearchableSpinner) findViewById(R.id.xmlSpinUbicacionUniRegistrar);

        UniDatabase = FirebaseDatabase.getInstance();
        universidadesRefRegistrar = UniDatabase.getReference("Universidades");

        /////Spinner Provincia
        provinciasRefUniRegistrar = FirebaseDatabase.getInstance().getReference();

        mStorageReference = FirebaseStorage.getInstance().getReference();

        ImageUniRegistrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType( "image/*" );
                galleryIntent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( galleryIntent, "Seleccionar Imagen" ), IMAGE_REQUEST_CODE );
            }
        } );

        spinProvUniversidadR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sUbicacionUniRegistrar = spinProvUniversidadR.getSelectedItem().toString();
                    Log.d("valorSpinProv", sUbicacionUniRegistrar);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        provinciasRefUniRegistrar.child("provincias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListProvincias = new ArrayList<String>();
                for (DataSnapshot provinciaSnapshot : dataSnapshot.getChildren()) {
                    String provinciaName = provinciaSnapshot.child("Nombre_Provincia").getValue(String.class);
                    ListProvincias.add(provinciaName);
                }

                ArrayAdapter<String> provinciasAdapter = new ArrayAdapter<String>(PantallaAgregarUniversidades.this, android.R.layout.simple_spinner_item, ListProvincias);
                provinciasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinProvUniversidadR.setAdapter(provinciasAdapter);
                spinProvUniversidadR.setTitle("Seleccionar Provincia");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/////Spinner Provincia

    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reportar_problema, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_EnviarProblema) {
            //process your onClick here
            RegistrarUniversidad();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                ImageUniRegistrar.setImageBitmap( bitmap );
            } catch (Exception e) {
                Toast.makeText( this, e.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }
    }


    private void RegistrarUniversidad() {
        mProgressDialogR.setTitle("Añadiendo Universidad...");
        mProgressDialogR.show();

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
                    String sImagenUniRegistrar = downloadUri.toString();
                    Log.d( "url", sImagenUniRegistrar );
                    sNombreUniRegistrar = editNombreUniR.getText().toString().trim();
                    sTelefonoUniRegistrar = editTelefonoUniR.getText().toString().trim();
                    sDireccionUniRegistrar = editDireccionUniR.getText().toString().trim();
                    sPaginaWebUniRegistrar = editPaginaWebUniR.getText().toString().trim();
                    //sUbicacionUniversidad = "";
                    //sImagenUniRegistrar = "https://firebasestorage.googleapis.com/v0/b/findjobsrd.appspot.com/o/Imagenes%20Provincia%2Fbonao.jpg?alt=media&token=287c737f-70b4-4e8e-bcf0-11e077edc509";
                    //sIdUserAdminUniRegistrar = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    sIdUserAdminUniRegistrar = "yo";

                    if (TextUtils.isEmpty( sNombreUniRegistrar )) {
                        Toast.makeText( PantallaAgregarUniversidades.this, "Por favor, Ingrese el nombre", Toast.LENGTH_LONG ).show();
                        mProgressDialogR.dismiss();

                        return;
                    }

                    if (TextUtils.isEmpty( sUbicacionUniRegistrar )) {
                        Toast.makeText( PantallaAgregarUniversidades.this, "Spinner vacío, por favor seleccione la Ubicacion", Toast.LENGTH_LONG ).show();
                        mProgressDialogR.dismiss();

                        return;
                    }
                    if (TextUtils.isEmpty( sTelefonoUniRegistrar )) {
                        Toast.makeText( PantallaAgregarUniversidades.this, "Por favor, Ingrese el numero de telefono", Toast.LENGTH_LONG ).show();
                        mProgressDialogR.dismiss();

                        return;
                    }

                    if (TextUtils.isEmpty( sDireccionUniRegistrar )) {
                        Toast.makeText( PantallaAgregarUniversidades.this, "Por favor, Ingrese la direccion de la institucion", Toast.LENGTH_LONG ).show();
                        mProgressDialogR.dismiss();

                        return;
                    }
                    if (TextUtils.isEmpty( sPaginaWebUniRegistrar )) {
                        Toast.makeText( PantallaAgregarUniversidades.this, "Por favor, Ingrese la pagina web de la institucion", Toast.LENGTH_LONG ).show();
                        mProgressDialogR.dismiss();

                        return;
                    }

                    mProgressDialogR.dismiss();

                    sIdUniversidadR = universidadesRefRegistrar.push().getKey();

                    Universidades universidad = new Universidades( sIdUniversidadR, sNombreUniRegistrar, sUbicacionUniRegistrar, sImagenUniRegistrar, sDireccionUniRegistrar, sTelefonoUniRegistrar, sPaginaWebUniRegistrar, sIdUserAdminUniRegistrar );
                    universidadesRefRegistrar.child( sIdUniversidadR ).setValue( universidad );
                    Toast.makeText( PantallaAgregarUniversidades.this, "La Universidad se añadio exitosamente", Toast.LENGTH_LONG ).show();
                    LimpiarCampos();

                }else {
                }
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDialogR.dismiss();
                Toast.makeText( PantallaAgregarUniversidades.this, e.getMessage(), Toast.LENGTH_LONG ).show();
            }
        } );
    }

    private void LimpiarCampos() {

        editNombreUniR.setText("");
        editTelefonoUniR.setText("");
        editDireccionUniR.setText("");
        editPaginaWebUniR.setText("");
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( contentResolver.getType( uri ) );
    }
}
