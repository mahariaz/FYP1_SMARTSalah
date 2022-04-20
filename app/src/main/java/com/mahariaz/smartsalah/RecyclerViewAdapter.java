package com.mahariaz.smartsalah;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context context;
    private OnTileListner tonTileListner;
    public RecyclerViewAdapter(ArrayList<Uri> statusIcon, ArrayList<String> complete, ArrayList<String> correct,ArrayList<String> salahName, Context context,OnTileListner onTileListner) {

        this.complete=complete;
        this.correct=correct;
        this.salahName=salahName;
        this.statusIcon = statusIcon;
        this.context=context;
        this.tonTileListner=onTileListner;

    }

    ArrayList<String>complete;
    ArrayList<String>correct;
    ArrayList<String>salahName;
    ArrayList<Uri>statusIcon;


    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.box_view,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view,tonTileListner);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.salahNameTv.setText(salahName.get(position));
        holder.completeTv.setText(complete.get(position));
        holder.correctTv.setText(correct.get(position));
        holder.statusIconIv.setImageURI(statusIcon.get(position));
    }

    @Override
    public int getItemCount() {
        return salahName.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView statusIconIv;
        TextView salahNameTv;
        TextView completeTv;
        TextView correctTv;
        CardView salahCv;
        OnTileListner onTileListner;

        public MyViewHolder(@NonNull  View itemView,OnTileListner onTileListner) {
            super(itemView);
            salahNameTv=itemView.findViewById(R.id.salahNameTv);
            completeTv=itemView.findViewById(R.id.completeTv);
            correctTv=itemView.findViewById(R.id.correctTv);
            statusIconIv=itemView.findViewById(R.id.statusIconIv);
            salahCv=itemView.findViewById(R.id.salahCv);
            this.onTileListner=onTileListner;
            salahCv.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onTileListner.onTileClick(getAdapterPosition());

        }
    }
    public interface OnTileListner{
        void onTileClick(int position);
    }
}
