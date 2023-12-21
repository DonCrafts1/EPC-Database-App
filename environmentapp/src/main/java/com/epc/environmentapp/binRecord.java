package com.epc.environmentapp;

public class binRecord {
    private String username, timeStamp, typeOfWaste, notes;
    private float weight;
    
    public binRecord(String username, String timeStamp, String typeOfWaste, float weight, String notes){
        this.username = username;
        this.timeStamp = timeStamp;
        this.typeOfWaste = typeOfWaste;
        this.weight = weight;
        this.notes = notes;
    }

    public binRecord(String timeStamp, String typeOfWaste, float weight){
        this.timeStamp = timeStamp;
        this.typeOfWaste = typeOfWaste;
        this.weight = weight;
        
    }
    public String getUsername() {
        return username;
    }
    
    public String getTimeStamp() {
        return timeStamp;
    }

    public String getTypeOfWaste() {
        return typeOfWaste;
    }

    public String getNotes() {
        return notes;
    }

    public float getWeight() {
        return weight;
    }

    public String toString() {
        return "binRecord{" + "username=" + username + ", timeStamp=" + timeStamp + 
                ", typeOfWaste=" + typeOfWaste + ", notes=" + notes + ", weight=" + weight +'}';
    }
}
