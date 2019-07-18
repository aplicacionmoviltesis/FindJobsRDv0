package com.example.findjobsrdv0.Administradores;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.findjobsrdv0.Clases_EmpleoCompleto.Empleadores;
import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Curriculos;
import com.example.findjobsrdv0.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PantallaAdministradorUsuarios extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference usersAdmin, usersEmpleadores, usersBuscadores;

    private RecyclerView recyclerViewAdmin;
    private RecyclerView.LayoutManager layoutManager;

    private String idUser;

    private Spinner spinTipoUsuario;
    private Button btnBuscarUsers;
    private String sTipodeUsuario;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_administrador_usuarios);

        //idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

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
                Log.d("ValorSpin",sTipodeUsuario);

                if (sTipodeUsuario.equals("Empleadores")) {
                    actionBar.setTitle("Empleadores");
                    loadEmpleadores();
                }
                if (sTipodeUsuario.equals("Buscadores Empleo")){
                    actionBar.setTitle("Buscadores Empleo");
                    loadBuscadores();
                }
                if (sTipodeUsuario.equals("Administradores")){
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
        final FirebaseRecyclerAdapter<Administrador, ViewHolderUsuarios> adapterUsersAdmin = new FirebaseRecyclerAdapter<Administrador, ViewHolderUsuarios>(Administrador.class,
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

                    }
                });
            }
        };
        recyclerViewAdmin.setAdapter(adapterUsersAdmin);
    }

    private void loadEmpleadores() {
        final FirebaseRecyclerAdapter<Empleadores, ViewHolderUsuarios> adapterEmpleadores = new FirebaseRecyclerAdapter<Empleadores, ViewHolderUsuarios>(Empleadores.class,
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
}
