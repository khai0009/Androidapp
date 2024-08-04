
package com.example.anew;

public class Account {
    private  String  accountNumber;
    private String password;
    private double balance;
    public Account(String accountNumber, double balance, String password) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.password = password;
    }
    public Account(){

    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public double getBalance(){
        return  balance;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
}
