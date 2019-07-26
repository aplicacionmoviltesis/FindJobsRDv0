package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
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
                                DBReference.child(getResources().getString(R.string.Ref_Curriculos)).child(Ukey).child(getResources().getString(R.string.Campo_sImagenC)).setValue("data:image/jpeg;base64,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");


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
                    Log.d( "rnc no existe", String.valueOf( dataSnapshot ) );

                    registrarUsuarioBuscador();

                } else {
                    BregistroCedula.setError( "Esta cedula ya a sido registrado" );
                    Log.d( "rnc si existe", String.valueOf( dataSnapshot ) );
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
