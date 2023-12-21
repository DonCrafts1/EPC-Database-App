/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.epc.environmentapp;

/**
 *
 * @author user
 */
public class binTable {
    private String binName, binCode, binType, category;
    private float maxWeight, contains;
    public binTable(String c, String b, String t, float mW, float co, String ca){
        binCode = c;
        binName = b;
        binType = t;
        maxWeight = mW;
        contains = co;
        category = ca;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String ca){
        category = ca;
    }
    public float getContains() {
        return contains;
    }

    public void setContains(float contains) {
        this.contains = contains;
    }

    public String getBinName() {
        return binName;
    }

    public void setBinName(String binName) {
        this.binName = binName;
    }

    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String code) {
        binCode = code;
    }

    public float getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(float maxWeight) {
        this.maxWeight = maxWeight;
    }
    
    public void setBinType(String type){
        binType = type;
    }
    public String getBinType(){
        return binType;
    }
    public void display(){
        System.out.println("binCode:"+binCode+". binName:"+binName+". type:"+binType+". maxW:"+maxWeight+". cont:"+contains);
    }
}
