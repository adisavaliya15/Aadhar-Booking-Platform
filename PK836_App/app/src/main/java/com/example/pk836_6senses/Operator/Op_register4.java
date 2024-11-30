package com.example.pk836_6senses.Operator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.pk836_6senses.Citizen.register4;
import com.example.pk836_6senses.R;
import com.example.pk836_6senses.RequestHandler;
import com.example.pk836_6senses.SharedPrefManager;
import com.example.pk836_6senses.URLs;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Op_register4 extends AppCompatActivity {
    Button next, login, signup, verify_otp;
    ImageView back;
    TextView city, state;
    TextView signup_title_text, slideText;
    TextInputLayout phone;
    ProgressBar progressBar;
    String uname, email, phoneNo, pwd, addr, role, urls, sn, str_gender, str_city, str_state, str_dob, str_pin, name,  mi;
    String s_nm, s_surname, s_mail, s_pass, s_confirmPass, s_phone;
    String str_address, str_pincode;
    File imagefile;
    PinView otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        sharedPrefManager.updateResource(sharedPrefManager.getLang());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_op_register4);

        signup_title_text = findViewById(R.id.signup_title_text);
        phone = findViewById(R.id.operator_phone);

        next = findViewById(R.id.signup_next_op_button456);
        login = findViewById(R.id.signup_login_button);
        back = findViewById(R.id.signup_back_button);
        slideText = findViewById(R.id.signup_slide_text);
        verify_otp = findViewById(R.id.btn_operator_verify_otp);
        signup = findViewById(R.id.signup_btn_op);
        otp = findViewById(R.id.show_otp_opreg);

        Intent intent = getIntent();

        s_nm = intent.getStringExtra("FNAME");
        s_surname = intent.getStringExtra("LNAME");
        s_mail = intent.getStringExtra("EMAIL");
        s_pass = intent.getStringExtra("PASSWORD");
        s_confirmPass = intent.getStringExtra("CONFIRM_PASS");
        str_dob = intent.getStringExtra("DOB");
        str_address = intent.getStringExtra("ADDRESS");
        str_gender = intent.getStringExtra("GENDER");
        str_city = intent.getStringExtra("CITY");

        str_state = intent.getStringExtra("STATE");
        str_pincode = intent.getStringExtra("PINCODE");
        name =intent.getStringExtra("imagename");
        imagefile = new File(intent.getStringExtra("imagefile"));
        mi =intent.getStringExtra("mi");
        Log.e("imagename", name);
        Log.e("imagefile", String.valueOf(imagefile));
        Log.e("mi", mi);

        Log.e("fd", intent.getStringExtra("ADDRESS"));
        Log.e("nb", intent.getStringExtra("PINCODE"));
        Log.e("dvd", intent.getStringExtra("CITY"));
        Log.e("dvd", intent.getStringExtra("STATE"));
        Log.e("dvd", intent.getStringExtra("GENDER"));
        Log.e("dvd", intent.getStringExtra("DOB"));
        Log.e("dvd", intent.getStringExtra("FNAME"));
        Log.e("dvd", intent.getStringExtra("LNAME"));
        Log.e("dvd", intent.getStringExtra("EMAIL"));
        Log.e("dvd", intent.getStringExtra("PASSWORD"));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUser();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getOtpforReg();
            }
        });
        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyOtp();
            }
        });

    }

    private void getOtpforReg() {
        String str_phone = phone.getEditText().getText().toString();

        class UserLogin extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if(!obj.getBoolean("error")) {


                        verify_otp.setVisibility(View.VISIBLE);
                        otp.setVisibility(View.VISIBLE);

                        Toast.makeText(Op_register4.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Op_register4.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("PHONE_NO", str_phone);

                return requestHandler.sendPostRequest(URLs.URL_GET_REG_OTP, params);

            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

    private void verifyOtp() {

        String str_phone = phone.getEditText().getText().toString();
        String str_otp = otp.getText().toString();

        class UserLogin extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if(!obj.getBoolean("error")) {

                        signup.setVisibility(View.VISIBLE);

                        Toast.makeText(Op_register4.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Op_register4.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("PHONE_NO", str_phone);
                params.put("OTP", str_otp);

                return requestHandler.sendPostRequest(URLs.URL_VERIFY_REG_OTP, params);

            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

    public void registerUser() {
        String str_phone = phone.getEditText().getText().toString();
        new Thread(new Runnable() {
            public void run() {
                // call send message here
                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("fname", s_nm)
                        .addFormDataPart("surname", s_surname)
                        .addFormDataPart("email", s_mail)
                        .addFormDataPart("password", s_pass)
                        .addFormDataPart("contact", str_phone)
                        .addFormDataPart("address", str_address)
                        .addFormDataPart("role", "OPERATOR")
                        .addFormDataPart("city", str_city)
                        .addFormDataPart("state", str_state)
                        .addFormDataPart("pincode", str_pincode)
                        .addFormDataPart("dob", str_dob)
                        .addFormDataPart("gender", str_gender)
                        .addFormDataPart("dp_file", name, RequestBody.create(imagefile, MediaType.parse(mi)))
                        .build();
                Log.e("imagename", name);
                Log.e("imagefile", String.valueOf(imagefile));
                Log.e("mi", mi);

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(URLs.URL_REGISTER)
                        .post(requestBody)
                        .build();

                okhttp3.Response response = null;

                try {
                    response = okHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JSONObject jsonObject = null;

                try {


                    if (response.body() != null) {
                        jsonObject = new JSONObject(response.body().string());
                        if (!jsonObject.getBoolean("error")) {
                            //Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            Log.e("dsf", jsonObject.getString("message"));
                            Intent i = new Intent(Op_register4.this, Login_Page_Operator.class);
                            startActivity(i);
                        } else {
                            // Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                        Log.e("asd", String.valueOf(jsonObject));
                    } else {
                        Toast.makeText(Op_register4.this, "Image no selected", Toast.LENGTH_SHORT).show();


                    }
                    Log.e("asd", String.valueOf(jsonObject));


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}