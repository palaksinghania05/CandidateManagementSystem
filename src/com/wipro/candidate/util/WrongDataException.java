package com.wipro.candidate.util;

public class WrongDataException extends Exception{
    public WrongDataException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        //write code here
        return "Data Incorrect";
    }

}
