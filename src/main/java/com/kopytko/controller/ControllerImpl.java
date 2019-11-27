package com.kopytko.controller;

import com.kopytko.model.*;

public class ControllerImpl implements Controller {
    private Model model;

    public ControllerImpl() {
        model = new ModelImpl();
    }

    @Override
    public void putNewNode(int key, String value) {
        model.putNewNode(key, value);
    }

    @Override
    public void removeNode(int key) {
        model.removeNode(key);
    }

    @Override
    public String getValueByKey(int key) {
        return model.getValueByKey(key);
    }

    @Override
    public void printTree() {
        model.printTree();
    }
}
