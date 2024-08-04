package com.example.anew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    Account account = new Account();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper db = new DatabaseHelper(this);
        setContentView(R.layout.activity_result);
        TextView resValues = findViewById(R.id.resValues);
        TextView resAccountNumber = findViewById(R.id.resAccountNumber);
        TextView balance = findViewById(R.id.sodu);
        Button backToHomeButton = findViewById(R.id.btnBackToHome);

        Intent data = getIntent();
        if(data != null){
            double values = Double.parseDouble(data.getStringExtra("Values"));
            resValues.setText(Money.doubleToMoNey(values));
            resAccountNumber.setText(data.getStringExtra("AccountNumber"));
            account = db.getAccountDataWithAccountNumber(resAccountNumber.getText().toString());
            balance.setText(Money.doubleToMoNey(account.getBalance()));
        }

        backToHomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
            intent.putExtra("AccountNumber",
                    data.getStringExtra("AccountNumber"));
            startActivity(intent);
            finish();
        });
    }
}