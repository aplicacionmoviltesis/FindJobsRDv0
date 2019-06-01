package com.example.findjobsrdv0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
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

public class PantallaPersonasAplicaronEmpleo extends AppCompatActivity {

    RecyclerView recycler_curriculo;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference vistaCurriculo,DBpersonasAplicaron;
    //String sPersonasAplicaron = "";
    String klkempleo = "-Lg4alOTsAzGLKms6tmu";
    ArrayList<FirebaseRecyclerAdapter<VistaCurriculomodel, VistaCurriculoViewHolder>> arreglo;



    // FirebaseAuth mAuth;

    FirebaseRecyclerAdapter<VistaCurriculomodel, VistaCurriculoViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_personas_aplicaron_empleo);

        database = FirebaseDatabase.getInstance();
        vistaCurriculo = database.getReference();
        DBpersonasAplicaron = database.getReference("EmpleosConCandidatos");


        recycler_curriculo = (RecyclerView) findViewById(R.id.ListaCurriculosAplicaronR);
        recycler_curriculo.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_curriculo.setLayoutManager(layoutManager);


        //loadCurriculo();
        TraerAplicaciones(klkempleo);
        //recycler_curriculo.setAdapter(adapter);
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
                    //sIdCurriculoAplico = areaName;

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void loadCurriculo(String sPersonasAplicaron) {
        adapter = new FirebaseRecyclerAdapter<VistaCurriculomodel,
                VistaCurriculoViewHolder>(VistaCurriculomodel.class,
                R.layout.cardview_vista_curriculo, VistaCurriculoViewHolder.class,
                vistaCurriculo.child("Curriculos").orderByChild("cCodigoId").equalTo(sPersonasAplicaron)) {
            //for()
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
                        Intent CurriculoDetalle = new Intent(PantallaPersonasAplicaronEmpleo.this, DetalleCurriculo.class);
                        CurriculoDetalle.putExtra("detallecurrID", adapter.getRef(position).getKey());
                        startActivity(CurriculoDetalle);

                        //  Log.d("klk id",adapter.getRef( position ).getKey());

                        // Toast.makeText( PantalaVistaCurriculo.this, ""+clickItem.getNombre(), Toast.LENGTH_SHORT ).show();
                    }
                });
            }
        };

        //ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>();
        //areasAdapter.add(adapter);
        //ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(PantallaPersonasAplicaronEmpleo.this, android.R.layout.simple_spinner_item, adapter );
        //ArrayList<Adapter> arreglo = new ArrayList<>();
        //arreglo.set(adapter);
        //arreglo.add(adapter);

        recycler_curriculo.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
