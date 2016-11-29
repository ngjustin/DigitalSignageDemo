package com.momentum.digitalsignagedemo;

import java.io.Serializable;

/**
 * Created by Mohammad on 10/27/2016.
 */

public class User implements Serializable{
    private String firstName;
    private String lastName;
    private String major;
    private int age;
    private String gender;

    public User(String firstName, String lastName, String major, int age, String gender){
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setMajor(major);
        this.setAge(age);
        this.setGender(gender);
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
