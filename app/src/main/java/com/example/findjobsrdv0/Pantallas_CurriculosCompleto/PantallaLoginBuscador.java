package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

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
import android.widget.TextView;
import android.widget.Toast;


import com.example.findjobsrdv0.GeneralesApp.PantallaRecuperarContrasena;
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


public class PantallaLoginBuscador extends AppCompatActivity {

    private TextView BtvLogin, textRecuperar;

    private EditText entradaCorreo;
    private EditText entradaContrasena;
    private Button BbtniniciarsesionBuscador;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialogloginBuscador;
    private DatabaseReference dBReferencesBuscadores;
    private FirebaseDatabase fDatabaseBuscadores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_login_buscador);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        BtvLogin = (TextView) findViewById(R.id.xmlbtvLogin);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Chomsky.otf");
        BtvLogin.setTypeface(face);

        textRecuperar = findViewById(R.id.textolvidoContrasenal);
        entradaCorreo = findViewById(R.id.entrada_correoLogin);
        entradaContrasena = findViewById(R.id.etPassword);
        BbtniniciarsesionBuscador = findViewById(R.id.EbtnIniciarSesion);

        firebaseAuth = FirebaseAuth.getInstance();
        fDatabaseBuscadores = FirebaseDatabase.getInstance();


        progressDialogloginBuscador = new ProgressDialog(this);

        BbtniniciarsesionBuscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginBuscador();

            }
        });

        textRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaLoginBuscador.this, PantallaRecuperarContrasena.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public void LoginBuscador() {
        String entrada_correo = entradaCorreo.getText().toString().trim();
        String entrada_contrasena = entradaContrasena.getText().toString().trim();

        if (TextUtils.isEmpty(entrada_correo)) {
            entradaCorreo.setError("Campo vacío, por favor escriba el correo");
            return;
        }
        if (TextUtils.isEmpty(entrada_contrasena)) {
            entradaContrasena.setError("Campo vacío, por favor escriba la contraseña");
            return;
        }

        progressDialogloginBuscador.setMessage("Iniciando sesión...");
        progressDialogloginBuscador.show();

        //creating a new user
        firebaseAuth.signInWithEmailAndPassword(entrada_correo, entrada_contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user.isEmailVerified()) {

                                ///Probando shardpreferences
                                SharedPreferences preferences = getSharedPreferences("UserPrefBuscadores", Context.MODE_PRIVATE);
                                final SharedPreferences.Editor editor = preferences.edit();

                                Log.i("Probando", firebaseAuth.getUid());
                                dBReferencesBuscadores = fDatabaseBuscadores.getReference().child("BuscadoresEmpleos").child(firebaseAuth.getUid());

                                dBReferencesBuscadores.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String NombreBuscador, ApellidoBuscador, CorreoBuscador, TelefonoBuscador, ImagenBuscador;


                                        Log.i("Prueba", dataSnapshot.toString());
                                        NombreBuscador = "";
                                        ApellidoBuscador = "";
                                        CorreoBuscador = "";
                                        TelefonoBuscador = "";
                                        ImagenBuscador = "";

                                        NombreBuscador = dataSnapshot.child("Nombre").getValue(String.class);
                                        ApellidoBuscador = dataSnapshot.child("Apellido").getValue(String.class);
                                        CorreoBuscador = dataSnapshot.child("Correo").getValue(String.class);
                                        TelefonoBuscador = dataSnapshot.child("Telefono").getValue(String.class);
                                        ImagenBuscador = dataSnapshot.child("ImagenEmpresa").getValue(String.class);

                                        editor.putString("Nombre", NombreBuscador);
                                        editor.putString("Apellido", ApellidoBuscador);
                                        editor.putString("Correo", CorreoBuscador);
                                        editor.putString("Telefono", TelefonoBuscador);
                                        editor.putString("ImagenEmpresa", ImagenBuscador);
                                        editor.commit();

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                Toast.makeText(PantallaLoginBuscador.this, "Bienvenido: " + entradaCorreo.getText(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(PantallaLoginBuscador.this, PantallaPrincipalBuscador.class);
                                startActivity(intent);
                            } else {

                                Toast.makeText(PantallaLoginBuscador.this, "Correo electronico no verificado", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(PantallaLoginBuscador.this, "El usuario ya existe", Toast.LENGTH_LONG).show();

                            } else {

                                Toast.makeText(PantallaLoginBuscador.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialogloginBuscador.dismiss();
                    }
                });
    }
}
