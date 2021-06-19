package com.example.lvtn_babershop.Model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lvtn_babershop.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    public Context context;
    public List<product> productList;

    public ProductAdapter(Context context, List<product> productList) {
        this.context = context;
        this.productList = productList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNameProduct, txtInfoProduct,  txtPriceProduct;
        ImageView imgProduct;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPriceProduct = itemView.findViewById(R.id.txtPrice);
            txtInfoProduct = itemView.findViewById(R.id.txtInfoProduct);
            txtNameProduct = itemView.findViewById(R.id.txtNameProduct);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }


    @NonNull
    @NotNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.service_product, parent,false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductAdapter.MyViewHolder holder, int position) {

      product pro =   productList.get(position);
      holder.txtNameProduct.setText(pro.getNameProduct());
      holder.txtInfoProduct.setText(pro.getInfoProduct());
      holder.txtPriceProduct.setText(pro.getPrice());
      Picasso.get().load(pro.getImage()).into(holder.imgProduct);

    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
}
