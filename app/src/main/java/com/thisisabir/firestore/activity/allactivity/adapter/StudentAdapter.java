package com.thisisabir.firestore.activity.allactivity.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thisisabir.firestore.R;
import com.thisisabir.firestore.activity.allactivity.allactivity.StudentList;
import com.thisisabir.firestore.activity.allactivity.allactivity.UpdateStudent;
import com.thisisabir.firestore.activity.allactivity.model.StudentPojo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.Holder> {

    private List<StudentPojo> studentList;
    private Context context;

    // This  object we used here only for deleted operation needed
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public StudentAdapter(List<StudentPojo> studentList, Context context) {
        this.studentList = studentList;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_student, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.name.setText(studentList.get(position).getStudentName());
        holder.department.setText(studentList.get(position).getStudentDepartment());
        holder.batch.setText(studentList.get(position).getStudentBatch());

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, department, batch;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.stdname);
            department = itemView.findViewById(R.id.stddepartment);
            batch = itemView.findViewById(R.id.stdbatch);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);
            builder.setTitle("Warning");
            builder.setMessage("Choose your Action please?");
            builder.setPositiveButton("Update Student", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    StudentPojo studentPojo = studentList.get(getAdapterPosition());
                    Intent intent = new Intent(context, UpdateStudent.class);
                    intent.putExtra("alldata", studentPojo);
                    context.startActivity(intent);
                }
            });
            builder.setNegativeButton("Delete Student", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, int which) {

                    StudentPojo studentPojo = studentList.get(getAdapterPosition());
                    db.collection("Student Information").document(studentPojo.getId()).delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {

                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(context, "Student Deleted Succefully",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

}
