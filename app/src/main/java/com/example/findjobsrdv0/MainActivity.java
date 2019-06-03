package com.example.findjobsrdv0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    SearchableSpinner spcountries;
    ArrayList arraycountires;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spcountries=(SearchableSpinner) findViewById(R.id.spcountries);
        arraycountires=new ArrayList<String>(Arrays.asList("India", "Pakistan", "US", "China", "UK", "Itely", "France"));
        adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arraycountires);
        spcountries.setAdapter(adapter);
        spcountries.setTitle("Select Countries");
        spcountries.setPositiveButton("Done");

    }
}
