package com.example.findjobsrdv0.Modelo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProvinciasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView NombreProvincia;
    public TextView DescripcionProvincia;
    public TextView CoordenadasProvincia;
    public ImageView imagenProvincia;

    private ItemClickListener itemClickListener;


    public ProvinciasViewHolder(@NonNull View itemView) {
        super(itemView);
    }


    @Override
    public void onClick(View v) {

    }
}
