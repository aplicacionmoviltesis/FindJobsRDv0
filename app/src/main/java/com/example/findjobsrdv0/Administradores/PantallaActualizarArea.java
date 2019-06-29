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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.findjobsrdv0.GeneralesApp.Areas;
import com.example.findjobsrdv0.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PantallaActualizarArea extends AppCompatActivity {

    private String sIdAreaActualizar = "";
    private String sNombreAreaActualizar, sDescAreaActualizar, sImagenAreaActualizar, sIdUserAdminAreaActualizar, sSubAreasActualizar;

    private EditText editNombreAreaActualizar, editDescAreaActualizar,editSubAreasActualizar;
    private ImageView ImageAreaActualizar;

    private DatabaseReference AreaRefActualizar;
    private FirebaseDatabase AreaDatabase;
    private ProgressDialog mProgressDialogA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_actualizar_area);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mProgressDialogA = new ProgressDialog(PantallaActualizarArea.this);

        ImageAreaActualizar = (ImageView) findViewById(R.id.xmlImagenAreaActualizar);

        editNombreAreaActualizar = (EditText) findViewById(R.id.xmlEditNombreAreaActualizar);
        editDescAreaActualizar = (EditText) findViewById(R.id.xmlEdiDescripcionAreaActualizar);
        editSubAreasActualizar = (EditText) findViewById(R.id.xmlEditSubAreasActualizar);

        editNombreAreaActualizar.setEnabled(false);
        editDescAreaActualizar.setEnabled(false);
        editSubAreasActualizar.setEnabled(false);

        AreaDatabase = FirebaseDatabase.getInstance();
        AreaRefActualizar = AreaDatabase.getReference("Areas");

        if (getIntent() != null) {
            sIdAreaActualizar = getIntent().getStringExtra("sAreaId");
            if (!sIdAreaActualizar.isEmpty()) {
                Log.d("holap", String.valueOf(sIdAreaActualizar));

                CargarArea(sIdAreaActualizar);
            }
        }
    }

    private void CargarArea(String sIdAreaActualizar) {
        Log.d("holap", String.valueOf(sIdAreaActualizar));

        AreaRefActualizar.child(sIdAreaActualizar).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("holap", String.valueOf(dataSnapshot));


                Areas areas = dataSnapshot.getValue(Areas.class);
                Log.d("holap", String.valueOf(areas));

                Picasso.get().load(areas.getsImagenArea()).into(ImageAreaActualizar);
                editNombreAreaActualizar.setText(areas.getsNombreArea());
                editDescAreaActualizar.setText(areas.getsDescripcionArea());
                editSubAreasActualizar.setText(areas.getsSubAreas());

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
            ActualizarArea(sIdAreaActualizar);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void ActivarCampos() {

        editNombreAreaActualizar.setEnabled(true);
        editDescAreaActualizar.setEnabled(true);
        editSubAreasActualizar.setEnabled(true);
    }

    private void ActualizarArea(String sIdAreaActualizar) {

        mProgressDialogA.setTitle("Actualizando...");
        mProgressDialogA.show();

        sNombreAreaActualizar = editNombreAreaActualizar.getText().toString().trim();
        sDescAreaActualizar = editDescAreaActualizar.getText().toString().trim();
        sSubAreasActualizar = editSubAreasActualizar.getText().toString().trim();

        sImagenAreaActualizar = "https://firebasestorage.googleapis.com/v0/b/findjobsrd.appspot.com/o/Imagenes%20Provincia%2Fbonao.jpg?alt=media&token=287c737f-70b4-4e8e-bcf0-11e077edc509";
        sIdUserAdminAreaActualizar = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (TextUtils.isEmpty(sNombreAreaActualizar)) {
            Toast.makeText(this, "Por favor, Ingrese el nombre", Toast.LENGTH_LONG).show();
            mProgressDialogA.dismiss();

            return;
        }

        if (TextUtils.isEmpty(sDescAreaActualizar)) {
            Toast.makeText(this, "Por favor, Ingrese la descripcion", Toast.LENGTH_LONG).show();
            mProgressDialogA.dismiss();

            return;
        }
        if (TextUtils.isEmpty(sSubAreasActualizar)) {
            Toast.makeText(this, "Por favor, Ingrese al menos una sub-area", Toast.LENGTH_LONG).show();
            mProgressDialogA.dismiss();

            return;
        }

        Areas areas = new Areas(sIdAreaActualizar,sNombreAreaActualizar,sDescAreaActualizar,sImagenAreaActualizar, sSubAreasActualizar,sIdUserAdminAreaActualizar);
        AreaRefActualizar.child(sIdAreaActualizar).setValue(areas);
        Toast.makeText(this, "La Actualizacion se Actualizo exitosamente", Toast.LENGTH_LONG).show();
        mProgressDialogA.dismiss();

        Intent intent = new Intent(PantallaActualizarArea.this, PantallaListaAreas.class);
        startActivity(intent);
    }

}
