package com.example.englen.utils;

import com.example.englen.Interface.stateApp;

public class LearnWord implements stateApp {
    static private int currentID = 1;

    static public int getCurrentID()
    {
        return currentID;
    }

    static public void addNewWord()
    {
        currentID++;
    }

    @Override
    public void Create() {

    }

    @Override
    public void Close() {

    }
}
