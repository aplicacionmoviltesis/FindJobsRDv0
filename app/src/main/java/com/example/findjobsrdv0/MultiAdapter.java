package com.example.findjobsrdv0;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MultiViewHolder> {

    private Context context;
    public ArrayList<EmpleosFav> empleosFavs;

    public MultiAdapter(Context context, ArrayList<EmpleosFav> empleosFavs) {
        this.context = context;
        this.empleosFavs = empleosFavs;
    }

    public void setEmpleosFavs(ArrayList<EmpleosFav> empleosFavs) {
        this.empleosFavs = new ArrayList<>();
        this.empleosFavs = empleosFavs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_mostrar_empleo_seleccionado, viewGroup, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(empleosFavs.get(position));
    }

    @Override
    public int getItemCount() {
        return empleosFavs.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {

        private TextView cNombreEmpleo;
        private TextView cNombreEmpresa;
        private TextView cProvinciaEmpleo;
        private TextView cAreaEmpleo;
        private TextView cEstado;
        private TextView cFecha;

        private ImageView imageView;
        private ImageView ImageViewArea;


        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            cNombreEmpleo = itemView.findViewById(R.id.CardNombreempleoSelect);
            cNombreEmpresa = itemView.findViewById(R.id.CardNombreEmpresaSelect);

            cProvinciaEmpleo = itemView.findViewById(R.id.CardProvinciaSelect);
            cAreaEmpleo = itemView.findViewById(R.id.CardAreaSelect);
            cEstado = itemView.findViewById(R.id.CardEstadoSelect);
            cFecha = itemView.findViewById(R.id.CardFechaSelect);

            imageView = itemView.findViewById(R.id.imageViewSelect);
            ImageViewArea = itemView.findViewById(R.id.CardImageAreaSelect);

        }

        void bind(final EmpleosFav empleosFav) {
            imageView.setVisibility(empleosFav.isChecked() ? View.VISIBLE : View.GONE);
            cNombreEmpleo.setText(empleosFav.getsNombreEmpleoE());
            cNombreEmpresa.setText(empleosFav.getsNombreEmpresaE());
            cProvinciaEmpleo.setText(empleosFav.getsProvinciaE());
            cAreaEmpleo.setText(empleosFav.getsAreaE());
            cEstado.setText(empleosFav.getsEstadoEmpleoE());
            cFecha.setText(empleosFav.getsFechaPublicacionE());

            Picasso.get().load(empleosFav.getsImagenEmpleoE()).into(ImageViewArea);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String hey="klk";
                    Log.d("hey",hey);
                    //Toast.makeText(MultiAdapter.this, "el click funciona", Toast.LENGTH_SHORT).show();

                    empleosFav.setChecked(!empleosFav.isChecked());
                    imageView.setVisibility(empleosFav.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public ArrayList<EmpleosFav> getAll() {
        return empleosFavs;
    }

    public ArrayList<EmpleosFav> getSelected() {
        ArrayList<EmpleosFav> selected = new ArrayList<>();
        for (int i = 0; i < empleosFavs.size(); i++) {
            if (empleosFavs.get(i).isChecked()) {
                selected.add(empleosFavs.get(i));
            }
        }
        return selected;
    }
}
