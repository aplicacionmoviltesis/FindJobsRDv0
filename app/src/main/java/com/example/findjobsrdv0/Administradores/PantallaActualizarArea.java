package com.example.findjobsrdv0.Administradores;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.findjobsrdv0.GeneralesApp.Areas;
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

import java.io.ByteArrayOutputStream;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class PantallaActualizarArea extends AppCompatActivity {

    private String sIdAreaActualizar = "";
    private String sNombreAreaActualizar, sDescAreaActualizar, sImagenAreaActualizar, sIdUserAdminAreaActualizar, sSubAreasActualizar;

    private EditText editNombreAreaActualizar, editDescAreaActualizar, editSubAreasActualizar;
    private ImageView ImageAreaActualizar;

    private DatabaseReference AreaRefActualizar;
    private FirebaseDatabase AreaDatabase;
    private ProgressDialog mProgressDialogA;

    /////Imagen
    private String mStoragePath = "Imagenes Areas/";
    Uri mFilePathUri;
    StorageReference mStorageReference;
    int IMAGE_REQUEST_CODE = 5;

    //String userActivo;
    /////Imagen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pantalla_actualizar_area );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayShowHomeEnabled( true );



        mProgressDialogA = new ProgressDialog( PantallaActualizarArea.this );

        ImageAreaActualizar = (ImageView) findViewById( R.id.xmlImagenAreaActualizar );

        editNombreAreaActualizar = (EditText) findViewById( R.id.xmlEditNombreAreaActualizar );
        editDescAreaActualizar = (EditText) findViewById( R.id.xmlEdiDescripcionAreaActualizar );
        editSubAreasActualizar = (EditText) findViewById( R.id.xmlEditSubAreasActualizar );

        editNombreAreaActualizar.setEnabled( false );
        editDescAreaActualizar.setEnabled( false );
        editSubAreasActualizar.setEnabled( false );

        AreaDatabase = FirebaseDatabase.getInstance();
        AreaRefActualizar = AreaDatabase.getReference( "Areas" );

        if (getIntent() != null) {
            sIdAreaActualizar = getIntent().getStringExtra( "sAreaId" );
            if (!sIdAreaActualizar.isEmpty()) {
                Log.d( "holap", String.valueOf( sIdAreaActualizar ) );

                CargarArea( sIdAreaActualizar );
            }
        }

        mStorageReference = getInstance().getReference();

        ImageAreaActualizar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType( "image/*" );
                galleryIntent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( galleryIntent, "Seleccionar Imagen" ), IMAGE_REQUEST_CODE );
            }
        } );

        //userActivo = FirebaseAuth.getInstance().getCurrentUser().getUid();


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
                ImageAreaActualizar.setImageBitmap( bitmap );
            } catch (Exception e) {

                Toast.makeText( this, e.getMessage(), Toast.LENGTH_LONG ).show();

            }
        }
    }

    private void CargarArea(String sIdAreaActualizar) {
        Log.d( "holap", String.valueOf( sIdAreaActualizar ) );

        AreaRefActualizar.child( sIdAreaActualizar ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d( "holap", String.valueOf( dataSnapshot ) );

                sImagenAreaActualizar = dataSnapshot.child( "sImagenArea" ).getValue( String.class );
                if (sImagenAreaActualizar != null && !sImagenAreaActualizar.isEmpty()) {
                    Picasso.get().load( sImagenAreaActualizar ).into( ImageAreaActualizar );


                    Areas areas = dataSnapshot.getValue( Areas.class );
                    Log.d( "holap", String.valueOf( areas ) );

                   // Picasso.get().load( areas.getsImagenArea() ).into( ImageAreaActualizar );
                    editNombreAreaActualizar.setText( areas.getsNombreArea() );
                    editDescAreaActualizar.setText( areas.getsDescripcionArea() );
                    editSubAreasActualizar.setText( areas.getsSubAreas() );
                }

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

    private void DeleteImagenAnterior() {
        if (sImagenAreaActualizar != null && !sImagenAreaActualizar.isEmpty()) {
            final StorageReference mPitureRef = getInstance().getReferenceFromUrl( sImagenAreaActualizar     );
            mPitureRef.delete().addOnSuccessListener( new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText( PantallaActualizarArea.this, "Eliminando Imagen...", Toast.LENGTH_LONG ).show();
                    Log.d( "link foto", String.valueOf( mPitureRef ) );
                    SubirNuevaImagen();
                }
            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( PantallaActualizarArea.this, e.getMessage(), Toast.LENGTH_LONG ).show();
                    mProgressDialogA.dismiss();
                }
            } );
        } else {
            Toast.makeText( PantallaActualizarArea.this, "No hay imagen agregada", Toast.LENGTH_LONG ).show();
            SubirNuevaImagen();
        }
    }

    private void SubirNuevaImagen() {
        String imageName = System.currentTimeMillis() + ".png";
        //String imageName = System.currentTimeMillis() + getFileExtension(mFilePathUri);

        StorageReference storageReference2do = mStorageReference.child( mStoragePath + imageName );

        Bitmap bitmap = ((BitmapDrawable) ImageAreaActualizar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.PNG, 100, baos );

        byte[] data = baos.toByteArray();
        UploadTask uploadTask = storageReference2do.putBytes( data );
        uploadTask.addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText( PantallaActualizarArea.this, "Nueva Imagen Subida...", Toast.LENGTH_LONG ).show();
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri downloadUri = uriTask.getResult();
                ActualizarArea( downloadUri.toString() );

            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( PantallaActualizarArea.this, e.getMessage(), Toast.LENGTH_LONG ).show();
                mProgressDialogA.dismiss();
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

    private void ActivarCampos() {

        editNombreAreaActualizar.setEnabled( true );
        editDescAreaActualizar.setEnabled( true );
        editSubAreasActualizar.setEnabled( true );
    }

    private void ActualizarArea(String foto) {

        mProgressDialogA.setTitle( "Actualizando..." );
        mProgressDialogA.show();

        sNombreAreaActualizar = editNombreAreaActualizar.getText().toString().trim();
        sDescAreaActualizar = editDescAreaActualizar.getText().toString().trim();
        sSubAreasActualizar = editSubAreasActualizar.getText().toString().trim();

        //sImagenAreaActualizar = "https://firebasestorage.googleapis.com/v0/b/findjobsrd.appspot.com/o/Imagenes%20Provincia%2Fbonao.jpg?alt=media&token=287c737f-70b4-4e8e-bcf0-11e077edc509";
        sIdUserAdminAreaActualizar = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (TextUtils.isEmpty( sNombreAreaActualizar )) {
            Toast.makeText( this, "Por favor, Ingrese el nombre", Toast.LENGTH_LONG ).show();
            mProgressDialogA.dismiss();

            return;
        }

        if (TextUtils.isEmpty( sDescAreaActualizar )) {
            Toast.makeText( this, "Por favor, Ingrese la descripcion", Toast.LENGTH_LONG ).show();
            mProgressDialogA.dismiss();

            return;
        }
        if (TextUtils.isEmpty( sSubAreasActualizar )) {
            Toast.makeText( this, "Por favor, Ingrese al menos una sub-area", Toast.LENGTH_LONG ).show();
            mProgressDialogA.dismiss();

            return;
        }

        Areas areas = new Areas( sIdAreaActualizar, sNombreAreaActualizar, sDescAreaActualizar, foto, sSubAreasActualizar, sIdUserAdminAreaActualizar );
        AreaRefActualizar.child( sIdAreaActualizar ).setValue( areas );
        Toast.makeText( this, "La Actualizacion se Actualizo exitosamente", Toast.LENGTH_LONG ).show();
        mProgressDialogA.dismiss();

        Intent intent = new Intent( PantallaActualizarArea.this, PantallaListaAreas.class );
        startActivity( intent );
    }

}
