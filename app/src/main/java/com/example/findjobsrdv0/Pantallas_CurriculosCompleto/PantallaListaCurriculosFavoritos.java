package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.AdapterCurriculo;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Curriculos;
import com.example.findjobsrdv0.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PantallaListaCurriculosFavoritos extends AppCompatActivity {

    FirebaseDatabase databaseCurriculoFav;
    DatabaseReference vistaCurriculoFavoritos, DBCurriculoFavoritos;

    String sEmpresaIdFav = "";

    private AdapterCurriculo adapterCurriculoFavorito;
    ArrayList<Curriculos> mDatasetCurriculo = new ArrayList<Curriculos>();

    private RecyclerView recyclerViewCurriculosFav;
    private RecyclerView.LayoutManager layoutManager;


    private String IdCurriculoFavorito, IdBuscadorCurriculoFav, ImagenCurriculoFav,
            NombreCurriculoFav, ApellidoCurriculoFav, CedulaCurriculoFav,
            EmailCurriculoFav, TelefonoCurriculoFav, CelularCurriculoFav,
            ProvinciaCurriculoFav, EstadoCivilCurriculoFav, DireccionCurriculoFav,
            OcupacionCurriculoFav, IdiomaCurriculoFav, GradoMayorCurriculoFav,
            EstadoActualCurriculoFav, SexoCurriculoFav, HabilidadesCurriculoFav, FechaCurriculoFav;

    private FirebaseAuth mAuthEmpresaFav;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_lista_curriculos_favoritos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        databaseCurriculoFav = FirebaseDatabase.getInstance();
        vistaCurriculoFavoritos = databaseCurriculoFav.getReference();
        DBCurriculoFavoritos = databaseCurriculoFav.getReference();

        recyclerViewCurriculosFav = (RecyclerView) findViewById(R.id.ListaCurriculosFavoritosR);

        recyclerViewCurriculosFav.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewCurriculosFav.setLayoutManager(layoutManager);

        adapterCurriculoFavorito = new AdapterCurriculo(PantallaListaCurriculosFavoritos.this, mDatasetCurriculo);
        recyclerViewCurriculosFav.setAdapter(adapterCurriculoFavorito);

        mAuthEmpresaFav = FirebaseAuth.getInstance();
        user = mAuthEmpresaFav.getCurrentUser();
        sEmpresaIdFav = user.getUid();

        //sEmpresaIdFav = "HmAtSRSnxdfxb0Z1kM2qoW1OvNo1";
        if (sEmpresaIdFav != null) {
            if (!sEmpresaIdFav.isEmpty()) {
                Log.d("dataAplicacionesempresa", String.valueOf(sEmpresaIdFav));

                TraerCurriculosFav(sEmpresaIdFav);
            }
        }
    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void TraerCurriculosFav(String sEmpresaId) {

        Query q = DBCurriculoFavoritos.child("Empleadores")
                .child(sEmpresaId)
                .child("likes");
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("dataAplicacionesempresa", String.valueOf(dataSnapshot));

                for (DataSnapshot CurriculosSnapshot : dataSnapshot.getChildren()) {

                    String IdCurriculoAplico = CurriculosSnapshot.child("IdCurriculoLike").getValue(String.class);
                    loadCurriculoFavorito(IdCurriculoAplico);
                    Log.d("dataidcurriculo", IdCurriculoAplico);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadCurriculoFavorito(final String sIdFavCurriculo) {
        vistaCurriculoFavoritos.child("Curriculos").orderByChild("sIdCurriculo").equalTo(sIdFavCurriculo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    Curriculos DatosCurriculoFav = datasnapshot.getValue(Curriculos.class);

                    ApellidoCurriculoFav = DatosCurriculoFav.getsApellidoC();
                    NombreCurriculoFav = DatosCurriculoFav.getsNombreC();
                    CedulaCurriculoFav = DatosCurriculoFav.getsCedulaC();
                    EmailCurriculoFav = DatosCurriculoFav.getsEmailC();
                    TelefonoCurriculoFav = DatosCurriculoFav.getsTelefonoC();
                    ProvinciaCurriculoFav = DatosCurriculoFav.getsProvinciaC();
                    EstadoCivilCurriculoFav = DatosCurriculoFav.getsEstadoCivilC();
                    DireccionCurriculoFav = DatosCurriculoFav.getsDireccionC();
                    OcupacionCurriculoFav = DatosCurriculoFav.getsOcupacionC();
                    IdiomaCurriculoFav = DatosCurriculoFav.getsIdiomaC();
                    GradoMayorCurriculoFav = DatosCurriculoFav.getsGradoMayorC();
                    CelularCurriculoFav = DatosCurriculoFav.getsCelularC();
                    EstadoActualCurriculoFav = DatosCurriculoFav.getsEstadoActualC();
                    SexoCurriculoFav = DatosCurriculoFav.getsSexoC();
                    HabilidadesCurriculoFav = DatosCurriculoFav.getsHabilidadesC();
                    FechaCurriculoFav = DatosCurriculoFav.getsFechaC();
                    IdCurriculoFavorito = DatosCurriculoFav.getsIdCurriculo();
                    IdBuscadorCurriculoFav = DatosCurriculoFav.getsIdBuscadorEmpleo();
                    ImagenCurriculoFav = DatosCurriculoFav.getsImagenC();


                    final Curriculos cvFav = new Curriculos(IdCurriculoFavorito, IdBuscadorCurriculoFav, ImagenCurriculoFav, NombreCurriculoFav, ApellidoCurriculoFav, CedulaCurriculoFav,
                            EmailCurriculoFav, TelefonoCurriculoFav, CelularCurriculoFav,
                            ProvinciaCurriculoFav, EstadoCivilCurriculoFav, DireccionCurriculoFav,
                            OcupacionCurriculoFav, IdiomaCurriculoFav, GradoMayorCurriculoFav,
                            EstadoActualCurriculoFav, SexoCurriculoFav, HabilidadesCurriculoFav,
                            FechaCurriculoFav, "aun no funciona");


                    PantallaListaCurriculosFavoritos.this.mDatasetCurriculo.add(cvFav);
                    adapterCurriculoFavorito.notifyDataSetChanged();

                    final Curriculos clickItem = cvFav;
                    adapterCurriculoFavorito.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(PantallaListaCurriculosFavoritos.this, "Spinner vacío, por favor seleccione una Provincia", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(PantallaListaCurriculosFavoritos.this, DetalleCurriculo.class);
                            intent.putExtra("detallecurrID", adapterCurriculoFavorito.mDatasetCurriculo.get(position).getsIdCurriculo());

                            //Log.d("klk",dataSnapshot.getRef().getKey());
                            startActivity(intent);
                        }
                    });
                }
                Log.d("CVPERSONASFAV::::", String.valueOf(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
