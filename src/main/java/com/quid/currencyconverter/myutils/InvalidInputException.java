package com.quid.currencyconverter.myutils;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String s){
        super(s);
    }
}
