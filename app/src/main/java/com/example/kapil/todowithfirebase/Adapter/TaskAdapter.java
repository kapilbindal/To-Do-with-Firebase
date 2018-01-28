package com.example.kapil.todowithfirebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kapil.todowithfirebase.R;
import com.example.kapil.todowithfirebase.model.Task;

import java.util.ArrayList;

/**
 * Created by KAPIL on 27-01-2018.
 */

public class TaskAdapter extends BaseAdapter {
    private Context context;
    ArrayList<Task> todos;

    public TaskAdapter(Context context, ArrayList<Task> todos) {
        this.context = context;
        this.todos = todos;
    }

    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Task getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View taskView = li.inflate(R.layout.task_layout,viewGroup,false);
        TextView tv = taskView.findViewById(R.id.tv1);
        TextView sNo = taskView.findViewById(R.id.sNo);
        ImageView del = taskView.findViewById(R.id.delete);
        CheckBox cb = taskView.findViewById(R.id.ch);

        final Task thisTask = todos.get(i);
        i++;
        tv.setText(thisTask.getData());
        sNo.setText(i + ".  ");
        cb.setChecked(thisTask.getChecked());

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todos.remove(thisTask);
                todos.trimToSize();
                notifyDataSetChanged();
            }
        });
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    thisTask.setChecked(true);
                }
                else if(!b){
                    thisTask.setChecked(false);
                }
            }
        });
        return taskView;
    }


}
