package com.openclassrooms.connexion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Client> clientdata;

    public ClientAdapter(Context c, ArrayList<Client> clientdata) {
        this.context =c;
        this.clientdata = clientdata;
    }

    @NonNull
    @Override
    public ClientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_clients,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Client client = this.clientdata.get(position);

        holder.tvNom.setText(client.getNom());
        holder.tvPrenom.setText(client.getPrenom());
        holder.tvAdresse.setText(client.getAdresse());
    }

    @Override
    public int getItemCount() {
        return clientdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNom, tvPrenom, tvAdresse;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.textView11);
            tvPrenom = itemView.findViewById(R.id.textView13);
            tvAdresse = itemView.findViewById(R.id.textView15);
        }
    }
}
