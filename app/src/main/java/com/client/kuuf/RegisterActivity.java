package com.client.kuuf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    EditText et_username, et_phone, et_dob, et_password, et_confirmPassword;
    Button buttonRegister, buttonLogin;
    CheckBox cb_terms;
    RadioGroup rg_gender;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final UsersDB usersDB = new UsersDB(RegisterActivity.this);
        et_dob = findViewById(R.id.et_dob);
        et_username = findViewById(R.id.et_username);
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        buttonRegister = findViewById(R.id.btn_register);
        buttonLogin = findViewById(R.id.btn_login);
        et_confirmPassword = findViewById(R.id.et_confirmPass);
        cb_terms = findViewById(R.id.checkBoxTermCondi);
        rg_gender = findViewById(R.id.rg_gender);

        buttonLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i=new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButtonMale:
                        gender = "Male";
                        break;

                    case R.id.radioButtonFemale:
                        gender = "Female";
                        break;
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String confirm_password = et_confirmPassword.getText().toString();
                String dob = et_dob.getText().toString();
                String phoneNum = et_phone.getText().toString();

                if(username.length() < 6 || username.length() > 12){
                    Toast.makeText(getApplicationContext(),"Username length must between 6-12 characters", Toast.LENGTH_SHORT).show();
                }
                else if (password.length() <= 8) {
                    Toast.makeText(getApplicationContext(),"Password length must be more than 8 characters", Toast.LENGTH_SHORT).show();
                }
                else if (!password.matches("[a-zA-Z0-9]+")){
                    Toast.makeText(getApplicationContext(),"Password must contains alphanumeric", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(confirm_password)){
                    Toast.makeText(getApplicationContext(),"Password and confirm password must be the same", Toast.LENGTH_SHORT).show();
                }
                else if (dob.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Date of birth must be filled", Toast.LENGTH_SHORT).show();
                }
                else if (phoneNum.length() < 10 || phoneNum.length() > 12){
                    Toast.makeText(getApplicationContext(),"Phone number length must between 10-12 characters", Toast.LENGTH_SHORT).show();
                }
                else if(!phoneNum.matches("[0-9]+")){
                    Toast.makeText(getApplicationContext(), "Phone number must contain only numbers", Toast.LENGTH_SHORT).show();
                }
                else if (rg_gender.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(),"Gender must be selected", Toast.LENGTH_SHORT).show();
                }
                else if(cb_terms.isChecked() == false){
                    Toast.makeText(getApplicationContext(),"Must agree to terms and conditions", Toast.LENGTH_SHORT).show();
                }
                else {
                    Users user = new Users(username, password, phoneNum, gender, 0, dob);

                    boolean success = usersDB.InsertUser(user);

                    if(success == true){
//                        Log.i("check", "onClick: " + usersDB.getUserPassword(2));
                        Toast.makeText(getApplicationContext(),"Registration Success!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "INSERT ERROR", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_dob.setText(sdf.format(myCalendar.getTime()));

    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}