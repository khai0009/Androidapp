package com.example.anew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class TransferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper db = new DatabaseHelper(this);
        setContentView(R.layout.activity_transfer);
        TextView accountNumberView = findViewById(R.id.accountNumberResc);
        TextView balanceView = findViewById(R.id.balanceResc);
        Button transferButton = findViewById(R.id.btnTransfer);
        EditText accountNumber = findViewById(R.id.accountNumberEditText);
        EditText values = findViewById(R.id.valueEditText);
        EditText noiDung = findViewById(R.id.noiDungCK);
        ImageView back = findViewById(R.id.backInfo2);
        String mainAccountNumber;
        Intent data = getIntent();
        if(data != null){
            mainAccountNumber = data.getStringExtra("AccountNumber");
            accountNumberView.setText(data.getStringExtra("AccountNumber"));
            balanceView.setText(data.getStringExtra("Balance"));
        } else {
            mainAccountNumber = "";
        }
        Account accountFrom = db.getAccountDataWithAccountNumber(mainAccountNumber);

        accountNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() != 10) {
                    accountNumber.setError("Số tài khoản không hợp lệ!");
                }
            }
        });
        values.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() < 1) {
                    values.setError("vui lòng nhập số tiền!");
                    transferButton.setEnabled(false);
                } else if(Double.parseDouble(editable.toString()) > accountFrom.getBalance()) {
                    values.setError("Tài khoản bạn nghèo nhập ít thôi!");
                    transferButton.setEnabled(false);
                } else transferButton.setEnabled(true);
            }
        });

        transferButton.setOnClickListener(view -> {
            if(db.checkAccountNumber(accountNumber.getText().toString())){

                String accountNumberTo = accountNumber.getText().toString();
                Account accountTo = db.getAccountDataWithAccountNumber(accountNumberTo);

                Double valuesGet = Double.parseDouble(values.getText().toString());
                db.updateBalance(mainAccountNumber,
                        accountFrom.getBalance() - valuesGet);
                db.updateBalance(accountNumberTo,
                        accountTo.getBalance() + valuesGet);
                Intent intent = new Intent(TransferActivity.this, ResultActivity.class);
                intent.putExtra("AccountNumber",
                        mainAccountNumber);
                intent.putExtra("TransferAccountNumber",
                        accountNumberTo);
                intent.putExtra("Values",
                        values.getText().toString());
                startActivity(intent);
                transferButton.setEnabled(true);
            }else{
                accountNumber.setError("Tài khoản không tồn tại!");
                transferButton.setEnabled(false);
            }
        });
        back.setOnClickListener(view -> {
            finish();
        });

    }
}