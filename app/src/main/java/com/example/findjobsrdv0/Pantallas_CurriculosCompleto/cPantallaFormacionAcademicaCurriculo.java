package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.FormacionAcademica;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import java.util.ArrayList;
import java.util.List;

public class cPantallaFormacionAcademicaCurriculo extends AppCompatActivity {
    private TextView TituloFormacionAcademica;

    EditText etCarrera, etNivelPrimario, etNivelSecundario, etNivelSuperior;
    private Button BtnRegistrarFormAcad;

    String idCurriculo, carrerac, nivelprimarioc, nivelsecundarioc, nivelsuperiorc;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    String detalleformacad = "";

    String idusuariosregistrado;

    String idActualizar;

    String Ukey;

    DatabaseReference databaseReferenceCurrilo;
    FirebaseDatabase database;

    String id;

    DatabaseReference UniversidadesRef;
    SearchableSpinner spinUniversidadFormAcad;
    boolean IsFirstTimeClick = true;
    String UniversidadesFormAcad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_c_pantalla_formacion_academica_curriculo );

        database = FirebaseDatabase.getInstance();
        databaseReferenceCurrilo = database.getReference( "Curriculos" );

        Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        TituloFormacionAcademica = (TextView) findViewById( R.id.xmlTituloFormacionAcademica );
        Typeface face = Typeface.createFromAsset( getAssets(), "fonts/robotoslab.bold.ttf" );
        TituloFormacionAcademica.setTypeface( face );

        mDatabase = FirebaseDatabase.getInstance().getReference( "Formacion_Academica" );
        mAuth = FirebaseAuth.getInstance();

        etCarrera = (EditText) findViewById( R.id.xmlEditCarreraNameFA );
        etNivelSuperior = (EditText) findViewById( R.id.xmlEditNivelSuperiorNameFA );
        etNivelSecundario = (EditText) findViewById( R.id.xmlEditNivelSecundarioNameFA );
        etNivelPrimario = (EditText) findViewById( R.id.xmlEditNivelPrimarioNameFA );

        UniversidadesRef = FirebaseDatabase.getInstance().getReference();
        spinUniversidadFormAcad = (SearchableSpinner) findViewById( R.id.xmlspinUniversidadFormAcad );
        spinUniversidadFormAcad.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!IsFirstTimeClick) {
                    UniversidadesFormAcad = spinUniversidadFormAcad.getSelectedItem().toString();
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        UniversidadesRef.child( "Universidades" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> ListUniversidades = new ArrayList<String>();
                for (DataSnapshot UniversidadSnapshot : dataSnapshot.getChildren()) {
                    String UniversidadName = UniversidadSnapshot.child( "Nombre" ).getValue( String.class );
                    ListUniversidades.add( UniversidadName );
                }
                ArrayAdapter<String> UniversidadesAdapter = new ArrayAdapter<String>( cPantallaFormacionAcademicaCurriculo.this, android.R.layout.simple_spinner_item, ListUniversidades );
                UniversidadesAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spinUniversidadFormAcad.setAdapter( UniversidadesAdapter );
                spinUniversidadFormAcad.setTitle( "Seleccionar Universidad" );
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

        Query query = databaseReferenceCurrilo.orderByChild( "sIdBuscadorEmpleo" ).equalTo( Ukey );
        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot FavdataSnapshot : dataSnapshot.getChildren()) {
                    id = FavdataSnapshot.child( "sIdCurriculo" ).getValue( String.class );
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        BtnRegistrarFormAcad = (Button) findViewById( R.id.xmlbtnAnadirformacionAcademica );
        BtnRegistrarFormAcad.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarFormacionAcademica( id );
            }
        } );

        Button btnactualizarformacionacad = findViewById( R.id.xmlbtnActualizarformacionAcademica );
        btnactualizarformacionacad.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActualizarFormacionAcad( Ukey, id );
            }
        } );

        if (getIntent() != null)
            detalleformacad = getIntent().getStringExtra( "DetalleFormacionAcademicaID" );

        if (!detalleformacad.isEmpty()) {
            RegistrarFormacionAcademica( id );
        }
        cardarcamposformacionacad( Ukey );
    }

    private void cardarcamposformacionacad(String ukey) {

        mDatabase = FirebaseDatabase.getInstance().getReference( "Formacion_Academica" );
        Query q = mDatabase.orderByChild( "sIdBuscadorEmpleoFormAcad" ).equalTo( ukey );

        q.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Log.d( "holapdddddd", String.valueOf( dataSnapshot ) );

                    FormacionAcademica formacionAcademica = datasnapshot.getValue( FormacionAcademica.class );
                    etNivelPrimario.setText( formacionAcademica.getsNivelPrimarioFormAcad() );
                    etNivelSecundario.setText( formacionAcademica.getsNivelSecundarioFormAcad() );
                    etNivelSuperior.setText( formacionAcademica.getsNivelSuperiorFormAcad() );
                    etCarrera.setText( formacionAcademica.getsCarreraFormAcad() );
                    spinUniversidadFormAcad.setSelection( obtenerPosicionItem( spinUniversidadFormAcad, formacionAcademica.getsUniversidadFormAcad() ) );

                    idActualizar = formacionAcademica.getsIdFormacionAcademica();
                }
            }

            public int obtenerPosicionItem(Spinner spinner, String fruta) {
                //Creamos la variable posicion y lo inicializamos en 0
                int posicion = 0;
                //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
                //que lo pasaremos posteriormente
                for (int i = 0; i < spinner.getCount(); i++) {
                    //Almacena la posición del ítem que coincida con la búsqueda
                    if (spinner.getItemAtPosition( i ).toString().equalsIgnoreCase( fruta )) {
                        posicion = i;
                    }
                }
                //Devuelve un valor entero (si encontro una coincidencia devuelve la
                // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
                return posicion;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

//    public void limpiarCampor() {
//        etNivelPrimario.setText( "" );
//        etNivelSecundario.setText( "" );
//        etNivelSuperior.setText( "" );
//        etCarrera.setText( "" );
//    }

    private void ActualizarFormacionAcad(final String Ukey, final String id) {
        Log.d( "cuando lo recive", String.valueOf( detalleformacad ) );
        carrerac = etCarrera.getText().toString().trim();
        nivelprimarioc = etNivelPrimario.getText().toString().trim();
        nivelsecundarioc = etNivelSecundario.getText().toString().trim();
        nivelsuperiorc = etNivelSuperior.getText().toString().trim();
        idCurriculo = id;

        if (TextUtils.isEmpty( carrerac )) {
            etCarrera.setError( "Campo vacío, por favor escriba el nombre " );
            return;
        }
     /*   if (TextUtils.isEmpty( cApellido )) {
            etApellido.setError( "Campo vacío, por favor escriba el apellido" );
            return;
        }
   */
        idusuariosregistrado = Ukey;
        FormacionAcademica formacionAcademica = new FormacionAcademica( idActualizar, idCurriculo, idusuariosregistrado, carrerac, nivelprimarioc, nivelsecundarioc, nivelsuperiorc, UniversidadesFormAcad );
        mDatabase.child( idActualizar ).setValue( formacionAcademica );
    }

    public void RegistrarFormacionAcademica(String id) {

        carrerac = etCarrera.getText().toString().trim();
        nivelprimarioc = etNivelPrimario.getText().toString().trim();
        nivelsecundarioc = etNivelSecundario.getText().toString().trim();
        nivelsuperiorc = etNivelSuperior.getText().toString().trim();
        idCurriculo = id;

        if (TextUtils.isEmpty( carrerac )) {
            etCarrera.setError( "Campo vacío, por favor escriba el nombre " );
            return;
        }
     /*   if (TextUtils.isEmpty( cApellido )) {
            etApellido.setError( "Campo vacío, por favor escriba el apellido" );
            return;
        }
   */
        idusuariosregistrado = Ukey;
        String IdFormacionAcademica = mDatabase.push().getKey();
        FormacionAcademica formacionAcademica = new FormacionAcademica( IdFormacionAcademica, idCurriculo, idusuariosregistrado, carrerac, nivelprimarioc, nivelsecundarioc, nivelsuperiorc, UniversidadesFormAcad );
        mDatabase.child( IdFormacionAcademica ).setValue( formacionAcademica );
    }
}
