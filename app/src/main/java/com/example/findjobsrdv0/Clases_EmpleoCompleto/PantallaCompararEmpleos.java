package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.example.findjobsrdv0.R;

public class PantallaCompararEmpleos extends AppCompatActivity {

    private RecyclerView recyclerViewPrimerEmp,recyclerViewSegundoEmp;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_comparar_empleos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerViewPrimerEmp = (RecyclerView) findViewById(R.id.PrimerEmpleoSeleccionado);
        recyclerViewSegundoEmp = (RecyclerView) findViewById(R.id.SegundoEmpleoSeleccionado);

        recyclerViewPrimerEmp.setHasFixedSize(true);
        recyclerViewSegundoEmp.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerViewPrimerEmp.setLayoutManager(layoutManager);
        recyclerViewSegundoEmp.setLayoutManager(layoutManager);
    }
}
