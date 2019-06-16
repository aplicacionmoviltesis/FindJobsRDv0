package com.example.findjobsrdv0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.findjobsrdv0.GeneralesApp.MultipleSelectionSpinner;
import com.example.findjobsrdv0.GeneralesApp.Provincias;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Curriculos;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class PantallaBuscarEmpleos extends AppCompatActivity{

    SearchableSpinner searchableSpinner;

    Spinner areaSpinner;

    FirebaseDatabase prueba;
    // FirebaseDatabase mFirebaseDatabase;
    DatabaseReference provinciasRef,DbLikes;
    Button btn_mierda;

    DatabaseReference bbdd;

    List<Provincias> provincias;
    boolean IsFirstTimeClick=true;

    ////////////////////////////////////////
    //MultipleSelection spinner object
    MultipleSelectionSpinner mSpinner;
    private DatabaseReference mDatabase4Like;
    private boolean isLikeChecked = false;


    //List which hold multiple selection spinner values
    List<String> list = new ArrayList<String>();

    ////////////////////////////////////////
    CheckBox mLikeBtn;
    DatabaseReference mDatabaseLike; //Para usarse en el metodo de los likes
    FirebaseAuth mAuth;  //Para usarse en el metodo de los likes



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_buscar_empleos);



//        mDatabase4Like = FirebaseDatabase.getInstance().getReference().child("Likes");
//        mLikeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                isLikeChecked = true;
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                final String currentUsName = user.getDisplayName();
//
//                mDatabase4Like.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        if (isLikeChecked) {
//                            //PARA SABER SI EL LIKE TIENE EL ID DEL USER PARA SABER SI YA DIO LIKE
//                           /* if (dataSnapshot.child(postId).hasChild(mAuth.getCurrentUser().getUid())) {
//                                //SI EXISTE, ENTOCES QUITA EL LIKE AL PULSARLO
//
//                                mDatabase4Like.child(postId).child(mAuth.getCurrentUser().getUid()).removeValue();
//                                isLikeChecked = false;
//
//
//                            } else {
//                                mDatabase4Like.child(postId).child(mAuth.getCurrentUser().getUid()).setValue(currentUsName);
//                                isLikeChecked = false;
//
//                            }*/
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//
//
//            }
//        });

        prueba = FirebaseDatabase.getInstance();
        DbLikes = prueba.getReference();

        btn_mierda = (Button) findViewById(R.id.mierda);
        btn_mierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addNewLike();
            }
        });


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
                        Toast.makeText(PantallaBuscarEmpleos.this, "klk : " , Toast.LENGTH_SHORT).show();

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

        Query q = bbdd.orderByChild("sIdBuscadorEmpleo").equalTo("----HmAtSRSnxdfxb0Z1kM2qoW1OvNo1");
        //Log.d("adaptador q", String.valueOf(q.getRef());
        String titulo = "klk";

//        if (q.getRef().getKey() != null) {
//
//            //user exists, do something
//            Log.d("adaptador no null",titulo);
//
//        } else {
//            //user does not exist, do something else
//            Log.d("adaptador si null",titulo);
//
//        }
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("adaptador", String.valueOf(dataSnapshot));

//                ArrayAdapter<String> adaptador;
//                ArrayList<String> listado = new ArrayList<String>();

//
                for(DataSnapshot curridatasnapshot: dataSnapshot.getChildren()){
//                    Log.d("holapdddddd", String.valueOf(dataSnapshot));

                Curriculos curriculos = curridatasnapshot.getValue(Curriculos.class);

                String titulo = "klk";
//                    String nombre = dataSnapshot.child("sNombreC").getValue(String.class);

                Log.d("adaptador",titulo);

                    if (curriculos.getsNombreC() != null) {

                        //user exists, do something
                        Log.d("adaptador no null",titulo);

                    } else {
                        //user does not exist, do something else
                        Log.d("adaptador si null",titulo);

                    }

                    //Log.d("holapdddddd", String.valueOf(nombre));


                    //listado.add(titulo);
                }

