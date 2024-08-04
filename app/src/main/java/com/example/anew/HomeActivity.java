package com.example.anew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    String mainAccountNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DatabaseHelper db = new DatabaseHelper(this);
        TextView accountNumber = findViewById(R.id.accountNumber);
        TextView balance = findViewById(R.id.balance);
        TextView name = findViewById(R.id.name);
        ImageView transferButton = findViewById(R.id.btnMoneyTransfer);
        ImageView logoutButton = findViewById(R.id.logoutButton);
        ImageView infoButton = findViewById(R.id.infoButton);

        Intent data = getIntent();
        if(data != null){
            mainAccountNumber = data.getStringExtra("AccountNumber");
            if(mainAccountNumber != null){
                final Account account = db.getAccountDataWithAccountNumber(mainAccountNumber);
                final User user = db.getUserDataWithAccountNumber(mainAccountNumber);
                accountNumber.setText(account.getAccountNumber());
                name.setText(user.getHoTen());
                balance.setText(Money.doubleToMoNey(account.getBalance()));
            }
        } else {
            mainAccountNumber = "";
        }
        transferButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, TransferActivity.class);
            intent.putExtra("AccountNumber",
                    mainAccountNumber);
            intent.putExtra("Balance",
                    balance.getText().toString());
            startActivity(intent);
        });

        logoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            db.removeTempAccount();
        });

        infoButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, InfoActivity.class);
            intent.putExtra("AccountNumber",
                    mainAccountNumber);
            startActivity(intent);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        TextView accountNumber = findViewById(R.id.accountNumber);
        TextView balance = findViewById(R.id.balance);
        TextView name = findViewById(R.id.name);
        DatabaseHelper db = new DatabaseHelper(this);
        Intent data = getIntent();
        if(data != null) {
            mainAccountNumber = data.getStringExtra("AccountNumber");
            if (mainAccountNumber != null) {
                final Account account = db.getAccountDataWithAccountNumber(mainAccountNumber);
                final User user = db.getUserDataWithAccountNumber(mainAccountNumber);
                accountNumber.setText(account.getAccountNumber());
                name.setText(user.getHoTen());
                balance.setText(Money.doubleToMoNey(account.getBalance()));
            }
        }
    }
}