package com.example.findjobsrdv0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class PantallaBuscarEmpleos extends AppCompatActivity{

    SearchableSpinner searchableSpinner;

    Spinner areaSpinner;

    // FirebaseDatabase mFirebaseDatabase;
    DatabaseReference provinciasRef;


    List<Provincias> provincias;
    boolean IsFirstTimeClick=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_buscar_empleos);

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
                    Log.d("perra", cri);
                }
                else {

                    IsFirstTimeClick=false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        provinciasRef.child("provincias").addValueEventListener(new ValueEventListener() {
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
        });


}



    }
