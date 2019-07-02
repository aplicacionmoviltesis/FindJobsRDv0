package com.example.findjobsrdv0.GeneralesApp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.findjobsrdv0.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PantallaAgregarOpiniones_Sugerencias extends AppCompatActivity {

    private ImageView ImagenOpiSug;
    int IMAGE_REQUEST_CODE = 5;
    Uri mFilePathUriOpiSug;

    private ProgressDialog mProgressDialogOpiSug;

    private String sIdOpiSug,sTituloOpiSug,sDescripcionOpiSug,sFechaOpiSug,sImagenOpiSug,sIdUserOpiSug;
    private EditText editTituloOpiSug,editDecripcionOpiSug;

    private DatabaseReference OpiSugRefRegistrar;
    private FirebaseDatabase OpiSugDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_agregar_opiniones);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Opiniones y Sugerencias");

        ImagenOpiSug = (ImageView) findViewById(R.id.xmlImagenOpiSug);

        mProgressDialogOpiSug = new ProgressDialog(PantallaAgregarOpiniones_Sugerencias.this);

        editTituloOpiSug = (EditText) findViewById(R.id.xmlEditTituloOpiSug);
        editDecripcionOpiSug = (EditText) findViewById(R.id.xmlEditOpiSug);

        OpiSugDatabase = FirebaseDatabase.getInstance();
        OpiSugRefRegistrar = OpiSugDatabase.getReference("OpinionesSugerenciasApp");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
        sFechaOpiSug = simpleDateFormat.format(new Date());
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
            EnviarOpinionSugerencia();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void EnviarOpinionSugerencia() {
        mProgressDialogOpiSug.setTitle("Enviando...");
        mProgressDialogOpiSug.show();

        sIdOpiSug = OpiSugRefRegistrar.push().getKey();

        sTituloOpiSug = editTituloOpiSug.getText().toString().trim();
        sDescripcionOpiSug = editDecripcionOpiSug.getText().toString().trim();

//        sImagenOpiSug = "";
//        sIdUserOpiSug = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        sFechaOpiSug = "";

        sImagenOpiSug = "https://firebasestorage.googleapis.com/v0/b/findjobsrd.appspot.com/o/Imagenes%20Provincia%2Fbonao.jpg?alt=media&token=287c737f-70b4-4e8e-bcf0-11e077edc509";
        sIdUserOpiSug = "yo";


        if (TextUtils.isEmpty(sTituloOpiSug)) {
            Toast.makeText(this, "Por favor, Titula o Agrega alguna palabra relacionada a la opinion o sugerencia", Toast.LENGTH_LONG).show();
            mProgressDialogOpiSug.dismiss();
            return;
        }

        if (TextUtils.isEmpty(sDescripcionOpiSug)) {
            Toast.makeText(this, "Por favor, Describe la opinion o sugerencia con la mas claridad y brevedad posible", Toast.LENGTH_LONG).show();
            mProgressDialogOpiSug.dismiss();
            return;
        }


        Opiniones_Sugerencias opinionesSugerencias = new Opiniones_Sugerencias(sIdOpiSug, sTituloOpiSug, sDescripcionOpiSug,sFechaOpiSug,sImagenOpiSug ,sIdUserOpiSug);
        OpiSugRefRegistrar.child(sIdOpiSug).setValue(opinionesSugerencias);
        Toast.makeText(this, "Su Problema se Reporto exitosamente, le estaremos resolviendo lo mas pronto posible", Toast.LENGTH_LONG).show();
        LimpiarCampos();
        mProgressDialogOpiSug.dismiss();


    }

    private void LimpiarCampos() {
        editTituloOpiSug.setText("");
        editDecripcionOpiSug.setText("");
    }
}
