package com.example.comp4342_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.comp4342_project.cart.roomdatabase.Cart;

public class AddTocartActivity extends AppCompatActivity {
    private ImageView primage;
    private TextView price;
    Button addtocart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tocart);
        primage=(ImageView)findViewById(R.id.primage);
        price=(TextView)findViewById(R.id.txtprprice);

        Intent intent=getIntent();
        final String imageurl=intent.getStringExtra("imageurl");
        final String prname=intent.getStringExtra("prname");
        final String prprice=intent.getStringExtra("prprice");
        final int id=intent.getIntExtra("id",0);
        Glide.with(this).load(imageurl).into(primage);
        price.setText("$"+prprice);

        addtocart=(Button)findViewById(R.id.addtocartbtn);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart=new Cart();
                cart.setId(id);
                cart.setImageid(imageurl);
                cart.setName(prname);
                cart.setPrice(prprice);
               if (AllClothes.myDatabase.cartDao().isAddToCart(id)!=1){
                   AllClothes.myDatabase.cartDao().addToCart(cart);
                   Toast.makeText(com.example.comp4342_project.AddTocartActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(com.example.comp4342_project.AddTocartActivity.this, "You are Already added to cart!", Toast.LENGTH_SHORT).show();

               }
            }
        });

    }
}
