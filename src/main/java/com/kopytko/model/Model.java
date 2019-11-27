package com.kopytko.model;

public interface Model {
    void putNewNode(int key, String value);
    void removeNode(int key);
    String getValueByKey(int key);
}
