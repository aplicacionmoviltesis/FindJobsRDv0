package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaEmpleosAplicado;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaEmpresasAplicaronMiCurriculo;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaListaEmpleosBuscados;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaListaEmpleosFavoritos;
import com.example.findjobsrdv0.GeneralesApp.PantallaConfiguracion;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaCompararEmpleos;
import com.example.findjobsrdv0.GeneralesApp.PantallaModoUsuario;
import com.example.findjobsrdv0.GeneralesApp.PantallaNavegador;

import com.example.findjobsrdv0.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PantallaPrincipalBuscador extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {
    private ImageView photoImageView;
    private TextView nameTextView, tituloelegiropcion;
    private TextView emailTextView;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private GoogleApiClient googleApiClient;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private String buscadoresconectados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal_buscador);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buscadoresconectados = FirebaseAuth.getInstance().getCurrentUser().getUid();
/*
       View header= navigationView.getHeaderView(0);
       photoImageView = (ImageView)header.findViewById( R.id.fotoperfilbuscador );
       nameTextView = (TextView)header.findViewById( R.id.nombreperfilbuscador);
       emailTextView = (TextView)header.findViewById( R.id.correoperfilbuscador );

       TextView TextViewEmpleador = (TextView) header.findViewById(R.id.nombreperfilbuscador);


*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        LinearLayout IrRegistrarCurriculo = (LinearLayout) findViewById(R.id.lyRegistrarCurriculo);
        IrRegistrarCurriculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), cPantallaRegistrarCurriculo.class);
                intent.putExtra("BuscadorConectado", buscadoresconectados);
                startActivityForResult(intent, 0);

            }
        });

        LinearLayout IrVistaCurriculo = (LinearLayout) findViewById(R.id.lyBuscarEmpleoBuscador);
        IrVistaCurriculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PantallaListaEmpleosBuscados.class);
                startActivityForResult(intent, 0);
            }
        });

        LinearLayout IrEmpresaInteresadas = (LinearLayout) findViewById(R.id.lyEmpresasInteresadas);
        IrEmpresaInteresadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PantallaEmpresasAplicaronMiCurriculo.class);
                startActivityForResult(intent, 0);
            }
        });

        tituloelegiropcion = (TextView) findViewById(R.id.tvelegiropcionBuscador);
        Typeface face = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts_robotos));
        tituloelegiropcion.setTypeface(face);

        mAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences preferences = this.getSharedPreferences("UserPref", Context.MODE_PRIVATE);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        photoImageView = (ImageView) header.findViewById(R.id.fotoperfilbuscador);
        nameTextView = (TextView) header.findViewById(R.id.nombreperfilbuscador);
        emailTextView = (TextView) header.findViewById(R.id.correoperfilbuscador);

        String Nombre = preferences.getString("Nombre", "Nombre") + " " + preferences.getString("Apellido", "Apellido");

        nameTextView.setText(Nombre);

        photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irPerfilBuscador(buscadoresconectados);
            }
        });

        nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irPerfilBuscador(buscadoresconectados);

            }
        });

        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irPerfilBuscador(buscadoresconectados);

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    setUserData(user);
                } else {
                    goRegInScreen();
                }
            }
        };
    }

    private void irPerfilBuscador(String buscadorconectado) {
        Intent intent = new Intent(this, cPantallaRegistrarCurriculo.class);
        intent.putExtra("BuscadorConectado",buscadorconectado);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setUserData(FirebaseUser user) {
        nameTextView.setText(user.getDisplayName());
        emailTextView.setText(user.getEmail());
        Glide.with(this).load(user.getPhotoUrl()).into(photoImageView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    private void goRegInScreen() {
        Intent intent = new Intent(this, PantallaModoUsuario.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut(View view) {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goRegInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.not_close_session, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void revoke(View view) {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goRegInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.not_revoke, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pantalla_principal_buscador, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, PantallaConfiguracion.class);
            startActivityForResult(intent, 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.empleosfavoritos) {
            Intent intent = new Intent(this, PantallaListaEmpleosFavoritos.class);
            startActivity(intent);

        } else if (id == R.id.empleosAplicadoBuscador) {
            Intent intent = new Intent(this, PantallaEmpleosAplicado.class);
            startActivity(intent);

        } else if (id == R.id.navegadorBuscador) {
            Intent intent = new Intent(this, PantallaNavegador.class);
            startActivity(intent);

        } else if (id == R.id.compararBuscador) {
            Intent intent = new Intent(this, PantallaCompararEmpleos.class);
            startActivity(intent);

        } else if (id == R.id.ConfiguracionBuscador) {
            Intent intent = new Intent(this, PantallaConfiguracion.class);
            startActivityForResult(intent, 0);

        } else if (id == R.id.compartirBuscador) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Si no tienes empleo y quieres encontrar alguno, Descarga ---> https://play.google.com/store/apps/details?id=com.FindJobsRD");
            startActivity(Intent.createChooser(intent, "Share with"));

        } else if (id == R.id.cerrarsesionBuscador) {

            firebaseAuth.signOut();

            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess()) {
                        Toast.makeText(getApplicationContext(), "La sesión se cerro con exito", Toast.LENGTH_SHORT).show();

                        goRegInScreen();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.not_close_session, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
