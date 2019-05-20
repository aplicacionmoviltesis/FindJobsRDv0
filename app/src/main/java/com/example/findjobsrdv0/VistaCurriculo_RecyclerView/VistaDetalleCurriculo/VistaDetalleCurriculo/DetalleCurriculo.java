package com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleCurriculo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleExperienciaLaboral.DetalleExperienciaLaboral;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleFormacionAcademica.DetalleFormacionAcademica;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleOtrosCursos.DetalleOtrosEstudios;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleReferencias.DetalleReferencias;
import com.example.findjobsrdv0.VistaCurriculo_RecyclerView.Vista_Curriculo_Principal.Modelo.VistaCurriculomodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetalleCurriculo extends AppCompatActivity {

    TextView txtNombreCurr, txtApellidoCurr, txtCedulaCurr, txtEmailCurr, txtTelefonoCurr, txtCelularCurr, txtprovinciaCurr, txtEstadoCivil, txtDireccionCurr, txtOcupacion, txtIdioma,
            txtEstadoActualCur, txtGradoMayorCurr, txtHabilidades, txtFecha;

    FirebaseDatabase database;
    DatabaseReference detalelcurriculo;


    String detallecurrid = "";
    Button btnIrFormacionAcademica,btnIrReferencia,btnIrExperienciaLab, btnOtrosEstudios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detalle_curriculo );

        database = FirebaseDatabase.getInstance();
        detalelcurriculo = database.getReference( "Curriculos" );

        txtNombreCurr = (TextView) findViewById( R.id.xmlTvNombreDetalleCu );
        txtApellidoCurr = (TextView) findViewById( R.id.xmlTvApellidoDetalleCu );
        txtCedulaCurr = (TextView) findViewById( R.id.xmlTvCedulaDetalleCu );
        txtEmailCurr = (TextView) findViewById( R.id.xmlTvEmailDetalleCu );
        txtTelefonoCurr = (TextView) findViewById( R.id.xmlTvTelefonoDetalleCu );
        txtCelularCurr = (TextView) findViewById( R.id.xmlTvCelularDetalleCu );
        txtprovinciaCurr = (TextView) findViewById( R.id.xmlTvProvinciaDetalleCu );
        txtEstadoCivil = (TextView) findViewById( R.id.xmlTvEstadoCivilDetalleCu );
        txtDireccionCurr = (TextView) findViewById( R.id.xmlTvDireccionDetalleCu );
        txtOcupacion = (TextView) findViewById( R.id.xmlTvOcupacionDetalleCu );
        txtIdioma = (TextView) findViewById( R.id.xmlTvIdiomaDetalleCu );
        txtEstadoActualCur = (TextView) findViewById( R.id.xmlTvDisponibilidadDetalleCu );
        txtGradoMayorCurr = (TextView) findViewById( R.id.xmlTvMaestriaDetalleCu );
        txtHabilidades = (TextView) findViewById( R.id.xmlTvHabilidadesDetalleCu );
        txtFecha = (TextView) findViewById( R.id.xmlTvFechaNacimientoDetalleCu );


        if (getIntent() != null)
            detallecurrid = getIntent().getStringExtra( "detallecurrID" );

        if (!detallecurrid.isEmpty()) {
            goDetailCurriculo( detallecurrid );
        }


        btnIrFormacionAcademica = (Button) findViewById( R.id.xmlBtnFormacionAcademicaDetalleCu );
        btnIrFormacionAcademica.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( DetalleCurriculo.this, DetalleFormacionAcademica.class );
                intent.putExtra( "DetalleFormacionAcademicaID", detallecurrid );
                startActivity( intent );
            }
        } );

        btnIrReferencia = (Button) findViewById( R.id.xmlBtnReferenciasDetalleCu );
        btnIrReferencia.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( DetalleCurriculo.this, DetalleReferencias.class );
                intent.putExtra( "DetalleReferenciaID", detallecurrid );
                startActivity( intent );
            }
        } );

        btnIrExperienciaLab = (Button) findViewById( R.id.xmlBtnExperienciaLaboralDetalleCu );
        btnIrExperienciaLab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( DetalleCurriculo.this, DetalleExperienciaLaboral.class );
                intent.putExtra( "DetalleExperienciaLabID", detallecurrid );
                startActivity( intent );
            }
        } );

        btnOtrosEstudios = (Button) findViewById( R.id.xmlBtnOtrosEstudiosDetalleCu );
        btnOtrosEstudios.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( DetalleCurriculo.this, DetalleOtrosEstudios.class );
                intent.putExtra( "DetalleOtrosEstudiosID", detallecurrid );
                startActivity( intent );
            }
        } );


    }

    private void goDetailCurriculo(String detallecurrid) {
        detalelcurriculo.child( detallecurrid ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("hola", String.valueOf(dataSnapshot));
                VistaCurriculomodel vistaCurriculomodel = dataSnapshot.getValue( VistaCurriculomodel.class);

                txtNombreCurr.setText( vistaCurriculomodel.getNombre() );
                txtApellidoCurr.setText( vistaCurriculomodel.getApellido() );
                txtCedulaCurr.setText( vistaCurriculomodel.getCedula() );
                txtEmailCurr.setText( vistaCurriculomodel.getEmail() );
                txtTelefonoCurr.setText( vistaCurriculomodel.getTelefono() );
                txtCelularCurr.setText( vistaCurriculomodel.getCelular() );
                txtprovinciaCurr.setText( vistaCurriculomodel.getProvincia() );
                txtEstadoCivil.setText( vistaCurriculomodel.getEstadoCivil() );
                txtDireccionCurr.setText( vistaCurriculomodel.getDireccion() );
                txtOcupacion.setText( vistaCurriculomodel.getOcupacion() );
                txtEstadoActualCur.setText( vistaCurriculomodel.getEstadoactual() );
                txtGradoMayorCurr.setText( vistaCurriculomodel.getGradomayor() );
                txtHabilidades.setText( vistaCurriculomodel.getHabilidades() );
                txtFecha.setText( vistaCurriculomodel.getFecha() );



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
