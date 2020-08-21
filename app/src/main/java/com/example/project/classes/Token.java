package com.example.project.classes;

public class Token {
    private static String token="void";
    private static String id;

    public static void setToken(String tk) {
        Token.token = tk;
    }

    public static String getToken(){
        return Token.token;
    }

    public static void setId(String uid) {
        Token.id = uid;
    }

    public static String getId(){
        return Token.id;
    }
}
