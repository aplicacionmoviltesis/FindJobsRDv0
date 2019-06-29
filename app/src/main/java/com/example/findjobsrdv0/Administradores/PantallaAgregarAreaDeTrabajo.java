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
import com.example.findjobsrdv0.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PantallaAgregarAreaDeTrabajo extends AppCompatActivity {

    private String sIdAreaR;
    private String sNombreAreaRegistrar, sDescAreaRegistrar, sImagenAreaRegistrar, sIdUserAdminAreaRegistrar, sSubAreasRegistrar;

    private EditText editNombreAreaR, editDescAreaRegistrar,editSubAreasRegistrar;
    private ImageView ImageAreaRegistrar;

    private DatabaseReference AreaRefRegistrar;
    private FirebaseDatabase AreaDatabase;
    private ProgressDialog mProgressDialogR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_agregar_area_de_trabajo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mProgressDialogR = new ProgressDialog(PantallaAgregarAreaDeTrabajo.this);

        ImageAreaRegistrar = (ImageView) findViewById(R.id.xmlImagenAreaRegistrar);

        editNombreAreaR = (EditText) findViewById(R.id.xmlEditNombreAreaRegistrar);
        editDescAreaRegistrar = (EditText) findViewById(R.id.xmlEdiDescripcionAreaRegistrar);
        editSubAreasRegistrar = (EditText) findViewById(R.id.xmlEditSubAreasRegistrar);

        AreaDatabase = FirebaseDatabase.getInstance();
        AreaRefRegistrar = AreaDatabase.getReference("Areas");
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
            RegistrarArea();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void RegistrarArea() {
        mProgressDialogR.setTitle("Añadiendo Area...");
        mProgressDialogR.show();

        sNombreAreaRegistrar = editNombreAreaR.getText().toString().trim();
        sDescAreaRegistrar = editDescAreaRegistrar.getText().toString().trim();
        sSubAreasRegistrar = editSubAreasRegistrar.getText().toString().trim();
        sImagenAreaRegistrar = "https://firebasestorage.googleapis.com/v0/b/findjobsrd.appspot.com/o/Imagenes%20Provincia%2Fbonao.jpg?alt=media&token=287c737f-70b4-4e8e-bcf0-11e077edc509";
        //sIdUserAdminAreaRegistrar = FirebaseAuth.getInstance().getCurrentUser().getUid();
        sIdUserAdminAreaRegistrar = "yo";

        if (TextUtils.isEmpty(sNombreAreaRegistrar)) {
            Toast.makeText(this, "Por favor, Ingrese el nombre", Toast.LENGTH_LONG).show();
            mProgressDialogR.dismiss();

            return;
        }

        if (TextUtils.isEmpty(sDescAreaRegistrar)) {
            Toast.makeText(this, "Por favor, Ingrese la descripcion del area", Toast.LENGTH_LONG).show();
            mProgressDialogR.dismiss();

            return;
        }
        if (TextUtils.isEmpty(sSubAreasRegistrar)) {
            Toast.makeText(this, "Por favor, Ingrese alguna Sub-Area relacionada", Toast.LENGTH_LONG).show();
            mProgressDialogR.dismiss();

            return;
        }

        sIdAreaR = AreaRefRegistrar.push().getKey();

        Areas areas = new Areas(sIdAreaR, sNombreAreaRegistrar, sDescAreaRegistrar,sImagenAreaRegistrar, sSubAreasRegistrar,sIdUserAdminAreaRegistrar);
        AreaRefRegistrar.child(sIdAreaR).setValue(areas);
        Toast.makeText(this, "El Area se añadio exitosamente", Toast.LENGTH_LONG).show();
        LimpiarCampos();
        mProgressDialogR.dismiss();
    }

    private void LimpiarCampos() {

        editNombreAreaR.setText("");
        editDescAreaRegistrar.setText("");
        editSubAreasRegistrar.setText("");
    }
}
