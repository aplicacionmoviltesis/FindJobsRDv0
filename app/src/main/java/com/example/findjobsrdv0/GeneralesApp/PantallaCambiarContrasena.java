package com.example.findjobsrdv0.GeneralesApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findjobsrdv0.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PantallaCambiarContrasena extends AppCompatActivity {

    private FirebaseUser user;
    private TextView TvTiCambiarPassword;
    private String email, sNuevaContrasena, sViejaContrasena;
    private EditText editEmail, editViejaContrasena, editNuevaContrasena;
    private Button btnUpdatePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_cambiar_contrasena);

        TvTiCambiarPassword = (TextView) findViewById(R.id.xmltvCambiarContraseña);
        Typeface face = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts_robotos));
        TvTiCambiarPassword.setTypeface(face);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Cambiar Contraseña");

        user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();

        editEmail = (EditText) findViewById(R.id.xmlEditEmailCambiarContrasena);
        editViejaContrasena = (EditText) findViewById(R.id.xmlEditViejaPassCambiarContrasena);
        editNuevaContrasena = (EditText) findViewById(R.id.xmlEditNuevaPassCambiarContrasena);

        btnUpdatePassword = (Button) findViewById(R.id.xmlBtnActualizarPass);

        editEmail.setText(email);

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePassword();
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void UpdatePassword() {

        sNuevaContrasena = editNuevaContrasena.getText().toString().trim();
        sViejaContrasena = editViejaContrasena.getText().toString().trim();
        View focusView;

        if (TextUtils.isEmpty(email)) {
            editEmail.setError("Campo vacío, por favor escriba el correo");
            return;
        }

        if (editEmail.getText().length() == 0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Correo Invalido");
            focusView = editEmail;
            focusView.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(sViejaContrasena)) {
            editViejaContrasena.setError("Campo vacío, Ingrese Contraseña Actual");
            return;
        }
        if (TextUtils.isEmpty(sNuevaContrasena)) {
            editNuevaContrasena.setError("Campo vacío, por favor escriba la Nueva Contraseña");
            return;
        }

        AuthCredential credential = EmailAuthProvider.getCredential(email, sViejaContrasena);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(sNuevaContrasena).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(PantallaCambiarContrasena.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(PantallaCambiarContrasena.this, "Password Successfully Modified", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(PantallaCambiarContrasena.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
