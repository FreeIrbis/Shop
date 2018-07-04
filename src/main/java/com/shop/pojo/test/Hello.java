package com.shop.pojo.test;


public class Hello {

    private String word = "Hello";
    private String name = "user";

    public Hello() {
    }


    public Hello(String word, String name) {
        this.word = word;
        this.name = name;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
