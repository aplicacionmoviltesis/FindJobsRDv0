package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findjobsrdv0.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class PantallaDetallePerfilEmpresa extends AppCompatActivity {

    private EditText editNombrePerfilE, editRncPerfilE, editPaginaWebPerfilE, editTelefonoPerfilE,
            editDireccionPerfilE, editCorreoPerfilE,editProvinciaPerfilE,editDescripcionPerfilE;
    private Button btnVerificacionPerfilEmpleador;
    private ImageView ImagePerfilEmpleador;

    private String sNombrePerfilE, sRncPerfilE, sPaginaWebPerfilE, sTelefonoPerfilE,
            sDireccionPerfilE, sCorreoPerfilE, sImagenPerfilEmpleador,sProvinciaPerfilE,sDescripcionPerfilE;

    String sIdEmpleador = "";

    private Boolean sVerificarEmpleador;

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
    private TextView TvTiPerfilEmpleadorPE;

    FirebaseAuth firebaseAuth;
    //String telefonoEmpleador,EmailEmpleador,NombreEmpleador, FotoPerfilCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalle_perfil_empresa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        database = FirebaseDatabase.getInstance();
        DBperfilEmpleadores = database.getReference("Empleadores");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        /*if(getIntent() != null){
            sIdEmpleador = getIntent().getStringExtra("EmpleadorConectado");
            if(!sIdEmpleador.isEmpty()){

                LoadEmpresa(sIdEmpleador);
            }
        }*/




        editNombrePerfilE = (EditText) findViewById(R.id.xmleditNombrePerfilEmpleador);
        editRncPerfilE = (EditText) findViewById(R.id.xmleditRNC);
        editPaginaWebPerfilE = (EditText) findViewById(R.id.xmleditPagWebPE);
        editTelefonoPerfilE = (EditText) findViewById(R.id.xmleditTelefono);
        editDireccionPerfilE = (EditText) findViewById(R.id.xmleditDireccion);
        editCorreoPerfilE = (EditText) findViewById(R.id.xmleditCorreoElectronico);
        editProvinciaPerfilE = (EditText) findViewById(R.id.xmleditProvincia);
        editDescripcionPerfilE = (EditText) findViewById(R.id.xmleditDescEmpresa);

        ImagePerfilEmpleador = (ImageView) findViewById(R.id.profile_image);

        btnVerificacionPerfilEmpleador = (Button) findViewById(R.id.xmlBtnVerificacionEmpresaDE);
        btnVerificacionPerfilEmpleador.setVisibility(View.INVISIBLE);


        //sIdEmpleador = "HmAtSRSnxdfxb0Z1kM2qoW1OvNo1";

        if(getIntent() != null){
            sIdEmpleador = getIntent().getStringExtra("sEmpresaIdAplico");
            if(!sIdEmpleador.isEmpty()){

                LoadEmpresa(sIdEmpleador);
            }
        }

    }

    public void LoadEmpresa(String sIdEmpleador) {
        DBperfilEmpleadores.child(sIdEmpleador).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Empleadores Datosempleadores = dataSnapshot.getValue(Empleadores.class);
                Log.d("verificacion", String.valueOf(dataSnapshot));

                //Provincia y descripcion no vienen porque no estan en la clase definidas.

                sImagenPerfilEmpleador = Datosempleadores.getsImagenEmpleador();
                Picasso.get().load(sImagenPerfilEmpleador).into(ImagePerfilEmpleador);

                sNombrePerfilE = Datosempleadores.getsNombreEmpleador();
                editNombrePerfilE.setText(sNombrePerfilE);

                sRncPerfilE = Datosempleadores.getsRncEmpleador();
                editRncPerfilE.setText(sRncPerfilE);

                sPaginaWebPerfilE = Datosempleadores.getsPaginaWebEmpleador();
                editPaginaWebPerfilE.setText(sPaginaWebPerfilE);


                sTelefonoPerfilE = Datosempleadores.getsTelefonoEmpleador();
                editTelefonoPerfilE.setText(sTelefonoPerfilE);

                sCorreoPerfilE = Datosempleadores.getsCorreoEmpleador();
                editCorreoPerfilE.setText(sCorreoPerfilE);

                sDireccionPerfilE = Datosempleadores.getsDireccionEmpleador();
                editDireccionPerfilE.setText(sDireccionPerfilE);

                sProvinciaPerfilE = Datosempleadores.getsProvinciaEmpleador();
                editProvinciaPerfilE.setText(sProvinciaPerfilE);

                sDescripcionPerfilE = Datosempleadores.getsDescripcionEmpleador();
                editDescripcionPerfilE.setText(sDescripcionPerfilE);

                sVerificarEmpleador = Datosempleadores.getsVerificacionEmpleador();
                Log.d("verificacion", String.valueOf(sVerificarEmpleador));

                if (sVerificarEmpleador != null) {

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
                Toast.makeText(PantallaDetallePerfilEmpresa.this, "No se realizo correctamente su aplicacion al empleo", Toast.LENGTH_LONG).show();
            }
        });

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalle_empresa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_VerEmpleos) {

            Intent intent = new Intent(PantallaDetallePerfilEmpresa.this, PantallaListaEmpleosEmpresa.class);
            intent.putExtra("sEmpresaId",sIdEmpleador);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
