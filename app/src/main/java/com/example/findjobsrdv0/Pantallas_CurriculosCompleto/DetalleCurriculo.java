package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.findjobsrdv0.Clases_EmpleoCompleto.AplicarCurriculo;

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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetalleCurriculo extends AppCompatActivity {

    private TextView txtNombreCurr, txtApellidoCurr, txtCedulaCurr, txtEmailCurr, txtTelefonoCurr, txtCelularCurr, txtprovinciaCurr, txtEstadoCivil, txtDireccionCurr, txtOcupacion, txtIdioma,
            txtEstadoActualCur, txtGradoMayorCurr, txtHabilidades, txtFecha,TvYaAplicoCurriculo;

    private FirebaseDatabase database,prueba;
    private DatabaseReference detalelcurriculo, DbLikesFavCurri;
    private DatabaseReference AplicarInteresCurriculoDataBase;

    private String detallecurrid = "";
    private Button btnIrFormacionAcademica, btnIrReferencia, btnIrExperienciaLab, btnOtrosEstudios, btnAplicarCurriculo;

    private FirebaseAuth mAuthEmpresa_Empleador,mFirebaseAuth;
    private FirebaseUser user,userActivo;

    private String sIdAplicarCurriculo, sIdCurriculoAplico, sIdEmpresaAplico, sFechadeAplicacion;

    private ToggleButton btnfavoritoCurriculo;

    private String sIdPersonaMarco;

    private ImageView FotoCurriculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_curriculo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        TvYaAplicoCurriculo = (TextView) findViewById(R.id.xmlTvYaaplicasteCurriculo);
        TvYaAplicoCurriculo.setVisibility(View.INVISIBLE);

        prueba = FirebaseDatabase.getInstance();
        DbLikesFavCurri = prueba.getReference();
        DbLikesFavCurri.keepSynced( true );

        mFirebaseAuth = FirebaseAuth.getInstance();
        userActivo = mFirebaseAuth.getCurrentUser();
        sIdPersonaMarco = userActivo.getUid();


        database = FirebaseDatabase.getInstance();
        detalelcurriculo = database.getReference("Curriculos");
        AplicarInteresCurriculoDataBase = database.getReference("CurriculosConSolicitudes");

        FotoCurriculo = (ImageView) findViewById(R.id.xmlImagenPerfilCurriculo);

        txtNombreCurr = (TextView) findViewById(R.id.xmlTvNombreDetalleCu);
        txtApellidoCurr = (TextView) findViewById(R.id.xmlTvApellidoDetalleCu);
        txtCedulaCurr = (TextView) findViewById(R.id.xmlTvCedulaDetalleCu);
        txtEmailCurr = (TextView) findViewById(R.id.xmlTvEmailDetalleCu);
        txtTelefonoCurr = (TextView) findViewById(R.id.xmlTvTelefonoDetalleCu);
        txtCelularCurr = (TextView) findViewById(R.id.xmlTvCelularDetalleCu);
        txtprovinciaCurr = (TextView) findViewById(R.id.xmlTvProvinciaDetalleCu);
        txtEstadoCivil = (TextView) findViewById(R.id.xmlTvEstadoCivilDetalleCu);
        txtDireccionCurr = (TextView) findViewById(R.id.xmlTvDireccionDetalleCu);
        txtOcupacion = (TextView) findViewById(R.id.xmlTvOcupacionDetalleCu);
        txtIdioma = (TextView) findViewById(R.id.xmlTvIdiomaDetalleCu);
        txtEstadoActualCur = (TextView) findViewById(R.id.xmlTvDisponibilidadDetalleCu);
        txtGradoMayorCurr = (TextView) findViewById(R.id.xmlTvMaestriaDetalleCu);
        txtHabilidades = (TextView) findViewById(R.id.xmlTvHabilidadesDetalleCu);
        txtFecha = (TextView) findViewById(R.id.xmlTvFechaNacimientoDetalleCu);



        if (getIntent() != null)
            detallecurrid = getIntent().getStringExtra("detallecurrID");

        if (!detallecurrid.isEmpty()) {

            goDetailCurriculo(detallecurrid);


            VerificarFavorito();
        }

        VerificarAplicacionCurriculoExiste(sIdPersonaMarco);

        btnfavoritoCurriculo = (ToggleButton) findViewById(R.id.btn_CurriculosFavoritos);
        btnfavoritoCurriculo.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    marcarFavorito(detallecurrid);
                    Log.d( "estado activo", String.valueOf( b ) );
                }else {
                    final Query prueba = DbLikesFavCurri.child( "Empleadores" ).child( sIdPersonaMarco )
                            .child( "likes" ).orderByChild( "IdCurriculoLike" ).equalTo( detallecurrid );

                    prueba.addListenerForSingleValueEvent( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot pruebadataSnapshot : dataSnapshot.getChildren()) {
                                pruebadataSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText( DetalleCurriculo.this, "no funciono", Toast.LENGTH_SHORT ).show();
                        }
                    } );

                    Log.d( "estado inactivo", String.valueOf( b ) );
                }
            }
        } );

        mAuthEmpresa_Empleador = FirebaseAuth.getInstance();
        user = mAuthEmpresa_Empleador.getCurrentUser();
        sIdEmpresaAplico= user.getUid();


        btnIrFormacionAcademica = (Button) findViewById(R.id.xmlBtnFormacionAcademicaDetalleCu);
        btnIrFormacionAcademica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalleCurriculo.this, DetalleFormacionAcademica.class);
                intent.putExtra("DetalleFormacionAcademicaID", detallecurrid);
                startActivity(intent);
            }
        });

        btnIrReferencia = (Button) findViewById(R.id.xmlBtnReferenciasDetalleCu);
        btnIrReferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalleCurriculo.this, DetalleReferencias.class);
                intent.putExtra("DetalleReferenciaID", detallecurrid);
                startActivity(intent);
            }
        });

        btnIrExperienciaLab = (Button) findViewById(R.id.xmlBtnExperienciaLaboralDetalleCu);
        btnIrExperienciaLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalleCurriculo.this, DetalleExperienciaLaboral.class);
                intent.putExtra("DetalleExperienciaLabID", detallecurrid);
                startActivity(intent);
            }
        });

        btnOtrosEstudios = (Button) findViewById(R.id.xmlBtnOtrosEstudiosDetalleCu);
        btnOtrosEstudios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalleCurriculo.this, DetalleOtrosEstudios.class);
                intent.putExtra("DetalleOtrosEstudiosID", detallecurrid);
                startActivity(intent);
            }
        });

        btnAplicarCurriculo = (Button) findViewById(R.id.xmlBtnAplicarEmpleoDetalleCu);
        btnAplicarCurriculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AplicarInteresCurriculo(sIdEmpresaAplico,detallecurrid);

            }
        });
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void AplicarInteresCurriculo(String sIdEmpleadorAplico,String Interes) {

        sIdCurriculoAplico = Interes;

        sIdEmpresaAplico = sIdEmpleadorAplico;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
        sFechadeAplicacion = simpleDateFormat.format(new Date());

        sIdAplicarCurriculo = AplicarInteresCurriculoDataBase.push().getKey();

        AplicarCurriculo aplicarCurriculo = new AplicarCurriculo(sIdAplicarCurriculo, sIdCurriculoAplico, sIdEmpresaAplico, sFechadeAplicacion);
        AplicarInteresCurriculoDataBase.child(sIdAplicarCurriculo).setValue(aplicarCurriculo);
    }


    private void goDetailCurriculo(String detallecurrid) {
        detalelcurriculo.child(detallecurrid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("hola", String.valueOf(dataSnapshot));
                Curriculos vistaCurriculomodel = dataSnapshot.getValue(Curriculos.class);

                Picasso.get().load(vistaCurriculomodel.getsImagenC()).into(FotoCurriculo);

                txtNombreCurr.setText(vistaCurriculomodel.getsNombreC());
                txtApellidoCurr.setText(vistaCurriculomodel.getsApellidoC());
                txtCedulaCurr.setText(vistaCurriculomodel.getsCedulaC());
                txtEmailCurr.setText(vistaCurriculomodel.getsEmailC());
                txtTelefonoCurr.setText(vistaCurriculomodel.getsTelefonoC());
                txtCelularCurr.setText(vistaCurriculomodel.getsCelularC());
                txtprovinciaCurr.setText(vistaCurriculomodel.getsProvinciaC());
                txtEstadoCivil.setText(vistaCurriculomodel.getsEstadoCivilC());
                txtDireccionCurr.setText(vistaCurriculomodel.getsDireccionC());
                txtOcupacion.setText(vistaCurriculomodel.getsOcupacionC());
                txtEstadoActualCur.setText(vistaCurriculomodel.getsEstadoActualC());
                txtGradoMayorCurr.setText(vistaCurriculomodel.getsGradoMayorC());
                txtHabilidades.setText(vistaCurriculomodel.getsHabilidadesC());
                txtFecha.setText(vistaCurriculomodel.getsFechaC());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void VerificarFavorito() {
        final Query q = DbLikesFavCurri.child( "Empleadores" )//referencia empleadores
                .child( sIdPersonaMarco )//referencia usuario
                .child( "likes" )//referencia likes
                .orderByChild( "IdCurriculoLike" ).equalTo( detallecurrid );

        q.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d( "fav", String.valueOf( dataSnapshot ) );
                for (DataSnapshot FavdataSnapshot : dataSnapshot.getChildren()) {

                    if (FavdataSnapshot != null) {
                        btnfavoritoCurriculo.setChecked( true );

                    } else {
                        btnfavoritoCurriculo.setChecked( false );
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    private void marcarFavorito(String sIdCurriculoFav) {

        Query q = DbLikesFavCurri.child("Empleadores")
                .child(sIdPersonaMarco)
                .child("likes")
                .orderByChild("IdCurriculoLike").equalTo(sIdCurriculoFav);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Toast.makeText(DetalleCurriculo.this, "Favorito si", Toast.LENGTH_LONG).show();
                    String newLikeID = DbLikesFavCurri.push().getKey();

                    DbLikesFavCurri.child( "Empleadores" )//referencia empleadores
                            .child( sIdPersonaMarco )//referencia usuario
                            .child( "likes" )//referencia likes
                            .child( newLikeID )
                            .child( "IdCurriculoLike" )
                            .setValue( detallecurrid );
                }
                else {
                    Toast.makeText(DetalleCurriculo.this, "Favorito repetido", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void VerificarAplicacionCurriculoExiste(String sPersonaIdE) {

        Query q = AplicarInteresCurriculoDataBase.orderByChild("sIdEmpresaAplico").equalTo(sPersonaIdE);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot CurriculosSnapshot : dataSnapshot.getChildren()) {

                    String IdCurriculoAplico = CurriculosSnapshot.child("sIdCurriculoAplico").getValue(String.class);
                    if(IdCurriculoAplico.equals(detallecurrid)){
                        btnAplicarCurriculo.setEnabled(false);
                        TvYaAplicoCurriculo.setVisibility(View.VISIBLE);

                        //btnAplicarCurriculo.setTextColor(getResources().getColor(R.color.md_red_900));
                        //Toast.makeText(PantallaDetallesEmpleo.this, "Usted ha Aplicado anteriormente a este empleo", Toast.LENGTH_LONG).show();

                    }else{
                        Log.d("dataidEmpleosAplicar", IdCurriculoAplico);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
