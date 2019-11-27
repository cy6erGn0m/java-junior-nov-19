package ru.levelp.tests;

public class MyList {
    private Object[] elementsOrEmpty = new Object[10];
    private int size;

    public void add(Object newElement) {
        elementsOrEmpty[size] = newElement;
        size = size + 1;
    }
}
