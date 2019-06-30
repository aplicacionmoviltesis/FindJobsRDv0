package com.example.findjobsrdv0.GeneralesApp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findjobsrdv0.Administradores.PantallaAgregarUniversidades;
import com.example.findjobsrdv0.Administradores.ProblemasAppReportar;
import com.example.findjobsrdv0.Administradores.Universidades;
import com.example.findjobsrdv0.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PantallaReportarProblemas extends AppCompatActivity {

    private ImageView ImagenUno,ImagenDos,ImagenTres;
    int IMAGE_REQUEST_CODE = 5;
    Uri mFilePathUriReportProblem;

    private ProgressDialog mProgressDialogReportarProblema;

    private String sIdProblemAppReport,sTituloProblem,sDescripcionProblem,sFechaProblem,sImagenProblem,sIdUserReport;
    private EditText editTituloProblem,editDecripcionProblem;

    private DatabaseReference ProblemReportRefRegistrar;
    private FirebaseDatabase ProblemDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_reportar_problemas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        ImagenUno = (ImageView) findViewById(R.id.xmlImagenUno);
        ImagenDos = (ImageView) findViewById(R.id.xmlImagenDos);
        ImagenTres = (ImageView) findViewById(R.id.xmlImagenTres);

        mProgressDialogReportarProblema = new ProgressDialog(PantallaReportarProblemas.this);

        editTituloProblem = (EditText) findViewById(R.id.xmlEditTituloProblem);
        editDecripcionProblem = (EditText) findViewById(R.id.xmlEditReportarProblema);

        ProblemDatabase = FirebaseDatabase.getInstance();
        ProblemReportRefRegistrar = ProblemDatabase.getReference("ProblemasReportadosApp");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
        sFechaProblem = simpleDateFormat.format(new Date());
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == IMAGE_REQUEST_CODE
//                && resultCode == RESULT_OK
//                && data != null
//                && data.getData() != null) {
//
//            mFilePathUriReportProblem = data.getData();
//
//            try {
//
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mFilePathUriReportProblem);
//                ImagenDos.setImageBitmap(bitmap);
//                ImagenUno.setImageBitmap(bitmap);
//
//            } catch (Exception e) {
//
//                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        }
//    }

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
            EnviarProblema();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void EnviarProblema() {
        mProgressDialogReportarProblema.setTitle("Reportando Problema...");
        mProgressDialogReportarProblema.show();

        sIdProblemAppReport = ProblemReportRefRegistrar.push().getKey();

        sTituloProblem = editTituloProblem.getText().toString().trim();
        sDescripcionProblem = editDecripcionProblem.getText().toString().trim();

        //sImagenProblem = "";
        //sIdUserReport = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //sFechaProblem = "";

        sImagenProblem = "https://firebasestorage.googleapis.com/v0/b/findjobsrd.appspot.com/o/Imagenes%20Provincia%2Fbonao.jpg?alt=media&token=287c737f-70b4-4e8e-bcf0-11e077edc509";
        sIdUserReport = "yo";


        if (TextUtils.isEmpty(sTituloProblem)) {
            Toast.makeText(this, "Por favor, Titula o Agrega alguna palabra relacionada al problema", Toast.LENGTH_LONG).show();
            mProgressDialogReportarProblema.dismiss();
            return;
        }

        if (TextUtils.isEmpty(sDescripcionProblem)) {
            Toast.makeText(this, "Por favor, Describe el problema con la mas claridad y brevedad posible", Toast.LENGTH_LONG).show();
            mProgressDialogReportarProblema.dismiss();
            return;
        }


        ProblemasAppReportar problemasAppReportar = new ProblemasAppReportar(sIdProblemAppReport, sTituloProblem, sDescripcionProblem,sFechaProblem,sImagenProblem ,sIdUserReport);
        ProblemReportRefRegistrar.child(sIdProblemAppReport).setValue(problemasAppReportar);
        Toast.makeText(this, "Su Problema se Reporto exitosamente, le estaremos resolviendo lo mas pronto posible", Toast.LENGTH_LONG).show();
        LimpiarCampos();
        mProgressDialogReportarProblema.dismiss();


    }

    private void LimpiarCampos() {
        editTituloProblem.setText("");
        editDecripcionProblem.setText("");
    }
}
