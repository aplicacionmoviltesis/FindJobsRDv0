package com.example.findjobsrdv0;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.findjobsrdv0.GeneralesApp.MultipleSelectionSpinner;
import com.example.findjobsrdv0.GeneralesApp.Provincias;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Curriculos;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class PantallaBuscarEmpleos extends AppCompatActivity {

    SearchableSpinner searchableSpinner;

    Spinner areaSpinner;

    // FirebaseDatabase mFirebaseDatabase;
    DatabaseReference provinciasRef;

    DatabaseReference bbdd;

    List<Provincias> provincias;
    boolean IsFirstTimeClick=true;

    ////////////////////////////////////////
    //MultipleSelection spinner object
    MultipleSelectionSpinner mSpinner;

    //List which hold multiple selection spinner values
    List<String> list = new ArrayList<String>();


    // FirebaseDatabase mFirebaseDatabase;


    FirebaseDatabase prueba;
    DatabaseReference DbLikes;
    String sEmpleoIdE;
    Button btn_mierda,btn_mierda1;
    ////////////////////////////////////////



    CheckBox checkBoxFav;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_buscar_empleos);

        checkBoxFav = (CheckBox) findViewById( R.id.checkboxFavoritoEmpleo );
      //  checkBoxFav.setOnCheckedChangeListener( this );


       prueba = FirebaseDatabase.getInstance();
        DbLikes = prueba.getReference();
