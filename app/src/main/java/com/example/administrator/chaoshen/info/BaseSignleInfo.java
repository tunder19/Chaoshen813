package com.example.administrator.chaoshen.info;

/**
 * Created by czg on 2016/5/13.
 */
public class BaseSignleInfo<T> extends BaseInfo{
    private T data;

    public T getResults() {
        return data;
    }

    public void setResults(T results) {
        this.data = results;
    }
}
