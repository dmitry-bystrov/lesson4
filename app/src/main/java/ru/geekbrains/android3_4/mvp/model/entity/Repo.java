package ru.geekbrains.android3_4.mvp.model.entity;

import com.google.gson.annotations.Expose;

public class Repo {
    @Expose
    private String name;
    @Expose
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
