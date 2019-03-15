package com.thisisabir.firestore.activity.allactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thisisabir.firestore.R;
import com.thisisabir.firestore.activity.allactivity.allactivity.StudentList;
import com.thisisabir.firestore.activity.allactivity.model.StudentPojo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText studentname, studentdepartment, studentbatch;
    private Button savedata, viewdata;
    private ProgressBar progressBar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressbar);
        studentname = findViewById(R.id.name);
        studentdepartment = findViewById(R.id.department);
        studentbatch = findViewById(R.id.batch);
        savedata = findViewById(R.id.save);
        viewdata = findViewById(R.id.viewdata);
        savedata.setOnClickListener(this);
        viewdata.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.save)
        {
            savedata();
            progressBar.setVisibility(View.VISIBLE);
        }
        if (v.getId() == R.id.viewdata)
        {
            startActivity(new Intent(MainActivity.this, StudentList.class));
        }
    }
    private void savedata() {
        String studentName = studentname.getText().toString();
        String studentDepartment = studentdepartment.getText().toString();
        String studentBatch = studentbatch.getText().toString();
        CollectionReference reference = db.collection("Student Information");
        StudentPojo studentPojo = new StudentPojo(studentName, studentDepartment, studentBatch);
        reference.add(studentPojo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(getApplicationContext(), "Student Information Inserted Successfully",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                studentname.setText("");
                studentdepartment.setText("");
                studentbatch.setText("");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(), "Student Information Inserted failed due to "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
