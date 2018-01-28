package com.example.kapil.todowithfirebase.model;

/**
 * Created by KAPIL on 27-01-2018.
 */

public class Task {
    String id;
    String data;
    Boolean isChecked;

    public Task() {
    }

    public Task(String id, String data, Boolean isChecked) {
        this.id = id;
        this.data = data;
        this.isChecked = isChecked;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
