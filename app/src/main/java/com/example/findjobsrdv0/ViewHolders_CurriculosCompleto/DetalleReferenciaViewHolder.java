package com.example.findjobsrdv0.ViewHolders_CurriculosCompleto;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;

public class DetalleReferenciaViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtNombre,  txtCargoOcupado, txtInstitucion, txtTelefono;

    private ItemClickListener itemClickListener;

    public DetalleReferenciaViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
        super( itemView );
        this.itemClickListener = itemClickListener;
    }

    public DetalleReferenciaViewHolder(@NonNull View itemView) {
        super( itemView );

        txtNombre = (TextView)itemView.findViewById( R.id.xmlTVNombreDetalleReferencia );
        txtCargoOcupado = (TextView)itemView.findViewById( R.id.xmlTVCargoOcupadoDetalleReferencia );
        txtInstitucion = (TextView)itemView.findViewById( R.id.xmlTVInstitucionDetalleReferencia );
        txtTelefono = (TextView)itemView.findViewById( R.id.xmlTVTelefonoDetalleReferencia );

        itemView.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick( view,getAdapterPosition(),false );
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
