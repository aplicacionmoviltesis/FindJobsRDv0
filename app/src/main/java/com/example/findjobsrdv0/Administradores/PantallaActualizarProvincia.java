package com.example.findjobsrdv0.Administradores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.findjobsrdv0.GeneralesApp.Provincias;
import com.example.findjobsrdv0.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PantallaActualizarProvincia extends AppCompatActivity {

    private ImageView ImageProvAct;

    private String sIdUserAdminProvAct,sImagenProvAct;
    private String sIdProvinciaProAct = "";

    private String sNombreProvAct,sDescripcionProvAct, sDivTerritorialProvAct,sPoblacionProvAct,sEconomiaProvAct,sClimaProvAct,sAtractivosProvAct;
    private EditText editNombreProvAct,editDescripcionProvAct,editDivTerritorialProvAct,
            editPoblacionProvAct,editEconomiaProvAct,editClimaProvAct,editAtractivosProvAct;


    private DatabaseReference ProvinciaRefActualizar;
    private FirebaseDatabase ProvinciaDatabase;
    private ProgressDialog mProgressDialogProvAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_actualizar_provincia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mProgressDialogProvAct = new ProgressDialog(PantallaActualizarProvincia.this);

        ImageProvAct = (ImageView) findViewById(R.id.xmlImagenProvinciaAct);

        editNombreProvAct = (EditText) findViewById(R.id.xmlEditNombreProvinciaAct);
        editDescripcionProvAct = (EditText) findViewById(R.id.xmlEditDescripcionProvinciaAct);
        editDivTerritorialProvAct = (EditText) findViewById(R.id.xmlEditDivisionProvinciaAct);
        editPoblacionProvAct = (EditText) findViewById(R.id.xmlEditPoblacionProvinciaAct);
        editEconomiaProvAct = (EditText) findViewById(R.id.xmlEditEconomiaProvinciaAct);
        editClimaProvAct = (EditText) findViewById(R.id.xmlEditClimaProvinciaAct);
        editAtractivosProvAct = (EditText) findViewById(R.id.xmlEditAtractivosProvinciaAct);


        ProvinciaDatabase = FirebaseDatabase.getInstance();
        ProvinciaRefActualizar = ProvinciaDatabase.getReference("Provincias");

        editNombreProvAct.setEnabled(false);
        editDescripcionProvAct.setEnabled(false);
        editDivTerritorialProvAct.setEnabled(false);
        editPoblacionProvAct.setEnabled(false);
        editEconomiaProvAct.setEnabled(false);
        editClimaProvAct.setEnabled(false);
        editAtractivosProvAct.setEnabled(false);

        if (getIntent() != null) {
            sIdProvinciaProAct = getIntent().getStringExtra("sProvinciaId");
            if (!sIdProvinciaProAct.isEmpty()) {
                Log.d("holap", String.valueOf(sIdProvinciaProAct));

                CargarProvincia(sIdProvinciaProAct);
            }
        }


    }

    private void CargarProvincia(String sIdProvinciaProAct) {
        Log.d("holap", String.valueOf(sIdProvinciaProAct));

        ProvinciaRefActualizar.child(sIdProvinciaProAct).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("holap", String.valueOf(dataSnapshot));

                Provincias provincias = dataSnapshot.getValue(Provincias.class);
                Log.d("holap", String.valueOf(provincias));

                Picasso.get().load(provincias.getsImagenProvincia()).into(ImageProvAct);
                editNombreProvAct.setText(provincias.getsNombreProvincia());
                editDescripcionProvAct.setText(provincias.getsDescripcionProvincia());
                editDivTerritorialProvAct.setText(provincias.getsDivisionTerritorialProvincia());
                editPoblacionProvAct.setText(provincias.getsPoblacionProvincia());
                editEconomiaProvAct.setText(provincias.getsEconomiaProvincia());
                editClimaProvAct.setText(provincias.getsClimaProvincia());
                editAtractivosProvAct.setText(provincias.getsAtractivosProvincia());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
            ActivarCampos();
            return true;
        }
        if (id == R.id.menu_ActualizarPerfil) {
            //process your onClick here
            ActualizarProvincia(sIdProvinciaProAct);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void ActualizarProvincia(String sIdProvinciaProAct) {
        mProgressDialogProvAct.setTitle("Actualizando...");
        mProgressDialogProvAct.show();

        sNombreProvAct = editNombreProvAct.getText().toString().trim();
        sDescripcionProvAct = editDescripcionProvAct.getText().toString().trim();
        sDivTerritorialProvAct = editDivTerritorialProvAct.getText().toString().trim();
        sPoblacionProvAct = editPoblacionProvAct.getText().toString().trim();
        sEconomiaProvAct = editEconomiaProvAct.getText().toString().trim();
        sClimaProvAct = editClimaProvAct.getText().toString().trim();
        sAtractivosProvAct = editAtractivosProvAct.getText().toString().trim();

        sImagenProvAct = "https://firebasestorage.googleapis.com/v0/b/findjobsrd.appspot.com/o/Imagenes%20Provincia%2Fbonao.jpg?alt=media&token=287c737f-70b4-4e8e-bcf0-11e077edc509";
        //sIdUserAdminProvAct = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //sIdProvinciaProAct = "";
        sIdUserAdminProvAct = "yo klk";

        if (TextUtils.isEmpty(sNombreProvAct)) {
            Toast.makeText(this, "Por favor, Ingrese el nombre", Toast.LENGTH_LONG).show();
            mProgressDialogProvAct.dismiss();
            return;
        }

        if (TextUtils.isEmpty(sDescripcionProvAct)) {
            Toast.makeText(this, "Por favor, Ingrese una breve descripcion de la provincia", Toast.LENGTH_LONG).show();
            mProgressDialogProvAct.dismiss();
            return;
        }
        if (TextUtils.isEmpty(sClimaProvAct)) {
            Toast.makeText(this, "Por favor, Ingrese el clima predominante en la provincia", Toast.LENGTH_LONG).show();
            mProgressDialogProvAct.dismiss();
            return;
        }

        if (TextUtils.isEmpty(sAtractivosProvAct)) {
            Toast.makeText(this, "Por favor, Mencione algunos atractivos de dicha provincia", Toast.LENGTH_LONG).show();
            mProgressDialogProvAct.dismiss();
            return;
        }



        Provincias provincias = new Provincias(sIdProvinciaProAct,sNombreProvAct,sDescripcionProvAct,sDivTerritorialProvAct,sPoblacionProvAct,sImagenProvAct,sIdUserAdminProvAct,sEconomiaProvAct,sClimaProvAct,sAtractivosProvAct);
        ProvinciaRefActualizar.child(sIdProvinciaProAct).setValue(provincias);
        Toast.makeText(this, "La Provincia se Actualizo exitosamente", Toast.LENGTH_LONG).show();
        mProgressDialogProvAct.dismiss();

        Intent intent = new Intent(PantallaActualizarProvincia.this, PantallaListaProvincias.class);
        startActivity(intent);
    }

    private void ActivarCampos() {

        editNombreProvAct.setEnabled(true);
        editDescripcionProvAct.setEnabled(true);
        editDivTerritorialProvAct.setEnabled(true);
        editPoblacionProvAct.setEnabled(true);
        editEconomiaProvAct.setEnabled(true);
        editClimaProvAct.setEnabled(true);
        editAtractivosProvAct.setEnabled(true);
    }
}
