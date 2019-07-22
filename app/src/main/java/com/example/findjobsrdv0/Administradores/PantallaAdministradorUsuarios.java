package com.example.findjobsrdv0.Administradores;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.findjobsrdv0.Adaptadores_Administrador.Administrador;
import com.example.findjobsrdv0.Adaptadores_Administrador.ViewHolderUsuarios;
import com.example.findjobsrdv0.Adaptadores_Empleador.Empleadores;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaDetallePerfilEmpresa;
import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.Curriculos;
import com.example.findjobsrdv0.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PantallaAdministradorUsuarios extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference usersAdmin, usersEmpleadores, usersBuscadores;

    private FirebaseRecyclerAdapter<Empleadores, ViewHolderUsuarios> adapterEmpleadores;
    private FirebaseRecyclerAdapter<Administrador, ViewHolderUsuarios> adapterUsersAdmin;

    private RecyclerView recyclerViewAdmin;
    private RecyclerView.LayoutManager layoutManager;

    private String idUser;

    private Spinner spinTipoUsuario;
    private Button btnBuscarUsers;
    private String sTipodeUsuario;

    private ActionBar actionBar;

    private TextView tvIdUser, tvNombreUser, tvApellidoUser, tvCorreoUser, tvTelefonoUser;
    private Button btnAceptarUser;

    private String sIdHAdmin,sNombreHAdmin, sApellidoHAdmin,sCorreoHAdmin,sTelefonoHAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_administrador_usuarios);

        idUser = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(idUser);

        database = FirebaseDatabase.getInstance();
        usersAdmin = database.getReference(getResources().getString(R.string.Ref_AdministradoresApp));
        usersBuscadores = database.getReference(getResources().getString(R.string.Ref_Curriculos));
        usersEmpleadores = database.getReference(getResources().getString(R.string.Ref_Empleadores));

        layoutManager = new LinearLayoutManager(this);
        recyclerViewAdmin = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        recyclerViewAdmin.setHasFixedSize(true);
        recyclerViewAdmin.setLayoutManager(layoutManager);

        spinTipoUsuario = (Spinner) findViewById(R.id.xmlspinSeleccionarTipoDeUsuario);
        ArrayAdapter<CharSequence> adapterTipoUsuario = ArrayAdapter.createFromResource(this,
                R.array.TipoUsuarioAdmin, android.R.layout.simple_spinner_item);
        adapterTipoUsuario.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTipoUsuario.setAdapter(adapterTipoUsuario);

        btnBuscarUsers = (Button) findViewById(R.id.btnBuscarUsuarioAdmin);
        btnBuscarUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sTipodeUsuario = spinTipoUsuario.getSelectedItem().toString();
                Log.d("ValorSpin", sTipodeUsuario);

                if (sTipodeUsuario.equals("Empleadores")) {
                    actionBar.setTitle("Empleadores");
                    loadEmpleadores();
                }
                if (sTipodeUsuario.equals("Buscadores Empleo")) {
                    actionBar.setTitle("Buscadores Empleo");
                    loadBuscadores();
                }
                if (sTipodeUsuario.equals("Administradores")) {
                    actionBar.setTitle("Administradores");
                    loadAdministradores();
                }
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadAdministradores() {
        adapterUsersAdmin = new FirebaseRecyclerAdapter<Administrador, ViewHolderUsuarios>(Administrador.class,
                R.layout.cardview_mostrar_usuarios, ViewHolderUsuarios.class, usersAdmin) {
            @Override
            protected void populateViewHolder(ViewHolderUsuarios ViewHolder, Administrador administrador, int i) {

                ViewHolder.tvNombreUser.setText(administrador.getsNombreAdmin());
                ViewHolder.tvApellidoUser.setText(administrador.getsApellidoAdmin());
                ViewHolder.tvEmailUser.setText(administrador.getsEmailAdmin());
                ViewHolder.tvTelefonoUser.setText(administrador.getsTelefonAdmin());

                final Administrador clickItem = administrador;

                ViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        VerInformacionesUsuarios(adapterUsersAdmin.getRef(position).getKey());
                    }
                });
            }
        };
        recyclerViewAdmin.setAdapter(adapterUsersAdmin);
    }

    private void loadEmpleadores() {
        adapterEmpleadores = new FirebaseRecyclerAdapter<Empleadores, ViewHolderUsuarios>(Empleadores.class,
                R.layout.cardview_mostrar_usuarios, ViewHolderUsuarios.class, usersEmpleadores) {
            @Override
            protected void populateViewHolder(ViewHolderUsuarios ViewHolder, Empleadores adminEmpleadores, int i) {

                ViewHolder.tvNombreUser.setText(adminEmpleadores.getsNombreEmpleador());
                ViewHolder.tvApellidoUser.setText(adminEmpleadores.getsDireccionEmpleador());
                ViewHolder.tvEmailUser.setText(adminEmpleadores.getsCorreoEmpleador());
                ViewHolder.tvTelefonoUser.setText(adminEmpleadores.getsTelefonoEmpleador());

                final Empleadores clickItem = adminEmpleadores;

                ViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(PantallaAdministradorUsuarios.this, PantallaDetallePerfilEmpresa.class);
                        intent.putExtra("sEmpresaIdAplico", adapterEmpleadores.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerViewAdmin.setAdapter(adapterEmpleadores);
    }

    private void loadBuscadores() {
        final FirebaseRecyclerAdapter<Curriculos, ViewHolderUsuarios> adapterBuscadores = new FirebaseRecyclerAdapter<Curriculos, ViewHolderUsuarios>(Curriculos.class,
                R.layout.cardview_mostrar_usuarios, ViewHolderUsuarios.class, usersBuscadores) {
            @Override
            protected void populateViewHolder(ViewHolderUsuarios ViewHolder, Curriculos buscadoresEmpleo, int i) {

                ViewHolder.tvNombreUser.setText(buscadoresEmpleo.getsNombreC());
                ViewHolder.tvApellidoUser.setText(buscadoresEmpleo.getsApellidoC());
                ViewHolder.tvEmailUser.setText(buscadoresEmpleo.getsEmailC());
                ViewHolder.tvTelefonoUser.setText(buscadoresEmpleo.getsTelefonoC());

                final Curriculos clickItem = buscadoresEmpleo;

                ViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }
        };
        recyclerViewAdmin.setAdapter(adapterBuscadores);
    }


    public void VerInformacionesUsuarios(String idUser) {

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.datos_usuarios_admin, null);

        final TextView TvTiInfoAdmin = (TextView) dialogView.findViewById(R.id.xmlTiInformacionesUser);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoslab.bold.ttf");
        TvTiInfoAdmin.setTypeface(face);

        tvIdUser = (TextView) dialogView.findViewById(R.id.xmlTvIdUser);
        tvNombreUser = (TextView) dialogView.findViewById(R.id.xmlTvNombreUser);
        tvApellidoUser = (TextView) dialogView.findViewById(R.id.xmlTvApellidoUser);
        tvCorreoUser = (TextView) dialogView.findViewById(R.id.xmlTvCorreoUser);
        tvTelefonoUser = (TextView) dialogView.findViewById(R.id.xmlTvTelefonoUser);

        btnAceptarUser = (Button) dialogView.findViewById(R.id.xmlbtnAceptarUser);

        usersAdmin.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

//                    Log.d("datos",String.valueOf(snapshot));
//                    tvNombreUser.setText(snapshot.child("sApellidoAdmin").getValue(String.class));
                    Administrador administrador = dataSnapshot.getValue(Administrador.class);

                    tvIdUser.setText(administrador.getsIdAdmin());
                    tvNombreUser.setText(administrador.getsNombreAdmin());
                    tvApellidoUser.setText(administrador.getsApellidoAdmin());
                    tvCorreoUser.setText(administrador.getsEmailAdmin());
                    tvTelefonoUser.setText(administrador.getsTelefonAdmin());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnAceptarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();

                sIdHAdmin = tvIdUser.getText().toString().trim();
                sNombreHAdmin = tvNombreUser.getText().toString().trim();
                sApellidoHAdmin = tvIdUser.getText().toString().trim();
                sCorreoHAdmin = tvIdUser.getText().toString().trim();
                sTelefonoHAdmin = tvIdUser.getText().toString().trim();

                Administrador administrador = new Administrador(sIdHAdmin,sNombreHAdmin, sApellidoHAdmin,sCorreoHAdmin,sTelefonoHAdmin,"Activo");
                usersAdmin.child(sIdHAdmin).setValue(administrador);
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }

    public void HacerUsuarioAdministrador(){

        Administrador administrador = new Administrador(sIdHAdmin,sNombreHAdmin, sApellidoHAdmin,sCorreoHAdmin,sTelefonoHAdmin,"Activo");
        usersAdmin.child(sIdHAdmin).setValue(administrador);


    }

}
