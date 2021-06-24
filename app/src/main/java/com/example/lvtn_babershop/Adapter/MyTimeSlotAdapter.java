package com.example.lvtn_babershop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lvtn_babershop.Comon.Common;
import com.example.lvtn_babershop.Model.TimeSlot;
import com.example.lvtn_babershop.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyTimeSlotAdapter extends RecyclerView.Adapter<MyTimeSlotAdapter.MyViewHolder> {
    Context context;
    List<TimeSlot> timeSlotList;

    public MyTimeSlotAdapter(Context context) {
        this.context = context;
        this.timeSlotList = new ArrayList<>();
    }

    public MyTimeSlotAdapter(Context context, List<TimeSlot> timeSlotList) {
        this.context = context;
        this.timeSlotList = timeSlotList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_time_slot,parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.txtTimeSlot.setText(new StringBuilder(Common.convertTimeSlotToString(position)).toString());
        if (timeSlotList.size() == 0)
        {
            holder.cardTimeSlot.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
            holder.txtTimeSlotDescription.setText("Available");
            holder.txtTimeSlotDescription.setTextColor(context.getResources()
            .getColor(android.R.color.black));
            holder.txtTimeSlot.setTextColor(context.getResources().getColor(android.R.color.black));

        }
        else //nếu có vị trí đã đầy  (booked)
        {
            for(TimeSlot slotValue:timeSlotList)

            {
                //Lặp lại tất cả các khoangr thời gian từ máy chủ và đặt màu khác nhau
                int slot = Integer.parseInt(slotValue.getSlot().toString());
                if (slot == position)
                {
                    holder.cardTimeSlot.setCardBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));

                    holder.txtTimeSlotDescription.setText("Full");
                    holder.txtTimeSlotDescription.setTextColor(context.getResources()
                            .getColor(android.R.color.white));
                    holder.txtTimeSlot.setTextColor(context.getResources().getColor(android.R.color.white));

                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return Common.TIME_SLOT_TOTAL;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtTimeSlot, txtTimeSlotDescription;
        CardView cardTimeSlot;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cardTimeSlot = itemView.findViewById(R.id.card_time_slot);
            txtTimeSlot = itemView.findViewById(R.id.txtTimeSlot);
            txtTimeSlotDescription = itemView.findViewById(R.id.txtTimeSlotDescription);

        }
    }
}
