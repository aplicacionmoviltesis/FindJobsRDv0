package com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;

public class VistaCurriculoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txtNombre, txtCedula, txtProvincia, txtDireccion, txtGradoMayor, txtEstadoActual;
    public ImageView imagen;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public VistaCurriculoViewHolder(@NonNull View itemView) {
        super(itemView);

        imagen = (ImageView) itemView.findViewById(R.id.CardImageArea);
        txtNombre = (TextView) itemView.findViewById(R.id.textViewNombreC);
        txtCedula = (TextView) itemView.findViewById(R.id.textViewCedulaC);
        txtDireccion = (TextView) itemView.findViewById(R.id.textViewDireccionC);
        txtEstadoActual = (TextView) itemView.findViewById(R.id.textViewEstadoActualCurr);
        txtGradoMayor = (TextView) itemView.findViewById(R.id.textViewMaestriaC);
        txtProvincia = (TextView) itemView.findViewById(R.id.textViewProvinciaC);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
