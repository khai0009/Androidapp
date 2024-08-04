package com.example.anew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    public Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        EditText matkhau = findViewById(R.id.registerPassword);
        EditText matkhau2 = findViewById(R.id.rePassword);
        EditText sodienthoai = findViewById(R.id.phoneNumber);
        EditText name = findViewById(R.id.hoTen);
        EditText address = findViewById(R.id.address);
        EditText idCard = findViewById(R.id.cmnd);
        EditText email = findViewById(R.id.email);
        RadioButton nam = findViewById(R.id.gioiTinhNam);
        RadioButton nu = findViewById(R.id.gioiTinhNu);
        Button registerButton = findViewById(R.id.tao);

        matkhau.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {
               if(editable.length() >= 8) {
                   matkhau2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                   matkhau2.setEnabled(true);
               }
               else {
                   matkhau.setError("Mật khẩu ít nhất 8 đến 16 kí tự!");
                   matkhau2.setText(null);
                   matkhau2.setBackgroundColor(Color.parseColor("#808080"));
                   matkhau2.setEnabled(false);
               }

           }
       }

        );
        matkhau2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!matkhau.getText().toString().equals(matkhau2.getText().toString()))
                    matkhau2.setError("Mật khẩu không khớp");
            }
        });
        sodienthoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() == 10) {
                    registerButton.setEnabled(true);
                }
                else {
                    sodienthoai.setError("Số điện thoại sai định dạng (10 số)");
                    registerButton.setEnabled(false);
                }
            }
        });

        Account newAccount = new Account();
        User newUser = new User();
        DatabaseHelper dangky = new DatabaseHelper(this);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String soDienThoai = sodienthoai.getText().toString();
                    String hoTen = name.getText().toString();
                    String diaChi = address.getText().toString();
                    String cccd = idCard.getText().toString();
                    String emailAddress = email.getText().toString();
                    String gioiTinh;
                    if(nam.isChecked()){
                        gioiTinh = nam.getText().toString();
                    }else{
                        gioiTinh = nu.getText().toString();
                    }

                    String username = sodienthoai.getText().toString();
                    String password = matkhau.getText().toString();

                    newAccount.setAccountNumber(username);
                    newAccount.setPassword(password);
                    newAccount.setBalance(1000000);

                    newUser.setCCCD(cccd);
                    newUser.setEmail(emailAddress);
                    newUser.setDiaChi(diaChi);
                    newUser.setGioiTinh(gioiTinh);
                    newUser.setHoTen(hoTen);
                    newUser.setPhoneNumber(soDienThoai);

                    if (dangky.checkAccountNumber(username) == true) {
                        Toast toast = Toast.makeText(RegisterActivity.this,
                                "Tài khoản đã tồn tại", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        boolean insAccount = dangky.addAccount(newAccount);
                        boolean insUser = dangky.addCustomer(newUser);
                        if (insAccount && insUser) {
                            Toast toast = Toast.makeText(RegisterActivity.this,
                                    "Đăng kí thành công", Toast.LENGTH_SHORT);
                            toast.show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast toast = Toast.makeText(RegisterActivity.this,
                                    "Đăng kí thất bại, đã xảy ra lỗi!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }

        });


    }
    @Override
    protected void onPause() {
        super.onPause();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onDestroy();
            }
        }, 30000);
    }
    @Override
    protected void onStart() {
        super.onStart();
        handler.removeCallbacks(null);
    }
}