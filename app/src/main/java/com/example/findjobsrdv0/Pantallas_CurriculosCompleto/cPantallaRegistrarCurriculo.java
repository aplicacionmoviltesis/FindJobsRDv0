package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.findjobsrdv0.GeneralesApp.DatePickerFragment;

import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.AreasCurriculos;
import com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador.Curriculos;
import com.example.findjobsrdv0.Adaptadores_Administrador.Provincias;
import com.example.findjobsrdv0.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class cPantallaRegistrarCurriculo extends AppCompatActivity {

    private EditText etNombre, etApellido, etCedula, etEmail, etTelefono, etCelular,
            etDireccion, etOcupacion, etHabilidades, etNivelPrimario, etNivelSecundario, etCarrera;

    private String nombre, apellido, cedula, email, telefono, celular, provincia, estadoCivil, direccion, ocupacion,
            idioma, gradomayor, estadoactual, sexo, habilidades, fecha, sAreaC, sImagenC, NivelPrimarioFormAcad,
            NivelSecundarioFormAcad, CarreraFormAcad;

    private Button btnIdiomasc;
    private SearchableSpinner spinEstadoCivil, spinGradoMayor, spinEstadoActual, spinSexo;

//    private RadioButton RdbDisponibleC, RdbNoDisponibleC;

    private TextView mEtxtFecha, mEtxtIdioma;

    private TextView muestraidioma;

    int IMAGE_REQUEST_CODE = 5;

    private String sIdBuscador = "";

    private DatabaseReference databaseReferenceCurrilo;
    private FirebaseDatabase database;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    private String mStoragePath = "Imagenes Curriculo/";
    private Uri mFilePathUri;
    StorageReference mStorageReference;

    private ImageView imageViewcurriculo;

    ProgressDialog mProgressDialog;

    private String CelularBuscador, EmailBuscador, NombreBuscador;

    private Button btnReferenciCurriculo, btnExperienciaLaboralCurriculo, btnOtrosCursosCurriculo, bntIdioma;

    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    private SearchableSpinner spinProvinciaCurriculo;
    private DatabaseReference provinciasRefCurriculo;
    List<Provincias> provincias;
    boolean IsFirstTimeClick = true;

    private String userActivo;

    //List which hold multiple selection spinner values
    List<String> list = new ArrayList<String>();

    private Button btnActualizarC;
//    Button btnActualizarC;

    DatabaseReference UniversidadesRef;
    SearchableSpinner spinUniversidadFormAcad;
    String UniversidadesFormAcad;

    private DatabaseReference Areas;
    private DatabaseReference RegistrarAreas;
    private SearchableSpinner spinAreaPrincipal, spinAreaSecundaria, spinAreaTerciaria;
    private String AreaPrincipal, AreaSecundaria, AreaTerciaria;

    private String CodigoArea;
    private String IdAreas;

    private DatabaseReference DBCedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_c_pantalla_registrar_curriculo );

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayShowHomeEnabled( true );
        //actionBar.setTitle(getResources().getString(R.string.titulo_CompararCurriculos));

        DBCedula = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.Ref_Curriculos));


        mStorageReference = getInstance().getReference();
        mProgressDialog = new ProgressDialog( cPantallaRegistrarCurriculo.this );

        database = FirebaseDatabase.getInstance();
        databaseReferenceCurrilo = database.getReference( getResources().getString( R.string.Ref_Curriculos ) );

        userActivo = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = firebaseAuth.getInstance().getCurrentUser();

        etNombre = (EditText) findViewById( R.id.etnombre );
        etApellido = (EditText) findViewById( R.id.etapellido );
        etCedula = (EditText) findViewById( R.id.etcedula );
        etEmail = (EditText) findViewById( R.id.etemail );
        etTelefono = (EditText) findViewById( R.id.ettelefono );
        etCelular = (EditText) findViewById( R.id.etcelula );
        etDireccion = (EditText) findViewById( R.id.etdireccion );
        etOcupacion = (EditText) findViewById( R.id.etocupacion );
        etHabilidades = (EditText) findViewById( R.id.ethabilidades );
        etNivelPrimario = (EditText) findViewById( R.id.xmlEditNivelPrimarioNameFA );
        etNivelSecundario = (EditText) findViewById( R.id.xmlEditNivelSecundarioNameFA );
        etCarrera = (EditText) findViewById( R.id.xmlEditCarreraNameFA );

        mEtxtFecha = (TextView) findViewById( R.id.tv );
        mEtxtIdioma = (TextView) findViewById( R.id.tvop );

