package com.example.lab07;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText edUsername, edPassword, edConfirmPassword;
    private Button btnCreateUser;
    private static final String PREFS_NAME = "UserPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);
        btnCreateUser = findViewById(R.id.btnCreateUser);

        btnCreateUser.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String username = edUsername.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        String confirmPassword = edConfirmPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Сохранение логина и пароля в SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();

        Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();

        // Переход обратно в LoginActivity
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Закрываем SignUpActivity
    }
}
