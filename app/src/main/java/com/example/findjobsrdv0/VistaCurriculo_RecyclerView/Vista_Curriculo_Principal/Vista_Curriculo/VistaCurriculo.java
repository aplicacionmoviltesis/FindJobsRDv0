package com.example.findjobsrdv0.VistaCurriculo_RecyclerView.Vista_Curriculo_Principal.Vista_Curriculo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.findjobsrdv0.Empleos;
import com.example.findjobsrdv0.Modelo.EmpleosViewHolder;
import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.PantallaDetallesEmpleo;
import com.example.findjobsrdv0.PantallaListaEmpleosBuscados;
import com.example.findjobsrdv0.Provincias;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Registro_del_Curriculo.cPantallaRegistrarCurriculo;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleCurriculo.DetalleCurriculo;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.Vista_Curriculo_Principal.Modelo.VistaCurriculomodel;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.Vista_Curriculo_Principal.ViewHolder.VistaCurriculoViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VistaCurriculo extends AppCompatActivity {


    RecyclerView recycler_curriculo;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference vistaCurriculo;



    // FirebaseAuth mAuth;

    FirebaseRecyclerAdapter<VistaCurriculomodel, VistaCurriculoViewHolder> adapter;

    /////Spinner Provincia

    private Spinner spinProvinciaCurriculo;
    private DatabaseReference provinciasRefCurriculo;
    private List<Provincias> provincias;
    private boolean IsFirstTimeClick = true;
    private String sProvinciaCurriculoB;
    private Button btnfiltrarPorProvincia;

/////Spinner Provincia


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_curriculo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        database = FirebaseDatabase.getInstance();
        vistaCurriculo = database.getReference("Curriculos");

        /////Spinner Provincia

        provinciasRefCurriculo = FirebaseDatabase.getInstance().getReference();
        spinProvinciaCurriculo = (Spinner) findViewById(R.id.xmlspinBuscarPorProvinciaCurriculo);

        spinProvinciaCurriculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    sProvinciaCurriculoB = spinProvinciaCurriculo.getSelectedItem().toString();
                    Log.d("valorSpinProv", sProvinciaCurriculoB);
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        provinciasRefCurriculo.child("provincias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListProvinciasCurri = new ArrayList<String>();
                for (DataSnapshot provinciaSnapshot : dataSnapshot.getChildren()) {
                    String provinciaName = provinciaSnapshot.child("Nombre_Provincia").getValue(String.class);
                    ListProvinciasCurri.add(provinciaName);
                }

                ArrayAdapter<String> provinciasAdapterCurriculo = new ArrayAdapter<String>(VistaCurriculo.this, android.R.layout.simple_spinner_item, ListProvinciasCurri);
                provinciasAdapterCurriculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinProvinciaCurriculo.setAdapter(provinciasAdapterCurriculo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/////Spinner Provincia


        btnfiltrarPorProvincia = (Button) findViewById(R.id.botonbuscarprovCurriculo);

        btnfiltrarPorProvincia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtroProvincia(sProvinciaCurriculoB);
            }
        });

        // mAuth = FirebaseAuth.getInstance();
        recycler_curriculo = (RecyclerView) findViewById(R.id.recyclerViewVistaCurriculo);
        recycler_curriculo.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_curriculo.setLayoutManager(layoutManager);

        loadCurriculo();


    }

        private void loadCurriculo() {
        adapter = new FirebaseRecyclerAdapter<VistaCurriculomodel, VistaCurriculoViewHolder>(VistaCurriculomodel.class, R.layout.cardview_vista_curriculo, VistaCurriculoViewHolder.class, vistaCurriculo) {
            @Override
            protected void populateViewHolder(VistaCurriculoViewHolder viewHolder, VistaCurriculomodel model, int position) {

                Picasso.get().load(model.getImagen()).into(viewHolder.imagen);
                viewHolder.txtNombre.setText(model.getNombre());
                viewHolder.txtCedula.setText(model.getCedula());
                viewHolder.txtDireccion.setText(model.getDireccion());
                viewHolder.txtEstadoActual.setText(model.getEstadoactual());
                viewHolder.txtProvincia.setText(model.getProvincia());
                viewHolder.txtGradoMayor.setText(model.getGradomayor());

                //    Log.d( "hola", String.valueOf( viewHolder ) );


                final VistaCurriculomodel clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent CurriculoDetalle = new Intent(VistaCurriculo.this, DetalleCurriculo.class);
                        CurriculoDetalle.putExtra("detallecurrID", adapter.getRef(position).getKey());
                        startActivity(CurriculoDetalle);

                        //  Log.d("klk id",adapter.getRef( position ).getKey());

                        // Toast.makeText( PantalaVistaCurriculo.this, ""+clickItem.getNombre(), Toast.LENGTH_SHORT ).show();
                    }
                });
            }
        };
        recycler_curriculo.setAdapter(adapter);
    }

    private void filtroProvincia(String Valor) {
        String campo = "provincia";


        Query firebaseSearchQuery = vistaCurriculo.orderByChild(campo).equalTo(Valor);


        adapter = new FirebaseRecyclerAdapter<VistaCurriculomodel, VistaCurriculoViewHolder>(VistaCurriculomodel.class, R.layout.cardview_vista_curriculo, VistaCurriculoViewHolder.class, firebaseSearchQuery) {
            @Override
            protected void populateViewHolder(VistaCurriculoViewHolder viewHolder, VistaCurriculomodel model, int position) {

                viewHolder.txtNombre.setText(model.getNombre());
                viewHolder.txtCedula.setText(model.getCedula());
                viewHolder.txtDireccion.setText(model.getDireccion());
                viewHolder.txtEstadoActual.setText(model.getEstadoactual());
                viewHolder.txtProvincia.setText(model.getProvincia());
                viewHolder.txtGradoMayor.setText(model.getGradomayor());

                //    Log.d( "hola", String.valueOf( viewHolder ) );


                final VistaCurriculomodel clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent CurriculoDetalle = new Intent(VistaCurriculo.this, DetalleCurriculo.class);
                        CurriculoDetalle.putExtra("detallecurrID", adapter.getRef(position).getKey());
                        startActivity(CurriculoDetalle);

                        //  Log.d("klk id",adapter.getRef( position ).getKey());

                        // Toast.makeText( PantalaVistaCurriculo.this, ""+clickItem.getNombre(), Toast.LENGTH_SHORT ).show();
                    }
                });
            }
        };
        recycler_curriculo.setAdapter(adapter);
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

        Query firebaseSearchQuery = vistaCurriculo.orderByChild("nombre").startAt(query).endAt(query + "\uf8ff");


        adapter = new FirebaseRecyclerAdapter<VistaCurriculomodel, VistaCurriculoViewHolder>(VistaCurriculomodel.class, R.layout.cardview_vista_curriculo, VistaCurriculoViewHolder.class, firebaseSearchQuery) {
            @Override
            protected void populateViewHolder(VistaCurriculoViewHolder viewHolder, VistaCurriculomodel model, int position) {

                viewHolder.txtNombre.setText(model.getNombre());
                viewHolder.txtCedula.setText(model.getCedula());
                viewHolder.txtDireccion.setText(model.getDireccion());
                viewHolder.txtEstadoActual.setText(model.getEstadoactual());
                viewHolder.txtProvincia.setText(model.getProvincia());
                viewHolder.txtGradoMayor.setText(model.getGradomayor());

                //    Log.d( "hola", String.valueOf( viewHolder ) );


                final VistaCurriculomodel clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent CurriculoDetalle = new Intent(VistaCurriculo.this, DetalleCurriculo.class);
                        CurriculoDetalle.putExtra("detallecurrID", adapter.getRef(position).getKey());
                        startActivity(CurriculoDetalle);

                        //  Log.d("klk id",adapter.getRef( position ).getKey());

                        // Toast.makeText( PantalaVistaCurriculo.this, ""+clickItem.getNombre(), Toast.LENGTH_SHORT ).show();
                    }
                });
            }
        };
        recycler_curriculo.setAdapter(adapter);
    }
}
