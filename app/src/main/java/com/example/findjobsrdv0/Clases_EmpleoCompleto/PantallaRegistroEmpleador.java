package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findjobsrdv0.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.android.gms.common.SignInButton.SIZE_WIDE;

public class PantallaRegistroEmpleador extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private TextView tvRegistrar;

    private TextInputLayout CtilRNC,Ctilregistrarnombre,Ctilregistraremail,Ctilregistrartelefono,
            Ctilregistrarcontrasena,Ctilconfirmarcontrasena;

    private EditText registroRNC, registroNombreempresa, registroCorreo, registroTelefono, registroPass,registroPass2;

    private Button EbtnRegistrar, EbtnIniciarsesion;

    private Boolean Verificacion;
    private String PaginaWeb, Direccion;
    private SignInButton EsignInButton;
    private ProgressBar EprogressBar;

    private DatabaseReference DBReferenceEmpleador;
    private ProgressDialog progressDialogEmpleador;


    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuthEmpleador;

    /////iniciar con google

    private GoogleApiClient googleApiClientEmpleador;
    public static final int SIGN_IN_CODE = 777;
    private FirebaseAuth mAuthEmpleador;
    private FirebaseAuth.AuthStateListener firebaseAuthListenerEmpleador;



/////iniciar con google

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro_empleador);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tvRegistrar = (TextView) findViewById(R.id.XMLtvRegistrar);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoslab.bold.ttf");
        tvRegistrar.setTypeface(face);

        firebaseAuthEmpleador= FirebaseAuth.getInstance();
        DBReferenceEmpleador=FirebaseDatabase.getInstance().getReference();

        EsignInButton = (SignInButton) findViewById(R.id.EsignInButtonRegistro);
        EsignInButton.setSize(SIZE_WIDE);
        EsignInButton.setColorScheme(SignInButton.COLOR_DARK);

        EprogressBar = (ProgressBar) findViewById(R.id.EprogressBarRegistro);

        registroRNC = (EditText) findViewById(R.id.XMLeditRegistrarRNC);
        registroNombreempresa = (EditText) findViewById(R.id.XMLeditRegistrarnombre);
        registroCorreo = (EditText) findViewById(R.id.XMLeditregistraremail);
        registroTelefono = (EditText) findViewById(R.id.XMLeditregistrartelefono);
        registroPass = (EditText) findViewById(R.id.XMLeditregistrarcontrasena);
        registroPass2 = (EditText) findViewById(R.id.XMLeditconfirmarcontrasena);

        CtilRNC = (TextInputLayout) findViewById(R.id.XMLregistrarRNC);
        Ctilregistrarnombre = (TextInputLayout) findViewById(R.id.XMLregistrarnombre);
        Ctilregistraremail = (TextInputLayout) findViewById(R.id.XMLregistraremail);
        Ctilregistrartelefono = (TextInputLayout) findViewById(R.id.XMLregistrartelefono);
        Ctilregistrarcontrasena = (TextInputLayout) findViewById(R.id.XMLregistrarcontrasena);
        Ctilconfirmarcontrasena = (TextInputLayout) findViewById(R.id.XMLconfirmarcontrasena);

        EbtnRegistrar = (Button) findViewById(R.id.XMLbtnRegistrarBuscador);
        EbtnIniciarsesion = (Button) findViewById(R.id.XMLbtniniciarsesion);

