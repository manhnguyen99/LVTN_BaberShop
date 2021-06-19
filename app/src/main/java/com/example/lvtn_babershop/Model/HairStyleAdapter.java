package com.example.lvtn_babershop.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lvtn_babershop.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HairStyleAdapter extends RecyclerView.Adapter<HairStyleAdapter.MyViewHolder> { //RecyclerView.Adapter uản lý và cập nhật dữ liệu hiển thị vào View trong phần  tử của recyclerview
    public Context context;
    public List<HairStyle> hairStyleList;

    public HairStyleAdapter(Context context, List<HairStyle> hairStyleList) {
        this.context = context;
        this.hairStyleList = hairStyleList;
    }

    @NonNull
    @NotNull
    @Override
    public HairStyleAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) { //tạo view mới cho RecyclerView.
        View view = LayoutInflater.from(context).inflate(R.layout.hair_style, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) { //gắn data và ánh xạ view.
        HairStyle hairStyle = hairStyleList.get(position);
        holder.txtInfoHair.setText(hairStyle.getHairInfo());
        holder.txtNameHair.setText(hairStyle.getHairName());
        Picasso.get().load(hairStyle.getHairImage()).into(holder.imgHair);

    }

    @Override
    public int getItemCount() {
        return hairStyleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtNameHair, txtInfoHair;
        ImageView imgHair;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtNameHair = itemView.findViewById(R.id.txtNameHair);
            txtInfoHair = itemView.findViewById(R.id.txtInfoHair);
            imgHair = itemView.findViewById(R.id.imgHair);
        }
    }
}
