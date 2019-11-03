package com.example.gerateqrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gerateqrcode.QRCode.QrCode;
import com.example.gerateqrcode.QRCode.QrGenerate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void readQr(View view){
        Intent readIntent = new Intent(this, QrCode.class);
        startActivity(readIntent);
    }

    public void generateQr(View view){
        Intent generateIntent = new Intent(this, QrGenerate.class);
        startActivity(generateIntent);
    }
}
