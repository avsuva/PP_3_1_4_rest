package ru.kata.spring.boot_security.demo.exep;

public class UserNotCreatedException  extends RuntimeException{
    public UserNotCreatedException(String msg){
        super(msg);
    }
}
