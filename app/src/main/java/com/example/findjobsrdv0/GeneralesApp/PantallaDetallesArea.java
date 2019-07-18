package com.example.findjobsrdv0.GeneralesApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.findjobsrdv0.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PantallaDetallesArea extends AppCompatActivity {

    private TextView TvNombreArea, TvDescripcionArea, TvSubAreasA;
    private ImageView MostImagenArea;
    private String sNombreAreakey = "";

    private FirebaseDatabase databaseArea;
    private DatabaseReference DBarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalles_area);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TvNombreArea = (TextView) findViewById(R.id.xmlTvNombreArea);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoslab.bold.ttf");
        TvNombreArea.setTypeface(face);

        databaseArea = FirebaseDatabase.getInstance();
        DBarea = databaseArea.getReference(getResources().getString(R.string.Ref_Areas));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        MostImagenArea = (ImageView) findViewById(R.id.xmlImagenArea);
        TvDescripcionArea = (TextView) findViewById(R.id.xmlTvDescripcionArea);
        TvSubAreasA = (TextView) findViewById(R.id.xmlTvSubAreasA);


        if (getIntent() != null) {
            sNombreAreakey = getIntent().getStringExtra("sAreaDE");
            if (!sNombreAreakey.isEmpty()) {
                goDetalleArea(sNombreAreakey);
            }
        }
//        sNombreAreakey="ingenieria civil";
//        goDetalleArea(sNombreAreakey);

    }

    private void goDetalleArea(String sNombreAreakey) {

        Query queryArea = DBarea.orderByChild("sNombreArea").equalTo(sNombreAreakey);
        queryArea.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

//                    Log.d("hola", String.valueOf(dataSnapshot));
//
//                    Areas Dareas = new Areas();
//                    Dareas.setsNombreArea(snapshot.child("Nombre_Area").getValue().toString());
//                    Dareas.setsImagenArea(snapshot.child("Imagen_Area").getValue().toString());
//                    Dareas.setsDescripcionArea(snapshot.child("Descripcion_Area").getValue().toString());
//                    Dareas.setsSubAreas(snapshot.child("Areas_Relacionadas").getValue().toString());
//
//                    TvNombreArea.setText(Dareas.getsNombreArea());
//                    TvDescripcionArea.setText(Dareas.getsDescripcionArea());
//                    TvSubAreasA.setText(Dareas.getsSubAreas());
//
//                    Log.d("foto", Dareas.getsImagenArea());
//                    Picasso.get().load(Dareas.getsImagenArea()).into(MostImagenArea);

                    Areas areas = snapshot.getValue(Areas.class);

                    Picasso.get().load(areas.getsImagenArea()).into(MostImagenArea);

                    TvNombreArea.setText(areas.getsNombreArea());
                    TvDescripcionArea.setText(areas.getsDescripcionArea());
                    TvSubAreasA.setText(areas.getsSubAreas());

                }
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
