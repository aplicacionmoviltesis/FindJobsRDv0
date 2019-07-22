package com.example.findjobsrdv0.GeneralesApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findjobsrdv0.Administradores.PantallaConfiguracionAdministrador;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaPrincipalEmpleador;
import com.example.findjobsrdv0.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PantallaConfiguracion extends AppCompatActivity {

    private Button btnCambiarPass, btnAcercaApp, btnContactoNosotros, btnReportarProblema, btnConfigAdmin;

    private ProgressDialog EprogressDialogAdmin;

    private FirebaseAuth mAuthAdministrador;

    private String emailadmin, passAdmin;

    private FirebaseDatabase fDatabaseAdmin;
    private DatabaseReference dBReferencesAdmin;

    private EditText editEmailAdmin, editPassAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_configuracion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        EprogressDialogAdmin = new ProgressDialog(this);

        fDatabaseAdmin = FirebaseDatabase.getInstance();
        dBReferencesAdmin = fDatabaseAdmin.getReference(getResources().getString(R.string.Ref_AdministradoresApp));

        mAuthAdministrador = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.titulo_Configuracion));

        btnCambiarPass = (Button) findViewById(R.id.xmlBtnCambiarContrasena);
        btnCambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaConfiguracion.this, PantallaCambiarContrasena.class);
                startActivityForResult(intent, 0);
            }
        });

        btnAcercaApp = (Button) findViewById(R.id.xmlBtnAcercaDe);
        btnAcercaApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaConfiguracion.this, PantallaAcercadeApp.class);
                startActivityForResult(intent, 0);
            }
        });

        btnReportarProblema = (Button) findViewById(R.id.xmlBtnReportarProblema);
        btnReportarProblema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaConfiguracion.this, PantallaReportarProblemas.class);
                startActivityForResult(intent, 0);
            }
        });

        btnContactoNosotros = (Button) findViewById(R.id.xmlBtnContactoNosotros);
        btnContactoNosotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactoNosotros();
            }
        });

        btnConfigAdmin = (Button) findViewById(R.id.xmlBtnConfigAdmin);
        btnConfigAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccesoAdmin();
            }
        });

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void ContactoNosotros() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PantallaConfiguracion.this);
        LayoutInflater inflater = (PantallaConfiguracion.this).getLayoutInflater();

        builder.setTitle("Contacto Con: ");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_contactocorreo);
        builder.setView(inflater.inflate(R.layout.pantalla_contacto_nosotros, null))
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create();
        builder.show();
    }

    public void AccesoAdmin() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.login_acceso_admin, null);

        final TextView TvTiLoginAdmin = (TextView) dialogView.findViewById(R.id.xmlTiLoginAdmin);
        Typeface face = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts_robotos));
        TvTiLoginAdmin.setTypeface(face);

        editEmailAdmin = (EditText) dialogView.findViewById(R.id.xmlEditEmailAdmin);
        editPassAdmin = (EditText) dialogView.findViewById(R.id.xmlEditPassAdmin);

        Button btnSalirAdmin = (Button) dialogView.findViewById(R.id.xmlBtnSalirAdmin);
        Button btnEntrarAdmin = (Button) dialogView.findViewById(R.id.xmlBtnEntrarAdmin);

        btnSalirAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        btnEntrarAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                emailadmin = editEmailAdmin.getText().toString().trim();
                passAdmin = editPassAdmin.getText().toString().trim();

                if (TextUtils.isEmpty(emailadmin)) {
                    editEmailAdmin.setError("Campo vacío, por favor escriba el correo");
                    return;
                }
                if (TextUtils.isEmpty(passAdmin)) {
                    editPassAdmin.setError("Campo vacío, por favor escriba la contraseña");
                    return;
                }
                EprogressDialogAdmin.setMessage("Iniciando sesión...");
                EprogressDialogAdmin.show();
                LoginAdministrador(emailadmin, passAdmin);
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    public void LoginAdministrador(String emailadmin, String passAdmin) {
        mAuthAdministrador.signInWithEmailAndPassword(emailadmin, passAdmin)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuthAdministrador.getCurrentUser();
                            if (user.isEmailVerified()) {
                                Log.i("Probando", mAuthAdministrador.getUid());

                                dBReferencesAdmin.child(mAuthAdministrador.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            Toast.makeText(PantallaConfiguracion.this, "Bienvenido: " + editEmailAdmin.getText(), Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(PantallaConfiguracion.this, PantallaConfiguracionAdministrador.class);
                                            startActivityForResult(intent, 0);

                                        } else {
                                            Toast.makeText(PantallaConfiguracion.this, "El Usuario: " + editEmailAdmin.getText() + " no tiene acceso de Administrador", Toast.LENGTH_LONG).show();
                                            mAuthAdministrador.signOut();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                Toast.makeText(PantallaConfiguracion.this, "Correo Electronico no verificado", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(PantallaConfiguracion.this, "No se pudo Iniciar Sesion", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(PantallaConfiguracion.this, "No se pudo Iniciar Sesion", Toast.LENGTH_LONG).show();
                            }
                        }
                        EprogressDialogAdmin.dismiss();
                    }
                });
    }
}
