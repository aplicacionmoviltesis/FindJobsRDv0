package com.example.findjobsrdv0;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PantallaDetallesEmpleosAnadidos extends AppCompatActivity {

    TextView TvNombreEmpleoDEB,TvNombreEmpresaDEB,TvProvinciaDEB,TvDireccionDEB,TvEmailDEB,TvTelefonoDEB,
            TvPaginaWebDEB,TvJornadaDEB,TvMostrarHorarioDEB,TvTipoContratoDEB,TvCantidadVacantesDEB,
            TvSalarioDEB,TvAreaDEB,TvAnosExperienciaDEB,TvFormacionAcademicaDEB,TvIdiomasDEB,TvSexoRequeridoDEB,
            TvRangoEdadDEB,TvNotaDEB,TvEstadoEmpleoDEB,TvFechaPublicacionDEB;

    private ImageView MostImagenDEB;

    Button BtnAplicarEmpleoDE,BtnVerificacionEmpresaDE;

    String sEmpleoIdAnadidos = "";

    FirebaseDatabase databaseEmpleosAnadidos;
    DatabaseReference DBempleosBuscados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalles_empleos_anadidos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

//Firebase
        databaseEmpleosAnadidos = FirebaseDatabase.getInstance();
        DBempleosBuscados = databaseEmpleosAnadidos.getReference("empleos");


        MostImagenDEB = (ImageView) findViewById(R.id.xmlImagenEmpleoDEB);



        TvNombreEmpleoDEB = (TextView) findViewById(R.id.xmlTvNombreEmpleoDEB);
        TvNombreEmpresaDEB = (TextView) findViewById(R.id.xmlTvNombreEmpresaDEB);
        TvProvinciaDEB = (TextView) findViewById(R.id.xmlTvProvinciaDEB);
        TvDireccionDEB = (TextView) findViewById(R.id.xmlTvDireccionDEB);
        TvTelefonoDEB = (TextView) findViewById(R.id.xmlTvTelefonoDEB);
        TvEmailDEB = (TextView) findViewById(R.id.xmlTvEmailDEB);
        TvPaginaWebDEB = (TextView) findViewById(R.id.xmlTvPaginaWebDEB);
        TvJornadaDEB = (TextView) findViewById(R.id.xmlTvJornadaDEB);
        TvMostrarHorarioDEB = (TextView) findViewById(R.id.xmlTvMostrarHorarioDEB);
        TvTipoContratoDEB = (TextView) findViewById(R.id.xmlTvTipoContratoDEB);
        TvCantidadVacantesDEB = (TextView) findViewById(R.id.xmlTvCantidadVacantesDEB);
        TvSalarioDEB = (TextView) findViewById(R.id.xmlTvSalarioDEB);
        TvAreaDEB = (TextView) findViewById(R.id.xmlTvAreaDEB);
        TvAnosExperienciaDEB = (TextView) findViewById(R.id.xmlTvAnosExperienciaDEB);
        TvFormacionAcademicaDEB = (TextView) findViewById(R.id.xmlTvFormacionAcademicaDEB);
        TvIdiomasDEB = (TextView) findViewById(R.id.xmlTvIdiomasDEB);
        TvSexoRequeridoDEB = (TextView) findViewById(R.id.xmlTvSexoRequeridoDEB);
        TvRangoEdadDEB = (TextView) findViewById(R.id.xmlTvRangoEdadDEB);
        TvNotaDEB = (TextView) findViewById(R.id.xmlTvNotaDEB);
        TvEstadoEmpleoDEB = (TextView) findViewById(R.id.xmlTvEstadoEmpleoDEB);
        TvFechaPublicacionDEB = (TextView) findViewById(R.id.xmlTvFechaPublicacionDEB);


        //goProvincia();




        if(getIntent() != null){
            sEmpleoIdAnadidos = getIntent().getStringExtra("sEmpleoIdAnadidos");
            if(!sEmpleoIdAnadidos.isEmpty()){

                goDetalleEmpleoAnadidos(sEmpleoIdAnadidos);
            }
        }




        //BtnAplicarEmpleoDE= (Button) findViewById(R.id.xmlBtnAplicarEmpleoDE);
        /*TvPaginaWebDE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goProvincia();
            }
        });*/


        //bundle= getIntent().getExtras();

        //TvNombreEmpleoDE.setText(bundle.getString("sNombreEmpleoE"));


        //MostImagen.setImageBitmap(bundle.getString("fotoempleo"));
        //TvNombreEmpresaDE.setText(bundle.getString("NomEmpresa"));







    }

    private void goDetalleEmpleoAnadidos(String sEmpleoIdE) {
        DBempleosBuscados.child(sEmpleoIdE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Empleos empleos= dataSnapshot.getValue(Empleos.class);
                //Log.d("holap", String.valueOf(empleos));

                Picasso.get().load(empleos.getsImagenEmpleoE()).into(MostImagenDEB);


                TvProvinciaDEB.setText(empleos.getsProvinciaE());
                TvDireccionDEB.setText(empleos.getsDireccionE());
                TvTelefonoDEB.setText(empleos.getsTelefonoE());
                TvPaginaWebDEB.setText(empleos.getsPaginaWebE());
                TvJornadaDEB.setText(empleos.getsJornadaE());
                TvMostrarHorarioDEB.setText(empleos.getsHorarioE());
                TvTipoContratoDEB.setText(empleos.getsTipoContratoE());
                TvCantidadVacantesDEB.setText(empleos.getsCantidadVacantesE());
                TvSalarioDEB.setText(empleos.getsSalarioE());
                TvAreaDEB.setText(empleos.getsAreaE());
                TvAnosExperienciaDEB.setText(empleos.getsAreaE());
                TvFormacionAcademicaDEB.setText(empleos.getsFormacionAcademica());
                TvIdiomasDEB.setText(empleos.getsMostrarIdioma());
                TvSexoRequeridoDEB.setText(empleos.getsSexoRequeridoE());
                TvRangoEdadDEB.setText(empleos.getsRangoE());
                TvNotaDEB.setText(empleos.getsOtrosDatosE());
                TvEstadoEmpleoDEB.setText(empleos.getsEstadoEmpleoE());
                TvFechaPublicacionDEB.setText(empleos.getsFechaPublicacionE());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
