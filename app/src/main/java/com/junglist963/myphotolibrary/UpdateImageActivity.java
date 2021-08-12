package com.junglist963.myphotolibrary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateImageActivity extends AppCompatActivity {

    private ImageView imgUpdateImage;
    private EditText edtTxtUpdateTitle, edtTxtUpdateDescription;
    private Button btnUpdateImage;

    private String title, description;
    private byte[] image;
    private int id;

    private Bitmap selectedImage;
    private Bitmap scaledImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Update Image");
        setContentView(R.layout.activity_update_image);

        imgUpdateImage = findViewById(R.id.imgUpdateImage);
        edtTxtUpdateTitle = findViewById(R.id.edtTxtUpdateTitle);
        edtTxtUpdateDescription = findViewById(R.id.edtTxtUpdateDescription);
        btnUpdateImage = findViewById(R.id.btnUpdateImage);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        image = getIntent().getByteArrayExtra("image");

        edtTxtUpdateTitle.setText(title);
        edtTxtUpdateDescription.setText(description);
        imgUpdateImage.setImageBitmap(BitmapFactory.decodeByteArray(image, 0 , image.length));

        imgUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imageIntent, 5);
            }
        });
        btnUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }
    public void updateData() {

        if (id == -1){
            Toast.makeText(this, "There is a problem!", Toast.LENGTH_SHORT).show();
        }else {
            String updateTitle = edtTxtUpdateTitle.getText().toString();
            String updateDescription = edtTxtUpdateDescription.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("id", id);
            intent.putExtra("updateTitle", updateTitle);
            intent.putExtra("updateDescription", updateDescription);

            if (selectedImage == null) {
                intent.putExtra("image", image);
            } else {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                scaledImage = makeSmall(selectedImage, 300);
                scaledImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
                byte[] image = outputStream.toByteArray();
                intent.putExtra("image", image);
            }
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 5 && resultCode == RESULT_OK && data != null){
            try {
                if(Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), data.getData());
                    selectedImage = ImageDecoder.decodeBitmap(source);
                }else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                }
                imgUpdateImage.setImageBitmap(selectedImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public Bitmap makeSmall (Bitmap image, int maxSize){
        int width = image.getWidth();
        int height = image.getHeight();

        float ratio = (float) width / (float) height;

        if (ratio > 1 ){
            width = maxSize;
            height = (int) (width / ratio);
        }else {
            height = maxSize;
            width = (int) (height * ratio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

}