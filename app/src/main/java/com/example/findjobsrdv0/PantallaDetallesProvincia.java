package com.example.findjobsrdv0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PantallaDetallesProvincia extends AppCompatActivity {

    TextView TvNombreProvincia, TvDescripcionProvincia, TvDivisionProvincia;
    ImageView MostImagenProvincia;
    String sNombreProvinciakey = "";

    FirebaseDatabase database;
    Query DBprovincia;

    String perro = "santiago";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalles_provincia);

        database = FirebaseDatabase.getInstance();
        DBprovincia = database.getReference("provincias");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        MostImagenProvincia = (ImageView) findViewById(R.id.xmlImagenProvincia);
        TvNombreProvincia = (TextView) findViewById(R.id.xmlTvNombreProvincia);
        TvDescripcionProvincia = (TextView) findViewById(R.id.xmlTvDescripcionProvincia);
        TvDivisionProvincia = (TextView) findViewById(R.id.xmlTvDivisionProvincia);
        TvDivisionProvincia.setMovementMethod(new ScrollingMovementMethod());
        TvDescripcionProvincia.setMovementMethod(new ScrollingMovementMethod());





        if(getIntent() != null){
            sNombreProvinciakey = getIntent().getStringExtra("sProvinciaDE");
            if(!sNombreProvinciakey.isEmpty()){

                goDetalleProvincia(sNombreProvinciakey);
            }
        }






/*
        if (getIntent() != null) {
            NombreProvinciakey = getIntent().getStringExtra("sNombreProvinciakey");
            if (!NombreProvinciakey.isEmpty()) {

                goDetalleEmpleo(NombreProvinciakey);
            }
        }*/

        goDetalleProvincia(sNombreProvinciakey);

    }

    String hola;

    private void goDetalleProvincia(String sNombreProvinciakey) {

        Query q = DBprovincia.orderByChild("Nombre_Provincia").equalTo(sNombreProvinciakey);
        //.orderByChild("descripcion").equalTo("hola")
        // .orderByChild("coordenadas").equalTo("3.5");
///       Query jj= q.orderByChild("coordenadas").equalTo("3.5");

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // if(dataSnapshot.child("descripcion").equals("descripcion"))
                    //{
                    //if(dataSnapshot.child("coordenadas").equals("coordenadas")){

                    Log.d("hola", String.valueOf(dataSnapshot));
                    // los datos llegan hasta el objeto, pero no se cargan en la clase

                    //TvNombreProvincia.setText(dataSnapshot.child("nombre").toString());

                    Provincias Dprovincia = new Provincias();
                    Dprovincia.setsNombreProvincia(snapshot.child("Nombre_Provincia").getValue().toString());
                    Dprovincia.setsImagenProvincia(snapshot.child("Imagen_Provincia").getValue().toString());
                    Dprovincia.setsDescripcionProvincia(snapshot.child("Descripcion_Provincia").getValue().toString());
                    Dprovincia.setsCoordenadasProvincia(snapshot.child("Division_Provincia").getValue().toString());


                    // }
                    //}
                    //---Log.d("hola", String.valueOf(dataSnapshot));
                    // los datos llegan hasta el objeto, pero no se cargan en la clase

                    //----Provincias Dprovincia = dataSnapshot.getValue(Provincias.class);

                    //Log.d("holap", String.valueOf(Dprovincia));


                    Picasso.get().load(Dprovincia.getsImagenProvincia()).into(MostImagenProvincia);

                    TvNombreProvincia.setText(Dprovincia.getsNombreProvincia());
                    TvDescripcionProvincia.setText(Dprovincia.getsDescripcionProvincia());
                    TvDivisionProvincia.setText(Dprovincia.getsCoordenadasProvincia());








                    //---------hola = Dprovincia.getsNombreProvincia();
                    //---------Log.d("holap", TvNombreProvincia.toString());
                    //--------- Log.d("holapp",dataSnapshot.getValue().toString());
                    //--------- Log.d("holappklk",dataSnapshot.child("01").getValue().toString());
                    //--------- Log.d("holappklk",Dprovincia.getsNombreProvincia());
                    //Log.d("hol", String.valueOf(hola));

                    Log.d("probando", Dprovincia.getsImagenProvincia());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}

