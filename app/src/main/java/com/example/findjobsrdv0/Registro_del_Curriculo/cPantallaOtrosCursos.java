package com.example.findjobsrdv0.Registro_del_Curriculo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.OtrosCursos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class cPantallaOtrosCursos extends AppCompatActivity {

    EditText etInstitucion, etAno, etAreaoTema;
    SearchableSpinner ssTipodeEstudio;
    Button BtnRegistrarOtrosCursos;

    String ocInstitucionC, ocAnoC, ocAreaoTemaC, ocTipoEstudio, ocIdBuscardor;

    private DatabaseReference DBOtrosCursosCurriculos;

    String detalleotroscursos = "";

    String idusuarioregistrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_c_pantalla_otros_cursos );

        DBOtrosCursosCurriculos = FirebaseDatabase.getInstance().getReference( "Otros_Cursos" );

        etInstitucion = (EditText) findViewById( R.id.xmlEditNombreInstitucionOC );
        etAno = (EditText) findViewById( R.id.xmlEditAñoOC );
        etAreaoTema = (EditText) findViewById( R.id.xmlEditAreaOC );
        ssTipodeEstudio = (SearchableSpinner) findViewById( R.id.xmlspinTipoEstudioOC );

        BtnRegistrarOtrosCursos = (Button) findViewById( R.id.xmlbtnGuardarCursosOC );
        BtnRegistrarOtrosCursos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarotroscurriculos(detalleotroscursos);
            }
        } );

        if (getIntent() != null)
            detalleotroscursos = getIntent().getStringExtra( "DetalleOtrosCursosID" );

        if (!detalleotroscursos.isEmpty()) {
            registrarotroscurriculos( detalleotroscursos );
        }

    }

    private void registrarotroscurriculos(String detalleotroscursos) {
        ocInstitucionC = etInstitucion.getText().toString().trim();
        ocAnoC = etAno.getText().toString().trim();
        ocAreaoTemaC = etAreaoTema.getText().toString().trim();
        ocTipoEstudio = ssTipodeEstudio.getSelectedItem().toString().trim();
        ocIdBuscardor = detalleotroscursos;

        if (TextUtils.isEmpty( ocInstitucionC )) {
            etInstitucion.setError( "Campo vacío, por favor escriba la institucion" );
            return;
        }

        String Ukey = FirebaseAuth.getInstance().getCurrentUser().getUid();

        idusuarioregistrado = Ukey;

        String IdCurriculo = DBOtrosCursosCurriculos.push().getKey();

        OtrosCursos otrosCursos = new OtrosCursos( IdCurriculo, ocIdBuscardor, idusuarioregistrado, ocInstitucionC, ocAnoC, ocAreaoTemaC, ocTipoEstudio );

        DBOtrosCursosCurriculos.child( IdCurriculo ).setValue( otrosCursos );

    }
}
