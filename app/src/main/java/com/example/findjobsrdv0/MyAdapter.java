package com.example.findjobsrdv0;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Curriculos;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {
    ArrayList<Curriculos> mDataset;
    Context context;

    public MyAdapter(Context context, ArrayList<Curriculos> mDataset) {
        this.context = context;
        this.mDataset = mDataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_vista_curriculo,parent,false);
        MinuteViewHolder minuteViewHolder = new MinuteViewHolder(view);
        return minuteViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MinuteViewHolder)holder).onBind(position);
    }

    @Override
    public int getItemCount() {
        if (mDataset ==null){
            return 0;
        }else {
            return mDataset.size();
        }
    }

    class MinuteViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
//        TextView rainProbabilityTextView;

        public MinuteViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.textViewNombreC);
//            rainProbabilityTextView = (TextView) itemView.findViewById(R.id.minutelyRainProbabilityTextView);
        }

        public void onBind(int position){

            String minute = mDataset.get(position).getsNombreC();
            titleTextView.setText(minute);
//            rainProbabilityTextView.setText(minute.getRainProbability());
        }
    }


}