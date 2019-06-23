package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findjobsrdv0.GeneralesApp.PantallaRecuperarContrasena;
import com.example.findjobsrdv0.GeneralesApp.Preferences;
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

public class PantallaLoginEmpleador extends AppCompatActivity {

    private TextView EtvLogin, ErecuperarPass;

    private EditText EentradaCorreo;
    private EditText EentradaContrasena;
    private Button EbtnIniciarsesion;

    private ProgressDialog EprogressDialog;

    private FirebaseAuth mAuthEmpleador;

    private FirebaseDatabase fDatabase;
    private DatabaseReference dBReferences;


    public void PantallaLoginEmpleador() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_login_empleador);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        EtvLogin = (TextView) findViewById(R.id.XMLEtvLogin);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoslab.bold.ttf");
        EtvLogin.setTypeface(face);

        EentradaCorreo = (EditText) findViewById(R.id.XMLEentrada_correoLogin);
        EentradaContrasena = (EditText) findViewById(R.id.XMLEetPassword);
        EbtnIniciarsesion = (Button) findViewById(R.id.XMLEbtnIniciarSesion);
        ErecuperarPass = (TextView) findViewById(R.id.XMLEtextolvidoContrasenal);

        fDatabase = FirebaseDatabase.getInstance();
        mAuthEmpleador = FirebaseAuth.getInstance();

        EprogressDialog = new ProgressDialog(this);

        EbtnIniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginEmpleador();

            }
        });

        ErecuperarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaLoginEmpleador.this, PantallaRecuperarContrasena.class);
                startActivity(intent);
                finish();
            }
        });


        if (Preferences.Obtener_estado_button(PantallaLoginEmpleador.this, Preferences.Preference_button)) {
            LoginEmpleador();

        }

    }

    public void LoginEmpleador() {
        String entrada_correo = EentradaCorreo.getText().toString().trim();
        String entrada_contrasena = EentradaContrasena.getText().toString().trim();

        if (TextUtils.isEmpty(entrada_correo)) {
            EentradaCorreo.setError("Campo vacío, por favor escriba el correo");
            return;
        }
        if (TextUtils.isEmpty(entrada_contrasena)) {
            EentradaContrasena.setError("Campo vacío, por favor escriba la contraseña");
            return;
        }

        EprogressDialog.setMessage("Iniciando sesión...");
        EprogressDialog.show();

        //creating a new user
        mAuthEmpleador.signInWithEmailAndPassword(entrada_correo, entrada_contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuthEmpleador.getCurrentUser();
                            if (user.isEmailVerified()) {
                                ///Probando shardpreferences
                                SharedPreferences preferences = getSharedPreferences("UserPrefEmpleador", Context.MODE_PRIVATE);
                                final SharedPreferences.Editor editor = preferences.edit();

                                Log.i("Probando", mAuthEmpleador.getUid());
                                dBReferences = fDatabase.getReference().child("Empleadores").child(mAuthEmpleador.getUid());

                                dBReferences.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String rncRE, nombreempresaRE, correoRE, telefonoRE, paginawebRE, direccionRE, imagenRE;
                                        Boolean verificacionRE;


                                        Log.i("Prueba", dataSnapshot.toString());
                                        rncRE = "";
                                        nombreempresaRE = "";
                                        correoRE = "";
                                        telefonoRE = "";
                                        paginawebRE = "";
                                        direccionRE = "";
                                        verificacionRE = false;
                                        imagenRE = "";

                                        rncRE = dataSnapshot.child("RNC").getValue(String.class);
                                        nombreempresaRE = dataSnapshot.child("Nombre").getValue(String.class);
                                        correoRE = dataSnapshot.child("Correo").getValue(String.class);
                                        telefonoRE = dataSnapshot.child("Telefono").getValue(String.class);
                                        paginawebRE = dataSnapshot.child("PaginaWeb").getValue(String.class);
                                        direccionRE = dataSnapshot.child("Direccion").getValue(String.class);
                                        verificacionRE = dataSnapshot.child("Verificacion").getValue(Boolean.class);
                                        imagenRE = dataSnapshot.child("ImagenEmpresa").getValue(String.class);

                                        editor.putString("RNC", rncRE);
                                        editor.putString("Nombre", nombreempresaRE);
                                        editor.putString("Correo", correoRE);
                                        editor.putString("Telefono", telefonoRE);
                                        editor.putString("PaginaWeb", paginawebRE);
                                        editor.putString("Direccion", direccionRE);
                                        //editor.putBoolean("Verificacion", verificacionRE);
                                        editor.putString("ImagenEmpresa", imagenRE);
                                        editor.commit();


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                Toast.makeText(PantallaLoginEmpleador.this, "Bienvenido: " + EentradaCorreo.getText(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(PantallaLoginEmpleador.this, PantallaPrincipalEmpleador.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(PantallaLoginEmpleador.this, "Correo electronico no verificado", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(PantallaLoginEmpleador.this, "El usuario ya existe", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(PantallaLoginEmpleador.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                            }
                        }
                        EprogressDialog.dismiss();
                    }
                });
    }
}
