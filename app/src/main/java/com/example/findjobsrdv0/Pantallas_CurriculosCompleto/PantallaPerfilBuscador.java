package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findjobsrdv0.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class PantallaPerfilBuscador extends AppCompatActivity {

    private TextView tvnombreperfilB, tvapellidoperfilB, tvcorreoelectronicoperfilB, tvtelefonoperfilB;
    private ImageView imageViewperfilB;
    private Button btnactivarcamposperfilB, btnactualizarcamposperfilB;

    private String imagenperfilB, nombreperfilB, apellidoperfilB, correoelectronicoperfilB, telefonoperfilB;

    String idBuscadorConectado = "";

    private FirebaseDatabase database;
    private DatabaseReference DBperfilBuscadores;

    private String mStoragePath = "ImagenesPerfilesBuscadores/";
    private String mDatabasePath = "BuscadoresEmpleos";

    Uri mFilePathUri;

    StorageReference mStorageReference;
    //DatabaseReference mDatabaseReference;

    ProgressDialog mProgressDialog;

    int IMAGE_REQUEST_CODE = 5;


    private TextView TvTiPerfilBuscadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_perfil_buscador );

        TvTiPerfilBuscadores = (TextView) findViewById( R.id.xmlTvTiPerfilBuscador );
        Typeface face = Typeface.createFromAsset( getAssets(), "fonts/robotoslab.bold.ttf" );
        TvTiPerfilBuscadores.setTypeface( face );

        database = FirebaseDatabase.getInstance();
        DBperfilBuscadores = database.getReference( "BuscadoresEmpleos" );
        //sIdEmpleador = "HmAtSRSnxdfxb0Z1kM2qoW1OvNo1";

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayShowHomeEnabled( true );

        tvnombreperfilB = (TextView) findViewById( R.id.xmleditNombreperfilBuscado );
        tvapellidoperfilB = (TextView) findViewById( R.id.xmleditDireccionperfilBuscado );
        tvcorreoelectronicoperfilB = (TextView) findViewById( R.id.xmleditCorreoElectronicoperfilBuscado );
        tvtelefonoperfilB = (TextView) findViewById( R.id.xmleditTelefonoperfilBuscado );

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

        mStorageReference = getInstance().getReference();
        //mDatabaseReference = FirebaseDatabase.getInstance().getReference(mDatabasePath);

        mProgressDialog = new ProgressDialog( PantallaPerfilBuscador.this );


        tvnombreperfilB.setEnabled( false );
        tvapellidoperfilB.setEnabled( false );
        tvcorreoelectronicoperfilB.setEnabled( false );
        tvtelefonoperfilB.setEnabled( false );

        if (getIntent() != null) {
            idBuscadorConectado = getIntent().getStringExtra( "BuscadorConectado" );
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

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mFilePathUri);
                imageViewperfilB.setImageBitmap(bitmap);
            } catch (Exception e) {

                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    }

    public void ActivarCampor() {
        tvnombreperfilB.setEnabled(true);
        tvapellidoperfilB.setEnabled(true);
        tvcorreoelectronicoperfilB.setEnabled(true);
        tvtelefonoperfilB.setEnabled(true);

        //btnActualizarPerfilE.setEnabled(true);

    }
}
