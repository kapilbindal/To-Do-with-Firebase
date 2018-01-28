package com.example.kapil.todowithfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kapil.todowithfirebase.Adapter.TaskAdapter;
import com.example.kapil.todowithfirebase.Adapter.TaskRecyclerAdapter;
import com.example.kapil.todowithfirebase.model.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Todo";

    //ListView lvTask;
    RecyclerView rvTask;
    Button b1;
    ArrayList<Task> todos;
    //TaskAdapter taskAdapter;
    TaskRecyclerAdapter taskRecyclerAdapter;
    private DatabaseReference mDatabase;

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                todos.clear();
                for(DataSnapshot taskSnapshot: dataSnapshot.getChildren()){
                    Task task = taskSnapshot.getValue(Task.class);
                    todos.add(task);
                }
                taskRecyclerAdapter = new TaskRecyclerAdapter(MainActivity.this,todos,mDatabase);
                rvTask.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rvTask.setAdapter(taskRecyclerAdapter);
                //taskAdapter = new TaskAdapter(MainActivity.this, todos);
                //lvTask.setAdapter(taskAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todos = new ArrayList<>();
        rvTask = findViewById(R.id.rvTask);
        mDatabase = FirebaseDatabase.getInstance().getReference("tasks");

       // lvTask = findViewById(R.id.lvTask);
        //taskAdapter = new TaskAdapter(this, todos);
        //lvTask.setAdapter(taskAdapter);

        b1 = findViewById(R.id.bt1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed = findViewById(R.id.ed);
                String data = ed.getText().toString();
                if (!data.isEmpty()) {
                    String id = mDatabase.push().getKey();
                    Task t = new Task(id,data, false);
                    todos.add(t);
                   // taskAdapter.notifyDataSetChanged();
                    taskRecyclerAdapter.notifyDataSetChanged();
                    mDatabase.child(id).setValue(t);
                    ed.setText("");
                    Toast.makeText(MainActivity.this, "Task Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Enter the Data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
