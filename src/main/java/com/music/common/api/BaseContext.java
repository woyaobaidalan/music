package com.music.common.api;

public class BaseContext {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(String token){
        threadLocal.set(token);
    }

    public static String getCurrentId(){
        return threadLocal.get();
    }

}