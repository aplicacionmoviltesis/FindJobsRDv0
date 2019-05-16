package com.example.findjobsrdv0;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.Modelo.EmpleosViewHolder;
import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class PantallaListaEmpleosBuscados extends AppCompatActivity {

    private RecyclerView listaEmpleos;
    DatabaseReference DBlistaEmpleos;

    private List<Empleos> ListEmpleos;
    //private List<VarEmpleo> ListEmpleos;
    private List<Empleos> ListBusqueda = new ArrayList<>();

    private List<Empleos> ListBusquedafiltrada = new ArrayList<>();


    FirebaseDatabase databaseEmpleosBuscados;
    DatabaseReference DBempleosBuscados;
    private RecyclerView listaEmpleosBuscados;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Empleos, EmpleosViewHolder> adapterEmpleosBuscados;
    FirebaseRecyclerAdapter<Empleos, EmpleosViewHolder> adapterEmpleosFiltrados;

    Button botonbuscarprovincia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_lista_empleos_buscados);


        //        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        databaseEmpleosBuscados = FirebaseDatabase.getInstance();
        DBempleosBuscados = databaseEmpleosBuscados.getReference("empleos");
        DBempleosBuscados.keepSynced(true);


        listaEmpleosBuscados = (RecyclerView) findViewById(R.id.ListaEmpleosBuscadosR);
        listaEmpleosBuscados.setHasFixedSize(true);
        listaEmpleosBuscados.setLayoutManager(new LinearLayoutManager(this));

        botonbuscarprovincia = (Button) findViewById(R.id.botonbuscarprovincia);

        botonbuscarprovincia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    filtro();
            }
        });

        cargarEmpleosBuscados();

    }

    private void firebaseSearch(String TextSearch) {

        Query firebaseSearchQuery = DBempleosBuscados.orderByChild("sNombreEmpleoE").startAt(TextSearch).endAt(TextSearch + "\uf8ff");


        adapterEmpleosBuscados = new FirebaseRecyclerAdapter<Empleos, EmpleosViewHolder>
                (Empleos.class, R.layout.cardview_mostrar_empleo, EmpleosViewHolder.class, firebaseSearchQuery) {
            @Override
            protected void populateViewHolder(EmpleosViewHolder empleosViewHolder, Empleos empleos, int i) {

                empleosViewHolder.NombreEmpleoCardView.setText(empleos.getsNombreEmpleoE());
                empleosViewHolder.NombreEmpresaCardView.setText(empleos.getsNombreEmpresaE());

                Picasso.get().load(empleos.getsImagenEmpleoE()).into(empleosViewHolder.imagenEmpleoCardView);
                final Empleos clickItem = empleos;
                empleosViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent intent = new Intent(PantallaListaEmpleosBuscados.this, PantallaDetallesEmpleo.class);
                        intent.putExtra("sEmpleoIdBuscado", adapterEmpleosBuscados.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        listaEmpleosBuscados.setAdapter(adapterEmpleosBuscados);
    }

    private void filtro() {
        String campo= "sNombreEmpleoE";
        String Valor= "limpiador";

        Query firebaseSearchQuery = DBempleosBuscados.orderByChild(campo).equalTo(Valor);


        adapterEmpleosBuscados = new FirebaseRecyclerAdapter<Empleos, EmpleosViewHolder>
                (Empleos.class, R.layout.cardview_mostrar_empleo, EmpleosViewHolder.class, firebaseSearchQuery) {
            @Override
            protected void populateViewHolder(EmpleosViewHolder empleosViewHolder, Empleos empleos, int i) {

                empleosViewHolder.NombreEmpleoCardView.setText(empleos.getsNombreEmpleoE());
                empleosViewHolder.NombreEmpresaCardView.setText(empleos.getsNombreEmpresaE());

                Picasso.get().load(empleos.getsImagenEmpleoE()).into(empleosViewHolder.imagenEmpleoCardView);
                final Empleos clickItem = empleos;
                empleosViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent intent = new Intent(PantallaListaEmpleosBuscados.this, PantallaDetallesEmpleo.class);
                        intent.putExtra("sEmpleoIdBuscado", adapterEmpleosBuscados.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        listaEmpleosBuscados.setAdapter(adapterEmpleosBuscados);
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

    private void cargarEmpleosBuscados() {

        adapterEmpleosBuscados = new FirebaseRecyclerAdapter<Empleos, EmpleosViewHolder>
                (Empleos.class, R.layout.cardview_mostrar_empleo, EmpleosViewHolder.class, DBempleosBuscados) {
            @Override
            protected void populateViewHolder(EmpleosViewHolder empleosViewHolder, Empleos empleos, int i) {
                String prov = "hola";
                //String valorProvincia = empleos.getsNombreEmpleoE();
/*
                if(empleos.getsNombreEmpleoE().equals(prov))
                {
                    Log.d("perrobiralata", empleos.getsNombreEmpleoE());

                }
                else {
                    Log.d("cabron", empleos.getsNombreEmpleoE());

                }*/
                //Log.d("hola", valorProvincia);
                //Log.d("perro", valorProvincia);


                //ListBusqueda.add(empleos);

                //int maximo = ListBusqueda.size();

                // for (int contador = 0; contador < maximo; contador++) {
                //if (ListBusqueda.get(contador).getsProvinciaE().equals(prov)) {


                //ListBusquedafiltrada.add(ListBusqueda.get(contador));
                Log.d("hola", empleos.getsNombreEmpleoE());
                //valorProvincia = empleos.getsNombreEmpleoE();

                //Log.d("perro", valorProvincia);

                //}

                // }

                if (empleos.getsNombreEmpleoE().equals(prov)) {
                    empleosViewHolder.NombreEmpleoCardView.setText(empleos.getsNombreEmpleoE());
                    empleosViewHolder.NombreEmpresaCardView.setText(empleos.getsNombreEmpresaE());

                    Picasso.get().load(empleos.getsImagenEmpleoE()).into(empleosViewHolder.imagenEmpleoCardView);

                    Log.d("perrobiralata", empleos.getsNombreEmpleoE());

                } else {
                    Log.d("cabron", empleos.getsNombreEmpleoE());

                }


                //empleosViewHolder.NombreEmpleoCardView.setText(empleos.getsNombreEmpleoE());
                // empleosViewHolder.NombreEmpresaCardView.setText(empleos.getsNombreEmpresaE());

                ///Picasso.get().load(empleos.getsImagenEmpleoE()).into(empleosViewHolder.imagenEmpleoCardView);


                final Empleos clickItem = empleos;
                empleosViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(PantallaListaEmpleosAnadidos.this,""+clickItem.getsNombreEmpleoE(),Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(PantallaListaEmpleosBuscados.this, PantallaDetallesEmpleo.class);
                        intent.putExtra("sEmpleoIdBuscado", adapterEmpleosBuscados.getRef(position).getKey());
                        startActivity(intent);


                    }
                });
            }
        };


        listaEmpleosBuscados.setAdapter(adapterEmpleosBuscados);
        // Log.d("probando",adapterEmpleosBuscados.getItem(1));

    }
/*

    public void EmpleosFiltrados() {
        String area = "tecnico";
        String prov = "la vega";
        String ano = "3";

        LimpiarListaEmpleos();

        int maximo = ListBusqueda.size();
        for (int contador = 0; contador < maximo; contador++) {
            if (ListBusqueda.get(contador).getsProvinciaE().equals(prov)) {

                ListBusquedafiltrada.add(ListBusqueda.get(contador));
            }

        }
        adapterEmpleosFiltrados = new ListBusquedafiltrada) {
            @Override
            protected void populateViewHolder(EmpleosViewHolder empleosViewHolder, Empleos empleos, int i) {
                empleosViewHolder.NombreEmpleoCardView.setText(empleos.getsNombreEmpleoE());
                empleosViewHolder.NombreEmpresaCardView.setText(empleos.getsNombreEmpresaE());

                Picasso.get().load(empleos.getsImagenEmpleoE()).into(empleosViewHolder.imagenEmpleoCardView);

                ListBusqueda.add(empleos);

                final Empleos clickItem = empleos;
                empleosViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(PantallaListaEmpleosAnadidos.this,""+clickItem.getsNombreEmpleoE(),Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(PantallaListaEmpleosBuscados.this, PantallaDetallesEmpleo.class);
                        intent.putExtra("sEmpleoIdBuscado", adapterEmpleosFiltrados.getRef(position).getKey());
                        startActivity(intent);


                    }
                });
            }
        };

        listaEmpleosBuscados.setAdapter(adapterEmpleosFiltrados);*/
    // Log.d("probando",adapterEmpleosBuscados.getItem(1));
/*
        adapterEmpleosFiltrados = new FirebaseRecyclerAdapter<Empleos, EmpleosViewHolder>() {
            @Override
            protected void populateViewHolder(EmpleosViewHolder empleosViewHolder, Empleos empleos, int i) {

            }
        };
        listaEmpleosBuscados.setAdapter(adapterEmpleosBuscados);
*/

    //adapterEmpleosBuscados.getItem(1);
    // for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


    //}


    // }

    private void LimpiarListaEmpleos() {

    }

/*
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DBlistaEmpleos = FirebaseDatabase.getInstance().getReference().child("empleos");
        DBlistaEmpleos.keepSynced(true);

        listaEmpleos = findViewById(R.id.ListaEmpleosBuscadosR);
        listaEmpleos.setHasFixedSize(true);
        listaEmpleos.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Empleos, ListaViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Empleos, ListaViewHolder>
                (Empleos.class,R.layout.cardview_mostrar_empleo, ListaViewHolder.class,DBlistaEmpleos) {
            @Override
            protected void populateViewHolder(ListaViewHolder listaViewHolder, Empleos viewHolder, int i) {
                listaViewHolder.setTitle((String) viewHolder.getsNombreEmpleoE());
                listaViewHolder.setNombreEmpresaDE(viewHolder.getsNombreEmpresaE());
                listaViewHolder.setImagenAreaDE(getApplicationContext(),viewHolder.getsDireccionE());












            }
        };

        listaEmpleos.setAdapter(firebaseRecyclerAdapter);
    }


    public static class ListaViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public ListaViewHolder(View itemView){
            super(itemView);
            mView=itemView;

        }

        public void setTitle(String title){
            TextView post_title = (TextView)mView.findViewById(R.id.CardNombreempleo);
            post_title.setText(title);

        }

        public void setNombreEmpresaDE(String sNombreEmpresaDE){
            TextView post_sNombreEmpresaDE = (TextView)mView.findViewById(R.id.CardNombreempresa);
            post_sNombreEmpresaDE.setText(sNombreEmpresaDE);

        }

        public void setImagenAreaDE(Context ctx, String sImagenAreaDE){
            ImageView post_sImagenAreaDE = (ImageView)mView.findViewById(R.id.CardImageArea);
            Picasso.get().load(sImagenAreaDE).into(post_sImagenAreaDE);
        }*/

////////////////////////////////////////////////////////////////////////////*
   /* private void buscar(){
        String area= "tecnico";
        String prov= "la vega";
        String ano= "3";


        limpiarRC();
        //recyclerView.setAdapter(EmpleoBuscar);
        listaEmpleos.setAdapter(firebaseRecyclerAdapter);//el adapter original con los empleos


        String sIDEmpleo,sNombreEmpleoE, sNombreEmpresaE,sProvinciaE,sDireccionE, sTelefonoE,sPaginaWebE,sEmailE,sSalarioE,sOtrosDatosE,
                sHorarioE,sFechaPublicacionE, sMostrarIdioma,sAreaE,sSexoRequeridoE,sRangoE,sJornadaE,sCantidadVacantesE,
                sTipoContratoE,sEstadoEmpleoE,sPersonasAplicaronE,sImagenEmpleoE, sIdEmpleadorE;

        int espacio= ListBusqueda.size();
        Log.i("Prueba busqueda", ""+espacio);

        if(!area.equals("AREAS") || !ano.equals("EXPERIENCIA") || !prov.equals("PROVINCIAS")){
            Log.i("Prueba busqueda", "Entro");
            if(area.equals("AREAS") && ano.equals("EXPERIENCIA")){
                for(int cont=0;cont<espacio;cont++){
                    if(ListBusqueda.get(cont).getsProvinciaE().equals(prov)){
                        sIDEmpleo= ListBusqueda.get(cont).sIDEmpleo;
                        Log.i("Prueba busqueda", sIDEmpleo);
                        sNombreEmpresaE= ListBusqueda.get(cont).sNombreEmpresaE;
                        sImagenEmpleoE=ListBusqueda.get(cont).sImagenEmpleoE;
                        sNombreEmpleoE= ListBusqueda.get(cont).sNombreEmpleoE;
                        sProvinciaE= ListBusqueda.get(cont).sProvinciaE;
                        sAreaE= ListBusqueda.get(cont).sAreaE;
                        sTelefonoE= ListBusqueda.get(cont).sTelefonoE;
                        sEmailE= ListBusqueda.get(cont).sEmailE;
                        sDireccionE= ListBusqueda.get(cont).sDireccionE;
                        sPaginaWebE= ListBusqueda.get(cont).sPaginaWebE;
                        sOtrosDatosE= ListBusqueda.get(cont).sOtrosDatosE;
                        sJornadaE= ListBusqueda.get(cont).sJornadaE;
                        sFechaPublicacionE= ListBusqueda.get(cont).sFechaPublicacionE;
                        sEstadoEmpleoE= ListBusqueda.get(cont).sEstadoEmpleoE;
                        sHorarioE = "";
                        sTipoContratoE = "";
                        sHorarioE = "";
                        sMostrarIdioma = "";
                        sSexoRequeridoE = "";
                        sRangoE = "";
                        sPersonasAplicaronE = "";
                        sIdEmpleadorE = "";
                        sCantidadVacantesE = "";
                        sSalarioE = "";


                        ListBusqueda.add(new Empleos(sIDEmpleo,sNombreEmpleoE, sNombreEmpresaE,sProvinciaE,sDireccionE, sTelefonoE,sPaginaWebE,sEmailE,sSalarioE,sOtrosDatosE,
                                sHorarioE,sFechaPublicacionE, sMostrarIdioma,sAreaE,sSexoRequeridoE,sRangoE,sJornadaE,sCantidadVacantesE,
                                sTipoContratoE,sEstadoEmpleoE,sPersonasAplicaronE,sImagenEmpleoE, sIdEmpleadorE));
                        firebaseRecyclerAdapter.notifyDataSetChanged();

                    }
                }
            }
        }


        listaEmpleos.setAdapter(firebaseRecyclerAdapter);
    }

    public void limpiarRC() {
        ListEmpleos.clear();
        firebaseRecyclerAdapter.notifyDataSetChanged();
    }*/
///////////////////////////////////////////////////////////////////////////
}
