package com.example.comp4342_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    public static int userID;
    DatePickerDialog.OnDateSetListener setListener;
    Button btnSignup;
    TextView tv_login;

    EditText et_email;
    EditText et_password;
    EditText et_password_2;
    EditText et_lastName;
    EditText et_firstName;
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText et_phone;
    EditText et_address_1;
    EditText et_address_2;
    EditText et_address_3;
    TextView tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnSignup = (Button) findViewById(R.id.btnSignup);
        tv_login = (TextView) findViewById(R.id.tv_login);

        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password_1);
        et_password_2 = (EditText) findViewById(R.id.et_password_2);
        et_lastName = (EditText) findViewById(R.id.et_lastName);
        et_firstName = (EditText) findViewById(R.id.et_firstName);
        radioGroup = (RadioGroup) findViewById(R.id.rd_gender);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_address_1 = (EditText) findViewById(R.id.et_address_1);
        et_address_2 = (EditText) findViewById(R.id.et_address_2);
        et_address_3 = (EditText) findViewById(R.id.et_address_3);
        tv_date = (TextView)findViewById(R.id.date);

        checkIsLogin();// To check rather user has logined or not

        //Used for display date picker
        Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);

        //to go to login page
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        //to perform register function
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });

        //to display date picker dialog
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        //to set date to birth day textview, if day is selected
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                //String date = day+"/"+month+"/"+year;
                tv_date.setText(date);
            }
        };
    }

    public void checkButton(View v){
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
        //Toast.makeText(this,radioButton.getText(),Toast.LENGTH_SHORT).show();
    }

    public void register(View view){
        //To get text from edittext
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String password_2 = et_password_2.getText().toString().trim();
        String lastName = et_lastName.getText().toString().trim();
        String firstName = et_firstName.getText().toString().trim();
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
        String gender = (String) radioButton.getText();
        String phone = et_phone.getText().toString().trim();
        String address_1 = et_address_1.getText().toString().trim();
        String address_2 = et_address_2.getText().toString().trim();
        String address_3 = et_address_3.getText().toString().trim();
        String date = tv_date.getText().toString().trim();

        //To check is the edittext empty or not
        //if not empty, request server to perform register function
        //else display error meesage
        if(!email.equals("") && !password.equals("") && !password_2.equals("") && !lastName.equals("") && !firstName.equals("")
                && !phone.equals("") && !address_1.equals("") && !address_2.equals("") && !address_3.equals("") && !date.equals("")){
            /*
            Toast.makeText(getApplicationContext(), "Yor email is " + email +
                            "\nYour password is " + password +
                            "\nYour password_2 is " + password_2 +
                            "\nYour lastName is " + lastName +
                            "\nYour firsttName is " + firsttName +
                            "\nYour gender is " + gender +
                            "\nYour phone is " + phone +
                            "\nYour address_1 is " + address_1 +
                            "\nYour address_2 is " + address_2 +
                            "\nYour address_3 is " + address_3 +
                            "\nYour date is " + date
                    , Toast.LENGTH_LONG).show();
             */

            //Validation
            //if validation passes, perform register function
            //else display error message
            if(!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
                //To check whether the email fullfills email address format
                //if not, display error message
                Toast.makeText(this,"Email format incorrect!",Toast.LENGTH_SHORT).show();
            }else if(password.length()<6){
                //Password must be at least 6 digits
                Toast.makeText(this,"Password must be at least 6 digits",Toast.LENGTH_SHORT).show();
            }else if(!password.equals(password_2)){
                //To check both password and password_2 match
                Toast.makeText(this,"Password 1 and 2 are not identical!",Toast.LENGTH_SHORT).show();
            }else if(phone.length()<8){
                //To check whether the phone number length equal to 8 digit
                //if not, display error message
                Toast.makeText(this,"Phone must be 8 digits number!",Toast.LENGTH_SHORT).show();
            }else{
                //http request url
                String URL = DatabaseInfo.URL + "register.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //the return object from server
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");
                            //if registration success, it return message includes success
                            //save the user id to session and redirect to home page
                            //,else display error message
                            if (result.compareTo("success") == 0) {
                                //save user id to session
                                saveSession(object.getInt("userID"));
                                //redirect to home page
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            } else {
                                //error message
                                String error = object.getString("error");
                                Toast.makeText(getApplicationContext(), "Register fail: " + error, Toast.LENGTH_SHORT).show();
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
                    //The parameters that is needed by server to perform register function
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("email",email);
                        data.put("password",password);
                        data.put("lastName",lastName);
                        data.put("firstName",firstName);
                        data.put("gender",gender);
                        data.put("phone",phone);
                        data.put("address_1",address_1);
                        data.put("address_2",address_2);
                        data.put("address_3",address_3);
                        data.put("date",date);
                        return data;
                    }
                };
                //send http request
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        }else{
            //error message
            Toast.makeText(this,"Please input all the data",Toast.LENGTH_SHORT).show();
        }
    }

    //To save user id in to session
    private void saveSession(int id) {
        //to open session
        SessionInfo sessionInfo = new SessionInfo(RegisterActivity.this);
        //to save user id to session
        sessionInfo.saveUserID(id);
    }

    //To use session to check whether user has logined or not
    //If logined, redirect to home page
    //If not logined, do nothing
    private void checkIsLogin() {
        SessionInfo sessionInfo = new SessionInfo(RegisterActivity.this);
        userID = sessionInfo.returnUserID();

        if(userID != -1){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }else{
            //do nothing
        }
    }
}