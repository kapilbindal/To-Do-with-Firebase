package com.example.kapil.todowithfirebase.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kapil.todowithfirebase.R;
import com.example.kapil.todowithfirebase.model.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import static com.example.kapil.todowithfirebase.MainActivity.TAG;

/**
 * Created by KAPIL on 27-01-2018.
 */

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder> {
    private Context context;
    private ArrayList<Task> todos;
    DatabaseReference mDatabase;

    public TaskRecyclerAdapter(Context context, ArrayList<Task> todos, DatabaseReference mDatabase) {
        this.context = context;
        this.todos = todos;
        this.mDatabase = mDatabase;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.task_layout,parent,false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        int i = position + 1;
        final Task current = todos.get(position);
        if (current != null) {
            holder.tv1.setText(String.valueOf(current.getData()));
            holder.sNo.setText(i + ". ");
            holder.cb.setChecked(current.getChecked());
            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatabase.child(current.getId()).removeValue();
                    todos.remove(current);
                    todos.trimToSize();
                    notifyDataSetChanged();
                }
            });
            holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        mDatabase.child(current.getId()).child("isChecked").setValue(true);
                        current.setChecked(true);
                    } else if (!b) {
                        mDatabase.child(current.getId()).child("isChecked").setValue(false);
                        current.setChecked(false);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView tv1,sNo;
        ImageView del;
        CheckBox cb;
        public TaskViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            sNo = itemView.findViewById(R.id.sNo);
            del = itemView.findViewById(R.id.delete);
            cb = itemView.findViewById(R.id.ch);
        }
    }

}