//        RdbDisponibleC = (RadioButton) findViewById( R.id.maculinoCurri );
//        RdbNoDisponibleC = (RadioButton) findViewById( R.id.femeninaCurri );

        btnIdiomasc = (Button) findViewById( R.id.xmlBtnIdioma );

        provinciasRefCurriculo = FirebaseDatabase.getInstance().getReference();
        spinProvinciaCurriculo = (SearchableSpinner) findViewById( R.id.spinnerprovincias );

        spinProvinciaCurriculo.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!IsFirstTimeClick) {
                    provincia = spinProvinciaCurriculo.getSelectedItem().toString();

                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        } );

        provinciasRefCurriculo.child( getResources().getString( R.string.Ref_Provincias ) ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> ListProvinciasCurri = new ArrayList<String>();
                for (DataSnapshot provinciaSnapshot : dataSnapshot.getChildren()) {
                    String provinciaName = provinciaSnapshot.child( "sNombreProvincia" ).getValue( String.class );
                    ListProvinciasCurri.add( provinciaName );
                }

                ArrayAdapter<String> provinciasAdapterCurriculo = new ArrayAdapter<String>( cPantallaRegistrarCurriculo.this, android.R.layout.simple_spinner_item, ListProvinciasCurri );
                provinciasAdapterCurriculo.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spinProvinciaCurriculo.setAdapter( provinciasAdapterCurriculo );
                spinProvinciaCurriculo.setTitle( "Seleccionar Provincia" );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

        spinEstadoCivil = (SearchableSpinner) findViewById( R.id.spinnerestadocivil );
        spinEstadoCivil.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (!IsFirstTimeClick) {
                    estadoCivil = spinEstadoCivil.getSelectedItem().toString();
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );

        ArrayAdapter<CharSequence> adapterEstadoCivil = ArrayAdapter.createFromResource( this,
                R.array.EstadoCivil, android.R.layout.simple_spinner_item );
        adapterEstadoCivil.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinEstadoCivil.setAdapter( adapterEstadoCivil );
        spinEstadoCivil.setTitle( "Seleccionar su Estado Civil" );


        spinGradoMayor = (SearchableSpinner) findViewById( R.id.spinnergradomayor );
        spinGradoMayor.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (!IsFirstTimeClick) {
                    gradomayor = spinGradoMayor.getSelectedItem().toString();
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );
        ArrayAdapter<CharSequence> adapterGradoMayor = ArrayAdapter.createFromResource( this,
                R.array.InfoGrado, android.R.layout.simple_spinner_item );
        adapterGradoMayor.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinGradoMayor.setAdapter( adapterGradoMayor );
        spinGradoMayor.setTitle( "Seleccionar el Grado Mayor" );


        spinEstadoActual = (SearchableSpinner) findViewById( R.id.spinnerestadoactual );
        spinEstadoActual.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (!IsFirstTimeClick) {
                    estadoactual = spinEstadoActual.getSelectedItem().toString();
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );

        ArrayAdapter<CharSequence> adapterEstadoActual = ArrayAdapter.createFromResource( this,
                R.array.EstadoActual, android.R.layout.simple_spinner_item );
        adapterEstadoActual.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinEstadoActual.setAdapter( adapterEstadoActual );
        spinEstadoActual.setTitle( "Seleccionar el Estado Actual" );

        spinSexo = (SearchableSpinner) findViewById( R.id.spinnersexo );
        spinSexo.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (!IsFirstTimeClick) {
                    sexo = spinSexo.getSelectedItem().toString();
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );

        ArrayAdapter<CharSequence> adapterSexo = ArrayAdapter.createFromResource( this,
                R.array.InfoSexo, android.R.layout.simple_spinner_item );
        adapterSexo.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinSexo.setAdapter( adapterSexo );
        spinSexo.setTitle( "Seleccionar el Estado Actual" );


        listItems = getResources().getStringArray( R.array.InfoIdiomas );
        checkedItems = new boolean[listItems.length];

        imageViewcurriculo = (ImageView) findViewById( R.id.xmlImageViewimagenenelCurriculo );
        imageViewcurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType( "image/*" );
                galleryIntent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( galleryIntent, "Seleccionar Imagen" ), IMAGE_REQUEST_CODE );
            }
        } );

        if (getIntent() != null) {
            sIdBuscador = getIntent().getStringExtra( "BuscadorConectado" );
            if (!sIdBuscador.isEmpty()) {
                CargarCamposCurriculo( sIdBuscador );
            }
        }


        btnOtrosCursosCurriculo = (Button) findViewById( R.id.AbrirOtrosCursos );
        btnOtrosCursosCurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( cPantallaRegistrarCurriculo.this, cPantallaOtrosCursos.class );
                intent.putExtra( "DetalleOtrosCursosID", sIdBuscador );
                startActivity( intent );
            }
        } );

        btnExperienciaLaboralCurriculo = (Button) findViewById( R.id.xmlBntExperienciaLaboralCurriculo );
        btnExperienciaLaboralCurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( cPantallaRegistrarCurriculo.this, cPantallaExperienciaLaboralCurriculo.class );
                intent.putExtra( "DetalleExperienciaLaboralID", sIdBuscador );
                startActivity( intent );
            }
        } );

        btnReferenciCurriculo = (Button) findViewById( R.id.xmlBtnReferenciaCurriculoC );
        btnReferenciCurriculo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( cPantallaRegistrarCurriculo.this, cPantallaReferenciasCurriculo.class );
                intent.putExtra( "DetalleReferenciasID", sIdBuscador );
                startActivity( intent );
            }
        } );

        bntIdioma = findViewById( R.id.xmlBtnIdioma );

        muestraidioma = findViewById( R.id.tvop );

        btnIdiomasc.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder( cPantallaRegistrarCurriculo.this );
                mBuilder.setTitle( "Elegir Idiomas Requerido" );
                mBuilder.setMultiChoiceItems( listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                        if (isChecked) {
                            mUserItems.add( position );
                        } else {
                            mUserItems.remove( (Integer.valueOf( position )) );
                        }
                    }
                } );

                mBuilder.setCancelable( false );
                mBuilder.setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get( i )];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        mEtxtIdioma.setText( item );
                    }
                } );

                mBuilder.setNegativeButton( "Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                } );

                mBuilder.setNeutralButton( "Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mEtxtIdioma.setText( "" );
                        }
                    }
                } );

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        } );

