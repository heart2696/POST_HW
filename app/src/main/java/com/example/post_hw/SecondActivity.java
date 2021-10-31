package com.example.post_hw;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.post_hw.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {
    ActivitySecondBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.close.setOnClickListener(v -> finish());

        Intent intent= getIntent();
        String message= intent.getStringExtra("EXTRA_NAME");
        binding.gettext.setText(message);
        String name= intent.getStringExtra("EXTRA_MESSAGE");
        binding.getname.setText(name);

        Bundle ex = getIntent().getExtras();
        Uri uri;
        if(ex != null && ex.containsKey("IMAGE")) {
            uri = Uri.parse(ex.getString("KEY"));
            binding.getimage.setImageURI(uri);
        }
    }
}