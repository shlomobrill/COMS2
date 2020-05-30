package com.example.project1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterCouncellor extends AppCompatActivity {
    EditText etFName,etLName,etUsername2,etEmail, etPassword2;

    final String url_Register = "https://lamp.ms.wits.ac.za/home/s2141916/RegisterCouncellor.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_councellor); //activity_register_councellor

            etFName = (EditText) findViewById(R.id.NameFirst);
            etLName = (EditText) findViewById(R.id.NameLast);
            etUsername2 = (EditText) findViewById(R.id.UsernameCouncellor);
            etEmail= (EditText) findViewById(R.id.EmailCouncellor);
            etPassword2 = (EditText) findViewById(R.id.PasswordCouncellor);

            Button ReturnBackToSignInPageFromRegisterCouncellor = (Button) findViewById(R.id.register2);
            ReturnBackToSignInPageFromRegisterCouncellor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String FirstName = etFName.getText().toString();
                    String LastName = etLName.getText().toString();
                    String UserName2 = etUsername2.getText().toString();
                    String Email = etEmail.getText().toString();
                    String Password2 = etPassword2.getText().toString();
                    new Register_User().execute(FirstName,LastName,UserName2,Email,Password2);

                }
            });

        }

        public class Register_User extends AsyncTask<String,Void,String> {

            @Override
            protected String doInBackground(String... strings) {
                String FirstN = strings[0];
                String LastN = strings[1];
                String Username = strings[2];
                String Email = strings[3];
                String Password = strings[4];

                String finalURL = url_Register + "?COUNCELLOR_FNAME=" + FirstN +
                        "&COUNCELLOR_LNAME=" + LastN +
                        "&COUNCELLOR_USERNAME=" + Username +
                        "&COUNCELLOR_EMAIL=" + Email +
                        "&COUNCELLOR_PASSWORD=" + Password;

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
                            Intent i = new Intent(RegisterCouncellor.this, MainActivity.class);
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
                    Toast.makeText(RegisterCouncellor.this, text, Toast.LENGTH_LONG).show();
                }
            });
        }

    }

