package com.example.findjobsrdv0.Administradores;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.findjobsrdv0.GeneralesApp.Areas;
import com.example.findjobsrdv0.GeneralesApp.Provincias;
import com.example.findjobsrdv0.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PantallaAgregarProvincias extends AppCompatActivity {

    private ImageView ImageProvReg;

    private String sIdProvinciaProvReg,sIdUserAdminProvReg,sImagenProvReg;

    private String sNombreProvReg,sDescripcionProvReg, sDivTerritorialProvReg,sPoblacionProvReg,sEconomiaProvReg,sClimaProvReg,sAtractivosProvReg;
    private EditText editNombreProvReg,editDescripcionProvReg,editDivTerritorialProvReg,
            editPoblacionProvReg,editEconomiaProvReg,editClimaProvReg,editAtractivosProvReg;


    private DatabaseReference ProvinciaRefRegistrar;
    private FirebaseDatabase ProvinciaDatabase;
    private ProgressDialog mProgressDialogProv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_agregar_provincias);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mProgressDialogProv = new ProgressDialog(PantallaAgregarProvincias.this);

        ImageProvReg = (ImageView) findViewById(R.id.xmlImagenProvincia);

        editNombreProvReg = (EditText) findViewById(R.id.xmlEditNombreProvincia);
        editDescripcionProvReg = (EditText) findViewById(R.id.xmlEditDescripcionProvincia);
        editDivTerritorialProvReg = (EditText) findViewById(R.id.xmlEditDivisionProvincia);
        editPoblacionProvReg = (EditText) findViewById(R.id.xmlEditPoblacionProvincia);
        editEconomiaProvReg = (EditText) findViewById(R.id.xmlEditEconomiaProvincia);
        editClimaProvReg = (EditText) findViewById(R.id.xmlEditClimaProvincia);
        editAtractivosProvReg = (EditText) findViewById(R.id.xmlEditAtractivosProvincia);


        ProvinciaDatabase = FirebaseDatabase.getInstance();
        ProvinciaRefRegistrar = ProvinciaDatabase.getReference("Provincias");
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
            RegistrarProvincia();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void RegistrarProvincia() {
        mProgressDialogProv.setTitle("Añadiendo Provincia...");
        mProgressDialogProv.show();

        sNombreProvReg = editNombreProvReg.getText().toString().trim();
        sDescripcionProvReg = editDescripcionProvReg.getText().toString().trim();
        sDivTerritorialProvReg = editDivTerritorialProvReg.getText().toString().trim();
        sPoblacionProvReg = editPoblacionProvReg.getText().toString().trim();
        sEconomiaProvReg = editEconomiaProvReg.getText().toString().trim();
        sClimaProvReg = editClimaProvReg.getText().toString().trim();
        sAtractivosProvReg = editAtractivosProvReg.getText().toString().trim();


        sImagenProvReg = "https://firebasestorage.googleapis.com/v0/b/findjobsrd.appspot.com/o/Imagenes%20Provincia%2Fbonao.jpg?alt=media&token=287c737f-70b4-4e8e-bcf0-11e077edc509";

        //sIdUserAdminProvReg = FirebaseAuth.getInstance().getCurrentUser().getUid();
        sIdUserAdminProvReg = "yo";

        sIdProvinciaProvReg = ProvinciaRefRegistrar.push().getKey();


        if (TextUtils.isEmpty(sNombreProvReg)) {
            Toast.makeText(this, "Por favor, Ingrese el nombre", Toast.LENGTH_LONG).show();
            mProgressDialogProv.dismiss();
            return;
        }

        if (TextUtils.isEmpty(sDescripcionProvReg)) {
            Toast.makeText(this, "Por favor, Ingrese la descripcion de la provincia", Toast.LENGTH_LONG).show();
            mProgressDialogProv.dismiss();
            return;
        }
        if (TextUtils.isEmpty(sClimaProvReg)) {
            Toast.makeText(this, "Por favor, Ingrese el clima predominante en dicha provincia", Toast.LENGTH_LONG).show();
            mProgressDialogProv.dismiss();
            return;
        }


        Provincias provincia = new Provincias(sIdProvinciaProvReg,sNombreProvReg,sDescripcionProvReg,sDivTerritorialProvReg,sPoblacionProvReg,sImagenProvReg,sIdUserAdminProvReg,sEconomiaProvReg,sClimaProvReg,sAtractivosProvReg);
        ProvinciaRefRegistrar.child(sIdProvinciaProvReg).setValue(provincia);
        Toast.makeText(this, "La Provincia se añadio exitosamente", Toast.LENGTH_LONG).show();
        LimpiarCampos();
        mProgressDialogProv.dismiss();
    }

    private void LimpiarCampos() {

        editNombreProvReg.setText("");
        editDescripcionProvReg.setText("");
        editDivTerritorialProvReg.setText("");
        editPoblacionProvReg.setText("");
        editEconomiaProvReg.setText("");
        editClimaProvReg.setText("");
        editAtractivosProvReg.setText("");

    }
}
