package com.example.findjobsrdv0;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class cPantallaRegistroCurriculo extends AppCompatActivity {

    TextView muestraidioma;


    String[] SPINNERLIST1 = {"soltero", "casado", "Spinner Using Material Library", "Material Spinner Example"};
    String[] SPINNERLIST = {"Android Material Design", "Material Design Spinner", "Spinner Using Material Library", "Material Spinner Example"};

    private Button btnSimpleList, btnRadioButtonList, btnCheckBoxList,klkproba;

    private String[] listItems = {"Español", "Ingles", "Frances", "Mandarin", "Ruso"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_pantalla_registro_curriculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.spinnerProvinciasRegistrarCurriculo);
        materialDesignSpinner.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST1);
        MaterialBetterSpinner materialDesignSpinner1 = (MaterialBetterSpinner)
                findViewById(R.id.spinnerEstadoCivilRegistrarCurriculoklk);
        materialDesignSpinner1.setAdapter(arrayAdapter1);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST1);
        MaterialBetterSpinner materialDesignSpinner2 = (MaterialBetterSpinner)
                findViewById(R.id.spinnerEstadoCivilRegistrarbueno);
        materialDesignSpinner2.setAdapter(arrayAdapter2);

        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST1);
        MaterialBetterSpinner materialDesignSpinner3 = (MaterialBetterSpinner)
                findViewById(R.id.spinnerlokeraCurriculoklk);
        materialDesignSpinner3.setAdapter(arrayAdapter3);

        btnSimpleList = findViewById(R.id.btn_simple_list);
        btnRadioButtonList = findViewById(R.id.btn_radio_button_list);
        btnCheckBoxList = findViewById(R.id.btnop);
        klkproba = findViewById(R.id.btn_check_box_list);

        muestraidioma = findViewById(R.id.tvop);


        btnSimpleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cPantallaRegistroCurriculo.this, cPantallaFormacionAcademicaCurriculo.class);
                startActivity(intent);
            }
        });

        btnRadioButtonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cPantallaRegistroCurriculo.this, cPantallaReferenciasCurriculos.class);
                startActivity(intent);
            }
        });

        klkproba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cPantallaRegistroCurriculo.this, cPantallaExperienciaLaboralCurriculo.class);
                startActivity(intent);
            }
        });

        btnCheckBoxList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(cPantallaRegistroCurriculo.this);
                builder.setTitle("Choose items");

                boolean[] checkedItems = new boolean[]{true, false, false, false, false}; //this will checked the items when user open the dialog
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {


                        Toast.makeText(cPantallaRegistroCurriculo.this, "Position: " + which + " Value: " + listItems[which] + " State: " + (isChecked ? "checked" : "unchecked"), Toast.LENGTH_LONG).show();
                        String hola=listItems[which];
                        muestraidioma.setText(hola);

                    }
                });

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    public void onButtonClicked(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"Date Picker");
    }


}




