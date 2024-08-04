package com.example.anew;

import android.icu.text.NumberFormat;
import android.icu.util.Currency;

import java.util.Locale;

public class Money {
    public static String doubleToMoNey(double values){
        Locale locale = new Locale("vi", "VN"); // Định dạng tiền tệ theo nước Mỹ
        Currency currency = Currency.getInstance(locale);

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        numberFormat.setCurrency(currency);

        String moneyString = numberFormat.format(values);

        return moneyString;
    }
}
