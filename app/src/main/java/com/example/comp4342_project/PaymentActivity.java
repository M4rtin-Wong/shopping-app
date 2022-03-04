package com.example.comp4342_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.comp4342_project.cart.adapter.CartProductAdapter;
import com.example.comp4342_project.cart.roomdatabase.Cart;
import com.example.comp4342_project.database.DatabaseInfo;
import com.example.comp4342_project.session.SessionInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public class PaymentActivity extends AppCompatActivity {

    TextView paymentamountholder;
    TextView paymentamount;
    EditText cardownername;
    EditText cardnumber;
    EditText cardexpiredate;
    EditText cardcvc;
    Button paynow;
    Button cancalpayment;
    ///////
    List<Cart> carts;
    CartProductAdapter cartProductAdapter;
    ////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //we find the cart info and find how many items are there and calculate the total price.
        carts=AllClothes.myDatabase.cartDao().getData();
        cartProductAdapter=new CartProductAdapter(carts,this);
        int countCartItems=cartProductAdapter.getItemCount();

        float totalPrice = 0;
        for (float value : AllClothes.myDatabase.cartDao().PriceOfItem()) {
            totalPrice += value;
        }

        TextView textView = (TextView) this.findViewById(R.id.payment_amount);
        textView.setText("$"+totalPrice);

        paymentamountholder = (TextView) findViewById(R.id.payment_amount_holder);
        paymentamount = (TextView) findViewById(R.id.payment_amount);
        cardownername = (EditText) findViewById(R.id.card_owner_name);
        cardnumber = (EditText) findViewById(R.id.card_number);
        cardexpiredate = (EditText) findViewById(R.id.expiry_date);
        cardcvc = (EditText) findViewById(R.id.card_cvc);
        paynow = (Button) findViewById(R.id.paymentbutton_paynow);
        cancalpayment = (Button) findViewById(R.id.button_cancalpayment);


        //to go to finish payment page
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { pay(v);}
        });

        //to go to home page
        cancalpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });


    }

    public void pay(View view){
        String ownername = cardownername.getText().toString().trim();
        String number = cardnumber.getText().toString().trim();
        String expiredate = cardexpiredate.getText().toString().trim();
        String cvc = cardcvc.getText().toString().trim();

        if(!ownername.equals("") && !number.equals("") && !expiredate.equals("") && !cvc.equals("")){
            if(number.length()!=16){
                Toast.makeText(this,"Invalid credit card number!",Toast.LENGTH_SHORT).show();
            } else if(expiredate.length()!=4){
                Toast.makeText(this,"Invalid expire date!",Toast.LENGTH_SHORT).show();
            } else if (cvc.length()!=3){
                Toast.makeText(this,"Invalid security code!",Toast.LENGTH_SHORT).show();
            }
             else {
                int userID;

                int size = AllClothes.myDatabase.cartDao().IdOfItem().length;

                int[] idarray = AllClothes.myDatabase.cartDao().IdOfItem();

                float totalPrice = 0;
                for (float value : AllClothes.myDatabase.cartDao().PriceOfItem()) {
                    totalPrice += value;
                }

                SessionInfo sessionInfo = new SessionInfo(PaymentActivity.this);
                userID = sessionInfo.returnUserID();

                String totalamount = String.valueOf(totalPrice);
                String userid = String.valueOf(userID);

                String productid = "";
                for (int i = 0; i<size ; i++) {
                    String tempid = String.valueOf(idarray[i]);
                    productid = productid+" "+tempid;

                }
                String finalProductid = productid;
                Log.d("finalProductid is:",finalProductid);
                String URL = DatabaseInfo.URL + "transaction.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override

                    protected Map<String, String> getParams() {
                        Map<String, String> data = new HashMap<>();
                        data.put("userid", userid);
                        data.put("amount",totalamount);
                        data.put("cardnumber", number);
                        data.put("card_exp_date", expiredate);
                        data.put("product_id",finalProductid);
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);


                AllClothes.myDatabase.cartDao().deleteAllItem();
                startActivity(new Intent(getApplicationContext(),FinishPaymentActivity.class));
            }

        } else{
            //error message
            Toast.makeText(this,"Please input all the data",Toast.LENGTH_SHORT).show();
        }
    }

}