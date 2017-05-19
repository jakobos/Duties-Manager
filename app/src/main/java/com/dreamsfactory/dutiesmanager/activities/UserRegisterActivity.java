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

public class UserRegisterActivity extends AppCompatActivity {

    private EditText userName;
    private EditText userEmail;
    private EditText userPassword;
    private Button btnRegister;
    private Button btnLinkToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        userName = (EditText)findViewById(R.id.userRegName);
        userEmail = (EditText) findViewById(R.id.userRegEmail);
        userPassword = (EditText) findViewById(R.id.userRegPassword);
        btnRegister = (Button) findViewById(R.id.btnUserRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnUserLinkToLoginScreen);

        if(Settings.getInstance(this).getBoolean(Settings.USER_IS_LOGGED_IN, false)){
            Intent intent = new Intent(UserRegisterActivity.this, FlatLoginActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString().trim();
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    userName.setText("");
                    userEmail.setText("");
                    userPassword.setText("");

                    registerUser(name, email, password);

                }else{
                    Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserRegisterActivity.this, UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        btnRegister.setOnClickListener(null);
        btnLinkToLogin.setOnClickListener(null);
    }

    private void registerUser(String username, String email, String password){

        Map<String, String> params = new HashMap<>();
        params.put("name", username);
        params.put("email", email);
        params.put("password", password);
        WebServiceManager.getInstance(this).registerUser(this, params);
    }
    public void backToLoginActivity(){
        Intent intent = new Intent(UserRegisterActivity.this, UserLoginActivity.class);
        startActivity(intent);
        finish();
    }
}
