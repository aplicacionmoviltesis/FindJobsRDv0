package com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;

public class DetalleOtrosEstudiosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtInstitucion, txtAno, txtAreaoTema, txtTipodeEstudio;

    private ItemClickListener itemClickListener;

    public DetalleOtrosEstudiosViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
        super(itemView);
        this.itemClickListener = itemClickListener;
    }

    public DetalleOtrosEstudiosViewHolder(@NonNull View itemView) {
        super(itemView);

        txtInstitucion = (TextView) itemView.findViewById(R.id.xmlTVInstitucionDetalleOtrosEstudios);
        txtAno = (TextView) itemView.findViewById(R.id.xmlTVanoDetalleOtrosEstudios);
        txtAreaoTema = (TextView) itemView.findViewById(R.id.xmlTVAreaoTemaDetalleOtrosEstudios);
        txtTipodeEstudio = (TextView) itemView.findViewById(R.id.xmlTVTipodeEstudioDetalleOtrosEstudios);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
