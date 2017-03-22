package com.dreamsfactory.dutiesmanager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dreamsfactory.dutiesmanager.R;
import com.dreamsfactory.dutiesmanager.settings.Settings;
import com.dreamsfactory.dutiesmanager.webServices.WebServiceManager;

import java.util.HashMap;
import java.util.Map;

public class UserLoginActivity extends AppCompatActivity {

    private EditText userEmail;
    private EditText userPassword;
    private Button btnLogin;
    private Button btnLinkToRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        userEmail = (EditText) findViewById(R.id.userLoginEmail);
        userPassword = (EditText) findViewById(R.id.userLoginPassword);
        btnLogin = (Button) findViewById(R.id.btnUserLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnUserLinkToRegisterScreen);


       // Settings.getInstance(this).set(Settings.USER_IS_LOGGED_IN, false);
        if(Settings.getInstance(this).getBoolean(Settings.USER_IS_LOGGED_IN, false)){
            Intent intent = new Intent(UserLoginActivity.this, FlatLoginActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                if(!email.isEmpty() && !password.isEmpty()){

                    checkLogin(email, password);

                }else{

                    Toast.makeText(getApplicationContext(), "Please enter the credentials.", Toast.LENGTH_LONG).show();

                }

            }
        });
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        btnLogin.setOnClickListener(null);
        btnLinkToRegister.setOnClickListener(null);
    }

    private void checkLogin(String email, String password){

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        WebServiceManager.getInstance(this).loginUser(params);
    }

    public void nextToFlatActivity(){
        Intent intent = new Intent(UserLoginActivity.this, FlatLoginActivity.class);
        startActivity(intent);
        finish();
    }


}