//        RadioGroup RGRegistrarCurriculo = (RadioGroup) findViewById( R.id.radiobuttonsexo );
//        RGRegistrarCurriculo.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                switch (checkedId) {
//                    case R.id.maculinoCurri:
//                        sexo = "Masculino";
//                        break;
//                    case R.id.femeninaCurri:
//                        sexo = "Femenino";
//                        break;
//                }
//            }
//        } );


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
                    String UniversidadName = UniversidadSnapshot.child( "sNombreUniversidad" ).getValue( String.class );
                    ListUniversidades.add( UniversidadName );
                }
                ArrayAdapter<String> UniversidadesAdapter = new ArrayAdapter<String>( cPantallaRegistrarCurriculo.this, android.R.layout.simple_spinner_item, ListUniversidades );
                UniversidadesAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spinUniversidadFormAcad.setAdapter( UniversidadesAdapter );
                spinUniversidadFormAcad.setTitle( "Seleccionar Universidad" );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

//---------------------------------------------------------------------------------------------

        RegistrarAreas = FirebaseDatabase.getInstance().getReference( "AreasCurriculos" );

        Areas = FirebaseDatabase.getInstance().getReference();
        spinAreaPrincipal = (SearchableSpinner) findViewById( R.id.xmlspinAreaPrincipal );
        spinAreaPrincipal.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!IsFirstTimeClick) {
                    AreaPrincipal = spinAreaPrincipal.getSelectedItem().toString();
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        Areas.child( "Areas" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> ListAreas = new ArrayList<String>();
                for (DataSnapshot AreasSnapshot : dataSnapshot.getChildren()) {
                    String AreaName = AreasSnapshot.child( "sNombreArea" ).getValue( String.class );
                    ListAreas.add( AreaName );
                }
                ArrayAdapter<String> AreasAdapter = new ArrayAdapter<String>( cPantallaRegistrarCurriculo.this, android.R.layout.simple_spinner_item, ListAreas );
                AreasAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spinAreaPrincipal.setAdapter( AreasAdapter );
                spinAreaPrincipal.setTitle( "Seleccionar Area" );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

        spinAreaSecundaria = (SearchableSpinner) findViewById( R.id.xmlspinAreaSecundario );
        spinAreaSecundaria.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!IsFirstTimeClick) {
                    AreaSecundaria = spinAreaSecundaria.getSelectedItem().toString();
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        Areas.child( "Areas" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> ListAreas = new ArrayList<String>();
                for (DataSnapshot AreasSnapshot : dataSnapshot.getChildren()) {
                    String AreaName = AreasSnapshot.child( "sNombreArea" ).getValue( String.class );
                    ListAreas.add( AreaName );
                }
                ArrayAdapter<String> AreasAdapter = new ArrayAdapter<String>( cPantallaRegistrarCurriculo.this, android.R.layout.simple_spinner_item, ListAreas );
                AreasAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spinAreaSecundaria.setAdapter( AreasAdapter );
                spinAreaSecundaria.setTitle( "Seleccionar Area" );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

        spinAreaTerciaria = (SearchableSpinner) findViewById( R.id.xmlspinAreaTerciaria );
        spinAreaTerciaria.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!IsFirstTimeClick) {
                    AreaTerciaria = spinAreaTerciaria.getSelectedItem().toString();
                } else {
                    IsFirstTimeClick = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        Areas.child( "Areas" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> ListAreas = new ArrayList<String>();
                for (DataSnapshot AreasSnapshot : dataSnapshot.getChildren()) {
                    String AreaName = AreasSnapshot.child( "sNombreArea" ).getValue( String.class );
                    ListAreas.add( AreaName );
                }
                ArrayAdapter<String> AreasAdapter = new ArrayAdapter<String>( cPantallaRegistrarCurriculo.this, android.R.layout.simple_spinner_item, ListAreas );
                AreasAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                spinAreaTerciaria.setAdapter( AreasAdapter );
                spinAreaTerciaria.setTitle( "Seleccionar Area" );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );

        IdAreas = RegistrarAreas.push().getKey();

        btnActualizarC = findViewById( R.id.xmlBtnActualizarGC );
        btnActualizarC.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Query q = databaseReferenceCurrilo.orderByChild( "sIdCurriculo" ).equalTo( sIdBuscador );
                q.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            cedula = etCedula.getText().toString().trim();

                            Query q = databaseReferenceCurrilo.orderByChild( "sCedulaC" ).equalTo( cedula );
                            q.addListenerForSingleValueEvent( new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.exists()) {

                                        Query q = DBCedula.orderByChild(getResources().getString(R.string.Campo_sCedulaC)).equalTo( cedula );
                                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (!dataSnapshot.exists()){
                                                    registrarcurriculo( sIdBuscador );
                                                } else {
                                                    etCedula.setError( "Esta cedula ya a sido registrado" );
                                                    Log.d( "rnc si existe", String.valueOf( dataSnapshot ) );
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        } );

                                    } else {
                                        etCedula.setError( "Esta cedula ya a sido registrado" );
                                        Log.d( "rnc si existe", String.valueOf( dataSnapshot ) );
                                        //Toast.makeText(PantallaRegistroEmpleador.this, "El RNC escrito ya existe", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            } );

                        }else {
                            beginUpdate();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                } );
            }
        } );


