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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.findjobsrdv0.Adaptadores_Administrador.Areas;
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

public class PantallaAgregarAreaDeTrabajo extends AppCompatActivity {

    private String sIdAreaR;
    private String sNombreAreaRegistrar, sDescAreaRegistrar, sImagenAreaRegistrar, sIdUserAdminAreaRegistrar, sSubAreasRegistrar;

    private EditText editNombreAreaR, editDescAreaRegistrar, editSubAreasRegistrar;
    private ImageView ImageAreaRegistrar;

    private DatabaseReference AreaRefRegistrar;
    private FirebaseDatabase AreaDatabase;
    private ProgressDialog mProgressDialogR;

    /////Imagen
    private String mStoragePath = "Imagenes Areas/";
    private Uri mFilePathUri;
    private StorageReference mStorageReference;
    int IMAGE_REQUEST_CODE = 5;
    /////Imagen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pantalla_agregar_area_de_trabajo );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayShowHomeEnabled( true );

        mProgressDialogR = new ProgressDialog( PantallaAgregarAreaDeTrabajo.this );


        mStorageReference = FirebaseStorage.getInstance().getReference();

        ImageAreaRegistrar = (ImageView) findViewById( R.id.xmlImagenAreaRegistrar );

        ImageAreaRegistrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType( "image/*" );
                galleryIntent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( galleryIntent, "Seleccionar Imagen" ), IMAGE_REQUEST_CODE );
            }
        } );

        editNombreAreaR = (EditText) findViewById( R.id.xmlEditNombreAreaRegistrar );
        editDescAreaRegistrar = (EditText) findViewById( R.id.xmlEdiDescripcionAreaRegistrar );
        editSubAreasRegistrar = (EditText) findViewById( R.id.xmlEditSubAreasRegistrar );

        AreaDatabase = FirebaseDatabase.getInstance();
        AreaRefRegistrar = AreaDatabase.getReference(getResources().getString(R.string.Ref_Areas));
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
                ImageAreaRegistrar.setImageBitmap( bitmap );
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
            RegistrarArea();
            return true;
        }
        return super.onOptionsItemSelected( item );
    }


    private void RegistrarArea() {
        mProgressDialogR.setTitle( "Añadiendo Area..." );
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

                    sNombreAreaRegistrar = editNombreAreaR.getText().toString().trim();
                    sDescAreaRegistrar = editDescAreaRegistrar.getText().toString().trim();
                    sSubAreasRegistrar = editSubAreasRegistrar.getText().toString().trim();
                    //sImagenAreaRegistrar = "https://firebasestorage.googleapis.com/v0/b/findjobsrd.appspot.com/o/Imagenes%20Provincia%2Fbonao.jpg?alt=media&token=287c737f-70b4-4e8e-bcf0-11e077edc509";
                    //sIdUserAdminAreaRegistrar = FirebaseAuth.getInstance().getCurrentUser().getUid();
                   // sIdUserAdminAreaRegistrar = "yo";

                    if (TextUtils.isEmpty( sNombreAreaRegistrar )) {
                        Toast.makeText( PantallaAgregarAreaDeTrabajo.this, "Por favor, Ingrese el nombre", Toast.LENGTH_LONG ).show();
                        mProgressDialogR.dismiss();

                        return;
                    }

                    if (TextUtils.isEmpty( sDescAreaRegistrar )) {
                        Toast.makeText( PantallaAgregarAreaDeTrabajo.this, "Por favor, Ingrese la descripcion del area", Toast.LENGTH_LONG ).show();
                        mProgressDialogR.dismiss();

                        return;
                    }
                    if (TextUtils.isEmpty( sSubAreasRegistrar )) {
                        Toast.makeText( PantallaAgregarAreaDeTrabajo.this, "Por favor, Ingrese alguna Sub-Area relacionada", Toast.LENGTH_LONG ).show();
                        mProgressDialogR.dismiss();

                        return;
                    }

                    sIdAreaR = AreaRefRegistrar.push().getKey();

                    Areas areas = new Areas( sIdAreaR, sNombreAreaRegistrar, sDescAreaRegistrar, sImagenUniRegistrar, sSubAreasRegistrar, sIdUserAdminAreaRegistrar );
                    AreaRefRegistrar.child( sIdAreaR ).setValue( areas );
                    Toast.makeText( PantallaAgregarAreaDeTrabajo.this, "El Area se añadio exitosamente", Toast.LENGTH_LONG ).show();
                    LimpiarCampos();
                    mProgressDialogR.dismiss();

                } else {
                }
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDialogR.dismiss();
                Toast.makeText( PantallaAgregarAreaDeTrabajo.this, e.getMessage(), Toast.LENGTH_LONG ).show();
            }
        } );
    }

    private void LimpiarCampos() {

        editNombreAreaR.setText( "" );
        editDescAreaRegistrar.setText( "" );
        editSubAreasRegistrar.setText( "" );
    }
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( contentResolver.getType( uri ) );
    }
}
