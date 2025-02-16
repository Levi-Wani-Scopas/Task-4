package com.example.taskfour;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class UploadActivity extends AppCompatActivity {

    private EditText etFileName;
    private TextView tvSelectedFile, Txtback;
    private Button btnSelectFile, btnUploadFile;
    private Uri fileUri;
    private StorageReference storageRef;
    private DatabaseReference databaseRef;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        etFileName = findViewById(R.id.etFileName);
        tvSelectedFile = findViewById(R.id.tvSelectedFile);
        btnSelectFile = findViewById(R.id.btnSelectFile);
        btnUploadFile = findViewById(R.id.btnUploadFile);
        Txtback = findViewById(R.id.back);

        storageRef = FirebaseStorage.getInstance().getReference("StudyResources");
        databaseRef = FirebaseDatabase.getInstance().getReference("Resources");
        progressDialog = new ProgressDialog(this);


        Txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadActivity.this, MainActivity.class));
            }
        });

        // Select file
        btnSelectFile.setOnClickListener(view -> openFileSelector());

        // Upload file
        btnUploadFile.setOnClickListener(view -> uploadFile());
    }

    private void openFileSelector() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Accepts PDFs, images, etc.
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            fileUri = data.getData();
            String fileName = fileUri.getLastPathSegment();
            tvSelectedFile.setText(fileName);
        }
    }

    private void uploadFile() {
        String fileName = etFileName.getText().toString().trim();

        if (TextUtils.isEmpty(fileName)) {
            Toast.makeText(this, "Please enter a file name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (fileUri == null) {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Uploading file...");
        progressDialog.show();

        StorageReference fileRef = storageRef.child(fileName);
        fileRef.putFile(fileUri).addOnSuccessListener(taskSnapshot -> {
            fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                saveFileInfo(fileName, uri.toString());
            });
        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(UploadActivity.this, "File upload failed", Toast.LENGTH_SHORT).show();
        });
    }

    private void saveFileInfo(String fileName, String fileUrl) {
        String uploadTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        HashMap<String, String> fileMap = new HashMap<>();
        fileMap.put("fileName", fileName);
        fileMap.put("fileUrl", fileUrl);
        fileMap.put("uploadTime", uploadTime);

        String fileId = databaseRef.push().getKey();
        databaseRef.child(fileId).setValue(fileMap).addOnCompleteListener(task -> {
            progressDialog.dismiss();
            if (task.isSuccessful()) {
                Toast.makeText(UploadActivity.this, "File uploaded successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(UploadActivity.this, "Failed to save file info", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
