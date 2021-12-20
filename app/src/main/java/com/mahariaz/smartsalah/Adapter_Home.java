package com.mahariaz.smartsalah;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_Home extends RecyclerView.Adapter<Adapter_Home.ViewHolder> {
    private List<ModelClass> userList;
    public Adapter_Home(List<ModelClass>userlist){
        this.userList=userlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String n_username=userList.get(position).getNusername();
        String n_content=userList.get(position).getContent();
        String note_time=userList.get(position).getNotetime();
        String note_name=userList.get(position).getNotename();
        holder.setData(n_content,note_name,note_time,n_username);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), WritingNotes.class);
            i.putExtra("recipientName",holder.notename.getText().toString());
            i.putExtra("recipientStatus","online");
            holder.itemView.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView notename;
        private TextView notetime;
        private TextView notecontent;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notename=itemView.findViewById(R.id.notename);
            notetime=itemView.findViewById(R.id.time);
            notecontent=itemView.findViewById(R.id.short_note);

        }

        public void setData(String n_content, String note_name, String time,String uname) {
            notename.setText(note_name);
            notetime.setText(time);
            notecontent.setText(n_content);

        }
    }
}
