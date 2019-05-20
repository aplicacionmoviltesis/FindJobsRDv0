package com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleFormacionAcademica.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.R;

public class DetalleFormAcadViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtNivelPrimario,  txtNivelSecundario, txtNivelSuperior, txtCarrera;

    private ItemClickListener itemClickListener;

    public DetalleFormAcadViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
        super( itemView );
        this.itemClickListener = itemClickListener;
    }

    public DetalleFormAcadViewHolder(@NonNull View itemView) {
        super( itemView );

        txtNivelPrimario = (TextView)itemView.findViewById( R.id.xmlTVprimerNivelDetalleFormAcad );
        txtNivelSecundario = (TextView)itemView.findViewById( R.id.xmlTVSegundoNivelDetalleFormAcad );
        txtNivelSuperior = (TextView)itemView.findViewById( R.id.xmlTVNivelSuperiorDetalleFormAcad );
        txtCarrera = (TextView)itemView.findViewById( R.id.xmlTVCarreraDetalleFormAcad );

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