//inicializamos el objeto firebaseAuth
        //firebaseAuth = FirebaseAuth.getInstance();

        //Referenciamos los views
        //TextEmail = (EditText) findViewById(R.id.xmlbeditregistraremail);
        //TextPassword = (EditText) findViewById(R.id.xmlbeditregistrarcontrasena);

        //btnRegistrar = (Button) findViewById(R.id.xmlbbtnRegistrarBuscador);
        progressDialogEmpleador = new ProgressDialog(this);

        //attaching listener to button
        EbtnRegistrar.setOnClickListener(this);

        ////////////iniciar con google

        EsignInButton = (SignInButton) findViewById(R.id.EsignInButtonRegistro);
        EsignInButton.setSize(SignInButton.SIZE_WIDE);
        EsignInButton.setColorScheme(SignInButton.COLOR_DARK);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClientEmpleador = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        EsignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClientEmpleador);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });


        mAuthEmpleador = FirebaseAuth.getInstance();
        firebaseAuthListenerEmpleador = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth mAuthEmpleador) {
                FirebaseUser user = mAuthEmpleador.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(PantallaRegistroEmpleador.this, PantallaPrincipalEmpleador.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        };

        EprogressBar = (ProgressBar) findViewById(R.id.EprogressBarRegistro);


        ////////////iniciar con google

        EbtnIniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), PantallaLoginEmpleador.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    private void registrarUsuarioEmpleador() {

        //Obtenemos el email y la contraseña desde las cajas de texto

        final String entrada_RNC = registroRNC.getText().toString().trim();
        final String entrada_Nombre = registroNombreempresa.getText().toString().trim();
        final String entrada_correo = registroCorreo.getText().toString().trim();
        final String entrada_telefono = registroTelefono.getText().toString().trim();
        final String entrada_contrasena = registroPass.getText().toString().trim();
        final String entrada_contrasena2 = registroPass2.getText().toString().trim();
        final String entrada_paginaweb = "";
        final String entrada_direccion = "";
        final boolean entrada_verificacion = false;
        final String entrada_imagenEmpleador="";

        //Verificamos que las cajas de texto no esten vacías

        if (TextUtils.isEmpty(entrada_RNC)) {
            registroRNC.setError("Campo vacío, por favor escriba el RNC");
            return;
        }
        if (TextUtils.isEmpty(entrada_Nombre)) {
            registroNombreempresa.setError("Campo vacío, por favor escriba el nombre");
            return;
        }
        if (TextUtils.isEmpty(entrada_correo)) {
            registroCorreo.setError("Campo vacío, por favor escriba el correo");
            return;
        }
        if (TextUtils.isEmpty(entrada_telefono)) {
            registroTelefono.setError("Campo vacío, por favor escriba el telefono");
            return;
        }
        if (TextUtils.isEmpty(entrada_contrasena)) {
            registroPass.setError("Campo vacío, por favor escriba la contraseña");
            return;
        }
        if (entrada_contrasena.length() < 8) {
            Toast.makeText(this, "La contraseña debe contener 8 o mas caracteres", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(entrada_contrasena2)) {
            registroPass2.setError("Campo vacío, por favor confirme la contraseña");
            return;
        }

        if (entrada_contrasena.equals(entrada_contrasena2)) {

            progressDialogEmpleador.setMessage("Realizando registro en linea...");
            progressDialogEmpleador.show();

            //creating a new user
            mAuthEmpleador.createUserWithEmailAndPassword(entrada_correo, entrada_contrasena)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuthEmpleador.getCurrentUser();
                                String Ukey = user.getUid();

                                DBReferenceEmpleador.child("Empleadores").child(Ukey).child("sRncEmpleador").setValue(entrada_RNC);
                                DBReferenceEmpleador.child("Empleadores").child(Ukey).child("sNombreEmpleador").setValue(entrada_Nombre);
                                DBReferenceEmpleador.child("Empleadores").child(Ukey).child("sCorreoEmpleador").setValue(entrada_correo);
                                DBReferenceEmpleador.child("Empleadores").child(Ukey).child("sTelefonoEmpleador").setValue(entrada_telefono);
                                DBReferenceEmpleador.child("Empleadores").child(Ukey).child("sPaginaWebEmpleador").setValue(entrada_paginaweb);
                                DBReferenceEmpleador.child("Empleadores").child(Ukey).child("sDireccionEmpleador").setValue(entrada_direccion);
                                //DBReferenceEmpleador.child("Empleadores").child(Ukey).child("Contraseña").setValue(entrada_contrasena);
                                DBReferenceEmpleador.child("Empleadores").child(Ukey).child("sVerificacionEmpleador").setValue(entrada_verificacion);
                                DBReferenceEmpleador.child("Empleadores").child(Ukey).child("sImagenEmpleador").setValue(entrada_imagenEmpleador);
                                DBReferenceEmpleador.child("Empleadores").child(Ukey).child("sIdEmpleador").setValue(Ukey);




                                //usas esa variable y usa .sendEmailVerification(); para mandar el correo de verificacion
                                user.sendEmailVerification();
                                mAuthEmpleador.signOut();


                                Toast.makeText(PantallaRegistroEmpleador.this, "Se ha registrado el usuario con el email: " + registroCorreo.getText()+" Espere el correo de verificacion", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(PantallaRegistroEmpleador.this, PantallaLoginEmpleador.class);
                                startActivity(intent);
                            } else {

                                Toast.makeText(PantallaRegistroEmpleador.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                            }
                            progressDialogEmpleador.dismiss();
                        }
                    });
        }else{
            Toast.makeText(this,"La confirmacion de contraseña no es correcta",Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onClick(View view) {
        //Invocamos al método:
        registrarUsuarioEmpleador();
    }

    private void goMainScreenLoginEmpleador() {
        Intent intent = new Intent(this, PantallaLoginEmpleador.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //////////////inicio de sesion con google

    @Override
    protected void onStart() {
        super.onStart();

        mAuthEmpleador.addAuthStateListener(firebaseAuthListenerEmpleador);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            firebaseAuthWithGoogle(result.getSignInAccount());
        } else {
            Toast.makeText(this, R.string.not_log_in, Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {

        EprogressBar.setVisibility(View.VISIBLE);
        EsignInButton.setVisibility(View.GONE);

        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        mAuthEmpleador.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                EprogressBar.setVisibility(View.GONE);
                EsignInButton.setVisibility(View.VISIBLE);

                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.not_firebase_auth, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListenerEmpleador != null) {
            mAuthEmpleador.removeAuthStateListener(firebaseAuthListenerEmpleador);
        }
    }

    /////////////inicio de sesion con google


}

