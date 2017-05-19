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
import java.util.Set;

public class FlatLoginActivity extends AppCompatActivity {

    private EditText flatAddress;
    private EditText flatPassword;
    private Button btnLogin;
    private Button btnLinkToRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_login);

        flatAddress = (EditText) findViewById(R.id.flatLoginAddress);
        flatPassword = (EditText) findViewById(R.id.flatLoginPassword);
        btnLogin = (Button) findViewById(R.id.btnFlatLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnFlatLinkToRegisterScreen);

        if(Settings.getInstance(this).getBoolean(Settings.USER_IS_LOGGED_IN,false) && Settings.getInstance(this).getBoolean(Settings.FLAT_IS_LOGGED_IN, false)){
            Intent intent = new Intent(FlatLoginActivity.this, MainActivity.class);
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
                String address = flatAddress.getText().toString().trim();
                String password = flatPassword.getText().toString().trim();

                if(!address.isEmpty() && !password.isEmpty()){

                    flatAddress.setText("");
                    flatPassword.setText("");
                    checkFlat(address, password);

                }else{
                    Toast.makeText(getApplicationContext(), "Please enter the credentials!", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlatLoginActivity.this, FlatRegisterActivity.class);
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

    private void checkFlat(String address, String password){
        Map<String, String> params = new HashMap<>();
        params.put("address", address);
        params.put("password", password);
        params.put("user_id", Settings.getInstance(this).get(Settings.USER_ID));


        WebServiceManager.getInstance(this).loginFlat(this, params);
    }

    public void nextToMainActivity(){
        Intent intent = new Intent(FlatLoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
