package com.example.findjobsrdv0.Adaptadores_Administrador;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;

public class ViewHolderUsuarios extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvNombreUser, tvApellidoUser, tvEmailUser, tvTelefonoUser;

    private ItemClickListener itemClickListener;

    public ViewHolderUsuarios(@NonNull View itemView, ItemClickListener itemClickListener) {
        super(itemView);
        this.itemClickListener = itemClickListener;
    }

    public ViewHolderUsuarios(@NonNull View itemView) {
        super(itemView);

        tvNombreUser = (TextView) itemView.findViewById(R.id.xmlTVNombreUser);
        tvApellidoUser = (TextView) itemView.findViewById(R.id.xmlTVApellidoUser);
        tvEmailUser = (TextView) itemView.findViewById(R.id.xmlTVEmailUser);
        tvTelefonoUser = (TextView) itemView.findViewById(R.id.xmlTVTelefonoUser);

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
