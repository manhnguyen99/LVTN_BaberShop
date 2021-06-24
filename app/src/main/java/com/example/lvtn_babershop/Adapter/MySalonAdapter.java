package com.example.lvtn_babershop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lvtn_babershop.Comon.Common;
import com.example.lvtn_babershop.Interface.IRecyclerItemSelectedListener;
import com.example.lvtn_babershop.Model.Salon;
import com.example.lvtn_babershop.R;

import org.jetbrains.annotations.NotNull;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

public class MySalonAdapter extends RecyclerView.Adapter<MySalonAdapter.MyViewHolder> {

    Context context;
    List<Salon> salonList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public MySalonAdapter(Context context, List<Salon> salonList) {
        this.context = context;
        this.salonList = salonList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
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
        if(!cardViewList.contains(holder.card_salon))
        {
            cardViewList.add(holder.card_salon);
            holder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
                @Override
                public void onItemSelectedListener(View view, int pos) {
                    //set white background for all card not be selected
                    for (CardView cardView:cardViewList)
                        cardView.setCardBackgroundColor(context.getResources()
                                .getColor(android.R.color.white));

                        holder.card_salon.setCardBackgroundColor(
                                context.getResources()
                        .getColor(android.R.color.holo_orange_dark)
                        );
                        // send Broadcast to tell Booking Activity enable Button next
                    Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                    intent.putExtra(Common.KEY_SALON_STORE, salonList.get(pos));
                    intent.putExtra(Common.KEY_STEP, 1);
                    localBroadcastManager.sendBroadcast(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return salonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtSalonName, txtSalonAddress;
        CardView card_salon;
        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;
        LocalBroadcastManager localBroadcastManager;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtSalonName = itemView.findViewById(R.id.txtSalonName);
            txtSalonAddress = itemView.findViewById(R.id.txtSalonAddress);
            card_salon = itemView.findViewById(R.id.card_salon);
            localBroadcastManager = LocalBroadcastManager.getInstance(context);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iRecyclerItemSelectedListener.onItemSelectedListener(v, getAdapterPosition());
        }
    }
}
