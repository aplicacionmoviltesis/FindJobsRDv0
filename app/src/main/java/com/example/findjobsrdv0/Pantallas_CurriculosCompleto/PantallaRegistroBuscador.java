package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.AreasCurriculos;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PantallaRegistroBuscador extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private DatabaseReference DBReference;
    private TextView tvBRegistrar;
    private TextInputLayout BCtilcedula,BCtilapellido, BCtilregistrarnombre, BCtilregistraremail, BCtilregistrartelefono,
            BCtilregistrarcontrasena, BCtilconfirmarcontrasena;

    private EditText BregistroCedula, BregistroApellido, BregistroNombre, BregistroCorreo, BregistroTelefono, BregistroPass, BregistroPass2;

    private Button BbtnRegistrar, BbtnIniciarsesion;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private GoogleApiClient googleApiClient;
    public static final int SIGN_IN_CODE = 777;
    private FirebaseAuth mAuthBuscador;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private ProgressBar BprogressBar;
    private SignInButton BsignInButton;

    private DatabaseReference DBCedula;
    private String entrada_bCedula;

    private DatabaseReference DBAreas;
    private String userActivo;
    private String IdAreas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro_buscador);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tvBRegistrar = (TextView) findViewById(R.id.xmlbtvRegistrar);
        Typeface face = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts_robotos));
        tvBRegistrar.setTypeface(face);

        DBAreas = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.Ref_AreasCurriculos));
        IdAreas = DBAreas.push().getKey();

        DBCedula = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.Ref_Curriculos));
