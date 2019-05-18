package com.example.findjobsrdv0.Vista_recycler_en_los_insert.recyclerOtrosEstudios.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.R;

public class OtrosEstudiosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtInstitucion,  txtAno, txtAreaoTema, txtTipoEstudio;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public OtrosEstudiosViewHolder(@NonNull View itemView) {
        super( itemView );
        txtInstitucion = (TextView)itemView.findViewById( R.id.textViewInstitucionOC );
        txtAno = (TextView)itemView.findViewById( R.id.textViewAnoOC );
        txtAreaoTema = (TextView)itemView.findViewById( R.id.textViewAreaoTemaOC );
        txtTipoEstudio = (TextView)itemView.findViewById( R.id.textViewNivelEstudioOC );

        itemView.setOnClickListener( this );

    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick( view,getAdapterPosition(),false );
    }
}
