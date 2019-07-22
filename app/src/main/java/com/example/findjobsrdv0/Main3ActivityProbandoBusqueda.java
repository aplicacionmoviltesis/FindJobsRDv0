package com.example.findjobsrdv0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.findjobsrdv0.Adaptadores_Empleador.EmpleosFav;
import com.example.findjobsrdv0.Adaptadores_Empleador.MultiAdapter;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaVistaComparacionEmpleos;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Main3ActivityProbandoBusqueda extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference DBempleos;
    private RecyclerView listaEmpleosAnadidos;
    private RecyclerView.LayoutManager layoutManager;

    //private FirebaseRecyclerAdapter<Empleos, EmpleosViewHolder> adapter;

    private String Ukey;
    ////////////////////////////////////////

    private ArrayList<EmpleosFav> empleosFavsArray = new ArrayList<>();
    private MultiAdapter adapter;
    AppCompatButton btnGetSelected;
    RecyclerView recyclerView;

    private String sIdEmpleoE1 ="-Lg4alOTsAzGLKms6tmu";
    private String sIdEmpleoE2 ="-LgdHslsVo394m0k53Il";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3_probando_busqueda);

//        database = FirebaseDatabase.getInstance();
//        DBempleos = database.getReference("empleos");
//        DBempleos.keepSynced(true);
//
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
//
//
//        listaEmpleosAnadidos = (RecyclerView) findViewById(R.id.ListaEmpleosBuscasteR);
//        listaEmpleosAnadidos.setHasFixedSize(true);
//        listaEmpleosAnadidos.setLayoutManager(new LinearLayoutManager(this));
//
//
//        //Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        Ukey = "HmAtSRSnxdfxb0Z1kM2qoW1OvNo1";
//        cargarEmpleos(Ukey);

        this.btnGetSelected = (AppCompatButton) findViewById(R.id.btnGetSelected);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerViewklk);

        getSupportActionBar().setTitle("Multiple Selection");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new MultiAdapter(this, empleosFavsArray);
        recyclerView.setAdapter(adapter);

        createList();

        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goVistaComparacionEmpleo(sIdEmpleoE1,sIdEmpleoE2);
                if (adapter.getSelected().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        stringBuilder.append(adapter.getSelected().get(i).getsNombreEmpleoE());
                        stringBuilder.append("\n");
                    }
                    showToast(stringBuilder.toString());
                } else {
                    showToast("No Selection");
                }
            }
        });


    }

    public void goVistaComparacionEmpleo(String sIdPrimerEmpleo, String sIdSegundoEmpleo) {

        Intent intent = new Intent(Main3ActivityProbandoBusqueda.this, PantallaVistaComparacionEmpleos.class);
        intent.putExtra("sIdEmpleoComparar1", sIdPrimerEmpleo);
        intent.putExtra("sIdEmpleoComparar2", sIdSegundoEmpleo);
        startActivity(intent);

    }

    private void createList() {
        empleosFavsArray = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            EmpleosFav empleosFav = new EmpleosFav();
            empleosFav.setsNombreEmpleoE("Employee " + (i + 1));
            empleosFavsArray.add(empleosFav);
        }
        adapter.setEmpleosFavs(empleosFavsArray);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
//
//    private void cargarEmpleos(String Ukey) {
//        adapter = new FirebaseRecyclerAdapter<Empleos, EmpleosViewHolder>
//                (Empleos.class, R.layout.cardview_mostrar_empleo, EmpleosViewHolder.class,
//                        DBempleos.orderByChild("sIdEmpleadorE").equalTo(Ukey)) {
//
//
//            @Override
//            protected void populateViewHolder(EmpleosViewHolder empleosViewHolder, Empleos empleos, int i) {
//                Log.d("dato:::", String.valueOf(empleos.getsProvinciaE()));
//
//                //if (!empleos.getsProvinciaE().equals("Distrito Nacional")) {
//
//                    empleosViewHolder.NombreEmpleoCardView.setText(empleos.getsNombreEmpleoE());
//                    empleosViewHolder.NombreEmpresaCardView.setText(empleos.getsNombreEmpresaE());
//                    empleosViewHolder.ProvinciaCardView.setText(empleos.getsProvinciaE());
//                    empleosViewHolder.AreaCardView.setText(empleos.getsAreaE());
//                    empleosViewHolder.EstadoCardView.setText(empleos.getsEstadoEmpleoE());
//                    empleosViewHolder.FechaPublicacionCardView.setText("Ultima Actualizacion: " + empleos.getsFechaPublicacionE());
//                    Picasso.get().load(empleos.getsImagenEmpleoE()).into(empleosViewHolder.imagenEmpleoCardView);
//
//                    final Empleos clickItem = empleos;
//                    empleosViewHolder.setItemClickListener(new ItemClickListener() {
//                        @Override
//                        public void onClick(View view, int position, boolean isLongClick) {
//
//                            Intent intent = new Intent(Main3ActivityProbandoBusqueda.this, PantallaActualizarEmpleo.class);
//                            intent.putExtra("sEmpleoIdAnadidos", adapter.getRef(position).getKey());
//                            startActivity(intent);
//                        }
//                    });
//                }
//            //}
//        };
//        listaEmpleosAnadidos.setAdapter(adapter);
//    }
}
