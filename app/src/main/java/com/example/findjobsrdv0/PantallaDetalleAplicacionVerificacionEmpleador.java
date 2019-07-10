package com.example.findjobsrdv0;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PantallaDetalleAplicacionVerificacionEmpleador extends AppCompatActivity {

    private TextView tvNombre;
    private PDFView pdfView;
    private FirebaseDatabase databaseVerifEmp;
    private DatabaseReference databaseReferenceVerifEmp;

    private WebView joder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalle_aplicacion_verificacion_empleador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseVerifEmp = FirebaseDatabase.getInstance();
        databaseReferenceVerifEmp = databaseVerifEmp.getReference("");

        pdfView = (PDFView) findViewById(R.id.pdfView);
        tvNombre = (TextView) findViewById(R.id.tvNombre);

        joder = (WebView) findViewById(R.id.leerdocu);

        String klkdocu = "http://www.economia.unam.mx/secss/docs/tesisfe/CanizalesPR/Introduccion.PDF";
       // new RetrievePDFStream().execute(Uri.parse(klkdocu));

        joder.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    class RetrievePDFStream extends AsyncTask<String, Void, InputStream>
    {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try
            {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode() == 200)
                {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            catch (IOException e)
            {
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream);
        }
    }
}
