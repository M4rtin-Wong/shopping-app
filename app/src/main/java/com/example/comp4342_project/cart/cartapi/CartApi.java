package com.example.comp4342_project.cart.cartapi;

import com.example.comp4342_project.cart.model.MyProductData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CartApi {

    @GET("getdata.php")
    Call<List<MyProductData>> getProductData();
}
