package com.example.findjobsrdv0.GeneralesApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findjobsrdv0.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PantallaDetallesUniversidad extends AppCompatActivity {

    TextView TvNombreUniDetalle, TvTelefonoUniDetalle, TvPaginaWebUniDetalle,TvUbicacionUniDetalle,TvDireccionUniDetalle;
    ImageView ImagenUniDetalle;
    String sNombreUniDetallekey = "";

    private FirebaseDatabase databaseUni;
    private DatabaseReference DbUni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalles_universidad);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TvNombreUniDetalle = (TextView) findViewById(R.id.xmlTvNombreUniDetalle);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoslab.bold.ttf");
        TvNombreUniDetalle.setTypeface(face);

        databaseUni = FirebaseDatabase.getInstance();
        DbUni = databaseUni.getReference("Universidades");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);



        ImagenUniDetalle = (ImageView) findViewById(R.id.xmlImagenUniDetalle);
        TvTelefonoUniDetalle = (TextView) findViewById(R.id.xmlTvTelefonoUniDetalle);
        TvPaginaWebUniDetalle = (TextView) findViewById(R.id.xmlTvPaginaWebUniDetalle);
        TvUbicacionUniDetalle = (TextView) findViewById(R.id.xmlTvUbicacionUniDetalle);
        TvDireccionUniDetalle = (TextView) findViewById(R.id.xmlTvDireccionUniDetalle);

        if (getIntent() != null) {
            sNombreUniDetallekey = getIntent().getStringExtra("sNombreUniDetallekey");
            if (!sNombreUniDetallekey.isEmpty()) {
                goDetalleUniversidad(sNombreUniDetallekey);
            }
        }
//        sNombreUniDetallekey = "UNPHU";
//        goDetalleUniversidad(sNombreUniDetallekey);

    }

    private void goDetalleUniversidad(String sNombreUniDetallekey) {
        Toast.makeText(this, "La Actualizacion se Actualizo exitosamente", Toast.LENGTH_LONG).show();

        Log.d("holapklk", String.valueOf(sNombreUniDetallekey));

        Query queryUni = DbUni.orderByChild("sNombreUniversidad").equalTo(sNombreUniDetallekey);
        queryUni.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Universidades universidades = snapshot.getValue(Universidades.class);

                    Picasso.get().load(universidades.getsImagenUniversidad()).into(ImagenUniDetalle);

                    TvNombreUniDetalle.setText(universidades.getsNombreUniversidad());
                    TvTelefonoUniDetalle.setText(universidades.getsTelefonoUniversidad());
                    TvPaginaWebUniDetalle.setText(universidades.getsPaginaWebUniversidad());
                    TvUbicacionUniDetalle.setText(universidades.getsUbicacionUniversidad());
                    TvDireccionUniDetalle.setText(universidades.getsDireccionUniversidad());
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
