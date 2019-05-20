package com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleExperienciaLaboral.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.Modelo.ItemClickListener;
import com.example.findjobsrdv0.R;

public class DetalleExperienciaLaboralViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtNombreempres, txtCargoocupado, txtTelefono, txtFechaEntrada, txtFechaSalida;

    private ItemClickListener itemClickListener;

    public DetalleExperienciaLaboralViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
        super( itemView );
        this.itemClickListener = itemClickListener;
    }

    public DetalleExperienciaLaboralViewHolder(@NonNull View itemView) {
        super( itemView );

        txtNombreempres = (TextView) itemView.findViewById( R.id.xmlTVNombreEmpresaDetalleExpLab );
        txtCargoocupado = (TextView) itemView.findViewById( R.id.xmlTVCragoocupadoDetalleExpLab );
        txtTelefono = (TextView) itemView.findViewById( R.id.xmlTVTelefonoDetalleExpLab );
        txtFechaEntrada = (TextView) itemView.findViewById( R.id.xmlTVFechaEntradaDetalleExpLab );
        txtFechaSalida = (TextView) itemView.findViewById( R.id.xmlTVFechaSalidaDetalleExpLab );

        itemView.setOnClickListener( this );

    }

    @Override
    public void onClick(View view) {

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
