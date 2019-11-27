package com.kopytko.model;

public class ModelImpl implements Model {
    private BinaryTreeMap<Integer, String> treeMap;

    public ModelImpl() {
        treeMap = new BinaryTreeMap();
        treeMap.put(60, "60 cats");
        treeMap.put(56, "56 monkeys");
        treeMap.put(80, "Age of my granny");
        treeMap.put(14, "Iphone");
        treeMap.put(2, "Second");
        treeMap.put(17, "The best age ever");
        treeMap.put(100, "Percent how you look");
        treeMap.put(103, "103");
        treeMap.put(98, "Another value");
        treeMap.put(96, "Value96");
        treeMap.put(99, "Almost hundred");
        treeMap.put(74, "Lol");
        treeMap.displayTree();
    }


    @Override
    public void putNewNode(int key, String value) {
        treeMap.put(key, value);
        treeMap.print();
    }

    @Override
    public void removeNode(int key) {
        treeMap.remove(key);
        treeMap.print();
    }

    @Override
    public String getValueByKey(int key) {
        return treeMap.get(key);
    }
}
