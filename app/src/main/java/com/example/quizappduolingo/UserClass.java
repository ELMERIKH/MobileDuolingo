package com.example.quizappduolingo;

public class UserClass {


    private String username;
   private float rank;

    public UserClass(String username, float rank) {
        this.username = username;
        this.rank = rank;
    }

    public String getUsername() {
        return username;
    }

    public float getRank() {
        return rank;
    }
}
