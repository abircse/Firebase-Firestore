package com.thisisabir.firestore.activity.allactivity.allactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.thisisabir.firestore.R;
import com.thisisabir.firestore.activity.allactivity.adapter.StudentAdapter;
import com.thisisabir.firestore.activity.allactivity.model.StudentPojo;

import java.util.ArrayList;
import java.util.List;

public class StudentList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<StudentPojo> studentList;
    private StudentAdapter studentAdapter;
    private ProgressBar progressBar;
    private TextView statustext;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        progressBar = findViewById(R.id.progressbar);
        statustext = findViewById(R.id.statustext);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        studentList = new ArrayList<>();
        studentAdapter = new StudentAdapter(studentList, this);
        loadstudentdata();
        recyclerView.setAdapter(studentAdapter);


    }

    private void loadstudentdata() {
        db.collection("Student Information").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                progressBar.setVisibility(View.GONE);
                if (queryDocumentSnapshots.isEmpty()) {
                    statustext.setText("No Data Available");
                    progressBar.setVisibility(View.GONE);
                }
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d : list) {
                    StudentPojo studentPojo = d.toObject(StudentPojo.class);
                    studentPojo.setId(d.getId());
                    studentList.add(studentPojo);
                }
                studentAdapter.notifyDataSetChanged();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed To load Data for " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
