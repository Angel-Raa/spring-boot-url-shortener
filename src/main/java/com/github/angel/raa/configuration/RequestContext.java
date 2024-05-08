package com.github.angel.raa.configuration;


public class RequestContext {
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    public RequestContext() {}
    public static void start(){
        USER_ID.remove();
    }
    public static void setId(Long id){
        USER_ID.set(id);
    }
    public static long getId(){
      return  USER_ID.get();
    }
}
