package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.PerfilBuscador;
import com.example.findjobsrdv0.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class PantallaPerfilBuscador extends AppCompatActivity {

    private EditText ETnombreperfilB, ETapellidoperfilB, ETcorreoelectronicoperfilB, ETtelefonoperfilB;
    private ImageView imageViewperfilB;
   // private Button btnactivarcamposperfilB, btnactualizarcamposperfilB;

    private String sImagenPerfilB, sNombrePerfilB, sApellidoperfilB, sEmailPerfilB, sTelefonoPerfilB;

    String sIdBuscador = "";

    private FirebaseDatabase database;
    private DatabaseReference DBperfilBuscadores;

    private String mStoragePath = "ImagenesPerfilesBuscadores/";
    private String mDatabasePath = "BuscadoresEmpleos";

    Uri mFilePathUri;

    StorageReference mStorageReference;
    //DatabaseReference mDatabaseReference;

    ProgressDialog mProgressDialog;

    int IMAGE_REQUEST_CODE = 5;

    private TextView TvTiPerfilBuscador;

    FirebaseAuth firebaseAuth;
    String TelefonoBuscador,EmailBuscador, NombreBuscador, FotoPerfilCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_perfil_buscador );
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TvTiPerfilBuscador = (TextView) findViewById( R.id.xmlTvTiPerfilBuscador );
        Typeface face = Typeface.createFromAsset( getAssets(), "fonts/robotoslab.bold.ttf" );
        TvTiPerfilBuscador.setTypeface( face );

        database = FirebaseDatabase.getInstance();
        DBperfilBuscadores = database.getReference( "BuscadoresEmpleos" );

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayShowHomeEnabled( true );

        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

        TelefonoBuscador = user.getPhoneNumber();
        EmailBuscador = user.getEmail();
        NombreBuscador = user.getDisplayName();
        FotoPerfilCorreo = user.getPhotoUrl().toString();

        ETnombreperfilB = (EditText) findViewById( R.id.xmleditNombreperfilBuscado );
        ETapellidoperfilB = (EditText) findViewById( R.id.xmleditDireccionperfilBuscado );
        ETcorreoelectronicoperfilB = (EditText) findViewById( R.id.xmleditCorreoElectronicoperfilBuscado );
        ETtelefonoperfilB = (EditText) findViewById( R.id.xmleditTelefonoperfilBuscado );

        imageViewperfilB = (ImageView) findViewById( R.id.profile_image_Buscador );
        imageViewperfilB.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType( "image/*" );
                galleryIntent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( galleryIntent, "Seleccionar Imagen" ), IMAGE_REQUEST_CODE );
            }
        } );



 /*       btnactivarcamposperfilB = (Button)findViewById( R.id.xmlBtnActivarperfilBuscador );
        btnactivarcamposperfilB.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivarCampor();
            }
        } );

        btnactualizarcamposperfilB = (Button)findViewById( R.id.xmlBtnActualizarPerfilBuscador );
        btnactualizarcamposperfilB.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginUpdate();

            }
        } );
*/

        mStorageReference = getInstance().getReference();
        //mDatabaseReference = FirebaseDatabase.getInstance().getReference(mDatabasePath);

        mProgressDialog = new ProgressDialog( PantallaPerfilBuscador.this );


        ETnombreperfilB.setEnabled( false );
        ETapellidoperfilB.setEnabled( false );
        ETcorreoelectronicoperfilB.setEnabled( false );
        ETtelefonoperfilB.setEnabled( false );

        if (getIntent() != null) {
            sIdBuscador = getIntent().getStringExtra( "BuscadorConectado" );

            if(!sIdBuscador.isEmpty()){

                VerificarBuscador( sIdBuscador );
            }
        }
    }



    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
                imageViewperfilB.setImageBitmap( bitmap );
            } catch (Exception e) {

                Toast.makeText( this, e.getMessage(), Toast.LENGTH_LONG ).show();

            }
        }
    }

    public void ActivarCampor() {
        ETnombreperfilB.setEnabled( true );
        ETapellidoperfilB.setEnabled( true );
        ETcorreoelectronicoperfilB.setEnabled( true );
        ETtelefonoperfilB.setEnabled( true );

        //btnActualizarPerfilE.setEnabled(true);

    }

    public void ActualizarPerfilBuscador(final String sIdBuscador) {
        if (mFilePathUri != null) {
            mProgressDialog.setTitle( "Subiendo Imagen..." );
            mProgressDialog.show();

            final StorageReference storageReference2do = mStorageReference.child( mStoragePath + System.currentTimeMillis() + "." + getFileExtension( mFilePathUri ) );

            //final StorageReference storageReference2do = storageReference2do.child("your_REF");
            UploadTask uploadTask = storageReference2do.putFile( mFilePathUri );

            Task<Uri> urlTask = uploadTask.continueWithTask( new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageReference2do.getDownloadUrl();
                }
            } ).addOnCompleteListener( new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String downloadURL = downloadUri.toString();
                        sNombrePerfilB = ETnombreperfilB.getText().toString().trim();
                        sApellidoperfilB = ETapellidoperfilB.getText().toString().trim();
                        sEmailPerfilB = ETcorreoelectronicoperfilB.getText().toString().trim();
                        sTelefonoPerfilB = ETtelefonoperfilB.getText().toString().trim();

                        mProgressDialog.dismiss();

                        mProgressDialog.setTitle( "Actualizando..." );
                        mProgressDialog.show();

                        Toast.makeText( PantallaPerfilBuscador.this, "Imagen subida exitosamente...", Toast.LENGTH_LONG ).show();

                        PerfilBuscador perfilBuscador = new PerfilBuscador( downloadURL,sIdBuscador, sNombrePerfilB, sApellidoperfilB, sEmailPerfilB, sTelefonoPerfilB);
                        DBperfilBuscadores.child( sIdBuscador ).setValue( perfilBuscador );
                        mProgressDialog.dismiss();
                    } else {
                        // Handle failures
                        // ...
                    }
                }

            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressDialog.dismiss();
                    Toast.makeText( PantallaPerfilBuscador.this, e.getMessage(), Toast.LENGTH_LONG ).show();

                }
            } );


        } else {
            Toast.makeText( PantallaPerfilBuscador.this, "Por favor, Seleccionar una Imagen", Toast.LENGTH_LONG ).show();

        }

    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( contentResolver.getType( uri ) );
    }

    public void beginUpdate() {

        mProgressDialog.setTitle( "Actualizando..." );
        mProgressDialog.show();
        DeleteImagenAnterior();
    }

    private void DeleteImagenAnterior() {
        if (sImagenPerfilB != null && ! sImagenPerfilB.isEmpty()){
            final StorageReference mPitureRef = getInstance().getReferenceFromUrl(sImagenPerfilB);
            mPitureRef.delete().addOnSuccessListener( new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText( PantallaPerfilBuscador.this, "Eliminando Imagen...", Toast.LENGTH_LONG).show();
                    Log.d("link foto",String.valueOf(mPitureRef));
                    SubirNuevaImagen();
                }
            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( PantallaPerfilBuscador.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
            } );
        } else {
            Toast.makeText( PantallaPerfilBuscador.this, "No hay imagen agregada", Toast.LENGTH_LONG).show();
            SubirNuevaImagen();
        }
    }

    private void SubirNuevaImagen() {
        String imageName = System.currentTimeMillis() + ".png";
        //String imageName = System.currentTimeMillis() + getFileExtension(mFilePathUri);

        StorageReference storageReference2do = mStorageReference.child(mStoragePath + imageName);

        Bitmap bitmap = ((BitmapDrawable) imageViewperfilB.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] data = baos.toByteArray();
        UploadTask uploadTask = storageReference2do.putBytes(data);
        uploadTask.addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText( PantallaPerfilBuscador.this, "Nueva Imagen Subida...", Toast.LENGTH_LONG).show();
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri downloadUri = uriTask.getResult();
                ActualizarDatosBuscador(downloadUri.toString());

            }
        }).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( PantallaPerfilBuscador.this, e.getMessage(), Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        } );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuperfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_EditarPerfil) {
            //process your onClick here
            ActivarCampor();
            return true;
        }
        if (id == R.id.menu_ActualizarPerfil) {
            //process your onClick here
            beginUpdate();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void ActualizarDatosBuscador(String foto) {
        sNombrePerfilB = ETnombreperfilB.getText().toString().trim();
        sApellidoperfilB = ETapellidoperfilB.getText().toString().trim();
        sEmailPerfilB = ETcorreoelectronicoperfilB.getText().toString().trim();
        sTelefonoPerfilB = ETtelefonoperfilB.getText().toString().trim();

        PerfilBuscador perfilBuscador = new PerfilBuscador(foto, sIdBuscador, sNombrePerfilB, sApellidoperfilB, sEmailPerfilB, sTelefonoPerfilB);
        DBperfilBuscadores.child( sIdBuscador ).setValue( perfilBuscador );
        Toast.makeText( PantallaPerfilBuscador.this, "Sus Datos han Sido Actualizado", Toast.LENGTH_LONG).show();
        Intent intent = new Intent( PantallaPerfilBuscador.this, PantallaPrincipalBuscador.class);
        startActivity(intent);
    }

    private void VerificarBuscador(String sIdBuscador) {
        DBperfilBuscadores.child(sIdBuscador).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                sImagenPerfilB = dataSnapshot.child("sImagenPerfilB").getValue(String.class);
                if (sImagenPerfilB != null && !sImagenPerfilB.isEmpty()) {
                    Picasso.get().load( sImagenPerfilB ).into( imageViewperfilB );
                }else
                    {
                        Glide.with( PantallaPerfilBuscador.this ).load( FotoPerfilCorreo ).into( imageViewperfilB );
                    }

                sNombrePerfilB = dataSnapshot.child( "sNombrePerfilB" ).getValue( String.class );
                if (sNombrePerfilB != null && sNombrePerfilB != ""){
                    ETnombreperfilB.setText( sNombrePerfilB );
                }else
                    {
                        ETnombreperfilB.setText( NombreBuscador );
                    }

                sApellidoperfilB = dataSnapshot.child( "sApellidoperfilB" ).getValue(String.class);
                if (sApellidoperfilB != null && sApellidoperfilB != ""){
                    ETapellidoperfilB.setText( sApellidoperfilB );
                }

                sEmailPerfilB = dataSnapshot.child( "sEmailPerfilB" ).getValue(String.class);
                if (sEmailPerfilB != null && sEmailPerfilB != ""){
                    ETcorreoelectronicoperfilB.setText( sEmailPerfilB );
                }else
                    {
                        ETcorreoelectronicoperfilB.setText( EmailBuscador );
                    }

                sTelefonoPerfilB = dataSnapshot.child( "sTelefonoPerfilB" ).getValue(String.class);
                if (sTelefonoPerfilB != null && sTelefonoPerfilB != ""){
                    ETtelefonoperfilB.setText( sTelefonoPerfilB );
                }else
                    {
                        ETtelefonoperfilB.setText( TelefonoBuscador );
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}