package com.example.post_hw;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.post_hw.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.save.setOnClickListener(V-> startSecondActivity());
        binding.pic.setOnClickListener(v-> getPhoto());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }


    }

    private void getPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultlauncher.launch(intent);
    }

    private ActivityResultLauncher<String> requestPermissionLauncher=
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted ->{
                if (isGranted){
                    Toast.makeText(this,"권한이 설정되었습니다", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "권한이 설정되지 않았습니다 권한이 없으므로 앱을 종료합니다", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

    public void startSecondActivity(){
        Intent intent = new Intent( this, SecondActivity.class);
        String message= binding.EditText.getText().toString();
        intent.putExtra("EXTRA_MESSAGE",message);
        String name= binding.TextView.getText().toString();
        intent.putExtra("EXTRA_NAME",name);



        startActivity(intent);

    }

    ActivityResultLauncher<Intent> resultlauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        Intent data = result.getData();
                        Log.i("TEST", "DATA: "+ data);

                        if (result.getResultCode()==RESULT_OK && null != data) {
                            Uri selectImage = data.getData();
                            Bitmap bitmap = loadBitmap(selectImage);
                            binding.image.setImageBitmap(bitmap);

                            Intent intent = new Intent(this, SecondActivity.class);
                            intent.putExtra("IMAGE", selectImage);
                        }


                    });

    private Bitmap loadBitmap(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        return BitmapFactory.decodeFile(picturePath);

    }

}