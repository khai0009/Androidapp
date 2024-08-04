package com.example.anew;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

public class ChangepwActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepw);
        ImageView back = findViewById(R.id.back);
        EditText cccd = findViewById(R.id.Cccdchange);
        EditText sdt = findViewById(R.id.accountchange);
        EditText pw = findViewById(R.id.Passwordchange);
        EditText repw = findViewById(R.id.rePasswordchange);
        cccd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(cccd.getText().toString().toString().length() == 12 && sdt.getText().toString().length() == 10 )
                {
                    pw.setEnabled(true);
                    repw.setEnabled(true);
                    pw.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    repw.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                else{
                    pw.setEnabled(false);
                    repw.setEnabled(false);
                    pw.setBackgroundColor(Color.parseColor("#808080"));
                    repw.setBackgroundColor(Color.parseColor("#808080"));
                }
            }
        });
        sdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(cccd.getText().toString().length() == 12 && sdt.getText().toString().length() == 10 )
                {
                    pw.setEnabled(true);
                    repw.setEnabled(true);
                    pw.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    repw.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                else{
                    pw.setEnabled(false);
                    repw.setEnabled(false);
                    pw.setBackgroundColor(Color.parseColor("#808080"));
                    repw.setBackgroundColor(Color.parseColor("#808080"));
                }
            }
        });
        ;
        back.setOnClickListener(view -> {
            finish();
        });
    }
}