package com.example.comp4342_project.cart.model;

import com.google.gson.annotations.SerializedName;

public class MyProductData {
    @SerializedName("id")
    public int id;

    @SerializedName("prname")
    public String prname;

    @SerializedName("primage")
    public String primage;

    @SerializedName("prprice")
    public String prprice;

    public MyProductData(int id, String prname, String primage, String prprice) {
        this.id = id;
        this.prname = prname;
        this.primage = primage;
        this.prprice = prprice;
    }

    public int getId() {
        return id;
    }

    public String getPrname() {
        return prname;
    }

    public String getPrimage() {
        return primage;
    }

    public String getPrprice() {
        return prprice;
    }
}
