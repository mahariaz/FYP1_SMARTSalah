package com.mahariaz.smartsalah;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context context;
    private OnTileListner tonTileListner;
    public RecyclerViewAdapter(ArrayList<Uri> img_uri, ArrayList<String> head, Context context,OnTileListner onTileListner) {

        this.img_uri = img_uri;
        this.head=head;
        this.context=context;
        this.tonTileListner=onTileListner;

    }

    ArrayList<String>head;
    ArrayList<Uri>img_uri;


    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.box_view,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view,tonTileListner);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.heading_view.setText(head.get(position));
        holder.small_img_view.setImageURI(img_uri.get(position));
    }

    @Override
    public int getItemCount() {
        return head.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView small_img_view;
        TextView heading_view;
        OnTileListner onTileListner;

        public MyViewHolder(@NonNull  View itemView,OnTileListner onTileListner) {
            super(itemView);
            heading_view=itemView.findViewById(R.id.heading);
            small_img_view=itemView.findViewById(R.id.small_iv);
            this.onTileListner=onTileListner;
            heading_view.setOnClickListener(this);

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
