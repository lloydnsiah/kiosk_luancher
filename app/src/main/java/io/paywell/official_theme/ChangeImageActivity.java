package io.paywell.official_theme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import io.paywell.official_theme.extras.TinyDB;

public class ChangeImageActivity extends AppCompatActivity {

    private Button btn_CImage,btn_CLogo,btn_save;
    private ImageView image1,image2,btn_back;
    private Context context;
    private int REQUEST_CODE_IMAGE_1 = 1;
    private int REQUEST_CODE_IMAGE_2 = 2;
    private TinyDB tinyDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_image);

        init();
        tinyDB = new TinyDB(context);





        btn_CImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_IMAGE_1);
            }
        });

        btn_CLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_IMAGE_2);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



    private void setImages() {
        String image_bg = tinyDB.getString("Background_Image");
        if (!image_bg.isEmpty()){
        image1.setImageURI(Uri.parse(image_bg));
        }
        String image_logo = tinyDB.getString("Logo_Image");
        if (!image_logo.isEmpty()){
        image2.setImageURI(Uri.parse(image_logo));
        }
    }

    public void init(){
        btn_CImage = findViewById(R.id.btn_change_backgroundImage);
        btn_CLogo = findViewById(R.id.btn_change_backgroundLogo);
        context = getApplicationContext();
        image1 = findViewById(R.id.change_background_image);
        image2 = findViewById(R.id.change_background_logo);
        btn_save = findViewById(R.id.btn_Save_settings);
        btn_back = findViewById(R.id.edit_change_image_back);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE_1 && resultCode == Activity.RESULT_OK){
            Uri image = data.getData();
            image1.setImageURI(image);
            tinyDB.putString("Background_Image",String.valueOf(image));


        }else if(requestCode == REQUEST_CODE_IMAGE_2 && resultCode == Activity.RESULT_OK){
            Uri image = data.getData();
           image2.setImageURI(image);
           tinyDB.putString("Logo_Image",String.valueOf(image));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setImages();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context,SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}