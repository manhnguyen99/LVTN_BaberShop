package com.example.lvtn_babershop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lvtn_babershop.Comon.Common;
import com.example.lvtn_babershop.Interface.IRecyclerItemSelectedListener;
import com.example.lvtn_babershop.Model.Baber;
import com.example.lvtn_babershop.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyBaberAdapter extends RecyclerView.Adapter<MyBaberAdapter.MyViewHolder> {
    Context context;
    List<Baber> baberList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public MyBaberAdapter(Context context, List<Baber> baberList) {
        this.context = context;
        this.baberList = baberList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);

    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_baber, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.txtBaberName.setText(baberList.get(position).getNameBaber());
        holder.ratingBar.setRating(baberList.get(position).getRating());

        if(!cardViewList.contains(holder.cardBarber));
        cardViewList.add(holder.cardBarber);

        holder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                // dat background cho tat ca nhung item chua duoc chon
                for (CardView cardView : cardViewList) {
                    cardView.setCardBackgroundColor(context.getResources()
                            .getColor(android.R.color.white));
                }

                //dat background cho item da duoc chon
                holder.cardBarber.setCardBackgroundColor(
                        context.getResources()
                .getColor(android.R.color.holo_orange_dark)
                );

                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_BABER_SELECTED, baberList.get(pos));
                intent.putExtra(Common.KEY_STEP,2);
                localBroadcastManager.sendBroadcast(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return baberList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtBaberName;
        RatingBar ratingBar;
        CardView cardBarber;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {

            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtBaberName  = itemView.findViewById(R.id.txtBaberName);
            ratingBar  = itemView.findViewById(R.id.rtb_baber);
            cardBarber = itemView.findViewById(R.id.card_baber);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            iRecyclerItemSelectedListener.onItemSelectedListener(v, getAdapterPosition());
        }
    }
}
