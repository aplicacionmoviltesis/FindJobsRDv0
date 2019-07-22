package com.example.findjobsrdv0.Adaptadores_Administrador;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;

public class Uni_Prov_Area_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView Nombre_Uni_Prov_Area_CardView;
    public TextView OtroDato_Uni_Prov_Area_CardView;

    public ImageView imagen_Uni_Prov_Area_CardView;

    private ItemClickListener itemClickListener;


    public Uni_Prov_Area_ViewHolder(@NonNull View itemView) {
        super(itemView);

        Nombre_Uni_Prov_Area_CardView = (TextView) itemView.findViewById(R.id.CardNombre_Prov_Area_Uni);
        OtroDato_Uni_Prov_Area_CardView = (TextView) itemView.findViewById(R.id.CardOtroDato_Prov_Area_Uni);

        imagen_Uni_Prov_Area_CardView = (ImageView) itemView.findViewById(R.id.CardImage_Prov_Area_Uni);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
