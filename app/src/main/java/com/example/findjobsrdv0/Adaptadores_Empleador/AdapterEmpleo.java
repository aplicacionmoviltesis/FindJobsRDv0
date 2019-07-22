package com.example.findjobsrdv0.Adaptadores_Empleador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterEmpleo extends RecyclerView.Adapter{

    public ArrayList<Empleos> mDatasetEmpleo;
    Context context;

    private TextView NombreEmpleoCardViewAplicado;
    private TextView NombreEmpresaCardViewAplicado;
    private TextView ProvinciaCardViewAplicado;
    private TextView AreaCardViewAplicado;
    private TextView EstadoCardViewAplicado;
    private TextView FechaPublicacionCardViewAplicado;

    public ImageView imagenEmpleoCardViewAplicado;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public AdapterEmpleo(Context context, ArrayList<Empleos> mDatasetEmpleo) {
        this.context = context;
        this.mDatasetEmpleo = mDatasetEmpleo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_mostrar_empleo,parent,false);
        AdapterEmpleo.EmpleoViewHolder empleoViewHolder = new AdapterEmpleo.EmpleoViewHolder(view);
        return empleoViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AdapterEmpleo.EmpleoViewHolder)holder).onBind(position);
    }

    @Override
    public int getItemCount() {
        if (mDatasetEmpleo ==null){
            return 0;
        }else {
            return mDatasetEmpleo.size();
        }
    }

    class EmpleoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



        public EmpleoViewHolder(View itemView) {
            super(itemView);

            NombreEmpleoCardViewAplicado = (TextView)itemView.findViewById(R.id.CardNombreempleo);
            NombreEmpresaCardViewAplicado = (TextView)itemView.findViewById(R.id.CardNombreempresa);
            ProvinciaCardViewAplicado = (TextView)itemView.findViewById(R.id.CardProvincia);
            AreaCardViewAplicado = (TextView)itemView.findViewById(R.id.Cardarea);
            EstadoCardViewAplicado = (TextView)itemView.findViewById(R.id.CardEstado);
            FechaPublicacionCardViewAplicado = (TextView)itemView.findViewById(R.id.CardFecha);
            imagenEmpleoCardViewAplicado = (ImageView) itemView.findViewById(R.id.CardImageArea);

            itemView.setOnClickListener(this);



        }

        public void onBind(int position){

            String NombreEmpleo = mDatasetEmpleo.get(position).getsNombreEmpleoE();
            String NombreEmpresa = mDatasetEmpleo.get(position).getsNombreEmpresaE();
            String ProvinciaEmpleo = mDatasetEmpleo.get(position).getsProvinciaE();
            String AreaEmpleo = mDatasetEmpleo.get(position).getsAreaE();
            String EstadoEmpleo = mDatasetEmpleo.get(position).getsEstadoEmpleoE();
            String FechaEmpleo = mDatasetEmpleo.get(position).getsFechaPublicacionE();
            String ImagenEmpleo = mDatasetEmpleo.get(position).getsImagenEmpleoE();


            NombreEmpleoCardViewAplicado.setText(NombreEmpleo);
            NombreEmpresaCardViewAplicado.setText(NombreEmpresa);
            ProvinciaCardViewAplicado.setText(ProvinciaEmpleo);
            AreaCardViewAplicado.setText(AreaEmpleo);
            EstadoCardViewAplicado.setText(EstadoEmpleo);
            FechaPublicacionCardViewAplicado.setText(FechaEmpleo);
            Picasso.get().load(ImagenEmpleo).into(imagenEmpleoCardViewAplicado);



        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false );

        }
    }

}
