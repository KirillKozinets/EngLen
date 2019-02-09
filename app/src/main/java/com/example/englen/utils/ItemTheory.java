package com.example.englen.utils;

public class ItemTheory {

    private String text;
    private int id;
    private boolean active;

    public ItemTheory(String text, int id, boolean active) {
        this.text = text;
        this.id = id;
        this.active = active;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
