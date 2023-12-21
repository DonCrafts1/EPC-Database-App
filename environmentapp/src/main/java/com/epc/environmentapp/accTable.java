/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.epc.environmentapp;

/**
 *
 * @author user
 */
public class accTable {
    private String role, user, pass, userAdd;
    public accTable(String role, String user, String pass, String userAdd){
        this.role = role;
        this.user = user;
        this.pass = pass;
        this.userAdd = userAdd;
    }
    public accTable(){
        
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUserAdd() {
        return userAdd;
    }

    public void setUserAdd(String userAdd) {
        this.userAdd = userAdd;
    }
    public void display(){
        System.out.println("User: "+user+", Pass: "+pass+", userAdd: "+userAdd);
    }
}