//        BregistroCedula.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "12")});

        firebaseAuth = FirebaseAuth.getInstance();
        DBReference = FirebaseDatabase.getInstance().getReference();

        BregistroCedula = (EditText) findViewById( R.id.xmlbeditCedula );
        BregistroNombre = (EditText) findViewById(R.id.xmlbeditRegistrarnombre);
        BregistroApellido = (EditText) findViewById(R.id.xmlbeditRegistrarApellido);
        BregistroCorreo = (EditText) findViewById(R.id.xmlbeditregistraremail);
        BregistroTelefono = (EditText) findViewById(R.id.xmlbeditregistrartelefono);
        BregistroPass = (EditText) findViewById(R.id.xmlbeditregistrarcontrasena);
        BregistroPass2 = (EditText) findViewById(R.id.xmlbeditconfirmarcontrasena);

        BCtilcedula = (TextInputLayout) findViewById( R.id.xmlbCedula );
        BCtilapellido = (TextInputLayout) findViewById(R.id.xmlbregistrarApellido);
        BCtilregistrarnombre = (TextInputLayout) findViewById(R.id.xmlbregistrarnombre);
        BCtilregistraremail = (TextInputLayout) findViewById(R.id.xmlbregistraremail);
        BCtilregistrartelefono = (TextInputLayout) findViewById(R.id.xmlbregistrartelefono);
        BCtilregistrarcontrasena = (TextInputLayout) findViewById(R.id.xmlbregistrarcontrasena);
        BCtilconfirmarcontrasena = (TextInputLayout) findViewById(R.id.xmlbconfirmarcontrasena);

        BbtnRegistrar = (Button) findViewById(R.id.xmlbbtnRegistrarBuscador);
        BbtnIniciarsesion = (Button) findViewById(R.id.xmlbbtniniciarsesion);

        progressDialog = new ProgressDialog(this);

        BbtnRegistrar.setOnClickListener(this);

        BsignInButton = (SignInButton) findViewById(R.id.BsignInButtonRegistro);
        BsignInButton.setSize(SignInButton.SIZE_WIDE);
        BsignInButton.setColorScheme(SignInButton.COLOR_DARK);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        BsignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });


        mAuthBuscador = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth mAuthBuscador) {
                FirebaseUser user = mAuthBuscador.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(PantallaRegistroBuscador.this, PantallaPrincipalBuscador.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        };

        BprogressBar = (ProgressBar) findViewById(R.id.BprogressBarRegistro);

        BbtnIniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PantallaLoginBuscador.class);
                startActivityForResult(intent, 0);
            }
        });
    }


    private void registrarUsuarioBuscador() {

        entrada_bCedula = BregistroCedula.getText().toString().trim();
        final String entrada_bNombre = BregistroNombre.getText().toString().trim();
        final String entrada_bApellido = BregistroApellido.getText().toString().trim();
        final String bemail = BregistroCorreo.getText().toString().trim();
        final String entrada_btelefono = BregistroTelefono.getText().toString().trim();
        final String bpassword = BregistroPass.getText().toString().trim();
        final String entrada_bcontrasena2 = BregistroPass2.getText().toString().trim();

        if (TextUtils.isEmpty(entrada_bNombre)) {
            BregistroCedula.setError("Campo vacío, por favor escriba la cedula");
            return;
        }
        if (TextUtils.isEmpty(entrada_bNombre)) {
            BregistroNombre.setError("Campo vacío, por favor escriba el nombre");
            return;
        }
        if (TextUtils.isEmpty(entrada_bApellido)) {
            BregistroApellido.setError("Campo vacío, por favor escriba el apellido");
            return;
        }
        if (TextUtils.isEmpty(bemail)) {
            BregistroCorreo.setError("Campo vacío, por favor escriba el correo");
            return;
        }
        if (TextUtils.isEmpty(entrada_btelefono)) {
            BregistroTelefono.setError("Campo vacío, por favor escriba el telefono");
            return;
        }
        if (TextUtils.isEmpty(bpassword)) {
            BregistroPass.setError("Campo vacío, por favor escriba la contraseña");
            return;
        }
        if (bpassword.length() < 8) {
            Toast.makeText(this, "La contraseña debe contener 8 o mas caracteres", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(entrada_bcontrasena2)) {
            BregistroPass2.setError("Campo vacío, por favor confirme su contraseña");
            return;
        }
        if (bpassword.equals(entrada_bcontrasena2)) {

            progressDialog.setMessage("Realizando registro en linea...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(bemail, bpassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String Ukey = user.getUid();

                                userActivo = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                DBReference.child(getResources().getString(R.string.Ref_Curriculos)).child(Ukey).child(getResources().getString(R.string.Campo_sCedulaC)).setValue(entrada_bCedula);
                                DBReference.child(getResources().getString(R.string.Ref_Curriculos)).child(Ukey).child(getResources().getString(R.string.Campo_sNombreC)).setValue(entrada_bNombre);
                                DBReference.child(getResources().getString(R.string.Ref_Curriculos)).child(Ukey).child(getResources().getString(R.string.Campo_sApellidoC)).setValue(entrada_bApellido);
                                DBReference.child(getResources().getString(R.string.Ref_Curriculos)).child(Ukey).child(getResources().getString(R.string.Campo_sEmailC)).setValue(bemail);
                                DBReference.child(getResources().getString(R.string.Ref_Curriculos)).child(Ukey).child(getResources().getString(R.string.Campo_sTelefonoC)).setValue(entrada_btelefono);
//                                DBReference.child(getResources().getString(R.string.Ref_Curriculos)).child(Ukey).child(getResources().getString(R.string.Campo_sImagenC)).setValue("https://firebasestorage.googleapis.com/v0/b/findjobsrd.appspot.com/o/ImagenesPerfilesBuscadores%2F1559055977214.jpeg?alt=media&token=9c185811-dcdf-4f1a-91d0-55d77a1ca441");

                                registrarareas(IdAreas,userActivo);

                                DBReference.child(getResources().getString(R.string.Ref_Curriculos)).child( Ukey ).child(getResources().getString(R.string.Campo_sIdCurriculo)).setValue( Ukey );

                                user.sendEmailVerification();
                                firebaseAuth.signOut();

                                Toast.makeText(PantallaRegistroBuscador.this, "Se ha registrado el usuario con el email: " + BregistroCorreo.getText() + " Espere el correo de verificacion", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(PantallaRegistroBuscador.this, PantallaLoginBuscador.class);
                                startActivity(intent);
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(PantallaRegistroBuscador.this, "El usuario ya existe", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(PantallaRegistroBuscador.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                                }
                            }
                            progressDialog.dismiss();
                        }
                    });
        } else {
            Toast.makeText(this, "La confirmacion de contraseña no es correcta", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onClick(View view) {

        entrada_bCedula = BregistroCedula.getText().toString().trim();
        Log.d(" todo", entrada_bCedula);

        Query q = DBCedula.orderByChild(getResources().getString(R.string.Campo_sCedulaC)).equalTo( entrada_bCedula );
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    registrarUsuarioBuscador();

                } else {
                    BregistroCedula.setError( "Esta cedula ya a sido registrado" );
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

    }

    public void registrarareas(String IdAreas, String userActivo){
        AreasCurriculos areasCurriculos = new AreasCurriculos( IdAreas, userActivo,
                "", "", "" );
        DBAreas.child( IdAreas ).setValue( areasCurriculos );
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuthBuscador.addAuthStateListener(firebaseAuthListener);
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

        BprogressBar.setVisibility(View.VISIBLE);
        BsignInButton.setVisibility(View.GONE);

        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        mAuthBuscador.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                BprogressBar.setVisibility(View.GONE);
                BsignInButton.setVisibility(View.VISIBLE);

                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.not_firebase_auth, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            mAuthBuscador.removeAuthStateListener(firebaseAuthListener);
        }
    }
}
