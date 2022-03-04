package com.example.comp4342_project.cart.roomdatabase;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartDao {

    @Insert
    public void addToCart(Cart cart);

    @Query("SELECT * FROM MyCart")
    public List<Cart>getData();

    @Query("SELECT EXISTS (SELECT 1 FROM mycart WHERE id=:id)")
    public int isAddToCart(int id);
    //we can use this to calculate the taotal price for payment activity!!!
    @Query("select COUNT (*) from MyCart")
    int countCart();

    @Query("DELETE FROM MyCart WHERE id=:id ")
    int deleteItem(int id);

    @Query("DELETE FROM MyCart")
    int deleteAllItem();

    @Query("Select price FROM MyCart")
    float[] PriceOfItem();

    @Query("Select id FROM MyCart")
    int[] IdOfItem();

    @Query("Select price FROM MyCart")
    char[] NameOfItem();

}
