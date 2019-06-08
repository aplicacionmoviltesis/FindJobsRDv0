package com.example.findjobsrdv0.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.findjobsrdv0.R;
import com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos.Curriculos;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {
    ArrayList<Curriculos> mDataset;
    Context context;
//    ArrayList<Minute> mDataset;

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

            String minute = mDataset.get(position).getNombre();
            titleTextView.setText(minute);
//            rainProbabilityTextView.setText(minute.getRainProbability());
        }
    }

//    @Override
//    public View getView(int position, View view, ViewGroup parent) {
//
//        ViewHolder viewHolder;
//
//
//        if (view==null){
//
//            view = LayoutInflater.from(context).inflate(R.layout.cardview_vista_curriculo,parent,false);
//            Log.d(TAG,"Crendo vista desde 0");
//            viewHolder=new ViewHolder();
//            viewHolder.dayTitle=(TextView) view.findViewById(R.id.textViewNombreC);
////            viewHolder.dayDescription= (TextView) view.findViewById(R.id.dailyListDescription);
////            viewHolder.dayProbability=(TextView) view.findViewById(R.id.dailyListProbability);
//
//            view.setTag(viewHolder);
//        }else {
//            viewHolder = (ViewHolder) view.getTag();
//        }
//
////        Day day= mDataSet.get(position);
//        viewHolder.dayTitle.setText(this.mDataset.get(position));
////        viewHolder.dayDescription.setText(day.getWeatherDescription());
////        viewHolder.dayProbability.setText(day.getRainProbability());
//
//        return view;
//    }

}