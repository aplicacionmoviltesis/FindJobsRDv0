package com.example.findjobsrdv0.GeneralesApp;


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

import com.example.findjobsrdv0.R;
import com.google.android.gms.tasks.Continuation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PantallaReportarProblemas extends AppCompatActivity {


    //private ImageView ImagenUno,ImagenDos,ImagenTres;
//    int IMAGE_REQUEST_CODE = 5;
//    Uri mFilePathUriReportProblem;

    private ProgressDialog mProgressDialogReportarProblema;

    private String sIdProblemAppReport, sTituloProblem, sDescripcionProblem, sFechaProblem, sImagenProblem, sIdUserReport;
    private EditText editTituloProblem, editDecripcionProblem;
    private ImageView ImagenUno;

    private DatabaseReference ProblemReportRefRegistrar;
    private FirebaseDatabase ProblemDatabase;

    /////Imagen
    private String mStoragePath = "Imagenes Problemas/";
    private Uri mFilePathUri;
    private StorageReference mStorageReference;
    int IMAGE_REQUEST_CODE = 5;
    /////Imagen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pantalla_reportar_problemas );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayShowHomeEnabled( true );

//        ImagenDos = (ImageView) findViewById(R.id.xmlImagenDos);
//        ImagenTres = (ImageView) findViewById(R.id.xmlImagenTres);

        mProgressDialogReportarProblema = new ProgressDialog( PantallaReportarProblemas.this );

        editTituloProblem = (EditText) findViewById( R.id.xmlEditTituloProblem );
        editDecripcionProblem = (EditText) findViewById( R.id.xmlEditReportarProblema );

        ProblemDatabase = FirebaseDatabase.getInstance();
        ProblemReportRefRegistrar = ProblemDatabase.getReference( "ProblemasReportadosApp" );

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "EEE dd MMM yyyy" );
        sFechaProblem = simpleDateFormat.format( new Date() );

        mStorageReference = FirebaseStorage.getInstance().getReference();

        ImagenUno = (ImageView) findViewById( R.id.xmlImagenUno );
        ImagenUno.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType( "image/*" );
                galleryIntent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( galleryIntent, "Seleccionar Imagen" ), IMAGE_REQUEST_CODE );
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
                ImagenUno.setImageBitmap( bitmap );
            } catch (Exception e) {
                Toast.makeText( this, e.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == IMAGE_REQUEST_CODE
//                && resultCode == RESULT_OK
//                && data != null
//                && data.getData() != null) {
//
//            mFilePathUriReportProblem = data.getData();
//
//            try {
//
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mFilePathUriReportProblem);
//                ImagenDos.setImageBitmap(bitmap);
//                ImagenUno.setImageBitmap(bitmap);
//
//            } catch (Exception e) {
//
//                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        }
//    }

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
            EnviarProblema();
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    private void EnviarProblema() {
        mProgressDialogReportarProblema.setTitle( "Reportando Problema..." );
        mProgressDialogReportarProblema.show();

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
                    String sImagenProblem = downloadUri.toString();

                    sIdProblemAppReport = ProblemReportRefRegistrar.push().getKey();

                    sTituloProblem = editTituloProblem.getText().toString().trim();
                    sDescripcionProblem = editDecripcionProblem.getText().toString().trim();

                    //sImagenProblem = "";
                    sIdUserReport = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    //sFechaProblem = "";

//        sImagenProblem = "https://firebasestorage.googleapis.com/v0/b/findjobsrd.appspot.com/o/Imagenes%20Provincia%2Fbonao.jpg?alt=media&token=287c737f-70b4-4e8e-bcf0-11e077edc509";
//        sIdUserReport = "yo";


                    if (TextUtils.isEmpty( sTituloProblem )) {
                        Toast.makeText( PantallaReportarProblemas.this, "Por favor, Titula o Agrega alguna palabra relacionada al problema", Toast.LENGTH_LONG ).show();
                        mProgressDialogReportarProblema.dismiss();
                        return;
                    }

                    if (TextUtils.isEmpty( sDescripcionProblem )) {
                        Toast.makeText( PantallaReportarProblemas.this, "Por favor, Describe el problema con la mas claridad y brevedad posible", Toast.LENGTH_LONG ).show();
                        mProgressDialogReportarProblema.dismiss();
                        return;
                    }


                    ProblemasAppReportar problemasAppReportar = new ProblemasAppReportar( sIdProblemAppReport, sTituloProblem, sDescripcionProblem, sFechaProblem, sImagenProblem, sIdUserReport );
                    ProblemReportRefRegistrar.child( sIdProblemAppReport ).setValue( problemasAppReportar );
                    Toast.makeText( PantallaReportarProblemas.this, "Su Problema se Reporto exitosamente, le estaremos resolviendo lo mas pronto posible", Toast.LENGTH_LONG ).show();
                    LimpiarCampos();
                    mProgressDialogReportarProblema.dismiss();

                } else {
                }
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDialogReportarProblema.dismiss();
                Toast.makeText( PantallaReportarProblemas.this, e.getMessage(), Toast.LENGTH_LONG ).show();
            }
        } );

    }

    private void LimpiarCampos() {
        editTituloProblem.setText( "" );
        editDecripcionProblem.setText( "" );
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( contentResolver.getType( uri ) );
    }
}
