package com.example.findjobsrdv0.ActualizarCurriculo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.Vista_Curriculo_Principal.Modelo.VistaCurriculomodel;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.Vista_Curriculo_Principal.ViewHolder.VistaCurriculoViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class VistaCurriculoActualizar extends AppCompatActivity {

    RecyclerView recycler_curriculo;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference vistaCurriculo;



    // FirebaseAuth mAuth;

    FirebaseRecyclerAdapter<VistaCurriculomodel, VistaCurriculoViewHolder> adapter;

    String Ukey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_vista_curriculo_actualizar );

        database = FirebaseDatabase.getInstance();
        vistaCurriculo = database.getReference("Curriculos");

        recycler_curriculo = (RecyclerView) findViewById(R.id.recyclerViewVistaCurriculo);
        recycler_curriculo.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_curriculo.setLayoutManager(layoutManager);

        Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();


        loadCurriculo(Ukey);
    }

    private void loadCurriculo(String Ukey) {
        adapter = new FirebaseRecyclerAdapter<VistaCurriculomodel, VistaCurriculoViewHolder>(VistaCurriculomodel.class, R.layout.cardview_vista_curriculo, VistaCurriculoViewHolder.class,
                vistaCurriculo.orderByChild("cIdBuscador").equalTo(Ukey)) {
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
                        Intent CurriculoActualizar = new Intent( VistaCurriculoActualizar.this, ActualizarCurriculo.class);
                        CurriculoActualizar.putExtra("curriculoactualizarID", adapter.getRef(position).getKey());
                        startActivity(CurriculoActualizar);

                        //  Log.d("klk id",adapter.getRef( position ).getKey());

                        // Toast.makeText( PantalaVistaCurriculo.this, ""+clickItem.getNombre(), Toast.LENGTH_SHORT ).show();
                    }
                });
            }
        };
        recycler_curriculo.setAdapter(adapter);
    }
}
