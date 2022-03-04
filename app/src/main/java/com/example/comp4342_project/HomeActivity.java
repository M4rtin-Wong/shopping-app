package com.example.comp4342_project;

import androidx.appcompat.app.AppCompatActivity;
import com.example.comp4342_project.session.SessionInfo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private TextView barTitle;
    private ImageView homeIcon;
    private ImageView userIcon;
    private ImageView cartIcon;
    private ImageView exitIcon;
    private ImageView searchIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        barTitle = (TextView)findViewById(R.id.toolbarTitle);
        homeIcon = (ImageView)findViewById(R.id.toolbarHome);
        userIcon = (ImageView)findViewById(R.id.toolbarUser);
        cartIcon = (ImageView)findViewById(R.id.toolbarCart);
        exitIcon = (ImageView)findViewById(R.id.toolbarExit);
        searchIcon = (ImageView)findViewById(R.id.toolbarSearch);

        checkIsLogin();// To check rather user has logined or not

        //To go to user information page
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserActivity.class));
            }
        });

        //To go to shopping cart page
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CartActivity.class));
            }
        });

        //To logout account
        //remove user id from session
        //redirect to main/login page
        exitIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove user id from session
                SessionInfo sessionInfo = new SessionInfo(HomeActivity.this);
                sessionInfo.removeSession();
                //redirect to main/login page
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        //To go to search page
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AllClothes.class));
            }
        });

    }

    //To use session to check whether user has logined or not
    //If logined, do nothing
    //If not logined, redirect to login(main) page
    private void checkIsLogin() {
        SessionInfo sessionInfo = new SessionInfo(HomeActivity.this);
        int userID = sessionInfo.returnUserID();
        if(userID == -1){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else{
            //do nothing
        }
    }
}