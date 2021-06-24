package com.client.kuuf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    TextView tv_username;
    TextView tv_gender;
    TextView tv_phoneNum;
    TextView tv_wallet;
    TextView tv_dob;
    RadioGroup rg_topup;
    EditText et_password;
    Button btn_confirm;
    int topup_nominal;
    int loggedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final UsersDB usersDB = new UsersDB(ProfileActivity.this);
        tv_username = findViewById(R.id.tv_username);
        tv_gender = findViewById(R.id.tv_gender);
        tv_phoneNum = findViewById(R.id.tv_phoneNum);
        tv_wallet = findViewById(R.id.tv_wallet);
        tv_dob = findViewById(R.id.tv_dob);
        rg_topup = findViewById(R.id.rg_topup);
        et_password = findViewById(R.id.et_password);
        btn_confirm = findViewById(R.id.btn_confirm);

        loggedID = getIntent().getIntExtra("loggedId", 0);

        final String username = usersDB.getUserUsername(loggedID);
        final String password = usersDB.getUserPassword(loggedID);
        final String phoneNum = usersDB.getUserPhone(loggedID);
        final String gender = usersDB.getUserGender(loggedID);
        final int wallet = usersDB.getUserWallet(loggedID);
        final String dob = usersDB.getUserDob(loggedID);

        tv_username.setText(username);
        tv_gender.setText(gender);
        tv_phoneNum.setText(phoneNum);
        tv_wallet.setText("Rp. " + wallet);
        tv_dob.setText(dob);

        rg_topup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_250:
                        topup_nominal = 250000;
                        break;

                    case R.id.rb_500:
                        topup_nominal = 500000;
                        break;

                    case R.id.rb_1000:
                        topup_nominal = 1000000;
                        break;
                }
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rg_topup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(ProfileActivity.this, "Please choose a top up nominal", Toast.LENGTH_SHORT).show();
                }
                else if(!et_password.getText().toString().equals(usersDB.getUserPassword(loggedID))){
                    Toast.makeText(ProfileActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                }
                else{
                    usersDB.UpdateUser(new Users(username, password, phoneNum, gender, wallet, dob), topup_nominal, loggedID);

                    Toast.makeText(ProfileActivity.this, "Top Up Successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                    intent.putExtra("loggedId", loggedID);
                    startActivity(intent);
                }
            }
        });

    }
}