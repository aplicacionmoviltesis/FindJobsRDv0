package com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewHolderCompCurriculo extends RecyclerView.Adapter<ViewHolderCompCurriculo.MultiViewHolder> {

    private Context context;
    public ArrayList<AdapterCompararCurriculo> curriculoFavs;
    private ViewGroup viewGroup;

    public ViewHolderCompCurriculo(Context context, ArrayList<AdapterCompararCurriculo> curriculoFavs) {
        this.context = context;
        this.curriculoFavs = curriculoFavs;
    }

    public void setCurriculoFavs(ArrayList<AdapterCompararCurriculo> curriculoFavs) {
        this.curriculoFavs = new ArrayList<>();
        this.curriculoFavs = curriculoFavs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_mostrar_curriculo_seleccionado, viewGroup, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(curriculoFavs.get(position));
    }

    @Override
    public int getItemCount() {
        return curriculoFavs.size();
    }

    public class MultiViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNombreComp, txtCedulaComp, txtProvinciaComp, txtDireccionComp, txtGradoMayorComp, txtEstadoActualComp;
        private ImageView imagenComp, imageView;

        public MultiViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewSelectComp);
            imagenComp = itemView.findViewById(R.id.CardImageAreaComp);
            txtNombreComp = itemView.findViewById(R.id.textViewNombreCComp);
            txtCedulaComp = itemView.findViewById(R.id.textViewCedulaCComp);
            txtDireccionComp = itemView.findViewById(R.id.textViewDireccionCComp);
            txtEstadoActualComp = itemView.findViewById(R.id.textViewEstadoActualCurrComp);
            txtGradoMayorComp = itemView.findViewById(R.id.textViewMaestriaCComp);
            txtProvinciaComp = itemView.findViewById(R.id.textViewProvinciaCComp);
        }

        void bind(final AdapterCompararCurriculo curriculoFavs) {
            imageView.setVisibility(curriculoFavs.isChecked() ? View.VISIBLE : View.GONE);
            txtNombreComp.setText(curriculoFavs.getsNombreC());
            txtCedulaComp.setText(curriculoFavs.getsCedulaC());
            txtDireccionComp.setText(curriculoFavs.getsDireccionC());
            txtEstadoActualComp.setText(curriculoFavs.getsEstadoActualC());
            txtGradoMayorComp.setText(curriculoFavs.getsGradoMayorC());
            txtProvinciaComp.setText(curriculoFavs.getsProvinciaC());

            Picasso.get().load(curriculoFavs.getsImagenC()).into(imagenComp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curriculoFavs.setChecked(!curriculoFavs.isChecked());
                    imageView.setVisibility(curriculoFavs.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public ArrayList<AdapterCompararCurriculo> getAll() {
        return curriculoFavs;
    }

    public ArrayList<AdapterCompararCurriculo> getSelected() {
        ArrayList<AdapterCompararCurriculo> selected = new ArrayList<>();
        for (int i = 0; i < curriculoFavs.size(); i++) {
            if (curriculoFavs.get(i).isChecked()) {
                selected.add(curriculoFavs.get(i));
            }
        }
        return selected;
    }
}
