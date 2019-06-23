package com.example.findjobsrdv0.GeneralesApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.google.firebase.auth.FirebaseAuth;

public class PantallaRecuperarContrasena extends AppCompatActivity {

    private EditText PasswordEmail;
    private Button MandarCorreo;
    private TextView TituloRecuperarPass;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_recuperar_contrasena);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        TituloRecuperarPass = (TextView) findViewById(R.id.textrecuperarPass);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/robotoslab.bold.ttf");
        TituloRecuperarPass.setTypeface(face);

        PasswordEmail=(EditText) findViewById(R.id.entracorreoOlvidado);
        MandarCorreo=(Button) findViewById(R.id.boton_enviarcorreo);
        firebaseAuth= FirebaseAuth.getInstance();

        MandarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usercorreo = PasswordEmail.getText().toString().trim();

                if (TextUtils.isEmpty(usercorreo)) {
                    PasswordEmail.setError("Campo vacío, por favor escriba el correo");
                    return;
                }else {
                    firebaseAuth.sendPasswordResetEmail(usercorreo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                                Toast.makeText(PantallaRecuperarContrasena.this, "Reset enviada al correo", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(PantallaRecuperarContrasena.this, PantallaModoUsuario.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(PantallaRecuperarContrasena.this, "Error al Mandar el Reset password al correo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