//        btnActualizarC = findViewById( R.id.xmlBtnActualizarDatosGC );
//        btnActualizarC.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                sAreaC = spinAreaC.getSelectedItemsAsString();
//                beginUpdate();
//            }
//        } );
    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void onButtonClicked(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show( getFragmentManager(), "Date Picker" );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == IMAGE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            mFilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap( getContentResolver(), mFilePathUri );
                imageViewcurriculo.setImageBitmap( bitmap );
            } catch (Exception e) {
                Toast.makeText( this, e.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }
    }

//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

    public void beginUpdate() {
        mProgressDialog.setTitle( "Actualizando..." );
        mProgressDialog.show();
        DeleteImagenAnterior();
    }

    private void DeleteImagenAnterior() {

        if (sImagenC != null && !sImagenC.isEmpty()) {
            final StorageReference mPitureRef = getInstance().getReferenceFromUrl( sImagenC );
            mPitureRef.delete().addOnSuccessListener( new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText( cPantallaRegistrarCurriculo.this, "Eliminando Imagen...", Toast.LENGTH_LONG ).show();
                    Log.d( "link foto", String.valueOf( mPitureRef ) );
                    SubirNuevaImagen();
                }
            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( cPantallaRegistrarCurriculo.this, "No hay imagen agregada", Toast.LENGTH_LONG ).show();

                    mProgressDialog.dismiss();
                }
            } );
        } else {
            Toast.makeText( cPantallaRegistrarCurriculo.this, "No hay imagen agregada", Toast.LENGTH_LONG ).show();
            SubirNuevaImagen();
        }
    }

    private void SubirNuevaImagen() {
        String imageName = System.currentTimeMillis() + ".png";
        //String imageName = System.currentTimeMillis() + getFileExtension(mFilePathUri);

        StorageReference storageReference2do = mStorageReference.child( mStoragePath + imageName );

        Bitmap bitmap = ((BitmapDrawable) imageViewcurriculo.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.PNG, 100, baos );

        byte[] data = baos.toByteArray();
        UploadTask uploadTask = storageReference2do.putBytes( data );
        uploadTask.addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText( cPantallaRegistrarCurriculo.this, "Nueva Imagen Subida...", Toast.LENGTH_LONG ).show();
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri downloadUri = uriTask.getResult();
                Actualizarcurriculo( downloadUri.toString() );
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( cPantallaRegistrarCurriculo.this, e.getMessage(), Toast.LENGTH_LONG ).show();
                mProgressDialog.dismiss();
            }
        } );
    }

    private void CargarCamposCurriculo(String sIdBuscador) {

        databaseReferenceCurrilo.child( sIdBuscador ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    btnActualizarC.setEnabled( false );
                    etCedula.setEnabled( false );

                    Curriculos Datoscurriculos = dataSnapshot.getValue( Curriculos.class );
                    sImagenC = Datoscurriculos.getsImagenC();
                    if (sImagenC != null && sImagenC != "") {
                        Picasso.get().load( sImagenC ).into( imageViewcurriculo );
                    } else {
                        if (user.getPhotoUrl() != null) {
                            Glide.with( cPantallaRegistrarCurriculo.this ).load( user.getPhotoUrl() ).into( imageViewcurriculo );
                        }
                    }

                    nombre = Datoscurriculos.getsNombreC();
                    if (nombre != null && nombre != "") {
                        etNombre.setText( nombre );
                    } else {
                        NombreBuscador = user.getDisplayName();
                        if (NombreBuscador != null && NombreBuscador != "") {
                            etNombre.setText( NombreBuscador );
                        }
                    }

                    celular = Datoscurriculos.getsCelularC();
                    if (celular != null && celular != "") {
                        etCelular.setText( celular );
                    } else {
                        CelularBuscador = user.getPhoneNumber();
                        if (CelularBuscador != null && CelularBuscador != "") {
                            etCelular.setText( CelularBuscador );
                        }
                    }

                    email = Datoscurriculos.getsEmailC();
                    if (email != null && email != "") {
                        etEmail.setText( email );
                    } else {
                        EmailBuscador = user.getEmail();
                        if (EmailBuscador != null && EmailBuscador != "") {
                            etEmail.setText( EmailBuscador );
                        }
                    }


                    etApellido.setText( Datoscurriculos.getsApellidoC() );
                    etCedula.setText( Datoscurriculos.getsCedulaC() );
                    etTelefono.setText( Datoscurriculos.getsTelefonoC() );

                    spinProvinciaCurriculo.setSelection( obtenerPosicionItem( spinProvinciaCurriculo, Datoscurriculos.getsProvinciaC() ) );
                    spinEstadoCivil.setSelection( obtenerPosicionItem( spinEstadoCivil, Datoscurriculos.getsEstadoCivilC() ) );

                    etDireccion.setText( Datoscurriculos.getsDireccionC() );
                    etOcupacion.setText( Datoscurriculos.getsOcupacionC() );
                    mEtxtIdioma.setText( Datoscurriculos.getsIdiomaC() );
                    mEtxtIdioma.setText( Datoscurriculos.getsIdiomaC() );

                    spinGradoMayor.setSelection( obtenerPosicionItem( spinGradoMayor, Datoscurriculos.getsGradoMayorC() ) );
                    spinEstadoActual.setSelection( obtenerPosicionItem( spinEstadoActual, Datoscurriculos.getsEstadoActualC() ) );
                    spinSexo.setSelection( obtenerPosicionItem( spinSexo, Datoscurriculos.getsSexoC() ) );

                    mEtxtFecha.setText( Datoscurriculos.getsFechaC() );
                    etHabilidades.setText( Datoscurriculos.getsHabilidadesC() );

                    etNivelPrimario.setText( Datoscurriculos.getsNivelPrimarioFormAcad() );
                    etNivelSecundario.setText( Datoscurriculos.getsNivelSecundarioFormAcad() );
                    etCarrera.setText( Datoscurriculos.getsCarreraFormAcad() );

                    spinUniversidadFormAcad.setSelection( obtenerPosicionItem( spinUniversidadFormAcad, Datoscurriculos.getsUniversidadFormAcad() ) );


                } else {
//                    btnActualizarC.setEnabled( false );
                    etCedula.setEnabled( true );
                    if (user.getPhotoUrl() != null) {
                        Glide.with( cPantallaRegistrarCurriculo.this ).load( user.getPhotoUrl() ).into( imageViewcurriculo );
                    }
                    NombreBuscador = user.getDisplayName();
                    if (NombreBuscador != null && NombreBuscador != "") {
                        etNombre.setText( NombreBuscador );
                    }
                    CelularBuscador = user.getPhoneNumber();
                    if (CelularBuscador != null && CelularBuscador != "") {
                        etCelular.setText( CelularBuscador );
                    }
                    EmailBuscador = user.getEmail();
                    if (EmailBuscador != null && EmailBuscador != "") {
                        etEmail.setText( EmailBuscador );
                    }
                }

                CargarCamposArea( sIdBuscador );

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
                Toast.makeText( cPantallaRegistrarCurriculo.this, "Hubo un problema con traer los datos", Toast.LENGTH_LONG ).show();
            }
        } );
    }

    private void CargarCamposArea(String sIdBuscador) {
        Query q = RegistrarAreas.orderByChild( "sIdCurriculo" ).equalTo( sIdBuscador );
        q.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    AreasCurriculos areasCurriculos = datasnapshot.getValue( AreasCurriculos.class );

                    spinAreaPrincipal.setSelection( obtenerPosicionItem( spinAreaPrincipal, areasCurriculos.getsAreaPrincipalCurr() ) );
                    spinAreaSecundaria.setSelection( obtenerPosicionItem( spinAreaSecundaria, areasCurriculos.getsAreaSecundariaCurr() ) );
                    spinAreaTerciaria.setSelection( obtenerPosicionItem( spinAreaTerciaria, areasCurriculos.getsAreaTerciaria() ) );

                    CodigoArea = areasCurriculos.getsIdAreaCurriculo();
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


    private void registrarcurriculo(final String cIdCurriculo) {

        mProgressDialog.setTitle( "Actualizando Curriculo..." );
        mProgressDialog.show();

        final StorageReference StorageReference2nd = mStorageReference.child( mStoragePath + System.currentTimeMillis() + "." + getFileExtension( mFilePathUri ) );

        UploadTask uploadTask = StorageReference2nd.putFile( mFilePathUri );
        Task<Uri> urlTask = uploadTask.continueWithTask( new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return StorageReference2nd.getDownloadUrl();
            }
        } ).addOnCompleteListener( new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String downloadURL = downloadUri.toString();
                    Log.d( "url", downloadURL );
                    nombre = etNombre.getText().toString().trim().toLowerCase();
                    apellido = etApellido.getText().toString().trim();
                    cedula = etCedula.getText().toString().trim();
                    email = etEmail.getText().toString().trim();
                    celular = etCelular.getText().toString().trim();
                    telefono = etTelefono.getText().toString().trim();
                    direccion = etDireccion.getText().toString().trim();
                    ocupacion = etOcupacion.getText().toString().trim();
                    idioma = mEtxtIdioma.getText().toString().trim();
                    habilidades = etHabilidades.getText().toString().trim();
                    fecha = mEtxtFecha.getText().toString().trim();

                    NivelPrimarioFormAcad = etNivelPrimario.getText().toString().trim();
                    NivelSecundarioFormAcad = etNivelSecundario.getText().toString().trim();
                    CarreraFormAcad = etCarrera.getText().toString().trim();

                    if (TextUtils.isEmpty( cedula )) {
                        etCedula.setError( "Campo vacío, por favor escriba la Cedula " );
                        return;
                    }
                    if (TextUtils.isEmpty( apellido )) {
                        etApellido.setError( "Campo vacío, por favor escriba el Apellido " );
                        return;
                    }
                    if (TextUtils.isEmpty( celular )) {
                        etCelular.setError( "Campo vacío, por favor escriba el numero de Celular " );
                        return;
                    }
                    if (TextUtils.isEmpty( telefono )) {
                        etTelefono.setError( "Campo vacío, por favor escriba el numero de Telefono " );
                        return;
                    }
                    if (TextUtils.isEmpty( direccion )) {
                        etDireccion.setError( "Campo vacío, por favor escriba la Direccion " );
                        return;
                    }
                    if (TextUtils.isEmpty( ocupacion )) {
                        etOcupacion.setError( "Campo vacío, por favor escriba la Ocupacion " );
                        return;
                    }
                    if (TextUtils.isEmpty( idioma )) {
                        mEtxtIdioma.setError( "Campo vacío, por favor escriba el Idioma " );
                        return;
                    }
                    if (TextUtils.isEmpty( habilidades )) {
                        etHabilidades.setError( "Campo vacío, por favor escriba las Habilidades " );
                        return;
                    }
                    if (TextUtils.isEmpty( fecha )) {
                        mEtxtFecha.setError( "Campo vacío, por favor escriba la Fecha " );
                        return;
                    }
                    if (TextUtils.isEmpty( NivelPrimarioFormAcad )) {
                        etNivelPrimario.setError( "Campo vacío, por favor escriba el Nivel Primario " );
                        return;
                    }
                    if (TextUtils.isEmpty( NivelSecundarioFormAcad )) {
                        etNivelSecundario.setError( "Campo vacío, por favor escriba el Nivel Secundario " );
                        return;
                    }
                    if (TextUtils.isEmpty( CarreraFormAcad )) {
                        etCarrera.setError( "Campo vacío, por favor escriba la Carrera " );
                        return;
                    }


                    if (TextUtils.isEmpty( provincia )) {
                        Toast.makeText( cPantallaRegistrarCurriculo.this, "Spinner vacío, por favor seleccione una Provincia", Toast.LENGTH_LONG ).show();
                        return;
                    }

                    mProgressDialog.dismiss();

                    Curriculos curriculos = new Curriculos( cIdCurriculo,
                            downloadURL, nombre, apellido, cedula, email, telefono, celular,
                            provincia, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual,
                            sexo, habilidades, fecha, sAreaC, NivelPrimarioFormAcad, NivelSecundarioFormAcad, CarreraFormAcad, UniversidadesFormAcad );

                    databaseReferenceCurrilo.child( cIdCurriculo ).setValue( curriculos );

                    resgistrarAreas( cIdCurriculo );

                    Toast.makeText( cPantallaRegistrarCurriculo.this, "Curriculo subida exitosamente...", Toast.LENGTH_LONG ).show();

                } else {

                }
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    mProgressDialog.dismiss();
                Toast.makeText( cPantallaRegistrarCurriculo.this, e.getMessage(), Toast.LENGTH_LONG ).show();

            }
        } );
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType( contentResolver.getType( uri ) );
    }

    private void resgistrarAreas(final String cIdCurricul) {

        IdAreas = RegistrarAreas.push().getKey();

        AreasCurriculos areasCurriculos = new AreasCurriculos( IdAreas, cIdCurricul,
                AreaPrincipal, AreaSecundaria, AreaTerciaria );

        RegistrarAreas.child( IdAreas ).setValue( areasCurriculos );

    }