//                adaptador = new ArrayAdapter<String>(PantallaBuscarEmpleos.this,android.R.layout.simple_list_item_1,listado);
//                //lista.setAdapter(adaptador);
//                adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                areaSpinner.setAdapter(adaptador);
//                Log.d("adaptador",String.valueOf(adaptador));

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





        ///////////////////////////////////////////////////

}

    public void setmLikeBtn(final String postId) {

        mDatabaseLike.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //AQUI DIRECTAMENTE VEO SI ESTA LIKEADO O NO EL POST POR EL CURRENT USER
                if (dataSnapshot.child(postId).hasChild(mAuth.getCurrentUser().getUid())) {
                    mLikeBtn.setChecked(true);

                } else {
                    mLikeBtn.setChecked(false);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
   // private void DeleteLike(){
        //Log.d(TAG, "addNewLike: adding new like");

       // String newLikeID = DbLikes.push().getKey();
        //Like like = new Like();
        //like.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
/*
        DbLikes.child("Empleadores")//referencia empleadores
                .child("01")//referencia usuario
                .child("likes")//referencia likes
                .child(newLikeID)
                .setValue(newLikeID+"dddd");

        DbLikes.child("Empleadores")//referencia empleadores
                .child("HmAtSRSnxdfxb0Z1kM2qoW1OvNo1")//referencia usuario
                .child("likes")//referencia likes
                .child(newLikeID)
                .child("IdEmpleoLike")
                .setValue(sEmpleoIdE);*/

//        DbLikes.child("Empleadores")
//                .child("HmAtSRSnxdfxb0Z1kM2qoW1OvNo1")
//                .child("likes")
//                //.orderByChild("IdEmpleoLike").equalTo(sEmpleoIdE)
//                .removeValue();

        /*myRef.child(getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mPhoto.getPhoto_id())
                .child(getString(R.string.field_likes))
                .child(newLikeID)
                .setValue(like);*/

        //mHeart.toggleLike();
        //getLikesString();
    //}

   /* private void addNewLike(){
        //Log.d(TAG, "addNewLike: adding new like");

        String newLikeID = DbLikes.push().getKey();
        //Like like = new Like();
        //like.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());

        DbLikes.child("Empleadores")//referencia empleadores
                .child("01")//referencia usuario
                .child("likes")//referencia likes
                .child(newLikeID)
                .setValue(newLikeID+"dddd");

        /*myRef.child(getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mPhoto.getPhoto_id())
                .child(getString(R.string.field_likes))
                .child(newLikeID)
                .setValue(like);*/

        //mHeart.toggleLike();
        //getLikesString();
    /*}

/*

    public class GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //Log.d(TAG, "onDoubleTap: double tap detected.");

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference
                    .child(getString(R.string.dbname_photos))
                    .child(mPhoto.getPhoto_id())
                    .child(getString(R.string.field_likes));
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                        String keyID = singleSnapshot.getKey();

                        //case1: Then user already liked the photo
                        if(mLikedByCurrentUser &&
                                singleSnapshot.getValue(Like.class).getUser_id()
                                        .equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                            myRef.child(getString(R.string.dbname_photos))
                                    .child(mPhoto.getPhoto_id())
                                    .child(getString(R.string.field_likes))
                                    .child(keyID)
                                    .removeValue();
///
                            myRef.child(getString(R.string.dbname_user_photos))
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child(mPhoto.getPhoto_id())
                                    .child(getString(R.string.field_likes))
                                    .child(keyID)
                                    .removeValue();

                            mHeart.toggleLike();
                            getLikesString();
                        }
                        //case2: The user has not liked the photo
                        else if(!mLikedByCurrentUser){
                            //add new like
                            addNewLike();
                            break;
                        }
                    }
                    if(!dataSnapshot.exists()){
                        //add new like
                        addNewLike();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return true;
        }
    }*/



    }
