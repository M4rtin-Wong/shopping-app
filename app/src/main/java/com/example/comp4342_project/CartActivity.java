package com.example.comp4342_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comp4342_project.cart.adapter.CartProductAdapter;
import com.example.comp4342_project.cart.roomdatabase.Cart;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//////////////////////////////////////////////////
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
///////
public class CartActivity extends AppCompatActivity {

    private TextView barTitle;
    private ImageView homeIcon;
    private ImageView userIcon;
    private ImageView cartIcon;
    private ImageView exitIcon;
    private ImageView searchIcon;
    private Button paynow;

    RecyclerView rv;
    CartProductAdapter cartProductAdapter;
    List<Cart>carts;

    public int countCartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        barTitle = (TextView)findViewById(R.id.toolbarTitle);
        homeIcon = (ImageView)findViewById(R.id.toolbarHome);
        userIcon = (ImageView)findViewById(R.id.toolbarUser);
        cartIcon = (ImageView)findViewById(R.id.toolbarCart);
        exitIcon = (ImageView)findViewById(R.id.toolbarExit);
        searchIcon = (ImageView)findViewById(R.id.toolbarSearch);
        paynow = (Button)findViewById(R.id.cartbutton_paynow);

        barTitle.setText("Cart");

        //////////////////////////////
        rv=(RecyclerView)findViewById(R.id.res);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        getCartData();
        countCartItems=cartProductAdapter.getItemCount();
        ///////////////////////////

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserActivity.class));
            }
        });

        exitIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Exit",Toast.LENGTH_SHORT).show();
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AllClothes.class));
            }
        });


        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PaymentActivity.class));
            }
        });
    }
    private void getCartData() {
        carts=AllClothes.myDatabase.cartDao().getData();
        cartProductAdapter=new CartProductAdapter(carts,this);
        rv.setAdapter(cartProductAdapter);

    }
}