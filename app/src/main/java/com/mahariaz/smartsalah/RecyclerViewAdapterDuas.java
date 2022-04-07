package com.mahariaz.smartsalah;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterDuas extends RecyclerView.Adapter<RecyclerViewAdapterDuas.MyViewHolder> {
    Context context;
    private RecyclerViewAdapterDuas.OnTileListner tonTileListner;
    public RecyclerViewAdapterDuas(ArrayList<String> duaTopic,ArrayList<Integer>  tileColor, ArrayList<Uri> uri_img ,Context context,OnTileListner onTileListner) {

        this.img_uri = uri_img;
        this.duaTopic=duaTopic;
        this.context=context;
        this.tonTileListner=onTileListner;
        this.tileColor=tileColor;

    }

    ArrayList<String>duaTopic;
    ArrayList<Integer>tileColor;
    ArrayList<Uri>img_uri;
    @NonNull
    @Override
    public RecyclerViewAdapterDuas.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.duatile_view,parent,false);
        RecyclerViewAdapterDuas.MyViewHolder myViewHolder=new RecyclerViewAdapterDuas.MyViewHolder(view,tonTileListner);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterDuas.MyViewHolder holder, int position) {
        holder.topic.setText(duaTopic.get(position));
        holder.topicPic.setImageURI(img_uri.get(position));
        holder.relativeLayoutColor.setBackgroundColor(tileColor.get(position));

    }


    @Override
    public int getItemCount() {
        return duaTopic.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView topicPic;
        TextView topic;
        RelativeLayout relativeLayoutColor;
        RecyclerViewAdapterDuas.OnTileListner onTileListner;

        public MyViewHolder(@NonNull  View itemView, OnTileListner onTileListner) {
            super(itemView);
            topic=itemView.findViewById(R.id.topic);
            topicPic=itemView.findViewById(R.id.topicPic);
            relativeLayoutColor=itemView.findViewById(R.id.tileColor);
            this.onTileListner=onTileListner;
            relativeLayoutColor.setOnClickListener(this);

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
