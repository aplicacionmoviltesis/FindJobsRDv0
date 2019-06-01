package com.example.findjobsrdv0;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentQueryMap;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static com.google.firebase.storage.FirebaseStorage.getInstance;


public class PantallaPerfilEmpleador extends AppCompatActivity {

    private EditText editNombrePerfilE, editRncPerfilE, editPaginaWebPerfilE, editTelefonoPerfilE,
            editDireccionPerfilE, editCorreoPerfilE;
    private Button btnVerificacionPerfilEmpleador, btnActualizarPerfilE, btnActivarPerfilE;
    private ImageView ImagePerfilEmpleador;

    private String sNombrePerfilE, sRncPerfilE, sPaginaWebPerfilE, sTelefonoPerfilE,
            sDireccionPerfilE, sCorreoPerfilE, sImagenPerfilEmpleador;

    String sIdEmpleador = "";

    private Boolean sVerificarEmpleador, sVerificacion;

    private FirebaseDatabase database;
    private DatabaseReference DBperfilEmpleadores;

    /////////ImagenPerfilEmpleador
    private String mStoragePath = "ImagenesPerfilesEmpleadores/";
    private String mDatabasePath = "Empleadores";

    Uri mFilePathUri;

    StorageReference mStorageReference;
    //DatabaseReference mDatabaseReference;

    ProgressDialog mProgressDialog;

    int IMAGE_REQUEST_CODE = 5;

    /////////ImagenPerfilEmpleador

