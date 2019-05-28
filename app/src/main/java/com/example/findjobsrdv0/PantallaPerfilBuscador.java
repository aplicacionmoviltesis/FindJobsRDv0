package com.example.findjobsrdv0;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class PantallaPerfilBuscador extends AppCompatActivity {


    private Button btnActualizarPerfilBuscador;
    private ImageView ImagePerfilBuscador;

    private FirebaseDatabase databaseBuscador;
    private DatabaseReference DBperfilBuscadores;

    /////////ImagenPerfilEmpleador
    private String mStoragePathBuscador = "ImagenesPerfilesBuscadores/";

    Uri mFilePathUriBuscador;

    StorageReference mStorageReferenceBuscador;

    ProgressDialog mProgressDialogB;

    int IMAGE_REQUEST_CODE = 5;

    /////////ImagenPerfilEmpleador


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_perfil_buscador);

        databaseBuscador = FirebaseDatabase.getInstance();
        DBperfilBuscadores = databaseBuscador.getReference("BuscadoresEmpleos");


        ImagePerfilBuscador = (ImageView) findViewById(R.id.fotoPerfilBuscador);
        ImagePerfilBuscador.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Seleccionar Imagen"), IMAGE_REQUEST_CODE);

            }
        });

        mStorageReferenceBuscador = FirebaseStorage.getInstance().getReference();
        //mDatabaseReference = FirebaseDatabase.getInstance().getReference(mDatabasePath);

        mProgressDialogB = new ProgressDialog(PantallaPerfilBuscador.this);


        btnActualizarPerfilBuscador = (Button) findViewById(R.id.btnSubirImagen);
        btnActualizarPerfilBuscador.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualizarPerfilBuscador();

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

            mFilePathUriBuscador = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mFilePathUriBuscador );
                ImagePerfilBuscador.setImageBitmap(bitmap);
            } catch (Exception e) {

                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

            }


        }
    }


    public void ActualizarPerfilBuscador() {

        if (mFilePathUriBuscador != null) {
            mProgressDialogB.setTitle("Subiendo Imagen...");
            mProgressDialogB.show();

            //klk = sIdEmpleador;

            final StorageReference storageReference2do = mStorageReferenceBuscador.child( mStoragePathBuscador + System.currentTimeMillis() + "." + getFileExtension( mFilePathUriBuscador ));

            ////////////////////////////////////////


            //final StorageReference storageReference2do = storageReference2do.child("your_REF");
            UploadTask uploadTask = storageReference2do.putFile( mFilePathUriBuscador );

            Task<Uri> urlTask = uploadTask.continueWithTask( new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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

                        Picasso.get().load(downloadURL).into(ImagePerfilBuscador);

                        mProgressDialogB.dismiss();
                        Toast.makeText(PantallaPerfilBuscador.this, "Imagen subida exitosamente...", Toast.LENGTH_LONG).show();
                        Log.d("url",downloadURL);


                    } else {
                        // Handle failures
                        // ...
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressDialogB.dismiss();
                    Toast.makeText(PantallaPerfilBuscador.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }else {
            Toast.makeText(PantallaPerfilBuscador.this, "Por favor, Seleccionar una Imagen", Toast.LENGTH_LONG).show();

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}