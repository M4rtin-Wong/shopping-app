package com.example.comp4342_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import androidx.constraintlayout.widget.Group;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.comp4342_project.database.DatabaseInfo;
import com.example.comp4342_project.session.SessionInfo;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    private TextView text_firstName, text_lastName, text_gender, text_phone, text_birth, text_email, text_room, text_building, text_district;
    private EditText edit_firstName, edit_LastName, edit_gender, edit_phone, edit_birth, edit_email, edit_room, edit_building, edit_district;
    boolean isPasswordTrue;
    private Group groupOfTextAndButton, getGroupOfEdit;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        TextView barTitle = findViewById(R.id.toolbarTitle);
        ImageView homeIcon = findViewById(R.id.toolbarHome);
        ImageView cartIcon = findViewById(R.id.toolbarCart);
        ImageView exitIcon = findViewById(R.id.toolbarExit);
        ImageView searchIcon = findViewById(R.id.toolbarSearch);

        groupOfTextAndButton = findViewById(R.id.groupOfTextAndButton);
        text_firstName = findViewById(R.id.text_firstName);
        text_lastName = findViewById(R.id.text_lastName);
        text_gender = findViewById(R.id.text_gender);
        text_phone = findViewById(R.id.text_phone);
        text_birth = findViewById(R.id.text_birth_date);
        text_email = findViewById(R.id.text_email);
        text_room = findViewById(R.id.text_room);
        text_building = findViewById(R.id.text_building);
        text_district = findViewById(R.id.text_district);
        Button change_Inf = findViewById(R.id.button_change_Inf);
        Button changePassword = findViewById(R.id.button_changePassword);
        Button record = findViewById(R.id.button_transactionRecord);
        Button ok = findViewById(R.id.ok);

        getGroupOfEdit = findViewById(R.id.groupOfEdit);
        edit_birth = findViewById(R.id.editBirth);
        edit_room = findViewById(R.id.editRoom);
        edit_building = findViewById(R.id.editBuilding);
        edit_gender = findViewById(R.id.editGender);
        edit_phone = findViewById(R.id.editPhone);
        edit_LastName = findViewById(R.id.editLastName);
        edit_firstName = findViewById(R.id.editFirstName);
        edit_email = findViewById(R.id.editEmail);
        edit_district = findViewById(R.id.editDistrict);

        Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);

        barTitle.setText(R.string.user_title);
        getUserInf();

        //The onFocusChangeListener to clean the text of editText when the user clicks it
        View.OnFocusChangeListener onFocusChangeListener = (view, hasFocus) -> {
            if (view.getId() == R.id.editRoom && hasFocus) edit_room.getText().clear();
            if (view.getId() == R.id.editBuilding && hasFocus) edit_building.getText().clear();
            if (view.getId() == R.id.editDistrict && hasFocus) edit_district.getText().clear();
            if (view.getId() == R.id.editEmail && hasFocus) edit_email.getText().clear();
            if (view.getId() == R.id.editFirstName && hasFocus) edit_firstName.getText().clear();
            if (view.getId() == R.id.editLastName && hasFocus) edit_LastName.getText().clear();
            if (view.getId() == R.id.editPhone && hasFocus) edit_phone.getText().clear();
            if (view.getId() == R.id.editGender && hasFocus) edit_gender.getText().clear();
        };

        //The focus will change to next editText when user presses enter
        TextView.OnEditorActionListener onEditorActionListener = (textView, i, keyEvent) -> false;

        edit_district.setOnFocusChangeListener(onFocusChangeListener);
        edit_phone.setOnFocusChangeListener(onFocusChangeListener);
        edit_email.setOnFocusChangeListener(onFocusChangeListener);
        edit_firstName.setOnFocusChangeListener(onFocusChangeListener);
        edit_LastName.setOnFocusChangeListener(onFocusChangeListener);
        edit_gender.setOnFocusChangeListener(onFocusChangeListener);
        edit_building.setOnFocusChangeListener(onFocusChangeListener);
        edit_room.setOnFocusChangeListener(onFocusChangeListener);

        edit_phone.setOnEditorActionListener(onEditorActionListener);
        edit_email.setOnEditorActionListener(onEditorActionListener);
        edit_firstName.setOnEditorActionListener(onEditorActionListener);
        edit_LastName.setOnEditorActionListener(onEditorActionListener);
        edit_gender.setOnEditorActionListener(onEditorActionListener);
        edit_building.setOnEditorActionListener(onEditorActionListener);
        edit_room.setOnEditorActionListener(onEditorActionListener);

        //Close the soft keyboard
        edit_district.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                return true;
            }
            return false;
        });

        //Button for different function
        changePassword.setOnClickListener(v -> showPasswordDialog(1));
        change_Inf.setOnClickListener(v -> showPasswordDialog(2));

        //Show the transaction record
        record.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
            View transactionRecord = getLayoutInflater().inflate(R.layout.activity_transaction_record, null);
            TextView product_ID = transactionRecord.findViewById(R.id.record_ProductID);
            TextView amount = transactionRecord.findViewById(R.id.record_Amount);
            TextView date = transactionRecord.findViewById(R.id.record_Date);

            builder.setView(transactionRecord);
            AlertDialog dialog = builder.create();
            dialog.show();

            String URL = DatabaseInfo.URL + "transactionRecord.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {

                try {
                    JSONObject object = new JSONObject(response);
                    product_ID.setText(object.getString("product_ID"));
                    amount.setText(object.getString("amount"));
                    date.setText(object.getString("date"));
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }


            }, error -> Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show()) {

                @Override
                //The parameters that is needed by server to perform register function
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<>();
                    data.put("userID", SessionInfo.returnUserID() + "");
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);


        });

        //Change data information, hide the editText and show the textView after ok button is clicked
        ok.setOnClickListener(v -> {
            if (dataChange()) {
                groupOfTextAndButton.setVisibility(View.VISIBLE);
                getGroupOfEdit.setVisibility(View.INVISIBLE);
            }
        });

        //Allow user chooses their birth date by dialog
        edit_birth.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(UserActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, setListener, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        edit_birth.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UserActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }

        });

        setListener = (view, year1, month1, day1) -> {
            month1 = month1 + 1;
            String date = year1 + "-" + month1 + "-" + day1;
            //String date = day+"/"+month+"/"+year;
            edit_birth.setText(date);
        };

        homeIcon.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), HomeActivity.class)));
        cartIcon.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CartActivity.class)));
        exitIcon.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_SHORT).show());
        searchIcon.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AllClothes.class)));

    }

    //Change data
    private boolean dataChange() {
        String firstName, lastName, phone, room, district, building, birth, gender, email;
        firstName = edit_firstName.getText().toString();
        lastName = edit_LastName.getText().toString();
        phone = edit_phone.getText().toString();
        room = edit_room.getText().toString();
        district = edit_district.getText().toString();
        building = edit_building.getText().toString();
        birth = edit_birth.getText().toString();
        gender = edit_gender.getText().toString();
        email = edit_email.getText().toString();

        //input check
        if (gender.equals("M") || gender.equals("Male") || gender.equals("MALE") || gender.equals("male")) {
            gender = "M";
        } else if (gender.equals("F") || gender.equals("Female") || gender.equals("FEMALE") || gender.equals("female")) {
            gender = "F";
        } else {
            Toast.makeText(this, "Please enter correct gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            Toast.makeText(this, "Email format incorrect!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phone.length() < 8) {
            Toast.makeText(this, "Phone must be 8 digits number!", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Post data to the server
        String URL = DatabaseInfo.URL + "changeInf.php";
        String finalGender = gender;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            getUserInf();
        }, error -> Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show()) {

            @Override
            //The parameters that is needed by server to perform register function
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("firstName", firstName);
                data.put("lastName", lastName);
                data.put("phone", phone);
                data.put("gender", finalGender);
                data.put("room", room);
                data.put("district", district);
                data.put("building", building);
                data.put("birth", birth);
                data.put("email", email);
                data.put("userID", SessionInfo.returnUserID() + "");
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        //Set the text of textView and save to the session
        setTextView(firstName, lastName, gender, email, phone, room, building, district, birth);
        String[] stringToSession = new String[9];
        if (!firstName.equals("")) stringToSession[0] = firstName;
        if (!lastName.equals("")) stringToSession[1] = lastName;
        stringToSession[2] = gender;
        if (!email.equals("")) stringToSession[3] = email;
        stringToSession[4] = phone;
        if (!room.equals("")) stringToSession[5] = room;
        if (!building.equals("")) stringToSession[6] = building;
        if (!district.equals("")) stringToSession[7] = district;
        if (!birth.equals("")) stringToSession[8] = birth;
        SessionInfo.saveInformation(stringToSession);
        return true;

    }

    //Show the dialog to get user's password
    public void showPasswordDialog(int mode) {

        AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
        View passwordView = getLayoutInflater().inflate(R.layout.activity_require_password, null);
        EditText passwordView_password = passwordView.findViewById(R.id.requirePassword);
        Button passwordView_okButton = passwordView.findViewById(R.id.okButton);
        Button passwordView_cancelButton = passwordView.findViewById(R.id.cancelButton);

        builder.setView(passwordView);
        AlertDialog dialog = builder.create();
        dialog.show();

        passwordView_okButton.setOnClickListener(v -> {
            String password = passwordView_password.getText().toString();
            checkPassword(password, mode, dialog, builder);

        });
        passwordView_cancelButton.setOnClickListener(v -> dialog.cancel());

    }

    //Change the user password and upload to server
    private void changePassword(String password) {
        String URL = DatabaseInfo.URL + "changePassword.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show(), error -> Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show()) {
            @Override
            //The parameters that is needed by server to perform register function
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("userID", SessionInfo.returnUserID() + "");
                data.put("password", password);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    //Check the password of user
    private void checkPassword(String password, int mode, AlertDialog dialog, AlertDialog.Builder builder) {

        String URL = DatabaseInfo.URL + "checkPassword.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                isPasswordTrue = object.getInt("passwordTrue") == 1;
                if (!password.isEmpty() && isPasswordTrue) {
                    switch (mode) {
                        //The case of changing password after password confirm
                        case 1:
                            dialog.cancel();
                            View passwordView = getLayoutInflater().inflate(R.layout.activity_change_password, null);
                            EditText passwordView_password = (EditText) passwordView.findViewById(R.id.requirePassword);
                            Button passwordView_okButton = (Button) passwordView.findViewById(R.id.okButton);
                            Button passwordView_cancelButton = (Button) passwordView.findViewById(R.id.cancelButton);
                            builder.setView(passwordView);
                            AlertDialog changePasswordDialog = builder.create();
                            changePasswordDialog.show();
                            passwordView_okButton.setOnClickListener(v -> {
                                String password1 = passwordView_password.getText().toString();
                                changePassword(password1);
                                changePasswordDialog.cancel();
                            });
                            passwordView_cancelButton.setOnClickListener(v -> changePasswordDialog.cancel());
                            break;
                        //The case of changing information after password confirm
                        case 2:
                            dialog.cancel();
                            isPasswordTrue = false;
                            edit_birth.setText(text_birth.getText().toString());
                            edit_building.setText(text_building.getText().toString());
                            edit_district.setText(text_district.getText().toString());
                            edit_email.setText(text_email.getText().toString());
                            edit_firstName.setText(text_firstName.getText().toString());
                            edit_LastName.setText(text_lastName.getText().toString());
                            edit_phone.setText(text_phone.getText().toString());
                            edit_gender.setText(text_gender.getText().toString());
                            edit_room.setText(text_room.getText().toString());
                            groupOfTextAndButton.setVisibility(View.INVISIBLE);
                            getGroupOfEdit.setVisibility(View.VISIBLE);
                            break;
                        default:
                            Toast.makeText(UserActivity.this, "this will never happen", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException jsonException) {

                jsonException.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show()) {

            @Override
            //The parameters that is needed by server to perform register function
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("userID", SessionInfo.returnUserID() + "");
                data.put("password", password);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    //Get the user information from server
    private void getUserInf() {
        String[] userData = SessionInfo.loadInformation();
        if (userData[0] == null) {
            String URL = DatabaseInfo.URL + "getInf.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                try {
                    JSONObject object = new JSONObject(response);
                    setTextView(object.getString("firstName"), object.getString("lastName"), object.getString("gender"), object.getString("email"),
                            object.getString("phone"), object.getString("room"), object.getString("building"),
                            object.getString("district"), object.getString("birth"));

                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }, error -> Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show()) {

                @Override
                //The parameters that is needed by server to perform register function
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<>();
                    data.put("userID", SessionInfo.returnUserID() + "");
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } else {
            setTextView(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5], userData[6], userData[7], userData[8]);
        }

    }

    //The function for setting textView
    private void setTextView(String firstName, String lastName, String gender, String email, String phone, String room, String building, String district, String birth_date) {
        if (!firstName.equals("")) text_firstName.setText(firstName);
        if (!lastName.equals("")) text_lastName.setText(lastName);
        if (!gender.equals("")) text_gender.setText(gender);
        if (!email.equals("")) text_email.setText(email);
        if (!phone.equals("")) text_phone.setText(phone);
        if (!room.equals("")) text_room.setText(room);
        if (!building.equals("")) text_building.setText(building);
        if (!district.equals("")) text_district.setText(district);
        if (!birth_date.equals("")) text_birth.setText(birth_date);
    }

}