package com.example.comp4342_project.cart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.comp4342_project.AllClothes;
import com.example.comp4342_project.R;
import com.example.comp4342_project.cart.roomdatabase.Cart;

import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {
    private List<Cart>carts;
    private Context context;

    public CartProductAdapter(List<Cart> carts, Context context) {
        this.carts = carts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.delete,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
              final Cart cart=carts.get(position);

        Glide.with(context).load(carts.get(position).getImageid()).into(holder.primage);
        holder.prprice.setText("$."+carts.get(position).getPrice());
        holder.deletbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carts.remove(position);
                notifyDataSetChanged();
                AllClothes.myDatabase.cartDao().deleteItem(cart.getId());
                int cartcount= AllClothes.myDatabase.cartDao().countCart();
                Intent intent=new Intent("mymsg");
                intent.putExtra("cartCountItems",cartcount);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView primage,deletbtn;
        private TextView prprice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            primage=(ImageView)itemView.findViewById(R.id.primage);
            deletbtn=(ImageView)itemView.findViewById(R.id.deletbtn);
            prprice=(TextView)itemView.findViewById(R.id.txtprprice);
        }
    }
}
