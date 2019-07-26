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
import android.util.Log;
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

    private ProgressDialog mProgressDialogReportarProblema;

    private String sIdProblemAppReport, sTituloProblem, sDescripcionProblem, sFechaProblem, sImagenProblem, sIdUserReport;
    private EditText editTituloProblem, editDecripcionProblem;
    private ImageView ImagenReportProblem;

    private String sEstadoReportProblem;

    private DatabaseReference ProblemReportRefRegistrar;
    private FirebaseDatabase ProblemDatabase;

    /////Imagen
    private String mStoragePath;
    private Uri mFilePathUri;
    private StorageReference mStorageReference;
    int IMAGE_REQUEST_CODE = 5;
    /////Imagen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pantalla_reportar_problemas );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        sEstadoReportProblem = getResources().getString( R.string.Estado_ReportProblem );
        mStoragePath = getResources().getString( R.string.Imagenes_Problemas );

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayShowHomeEnabled( true );
        actionBar.setTitle( getResources().getString( R.string.titulo_ReportProblem ) );

        mProgressDialogReportarProblema = new ProgressDialog( PantallaReportarProblemas.this );

        editTituloProblem = (EditText) findViewById( R.id.xmlEditTituloProblem );
        editDecripcionProblem = (EditText) findViewById( R.id.xmlEditReportarProblema );

        ProblemDatabase = FirebaseDatabase.getInstance();
        ProblemReportRefRegistrar = ProblemDatabase.getReference( getResources().getString( R.string.Ref_ProblemasReportadosApp ) );

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "EEE dd MMM yyyy" );
        sFechaProblem = simpleDateFormat.format( new Date() );

        mStorageReference = FirebaseStorage.getInstance().getReference("Imagenes problemas/");

        ImagenReportProblem = (ImageView) findViewById( R.id.xmlImagenUno );
        ImagenReportProblem.setOnClickListener( new View.OnClickListener() {
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
                ImagenReportProblem.setImageBitmap( bitmap );
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

            subirdatos();

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
                    sImagenProblem = downloadUri.toString();

                    sIdProblemAppReport = ProblemReportRefRegistrar.push().getKey();

                    sTituloProblem = editTituloProblem.getText().toString().trim();
                    sDescripcionProblem = editDecripcionProblem.getText().toString().trim();

                    sIdUserReport = FirebaseAuth.getInstance().getCurrentUser().getUid();

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
//                    Log.d( "imagen123", String.valueOf( sImagenProblem ) );
                    ProblemasAppReportar problemasAppReportar = new ProblemasAppReportar( sIdProblemAppReport, sTituloProblem, sDescripcionProblem, sFechaProblem, sImagenProblem, sIdUserReport, sEstadoReportProblem );
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

    private void subirdatos() {

        if (mFilePathUri != null) {

            EnviarProblema();

        } else {

            sImagenProblem = "data:image/jpeg;base64,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";

            sIdProblemAppReport = ProblemReportRefRegistrar.push().getKey();

            sTituloProblem = editTituloProblem.getText().toString().trim();
            sDescripcionProblem = editDecripcionProblem.getText().toString().trim();

            sIdUserReport = FirebaseAuth.getInstance().getCurrentUser().getUid();

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
            Log.d( "imagen123", String.valueOf( sImagenProblem ) );
            ProblemasAppReportar problemasAppReportar = new ProblemasAppReportar( sIdProblemAppReport, sTituloProblem, sDescripcionProblem, sFechaProblem, sImagenProblem, sIdUserReport, sEstadoReportProblem );
            ProblemReportRefRegistrar.child( sIdProblemAppReport ).setValue( problemasAppReportar );
            Toast.makeText( PantallaReportarProblemas.this, "Su Problema se Reporto exitosamente, le estaremos resolviendo lo mas pronto posible", Toast.LENGTH_LONG ).show();
            LimpiarCampos();
            mProgressDialogReportarProblema.dismiss();

        }
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