    final String klk = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_perfil_empleador);

        database = FirebaseDatabase.getInstance();
        DBperfilEmpleadores = database.getReference("Empleadores");
        //sIdEmpleador = "HmAtSRSnxdfxb0Z1kM2qoW1OvNo1";

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        editNombrePerfilE = (EditText) findViewById(R.id.xmleditNombrePerfilEmpleador);
        editRncPerfilE = (EditText) findViewById(R.id.xmleditRNC);
        editPaginaWebPerfilE = (EditText) findViewById(R.id.xmleditPaginaWebPerfilEmpleador);
        editTelefonoPerfilE = (EditText) findViewById(R.id.xmleditTelefono);
        editDireccionPerfilE = (EditText) findViewById(R.id.xmleditDireccion);
        editCorreoPerfilE = (EditText) findViewById(R.id.xmleditCorreoElectronico);

        ImagePerfilEmpleador = (ImageView) findViewById(R.id.profile_image);
        ImagePerfilEmpleador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Seleccionar Imagen"), IMAGE_REQUEST_CODE);

            }
        });

        mStorageReference = getInstance().getReference();
        //mDatabaseReference = FirebaseDatabase.getInstance().getReference(mDatabasePath);

        mProgressDialog = new ProgressDialog(PantallaPerfilEmpleador.this);


        btnVerificacionPerfilEmpleador = (Button) findViewById(R.id.xmlBtnVerificacionEmpresaDE);
        btnVerificacionPerfilEmpleador.setVisibility(View.INVISIBLE);

        btnActualizarPerfilE = (Button) findViewById(R.id.xmlBtnActualizarPerfil);
        btnActualizarPerfilE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///ActualizarPerfilEmpleador(sIdEmpleador);
                beginUpdate();
            }
        });

        btnActivarPerfilE = (Button) findViewById(R.id.xmlBtnActivar);
        btnActivarPerfilE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivarCampor();
            }
        });


        editNombrePerfilE.setEnabled(false);
        editRncPerfilE.setEnabled(false);
        editPaginaWebPerfilE.setEnabled(false);
        editTelefonoPerfilE.setEnabled(false);
        editDireccionPerfilE.setEnabled(false);
        editCorreoPerfilE.setEnabled(false);

        btnActualizarPerfilE.setEnabled(false);


        //sIdEmpleador = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(getIntent() != null){
            sIdEmpleador = getIntent().getStringExtra("EmpleadorConectado");
            if(!sIdEmpleador.isEmpty()){

                VerificacionEmpresa(sIdEmpleador);
            }
        }

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            mFilePathUri = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mFilePathUri);
                ImagePerfilEmpleador.setImageBitmap(bitmap);
            } catch (Exception e) {

                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

            }


        }
    }


    public void ActivarCampor() {
        editNombrePerfilE.setEnabled(true);
        editRncPerfilE.setEnabled(true);
        editPaginaWebPerfilE.setEnabled(true);
        editTelefonoPerfilE.setEnabled(true);
        editDireccionPerfilE.setEnabled(true);
        editCorreoPerfilE.setEnabled(true);

        btnActualizarPerfilE.setEnabled(true);

    }

    public void ActualizarPerfilEmpleador(final String sIdEmpleador) {

        if (mFilePathUri != null) {
            mProgressDialog.setTitle("Subiendo Imagen...");
            mProgressDialog.show();

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
                        mProgressDialog.setTitle("Actualizando...");
                        mProgressDialog.show();

                        Toast.makeText(PantallaPerfilEmpleador.this, "Imagen subida exitosamente...", Toast.LENGTH_LONG).show();
                        Empleadores empleadores = new Empleadores(sNombrePerfilE, sRncPerfilE, sPaginaWebPerfilE, sTelefonoPerfilE, sDireccionPerfilE, sCorreoPerfilE, downloadURL, sVerificacion);
                        DBperfilEmpleadores.child(sIdEmpleador).setValue(empleadores);
                        mProgressDialog.dismiss();

                    } else {
                        // Handle failures
                        // ...
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressDialog.dismiss();
                    Toast.makeText(PantallaPerfilEmpleador.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            });/*.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgressDialog.setTitle("Subiendo Imagen...");
                }
            });*/


            ///////////////////////////////////////
            /*storageReference2do.putFile(mFilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            sNombrePerfilE = editNombrePerfilE.getText().toString().trim();
                            sRncPerfilE = editNombrePerfilE.getText().toString().trim();
                            sPaginaWebPerfilE = editNombrePerfilE.getText().toString().trim();
                            sTelefonoPerfilE = editNombrePerfilE.getText().toString().trim();
                            sDireccionPerfilE = editNombrePerfilE.getText().toString().trim();
                            sCorreoPerfilE = editNombrePerfilE.getText().toString().trim();

                            mProgressDialog.dismiss();
                            Toast.makeText(PantallaPerfilEmpleador.this, "Imagen subida exitosamente...", Toast.LENGTH_LONG).show();
Empleadores empleadores = new Empleadores(sNombrePerfilE,sRncPerfilE,sPaginaWebPerfilE,sTelefonoPerfilE,sDireccionPerfilE,sCorreoPerfilE,taskSnapshot.);


                        }
                    });*/
        } else {
            Toast.makeText(PantallaPerfilEmpleador.this, "Por favor, Seleccionar una Imagen", Toast.LENGTH_LONG).show();

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void beginUpdate() {

        mProgressDialog.setTitle("Actualizando...");
        mProgressDialog.show();
        DeleteImagenAnterior();
    }

    public void DeleteImagenAnterior() {
        if (sImagenPerfilEmpleador != null && !sImagenPerfilEmpleador.isEmpty()) {
        final StorageReference mPictureRef = getInstance().getReferenceFromUrl(sImagenPerfilEmpleador);
            mPictureRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(PantallaPerfilEmpleador.this, "Eliminando Imagen...", Toast.LENGTH_LONG).show();
                    Log.d("link foto",String.valueOf(mPictureRef));
                    SubirNuevaImagen();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PantallaPerfilEmpleador.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();

                }
            });
        }else {

            Toast.makeText(PantallaPerfilEmpleador.this, "No hay imagen agregada", Toast.LENGTH_LONG).show();
            SubirNuevaImagen();
        }



    }

    private void SubirNuevaImagen() {
        String imageName = System.currentTimeMillis() + ".png";
        //String imageName = System.currentTimeMillis() + getFileExtension(mFilePathUri);

        StorageReference storageReference2do = mStorageReference.child(mStoragePath + imageName);

        Bitmap bitmap = ((BitmapDrawable) ImagePerfilEmpleador.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] data = baos.toByteArray();
        UploadTask uploadTask = storageReference2do.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(PantallaPerfilEmpleador.this, "Nueva Imagen Subida...", Toast.LENGTH_LONG).show();
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri downloadUri = uriTask.getResult();
                ActualizarDatosEmpleador(downloadUri.toString());

            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PantallaPerfilEmpleador.this, e.getMessage(), Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        });


    }

    private void ActualizarDatosEmpleador(String foto) {

        sNombrePerfilE = editNombrePerfilE.getText().toString().trim();
        sRncPerfilE = editRncPerfilE.getText().toString().trim();
        sPaginaWebPerfilE = editPaginaWebPerfilE.getText().toString().trim();
        sTelefonoPerfilE = editTelefonoPerfilE.getText().toString().trim();
        sDireccionPerfilE = editDireccionPerfilE.getText().toString().trim();
        sCorreoPerfilE = editCorreoPerfilE.getText().toString().trim();
        sVerificacion = false;

        Empleadores empleadores = new Empleadores(sNombrePerfilE, sRncPerfilE, sPaginaWebPerfilE, sTelefonoPerfilE, sDireccionPerfilE, sCorreoPerfilE, foto, sVerificacion);
        DBperfilEmpleadores.child(sIdEmpleador).setValue(empleadores);
        mProgressDialog.dismiss();
        Toast.makeText(PantallaPerfilEmpleador.this, "Sus Datos han Sido Actualizado", Toast.LENGTH_LONG).show();


    }


    public void VerificacionEmpresa(String sIdEmpleador) {
        DBperfilEmpleadores.child(sIdEmpleador).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                sImagenPerfilEmpleador = dataSnapshot.child("sImagenEmpleador").getValue(String.class);
                if (sImagenPerfilEmpleador != null && !sImagenPerfilEmpleador.isEmpty()) {
                    Picasso.get().load(sImagenPerfilEmpleador).into(ImagePerfilEmpleador);
                }


                Log.d("holapkkk", String.valueOf(dataSnapshot));
                sNombrePerfilE = dataSnapshot.child("sNombreEmpleador").getValue(String.class);
                editNombrePerfilE.setText(sNombrePerfilE);

                sRncPerfilE = dataSnapshot.child("sRncEmpleador").getValue(String.class);
                editRncPerfilE.setText(sRncPerfilE);

                sPaginaWebPerfilE = dataSnapshot.child("sPaginaWebEmpleador").getValue(String.class);
                editPaginaWebPerfilE.setText(sPaginaWebPerfilE);

                sTelefonoPerfilE = dataSnapshot.child("sTelefonoEmpleador").getValue(String.class);
                editTelefonoPerfilE.setText(sTelefonoPerfilE);

                sDireccionPerfilE = dataSnapshot.child("sDireccionEmpleador").getValue(String.class);
                editDireccionPerfilE.setText(sDireccionPerfilE);

                sCorreoPerfilE = dataSnapshot.child("sCorreoEmpleador").getValue(String.class);
                editCorreoPerfilE.setText(sCorreoPerfilE);

                sVerificarEmpleador = dataSnapshot.child("Verificacion").getValue(Boolean.class);
                Log.d("verificacion", String.valueOf(sVerificarEmpleador));

                if (sVerificarEmpleador == null) {
                    //Toast.makeText(PantallaPerfilEmpleador.this, "No se realizo correctamente su aplicacion al empleo", Toast.LENGTH_LONG).show();
                } else {

                    if (sVerificarEmpleador == true) {
                        btnVerificacionPerfilEmpleador.setVisibility(View.VISIBLE);
                    }
                    if (sVerificarEmpleador == false) {
                        btnVerificacionPerfilEmpleador.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PantallaPerfilEmpleador.this, "No se realizo correctamente su aplicacion al empleo", Toast.LENGTH_LONG).show();
            }
        });

    }
}
