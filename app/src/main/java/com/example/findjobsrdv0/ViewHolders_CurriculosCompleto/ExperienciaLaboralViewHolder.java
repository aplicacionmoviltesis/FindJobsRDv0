package com.example.findjobsrdv0.ViewHolders_CurriculosCompleto;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.R;

public class ExperienciaLaboralViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtNombreEmpresa,  txtCargoOcupado, txtTelefono, txtFechaEntrada, txtFechaSalida;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ExperienciaLaboralViewHolder(@NonNull View itemView) {
        super( itemView );
        txtNombreEmpresa = (TextView)itemView.findViewById( R.id.textViewNombreEmpresa );
        txtCargoOcupado = (TextView)itemView.findViewById( R.id.textViewCargoOcupado );
        txtTelefono = (TextView)itemView.findViewById( R.id.textViewTelefono );
        txtFechaEntrada = (TextView)itemView.findViewById( R.id.textViewFechaEntrada );
        txtFechaSalida = (TextView)itemView.findViewById( R.id.textViewFechaSalida );

        itemView.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick( view,getAdapterPosition(),false );
    }
}
