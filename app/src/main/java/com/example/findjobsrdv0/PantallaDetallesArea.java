package com.example.findjobsrdv0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PantallaDetallesArea extends AppCompatActivity {

    TextView TvNombreArea, TvDescripcionArea, TvSubAreasA;
    ImageView MostImagenArea;
    String sNombreAreakey = "";

    private FirebaseDatabase databaseArea;
    Query DBarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalles_area);

        databaseArea = FirebaseDatabase.getInstance();
        DBarea = databaseArea.getReference("Areas");

        MostImagenArea = (ImageView) findViewById(R.id.xmlImagenArea);
        TvNombreArea = (TextView) findViewById(R.id.xmlTvNombreArea);
        TvDescripcionArea = (TextView) findViewById(R.id.xmlTvSubAreasA);
        TvSubAreasA = (TextView) findViewById(R.id.xmlTvDescripcionArea);


        if(getIntent() != null){
            sNombreAreakey = getIntent().getStringExtra("sAreaDE");
            if(!sNombreAreakey.isEmpty()){

                goDetalleArea(sNombreAreakey);
            }
        }


        goDetalleArea(sNombreAreakey);
    }

    private void goDetalleArea(String sNombreAreakey) {

        Query queryArea = DBarea.orderByChild("nombre").equalTo(sNombreAreakey);
        queryArea.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Log.d("hola", String.valueOf(dataSnapshot));

                    Areas Dareas = new Areas();
                    Dareas.setsNombreArea(snapshot.child("nombre").getValue().toString());
                    Log.d("probando", Dareas.getsNombreArea());

                    Dareas.setsImagenArea(snapshot.child("imagenarea").getValue().toString());
                    Dareas.setsDescripcionArea(snapshot.child("descripcion").getValue().toString());
                    Dareas.setsSubAreas(snapshot.child("subarea").getValue().toString());

                    Picasso.get().load(Dareas.getsImagenArea()).into(MostImagenArea);

                    TvNombreArea.setText(Dareas.getsNombreArea());
                    TvDescripcionArea.setText(Dareas.getsDescripcionArea());
                    TvSubAreasA.setText(Dareas.getsSubAreas());

                    Log.d("probando", Dareas.getsNombreArea());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
