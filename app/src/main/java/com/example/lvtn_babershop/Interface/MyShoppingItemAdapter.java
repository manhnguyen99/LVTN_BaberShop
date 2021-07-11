package com.example.lvtn_babershop.Interface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lvtn_babershop.Comon.Common;
import com.example.lvtn_babershop.Model.ShoppingItem;
import com.example.lvtn_babershop.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class MyShoppingItemAdapter extends RecyclerView.Adapter<MyShoppingItemAdapter.MyViewHolder> {

    Context context;
    List<ShoppingItem> shoppingItemList;

    public MyShoppingItemAdapter(Context context, List<ShoppingItem> shoppingItemList) {
        this.context = context;
        this.shoppingItemList = shoppingItemList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_shopping_item,parent,false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Picasso.get().load(shoppingItemList.get(position).getImage()).into(holder.imgShoppingItem);
        holder.txtShoppingItemName.setText(Common.formartShoppingItemName(shoppingItemList.get(position).getName()));
        holder.txtShoppingItemPrice.setText(new StringBuilder("$").append(shoppingItemList.get(position).getPrice()));

    }

    @Override
    public int getItemCount() {
        return shoppingItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtShoppingItemName, txtShoppingItemPrice, txtAddToCart;
        ImageView imgShoppingItem;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtShoppingItemName = itemView.findViewById(R.id.txtNameShoppingItem);
            txtShoppingItemPrice =itemView.findViewById(R.id.txtPriceShoppingItem);
            txtAddToCart = itemView.findViewById(R.id.txtAddToCart);
            imgShoppingItem = itemView.findViewById(R.id.imgShoppingItem);
        }
    }
}
