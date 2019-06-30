package com.thisisabir.firestore.activity.allactivity.allactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thisisabir.firestore.R;
import com.thisisabir.firestore.activity.allactivity.model.StudentPojo;

public class UpdateStudent extends AppCompatActivity implements View.OnClickListener {

    private EditText studentname, studentdepartment, studentbatch;
    private Button updatedata;
    private ProgressBar progressBar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StudentPojo studentPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);
        progressBar = findViewById(R.id.progressbar);
        studentPojo = (StudentPojo) getIntent().getSerializableExtra("alldata");
        studentname = findViewById(R.id.name);
        studentdepartment = findViewById(R.id.department);
        studentbatch = findViewById(R.id.batch);
        updatedata = findViewById(R.id.update);
        updatedata.setOnClickListener(this);
        studentname.setText(studentPojo.getStudentName());
        studentdepartment.setText(studentPojo.getStudentDepartment());
        studentbatch.setText(studentPojo.getStudentBatch());

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.update) {
            update();
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void update() {

        String studentName = studentname.getText().toString();
        String studentDepartment = studentdepartment.getText().toString();
        String studentBatch = studentbatch.getText().toString();
        StudentPojo student = new StudentPojo(studentName, studentDepartment, studentBatch);

//        // this is the task for update database by auto generated key in firestore
//        db.collection("Student Information").document(studentPojo.getId()).set(student)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//                        Toast.makeText(getApplicationContext(), "Student Information Updated",Toast.LENGTH_LONG).show();
//                        progressBar.setVisibility(View.GONE);
//                        startActivity(new Intent(UpdateStudent.this, StudentList.class));
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//                Toast.makeText(getApplicationContext(), "Student Information not Updated for "+e.getMessage(),Toast.LENGTH_LONG).show();
//
//            }
//        });

        // this is the task for update database specific field with model class object
        db.collection("Student Information").document(studentPojo.getId())
                .update("studentName",student.getStudentName(),
                        "studentDepartment",student.getStudentDepartment(),
                        "studentBatch", student.getStudentBatch()
                        )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(getApplicationContext(), "Student Information Updated",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(), "Student Information not Updated for "+e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }
}
