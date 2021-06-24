package com.client.kuuf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText et_username, et_password;
    Button btn_login, btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final UsersDB usersDB = new UsersDB(LoginActivity.this);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameInput = et_username.getText().toString();
                String passwordInput = et_password.getText().toString();

                if(usernameInput.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Username must be filled", Toast.LENGTH_SHORT).show();
                }
                else if(passwordInput.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Password must be filled", Toast.LENGTH_SHORT).show();
                }
                else if(usersDB.checkUser(usernameInput, passwordInput) == true){
                    int userID = usersDB.checkUserID(usernameInput, passwordInput);
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    i.putExtra("loggedId", userID);
                    startActivity(i);
                    finish();
                }
                else if(usersDB.checkUser(usernameInput, passwordInput) == false){
                    Toast.makeText(LoginActivity.this, "Username and password does not match or not registered", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}