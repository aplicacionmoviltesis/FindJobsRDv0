package com.example.findjobsrdv0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class PantallaRegistroEmpleador extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener AuthStateListener;


    private DatabaseReference DBReference;


    private TextView tvRegistrar;
    private TextInputLayout CtilRNC,Ctilregistrarnombre,Ctilregistraremail,Ctilregistrartelefono,
            Ctilregistrarcontrasena,Ctilconfirmarcontrasena;
    private EditText registroRNC, registroNombreempresa, registroCorreo, registroTelefono, registroPass,registroPass2;
    private Button btnRegistrar, btnIniciarsesion;

    private Boolean Verificacion;
    private String PaginaWeb, Direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro_empleador);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tvRegistrar = (TextView) findViewById(R.id.XMLtvRegistrar);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/Chomsky.otf");
        tvRegistrar.setTypeface(face);


        registroRNC= (EditText) findViewById(R.id.XMLeditRegistrarRNC);
        registroNombreempresa= (EditText) findViewById(R.id.XMLeditRegistrarnombre);
        registroCorreo= (EditText) findViewById(R.id.XMLeditregistraremail);
        registroTelefono= (EditText) findViewById(R.id.XMLeditregistrartelefono);
        registroPass= (EditText) findViewById(R.id.XMLeditregistrarcontrasena);
        registroPass2= (EditText) findViewById(R.id.XMLeditconfirmarcontrasena);

        CtilRNC = (TextInputLayout) findViewById(R.id.XMLregistrarRNC);
        Ctilregistrarnombre = (TextInputLayout) findViewById(R.id.XMLregistrarnombre);
        Ctilregistraremail = (TextInputLayout) findViewById(R.id.XMLregistraremail);
        Ctilregistrartelefono = (TextInputLayout) findViewById(R.id.XMLregistrartelefono);
        Ctilregistrarcontrasena = (TextInputLayout) findViewById(R.id.XMLregistrarcontrasena);
        Ctilconfirmarcontrasena = (TextInputLayout) findViewById(R.id.XMLconfirmarcontrasena);

        btnRegistrar= (Button) findViewById(R.id.XMLbtnRegistrarBuscador);
        btnIniciarsesion= (Button) findViewById(R.id.XMLbtniniciarsesion);


        mAuth= FirebaseAuth.getInstance();
        DBReference=FirebaseDatabase.getInstance().getReference();

            AuthStateListener= new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user!=null){
                        Toast.makeText(PantallaRegistroEmpleador.this, "El usuario fue creado", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(PantallaRegistroEmpleador.this, "El usuario no fue creado", Toast.LENGTH_SHORT).show();

                    }

                }
            };

        }

        public void registrarUsuario(View view) {

            final String entrada_RNC = registroRNC.getText().toString();
            final String entrada_Nombre = registroNombreempresa.getText().toString();
            final String entrada_correo = registroCorreo.getText().toString();
            final String entrada_telefono = registroTelefono.getText().toString();
            final String entrada_contrasena = registroPass.getText().toString();
            final String entrada_contrasena2 = registroPass2.getText().toString();
            final String entrada_paginaweb = "";
            final String entrada_direccion = "";
            final boolean entrada_verificacion = false;



            boolean a = esRNC(entrada_RNC);
            boolean b = esNombreValido(entrada_Nombre);
            boolean c = esTelefonoValido(entrada_correo);
            boolean d = esCorreoValido(entrada_telefono);


            registroRNC.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            registroNombreempresa.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Ctilregistrarnombre.setError(null);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            registroCorreo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Ctilregistraremail.setError(null);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


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
            if (entrada_contrasena2.length() < 8) {
                registroPass2.setError("La contraseña deben ser 8 caracteres o mas");
                return;
            }

            if (TextUtils.isEmpty(entrada_contrasena2)) {
                registroPass2.setError("Campo vacío, por favor escriba la contraseña");
                return;
            }

            if (a && b && c && d) {

            if (entrada_contrasena.equals(entrada_contrasena2)) {

                mAuth.createUserWithEmailAndPassword(entrada_correo, entrada_contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(PantallaRegistroEmpleador.this, "Hubo un error, intente de nuevo, el correo ya ha sido utilizado", Toast.LENGTH_SHORT).show();


                        } else {

                            FirebaseUser user = mAuth.getCurrentUser();
                            String Ukey = user.getUid();

                            DBReference.child("Empleadores").child(Ukey).child("RNC").setValue(entrada_RNC);
                            DBReference.child("Empleadores").child(Ukey).child("Nombre").setValue(entrada_Nombre);
                            DBReference.child("Empleadores").child(Ukey).child("Correo").setValue(entrada_correo);
                            DBReference.child("Empleadores").child(Ukey).child("Telefono").setValue(entrada_telefono);
                            DBReference.child("Empleadores").child(Ukey).child("PaginaWeb").setValue(entrada_paginaweb);
                            DBReference.child("Empleadores").child(Ukey).child("Direccion").setValue(entrada_direccion);
                            DBReference.child("Empleadores").child(Ukey).child("Contraseña").setValue(entrada_contrasena);
                            DBReference.child("Empleadores").child(Ukey).child("Verificacion").setValue(entrada_verificacion);

                            //usas esa variable y usa .sendEmailVerification(); para mandar el correo de verificacion
                            user.sendEmailVerification();
                            mAuth.signOut();

                        }
                    }
                });

            } else {

                Toast.makeText(PantallaRegistroEmpleador.this, "La confirmacion no fue correcta", Toast.LENGTH_SHORT).show();

            }
        }else {

        Toast.makeText(PantallaRegistroEmpleador.this, "Sus datos no han sido validados en su totalidad", Toast.LENGTH_SHORT).show();

    }
        }



        protected void onStart() {
            super.onStart();
            mAuth.addAuthStateListener(AuthStateListener);
        }

        @Override
        protected void onStop() {
            super.onStop();
            if (AuthStateListener!=null){
                mAuth.removeAuthStateListener(AuthStateListener);
            }

        }

    private boolean esRNC(String rnc) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(rnc).matches() || rnc.length() > 30) {
            CtilRNC.setError("Nombre inválido");
            return false;
        } else {
            CtilRNC.setError(null);
        }

        return true;
    }

    private boolean esNombreValido(String nombreempresa) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombreempresa).matches() || nombreempresa.length() > 30) {
            Ctilregistrarnombre.setError("Nombre inválido");
            return false;
        } else {
            Ctilregistrarnombre.setError(null);
        }

        return true;
    }

    private boolean esTelefonoValido(String telefono) {
        if (android.util.Patterns.PHONE.matcher(telefono).matches()) {
            Ctilregistrartelefono.setError("Teléfono inválido");
            return false;
        } else {
            Ctilregistrartelefono.setError(null);
        }

        return true;
    }

    private boolean esCorreoValido(String correo) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Ctilregistraremail.setError("Correo electrónico inválido");
            return false;
        } else {
            Ctilregistraremail.setError(null);
        }

        return true;
    }

}

