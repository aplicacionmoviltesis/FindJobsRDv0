package com.example.findjobsrdv0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PantallaDetallesProvincia extends AppCompatActivity {

    TextView TvNombreProvincia, TvDescripcionProvincia, TvCoordenadasProvincia;
    ImageView MostImagenProvincia;
    String NombreProvinciakey = "";

    FirebaseDatabase database;
    Query DBprovincia;

    String perro="santiago";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalles_provincia);

        database = FirebaseDatabase.getInstance();
        DBprovincia = database.getReference("provincias");

        MostImagenProvincia = (ImageView) findViewById(R.id.xmlImagenProvincia);
        TvNombreProvincia = (TextView) findViewById(R.id.xmlTvNombreProvincia);
        TvDescripcionProvincia = (TextView) findViewById(R.id.xmlTvCoordenadasProvincia);
        TvCoordenadasProvincia = (TextView) findViewById(R.id.xmlTvDescripcionProvincia);









/*
        if (getIntent() != null) {
            NombreProvinciakey = getIntent().getStringExtra("sNombreProvinciakey");
            if (!NombreProvinciakey.isEmpty()) {

                goDetalleEmpleo(NombreProvinciakey);
            }
        }*/

        goDetalleProvincia();

    }
    String hola;
    private void goDetalleProvincia() {

        Query q = DBprovincia.orderByChild("descripcion").equalTo("klk");
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
                            Dprovincia.setsNombreProvincia(snapshot.child("nombre").getValue().toString());
                    Dprovincia.setsImagenProvincia(snapshot.child("imagen").getValue().toString());








                            // }
                    //}
                    //---Log.d("hola", String.valueOf(dataSnapshot));
                    // los datos llegan hasta el objeto, pero no se cargan en la clase

                    //----Provincias Dprovincia = dataSnapshot.getValue(Provincias.class);

                    //Log.d("holap", String.valueOf(Dprovincia));


                    Picasso.get().load(Dprovincia.getsImagenProvincia()).into(MostImagenProvincia);

                    TvNombreProvincia.setText(Dprovincia.getsNombreProvincia());
                    TvDescripcionProvincia.setText(Dprovincia.getsDescripcionProvincia());
                    TvCoordenadasProvincia.setText(Dprovincia.getsCoordenadasProvincia());

                     //---------hola = Dprovincia.getsNombreProvincia();
                    //---------Log.d("holap", TvNombreProvincia.toString());
                    //--------- Log.d("holapp",dataSnapshot.getValue().toString());
                    //--------- Log.d("holappklk",dataSnapshot.child("01").getValue().toString());
                    //--------- Log.d("holappklk",Dprovincia.getsNombreProvincia());
                    //Log.d("hol", String.valueOf(hola));

                    Log.d("probando",Dprovincia.getsNombreProvincia());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}

