package com.example.lab07;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private EditText editFileData;
    private TextView textFileData;
    private ListView listViewFiles;
    private static final String FILE_NAME = "test.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editFileData = findViewById(R.id.editFileData);
        textFileData = findViewById(R.id.textFileData);
        listViewFiles = findViewById(R.id.listViewFiles);
        Button btnSaveFile = findViewById(R.id.btnSaveFile);
        Button btnLoadFile = findViewById(R.id.btnLoadFile);

        btnSaveFile.setOnClickListener(v -> saveToFile());
        btnLoadFile.setOnClickListener(v -> loadFromFile());

        // Показываем список файлов
        displayFileList();

        // При клике на файл загружаем его содержимое
        listViewFiles.setOnItemClickListener((parent, view, position, id) -> {
            String selectedFile = (String) parent.getItemAtPosition(position);
            loadFromFile(selectedFile);
        });
    }

    private void saveToFile() {
        String data = editFileData.getText().toString().trim();
        if (data.isEmpty()) {
            Toast.makeText(this, "Введите текст перед сохранением", Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fos.write(data.getBytes(StandardCharsets.UTF_8));
            Toast.makeText(this, "Файл сохранён!", Toast.LENGTH_SHORT).show();
            displayFileList(); // Обновляем список файлов
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка при сохранении файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        loadFromFile(FILE_NAME);
    }

    private void loadFromFile(String fileName) {
        try (FileInputStream fis = openFileInput(fileName)) {
            int size;
            StringBuilder stringBuilder = new StringBuilder();
            while ((size = fis.read()) != -1) {
                stringBuilder.append((char) size);
            }
            textFileData.setText(stringBuilder.toString());
            editFileData.setText(stringBuilder.toString());
            Toast.makeText(this, "Файл загружен: " + fileName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка при загрузке файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayFileList() {
        String[] files = fileList(); // Получаем список файлов
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, files);
        listViewFiles.setAdapter(adapter);
    }
}
