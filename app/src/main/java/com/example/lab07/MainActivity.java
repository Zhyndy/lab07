package com.example.lab07;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText editUsername, editPassword, editFileData;
    private TextView textFileData;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String FILE_NAME = "test.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editFileData = findViewById(R.id.editFileData);
        textFileData = findViewById(R.id.textFileData);
        Button btnSavePrefs = findViewById(R.id.btnSavePrefs);
        Button btnLoadPrefs = findViewById(R.id.btnLoadPrefs);
        Button btnSaveFile = findViewById(R.id.btnSaveFile);
        Button btnLoadFile = findViewById(R.id.btnLoadFile);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        btnSavePrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
            }
        });

        btnLoadPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPreferences();
            }
        });

        btnSaveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile();
            }
        });

        btnLoadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile();
            }
        });

        // Автоматическая загрузка логина при запуске
        loadPreferences();
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", editUsername.getText().toString());
        editor.putString("password", editPassword.getText().toString());
        editor.apply();
        Log.d("DEBUG", "Логин сохранён: " + editUsername.getText().toString());
    }

    private void loadPreferences() {
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");

        if (username.isEmpty()) {
            Log.d("DEBUG", "Логин не найден в SharedPreferences!");
        } else {
            Log.d("DEBUG", "Загруженный логин: " + username);
        }

        editUsername.setText(username);
        editPassword.setText(password);
    }

    private void saveToFile() {
        String data = editFileData.getText().toString();
        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fos.write(data.getBytes());
            Log.d("DEBUG", "Данные сохранены в файл: " + data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            int size;
            StringBuilder stringBuilder = new StringBuilder();
            while ((size = fis.read()) != -1) {
                stringBuilder.append((char) size);
            }
            textFileData.setText(stringBuilder.toString());
            Log.d("DEBUG", "Загруженные данные из файла: " + stringBuilder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
