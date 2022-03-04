package com.example.comp4342_project.cart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.comp4342_project.AddTocartActivity;
import com.example.comp4342_project.R;
import com.example.comp4342_project.cart.model.MyProductData;

import java.util.List;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.ViewHolder> {
    private List<MyProductData>myProductData;
    private Context context;

    public MyProductAdapter(List<MyProductData> myProductData, Context context) {
        this.myProductData = myProductData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Glide.with(context).load(myProductData.get(position).primage).into(holder.primage);
        holder.prprice.setText("$"+myProductData.get(position).getPrprice());
        holder.primage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AddTocartActivity.class);
                String imageurl=myProductData.get(position).primage;
                String prprice=myProductData.get(position).prprice;
                intent.putExtra("imageurl",imageurl);
                intent.putExtra("prname",myProductData.get(position).prname);
                intent.putExtra("prprice",myProductData.get(position).getPrprice());
                intent.putExtra("id",myProductData.get(position).id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myProductData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView primage;
        private TextView prprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            primage=(ImageView)itemView.findViewById(R.id.primage);
            prprice=(TextView)itemView.findViewById(R.id.txtprprice);
        }
    }
}
