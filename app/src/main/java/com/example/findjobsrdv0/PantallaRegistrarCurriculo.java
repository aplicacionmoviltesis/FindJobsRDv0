package com.example.findjobsrdv0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class PantallaRegistrarCurriculo extends AppCompatActivity {

    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private String klk;
    private EditText Probando;
    Button prueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registrar_curriculo);

        tablayout = (TabLayout) findViewById(R.id.tabs);
        appBarLayout = (AppBarLayout) findViewById(R.id.Appbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentDatosGeneralesCurriculo(), "Datos Generales");

        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);

        final EditText probando = (EditText) findViewById(R.id.xmlbeditRegistrarnombrecurriculo);
        prueba = (Button) findViewById(R.id.paproba);

        prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klk=probando.getText().toString().trim();
                Log.d(klk,"hola");
            }
        });
    }
}
