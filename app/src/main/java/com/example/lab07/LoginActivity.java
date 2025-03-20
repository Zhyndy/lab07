package com.example.lab07;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edUsername, edPassword;
    private Button btnLogin, btnSignUp;
    private static final String PREFS_NAME = "UserPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String usernameInput = edUsername.getText().toString().trim();
        String passwordInput = edPassword.getText().toString().trim();

        if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
            Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        // Получаем сохраненные данные
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");

        // Проверяем, совпадают ли введённые данные с сохранёнными
        if (usernameInput.equals(savedUsername) && passwordInput.equals(savedPassword)) {
            Toast.makeText(this, "Вход успешный!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Закрываем LoginActivity
        } else {
            Toast.makeText(this, "Неверное имя пользователя или пароль", Toast.LENGTH_SHORT).show();
        }
    }
}
