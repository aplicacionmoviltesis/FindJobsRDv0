package com.example.findjobsrdv0.ActualizarFormacionAcad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.findjobsrdv0.ActualizarFormacionAcad.ViewHolder.ActualizarFormAcadViewHolder;
import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.FormacionAcademica;
import com.example.findjobsrdv0.Vista_recycler_en_los_insert.recyclerOtrosEstudios.Modelo.modeloOtrosCursos;
import com.example.findjobsrdv0.Vista_recycler_en_los_insert.recyclerOtrosEstudios.ViewHolder.OtrosEstudiosViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActualizarFormacionAcad extends AppCompatActivity {

    EditText etNivelPrimarioAct, etNivelSecundarioAct, etNivelSuperiorAct, etCarreraAct;
    Button btnActualizarFormacionesAcad;

    String idbuscadorc, idusuarioregistrado, carrerac, nivelprimarioc, nivelsecundarioc, nivelsuperiorc;

    String idusuariosregistrado;

//---------------------codigo de la vista de  Actualizar formacion academica----------------------------------------------------------------------------------------------------------------------


    FirebaseRecyclerAdapter<FormacionAcademica, ActualizarFormAcadViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference actualizarFormAcad;

    RecyclerView recycler_actualizarformacad;
    RecyclerView.LayoutManager layoutManager;

    String codigoc;

    String idactualizarformacad;



    String ActFormAcad = "";
//---------------------codigo de la vista de  Actualizar formacion academica----------------------------------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_actualizar_formacion_acad );

//---------------------codigo de la vista de  Actualizar formacion academica----------------------------------------------------------------------------------------------------------------------

        database = FirebaseDatabase.getInstance();
        actualizarFormAcad = database.getReference( "Formacion_Academica" );

        recycler_actualizarformacad = (RecyclerView) findViewById( R.id.recyclerActualizarFormAcad );
        recycler_actualizarformacad.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recycler_actualizarformacad.setLayoutManager( layoutManager );

//---------------------codigo de la vista de  Actualizar formacion academica----------------------------------------------------------------------------------------------------------------------

//---------------------codigo de la vista de  Actualizar formacion academica----------------------------------------------------------------------------------------------------------------------

        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        codigoc = Ukey;

        CargarFormacionAcademica( Ukey );
//---------------------codigo de la vista de  Actualizar formacion academica----------------------------------------------------------------------------------------------------------------------


        etNivelPrimarioAct = (EditText) findViewById( R.id.xmlEditNivelPrimarioNameFAAcrualizar );
        etNivelSecundarioAct = (EditText) findViewById( R.id.xmlEditNivelSecundarioNameFAAcrualizar );
        etNivelSuperiorAct = (EditText) findViewById( R.id.xmlEditNivelSuperiorNameFAAcrualizar );
        etCarreraAct = (EditText) findViewById( R.id.xmlEditCarreraNameFAAcrualizar );

        btnActualizarFormacionesAcad = (Button) findViewById( R.id.xmlbtnAnadirformacionAcademicaAcrualizar );
        btnActualizarFormacionesAcad.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActualizarFormacionAcademica( idactualizarformacad );
            }
        } );

        if (getIntent() != null)
            ActFormAcad = getIntent().getStringExtra( "DetalleFormacionAcademicaID" );

        if (!ActFormAcad.isEmpty()) {
         //   registrarotroscurriculos( ActFormAcad );
        }
    }

    private void CargarFormacionAcademica(String ukey) {
        adapter = new FirebaseRecyclerAdapter<FormacionAcademica, ActualizarFormAcadViewHolder>( FormacionAcademica.class, R.layout.cardview_actualizar_formacion_acad, ActualizarFormAcadViewHolder.class,
                actualizarFormAcad.orderByChild( "idusuarioregistrado" ).equalTo( ukey ) ) {
            @Override
            protected void populateViewHolder(ActualizarFormAcadViewHolder ViewHolder, FormacionAcademica modelo, int position) {

                ViewHolder.txtNivelPrimarioAct.setText( modelo.getNivelprimarioc() );
                ViewHolder.txtNivelSecundarioAct.setText( modelo.getNivelsecundarioc() );
                ViewHolder.txtNivelSuperiorAct.setText( modelo.getNivelsuperiorc() );
                ViewHolder.txtCarreraAct.setText( modelo.getCarrerac() );

                final FormacionAcademica ClickItem = modelo;
                ViewHolder.setItemClickListener( new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        idactualizarformacad = adapter.getRef( position ).getKey();

                        gocargardatosFormacionCad( idactualizarformacad );

                    }
                } );
            }
        };
        recycler_actualizarformacad.setAdapter( adapter );

    }

    private void gocargardatosFormacionCad(String idactualizarformacad) {
        actualizarFormAcad.child( idactualizarformacad ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                FormacionAcademica formacionAcademica = dataSnapshot.getValue( FormacionAcademica.class );

                etNivelPrimarioAct.setText( formacionAcademica.getNivelprimarioc() );
                etNivelSecundarioAct.setText( formacionAcademica.getNivelsuperiorc() );
                etNivelSuperiorAct.setText( formacionAcademica.getNivelsuperiorc() );
                etCarreraAct.setText( formacionAcademica.getCarrerac() );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    private void ActualizarFormacionAcademica(String idactualizarformacad) {
        carrerac = etCarreraAct.getText().toString().trim();
        nivelprimarioc = etNivelPrimarioAct.getText().toString().trim();
        nivelsecundarioc = etNivelSecundarioAct.getText().toString().trim();
        nivelsuperiorc = etNivelSuperiorAct.getText().toString().trim();
        idbuscadorc = ActFormAcad;


        if (TextUtils.isEmpty( carrerac )) {
            etNivelPrimarioAct.setError( "Campo vacío, por favor escriba el nombre " );
            return;
        }
     /*   if (TextUtils.isEmpty( cApellido )) {
            etApellido.setError( "Campo vacío, por favor escriba el apellido" );
            return;
        }
   */


        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        idusuariosregistrado = Ukey;

        String IdFormacionAcademica = actualizarFormAcad.push().getKey();

        String IdFormacionAcademic = idactualizarformacad;


        FormacionAcademica formacionAcademica = new FormacionAcademica( IdFormacionAcademic, idbuscadorc, idusuariosregistrado, carrerac, nivelprimarioc, nivelsecundarioc, nivelsuperiorc );

        actualizarFormAcad.child( IdFormacionAcademic ).setValue( formacionAcademica );

        // limpiarCampor();

        //DBReferenceCurriculos.child("empleos").child(IDEmpleo).setValue(empleos);
        // mDatabase.child(Ukey).push().setValue(formacionAcademica);//para registrarlo dentro del usuario que inicio sesion


    }
}
