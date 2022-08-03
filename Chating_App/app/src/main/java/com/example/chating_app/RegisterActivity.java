package com.example.chating_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.chating_app.Data.AppDB;
import com.example.chating_app.Data.User;
import com.example.chating_app.Data.UserDao;
import com.example.chating_app.api.ContactsAPI;
import com.example.chating_app.databinding.ActivityRegisterBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private ContactsAPI api;
    private UserDao userDao;
    private AppDB db;
    private MutableLiveData<String> token;
    ImageView IVPreviewImage;
    Button BSelectImage;
    private String username;
    private String name;
    private String password;
    private byte[] byteArray;


    int SELECT_PICTURE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.moveToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
        BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        api = new ContactsAPI();
        token = new MutableLiveData<String>();

        BSelectImage.setOnClickListener(v -> imageChooser());

        token.observe(this, tok -> {
            if (Objects.equals(tok, "ok")) {
                Intent intent = new Intent(this, LoginActivity.class);
                db = Room.databaseBuilder(getApplicationContext(), AppDB.class, username)
                    .allowMainThreadQueries().build();
                userDao = db.userDao();
                User user = new User(username, name, password, byteArray);
                userDao.insertUser(user);
                startActivity(intent);
            }
        });
        binding.registerBtn.setOnClickListener(v -> {
            username = binding.registerUsername.getText().toString();
            name = binding.registerName.getText().toString();
            password = binding.registerPassword.getText().toString();
            api.register(username, name, password, token);
        });
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byteArray = stream.toByteArray();
                        bitmap.recycle();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
}