/*
        btn_mierda = (Button) findViewById(R.id.mierda);
        btn_mierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewLike();
            }
        });

        btn_mierda1 = (Button) findViewById(R.id.mierda1);
        btn_mierda1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteLike();
            }
        });

*/
        MaterialFavoriteButton favorite = new MaterialFavoriteButton.Builder(this)
                .create();

        favorite.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        //
                        if(favorite == true){

                            Log.d("valor boolean",String.valueOf(favorite));
                        }
                        if(favorite == false){

                            Log.d("valor boolean",String.valueOf(favorite));
                        }
                    }
                });

        favorite.setOnFavoriteAnimationEndListener(
                new MaterialFavoriteButton.OnFavoriteAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(MaterialFavoriteButton buttonView, boolean favorite) {
                        //
                    }
                });

        //favoriteButton.setFavorite(isFavorite(data.get(position)));


        //searchableSpinner = (SearchableSpinner)findViewById(R.id.searchable_spinner);

        provinciasRef = FirebaseDatabase.getInstance().getReference();//.child("Empleadores");
        areaSpinner = (Spinner) findViewById(R.id.searchable_spinner);


        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!IsFirstTimeClick){
                    //Provincias provincia = provincias.get(position);
                    // klk= provincia.sNombreProvincia;


                    //String klk= provincia.getName();
                    //Log.d("klk",klk);

                    String cri= areaSpinner.getSelectedItem().toString();
                    Log.d("valorSpin", cri);
                }
                else {

                    IsFirstTimeClick=false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinner = findViewById(R.id.mSpinner);
/*
        provinciasRef.child("Curriculos").orderByChild("cCodigoId").equalTo("-LgGKwS2my1D564M4Drg").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> areas = new ArrayList<String>();
                Log.d("holap", String.valueOf(dataSnapshot));

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {

                    String areaName = areaSnapshot.child("nombre").getValue(String.class);
                    areas.add(areaName);
                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(PantallaBuscarEmpleos.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
                String cri= areaSpinner.getSelectedItem().toString();
                Log.d("seleccionado", cri);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        ///////////////////////////////////////////////////

        bbdd = FirebaseDatabase.getInstance().getReference("Curriculos");

        Query q = bbdd.orderByChild("cIdBuscador").equalTo("probandousuario");

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;
                ArrayList<String> listado = new ArrayList<String>();

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    Log.d("holapdddddd", String.valueOf(dataSnapshot));

                    Curriculos curriculos = datasnapshot.getValue(Curriculos.class);

                    String titulo = curriculos.getsNombreC();
                    Log.d("adaptador",titulo);

                    listado.add(titulo);
                }

                adaptador = new ArrayAdapter<String>(PantallaBuscarEmpleos.this,android.R.layout.simple_list_item_1,listado);
                //lista.setAdapter(adaptador);
                Log.d("adaptador",String.valueOf(adaptador));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //casting of spinner
        mSpinner = findViewById(R.id.mSpinner);

        //adding items to list
        list.add("Violet");
        list.add("Indigo");
        list.add("Brown");
        list.add("Green");
        list.add("Yellow");
        list.add("Orange");
        list.add("Red");

        //set items to spinner from list
        mSpinner.setItems(list);

        //onClick listener of button for showing multiple selection spinner values
        findViewById(R.id.btnelegir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PantallaBuscarEmpleos.this, "Selected : " + mSpinner.getSelectedItemsAsString() , Toast.LENGTH_SHORT).show();
            }
        });



        sEmpleoIdE = "-Lg4YqLFDOwMHBe7RNbz";


        ///////////////////////////////////////////////////

}
/*

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        String newLikeID = DbLikes.push().getKey();

        if (b){
            DbLikes.child("Empleadores")//referencia empleadores
                    .child("HmAtSRSnxdfxb0Z1kM2qoW1OvNo1")//referencia usuario
                    .child("likes")//referencia likes
                    .child(newLikeID)
                    .child("IdEmpleoLike")
                    .setValue(sEmpleoIdE);

        }else {
            DbLikes.child("Empleadores")
                    .child("HmAtSRSnxdfxb0Z1kM2qoW1OvNo1")
                    .child("likes")
                    .child("-LhCgqilQ0DPJLZgOu_4")
                    .removeValue();
        }

    }*/



    public void onClick(View view) {

        String newLikeID = DbLikes.push().getKey();
        if (view.getId()==R.id.checkboxFavoritoEmpleo){
            if (checkBoxFav.isChecked()){
                DbLikes.child("BuscadoresEmpleos")//referencia empleadores
                        .child("owd7TZZ5JRTFSUR7RTe00AcIVdy2")//referencia usuario
                        .child("likes")//referencia likes
                        .child(newLikeID)
                        .child("IdEmpleoLike")
                        .setValue(sEmpleoIdE);
            }else {
                final Query prueba = DbLikes.child( "BuscadoresEmpleos" ).child("owd7TZZ5JRTFSUR7RTe00AcIVdy2")
                        .child("likes").orderByChild( "IdEmpleoLike" ).equalTo( sEmpleoIdE );

                prueba.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot pruebadataSnapshot: dataSnapshot.getChildren()){
                            pruebadataSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText( PantallaBuscarEmpleos.this, "no funciono", Toast.LENGTH_SHORT ).show();
                    }
                } );

            }

        }

    }

/*
    private void addNewLike(){

        String newLikeID = DbLikes.push().getKey();



        DbLikes.child("Empleadores")//referencia empleadores
                .child("HmAtSRSnxdfxb0Z1kM2qoW1OvNo1")//referencia usuario
                .child("likes")//referencia likes
                .child(newLikeID)
                .child("IdEmpleoLike")
                .setValue(sEmpleoIdE);


    }

    private void DeleteLike(){
        String newLikeID = DbLikes.push().getKey();


        final Query prueba = DbLikes.child( "Empleadores" ).child("HmAtSRSnxdfxb0Z1kM2qoW1OvNo1")
                .child("likes").orderByChild( "IdEmpleoLike" ).equalTo( sEmpleoIdE );

        prueba.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot pruebadataSnapshot: dataSnapshot.getChildren()){
                    pruebadataSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText( PantallaBuscarEmpleos.this, "no funciono", Toast.LENGTH_SHORT ).show();
            }
        } );
*/
/*
        String klk = newLikeID;
        DbLikes.child("Empleadores")
                .child("HmAtSRSnxdfxb0Z1kM2qoW1OvNo1")
                .child("likes")
                .orderByChild( "IdEmpleoLike" ).equalTo( klk )
                .setValue( null );
*/



}
