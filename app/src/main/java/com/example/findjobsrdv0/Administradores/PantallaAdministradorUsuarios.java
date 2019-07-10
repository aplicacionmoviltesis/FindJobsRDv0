package com.example.findjobsrdv0.Administradores;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PantallaAdministradorUsuarios extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference userAdmin;

    private RecyclerView recycler_user_Admin;
    private RecyclerView.LayoutManager layoutManager;

    private  String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_administrador_usuarios);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        database = FirebaseDatabase.getInstance();
        userAdmin = database.getReference("AdministradoresApp");

        recycler_user_Admin = (RecyclerView) findViewById(R.id.recyclerViewUserAdmin);
        recycler_user_Admin.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_user_Admin.setLayoutManager(layoutManager);

        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        loadReferencias(idUser);
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void loadReferencias(String idUser) {

        final FirebaseRecyclerAdapter<Administrador, ViewHolderUsuarios> adapter = new FirebaseRecyclerAdapter<Administrador, ViewHolderUsuarios>(Administrador.class,
                R.layout.cardview_mostrar_usuarios, ViewHolderUsuarios.class, userAdmin ) {
            @Override
            protected void populateViewHolder(ViewHolderUsuarios ViewHolder, Administrador administrador, int i) {

                ViewHolder.tvNombreUser.setText( administrador.getsNombreAdmin() );
                ViewHolder.tvApellidoUser.setText( administrador.getsApellidoAdmin() );
                ViewHolder.tvEmailUser.setText( administrador.getsEmailAdmin() );
                ViewHolder.tvTelefonoUser.setText( administrador.getsTelefonAdmin() );

                final Administrador clickItem = administrador;

                ViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }
        };
        recycler_user_Admin.setAdapter(adapter);
    }
}
