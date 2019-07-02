package com.example.findjobsrdv0.Administradores;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.findjobsrdv0.GeneralesApp.Areas;
import com.example.findjobsrdv0.GeneralesApp.Provincias;
import com.example.findjobsrdv0.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PantallaAgregarProvincias extends AppCompatActivity {

    private ImageView ImageProvReg;

    private String sIdProvinciaProvReg, sIdUserAdminProvReg, sImagenProvReg;

    private String sNombreProvReg, sDescripcionProvReg, sDivTerritorialProvReg, sPoblacionProvReg, sEconomiaProvReg, sClimaProvReg, sAtractivosProvReg;
    private EditText editNombreProvReg, editDescripcionProvReg, editDivTerritorialProvReg,
            editPoblacionProvReg, editEconomiaProvReg, editClimaProvReg, editAtractivosProvReg;


    private DatabaseReference ProvinciaRefRegistrar;
    private FirebaseDatabase ProvinciaDatabase;
    private ProgressDialog mProgressDialogProv;


    /////Imagen
    private String mStoragePath = "Imagenes Provincia/";
    private Uri mFilePathUri;
    private StorageReference mStorageReference;
    int IMAGE_REQUEST_CODE = 5;
    /////Imagen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pantalla_agregar_provincias );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayShowHomeEnabled( true );

        mProgressDialogProv = new ProgressDialog( PantallaAgregarProvincias.this );


        editNombreProvReg = (EditText) findViewById( R.id.xmlEditNombreProvincia );
        editDescripcionProvReg = (EditText) findViewById( R.id.xmlEditDescripcionProvincia );
        editDivTerritorialProvReg = (EditText) findViewById( R.id.xmlEditDivisionProvincia );
        editPoblacionProvReg = (EditText) findViewById( R.id.xmlEditPoblacionProvincia );
        editEconomiaProvReg = (EditText) findViewById( R.id.xmlEditEconomiaProvincia );
        editClimaProvReg = (EditText) findViewById( R.id.xmlEditClimaProvincia );
        editAtractivosProvReg = (EditText) findViewById( R.id.xmlEditAtractivosProvincia );

        mStorageReference = FirebaseStorage.getInstance().getReference();
        ImageProvReg = (ImageView) findViewById( R.id.xmlImagenProvincia );
        ImageProvReg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType( "image/*" );
                galleryIntent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( galleryIntent, "Seleccionar Imagen" ), IMAGE_REQUEST_CODE );
            }
        } );


        ProvinciaDatabase = FirebaseDatabase.getInstance();
        ProvinciaRefRegistrar = ProvinciaDatabase.getReference( "Provincias" );
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
                ImageProvReg.setImageBitmap( bitmap );
            } catch (Exception e) {
                Toast.makeText( this, e.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }
    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_reportar_problema, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_EnviarProblema) {
            //process your onClick here
            RegistrarProvincia();
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    private void RegistrarProvincia() {
        mProgressDialogProv.setTitle( "Añadiendo Provincia..." );
        mProgressDialogProv.show();

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
                    String sImagenProvReg = downloadUri.toString();

                    sNombreProvReg = editNombreProvReg.getText().toString().trim();
                    sDescripcionProvReg = editDescripcionProvReg.getText().toString().trim();
                    sDivTerritorialProvReg = editDivTerritorialProvReg.getText().toString().trim();
                    sPoblacionProvReg = editPoblacionProvReg.getText().toString().trim();
                    sEconomiaProvReg = editEconomiaProvReg.getText().toString().trim();
                    sClimaProvReg = editClimaProvReg.getText().toString().trim();
                    sAtractivosProvReg = editAtractivosProvReg.getText().toString().trim();


                    //sImagenProvReg = "https://firebasestorage.googleapis.com/v0/b/findjobsrd.appspot.com/o/Imagenes%20Provincia%2Fbonao.jpg?alt=media&token=287c737f-70b4-4e8e-bcf0-11e077edc509";

                    //sIdUserAdminProvReg = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    //sIdUserAdminProvReg = "yo";

                    sIdProvinciaProvReg = ProvinciaRefRegistrar.push().getKey();


                    if (TextUtils.isEmpty( sNombreProvReg )) {
                        Toast.makeText( PantallaAgregarProvincias.this, "Por favor, Ingrese el nombre", Toast.LENGTH_LONG ).show();
                        mProgressDialogProv.dismiss();
                        return;
                    }

                    if (TextUtils.isEmpty( sDescripcionProvReg )) {
                        Toast.makeText( PantallaAgregarProvincias.this, "Por favor, Ingrese la descripcion de la provincia", Toast.LENGTH_LONG ).show();
                        mProgressDialogProv.dismiss();
                        return;
                    }
                    if (TextUtils.isEmpty( sClimaProvReg )) {
                        Toast.makeText( PantallaAgregarProvincias.this, "Por favor, Ingrese el clima predominante en dicha provincia", Toast.LENGTH_LONG ).show();
                        mProgressDialogProv.dismiss();
                        return;
                    }


                    Provincias provincia = new Provincias( sIdProvinciaProvReg, sNombreProvReg, sDescripcionProvReg, sDivTerritorialProvReg, sPoblacionProvReg, sImagenProvReg, sIdUserAdminProvReg, sEconomiaProvReg, sClimaProvReg, sAtractivosProvReg );
                    ProvinciaRefRegistrar.child( sIdProvinciaProvReg ).setValue( provincia );
                    Toast.makeText( PantallaAgregarProvincias.this, "La Provincia se añadio exitosamente", Toast.LENGTH_LONG ).show();
                    LimpiarCampos();
                    mProgressDialogProv.dismiss();
                } else {
                }
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDialogProv.dismiss();
                Toast.makeText( PantallaAgregarProvincias.this, e.getMessage(), Toast.LENGTH_LONG ).show();
            }
        } );
    }

    private void LimpiarCampos() {

        editNombreProvReg.setText( "" );
        editDescripcionProvReg.setText( "" );
        editDivTerritorialProvReg.setText( "" );
        editPoblacionProvReg.setText( "" );
        editEconomiaProvReg.setText( "" );
        editClimaProvReg.setText( "" );
        editAtractivosProvReg.setText( "" );

    }
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( contentResolver.getType( uri ) );
    }
}
