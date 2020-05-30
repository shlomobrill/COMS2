package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterUser extends AppCompatActivity {
    EditText etName;
    EditText etPassword;
    final String url_Register = "https://lamp.ms.wits.ac.za/home/s2141916/Register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Button ReturnBackToSignInPageFromRegisterUser;


            etName = (EditText) findViewById(R.id.etName);
            etPassword = (EditText) findViewById(R.id.etPassword);

            ReturnBackToSignInPageFromRegisterUser = (Button) findViewById(R.id.register2);
            ReturnBackToSignInPageFromRegisterUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Name = etName.getText().toString();
                    String Password = etPassword.getText().toString();
                    new Register_User().execute(Name,Password);

                }
            });

        }

        public class Register_User extends AsyncTask<String,Void,String> {

            @Override
            protected String doInBackground(String... strings) {
                String Username = strings[0];

                String Password = strings[1];

                String finalURL = url_Register + "?USER_USERNAME=" + Username +
                        "&USER_PASSWORD=" + Password;

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(finalURL)
                        .get()
                        .build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        showToast(result);

                        if (result.equalsIgnoreCase("User registered successfully")) {
                            showToast("Register successful");
                            Intent i = new Intent(RegisterUser.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else if (result.equalsIgnoreCase("User already exists")) {
                            showToast("User already exists");
                        } else {
                            showToast("oops! please try again!");
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

        public void showToast(final String text)
        {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RegisterUser.this, text, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
