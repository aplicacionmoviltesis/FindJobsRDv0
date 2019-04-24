package com.example.findjobsrdv0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class PantallaLoginEmpleador extends AppCompatActivity implements View.OnClickListener{

    private TextView EtvLogin,ErecuperarPass;

    private EditText EentradaCorreo;
    private EditText EentradaContrasena;
    private Button EbtnIniciarsesion;

    private ProgressDialog EprogressDialog;


    private FirebaseAuth mAuthEmpleador;
    private FirebaseAuth.AuthStateListener AuthStateListener;

    private FirebaseUser userCorreo;
    private FirebaseDatabase fDatabase;
    private DatabaseReference dBReferences;
    private boolean Active;
    private CheckBox Sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_login_empleador);

        EtvLogin = (TextView) findViewById(R.id.XMLEtvLogin);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Chomsky.otf");
        EtvLogin.setTypeface(face);

        EentradaCorreo = (EditText) findViewById(R.id.XMLEentrada_correoLogin);
        EentradaContrasena = (EditText) findViewById(R.id.XMLEetPassword);
        EbtnIniciarsesion = (Button) findViewById(R.id.XMLEbtnIniciarSesion);
        ErecuperarPass = (TextView) findViewById(R.id.XMLEtextolvidoContrasenal);

        fDatabase = FirebaseDatabase.getInstance();
        mAuthEmpleador= FirebaseAuth.getInstance();

        EprogressDialog = new ProgressDialog(this);

        EbtnIniciarsesion.setOnClickListener(this);

        ErecuperarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaLoginEmpleador.this, PantallaRecuperarContrasena.class);
                startActivity(intent);
                finish();
            }
        });
///codigo nuevo, pa´verificar correo
       /* AuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuthEmpleador.getCurrentUser();
                if (user != null) {
                    if (!user.isEmailVerified()) {
                        Toast.makeText(PantallaLoginEmpleador.this, "Correo electronico no verificado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PantallaLoginEmpleador.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        gotoPantallaCentralEmpleador();

                    }
                }
            }


        };*/
///codigo nuevo, pa´verificar correo

    }

    private void gotoPantallaCentralEmpleador() {
        Intent intent = new Intent(this, PantallaPrincipalEmpleador.class);
        startActivity(intent);
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

                                Toast.makeText(PantallaLoginEmpleador.this, "Bienvenido: " + EentradaCorreo.getText(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(PantallaLoginEmpleador.this, PantallaPrincipalBuscador.class);
                                startActivity(intent);
                            }else {

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

    @Override
    public void onClick(View view) {
        //Invocamos al método:
        LoginEmpleador();
    }

}
