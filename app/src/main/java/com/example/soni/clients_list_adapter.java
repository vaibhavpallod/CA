package com.example.soni;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class clients_list_adapter extends RecyclerView.Adapter<clients_list_adapter.MyViewHolder> {
   Context context;
    View.OnClickListener mlistner;
    List<Client_return> clientlist;


    public clients_list_adapter(Context context, List<Client_return> clientlist) {
        this.context = context;
        this.clientlist = clientlist;
    }



    public void setOnItemClickListner(View.OnClickListener listner)
    {
        mlistner=listner;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_layout_clients,parent,false);
        view.setOnClickListener(mlistner);
        return new MyViewHolder(view,mlistner);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    Client_return client_return = clientlist.get(position);
    holder.nameofclient.setText(client_return.getName());

    }

    @Override
    public int getItemCount() {
        return clientlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
      //  private static View.OnClickListener onClickListener;

        TextView nameofclient;
        public MyViewHolder(@NonNull View itemView, View.OnClickListener mlistner) {
            super(itemView);
            nameofclient = itemView.findViewById(R.id.clientsnameinrecyclerview);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener((View.OnLongClickListener) this);

        }

        @Override
        public void onClick(View v) {

            Intent intent =  new Intent(v.getContext(),client_details.class);
            v.getContext().startActivity(intent);

            Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show();

        }

        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(context,"Long Clicked",Toast.LENGTH_SHORT).show();

            return false;
        }
    }
}
