package com.example.findjobsrdv0.ViewHolders_CurriculosCompleto;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;

public class ReferenciasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtNombre, txtCargoOcupado, txtInstitucion, txtTelefono;

    private ItemClickListener itemClickListener;

    public ReferenciasViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
        super( itemView );
        this.itemClickListener = itemClickListener;
    }


    public ReferenciasViewHolder(@NonNull View itemView) {
        super( itemView );
        txtInstitucion = (TextView) itemView.findViewById( R.id.institucion );
        txtNombre = (TextView) itemView.findViewById( R.id.textViewTitle );
        txtTelefono = (TextView) itemView.findViewById( R.id.textViewShortDesc );
        txtCargoOcupado = (TextView) itemView.findViewById( R.id.cargo );

        itemView.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick( view, getAdapterPosition(), false );
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
