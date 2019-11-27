package com.kopytko.controller;

public interface Controller {
    void putNewNode(int key, String value);
    void removeNode(int key);
    String getValueByKey(int key);
}
