package com.example.findjobsrdv0.GeneralesApp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.findjobsrdv0.R;

public class PantallaReportarProblemas extends AppCompatActivity {

    ImageView ImagenUno,ImagenDos,ImagenTres;
    int IMAGE_REQUEST_CODE = 5;

    ProgressDialog mProgressDialogReportarProblema;

    Uri mFilePathUriReportProblem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_reportar_problemas);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        ImagenUno = (ImageView) findViewById(R.id.xmlImagenUno);
        ImagenDos = (ImageView) findViewById(R.id.xmlImagenDos);
        ImagenTres = (ImageView) findViewById(R.id.xmlImagenTres);

        ImagenUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Seleccionar Imagen"), IMAGE_REQUEST_CODE);

            }
        });

        ImagenDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Seleccionar Imagen"), IMAGE_REQUEST_CODE);

            }
        });

        ImagenTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                startActivityForResult(Intent.createChooser(galleryIntent, "Seleccionar Imagen"), IMAGE_REQUEST_CODE);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            mFilePathUriReportProblem = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mFilePathUriReportProblem);
                ImagenDos.setImageBitmap(bitmap);
                ImagenUno.setImageBitmap(bitmap);

            } catch (Exception e) {

                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

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
        getMenuInflater().inflate(R.menu.menu_reportar_problema, menu);
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


        return super.onOptionsItemSelected(item);
    }

    private void EnviarProblema() {
        Toast.makeText(PantallaReportarProblemas.this, "Se enviooooooooooooo", Toast.LENGTH_LONG).show();
       /* public void ActualizarPerfilEmpleador(final String sIdEmpleador) {

            if (mFilePathUri != null) {
                mProgressDialogReportarProblema.setTitle("Subiendo Imagen...");
                mProgressDialogReportarProblema.show();

                //klk = sIdEmpleador;

                final StorageReference storageReference2do = mStorageReference.child(mStoragePath + System.currentTimeMillis() + "." + getFileExtension(mFilePathUri));

                ////////////////////////////////////////


                //final StorageReference storageReference2do = storageReference2do.child("your_REF");
                UploadTask uploadTask = storageReference2do.putFile(mFilePathUri);

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return storageReference2do.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            String downloadURL = downloadUri.toString();
                            sNombrePerfilE = editNombrePerfilE.getText().toString().trim();
                            sRncPerfilE = editRncPerfilE.getText().toString().trim();
                            sPaginaWebPerfilE = editPaginaWebPerfilE.getText().toString().trim();
                            sTelefonoPerfilE = editTelefonoPerfilE.getText().toString().trim();
                            sDireccionPerfilE = editDireccionPerfilE.getText().toString().trim();
                            sCorreoPerfilE = editCorreoPerfilE.getText().toString().trim();
                            sVerificacion = false;
                            //sImagenPerfilEmpleador = downloadURL;
                            mProgressDialog.dismiss();

                            //DeleteImagenAnterior();
                            mProgressDialogReportarProblema.setTitle("Actualizando...");
                            mProgressDialogReportarProblema.show();

                            Toast.makeText(PantallaPerfilEmpleador.this, "Imagen subida exitosamente...", Toast.LENGTH_LONG).show();
                            Empleadores empleadores = new Empleadores(sNombrePerfilE, sRncPerfilE, sPaginaWebPerfilE, sTelefonoPerfilE, sDireccionPerfilE, sCorreoPerfilE, downloadURL, sVerificacion);
                            DBperfilEmpleadores.child(sIdEmpleador).setValue(empleadores);
                            mProgressDialogReportarProblema.dismiss();

                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mProgressDialogReportarProblema.dismiss();
                        Toast.makeText(PantallaReportarProblemas.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
            } else {
                Toast.makeText(PantallaReportarProblemas.this, "Por favor, Seleccionar una Imagen", Toast.LENGTH_LONG).show();

            }
        }*/
    }
}
