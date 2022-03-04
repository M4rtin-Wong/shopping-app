package com.example.comp4342_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.example.comp4342_project.cart.adapter.MyProductAdapter;
import com.example.comp4342_project.cart.cartapi.Myretrofit;
import com.example.comp4342_project.cart.model.MyProductData;
import com.example.comp4342_project.cart.roomdatabase.MyDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//////////////////
public class AllClothes extends AppCompatActivity {

    private TextView barTitle;
    private ImageView homeIcon;
    private ImageView userIcon;
    private ImageView cartIcon;
    private ImageView exitIcon;
    private ImageButton searchIcon;
    /////////////////////

    RecyclerView rv;
    ImageView cartbtn;
    TextView cartcount;
    MyProductAdapter myProductAdapter;
    public static MyDatabase myDatabase;
    List<MyProductData>myProductData;

    //////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_all_clothes);

        barTitle = (TextView)findViewById(R.id.toolbarTitle);
        homeIcon = (ImageView)findViewById(R.id.toolbarHome);
        userIcon = (ImageView)findViewById(R.id.toolbarUser);
        cartIcon = (ImageView)findViewById(R.id.toolbarCart);
        exitIcon = (ImageView)findViewById(R.id.toolbarExit);
        searchIcon = (ImageButton)findViewById(R.id.toolbarSearch);

        //////////////////////

        rv=(RecyclerView)findViewById(R.id.res);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this,2));

        //////////////////////
        barTitle.setText("All Clothes");

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserActivity.class));
            }
        });

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CartActivity.class));
            }
        });
        myDatabase= Room.databaseBuilder(getApplicationContext(),MyDatabase.class,"My_Cart").allowMainThreadQueries().build();
        getdata();

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

    }
    //use this to update the count of product items.
    private void updatacartcount() {
        if (cartcount==null)return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (myDatabase.cartDao().countCart()==0)
                    cartcount.setVisibility(View.INVISIBLE);
                else {
                    cartcount.setVisibility(View.VISIBLE);
                    cartcount.setText(String.valueOf(myDatabase.cartDao().countCart()));
                }
            }
        });

    }
    private void getdata() {
        Call<List<MyProductData>>call= Myretrofit.getInstance().getMyApi().getProductData();
        call.enqueue(new Callback<List<MyProductData>>() {
            @Override
            public void onResponse(Call<List<MyProductData>> call, Response<List<MyProductData>> response) {
                myProductData=response.body();
                myProductAdapter=new MyProductAdapter(myProductData,AllClothes.this);
                rv.setAdapter(myProductAdapter);
            }

            @Override
            public void onFailure(Call<List<MyProductData>> call, Throwable t) {

            }
        });
    }
}