//    public void limpiarCampor() {
//        etNombre.setText( "" );
//        etApellido.setText( "" );
//        etCedula.setText( "" );
//        etEmail.setText( "" );
//        etCelular.setText( "" );
//        etTelefono.setText( "" );
//        etDireccion.setText( "" );
//
//        etOcupacion.setText( "" );
//        mEtxtIdioma.setText( "" );
//        etHabilidades.setText( "" );
//        mEtxtFecha.setText( "" );
//    }
//
//    public void ActivarCampor() {
//        btnFormacionAcademica.setEnabled( true );
//        btnOtrosCursosCurriculo.setEnabled( true );
//        btnExperienciaLaboralCurriculo.setEnabled( true );
//        btnReferenciCurriculo.setEnabled( true );
//
//
//    }

    private void Actualizarcurriculo(String foto) {

        nombre = etNombre.getText().toString().trim().toLowerCase();
        apellido = etApellido.getText().toString().trim();
        cedula = etCedula.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        celular = etCelular.getText().toString().trim();
        telefono = etTelefono.getText().toString().trim();
        direccion = etDireccion.getText().toString().trim();
        ocupacion = etOcupacion.getText().toString().trim();
        idioma = mEtxtIdioma.getText().toString().trim();
        habilidades = etHabilidades.getText().toString().trim();
        fecha = mEtxtFecha.getText().toString().trim();

        NivelPrimarioFormAcad = etNivelPrimario.getText().toString().trim();
        NivelSecundarioFormAcad = etNivelSecundario.getText().toString().trim();
        CarreraFormAcad = etCarrera.getText().toString().trim();

//        if (TextUtils.isEmpty( direccion )) {
//            etDireccion.setError( "Campo vacío, por favor escriba la direccion " );
//            return;
//        }
//
//        if (TextUtils.isEmpty(provincia)) {
//            Toast.makeText(this, "Spinner vacío, por favor seleccione una Provincia", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if (TextUtils.isEmpty(estadoCivil)) {
//            Toast.makeText(this, "Spinner vacío, por favor seleccione el estado civil", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if (TextUtils.isEmpty(AreaPrincipal)) {
//            Toast.makeText(this, "Spinner vacío, por favor seleccione una Area", Toast.LENGTH_LONG).show();
//            return;
//        }

        mProgressDialog.dismiss();

        Curriculos curriculos = new Curriculos( sIdBuscador,
                foto, nombre, apellido, cedula, email, telefono, celular,
                provincia, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual,
                sexo, habilidades, fecha, sAreaC, NivelPrimarioFormAcad, NivelSecundarioFormAcad, CarreraFormAcad, UniversidadesFormAcad );

        databaseReferenceCurrilo.child( sIdBuscador ).setValue( curriculos );

        ActualizarAreas( CodigoArea );

        Toast.makeText( cPantallaRegistrarCurriculo.this, "Curriculo actualizado exitosamente...", Toast.LENGTH_LONG ).show();

    }

    private void ActualizarAreas(String CodigoArea) {

        AreasCurriculos areasCurriculos = new AreasCurriculos( CodigoArea, sIdBuscador,
                AreaPrincipal, AreaSecundaria, AreaTerciaria );
        RegistrarAreas.child( CodigoArea ).setValue( areasCurriculos );

    }
}