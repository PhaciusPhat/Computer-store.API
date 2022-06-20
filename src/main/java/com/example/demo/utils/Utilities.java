package com.example.demo.utils;

public class Utilities {
    public boolean isValid(String str){
        return str != null && !str.isEmpty();
    }

    public boolean isValid(String str, int length){
        return str != null && !str.isEmpty() && str.length() == length;
    }

    public boolean isValid(String str, int minLength, int maxLength){
        return str != null && !str.isEmpty() && str.length() >= minLength && str.length() <= maxLength;
    }

    public boolean isValid(String str, int minLength, int maxLength, String regex){
        return str != null && !str.isEmpty() && str.length() >= minLength && str.length() <= maxLength && str.matches(regex);
    }

    public boolean isValidNumber(int number){
        return number > 0;
    }

    public boolean isValidNumber(int number, int min, int max){
        return number > min && number < max;
    }

    public boolean isValidNumber(long number){
        return number > 0;
    }

    public boolean isValidNumber(long number, long min, long max){
        return number > min && number < max;
    }

}
