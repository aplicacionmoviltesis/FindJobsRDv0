package com.example.findjobsrdv0.Administradores;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.findjobsrdv0.GeneralesApp.Provincias;
import com.example.findjobsrdv0.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class PantallaAgregarUniversidades extends AppCompatActivity {

    private String sIdUniversidadR = "";
    private String sNombreUniRegistrar, sUbicacionUniRegistrar, sImagenUniRegistrar, sIdUserAdminUniRegistrar, sTelefonoUniRegistrar, sDireccionUniRegistrar, sPaginaWebUniRegistrar;
    private EditText editNombreUniR, editTelefonoUniR, editDireccionUniR, editPaginaWebUniR;
    private ImageView ImageUniRegistrar;
    private DatabaseReference universidadesRefRegistrar;
    private FirebaseDatabase UniDatabase;
    private ProgressDialog mProgressDialogR;


    /////Spinner Provincia
    private SearchableSpinner spinProvUniversidadR;
    private DatabaseReference provinciasRefUniRegistrar;
    private List<Provincias> provincias;
    boolean IsFirstTimeClick = true;

    /////Spinner Provincia

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_agregar_universidades);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mProgressDialogR = new ProgressDialog(PantallaAgregarUniversidades.this);

        ImageUniRegistrar = (ImageView) findViewById(R.id.xmlImagenUniRegistrar);

        editNombreUniR = (EditText) findViewById(R.id.xmlEditNombreUniRegistrar);
        editTelefonoUniR = (EditText) findViewById(R.id.xmlEditTelefonoUniRegistrar);
        editDireccionUniR = (EditText) findViewById(R.id.xmlEditDireccionUniRegistrar);
        editPaginaWebUniR = (EditText) findViewById(R.id.xmlEditPaginaWebUniRegistrar);

        spinProvUniversidadR = (SearchableSpinner) findViewById(R.id.xmlSpinUbicacionUniRegistrar);

        UniDatabase = FirebaseDatabase.getInstance();
        universidadesRefRegistrar = UniDatabase.getReference("Universidades");

        /////Spinner Provincia

        provinciasRefUniRegistrar = FirebaseDatabase.getInstance().getReference();

        spinProvUniversidadR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sUbicacionUniRegistrar = spinProvUniversidadR.getSelectedItem().toString();
                    Log.d("valorSpinProv", sUbicacionUniRegistrar);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        provinciasRefUniRegistrar.child("provincias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListProvincias = new ArrayList<String>();
                for (DataSnapshot provinciaSnapshot : dataSnapshot.getChildren()) {
                    String provinciaName = provinciaSnapshot.child("Nombre_Provincia").getValue(String.class);
                    ListProvincias.add(provinciaName);
                }

                ArrayAdapter<String> provinciasAdapter = new ArrayAdapter<String>(PantallaAgregarUniversidades.this, android.R.layout.simple_spinner_item, ListProvincias);
                provinciasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinProvUniversidadR.setAdapter(provinciasAdapter);
                spinProvUniversidadR.setTitle("Seleccionar Provincia");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/////Spinner Provincia

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
            RegistrarUniversidad();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void RegistrarUniversidad() {
        mProgressDialogR.setTitle("Añadiendo Universidad...");
        mProgressDialogR.show();

        sNombreUniRegistrar = editNombreUniR.getText().toString().trim();
        sTelefonoUniRegistrar = editTelefonoUniR.getText().toString().trim();
        sDireccionUniRegistrar = editDireccionUniR.getText().toString().trim();
        sPaginaWebUniRegistrar = editPaginaWebUniR.getText().toString().trim();
        //sUbicacionUniversidad = "";
        sImagenUniRegistrar = "https://firebasestorage.googleapis.com/v0/b/findjobsrd.appspot.com/o/Imagenes%20Provincia%2Fbonao.jpg?alt=media&token=287c737f-70b4-4e8e-bcf0-11e077edc509";
        //sIdUserAdminUniRegistrar = FirebaseAuth.getInstance().getCurrentUser().getUid();
        sIdUserAdminUniRegistrar = "yo";
        if (TextUtils.isEmpty(sNombreUniRegistrar)) {
            Toast.makeText(this, "Por favor, Ingrese el nombre", Toast.LENGTH_LONG).show();
            mProgressDialogR.dismiss();

            return;
        }

        if (TextUtils.isEmpty(sUbicacionUniRegistrar)) {
            Toast.makeText(this, "Spinner vacío, por favor seleccione la Ubicacion", Toast.LENGTH_LONG).show();
            mProgressDialogR.dismiss();

            return;
        }
        if (TextUtils.isEmpty(sTelefonoUniRegistrar)) {
            Toast.makeText(this, "Por favor, Ingrese el numero de telefono", Toast.LENGTH_LONG).show();
            mProgressDialogR.dismiss();

            return;
        }

        if (TextUtils.isEmpty(sDireccionUniRegistrar)) {
            Toast.makeText(this, "Por favor, Ingrese la direccion de la institucion", Toast.LENGTH_LONG).show();
            mProgressDialogR.dismiss();

            return;
        }
        if (TextUtils.isEmpty(sPaginaWebUniRegistrar)) {
            Toast.makeText(this, "Por favor, Ingrese la pagina web de la institucion", Toast.LENGTH_LONG).show();
            mProgressDialogR.dismiss();

            return;
        }

        sIdUniversidadR = universidadesRefRegistrar.push().getKey();

        Universidades universidad = new Universidades(sIdUniversidadR, sNombreUniRegistrar, sUbicacionUniRegistrar, sImagenUniRegistrar, sDireccionUniRegistrar, sTelefonoUniRegistrar, sPaginaWebUniRegistrar, sIdUserAdminUniRegistrar);
        universidadesRefRegistrar.child(sIdUniversidadR).setValue(universidad);
        Toast.makeText(this, "La Universidad se añadio exitosamente", Toast.LENGTH_LONG).show();
        LimpiarCampos();
        mProgressDialogR.dismiss();
    }

    private void LimpiarCampos() {

        editNombreUniR.setText("");
        editTelefonoUniR.setText("");
        editDireccionUniR.setText("");
        editPaginaWebUniR.setText("");
    }
}
