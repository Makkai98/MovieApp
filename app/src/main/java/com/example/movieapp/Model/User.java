package com.example.movieapp.Model;


public class User {

    private int id;
    private String name;
    private String password;
    private String profileimage;

    public User(int id, String name, String password, String profileimage) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.profileimage = profileimage;
    }

    public User(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", profileimage='" + profileimage + '\'' +
                '}';
    }
}