package com.example.findjobsrdv0.Modelo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.R;

public class EmpleosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView NombreEmpleoCardView;
    public TextView NombreEmpresaCardView;
    public TextView ProvinciaCardView;
    public TextView AreaCardView;
    public TextView EstadoCardView;
    public TextView FechaPublicacionCardView;

    public ImageView imagenEmpleoCardView;



    private ItemClickListener itemClickListener;


    public EmpleosViewHolder(@NonNull View itemView) {
        super(itemView);

        NombreEmpleoCardView = (TextView)itemView.findViewById(R.id.CardNombreempleo);
        NombreEmpresaCardView = (TextView)itemView.findViewById(R.id.CardNombreempresa);
        ProvinciaCardView = (TextView)itemView.findViewById(R.id.CardProvincia);
        AreaCardView = (TextView)itemView.findViewById(R.id.Cardarea);
        EstadoCardView = (TextView)itemView.findViewById(R.id.CardEstado);
        FechaPublicacionCardView = (TextView)itemView.findViewById(R.id.CardFecha);


        imagenEmpleoCardView = (ImageView) itemView.findViewById(R.id.CardImageArea);

        itemView.setOnClickListener(this);


    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view, getAdapterPosition(),false);
    }
}
