package com.example.gerateqrcode.QRCode;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gerateqrcode.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class QrGenerate extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generate);
    }

    public void qrGenerator(View view){
        try {
            hideKeyboardFrom(this, view);
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            int width = point.x;
            int height = point.y;
            int smaillesDimension = width < height ? width : height;

            EditText qrInput = (EditText) findViewById(R.id.qrInput);
            String qrCodeData = qrInput.getText().toString();

            String charSet = "UTF-8"; // ISO-8859-1
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            createQRCode(qrCodeData, charSet, hintMap, smaillesDimension, smaillesDimension);

        }catch (Exception e){
            Toast.makeText(this, "Error -> " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void createQRCode(String qrCodeData, String charSet, Map hintMap, int qrCodeheight, int qrCodewidth) {
        try{
            BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charSet), charSet), BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight);

            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];

            for (int i = 0; i < height; i++){
                int offSet = i * width;
                for (int j = 0; j < height; j++){
                    pixels[offSet + j] = matrix.get(i, j) ? BLACK : WHITE;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

            ImageView img = (ImageView) findViewById(R.id.imageView1);
            img.setImageBitmap(bitmap);

        } catch (Exception e){
            Toast.makeText(this, "Campo texto vazio ", Toast.LENGTH_LONG).show();
            Log.e("QrGenerate",e.getMessage());
        }
    }


    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
