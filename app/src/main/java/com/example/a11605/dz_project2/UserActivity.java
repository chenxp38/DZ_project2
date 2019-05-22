package com.example.a11605.dz_project2;

public class UserActivity {
    private String name;
    private String password;

    public UserActivity(){
        name = null;
        password = null;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
