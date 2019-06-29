package com.example.findjobsrdv0.Administradores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.findjobsrdv0.Clases_EmpleoCompleto.Empleos;
import com.example.findjobsrdv0.GeneralesApp.Areas;
import com.example.findjobsrdv0.GeneralesApp.Provincias;
import com.example.findjobsrdv0.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.List;

public class PantallaActualizarUnivesidad extends AppCompatActivity {

    private String sIdUniversidad = "";
    private String sNombreUniversidad, sUbicacionUniversidad, sImagenUniversidad;
    private EditText editNombreUni;
    private ImageView ImageUniversidad;
    private DatabaseReference universidadesRefActualizar;
    private FirebaseDatabase UniDatabase;


    /////Spinner Provincia
    private SearchableSpinner spinUniversidades;
    private DatabaseReference provinciasRefActualizar;
    private List<Provincias> provincias;
    /////Spinner Provincia

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_actualizar_univesidad);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        ImageUniversidad = (ImageView) findViewById(R.id.xmlImagenUniversidad);

        editNombreUni = (EditText) findViewById(R.id.xmlEditNombreUniversidad);
        spinUniversidades = (SearchableSpinner) findViewById(R.id.xmlSpinUbicacionUniversidad);

        UniDatabase = FirebaseDatabase.getInstance();
        universidadesRefActualizar = UniDatabase.getReference("Universidades");

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
                    spinUniversidades.setSelection(obtenerPosicionItem(spinUniversidades, universidades.getsUbicacionUniversidad()));

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
}
