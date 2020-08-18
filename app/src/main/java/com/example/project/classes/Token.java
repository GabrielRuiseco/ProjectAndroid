package com.example.project.classes;

public class Token {
    private static String token="void";
    private static int id;

    public static void setToken(String tk) {
        Token.token = tk;
    }

    public static String getToken(){
        return Token.token;
    }

    public static void setId(int uid) {
        Token.id = uid;
    }

    public static int getId(){
        return Token.id;
    }
}
