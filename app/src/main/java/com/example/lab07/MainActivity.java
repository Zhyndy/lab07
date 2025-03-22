package com.example.lab07;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editFileData;
    private TextView textFileData;
    private ListView listViewFiles;
    private String selectedFileName = "default.txt"; // Имя файла по умолчанию

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
        btnLoadFile.setOnClickListener(v -> displayFileList());

        // Показываем файлы в списке
        displayFileList();

        // При клике загружаем содержимое файла
        listViewFiles.setOnItemClickListener((parent, view, position, id) -> {
            selectedFileName = (String) parent.getItemAtPosition(position);
            loadFromFile(selectedFileName);
        });
    }

    private void saveToFile() {
        String data = editFileData.getText().toString().trim();
        if (data.isEmpty()) {
            Toast.makeText(this, "Введите текст перед сохранением", Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileOutputStream fos = openFileOutput(selectedFileName, MODE_PRIVATE)) {
            fos.write(data.getBytes(StandardCharsets.UTF_8));
            Toast.makeText(this, "Файл сохранён: " + selectedFileName, Toast.LENGTH_SHORT).show();
            displayFileList(); // Обновляем список файлов
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка при сохранении файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile(String fileName) {
        try (FileInputStream fis = openFileInput(fileName)) {
            int size;
            StringBuilder stringBuilder = new StringBuilder();
            while ((size = fis.read()) != -1) {
                stringBuilder.append((char) size);
            }
            textFileData.setText("Файл: " + fileName + "\n" + stringBuilder.toString());
            editFileData.setText(stringBuilder.toString());
            Toast.makeText(this, "Файл загружен: " + fileName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка при загрузке файла", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayFileList() {
        String[] files = fileList(); // Получаем список файлов
        List<String> fileList = new ArrayList<>(Arrays.asList(files));

        if (fileList.isEmpty()) {
            textFileData.setText("Файлы отсутствуют");
        } else {
            textFileData.setText("Выберите файл для загрузки:");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileList);
        listViewFiles.setAdapter(adapter);
    }
}
