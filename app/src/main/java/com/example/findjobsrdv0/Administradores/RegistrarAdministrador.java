package com.example.findjobsrdv0.Administradores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findjobsrdv0.Pantallas_CurriculosCompleto.PantallaLoginBuscador;
import com.example.findjobsrdv0.Pantallas_CurriculosCompleto.PantallaRegistroBuscador;
import com.example.findjobsrdv0.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarAdministrador extends AppCompatActivity {

    private EditText registroNombreAdmin, registroApellidoAdmin, registroCorreoAdmin, registroTelefonoAdmin,
            registroPassAdmin, registroPass2Admin;

    private Button BbtnRegistrarAdmin, BbtnIniciarsesionAdmin;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    private FirebaseDatabase databaseAdmin;
    private DatabaseReference databaseReferenceAdmin;
    private String IdAdmin, NombreAdmin, ApellidoAdmin, CorreoAdmin, TelefonoAdmin, PassAdmin, ConfirmarPass2Admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_administrador);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Crear Usuario Administrador");

        firebaseAuth = FirebaseAuth.getInstance();

        databaseAdmin = FirebaseDatabase.getInstance();
        databaseReferenceAdmin = databaseAdmin.getReference(getResources().getString(R.string.Ref_AdministradoresApp));

        progressDialog = new ProgressDialog(this);

        registroNombreAdmin = (EditText) findViewById(R.id.xmleditRegistrarnombreAdmin);
        registroApellidoAdmin = (EditText) findViewById(R.id.xmleditRegistrarApellidoAdmin);
        registroCorreoAdmin = (EditText) findViewById(R.id.xmleditregistraremailAdmin);
        registroTelefonoAdmin = (EditText) findViewById(R.id.xmleditregistrartelefonoAdmin);
        registroPassAdmin = (EditText) findViewById(R.id.xmleditregistrarcontrasenaAdmin);
        registroPass2Admin = (EditText) findViewById(R.id.xmleditconfirmarcontrasenaAdmin);

        BbtnRegistrarAdmin = (Button) findViewById(R.id.xmlbtnRegistrarBuscadorAdmin);

        BbtnRegistrarAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarAdmin();
            }
        });

    }

    private void RegistrarAdmin() {

        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        NombreAdmin = registroNombreAdmin.getText().toString().trim();
        ApellidoAdmin = registroApellidoAdmin.getText().toString().trim();
        CorreoAdmin = registroCorreoAdmin.getText().toString().trim();
        TelefonoAdmin = registroTelefonoAdmin.getText().toString().trim();

        PassAdmin = registroPassAdmin.getText().toString().trim();
        ConfirmarPass2Admin = registroPass2Admin.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(CorreoAdmin, PassAdmin)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            IdAdmin = user.getUid();
                            Administrador administrador = new Administrador(IdAdmin, NombreAdmin,
                                    ApellidoAdmin, CorreoAdmin, TelefonoAdmin);
                            databaseReferenceAdmin.child(IdAdmin).setValue(administrador);

                            user.sendEmailVerification();
                            firebaseAuth.signOut();

                            Toast.makeText(RegistrarAdministrador.this, "Se ha registrado el usuario Administrador con el email: " + CorreoAdmin + " Espere el correo de verificacion", Toast.LENGTH_LONG).show();

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegistrarAdministrador.this, "El usuario ya existe", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(RegistrarAdministrador.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
