/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.epc.environmentapp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author user
 */
public class DataAnalysisController {
    @FXML
    private TabPane tabPane;
    @FXML
    private PieChart pieChart;       
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private Pane weightsPane;
    @FXML
    private ChoiceBox dataOverviewChoiceBox;
    
    //-------
    
    @FXML 
    private ChoiceBox typeChoiceBox;
    @FXML
    private ChoiceBox chooseCategoryChoiceBox;
    @FXML
    private ChoiceBox chooseCategoryChoiceBox2;
    @FXML
    private DatePicker fromDatePickerComp;
    @FXML
    private DatePicker toDatePickerComp;
    @FXML
    private Label catTotalWeightLabel;
    @FXML
    private Label catContWeightLabel;
    @FXML
    private Label contaminationPercentLabel;
    @FXML
    private Label co2SavedLabel;
    @FXML
    private Label plasticWasteLabel;
    @FXML
    private Label metalWasteLabel;
    
    @FXML
    private Label catTotalWeightLabel2;
    @FXML
    private Label catContWeightLabel2;
    @FXML
    private Label contaminationPercentLabel2;
    @FXML
    private Label co2SavedLabel2;
    @FXML
    private Label plasticWasteLabel2;
    @FXML
    private Label metalWasteLabel2;
    
    @FXML
    public Pane lostConnectionDataPane;
    
    //https://docs.oracle.com/javafx/2/charts/bar-chart.htm

    private XYChart.Series<String, Number> seriesClean =
            new XYChart.Series<>();
    private XYChart.Series<String, Number> seriesContaminated =
            new XYChart.Series<>();
    private ObservableList<String> barChartCat = FXCollections.observableArrayList("Category 1","Category 2");
    @FXML
    private StackedBarChart<String, Number> stackedBarChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    
    private int dataOverviewChoice = 0, catComparisonTypeChoice = 0;
    
    private String catComparisonChoice, catComparisonChoice2; //for comparison tab, two choices
    
    private LocalDate fromDate = LocalDate.parse("2023-01-01"), toDate = LocalDate.now().plusDays(1), //data overview
            fromDateComp = LocalDate.parse("2023-01-01"), toDateComp = LocalDate.now().plusDays(1); //comparison tab
    
    DatabaseConnection connectNow = LoginController.connectNow;
    Connection connectDB = LoginController.connectDB;
    
    public void back(ActionEvent e) throws Exception{
        App.setRoot("recyclepage");
    }

