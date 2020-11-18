package com.javaAdvance.spring.BeanLoad;

public class BeanDemo {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public void print(){
        System.out.println("bean loading success.......");
    }
}
