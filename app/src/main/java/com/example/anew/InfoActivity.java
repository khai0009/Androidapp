package com.example.anew;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.lang.Object;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.io.Util;

import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class InfoActivity extends AppCompatActivity {
    String mainAccountNumber;
    ImageView image;
    DatabaseHelper db;
    Account account;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},34);
        ImageView back = findViewById(R.id.backInfo);
        TextView accountNumber = findViewById(R.id.accountNumberInfo);
        TextView hoTen = findViewById(R.id.hoTenInfo);
        TextView diaChi = findViewById(R.id.diaChi);
        TextView cccd = findViewById(R.id.cmnd);
        TextView email = findViewById(R.id.email);
        ImageView hideButton = findViewById(R.id.hide);
        image =findViewById(R.id.imageView2);

        Button logoutButton = findViewById(R.id.logoutInfo);
        db = new DatabaseHelper(this);
        Intent data = getIntent();
        if(data != null){
            mainAccountNumber = data.getStringExtra("AccountNumber");
        }
        account = db.getAccountDataWithAccountNumber(mainAccountNumber);
        user = db.getUserDataWithAccountNumber(mainAccountNumber);
        hoTen.setText(user.getHoTen());
        diaChi.setText(user.getDiaChi());
        cccd.setText(user.getCCCD());
        email.setText(user.getEmail());
        if(user.getImg() == null) image.setImageResource(R.drawable.icons8_account_100);
        else image.setImageBitmap(db.draw(user,InfoActivity.this));
        accountNumber.setText("Số tài khoản: xxxxxx" +
                account.getAccountNumber().substring(6));
        AtomicInteger countHide = new AtomicInteger(1);
        hideButton.setOnClickListener(view -> {
            if(countHide.get() % 2 == 1){
                hideButton.setImageResource(R.drawable.icons8_eye_100);
                accountNumber.setText("Số tài khoản: " + account.getAccountNumber());
                countHide.getAndIncrement();
            }else{
                hideButton.setImageResource(R.drawable.icons8_blind_100);
                accountNumber.setText("Số tài khoản: xxxxxx" +
                        account.getAccountNumber().substring(6));
                countHide.getAndIncrement();
            }

        });

        logoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(InfoActivity.this, LoginActivity.class);
            startActivity(intent);
            db.removeTempAccount();
        });
        back.setOnClickListener(view -> {
            finish();
        });
    }
    Intent data;
    ActivityResultLauncher<Intent> upload = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri uri = data.getData();;
                        byte[] anh;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            ByteArrayOutputStream insertanh = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100,insertanh);
                            anh = insertanh.toByteArray();
                            long sizeimg = anh.length;
                            if(sizeimg/1024 <= 1024) {
                                user = db.getUserDataWithAccountNumber(mainAccountNumber);
                                db.updateimg(user, anh);
                                user = db.getUserDataWithAccountNumber(mainAccountNumber);
                                image = findViewById(R.id.imageView2);
                                image.setImageBitmap(db.draw(user, InfoActivity.this));
                            }
                            else Toast.makeText(InfoActivity.this,"Chỉ thêm ảnh dưới 1 MB",Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
    );
    ActivityResultLauncher<Intent> uploadcamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        byte[] anh;
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        ByteArrayOutputStream insertanh = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,insertanh);
                        anh = insertanh.toByteArray();
                        long sizeimg = anh.length;
                        if(sizeimg/1024 <= 1024) {
                            user = db.getUserDataWithAccountNumber(mainAccountNumber);
                            db.updateimg(user, anh);
                            user = db.getUserDataWithAccountNumber(mainAccountNumber);
                            image = findViewById(R.id.imageView2);
                            image.setImageBitmap(db.draw(user, InfoActivity.this));
                        }
                        else Toast.makeText(InfoActivity.this,"Chỉ thêm ảnh dưới 1 MB",Toast.LENGTH_LONG).show();
                    }
                }
            }
    );
    public void selectImage(View view) {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    public void galleryIntent(){
        Intent data = new Intent(Intent.ACTION_GET_CONTENT);
        data.setType("image/*");
        data= Intent.createChooser(data,"chon anh");
        upload.launch(data);

    }
    public void cameraIntent(){
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        uploadcamera.launch(camera);

    }

}