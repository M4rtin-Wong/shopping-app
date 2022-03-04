package com.example.comp4342_project.cart.cartapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Myretrofit {

    private static final String BASE_URL="http://192.168.0.100/comp4342_project/mycart/";
    private static com.example.comp4342_project.cart.cartapi.Myretrofit myClient;
    private Retrofit retrofit;

    private Myretrofit(){

        retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }
    public static synchronized com.example.comp4342_project.cart.cartapi.Myretrofit getInstance(){
        if (myClient==null){
            myClient=new com.example.comp4342_project.cart.cartapi.Myretrofit();
        }
        return myClient;
    }
    public CartApi getMyApi(){
        return retrofit.create(CartApi.class);
    }

}
