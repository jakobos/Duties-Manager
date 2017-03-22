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

public class FlatRegisterActivity extends AppCompatActivity {

    private EditText flatAddress;
    private EditText flatPassword;
    private Button btnRegister;
    private Button btnLinkToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_register);

        flatAddress = (EditText) findViewById(R.id.flatRegAddress);
        flatPassword = (EditText) findViewById(R.id.flatRegPassword);
        btnRegister = (Button) findViewById(R.id.btnFlatRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnFlatLinkToLoginScreen);

        if(Settings.getInstance(this).getBoolean(Settings.USER_IS_LOGGED_IN,false) && Settings.getInstance(this).getBoolean(Settings.FLAT_IS_LOGGED_IN, false)){
            Intent intent = new Intent(FlatRegisterActivity.this, MainActivity.class);
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
                String address = flatAddress.getText().toString().trim();
                String password = flatPassword.getText().toString().trim();
                if(!address.isEmpty() && !password.isEmpty()){

                    registerFlat(address, password);

                }else{
                    Toast.makeText(getApplicationContext(), "Please enter flat details!", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        btnRegister.setOnClickListener(null);
        btnLinkToLogin.setOnClickListener(null);
    }
    private void registerFlat(String address, String password){

        Map<String, String> params = new HashMap<>();
        params.put("address", address);
        params.put("password", password);
        params.put("owner_id", Settings.getInstance(this).get(Settings.USER_ID));

        WebServiceManager.getInstance(this).registerFlat(params);
    }
    public void backToLoginActivity(){
        Intent intent = new Intent(FlatRegisterActivity.this, FlatLoginActivity.class);
        startActivity(intent);
        finish();
    }
}
