package com.example.findjobsrdv0.Administradores;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.findjobsrdv0.Adaptadores_Administrador.Administrador;
import com.example.findjobsrdv0.Adaptadores_Administrador.ViewHolderUsuarios;
import com.example.findjobsrdv0.Adaptadores_Empleador.Empleadores;
import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.Curriculos;
import com.example.findjobsrdv0.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PantallaAdministradorUsuarios extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference usersAdmin, usersEmpleadores, usersBuscadores;

    private FirebaseRecyclerAdapter<Empleadores, ViewHolderUsuarios> adapterEmpleadores;
    private FirebaseRecyclerAdapter<Administrador, ViewHolderUsuarios> adapterUsersAdmin;
    private FirebaseRecyclerAdapter<Curriculos, ViewHolderUsuarios> adapterBuscadores;

    private RecyclerView recyclerViewAdmin;
    private RecyclerView.LayoutManager layoutManager;

    private String idUser;

    private Spinner spinTipoUsuario;
    private Button btnBuscarUsers;
    private String sTipodeUsuario;

    private ActionBar actionBar;

    private TextView tvIdUser, tvNombreUser, tvApellidoUser, tvCorreoUser, tvTelefonoUser;
    private Button btnAceptarUser;

    private TextView tvIdEmpleadorAdm, tvNombreEmpleadorAdm, tvPaginaWebEmpleadorAdm,
            tvCorreoEmpleadorAdm, tvTelefonoEmpleadorAdm, tvProvinciaEmpleadorAdm,
            tvDescripcionEmpleadorAdm, tvRncEmpleadorAdm, tvDireccionEmpleadorAdm;

    private Button btnHacerAdmin;

    private TextView tvTiApellido_PagWeb;

    private String sIdHAdmin, sNombreHAdmin, sApellidoHAdmin, sCorreoHAdmin, sTelefonoHAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_administrador_usuarios);

        //idUser = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubuscar, menu);
        MenuItem item = menu.findItem(R.id.menuBuscar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menuBuscar) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void firebaseSearch(String TextSearch) {

        String query = TextSearch.toLowerCase();
        Query firebaseSearchQuery = usersBuscadores.orderByChild(getResources().getString(R.string.Campo_sNombreC)).startAt(query).endAt(query + "\uf8ff");
        adapterBuscadores = new FirebaseRecyclerAdapter<Curriculos, ViewHolderUsuarios>(Curriculos.class,
                R.layout.cardview_mostrar_usuarios, ViewHolderUsuarios.class, firebaseSearchQuery) {
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
                        Log.d("curricu",String.valueOf(adapterBuscadores.getRef(position).getKey()));

                        VerInformacionesBuscadores(adapterBuscadores.getRef(position).getKey());
                    }
                });
            }
        };
        recyclerViewAdmin.setAdapter(adapterBuscadores);
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
                        VerInformacionesEmpleadores(adapterEmpleadores.getRef(position).getKey());
                    }
                });
            }
        };
        recyclerViewAdmin.setAdapter(adapterEmpleadores);
    }

    private void loadBuscadores() {
        adapterBuscadores = new FirebaseRecyclerAdapter<Curriculos, ViewHolderUsuarios>(Curriculos.class,
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
                        Log.d("curricu",String.valueOf(adapterBuscadores.getRef(position).getKey()));

                        VerInformacionesBuscadores(adapterBuscadores.getRef(position).getKey());
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
        Typeface face = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts_robotos));
        TvTiInfoAdmin.setTypeface(face);

        tvIdUser = (TextView) dialogView.findViewById(R.id.xmlTvIdUser);
        tvNombreUser = (TextView) dialogView.findViewById(R.id.xmlTvNombreUser);
        tvApellidoUser = (TextView) dialogView.findViewById(R.id.xmlTvApellidoUser);
        tvCorreoUser = (TextView) dialogView.findViewById(R.id.xmlTvCorreoUser);
        tvTelefonoUser = (TextView) dialogView.findViewById(R.id.xmlTvTelefonoUser);

        tvTiApellido_PagWeb = (TextView) dialogView.findViewById(R.id.xmlTiApellidoUser);

        btnAceptarUser = (Button) dialogView.findViewById(R.id.xmlbtnAceptarUser);

        usersAdmin.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

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

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    public void VerInformacionesEmpleadores(String idUser) {

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.datos_empleadores_admin, null);

        final TextView TvTiInfoAdmin = (TextView) dialogView.findViewById(R.id.xmlTiInformacionesUser);
        Typeface face = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts_robotos));
        TvTiInfoAdmin.setTypeface(face);

        tvIdEmpleadorAdm = (TextView) dialogView.findViewById(R.id.xmlTvIdEmpleador);
        tvNombreEmpleadorAdm = (TextView) dialogView.findViewById(R.id.xmlTvRncEmpleador);
        tvPaginaWebEmpleadorAdm = (TextView) dialogView.findViewById(R.id.xmlTvNombreEmpleador);
        tvCorreoEmpleadorAdm = (TextView) dialogView.findViewById(R.id.xmlTvCorreoEmpleador);
        tvTelefonoEmpleadorAdm = (TextView) dialogView.findViewById(R.id.xmlTvTelefonoEmpleador);
        tvProvinciaEmpleadorAdm = (TextView) dialogView.findViewById(R.id.xmlTvProvinciaEmpleador);
        tvDescripcionEmpleadorAdm = (TextView) dialogView.findViewById(R.id.xmlTvdescripcionEmpleador);
        tvRncEmpleadorAdm = (TextView) dialogView.findViewById(R.id.xmlTvRncEmpleador);
        tvDireccionEmpleadorAdm = (TextView) dialogView.findViewById(R.id.xmlTvDireccionEmpleador);

        btnHacerAdmin = (Button) dialogView.findViewById(R.id.xmlbtnHacerAdminEmpleaador);

        usersEmpleadores.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Empleadores empleadores = dataSnapshot.getValue(Empleadores.class);

                    tvIdEmpleadorAdm.setText(empleadores.getsIdEmpleador());
                    tvNombreEmpleadorAdm.setText(empleadores.getsNombreEmpleador());
                    tvPaginaWebEmpleadorAdm.setText(empleadores.getsPaginaWebEmpleador());
                    tvCorreoEmpleadorAdm.setText(empleadores.getsCorreoEmpleador());
                    tvTelefonoEmpleadorAdm.setText(empleadores.getsTelefonoEmpleador());
                    tvProvinciaEmpleadorAdm.setText(empleadores.getsProvinciaEmpleador());
                    tvDireccionEmpleadorAdm.setText(empleadores.getsDireccionEmpleador());
                    tvDescripcionEmpleadorAdm.setText(empleadores.getsDescripcionEmpleador());
                    tvRncEmpleadorAdm.setText(empleadores.getsRncEmpleador());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnHacerAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sIdHAdmin = tvIdEmpleadorAdm.getText().toString().trim();
                sNombreHAdmin = tvNombreEmpleadorAdm.getText().toString().trim();
                sApellidoHAdmin = tvDireccionEmpleadorAdm.getText().toString().trim();
                sCorreoHAdmin = tvCorreoEmpleadorAdm.getText().toString().trim();
                sTelefonoHAdmin = tvTelefonoEmpleadorAdm.getText().toString().trim();

                Administrador administrador = new Administrador(sIdHAdmin, sNombreHAdmin, sApellidoHAdmin, sCorreoHAdmin, sTelefonoHAdmin, "Activo");
                usersAdmin.child(sIdHAdmin).setValue(administrador);
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    public void VerInformacionesBuscadores(String idUser) {

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.datos_usuarios_admin, null);

        final TextView TvTiInfoAdmin = (TextView) dialogView.findViewById(R.id.xmlTiInformacionesUser);
        Typeface face = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fonts_robotos));
        TvTiInfoAdmin.setTypeface(face);

        dialogBuilder.setTitle("Informaciones Buscador");
        dialogBuilder.setMessage("ooooo");

        tvIdUser = (TextView) dialogView.findViewById(R.id.xmlTvIdUser);
        tvNombreUser = (TextView) dialogView.findViewById(R.id.xmlTvNombreUser);
        tvApellidoUser = (TextView) dialogView.findViewById(R.id.xmlTvApellidoUser);
        tvCorreoUser = (TextView) dialogView.findViewById(R.id.xmlTvCorreoUser);
        tvTelefonoUser = (TextView) dialogView.findViewById(R.id.xmlTvTelefonoUser);

        tvTiApellido_PagWeb = (TextView) dialogView.findViewById(R.id.xmlTiApellidoUser);

        btnAceptarUser = (Button) dialogView.findViewById(R.id.xmlbtnAceptarUser);

        usersBuscadores.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Curriculos curriculos = dataSnapshot.getValue(Curriculos.class);
                    Log.d("curri",String.valueOf(dataSnapshot));
                    Log.d("curric",String.valueOf(curriculos));

                    tvIdUser.setText(curriculos.getsIdCurriculo());
                    tvNombreUser.setText(curriculos.getsNombreC());
                    tvApellidoUser.setText(curriculos.getsApellidoC());
                    tvCorreoUser.setText(curriculos.getsEmailC());
                    tvTelefonoUser.setText(curriculos.getsTelefonoC());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnAceptarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sIdHAdmin = tvIdUser.getText().toString().trim();
                sNombreHAdmin = tvNombreUser.getText().toString().trim();
                sApellidoHAdmin = tvApellidoUser.getText().toString().trim();
                sCorreoHAdmin = tvCorreoUser.getText().toString().trim();
                sTelefonoHAdmin = tvTelefonoUser.getText().toString().trim();

                Administrador administrador = new Administrador(sIdHAdmin, sNombreHAdmin, sApellidoHAdmin, sCorreoHAdmin, sTelefonoHAdmin, "Activo");
                usersAdmin.child(sIdHAdmin).setValue(administrador);
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }
}
