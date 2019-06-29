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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class PantallaActualizarUnivesidad extends AppCompatActivity {

    private String sIdUniversidad = "";
    private String sNombreUniversidad, sUbicacionUniversidad, sImagenUniversidad, sIdUserAdminUni,sTelefonoUni,sDireccionUni,sPaginaWebUni;
    private EditText editNombreUni,editTelefonoUni,editDireccionUni,editPaginaWebUni;
    private ImageView ImageUniversidad;
    private DatabaseReference universidadesRefActualizar;
    private FirebaseDatabase UniDatabase;
    private ProgressDialog mProgressDialog;


    /////Spinner Provincia
    private SearchableSpinner spinProvUniversidad;
    private DatabaseReference provinciasRefUniActualizar;
    private List<Provincias> provincias;
    boolean IsFirstTimeClick = true;

    /////Spinner Provincia

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_actualizar_univesidad);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mProgressDialog = new ProgressDialog(PantallaActualizarUnivesidad.this);

        ImageUniversidad = (ImageView) findViewById(R.id.xmlImagenUniversidad);

        editNombreUni = (EditText) findViewById(R.id.xmlEditNombreUniversidad);
        editTelefonoUni = (EditText) findViewById(R.id.xmlEditTelefonoUniversidad);
        editDireccionUni = (EditText) findViewById(R.id.xmlEditDireccionUniversidad);
        editPaginaWebUni = (EditText) findViewById(R.id.xmlEditPaginaWebUniversidad);

        spinProvUniversidad = (SearchableSpinner) findViewById(R.id.xmlSpinUbicacionUniversidad);

        editNombreUni.setEnabled(false);
        spinProvUniversidad.setEnabled(false);
        editTelefonoUni.setEnabled(false);
        editDireccionUni.setEnabled(false);
        editPaginaWebUni.setEnabled(false);

        UniDatabase = FirebaseDatabase.getInstance();
        universidadesRefActualizar = UniDatabase.getReference("Universidades");

        /////Spinner Provincia

        provinciasRefUniActualizar = FirebaseDatabase.getInstance().getReference();

        spinProvUniversidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sUbicacionUniversidad = spinProvUniversidad.getSelectedItem().toString();
                    Log.d("valorSpinProv", sUbicacionUniversidad);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        provinciasRefUniActualizar.child("provincias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListProvincias = new ArrayList<String>();
                for (DataSnapshot provinciaSnapshot : dataSnapshot.getChildren()) {
                    String provinciaName = provinciaSnapshot.child("Nombre_Provincia").getValue(String.class);
                    ListProvincias.add(provinciaName);
                }

                ArrayAdapter<String> provinciasAdapter = new ArrayAdapter<String>(PantallaActualizarUnivesidad.this, android.R.layout.simple_spinner_item, ListProvincias);
                provinciasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinProvUniversidad.setAdapter(provinciasAdapter);
                spinProvUniversidad.setTitle("Seleccionar Provincia");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/////Spinner Provincia

        if (getIntent() != null) {
            sIdUniversidad = getIntent().getStringExtra("sUniversidadId");
            if (!sIdUniversidad.isEmpty()) {
                Log.d("holap", String.valueOf(sIdUniversidad));

                CargarUniversidad(sIdUniversidad);
            }
        }
    }

    private void CargarUniversidad(String sIdUniversidad) {
        Log.d("holap", String.valueOf(sIdUniversidad));

        universidadesRefActualizar.child(sIdUniversidad).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("holap", String.valueOf(dataSnapshot));


                    Universidades universidades = dataSnapshot.getValue(Universidades.class);
                    Log.d("holap", String.valueOf(universidades));
                    Log.d("holap", String.valueOf(universidades));

                    Picasso.get().load(universidades.getsImagenUniversidad()).into(ImageUniversidad);
                    editNombreUni.setText(universidades.getsNombreUniversidad());
                    spinProvUniversidad.setSelection(obtenerPosicionItem(spinProvUniversidad, universidades.getsUbicacionUniversidad()));

                }


            public int obtenerPosicionItem(Spinner spinner, String fruta) {
                //Creamos la variable posicion y lo inicializamos en 0
                int posicion = 0;
                //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
                //que lo pasaremos posteriormente
                for (int i = 0; i < spinner.getCount(); i++) {
                    //Almacena la posición del ítem que coincida con la búsqueda
                    if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(fruta)) {
                        posicion = i;
                    }
                }
                //Devuelve un valor entero (si encontro una coincidencia devuelve la
                // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
                return posicion;
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
            ActualizarUniversidad(sIdUniversidad);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void ActualizarUniversidad(String sIdUniversidad) {
        mProgressDialog.setTitle("Actualizando...");
        mProgressDialog.show();

        sNombreUniversidad = editNombreUni.getText().toString().trim();
        sTelefonoUni = editTelefonoUni.getText().toString().trim();
        sDireccionUni = editDireccionUni.getText().toString().trim();
        sPaginaWebUni = editPaginaWebUni.getText().toString().trim();
        //sUbicacionUniversidad = "";
        sImagenUniversidad = "https://firebasestorage.googleapis.com/v0/b/findjobsrd.appspot.com/o/Imagenes%20Provincia%2Fbonao.jpg?alt=media&token=287c737f-70b4-4e8e-bcf0-11e077edc509";
        sIdUserAdminUni = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (TextUtils.isEmpty(sNombreUniversidad)) {
            Toast.makeText(this, "Por favor, Ingrese el nombre", Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();

            return;
        }

        if (TextUtils.isEmpty(sUbicacionUniversidad)) {
            Toast.makeText(this, "Spinner vacío, por favor seleccione la Ubicacion", Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();

            return;
        }
        if (TextUtils.isEmpty(sTelefonoUni)) {
            Toast.makeText(this, "Por favor, Ingrese el numero de telefono", Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();

            return;
        }

        if (TextUtils.isEmpty(sDireccionUni)) {
            Toast.makeText(this, "Por favor, Ingrese la direccion de la institucion", Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();

            return;
        }
        if (TextUtils.isEmpty(sPaginaWebUni)) {
            Toast.makeText(this, "Por favor, Ingrese la pagina web de la institucion", Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();

            return;
        }


        Universidades universidad = new Universidades(sIdUniversidad,sNombreUniversidad,sUbicacionUniversidad,sImagenUniversidad, sDireccionUni,sTelefonoUni,sPaginaWebUni,sIdUserAdminUni);
        universidadesRefActualizar.child(sIdUniversidad).setValue(universidad);
        Toast.makeText(this, "La Actualizacion se Actualizo exitosamente", Toast.LENGTH_LONG).show();
        mProgressDialog.dismiss();

        Intent intent = new Intent(PantallaActualizarUnivesidad.this, PantallaListaUniversidades.class);
        startActivity(intent);

    }

    private void ActivarCampos() {

        spinProvUniversidad.setEnabled(true);
        editNombreUni.setEnabled(true);
        editTelefonoUni.setEnabled(true);
        editDireccionUni.setEnabled(true);
        editPaginaWebUni.setEnabled(true);
    }
}
