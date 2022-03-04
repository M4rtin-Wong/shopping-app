package com.example.comp4342_project.cart.roomdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={Cart.class},version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract CartDao cartDao();
}
