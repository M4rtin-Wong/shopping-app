package com.example.comp4342_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.comp4342_project.database.DatabaseInfo;
import com.example.comp4342_project.session.SessionInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
    private Button btn_login;
    private TextView tv_signup;
    private EditText et_email;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_email = (EditText) findViewById(R.id.et_Email);
        et_password = (EditText) findViewById(R.id.et_Password);
        btn_login = (Button) findViewById(R.id.btn_Login);
        tv_signup = (TextView) findViewById(R.id.tv_Signup);

        checkIsLogin(); // To check rather user has logined or not

        //Let the user to perform login function
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        //Redirect to sign up page
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }

    //Perform login function
    public void login(View view){
        //To get text from edittext
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        //To check is the edittext empty or not
        //if not empty, request server to perform login function
        //else display error meesage
        if(!email.equals("") && !password.equals("")){
            //http request url
            String URL = DatabaseInfo.URL + "login.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        //Return object from server
                        JSONObject object = new JSONObject(response);
                        String result = object.getString("result");
                        //If return object include success message,
                        //login success, user id will be saved to session, and user will be redirect to home page
                        //else, display error message
                        if (result.compareTo("success") == 0) {
                            //save user id to session
                            saveSession(object.getInt("userID"));
                            //redirect to home page
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            //login fail, display error message
                            String error = object.getString("error");
                            Toast.makeText(getApplicationContext(), "Login fail: " + error, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.i("tagconvertstr", "["+response+"]");
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                //The parameters that is needed by server to perform login function
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("email",email);
                    data.put("password",password);
                    return data;
                }
            };
            //send http request
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }else{
            //error message
            Toast.makeText(this,"Please input email and password",Toast.LENGTH_SHORT).show();
        }
    }

    //To save user id in to session
    private void saveSession(int id) {
        //to open session
        SessionInfo sessionInfo = new SessionInfo(MainActivity.this);
        //to save user id to session
        sessionInfo.saveUserID(id);
    }

    //To use session to check whether user has logined or not
    //If logined, redirect to home page
    //If not logined, do nothing
    private void checkIsLogin() {
        SessionInfo sessionInfo = new SessionInfo(MainActivity.this);
        int userID = sessionInfo.returnUserID();
        if(userID != -1){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }else{
            //do nothing
        }
    }

}