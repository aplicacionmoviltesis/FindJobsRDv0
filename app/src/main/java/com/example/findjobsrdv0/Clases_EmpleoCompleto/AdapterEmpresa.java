package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.Adaptadores_Empleador.Empleadores;
import com.example.findjobsrdv0.GeneralesApp.ItemClickListener;
import com.example.findjobsrdv0.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterEmpresa extends RecyclerView.Adapter {

    ArrayList<Empleadores> mDatasetEmpleadores;
    Context context;

    private TextView NombreEAplico;
    private TextView RncEAplico;
    private TextView PaginaWebEAplico;
    private TextView TelefonoEAplico;
    private TextView DireccionEAplico;
    private TextView CorreoEAplico;

    public ImageView ImagenEAplico;
    private Button VerificacionEAplico;

    //private TextView FechaEAplico;
    //private TextView ProvinciaEAplico;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public AdapterEmpresa(Context context, ArrayList<Empleadores> mDatasetEmpleadores) {
        this.context = context;
        this.mDatasetEmpleadores = mDatasetEmpleadores;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_mostrar_empresa,parent,false);
        AdapterEmpresa.EmpresaViewHolder empresaViewHolder = new AdapterEmpresa.EmpresaViewHolder(view);
        return empresaViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AdapterEmpresa.EmpresaViewHolder)holder).onBind(position);
    }

    @Override
    public int getItemCount() {
        if (mDatasetEmpleadores ==null){
            return 0;
        }else {
            //mDatasetEmpleadores.clear();

            return mDatasetEmpleadores.size();
        }
    }

    class EmpresaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



        public EmpresaViewHolder(View itemView) {
            super(itemView);

            NombreEAplico = (TextView)itemView.findViewById(R.id.CardNombreEmpresaAplico);
            CorreoEAplico = (TextView)itemView.findViewById(R.id.CorreoEmpresa);
            DireccionEAplico = (TextView)itemView.findViewById(R.id.CardDireccionEmpresa);
            TelefonoEAplico = (TextView)itemView.findViewById(R.id.CardTelefonoEmpresa);
            PaginaWebEAplico = (TextView)itemView.findViewById(R.id.CardPaginaWeb);

            VerificacionEAplico = (Button) itemView.findViewById(R.id.CardBtnVerificacionEmpresa);
            ImagenEAplico = (ImageView) itemView.findViewById(R.id.CardImageEmpresa);

            itemView.setOnClickListener(this);

        }

        public void onBind(int position){

            String NombreEmpresa = mDatasetEmpleadores.get(position).getsNombreEmpleador();
            String CorreoEmpresa = mDatasetEmpleadores.get(position).getsCorreoEmpleador();
            String DireccionEmpresa = mDatasetEmpleadores.get(position).getsDireccionEmpleador();
            String TelefonoEmpresa = mDatasetEmpleadores.get(position).getsTelefonoEmpleador();
            String PaginaWebEmpresa = mDatasetEmpleadores.get(position).getsPaginaWebEmpleador();

            Boolean VerificacionEmpresa = mDatasetEmpleadores.get(position).getsVerificacionEmpleador();
            String ImagenEmpresa = mDatasetEmpleadores.get(position).getsImagenEmpleador();


            NombreEAplico.setText(NombreEmpresa);
            CorreoEAplico.setText(CorreoEmpresa);
            DireccionEAplico.setText(DireccionEmpresa);
            TelefonoEAplico.setText(TelefonoEmpresa);
            PaginaWebEAplico.setText(PaginaWebEmpresa);

            Picasso.get().load(ImagenEmpresa).into(ImagenEAplico);

            if (VerificacionEmpresa != null) {
                if (VerificacionEmpresa == true) {
                    VerificacionEAplico.setVisibility(View.VISIBLE);
                }
                if (VerificacionEmpresa == false) {
                    VerificacionEAplico.setVisibility(View.INVISIBLE);
                }
            }


        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false );

        }
    }
}
