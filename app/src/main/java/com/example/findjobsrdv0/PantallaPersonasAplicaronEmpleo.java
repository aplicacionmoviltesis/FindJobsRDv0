package com.example.findjobsrdv0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.Curriculos;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleCurriculo.DetalleCurriculo;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.Vista_Curriculo_Principal.Modelo.VistaCurriculomodel;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.Vista_Curriculo_Principal.ViewHolder.VistaCurriculoViewHolder;
import com.example.findjobsrdv0.adaptador.MyAdapter;
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

public class PantallaPersonasAplicaronEmpleo extends AppCompatActivity {

    RecyclerView recycler_curriculo;
//    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference vistaCurriculo,DBpersonasAplicaron;
    //String sPersonasAplicaron = "";
    String klkempleo = "-Lg4alOTsAzGLKms6tmu";
    ArrayList<FirebaseRecyclerAdapter<VistaCurriculomodel, VistaCurriculoViewHolder>> arreglo;

    //FirebaseRecyclerAdapter klk;
    FirebaseRecyclerAdapter klk;

    // FirebaseAuth mAuth;
    ArrayAdapter<String> adaptador;
    ArrayList<FirebaseRecyclerAdapter<VistaCurriculomodel, VistaCurriculoViewHolder>> listado = new ArrayList<>();

    FirebaseRecyclerAdapter<VistaCurriculomodel, VistaCurriculoViewHolder> adapter;

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Curriculos> mDataset = new ArrayList<Curriculos>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_personas_aplicaron_empleo);

        database = FirebaseDatabase.getInstance();
        vistaCurriculo = database.getReference();
        DBpersonasAplicaron = database.getReference("EmpleosConCandidatos");
//
//
//        recycler_curriculo = (RecyclerView) findViewById(R.id.ListaCurriculosAplicaronR);
//        recycler_curriculo.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recycler_curriculo.setLayoutManager(layoutManager);
        recyclerView = (RecyclerView) findViewById(R.id.ListaCurriculosAplicaronR);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
//        ArrayList<String> gfgf= new ArrayList<String>();
//        gfgf.add("dad");
//        String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};

//        mDataset.add("elmento 1");
//        mDataset.add("elmento 2asdfa");
        mAdapter = new MyAdapter(PantallaPersonasAplicaronEmpleo.this, mDataset);
        recyclerView.setAdapter(mAdapter);
//        setLis
//        (mAdapter.getCount() == 0)


        //loadCurriculo();
        TraerAplicaciones(this.klkempleo);
        //recycler_curriculo.setAdapter(adapter);

//        mDataset.add("ELEMENTO 3");


    }

    public void TraerAplicaciones(String sEmpleoIdE){

        Query q = DBpersonasAplicaron.orderByChild("sIdEmpleoAplico").equalTo(sEmpleoIdE);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> CurriculosAplico = new ArrayList<String>();
                Log.d("dataAplicaciones", String.valueOf(dataSnapshot));

                for (DataSnapshot CurriculosSnapshot: dataSnapshot.getChildren()) {

                    String IdCurriculoAplico = CurriculosSnapshot.child("sIdCurriculoAplico").getValue(String.class);
                    //areas.add(areaName);
                    loadCurriculo(IdCurriculoAplico);
                    //cargarCurri(IdCurriculoAplico);
                    Log.d("dataidcurriculo", IdCurriculoAplico);
                    //arreglo.add(adapter);
                    //sIdCurriculoAplico = areaName;
                    //klk = adapter;
                }
//                recycler_curriculo.setAdapter(klk);
                //recycler_curriculo.setAdapter(adaptador);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void loadCurriculo(String sPersonasAplicaron) {
        vistaCurriculo.child("Curriculos").orderByChild("cCodigoId").equalTo(sPersonasAplicaron).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){

                    Log.d("DATOS::::",datasnapshot.child("cedula").getValue(String.class));
                    Curriculos cv =  new Curriculos("ASDFASDFASD", "ASDFASDFASD","ASDFASDFASD",datasnapshot.child("nombre").getValue(String.class),"ASDFASDFASD",datasnapshot.child("cedula").getValue(String.class),
                            "ASDFASDFASD", "ASDFASDFASD","ASDFASDFASD","ASDFASDFASD","ASDFASDFASD","ASDFASDFASD",
                            "ASDFASDFASD", "ASDFASDFASD","ASDFASDFASD","ASDFASDFASD","ASDFASDFASD","ASDFASDFASD",
                            "DSADADAD"

                    );
                    PantallaPersonasAplicaronEmpleo.this.mDataset.add(cv);
                    mAdapter.notifyDataSetChanged();
                }

                Log.d("CVPERSONAS::::",String.valueOf(dataSnapshot));

//                this.m
//                PantallaPersonasAplicaronEmpleo.this.mDataset.add(String.valueOf(dataSnapshot));

//                Curriculos curriculos = dataSnapshot.getValue(Curriculos.class);
//                PantallaPersonasAplicaronEmpleo.this.mDataset.add(cv);
//                Log.d("NOMBRE PERSONA::::", model.getNombre());

