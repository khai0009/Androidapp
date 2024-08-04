package com.example.anew;

import static android.graphics.BitmapFactory.decodeResource;
import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.widget.ImageView;

import java.io.File;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Bank.sqlite3";

    private static final String TABLE_ACCOUNT = "TAIKHOAN";
    private static final String COLUMN_ACCOUNTNUMBER = "AccountNumber";
    private static final String COLUMN_PASSWORD = "Password";
    private static final String COLUMN_BALANCE = "Balance";

    private static final String TABLE_CUSTOMERS = "customers";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_ID_CARD = "id_card";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_IMG = "image";
    private static final String TABLE_TEMPACCOUNT = "TempAccount";
    private static final String COLUMN_ID = "ID";
    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE " + TABLE_CUSTOMERS + "("
            + COLUMN_PHONE + " TEXT PRIMARY KEY,"
            + COLUMN_FULL_NAME + " TEXT,"
            + COLUMN_ID_CARD + " TEXT,"
            + COLUMN_GENDER + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_ADDRESS + " TEXT,"
            + COLUMN_IMG + " BLOB);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TAIKHOAN(AccountNumber TEXT PRIMARY KEY," +
                "Password TEXT, Balance REAL)");
        db.execSQL(CREATE_TABLE_CUSTOMERS);
        db.execSQL("CREATE TABLE TempAccount(ID TEXT PRIMARY KEY," +
                "AccountNumber TEXT)");
        db.execSQL("INSERT INTO TempAccount values('1','-1')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TAIKHOAN");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPACCOUNT );
        onCreate(db);
    }
    public boolean addAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ACCOUNTNUMBER, account.getAccountNumber());
        contentValues.put(COLUMN_PASSWORD,account.getPassword());
        contentValues.put(COLUMN_BALANCE, account.getBalance());
        long result = db.insert(TABLE_ACCOUNT,null, contentValues);
        if(result == -1) return false;
        else return true;
    }

    public boolean addCustomer(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE, user.getPhoneNumber());
        values.put(COLUMN_FULL_NAME, user.getHoTen());
        values.put(COLUMN_ID_CARD, user.getCCCD());
        values.put(COLUMN_GENDER, user.getGioiTinh());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_ADDRESS, user.getDiaChi());
            long result = db.insert(TABLE_CUSTOMERS, null, values);
        if(result == -1) return false;
        else return true;
    }
    public Boolean checkPassword(String password){
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("Select * from " + TABLE_ACCOUNT +
                " where " + COLUMN_PASSWORD + " = ?",new String[] {password});

        if(cursor.getCount() > 0)
            return true;
        else
            return false;

    }
    public Boolean checkAccountNumber(String accountNumber){
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("Select * from " + TABLE_ACCOUNT +
                " where " + COLUMN_ACCOUNTNUMBER + " = ?",new String[] {accountNumber});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public int update(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACCOUNTNUMBER, account.getAccountNumber());
        values.put(COLUMN_PASSWORD, account.getPassword());
        values.put(COLUMN_BALANCE, account.getBalance());
        return db.update(TABLE_ACCOUNT, values, COLUMN_ACCOUNTNUMBER + " = ?",
                new String[]{String.valueOf(account.getAccountNumber())});
    }
    public void updateimg(User user,byte[] anh){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMG,anh);
        int capnhat = db.update(TABLE_CUSTOMERS,values,COLUMN_PHONE+" = ? ",new String[]{user.getPhoneNumber()});
    }

    public void updateBalance(String accountNumber, Double balance) {
        // Tạo một đối tượng SQLiteDatabase từ DatabaseHelper (dbHelper là đối tượng của lớp DatabaseHelper)
        SQLiteDatabase db = getWritableDatabase();
        // Tạo một đối tượng ContentValues để lưu dữ liệu cần cập nhật
        ContentValues values = new ContentValues();
        values.put(COLUMN_BALANCE, balance); // Điền các giá trị cần cập nhật vào ContentValues

        // Định nghĩa điều kiện WHERE cho việc cập nhật (ví dụ: cập nhật dựa trên ID)
        String selection = COLUMN_ACCOUNTNUMBER + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(accountNumber)};

        // Thực hiện cập nhật dữ liệu
        int count = db.update(TABLE_ACCOUNT, values, selection, selectionArgs);

        // Đóng cơ sở dữ liệu sau khi hoàn tất cập nhật
        db.close();
    }
    public void updatePassword(String accountNumber, String password) {
        // Tạo một đối tượng SQLiteDatabase từ DatabaseHelper (dbHelper là đối tượng của lớp DatabaseHelper)
        SQLiteDatabase db = getWritableDatabase();
        // Tạo một đối tượng ContentValues để lưu dữ liệu cần cập nhật
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, password); // Điền các giá trị cần cập nhật vào ContentValues

        // Định nghĩa điều kiện WHERE cho việc cập nhật (ví dụ: cập nhật dựa trên ID)
        String selection = COLUMN_ACCOUNTNUMBER + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(accountNumber)};

        // Thực hiện cập nhật dữ liệu
        int count = db.update(TABLE_ACCOUNT, values, selection, selectionArgs);

        // Đóng cơ sở dữ liệu sau khi hoàn tất cập nhật
        db.close();
    }

    @SuppressLint("Range")
    public Account getAccountDataWithAccountNumber(String accountNumber) {
        Account account = new Account();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_ACCOUNT + " WHERE " + COLUMN_ACCOUNTNUMBER + " =?",
                new String[]{String.valueOf(accountNumber)});
        if (cursor.moveToFirst()) {
            account.setAccountNumber(cursor.getString(cursor.getColumnIndex(COLUMN_ACCOUNTNUMBER)));
            account.setBalance(cursor.getDouble(cursor.getColumnIndex(COLUMN_BALANCE)));
            account.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
        }
        cursor.close();
        return account;
    }
    @SuppressLint("Range")
        public User getUserDataWithAccountNumber(String phoneNumber) {
        User user = new User();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_CUSTOMERS + " WHERE " + COLUMN_PHONE + " =?",
                new String[]{String.valueOf(phoneNumber)});
        if (cursor.moveToFirst()) {
            user.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
            user.setHoTen(cursor.getString(cursor.getColumnIndex(COLUMN_FULL_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            user.setGioiTinh(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
            user.setDiaChi(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
            user.setCCCD(cursor.getString(cursor.getColumnIndex(COLUMN_ID_CARD)));
            user.setImg(cursor.getBlob(cursor.getColumnIndex(COLUMN_IMG)));
        }
        cursor.close();
        return user;
    }
    public void addTempAccount(String accountNumber){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_TEMPACCOUNT + " SET " + COLUMN_ACCOUNTNUMBER + " = "
                + accountNumber + " WHERE " + COLUMN_ID + " = 1");
        db.close();
    }
    public void removeTempAccount(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_TEMPACCOUNT + " SET " + COLUMN_ACCOUNTNUMBER
                + " = -1 WHERE " + COLUMN_ID + " = 1");
        db.close();
    }
    @SuppressLint("Range")
    public String getTempAccountNumber() {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        String columnToQuery = COLUMN_ACCOUNTNUMBER;
        String table = TABLE_TEMPACCOUNT;
        String condition = COLUMN_ID + " = 1";

        String query = "SELECT " + columnToQuery + " FROM " + table + " WHERE " + condition;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(columnToQuery));
            // Xử lý giá trị được truy vấn tại đây
            cursor.close();
        }
        return result;
    }
    public void deleteAccount(String accountNumber){
        SQLiteDatabase db = getWritableDatabase();
        String tableAccount = TABLE_ACCOUNT;
        String tableUser = TABLE_CUSTOMERS;

        String selection = COLUMN_ACCOUNTNUMBER + " = ?";
        String[] selectionArgs = { String.valueOf(accountNumber) };

        db.delete(tableAccount, selection, selectionArgs);
        db.delete(tableUser, selection, selectionArgs);
        db.close();
    }
    public boolean isNullTempAccount(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_TEMPACCOUNT +
                " where " + COLUMN_ACCOUNTNUMBER + " = ?",new String[] {"-1"});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public Bitmap draw(User user, Context context){
       byte[] anh = user.getImg();
        String encode = Base64.encodeToString(anh,Base64.DEFAULT);
        anh = Base64.decode(encode, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(anh,0,anh.length);
        Bitmap scale = Bitmap.createScaledBitmap(bitmap,150,150,false);
        return scale;
    }


}