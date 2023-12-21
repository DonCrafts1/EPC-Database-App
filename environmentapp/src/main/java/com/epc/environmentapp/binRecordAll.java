package com.epc.environmentapp;


public class binRecordAll extends binRecord{
    String binCode = null; 
    private String binName = null; 
    private String binType = null; 
    private String binCategory = null;

    public binRecordAll(String username, String timeStamp, String typeOfWaste, float weight, String notes, String binCode, String binName, String binType){
        super(username, timeStamp, typeOfWaste, weight, notes);
        this.binCode = binCode;
        this.binName = binName;
        this.binType = binType;
    }
    
    public binRecordAll(String timeStamp, String typeOfWaste, float weight, String binName, String binType, String binCategory){
        super(timeStamp, typeOfWaste, weight);
        this.binName = binName;
        this.binType = binType;
        this.binCategory = binCategory;
    }
    public void setBinCode(String binCode){
        this.binCode = binCode;
    }
    public String getBinCode(){
        return binCode;
    }
    public void setBinName(String binName){
        this.binName = binName;
    }
    public String getBinName(){
        return binName;
    }
    public String getBinType() {
        return binType;
    }

    public void setBinType(String binType) {
        this.binType = binType;
    }

    public String getBinCategory() {
        return binCategory;
    }

    public void setBinCategory(String binCategory) {
        this.binCategory = binCategory;
    }
    
    public String toString(){
        return super.toString()+".  binName: "+binName+". binType: "+binType+". binCat: "+binCategory;
    }

    
    
}