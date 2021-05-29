package com.test.utility;

public class LoginRequest {

    private String email;
    private String password;
    private UserLoginType userType;

//    getter setter


    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserLoginType getUserType() {
        return userType;
    }

    public void setUserType(UserLoginType userType) {
        this.userType = userType;
    }
}