//                ArrayAdapter<String> adaptador;
//                ArrayList<String> listado = new ArrayList<String>();
//
//                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
//                    Log.d("holapdddddd", String.valueOf(dataSnapshot));
//
//                    Curriculos curriculos = datasnapshot.getValue(Curriculos.class);
//
//                    String titulo = curriculos.getNombre();
//                    Log.d("adaptador",titulo);
//
////                    listado.add(titulo);
//                }
//
//                adaptador = new ArrayAdapter<String>(PantallaBuscarEmpleos.this,android.R.layout.simple_list_item_1,listado);
//                //lista.setAdapter(adaptador);
//                Log.d("adaptador",String.valueOf(adaptador));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        adapter = new FirebaseRecyclerAdapter<VistaCurriculomodel,
//                VistaCurriculoViewHolder>(VistaCurriculomodel.class,
//                R.layout.cardview_vista_curriculo, VistaCurriculoViewHolder.class,
//                vistaCurriculo.child("Curriculos").orderByChild("cCodigoId").equalTo(sPersonasAplicaron)) {
//            //for()
//            @Override
//            protected void populateViewHolder(VistaCurriculoViewHolder viewHolder, VistaCurriculomodel model, int position) {
//
//                PantallaPersonasAplicaronEmpleo.this.mDataset.add(model.getNombre());
//                Log.d("NOMBRE PERSONA::::", model.getNombre());
//                mAdapter.notifyDataSetChanged();
////                this.mDataset.add
//                Picasso.get().load(model.getImagen()).into(viewHolder.imagen);
//                viewHolder.txtNombre.setText(model.getNombre());
//                viewHolder.txtCedula.setText(model.getCedula());
//                viewHolder.txtDireccion.setText(model.getDireccion());
//                viewHolder.txtEstadoActual.setText(model.getEstadoactual());
//                viewHolder.txtProvincia.setText(model.getProvincia());
//                viewHolder.txtGradoMayor.setText(model.getGradomayor());
//
//                //    Log.d( "hola", String.valueOf( viewHolder ) );
//
//
//                listado.add(adapter);
//
//                final VistaCurriculomodel clickItem = model;
//                viewHolder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void onClick(View view, int position, boolean isLongClick) {
//                        Intent CurriculoDetalle = new Intent(PantallaPersonasAplicaronEmpleo.this, DetalleCurriculo.class);
//                        CurriculoDetalle.putExtra("detallecurrID", adapter.getRef(position).getKey());
//                        startActivity(CurriculoDetalle);
//
//                        //  Log.d("klk id",adapter.getRef( position ).getKey());
//
//                        // Toast.makeText( PantalaVistaCurriculo.this, ""+clickItem.getNombre(), Toast.LENGTH_SHORT ).show();
//                    }
//                });
//                //klk[position]= adapter;
//                adaptador = new ArrayAdapter<String>(PantallaPersonasAplicaronEmpleo.this,android.R.layout.simple_list_item_1,(ArrayList)listado);
//
//            }
//        };
        //ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>();
        //areasAdapter.add(adapter);
        //ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(PantallaPersonasAplicaronEmpleo.this, android.R.layout.simple_spinner_item, adapter );
        //ArrayList<Adapter> arreglo = new ArrayList<>();
        //arreglo.set(adapter);
        //arreglo.add(adapter);

        //recycler_curriculo.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
//        klk= adapter;
    }


    public void cargarCurri(String sEmpleoIdE){

        vistaCurriculo.child("Curriculos").orderByChild("cCodigoId").equalTo(sEmpleoIdE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                //final List<String> areas = new ArrayList<String>();
                Log.d("holap", String.valueOf(dataSnapshot));

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {

                    String areaName = areaSnapshot.child("nombre").getValue(String.class);
                    ///areas.add(areaName);
                    Log.d("holap", areaName);

                }

                /*ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(PantallaBuscarEmpleos.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
                String cri= areaSpinner.getSelectedItem().toString();
                Log.d("seleccionado", cri);*/

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ///////////////////////////////////////////////////
        //Query query = DBpersonasAplicaron.orderByChild("sIdEmpleoAplico").equalTo(sEmpleoIdE);
        //q.
        /*.child(sEmpleoIdE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> CurriculosAplico = new ArrayList<String>();
                Log.d("dataApdatos", String.valueOf(dataSnapshot));

                for (DataSnapshot CurriculosSnapshot: dataSnapshot.getChildren()) {

                    Curriculos curriculos = new Curriculos();
                    curriculos.setNombre(CurriculosSnapshot.child("nombre").getValue().toString());

                    //String klkIdCurriculoAplico = CurriculosSnapshot.child("nombre").getValue().toString();
                    //areas.add(areaName);
                    //Dareas.setsNombreArea(snapshot.child("Nombre_Area").getValue().toString());

                    //loadCurriculo(IdCurriculoAplico);
                    Log.d("datanombre", curriculos.getNombre());
                    //Log.d("datanombreotro", klkIdCurriculoAplico);

                    //sIdCurriculoAplico = areaName;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/



    }

  /* public void Probando(){

        for(int i=0; i<1000;i++){



        }
    }*/


}
