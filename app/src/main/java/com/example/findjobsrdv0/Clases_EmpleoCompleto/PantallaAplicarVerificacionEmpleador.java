package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findjobsrdv0.Adaptadores_Empleador.AplicarVerificacionEmpleador;
import com.example.findjobsrdv0.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PantallaAplicarVerificacionEmpleador extends AppCompatActivity {

    private EditText etNombreDocumento;
    private Button btnRegistrarDocumento;

    //String NombreDocumVerifEmp;


    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private String userActivo;
    String NombreEmpresa;

//    private Uri ur1;

    String sFechaVerifEmp;

    private String sEstadoReportProblem = "Pendiente";
    private ActionBar actionBar;
    private FirebaseAuth mAuthEmpleador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_aplicar_verificacion_empleo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mAuthEmpleador = FirebaseAuth.getInstance();
        FirebaseUser user = mAuthEmpleador.getCurrentUser();
        actionBar.setTitle(user.getDisplayName());

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("SolicitudVerificacionEmpleador");

        userActivo = FirebaseAuth.getInstance().getCurrentUser().getUid();

        etNombreDocumento = (EditText) findViewById(R.id.NombreArchivoVerEmp);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
        sFechaVerifEmp = simpleDateFormat.format(new Date());

        btnRegistrarDocumento = (Button) findViewById(R.id.btnRegistrarArchivo);
        btnRegistrarDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(!NombreEmpresa.isEmpty()){
                seleccionArchivoPDF();
            }else {
                Toast.makeText(PantallaAplicarVerificacionEmpleador.this, "Favor llenar el campo Nombre Empresa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void seleccionArchivoPDF() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccione Archivo PDF"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            SubirArchivoPDF(data.getData());
        }
    }

    private void SubirArchivoPDF(Uri data) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Subiendo Documento...");
        progressDialog.show();

        StorageReference Reference = storageReference.child("SolicitudVerificacionEmpleador/" + System.currentTimeMillis() + ".pdf");
        Reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isComplete()) ;
                Uri sDocumentoVerifEmp = uri.getResult();

                String Id = databaseReference.push().getKey();

                AplicarVerificacionEmpleador aplicarVerificacionEmpleador = new AplicarVerificacionEmpleador(Id, userActivo, etNombreDocumento.getText().toString(),
                        sDocumentoVerifEmp.toString(), sEstadoReportProblem, sFechaVerifEmp);
//                databaseReference.child( databaseReference.push().getKey() ).setValue( aplicarVerificacionEmpleador  );

                databaseReference.child(Id).setValue(aplicarVerificacionEmpleador);

                Toast.makeText(PantallaAplicarVerificacionEmpleador.this, "Documento subido", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();

                // registrar();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                progressDialog.setMessage("Subiendo:" + (int) progress + "%");
            }
        });
    }
}

//    private void registrar(){
//
//        NombreDocumVerifEmp = etNombreDocumento.getText().toString().trim();
//
//
//        String Id = databaseReference.push().getKey();
//
//        AplicarVerificacionEmpleador aplicarVerificacionEmpleador = new AplicarVerificacionEmpleador(Id,
//                userActivo,NombreDocumVerifEmp,ur1,"klk");
//
//        databaseReference.child( Id ).setValue( aplicarVerificacionEmpleador );
//        Toast.makeText( PantallaAplicarVerificacionEmpleador.this, "Curriculo subida exitosamente...", Toast.LENGTH_LONG ).show();
//
//    }
//}
