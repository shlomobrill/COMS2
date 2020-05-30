package com.example.project1;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    final String url_loginUser = "https://lamp.ms.wits.ac.za/home/s2141916/LoginsUser.php";
    final String url_loginCouncellor = "https://lamp.ms.wits.ac.za/home/s2141916/LoginsCouncellor.php";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpClient client = new OkHttpClient();

        final TextView RegisterLink = (TextView) findViewById(R.id.RegisterLink);
        final TextView RegisterLink2 = (TextView) findViewById(R.id.RegisterLink2);

        RegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterUser.class);
                MainActivity.this.startActivity(i);
            }
        });

        RegisterLink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this, RegisterCouncellor.class);
                MainActivity.this.startActivity(t);
            }
        });

        final EditText UsernameLogin = (EditText) findViewById(R.id.Login);
        final EditText PasswordLogin = (EditText) findViewById(R.id.Password);
        EditText Type = (EditText) findViewById(R.id.SelectRole);
        final String UserType = Type.getText().toString();
        Button Login = (Button) findViewById(R.id.blogin);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Username = UsernameLogin.getText().toString();
                String Password = PasswordLogin.getText().toString();

                if (UserType.equals("User"))
                {
                    new loginUser().execute(Username, Password);
                }
                if(UserType.equals("Councellor"))
                {
                    new  loginCouncellor().execute(Username, Password);
                }
            }
        });
    }

    public class loginUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String Username = strings[0];
            String Password = strings[1];


            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("USER_USERNAME", Username)
                    .add("USER_PASSWORD", Password)
                    .build();

            Request request = new Request.Builder()
                    .url(url_loginUser)
                    .post(formBody)
                    .build();
            Response response = null;
            try {

                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String result = response.body().toString();

                    if (result.equalsIgnoreCase("login")) {
                        Intent i = new Intent(MainActivity.this, DashBoardUser.class);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(MainActivity.this,
                                "Email or Password mismatched!",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public class loginCouncellor extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String Username2 = strings[0];
            String Password2 = strings[1];

            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("COUNCELLOR_USERNAME", Username2)
                    .add("COUNCELLOR_PASSWORD", Password2)
                    .build();

            Request request = new Request.Builder()
                    .url(url_loginCouncellor)
                    .post(formBody)
                    .build();

            try {

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String result = response.body().toString();

                    if (result.equalsIgnoreCase("login")) {
                        Intent i = new Intent(MainActivity.this, DashBoardCouncellor.class);
                        MainActivity.this.startActivity(i);

                    } else {
                        Toast.makeText(MainActivity.this,
                                "Email or Password mismatched!",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }



}
