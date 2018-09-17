package com.example.administrator.chaoshen.bean;

import java.io.Serializable;

public class ArticleClassiBean implements Serializable {
    private long id;
    private String name;

    public ArticleClassiBean(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
