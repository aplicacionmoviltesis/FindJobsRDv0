package com.example.findjobsrdv0.ActualizarFormacionAcad.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.R;

public class ActualizarFormAcadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtNivelPrimarioAct,  txtNivelSecundarioAct, txtNivelSuperiorAct, txtCarreraAct;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ActualizarFormAcadViewHolder(@NonNull View itemView) {
        super( itemView );

        txtNivelPrimarioAct = (TextView)itemView.findViewById( R.id.xmlTVprimerNivelDetalleFormAcadActualizar );
        txtNivelSecundarioAct = (TextView)itemView.findViewById( R.id.xmlTVSegundoNivelDetalleFormAcadActualizar );
        txtNivelSuperiorAct = (TextView)itemView.findViewById( R.id.xmlTVNivelSuperiorDetalleFormAcadActualizar );
        txtCarreraAct = (TextView)itemView.findViewById( R.id.xmlTVCarreraDetalleFormAcadActualizar );

        itemView.setOnClickListener( this );

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick( view,getAdapterPosition(),false );

    }
}