    @FXML
    private void initialize(){
        Platform.runLater(() -> {
            refreshDataOverviewPage();
        });
        
        ObservableList<String> options = FXCollections.observableArrayList("Bin Categories","Bin Type","Individual Bins");
        dataOverviewChoiceBox.setItems(options);
        typeChoiceBox.setItems(options);
        dataOverviewChoiceBox.setValue("Bin Categories");
        typeChoiceBox.setValue("Bin Categories");
        dataOverviewChoiceBox.getSelectionModel().selectedIndexProperty().addListener((obs, oldval, newval) -> {
            dataOverviewChoice = newval.intValue();
            refreshDataOverviewPage();
        });
        typeChoiceBox.getSelectionModel().selectedIndexProperty().addListener((obs, oldval, newval) -> {
            catComparisonTypeChoice = newval.intValue();
            categoryChoiceRefresh();
        });
        chooseCategoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldval, newval) -> {
            catComparisonChoice = String.valueOf(newval);
            if (catComparisonChoice.equals(catComparisonChoice2)){
                catComparisonChoice = String.valueOf(oldval);
                chooseCategoryChoiceBox.getSelectionModel().select(oldval);
            } else if (catComparisonChoice != null){
                categoryComparisonChose(1);
            }
        });
        chooseCategoryChoiceBox2.getSelectionModel().selectedItemProperty().addListener((obs, oldval, newval) -> {
            catComparisonChoice2 = String.valueOf(newval);
            if (catComparisonChoice2.equals(catComparisonChoice)){
                catComparisonChoice2 = String.valueOf(oldval);
                chooseCategoryChoiceBox2.getSelectionModel().select(oldval);
            } else if (catComparisonChoice2 != null){
                categoryComparisonChose(2);
            }
        });
        toDatePicker.setValue(toDate);
        fromDatePicker.setValue(fromDate);
        toDatePickerComp.setValue(toDateComp);
        fromDatePickerComp.setValue(fromDateComp);
        tabPane.getSelectionModel().selectedIndexProperty().addListener((observable, oldval, newval) -> {
            int selected = newval.intValue();
            System.out.println("Selected: "+selected);
            switch (selected){
                case 0:
                    //refreshDataOverviewPage();
                    break;
                case 1:
                    categoryChoiceRefresh();
                    xAxis.setCategories(barChartCat);
                    break;
            }
        });
        fromDatePicker.setOnAction((ActionEvent e) -> {
            LocalDate tempDate = fromDatePicker.getValue();
            System.out.println(tempDate.toString());
            if (tempDate.isBefore(toDate) && !tempDate.equals(fromDate)){
                fromDate = tempDate;
                refreshDataOverviewPage();
            } else {
                fromDatePicker.setValue(fromDate);
            }
        });
        toDatePicker.setOnAction((ActionEvent e) -> {
            LocalDate tempDate = toDatePicker.getValue();
             System.out.println(tempDate.toString());
            if (tempDate.isAfter(fromDate) && !tempDate.equals(toDate)){
                toDate = tempDate;
                refreshDataOverviewPage();
            } else {
                toDatePicker.setValue(toDate);
            }
        });
        fromDatePickerComp.setOnAction((ActionEvent e) -> {
            LocalDate tempDate = fromDatePickerComp.getValue();
            System.out.println(tempDate.toString());
            if (tempDate.isBefore(toDateComp) && !tempDate.equals(fromDateComp)){
                fromDateComp = tempDate;
                categoryComparisonChose(1);
                categoryComparisonChose(2);
            } else {
                fromDatePickerComp.setValue(fromDateComp);
            }
        });
        toDatePickerComp.setOnAction((ActionEvent e) -> {
            LocalDate tempDate = toDatePickerComp.getValue();
             System.out.println(tempDate.toString());
            if (tempDate.isAfter(fromDateComp) && !tempDate.equals(toDateComp)){
                toDateComp = tempDate;
                categoryComparisonChose(1);
                categoryComparisonChose(2);
            } else {
                toDatePickerComp.setValue(toDateComp);
            }
        });
        seriesClean.setName("Clean");
        seriesContaminated.setName("Contaminated");
        seriesClean.getData().add(0, new XYChart.Data<>("Category 1",0));
        seriesClean.getData().add(1, new XYChart.Data<>("Category 2",0));
        seriesContaminated.getData().add(0, new XYChart.Data<>("Category 1",0));
        seriesContaminated.getData().add(0, new XYChart.Data<>("Category 2",0));
        yAxis.setLabel("Weight (kg)");
        stackedBarChart.setAnimated(false);
        stackedBarChart.getData().addAll(seriesClean, seriesContaminated);
    }
   
    
    public void refreshDataOverviewPage(){
        ObservableList <PieChart.Data> pieChartData = FXCollections.observableArrayList();
        SLL<binRecordAll> recordList = new SLL<>();
        SLL<String> categories = new SLL<>();
        
        float[] weights;
        
        try (Statement s = connectDB.createStatement()){
            ResultSet r = s.executeQuery("SELECT BinRecords.timeStamp, BinRecords.typeOfWaste, BinRecords.weight, "
                + "BinTable.name,"
                + "BinTable.type, BinTable.category FROM BinRecords"
                + " inner join BinTable on BinRecords.tagID = BinTable.tagID"
                + " WHERE BinRecords.timeStamp BETWEEN '"+fromDate+"' AND '"+toDate+"'");
            while (r.next()){
                binRecordAll m = new binRecordAll(r.getString(1), r.getString(2), r.getFloat(3), r.getString(4), r.getString(5), r.getString(6));
                recordList.add(m);
            }
        } catch (SQLException e){}
        
        Label topCatLabel = (Label) weightsPane.getChildren().get(0); // Get first label in the pane
        
        switch (dataOverviewChoice){
            case 0:
                topCatLabel.setText("Top Categories");
                categories.add("Others");
                String query = "SELECT category FROM BinCategories";
                try (ResultSet r = connectDB.createStatement().executeQuery(query)){
                    while (r.next()){
                        categories.add(r.getString(1));
                    }
                } catch (SQLException e){}
                break;
            case 1:
                topCatLabel.setText("Waste Types");
                categories.add("Plastic Waste");
                categories.add("Metal Waste");
                break;
            case 2:
                topCatLabel.setText("Top Bins");
                query = "SELECT name FROM BinTable";
                try (ResultSet r = connectDB.createStatement().executeQuery(query)){
                    while (r.next()){
                        categories.add(r.getString(1));
                    }
                } catch (SQLException e){}
                break;
        }
        
        weights = new float[categories.size()];
        
        for (int i = 0; i<recordList.size(); i++){
            String cat = "";
            switch (dataOverviewChoice){
                case 0:
                    cat = recordList.getData(i).getBinCategory();
                    break;
                case 1:
                    cat = recordList.getData(i).getBinType();
                    break;
                case 2:
                    cat = recordList.getData(i).getBinName();
                    break;
            }
            if (categories.contains(cat))
                weights[categories.getIndex(cat)] += recordList.getData(i).getWeight();
            else 
                weights[0] += recordList.getData(i).getWeight(); //Add into "Others" category (first element)
        }
        
        for (int i = 0; i<categories.size(); i++){
            if (weights[i] != 0)
                pieChartData.add(new PieChart.Data(categories.getData(i), weights[i]));
            //+": "+String.format("%.2f",weights[i])
        }
        //For 8 biggest categories
        int ind = categories.size();
        if (categories.size() > 8) ind = 8;
        for (int i = 0; i < ind; i++){
            //i<4
            int maxIndex = i;
            for (int j = i+1; j<categories.size(); j++){
                if (weights[j] > weights[maxIndex]){
                    maxIndex = j;
                }
            }
            float temp = weights[i];
            weights[i] = weights[maxIndex];
            weights[maxIndex] = temp;
            categories.switchData(i, maxIndex);
        }
        
        for (int i = 0; i<8; i++){
            Label l = (Label)weightsPane.getChildren().get(i+1);
            try {
                l.setText(categories.getData(i)+" - "+String.format("%.2f",weights[i])+"kg");
            } catch (Exception e) {
                //if there are not enough categories (e.g. for plastic or metal)
                l.setText("");
            }
            
        }
        pieChart.setData(pieChartData);
    }
    
    //--- category comparison
    
    public void categoryChoiceRefresh(){
        ObservableList<String> categoryOptions = FXCollections.observableArrayList();
        chooseCategoryChoiceBox.setItems(categoryOptions);
        chooseCategoryChoiceBox2.setItems(categoryOptions);
        catContWeightLabel.setText("Contaminated Weight (kg): 0.0");
        catTotalWeightLabel.setText("Total Weight (kg): 0.0");
        contaminationPercentLabel.setText("0.0");
        plasticWasteLabel.setText("Plastic Waste (kg): 0.0");
        metalWasteLabel.setText("Metal Waste (kg): 0.0");
        catContWeightLabel2.setText("Contaminated Weight (kg): 0.0");
        catTotalWeightLabel2.setText("Total Weight (kg): 0.0");
        contaminationPercentLabel2.setText("0.0");
        plasticWasteLabel2.setText("Plastic Waste (kg): 0.0");
        metalWasteLabel2.setText("Metal Waste (kg): 0.0");
        
        //clears barchart (sets both clean and contaminated portion of category to 0)
        seriesClean.getData().forEach((data)->{
            data.setXValue("Category");
            data.setYValue(0);
        });
        seriesContaminated.getData().forEach((data)->{
            data.setXValue("Category");
            data.setYValue(0);
        });
        
        switch (catComparisonTypeChoice){
            case 0: // Bin Category 
                try (ResultSet r = connectDB.createStatement().executeQuery("select category from BinCategories")){
                    while (r.next()){
                        categoryOptions.add(r.getString(1));
                    }
                } catch (SQLException e){
                    System.out.println("Case 0 fail");
                }
                break;
            case 1: // Plastic v Metals
                categoryOptions.add("Plastic Waste");
                categoryOptions.add("Metal Waste");
                catComparisonChoice = "Plastic Waste";
                catComparisonChoice2 = "Metal Waste";
                break;
            case 2: // Bins
                try (ResultSet r = connectDB.createStatement().executeQuery("select name from BinTable")){
                    while (r.next()){
                        categoryOptions.add(r.getString(1));
                    }
                } catch (SQLException e){
                    System.out.println("Case 2 fail");
                }
                break;
        }
        
        chooseCategoryChoiceBox.getSelectionModel().select(0);
        chooseCategoryChoiceBox2.getSelectionModel().select(1); 
        //Choiceboxes automatically selects first 2 elements
    }
    
    public void categoryComparisonChose(int firstorsecond){
        SLL<String> categories = new SLL<>();
        SLL<String> binTypes = new SLL<>(); //for when catComparisonTypeChoice = 0 (bin category); differentiate between metal and plastic
        float totalWeight = 0, contaminatedWeight = 0;
        float plasticWaste = 0, metalWaste = 0;
        String choice;
        
        if (firstorsecond == 1) choice = catComparisonChoice;
        else choice = catComparisonChoice2;
        System.out.println("barchartcat: "+barChartCat.size());
        barChartCat.remove(firstorsecond-1);
        barChartCat.add(firstorsecond - 1, choice);
        xAxis.setCategories(barChartCat);
        
        switch (catComparisonTypeChoice){
            case 0: // Bin Categories
                String query = "SELECT tagID, type FROM BinTable where category = '"+choice+"'";
                try (ResultSet r = connectDB.createStatement().executeQuery(query)){
                    while (r.next()){
                        categories.add(r.getString("tagID"));
                        binTypes.add(r.getString("type"));
                    }
                    String catString = "";
                    boolean notAddedYet = true;
                    for (int i = 0; i<categories.size(); i++) {
                        if (notAddedYet){
                            catString = categories.getData(i);
                            notAddedYet = false;
                        } else
                            catString = catString + "','" + categories.getData(i); //String to find bins in the bin category (in query)
                    }
                    //System.out.println(catString);
                    String query2 = "SELECT weight, typeOfWaste, tagID from BinRecords where tagID IN ('"+catString+"') and timeStamp between '"+fromDateComp+"' AND '"+toDateComp+"'";
                    
                    try (ResultSet r2 = connectDB.createStatement().executeQuery(query2)){
                        while (r2.next()){
                            float tempWeight = r2.getFloat("weight");
                            if(r2.getString("typeOfWaste").equals("Contaminated"))
                                contaminatedWeight += tempWeight;
                            if (binTypes.getData(categories.getIndex(r2.getString("tagID"))).equals("Plastic Waste")){
                                plasticWaste += tempWeight;
                            } else if (binTypes.getData(categories.getIndex(r2.getString("tagID"))).equals("Metal Waste")){
                                metalWaste += tempWeight;
                            }
                            totalWeight += tempWeight;
                        }
                    }
                } catch (SQLException err){
                    System.out.println("Error in catComparisonChose() case 0");
                }
                break;
            case 1: // Plastic v Metal
                query = "SELECT BinRecords.weight, BinRecords.typeOfWaste, BinTable.type FROM BinRecords"
                + " inner join BinTable on BinRecords.tagID = BinTable.tagID"
                + " where BinTable.type = '"+choice+"' and BinRecords.timeStamp BETWEEN '"+fromDateComp+"' AND '"+toDateComp+"'";
                        
                try (ResultSet r = connectDB.createStatement().executeQuery(query)){
                    while (r.next()){
                        if(r.getString("typeOfWaste").equals("Contaminated"))
                            contaminatedWeight += r.getFloat("weight");
                        totalWeight += r.getFloat("weight");
                    }
                } catch (SQLException err){System.out.println("Error in catComparisonChose() case 1");}
                if (choice.equals("Metal Waste")){
                    metalWaste = totalWeight;
                    plasticWaste = 0;
                } else if (choice.equals("Plastic Waste")){
                    plasticWaste = totalWeight;
                    metalWaste = 0;
                }
                break;
            case 2:
                try (ResultSet r = connectDB.createStatement().executeQuery("SELECT tagID, type from BinTable where name = '"+choice+"'")){
                    r.next();
                    String chosenBinID = r.getString("tagID");
                    String type = r.getString("type");
                    System.out.println("id"+chosenBinID+". type: "+type);
                    String query2 = "SELECT weight, typeOfWaste from BinRecords where tagID = "+chosenBinID +" and timeStamp between '"+fromDateComp+"' AND '"+toDateComp+"'";
                    try (ResultSet r2 = connectDB.createStatement().executeQuery(query2)){
                        while (r2.next()){
                            //System.out.println("run");
                            if(r2.getString("typeOfWaste").equals("Contaminated"))
                                contaminatedWeight += r2.getFloat("weight");
                            totalWeight += r2.getFloat("weight");
                            
                        }
                    }
                    if (type.equals("Metal Waste")){
                        metalWaste = totalWeight;
                        plasticWaste = 0;
                    } else if (type.equals("Plastic Waste")){
                        plasticWaste = totalWeight;
                        metalWaste = 0;
                    }
                } catch (SQLException e) {
                    System.out.println("SQLException case2 categoryCompChose");
                    //error label
                    return;
                }

        }
        float co2Saved = co2Saved(plasticWaste, metalWaste);
        if (firstorsecond == 1){
            catContWeightLabel.setText("Contaminated Weight (kg): "+contaminatedWeight);
            catTotalWeightLabel.setText("Total Weight (kg): "+totalWeight);
            
            float contPercent = 100*(contaminatedWeight/totalWeight);

            contaminationPercentLabel.setText(String.format("%.1f",contPercent));
            plasticWasteLabel.setText("Plastic Waste (kg): "+plasticWaste);
            metalWasteLabel.setText("Metal Waste (kg): "+metalWaste);
            
            co2SavedLabel.setText(String.format("%.1f", co2Saved));
            
        } else if (firstorsecond == 2){
            catContWeightLabel2.setText("Contaminated Weight (kg): "+contaminatedWeight);
            catTotalWeightLabel2.setText("Total Weight (kg): "+totalWeight);
            float contPercent = 100*(contaminatedWeight/totalWeight);

            contaminationPercentLabel2.setText(String.format("%.1f",contPercent));
            plasticWasteLabel2.setText("Plastic Waste (kg): "+plasticWaste);
            metalWasteLabel2.setText("Metal Waste (kg): "+metalWaste);
            
            co2SavedLabel2.setText(String.format("%.1f", co2Saved));
        }
        seriesClean.getData().remove(firstorsecond-1);
        seriesContaminated.getData().remove(firstorsecond-1);
        seriesClean.getData().add(firstorsecond-1,new XYChart.Data<>(choice, totalWeight - contaminatedWeight));
        seriesContaminated.getData().add(firstorsecond-1,new XYChart.Data<>(choice, contaminatedWeight));
    }
    
    private float co2Saved(float plastic, float metal){
        return (float)(plastic * 1.08 + metal * 8.14);
        // https://changeit.app/blog/recycle-matters/ source
    }
    
    @FXML
    public void generateReport(ActionEvent e){
        
        String reportPath = "src/main/resources/com/epc/environmentapp/test.jrxml";
        try {
            JasperDesign jd = JRXmlLoader.load(reportPath); // Load jasperdesign from jrxml file
            JRDesignQuery q = new JRDesignQuery(); //Initialise JRDesignQuery for data got in JasperReport during compiling report
            q.setText("select tagID, weight, typeOfWaste, notes, username from BinRecords where timeStamp BETWEEN '"+fromDate+"' AND '"+toDate+"'");
            jd.setQuery(q); //Set query to the one above (Finding records from specific time period)
            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("dates", "From "+fromDate.toString()+" to "+toDate.toString()); 
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, parameter, connectDB);//"Parameter" in JRXML - set report header text to selected time period 
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            System.out.println("Error");
        } 
    }
}
