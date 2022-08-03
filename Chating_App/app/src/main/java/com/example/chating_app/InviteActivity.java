package com.example.chating_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.chating_app.api.TransferApi;
import com.example.chating_app.databinding.ActivityInviteBinding;
import com.example.chating_app.repository.UserRepository;

public class InviteActivity extends AppCompatActivity {

    private ActivityInviteBinding binding;
    private UserRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_Dark);
        } else {
            setTheme(R.style.Theme_Light);
        }
        super.onCreate(savedInstanceState);
        binding = ActivityInviteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserRepository.getInstance();

        binding.addContact.setOnClickListener(v -> {
            String username = binding.InviteUsername.getText().toString();
            String name = binding.InviteName.getText().toString();
            String server = binding.InviteServer.getText().toString();
            if (server.trim().length() == 0 || name.trim().length() == 0 || username.trim().length() == 0) {
                return;
            }
            String[] preServer = server.split(":");
            TransferApi inviteApi = new TransferApi("http://10.0.2.2:" + preServer[1] + "/api/");
            repository.addContact(username, name, server);
            inviteApi.invite(User_Token.username, username, server);
            binding.InviteUsername.setText("");
            binding.InviteName.setText("");
            binding.InviteServer.setText("");
        });

        binding.returnToChat.setOnClickListener(v -> {
            finish();
        });

    }
}