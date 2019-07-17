package com.example.findjobsrdv0;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;

public class ViewHolderVerificacionEmpleador extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView TVNombreDocum, TVEstadoEmpleador, TVFecha;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public ViewHolderVerificacionEmpleador(@NonNull View itemView) {
        super( itemView );

        TVNombreDocum = (TextView) itemView.findViewById( R.id.xmlTVNombreDocumentoVerifEmp);
        TVEstadoEmpleador = (TextView) itemView.findViewById( R.id.xmlTVEstadoVerifEmp );
        TVFecha = (TextView) itemView.findViewById( R.id.xmlTVFechaVerifEmp );

        itemView.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick( view,getAdapterPosition(),false );
    }
}
