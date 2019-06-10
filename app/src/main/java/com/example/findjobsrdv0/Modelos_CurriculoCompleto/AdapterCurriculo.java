package com.example.findjobsrdv0.Modelos_CurriculoCompleto;

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

public class AdapterCurriculo extends RecyclerView.Adapter{

    ArrayList<Curriculos> mDatasetCurriculo;
    Context context;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public AdapterCurriculo(Context context, ArrayList<Curriculos> mDatasetCurriculo) {
        this.context = context;
        this.mDatasetCurriculo = mDatasetCurriculo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_vista_curriculo,parent,false);
        AdapterCurriculo.CurriculoViewHolder curriculoViewHolder = new AdapterCurriculo.CurriculoViewHolder(view);
        return curriculoViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AdapterCurriculo.CurriculoViewHolder)holder).onBind(position);
    }

    @Override
    public int getItemCount() {
        if (mDatasetCurriculo ==null){
            return 0;
        }else {
            return mDatasetCurriculo.size();
        }
    }

    class CurriculoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView txtNombre,  txtCedula, txtProvincia, txtDireccion, txtGradoMayor, txtEstadoActual;
        private ImageView imagen;

        public CurriculoViewHolder(View itemView) {
            super(itemView);

            imagen = (ImageView) itemView.findViewById( R.id.CardImageArea );
            txtNombre = (TextView)itemView.findViewById( R.id.textViewNombreC );
            txtCedula = (TextView) itemView.findViewById( R.id.textViewCedulaC);
            txtDireccion = (TextView) itemView.findViewById( R.id.textViewDireccionC);
            txtEstadoActual = (TextView) itemView.findViewById( R.id.textViewEstadoActualCurr);
            txtGradoMayor = (TextView) itemView.findViewById( R.id.textViewMaestriaC);
            txtProvincia = (TextView) itemView.findViewById( R.id.textViewProvinciaC);

            //itemView.setOnClickListener(this);
            int p = getAdapterPosition();
            itemView.setOnClickListener(this);

        }

        public void onBind(int position){

            String ImagenCurriculo = mDatasetCurriculo.get(position).getsImagenC();
            String NombreCurriculo = mDatasetCurriculo.get(position).getsApellidoC();
            String CedulaCurriculo = mDatasetCurriculo.get(position).getsCedulaC();
            String DireccionCurriculo = mDatasetCurriculo.get(position).getsDireccionC();
            String EstadoActualCurriculo = mDatasetCurriculo.get(position).getsEstadoActualC();
            String GradoMayorCurriculo = mDatasetCurriculo.get(position).getsGradoMayorC();
            String ProvinciaCurriculo = mDatasetCurriculo.get(position).getsProvinciaC();


            txtNombre.setText(NombreCurriculo);
            txtCedula.setText(CedulaCurriculo);
            txtDireccion.setText(DireccionCurriculo);
            txtEstadoActual.setText(EstadoActualCurriculo);
            txtGradoMayor.setText(GradoMayorCurriculo);
            txtProvincia.setText(ProvinciaCurriculo);
            Picasso.get().load(ImagenCurriculo).into(imagen);

        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false );

        }
    }


}
