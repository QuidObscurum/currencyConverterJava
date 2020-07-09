package com.quid.currencyconverter.myUtils;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String s){
        super(s);
    }
}
