package com.example.anew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button registerButton = findViewById(R.id.regsiter);
        Button loginButton = findViewById(R.id.login);
        Button otherAccount = findViewById(R.id.otherAccount);
        Button fogotpass = findViewById(R.id.fogotpw);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        TextView hoTen = findViewById(R.id.hoTenLogin);
        TextView accountNumber = findViewById(R.id.accountNumberLogin);
        DatabaseHelper db = new DatabaseHelper(this);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() < 10) {
                    username.setError("Số điện thoại sai định dạng (10 số)");
                    password.setEnabled(false);
                    password.setBackgroundColor(Color.parseColor("#808080"));
                }else{
                    password.setEnabled(true);
                    password.setBackgroundColor(Color.parseColor("#FFFFFF"));

                }
            }
        });

        if(db.isNullTempAccount()){
            hoTen.setVisibility(View.GONE);
            accountNumber.setVisibility(View.GONE);
            otherAccount.setVisibility(View.GONE);
        }else{
            Account account = db.getAccountDataWithAccountNumber(db.getTempAccountNumber());
            User user = db.getUserDataWithAccountNumber(db.getTempAccountNumber());
            accountNumber.setText("Số tài khoản: ******" +
                    account.getAccountNumber().substring(6));
            hoTen.setText(user.getHoTen());
            password.setEnabled(true);
            password.setBackgroundColor(Color.parseColor("#FFFFFF"));
            username.setVisibility(View.GONE);
            registerButton.setVisibility(View.GONE);
        }
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        DatabaseHelper login = new DatabaseHelper(this);
        loginButton.setOnClickListener(view -> {
            String getUsername = username.getText().toString();
            String getPassword = password.getText().toString();
            if(!db.isNullTempAccount()) getUsername = db.getTempAccountNumber();
            Boolean checkUsername = login.checkAccountNumber(getUsername);
            Boolean checkPassword = login.checkPassword(getPassword);
            if(checkUsername == true){
                if(checkPassword == true){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("AccountNumber",
                            getUsername);
                    db.addTempAccount(getUsername);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,
                            "Sai mật khẩu",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(LoginActivity.this,
                        "Tài khoản không tồn tại",Toast.LENGTH_SHORT).show();
            }
        });
        otherAccount.setOnClickListener(view -> {
            hoTen.setVisibility(View.GONE);
            accountNumber.setVisibility(View.GONE);
            otherAccount.setVisibility(View.GONE);
            username.setVisibility(View.VISIBLE);
            registerButton.setVisibility(View.VISIBLE);
        });
        fogotpass.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this,ChangepwActivity.class));
        });
    }
}