package com.example.lvtn_babershop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lvtn_babershop.Model.Salon;
import com.example.lvtn_babershop.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MySalonAdapter extends RecyclerView.Adapter<MySalonAdapter.MyViewHolder> {

    Context context;
    List<Salon> salonList;

    public MySalonAdapter(Context context, List<Salon> salonList) {
        this.context = context;
        this.salonList = salonList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_salon, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.txtSalonName.setText(salonList.get(position).getNameSalon());
        holder.txtSalonAddress.setText(salonList.get(position).getAddressSalon());

    }

    @Override
    public int getItemCount() {
        return salonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView txtSalonName, txtSalonAddress;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtSalonName = itemView.findViewById(R.id.txtSalonName);
            txtSalonAddress = itemView.findViewById(R.id.txtSalonAddress);

        }
    }
}
