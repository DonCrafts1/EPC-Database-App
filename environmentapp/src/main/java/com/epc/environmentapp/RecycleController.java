package com.epc.environmentapp;

import com.mysql.cj.exceptions.CJCommunicationsException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class RecycleController {
    @FXML
    private Pane lostConnectionPane;
    @FXML
    private Pane blockPane; //this is for when user presses a button to bring up a pane
    //a pane blocks rest of buttons from being pressed
    @FXML
    private Label errorLabel;
    @FXML
    private Label helloLabel;
    @FXML
    private Button logoutButton;
    @FXML
    private Pane enterPane;
    @FXML
    private Pane lookPane;
    @FXML
    private Pane accPane;
    @FXML
    private Pane addBinPane;
    @FXML
    private Label binAlreadyExistsLabel;
    @FXML
    private TextField codeEnterTextField; //for addBin
    @FXML
    private TextField binNameEnterTextField;
    @FXML
    private TextField binCodeEnterTextField;
    @FXML
    private TextField binWeightEnterTextField;
    
    @FXML
    private ComboBox selectCategoryBox;

    @FXML
    private ChoiceBox typeOfBinSelect;
   
    @FXML
    private Pane lookupPane;
    @FXML
    private Label binNotFoundLabel;
    @FXML
    private Button binLookupButton;
    @FXML
    private Pane addEntryPane;
    @FXML
    private Label binCodeAddEntryLabel;
    @FXML
    private Label binNameAddEntryLabel;
    @FXML
    private Label selectImageLabel;
    @FXML
    private TextField weightTextField;
    @FXML
    private TextField noteTextField;
    @FXML
    private Label weightNotValidLabel;
    @FXML
    private Pane lookupViewPane;
    
    @FXML
    private Pane binCategoriesPane;
    @FXML
    private TableView<String> binCategoryTable;
    @FXML
    private TableColumn<String, String> binCategoryCol;
    @FXML
    private TextField addBinCategoryTextField;
    @FXML
    private TextField deleteBinCategoryTextField;
    @FXML
    private Button binCategoryAddButton;
    
    @FXML
    private Pane addAccountPane;
    @FXML
    private TextField accountNameTextField;
    @FXML
    private TextField accountPassTextField;
    @FXML
    private Label addAccountErrorLabel;
    @FXML
    private ChoiceBox roleChoiceBox;
    @FXML
    private Button viewAccountButton;
    
    @FXML
    private ChoiceBox typeOfWasteSelect;
   
    @FXML
    private TableView<binRecord> lookupTable;
    @FXML
    private TableColumn<binRecord, String> usernameCol;
    @FXML
    private TableColumn<binRecord, String> timeStampCol;
    @FXML
    private TableColumn<binRecord, String> typeOfWasteCol;
    @FXML
    private TableColumn<binRecord, Float> weightCol;
    @FXML
    private TableColumn<binRecord, String> notesCol;
    @FXML
    private TableColumn<binRecord, Button> infoCol;
    
    @FXML
    private Pane allRecordsPane;
    @FXML
    private TableView<binRecordAll> allRecordsTable;
    @FXML
    private TableColumn<binRecordAll, String> binCodeCol;
    @FXML
    private TableColumn<binRecordAll, String> usernameColAll;
    @FXML
    private TableColumn<binRecordAll, String> timeStampColAll;
    @FXML
    private TableColumn<binRecordAll, String> typeOfWasteColAll;
    @FXML
    private TableColumn<binRecordAll, String> weightColAll;
    @FXML
    private TableColumn<binRecordAll, String> notesColAll;
    @FXML
    private TableColumn<binRecordAll, Button> editColAll;
    @FXML
    private Label allRecordsWeekWeightLabel;
    
    @FXML
    private Pane viewRecordPane;
    @FXML
    private Label viewRecordCodeLabel;
    @FXML
    private Label viewRecordUserLabel;
    @FXML
    private Label viewRecordBinTypeLabel;
    @FXML
    private Label viewRecordBinNameLabel;
    @FXML
    private Label viewRecordTimestampLabel;
    @FXML
    private Label viewRecordWeightLabel;
    @FXML
    private Label viewRecordsNotesLabel;
    @FXML
    private Label viewRecordImageLabel;
    
    @FXML
    private Pane binListPane;
    @FXML
    private Button isSearchUsingNameButton;
    @FXML
    private TableView<binTable> binListTable;
    @FXML
    private TableColumn<binTable, String> LbinCategoryCol;
    @FXML
    private TableColumn<binTable, String> LbinNameCol;
    @FXML
    private TableColumn<binTable, String> LbinCodeCol;
    @FXML
    private TableColumn<binTable, Float> LbinMaxWeightCol;
    @FXML
    private TableColumn<binTable, Float> LbinContainsCol;
    @FXML
    private TableColumn<binTable, String> LbinTypeCol;
    @FXML
    private TableColumn<binTable, Button> LbinEditCol;
    @FXML
    private ComboBox binListSelectCategoryBox;
    
    @FXML
    private Pane editBinPane;
    @FXML
    private TextField editCodeTextField;
    @FXML
    private TextField editBinNameTextField;
    @FXML
    private TextField editBinWeightTextField;
    @FXML
    private ChoiceBox editTypeOfBinSelect;
    @FXML
    private Label editBinAlreadyExistsLabel;
    @FXML
    private ComboBox editSelectCategoryBox;
    @FXML
    private Button deleteBinButton;
    @FXML
    private Pane userViewPane;
    @FXML
    private TableView<accTable> accountTable;
    @FXML
    private TableColumn<accTable, String> roleCol;
    @FXML
    private TableColumn<accTable, String> userCol;
    @FXML
    private TableColumn<accTable, String> passCol;
    @FXML
    private TableColumn<accTable, String> userAddCol;
    @FXML
    private TextField binSearchTextField;
    
    @FXML
    private Button lookupClearAllButton;
    @FXML
    private Button lookupClearAllConfirmButton;
    @FXML
    private Label binCodeLookupLabel;
    @FXML
    private Label binNameLookupLabel;
    @FXML
    private Label binLookupWeightLabel;
    
    @FXML
    private Pane removeUserPane;
    @FXML
    private TextField editUsernameTextField;
    @FXML
    private TextField editUserPassTextField;
    @FXML
    private Label removeAccountErrorLabel; //view
    @FXML
    private Button removeAccountButton;
    @FXML
    private ChoiceBox roleEditChoiceBox;
    @FXML
    private Pane enterWeeklyTotalPane;
    @FXML
    private Label enterWeeklyTotalErrorLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label weekDisplayLabel;
    @FXML
    private TextField enterWeekWeightTextField;
    @FXML
    private Button transferOwnershipButton;
        
    DatabaseConnection connectNow = LoginController.connectNow;
    //DatabaseConnection testConnection = new DatabaseConnection(); //used for testing connection every 3s
    Connection connectDB = LoginController.connectDB; //connect to the database
    
    String username, role;
       
    Timer timer = new Timer(); //used for checking connection every 5s in initialize()
    
    @FXML //remember to put @FXML, since if no, typeOfWasteSelect does not exist yet when initialize is run
    private void initialize(){
        
        Locale.setDefault(Locale.US); //for setting datepicker (calendar) to have sunday as first day 
        username = LoginController.username; //both for records and for seeing username
        role = LoginController.role;
        //if helper role, disable some functions
        if (role.equals("Helper")){
            transferOwnershipButton.setVisible(false);
            removeUserPane.setDisable(true);
            viewAccountButton.setDisable(true);
            roleChoiceBox.setVisible(false);
        }
        ObservableList<String> roleList = FXCollections.observableArrayList("Admin", "Helper");
        roleChoiceBox.setItems(roleList);
        roleEditChoiceBox.setItems(roleList);
        typeOfWasteSelect.setItems(FXCollections.observableArrayList("Clean", "Contaminated"));  //essential for setting items into the choicebox
        typeOfWasteSelect.setValue("Clean");
        ObservableList<String> typeOfBinList = FXCollections.observableArrayList("Plastic Waste", "Metal Waste");
        typeOfBinSelect.setItems(typeOfBinList); 
        editTypeOfBinSelect.setItems(typeOfBinList);
        typeOfBinSelect.setValue("Plastic Waste");
        helloLabel.setText("Hello, "+username+"!");
        DropShadow shadow = new DropShadow(8, Color.BLACK);
        enterPane.setEffect(shadow); lookPane.setEffect(shadow); accPane.setEffect(shadow);

        allRecordsTable.setPlaceholder(new Label("No bin records!"));
        lookupTable.setPlaceholder(new Label("No bin records!"));
        binListTable.setPlaceholder(new Label("No bins!"));
        binCategoryTable.setPlaceholder(new Label("No bin categories!"));
        datePicker.setShowWeekNumbers(false);
        TimerTask checkConn = new TimerTask(){
            @Override
            public void run(){
                //if (testConnection.isValid()){
                checkConnection(connectDB);
            }
        };
        timer.scheduleAtFixedRate(checkConn,0,3000); //Connection is checked every 3 seconds
    }
    
    public void checkConnection(Connection connectDB){ 
        try {
            PreparedStatement ps = connectDB.prepareStatement("SELECT 1");
            ps.execute(); //Executes test query
            Platform.runLater(() -> {
                lostConnectionPane.setVisible(false);
            }); //If connection revives, remove lostConnectionPane
        } catch (SQLException | NullPointerException e) {
            Platform.runLater(() -> {lostConnectionPane.setVisible(true);}); //If connection dies, show lostConnectionPane
        }
    }
    
    @FXML
    public void restartConnection(ActionEvent e)  {
        try {
            connectDB.close();
            connectDB = connectNow.getConnection(); //Get connection
        } catch (CJCommunicationsException | SQLException error) {}
        if (connectNow.valid){ //I can also use connectDB.isValid(3 or smth) 
            lostConnectionPane.setVisible(false);
            errorLabel.setVisible(false);
        } 
    }
    
    String code = null; //the generated code for addBin(), checkIfCodeExists(), and addBinOK()
    
    boolean addEntryLookup = false; //for toggling between button action for lookup screen
    
    public void executeUpdate(String update) throws SQLException{
        try (Statement s = connectDB.createStatement()) {
            s.executeUpdate(update);
        }
    }
    
    ObservableList<String> binCategories = FXCollections.observableArrayList(); //public, used in addBin() and bin category methods
    @FXML
    public void addBin(ActionEvent e) throws SQLException{
        blockPane.setVisible(true);
        addBinPane.setVisible(true);
        codeEnterTextField.clear();
        binNameEnterTextField.clear();
        binWeightEnterTextField.clear();
        binAlreadyExistsLabel.setVisible(false);
        codeEnterTextField.setText("Loading...");
        Random r = new Random();
        code = String.format("%04d",r.nextInt(10000)); //Randomly generate 4 digit code 
        while (checkIfCodeExists(code) == true){ //If code exists in database, generate another
            code = String.format("%04d",r.nextInt(10000));
            checkIfCodeExists(code);
        } 
        codeEnterTextField.setText(code);
        try (Statement s = connectDB.createStatement()){
            ResultSet rs = s.executeQuery("SELECT category FROM BinCategories ORDER BY category");
            while (rs.next()){
                binCategories.add(rs.getString(1)); 
            }
        }
        selectCategoryBox.setItems(binCategories); //Choicebox for categories
        selectCategoryBox.getSelectionModel().selectFirst();
    }
    
    public boolean checkIfCodeExists(String code) throws SQLException{
        String verifyIfBinCodeExists = "SELECT count(1) FROM BinTable WHERE tagID = '"+code+"'";
        Boolean exists = false;
        try (Statement st = connectDB.createStatement();) {
            //statements allow to issue SQL queries to the database
            ResultSet r = st.executeQuery(verifyIfBinCodeExists); //executes the query (bincode string)
            //resultset also gets the result of the sql query, only 1 ResultSet per statement can be open at 1 time
            if (r.next()) exists = r.getBoolean(1); //if found match, exists = true
        } 
        return exists;
    }
    
    public boolean checkIfBinNameExists(String name) throws Exception{
        String verifyIfBinNameExists = "SELECT count(1) FROM BinTable WHERE name = BINARY'"+name+"'";
        Boolean exists = false;
        try (Statement statement = connectDB.createStatement()) {
            ResultSet queryResult = statement.executeQuery(verifyIfBinNameExists); 
            if(queryResult.next()){
                exists = queryResult.getBoolean(1); //if bin is found in BinTable with inputted name, exists = true
            }
        }
        return exists;
    }
    
    @FXML
    public void addBinOK(ActionEvent e) throws Exception{
        code = codeEnterTextField.getText();
        String binName = binNameEnterTextField.getText();
        String maxWeightString = binWeightEnterTextField.getText();
        float maxWeight;
        try {
            maxWeight = Float.parseFloat(maxWeightString);
        } catch (NumberFormatException error){ //Will catch error when input includes non-number symbol, excluding dot
            binAlreadyExistsLabel.setText("Bin max weight must be a number!");
            binAlreadyExistsLabel.setVisible(true);
            return;
        }
        if (checkIfBinNameExists(binName)){ //Checks if bin name already exists in BinTable
            binAlreadyExistsLabel.setText("Warning: Bin already exists");
            binAlreadyExistsLabel.setVisible(true);
        } else if (binName.isBlank()){ //Checks if inputted bin name is empty (spaces included)
            binAlreadyExistsLabel.setText("Warning: Bin name cannot be empty");            
            binAlreadyExistsLabel.setVisible(true);
        } else if (code.length() != 4){ //Checks if bin code length is not 4 characters
            binAlreadyExistsLabel.setText("Bin code has to contain 4 characters");
            binAlreadyExistsLabel.setVisible(true);
        } else if (checkIfCodeExists(code)){ //Checks if bin code exists in database
            binAlreadyExistsLabel.setText("Warning: Bin code already exists");
            binAlreadyExistsLabel.setVisible(true);
        } else if (maxWeight < 1 || maxWeight > 100){ //Checks if maxWeight is a valid value
            binAlreadyExistsLabel.setText("Warning: Enter valid max capacity");
            binAlreadyExistsLabel.setVisible(true);
        } else {
            String type = typeOfBinSelect.getValue().toString();
            String category = selectCategoryBox.getValue().toString();
            System.out.println(category);
            String addBin = "INSERT INTO BinTable(tagID, name, type, maxWeight, contains, category) VALUES "
                    + "('"+code+"','"+binName+"','"+type+"','"+maxWeight+"','0','"+category+"')";
            try{
                executeUpdate(addBin);
                errorLabel.setVisible(false);
                addBinPane.setVisible(false);
                blockPane.setVisible(false); 
            } catch (SQLException error) {
                errorLabel.setVisible(true);
            }
        }
    }
    
    @FXML
    public void addBinCancel(ActionEvent e){
        addBinPane.setVisible(false);
        blockPane.setVisible(false);
        binCategories.clear();
    }

    @FXML
    public void addEntry(ActionEvent e) throws SQLException{
        lookupPane.setVisible(true);
        binCodeEnterTextField.clear();
        addEntryLookup = true;
        binLookupButton.setText("Add");
        blockPane.setVisible(true);
    }
    
    @FXML
    public void addEntryCancel(ActionEvent e){
        addEntryPane.setVisible(false);
        blockPane.setVisible(false);
    }
    
    File selectedImage;
    @FXML
    public void selectImage(ActionEvent e){
        FileChooser chooser = new FileChooser();  
        chooser.setTitle("Select Photo");
        Stage stage = (Stage)logoutButton.getScene().getWindow();
        File chosen = chooser.showOpenDialog(stage); // opens JavaFX file chooser
        if (chosen != null){ // checks if file is null
            String chosenFileName = chosen.getName(); // gets file name from chosen file
            String extension = "";
            int i = chosenFileName.lastIndexOf('.'); // find index of character "." in filename string 
            if (i >= 0) 
                extension = chosenFileName.substring(i+1).toLowerCase(); //get extension through 
            if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")){
                if (chosen.length() < 4000000){
                //note: 16MiB ~ 16,777,216 bytes refers to the max size of MEDIUMBLOB in MySQL. 4MB chosen as a safe limit.
                selectedImage = chosen; // assigns chosen file to global file variable
                selectImageLabel.setText("Selected: "+selectedImage.getName());
                } else {
                    selectImageLabel.setText("Image cannot exceed 4MB!");
                }
            } else {
                selectImageLabel.setText("Select valid format (jpg, jpeg, png)!");
            }
        }
        else selectImageLabel.setText("");
    }
    @FXML
    public void deselectImage(ActionEvent e){
        selectedImage = null;
        selectImageLabel.setText("");
    }
    
    @FXML
    public void addEntryOK(ActionEvent e) throws SQLException, FileNotFoundException {
        weightNotValidLabel.setText("Enter a valid weight!");
        float weight;
        float binMaxWeight;
        float binContains;
        try {
            weight = Float.parseFloat(weightTextField.getText()); 
        } catch (NumberFormatException e2){
            weightNotValidLabel.setVisible(true);
            return;
        } //If user enters anything other than float format, does not run rest of code
        if (weight <= 0 || weight > 1000){
            weightNotValidLabel.setVisible(true);
            return;
        }
        if (connectDB.isValid(3)){
            try (Statement st = connectDB.createStatement()) {
                ResultSet rs = st.executeQuery("SELECT maxWeight, contains FROM BinTable WHERE tagID = '"+foundCode+"'");
                rs.next();
                binMaxWeight = rs.getFloat(1);
                binContains = rs.getFloat(2); //Get maxWeight and amount of waste bin contains at the moment...
            }
            if (weight + binContains > binMaxWeight){ //... to see if new entry would exceed it
                weightNotValidLabel.setVisible(true);
                weightNotValidLabel.setText("Bin capacity exceeded!");
            } else {
                String notes = noteTextField.getText(); //Get notes from text field
                String choice = typeOfWasteSelect.getValue().toString(); //Get clean/contaminated from choicebox
                
                //Use file input stream for selectedImage file object
                FileInputStream input = null; 
                int filesize = 0;
                if (selectedImage != null){
                    input = new FileInputStream(selectedImage);
                    filesize = (int)selectedImage.length();
                }
                String command = "INSERT INTO BinRecords (username,tagID,typeOfWaste,weight,notes,image) VALUES (?,?,?,?,?,?)";
                String command2 = "UPDATE BinTable SET contains = "+(binContains+weight)+" WHERE tagID = '"+foundCode+"'";
                try {
                    try (PreparedStatement ps = connectDB.prepareStatement(command)) {
                        ps.setString(1, username);
                        ps.setString(2, foundCode);
                        ps.setString(3, choice);
                        ps.setFloat(4, weight);
                        ps.setString(5, notes);
                        if (input != null)
                            ps.setBinaryStream(6, input, filesize); //If there is an image, add it to preparedStatement
                        else
                            ps.setBinaryStream(6, null);
                        ps.executeUpdate(); //Insert entry into database
                    }
                    executeUpdate(command2);
                } catch (SQLException error) {
                    errorLabel.setVisible(true);
                    return;
                }
                //this is for actually inserting data, unlike before, where it is looking up against the database
                addEntryPane.setVisible(false);
                typeOfWasteSelect.setValue("Clean");
                weightTextField.clear();
                noteTextField.clear();
                errorLabel.setVisible(false);
                blockPane.setVisible(false);
                selectedImage = null;
            }   
        } else {
            lostConnectionPane.setVisible(true);
        }
    }
    
    @FXML
    public void binCategoryView(ActionEvent e) throws SQLException{
        binCategoriesPane.setVisible(true);
        blockPane.setVisible(true);
        binCategoryCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        new Thread(){
            @Override
            public void run(){
                try (Statement s = connectDB.createStatement()){
                    ResultSet rs = s.executeQuery("SELECT category FROM BinCategories ORDER BY category");
                    while (rs.next()){
                        binCategories.add(rs.getString(1));
                    }
                } catch (SQLException error){}
                Platform.runLater(() -> {
                    binCategoryTable.setItems(binCategories); //Sets table to see bin categories
                });
            }
        }.start();
        
        addBinCategoryTextField.textProperty().addListener((observable, oldval, newval) -> {
            if (oldval.equals("") && !(newval.equals("") || newval.equals(" "))) binCategoryAddButton.setDisable(false); 
            else if (newval.equals("")) binCategoryAddButton.setDisable(true); //If no text, Add Button is grayed out
            else if (newval.length() == 20) addBinCategoryTextField.setText(oldval); //Limit to how long binCategory is
        });
        binCategoryTable.getSelectionModel().selectedItemProperty().addListener((observable, oldval, newval) -> {
            //Did it this way since table is only populated with Strings
            if (newval != null){
                deleteBinCategoryTextField.setText(newval);
            }
        });
    }
    
    @FXML
    public void binCategoryBack(ActionEvent e){
        binCategoriesPane.setVisible(false);
        blockPane.setVisible(false);
        deleteBinCategoryTextField.setText("");
        binCategories.clear();
    }
    
    @FXML
    public void binCategoryAdd(ActionEvent e) throws SQLException{
        String newCategory = addBinCategoryTextField.getText();
        if (!newCategory.isEmpty() && !binCategories.contains(newCategory)){
            String cmd = "INSERT INTO BinCategories (category) VALUES (?)";
            try (PreparedStatement ps = connectDB.prepareStatement(cmd)){
                ps.setString(1,newCategory);
                ps.executeUpdate();
            }
            //Adds category into database
            binCategories.add(newCategory);
            binCategoryTable.setItems(binCategories);
        }
    }
    
    @FXML
    public void binCategoryDelete(ActionEvent e) throws SQLException{
        String categoryToDelete = deleteBinCategoryTextField.getText();
        executeUpdate("DELETE from BinCategories WHERE category = '"+categoryToDelete+"'");
        executeUpdate("UPDATE BinTable SET category = '' WHERE category = '"+categoryToDelete+"'");
        //Removes category from database
        binCategories.remove(categoryToDelete);
        binCategoryTable.setItems(binCategories);
    }
    
    LocalDate ld; //for enterWeeklyTotal, findWeek (and weekDate)
    
    public LocalDate[] weekDate(LocalDate ld){
        LocalDate[] week = {ld,ld}; //Creates LocalDate array to return, both assigned to parameter LocalDate value
        while (week[0].getDayOfWeek() != DayOfWeek.SUNDAY) { //Until first date is Sunday, "minus" the date
           week[0] = week[0].minusDays(1); 
        }
        while (week[1].getDayOfWeek() != DayOfWeek.SATURDAY) { //Until second date is Saturday, "plus" the date
           week[1] = week[1].plusDays(1);
        }
        return week; //Return array (two dates)
    }
    
    @FXML
    public void enterWeeklyTotal(ActionEvent e) throws SQLException{
        blockPane.setVisible(true);
        enterWeeklyTotalPane.setVisible(true);
        enterWeeklyTotalErrorLabel.setVisible(false);
        ld = LocalDate.now(); 
        datePicker.setValue(ld); //Set date picker to today
        LocalDate[] week = weekDate(ld); //Finds start and end date for this week
        weekDisplayLabel.setText(week[0].toString()+" to "+week[1].toString()); //GUI
        String query = "SELECT * FROM BinWeeklyTotal WHERE yearweek(timeStamp) = yearweek('"+ld.toString()+"')";
        try (Statement st = connectDB.createStatement(); Statement st2 = connectDB.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            boolean exist = rs.next();
            float tempWeight;
            if (exist){
                tempWeight = rs.getFloat(1);
                enterWeekWeightTextField.setText(String.valueOf(tempWeight));
                if (rs.getInt("edited") == 0){
                    ResultSet rs2 = st2.executeQuery("SELECT sum(weight) FROM BinRecords WHERE yearweek(timeStamp) = yearweek('"+ld.toString()+"')");
                    float totalWeight = 0;
                    if (rs2.next())
                        totalWeight = Float.parseFloat(rs2.getString(1));
                    executeUpdate("UPDATE BinWeeklyTotal SET weekWeight = '"+totalWeight+"' WHERE yearweek(timeStamp) = yearweek('"+ld.toString()+"')");
                }
            } else {
                ResultSet rs2 = st2.executeQuery("SELECT sum(weight) FROM BinRecords WHERE yearweek(timeStamp) = yearweek('"+ld.toString()+"')");
                float totalWeight = 0;
                if (rs2.next()){
                    try {
                        totalWeight = Float.parseFloat(rs2.getString(1));
                        enterWeekWeightTextField.setText(Float.toString(totalWeight));
                    } catch (NullPointerException error){
                        totalWeight = 0;
                        enterWeekWeightTextField.setText("0.0");
                    }
                }
                executeUpdate("INSERT into BinWeeklyTotal (timeStamp, weekWeight, edited) VALUES (now(), "+totalWeight+", 0)");
            }
        }

        datePicker.setOnAction((ActionEvent event) -> {
            findWeek();
        });
    }
    @FXML
    public void findWeek(){ //PART OF enterWeeklyTotal (module)
        ld = datePicker.getValue(); //datePicker is the calendar
        String query = "SELECT * FROM BinWeeklyTotal WHERE yearweek(timeStamp) = yearweek('"+ld.toString()+"')";
        try {
            try (Statement st = connectDB.createStatement()) {
                ResultSet rs = st.executeQuery(query);
                if (rs.next()){
                    enterWeeklyTotalErrorLabel.setVisible(false);
                    LocalDate[] week = weekDate(ld);
                    weekDisplayLabel.setText(week[0].toString()+" to "+week[1].toString());
                    enterWeekWeightTextField.setText(String.valueOf(rs.getFloat(1)));
                } else {
                    enterWeeklyTotalErrorLabel.setText("No valid week found!");
                    enterWeeklyTotalErrorLabel.setVisible(true);
                }
            }
        } catch (SQLException error){
            enterWeeklyTotalErrorLabel.setText("Warning: Input error");
            enterWeeklyTotalErrorLabel.setVisible(true);
        }
    }
    
    @FXML
    public void enterWeeklyTotalConfirm (ActionEvent e) throws SQLException{
        float weeklyTotal;
        try {
            weeklyTotal = Float.valueOf(enterWeekWeightTextField.getText());
        } catch(NumberFormatException error){
            enterWeeklyTotalErrorLabel.setVisible(true);
            enterWeeklyTotalErrorLabel.setText("Enter valid weight!");
            return;
        }
        if (weeklyTotal < 0 || weeklyTotal > 500){
            enterWeeklyTotalErrorLabel.setVisible(true);
            enterWeeklyTotalErrorLabel.setText("Enter valid weight!");
            return;
        }
        executeUpdate("UPDATE BinWeeklyTotal SET weekWeight = '"+weeklyTotal+"', edited = 1 WHERE yearweek(timeStamp) = yearweek('"+ld.toString()+"')");
        enterWeeklyTotalPane.setVisible(false);
        blockPane.setVisible(false);
    }
    
    @FXML
    public void enterWeeklyTotalReset(ActionEvent e) throws SQLException{
        String cmd = "SELECT sum(weight) FROM BinRecords WHERE yearweek(timeStamp) = yearweek('"+ld.toString()+"')";
        String weekBinWeight = "";
        try (Statement st = connectDB.createStatement()) {
            ResultSet r = st.executeQuery(cmd);
            if (r.next())
                weekBinWeight = r.getString(1);
        }
        if (weekBinWeight == null)
            weekBinWeight = "0.0";
        enterWeekWeightTextField.setText(weekBinWeight);
    }
    
    @FXML
    public void enterWeeklyTotalCancel(ActionEvent e){
        enterWeeklyTotalPane.setVisible(false);
        blockPane.setVisible(false);
    }
    
    String foundCode = null, foundName = null;  //foundcode, foundname used in lookup, editBin 
    @FXML
    public void lookupOK(ActionEvent e) throws Exception{ //serves as addEntry and lookUp
        String enteredCode = binCodeEnterTextField.getText();
        blockPane.setVisible(true);
        if (checkIfCodeExists(enteredCode)){
            foundCode = enteredCode;
            try (Statement st = connectDB.createStatement()){
                ResultSet r = st.executeQuery("SELECT name from BinTable where tagID = '"+foundCode+"'"); //Get bin name
                if (r.next()) foundName = r.getString(1);
            } catch (SQLException err){
                errorLabel.setVisible(true);
                return;
            }
            if (addEntryLookup) { //if addEntry, addEntry pane becomes visible
                lookupPane.setVisible(false);
                addEntryPane.setVisible(true);
                addEntryLookup = false;
                binCodeAddEntryLabel.setText("Bin Code: "+foundCode);
                binNameAddEntryLabel.setText("Bin Name: "+foundName);
            } else { //if lookUp, lookupTable pane becomes visible
                lookupPane.setVisible(false);
                lookupViewPane.setVisible(true);
                lookupTableRefresh();
            } 
            errorLabel.setVisible(false);
        } else {
            binNotFoundLabel.setVisible(true);
        }
    }
    
    class lookupTableFetchThread extends Thread{
        @Override public void start(){
            super.start();
        }
        @Override public void run(){
            ObservableList<binRecord> lookupList = FXCollections.observableArrayList();
            try (Statement st = connectDB.createStatement()) {
                ResultSet get = st.executeQuery("SELECT username, timeStamp, typeOfWaste, weight"
                        + ", notes FROM BinRecords WHERE tagID = '"+foundCode+"'");
                while (get.next()){
                    lookupList.add(new binRecord(get.getString("username"),get.getString("timeStamp"),
                            get.getString("typeOfWaste"),get.getFloat("weight"),get.getString("notes")));
                } //Adds the rows that have the entered ID into lookupList
            } catch (SQLException e){}
            if (lookupList.isEmpty() || role.equals("Helper"))
                lookupClearAllButton.setDisable(true);
            else 
                lookupClearAllButton.setDisable(false);
            
            Platform.runLater(() -> {
                lookupTable.setItems(lookupList);
            });
            
        }
        
    }
    
    public void lookupTableRefresh() throws SQLException{
        lookupClearAllButton.setVisible(true);
        lookupClearAllConfirmButton.setVisible(false);
        lookupTable.getItems().clear(); //clears the table
        String type = "", name = "";
        
        String weekly = "SELECT contains, type, name FROM BinTable WHERE tagID = '"+foundCode+"'";
        float weekBinWeight = 0f;
        try (Statement st = connectDB.createStatement()) {
            ResultSet r = st.executeQuery(weekly);
            if (r.next()){
                weekBinWeight = r.getFloat("contains");
                type = r.getString("type");
                name = r.getString("name");
            }
        } catch (SQLException err){
            errorLabel.setVisible(true);
            return;
        }
        binLookupWeightLabel.setText("Current Weight: "+String.format("%.2f",weekBinWeight)+"kg");
        binCodeLookupLabel.setText("Bin Code: "+foundCode);
        binNameLookupLabel.setText("Bin Name: "+foundName);
        
        final String type2 = type, name2 = name; //Since lambda functions avoid using non-final variables
        
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        timeStampCol.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
        typeOfWasteCol.setCellValueFactory(new PropertyValueFactory<>("typeOfWaste"));
        weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
        infoCol.setCellFactory(ActionButtonTableCell.<binRecord>forTableColumn("", (binRecord p) -> {
            viewRecordPane.setVisible(true);
            viewRecordCodeLabel.setText(foundCode);
            viewRecordTimestampLabel.setText(p.getTimeStamp());
            viewRecordWeightLabel.setText(Float.toString(p.getWeight())+" kg");
            viewRecordsNotesLabel.setText(p.getNotes());
            viewRecordUserLabel.setText(p.getUsername());
            viewRecordBinTypeLabel.setText(type2);
            viewRecordBinNameLabel.setText(name2);
            Image recordImage;
            try {
                String q = "SELECT image FROM BinRecords WHERE timeStamp = '"+p.getTimeStamp()+"'";
                try (Statement st2 = connectDB.createStatement()) {
                    ResultSet rsphoto = st2.executeQuery(q);
                    if (rsphoto.next()){
                        Blob blob = rsphoto.getBlob("image");
                        if (blob != null){
                            InputStream blobInput = blob.getBinaryStream();
                            recordImage = new Image(blobInput);
                            ImageView view = new ImageView(recordImage);
                            view.setFitHeight(170);
                            view.setFitWidth(320);
                            view.setPreserveRatio(true);
                            viewRecordImageLabel.setText("");
                            viewRecordImageLabel.setGraphic(view);
                        } else {
                            viewRecordImageLabel.setText("No image!");
                            viewRecordImageLabel.setGraphic(null);
                        }
                    } else {
                        viewRecordImageLabel.setText("No image!");
                        viewRecordImageLabel.setGraphic(null); 
                    }
                }
                errorLabel.setVisible(false);
            } catch (SQLException error) {
                errorLabel.setVisible(true);
            }
            return p;
        }, 90));

        new lookupTableFetchThread().start();
    }
    
    @FXML
    public void lookupClearAll(ActionEvent e){
        lookupClearAllButton.setVisible(false);
        lookupClearAllConfirmButton.setVisible(true);
    }
    
    @FXML
    public void lookupClearAllConfirm(ActionEvent e){
        try {
            executeUpdate("UPDATE BinTable SET contains = 0 WHERE tagID = '"+foundCode+"'");
            binLookupWeightLabel.setText("Current Weight: 0.00kg");
        } catch (SQLException err){
            errorLabel.setVisible(true);
            return;
        }
        lookupClearAllConfirmButton.setVisible(false);
        lookupClearAllButton.setVisible(true);
        lookupClearAllButton.setDisable(true);
    }
    
    @FXML
    public void lookupCancel(ActionEvent e){
        lookupPane.setVisible(false);
        blockPane.setVisible(false);
    }
    
    @FXML
    public void lookup(ActionEvent e) throws SQLException{
        lookupPane.setVisible(true);
        blockPane.setVisible(true);
        binLookupButton.setText("Find");
        binCodeEnterTextField.clear();
    }
    
    @FXML
    public void lookupBack(ActionEvent e){
        lookupViewPane.setVisible(false);
        blockPane.setVisible(false);
    }
    
    
    @FXML
    public void allRecords(ActionEvent e) throws SQLException{
        ObservableList<binRecordAll> allRecordsList = FXCollections.observableArrayList();
        if (connectDB.isValid(3)){ //timeout time = 3s
            allRecordsPane.setVisible(true);
            blockPane.setVisible(true);
            allRecordsTable.getItems().clear();
            
            binCodeCol.setCellValueFactory(new PropertyValueFactory<>("binCode"));
            usernameColAll.setCellValueFactory(new PropertyValueFactory<>("username"));
            timeStampColAll.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
            typeOfWasteColAll.setCellValueFactory(new PropertyValueFactory<>("typeOfWaste"));
            weightColAll.setCellValueFactory(new PropertyValueFactory<>("weight"));
            notesColAll.setCellValueFactory(new PropertyValueFactory<>("notes"));
            editColAll.setCellFactory(ActionButtonTableCell.<binRecordAll>forTableColumn("", p -> {
                viewRecordPane.setVisible(true);
                viewRecordCodeLabel.setText(p.getBinCode());
                viewRecordTimestampLabel.setText(p.getTimeStamp());
                viewRecordWeightLabel.setText(Float.toString(p.getWeight())+" kg");
                viewRecordsNotesLabel.setText(p.getNotes());
                viewRecordUserLabel.setText(p.getUsername());
                viewRecordBinTypeLabel.setText(p.getBinType());
                viewRecordBinNameLabel.setText(p.getBinName());
                Image recordImage;
                try {
                    String q = "SELECT image FROM BinRecords WHERE timeStamp = '"+p.getTimeStamp()+"'";
                    try (Statement st2 = connectDB.createStatement()) {
                        ResultSet rsphoto = st2.executeQuery(q);
                        if (rsphoto.next()){
                            Blob blob = rsphoto.getBlob("image");
                            if (blob != null){
                                InputStream blobInput = blob.getBinaryStream();
                                recordImage = new Image(blobInput);
                                ImageView view = new ImageView(recordImage);
                                view.setFitHeight(170);
                                view.setFitWidth(320);
                                view.setPreserveRatio(true);
                                viewRecordImageLabel.setText("");
                                viewRecordImageLabel.setGraphic(view);
                            } else {
                                viewRecordImageLabel.setText("No image!");
                                viewRecordImageLabel.setGraphic(null);
                            }
                        } else {
                            viewRecordImageLabel.setText("No image!");
                            viewRecordImageLabel.setGraphic(null); 
                        }
                    }
                } catch (SQLException error) {}
                return p;
            }, 70));
            String cmd = "SELECT BinRecords.username, "
                    + "BinRecords.timeStamp, "
                    + "BinRecords.typeOfWaste, "
                    + "BinRecords.weight, "
                    + "BinRecords.notes, "
                    + "BinRecords.tagID, BinTable.name, BinTable.type "
                    + "FROM BinRecords inner join BinTable on BinRecords.tagID = BinTable.tagID"; //also gets binName 
            //Optimise, get image blob when user clicks on Info button
            new Thread(){
                @Override
                public void run(){
                    try (Statement st = connectDB.createStatement()) {
                        ResultSet rs = st.executeQuery(cmd); //Gets all record info (excluding image)
                        while (rs.next()){
                            allRecordsList.add(new binRecordAll(rs.getString("username"),rs.getString("timeStamp"),rs.getString("typeOfWaste"),
                                rs.getFloat("weight"),rs.getString("notes"),  rs.getString("tagID"), rs.getString("name"), rs.getString("type")));
                        }
                    } catch (SQLException err) {
                        errorLabel.setVisible(true);
                        return;
                    }
                    float weekBinWeight = 0;
                    String weekly = "SELECT sum(weight) FROM BinRecords WHERE yearweek(timeStamp) = yearweek(now())";
                    try (Statement s = connectDB.createStatement()){
                        ResultSet r = s.executeQuery(weekly);
                        if (r.next()){
                            String val = r.getString(1);
                            if (val != null)
                                weekBinWeight = Float.parseFloat(val);
                             else 
                                weekBinWeight = 0;
                        }
                    } catch (SQLException err){}
                    final String weekStr = "This week: "+String.format("%.2f",weekBinWeight)+"kg";
                    Platform.runLater(() -> {
                        allRecordsTable.setItems(allRecordsList);
                        allRecordsWeekWeightLabel.setText(weekStr);
                    });
                }
            }.start();
            
        } else {
            errorLabel.setVisible(true);
            lostConnectionPane.setVisible(true);
            allRecordsList.clear();
        }
    }
    
    @FXML
    public void viewRecordBack(ActionEvent e){
        viewRecordPane.setVisible(false);
    }
    
    @FXML
    public void allRecordsBack(ActionEvent e){
        blockPane.setVisible(false);
        allRecordsPane.setVisible(false);
        System.gc();
    }
    
    boolean isSearchUsingName = true;
    String eventChoice = "All", binSearchString = "";
    String tempmaxWeight, tempbinType, tempbinCat;
    ObservableList<binTable> binList;
    ObservableList<binTable> tempBinList;
    @FXML
    public void viewBinList(ActionEvent e) throws SQLException, InterruptedException{
        binSearchTextField.clear();
        binCategories.clear();
        if (connectDB.isValid(3)){
            if (binList == null){
                binList = FXCollections.observableArrayList(); 
                tempBinList = FXCollections.observableArrayList();
            }
            binListPane.setVisible(true);
            blockPane.setVisible(true);
            binListTable.getItems().clear();
            LbinCategoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
            LbinCodeCol.setCellValueFactory(new PropertyValueFactory<>("binCode"));
            LbinNameCol.setCellValueFactory(new PropertyValueFactory<>("binName"));
            LbinTypeCol.setCellValueFactory(new PropertyValueFactory<>("binType"));
            LbinMaxWeightCol.setCellValueFactory(new PropertyValueFactory<>("maxWeight"));
            LbinContainsCol.setCellValueFactory(new PropertyValueFactory<>("contains"));
            LbinEditCol.setCellFactory(ActionButtonTableCell.<binTable>forTableColumn("Edit", (binTable p) -> {
                //each edit button in this column lets user edit bin contents
                editBinPane.setVisible(true);
                binListPane.setVisible(false);
                
                foundCode = p.getBinCode();// for viewLookupBinEntries later
                foundName = p.getBinName();// again, for viewLookupBinEntries later
                tempmaxWeight = String.valueOf(p.getMaxWeight());
                tempbinType = p.getBinType();
                tempbinCat = p.getCategory();

                editCodeTextField.setText(foundCode);
                editBinNameTextField.setText(foundName);
                editBinWeightTextField.setText(tempmaxWeight);
                editTypeOfBinSelect.setValue(tempbinType);
                editSelectCategoryBox.setValue(tempbinCat);

                return p;
            }, 105)); //<-- 105 refers to width of button size
            
            try (Statement s = connectDB.createStatement()){
                ResultSet rs = s.executeQuery("SELECT category FROM BinCategories ORDER BY category");
                binCategories.add("All");
                while (rs.next()){
                    binCategories.add(rs.getString(1));
                }
            }

            binListSelectCategoryBox.setItems(binCategories); // Sort bins by category
            binListSelectCategoryBox.getSelectionModel().selectFirst();
            binListSelectCategoryBox.valueProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal != null && newVal != oldVal){
                    eventChoice = newVal.toString();
                    specificBinSearch();
                }
            });
            binSearchTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                binSearchString = newValue.toLowerCase();
                specificBinSearch();
            });
            ObservableList editBinCategories = FXCollections.observableArrayList(binCategories);
            editBinCategories.remove("All");
            editSelectCategoryBox.setItems(editBinCategories); // For editing bin category
            refreshBinList();
        } else {
            lostConnectionPane.setVisible(true);
        }
    }
    
    private void specificBinSearch(){ //Called when user types into the bin search textbox
        tempBinList.clear();
        int size = binList.size();
        if (!binSearchString.equals("")){
            if (isSearchUsingName){
                if (eventChoice.equals("All"))
                    for (int i = 0; i < size; i++){
                        binTable temp = binList.get(i);
                        if (temp.getBinName().toLowerCase().contains(binSearchString))
                            tempBinList.add(temp);
                    }
                else {
                    for (int i = 0; i < size; i++){
                        binTable temp = binList.get(i);
                        if (temp.getBinName().toLowerCase().contains(binSearchString) && temp.getCategory().equals(eventChoice))
                            tempBinList.add(temp);
                    }
                }
            } else {
                if (eventChoice.equals("All"))
                    for (int i = 0; i < size; i++){
                        binTable temp = binList.get(i);
                        if (temp.getBinCode().toLowerCase().contains(binSearchString))
                            tempBinList.add(temp);
                    }
                else {
                    for (int i = 0; i < size; i++){
                        binTable temp = binList.get(i);
                        if (temp.getBinCode().toLowerCase().contains(binSearchString) && temp.getCategory().equals(eventChoice))
                            tempBinList.add(temp);
                    }
                }
            }
        } else {
            if (!eventChoice.equals("All")){
                for (int i = 0; i < size; i++){
                    binTable temp = binList.get(i);
                    if (temp.getCategory().equals(eventChoice))
                        tempBinList.add(temp);
                }
                
            } else {
                binListTable.setItems(binList);
                //if no search/no category, set table to all bins
                return;
            }
        }
        System.out.println(tempBinList);
        binListTable.setItems(tempBinList);
        
    }
    
    public void refreshBinList() throws SQLException{
        binList.clear();
        binListTable.getItems().clear();
        try (Statement s = connectDB.createStatement()) {
            ResultSet rs;
            if (eventChoice.equals("All")){
                rs = s.executeQuery("SELECT * FROM BinTable");
            } else {
                rs = s.executeQuery("SELECT * FROM BinTable WHERE category = '"+eventChoice+"'");
            }
            while (rs.next()){
                binList.add(new binTable(rs.getString("tagID"),rs.getString("name"),rs.getString("type")
                        ,rs.getFloat("maxWeight"),rs.getFloat("contains"),rs.getString("category")));
            }
            rs.close();
        } catch (SQLException e) {
            errorLabel.setVisible(true);
        }
        binListTable.setItems(binList);
    }
   
    @FXML
    public void switchBinSearch (ActionEvent e){
        if (isSearchUsingName) {
            isSearchUsingName = false;
            isSearchUsingNameButton.setText("Code");
            binSearchTextField.setPromptText("Search bin using code");
        } else {
            isSearchUsingName = true;
            isSearchUsingNameButton.setText("Name");
            binSearchTextField.setPromptText("Search bin using name");
        }
        binSearchTextField.clear();
    }
    
    @FXML
    public void viewLookupBinEntries(ActionEvent e) throws SQLException{
        editBinPane.setVisible(false);
        lookupViewPane.setVisible(true);
        lookupTableRefresh(); //relies on foundCode string variable to look for bin specific entries
    }
    
    @FXML
    public void viewBinListBack(ActionEvent e){
        binList = null;
        tempBinList = null;
        binCategories.clear();
        binListPane.setVisible(false);
        blockPane.setVisible(false);
    }
    
    @FXML
    public void editBinOK(ActionEvent e) throws Exception {
        //"c" refers to changed
        String cName = editBinNameTextField.getText();
        String cCode = editCodeTextField.getText();
        String cMaxWeight = editBinWeightTextField.getText();
        String cType = editTypeOfBinSelect.getValue().toString();
        String cCategory = editSelectCategoryBox.getValue().toString();
        System.out.println(cCategory);
        if (!(cName.equals(foundName) && cCode.equals(foundCode) && cMaxWeight.equals(tempmaxWeight) && cType.equals(tempbinType) && cCategory.equals(tempbinCat))){ 
            //checks if there was a change; if not, do nothing
            float maxWeight;
            try {
                maxWeight = Float.parseFloat(cMaxWeight);
            } catch (NumberFormatException error){
                editBinAlreadyExistsLabel.setText("Bin max weight must be a number!");
                return;
            }
            if (checkIfBinNameExists(cName) && !cName.equals(foundName)){
                editBinAlreadyExistsLabel.setText("Warning: Bin already exists");
                editBinAlreadyExistsLabel.setVisible(true);
            } else if (cName.isEmpty()){
                editBinAlreadyExistsLabel.setText("Warning: Bin name cannot be empty");            
                editBinAlreadyExistsLabel.setVisible(true);
            } else if (cCode.length() != 4){
                editBinAlreadyExistsLabel.setText("Bin code has to contain 4 characters");
                editBinAlreadyExistsLabel.setVisible(true);
            } else if (checkIfCodeExists(cCode) && !cCode.equals(foundCode)){
                editBinAlreadyExistsLabel.setText("Warning: Bin code already exists");
                editBinAlreadyExistsLabel.setVisible(true);
            } else if (maxWeight < 0 || maxWeight > 1000){
                editBinAlreadyExistsLabel.setText("Warning: Enter valid max capacity");
                editBinAlreadyExistsLabel.setVisible(true);
            } else {
                String cmd = "update BinTable set tagID = '"+cCode+"', name = '"+cName+"', maxWeight = '"+cMaxWeight+"', "
                        + "type = '"+cType+"', category = '"+cCategory+"' where tagID = '"+foundCode+"'";
                executeUpdate(cmd);
                binListPane.setVisible(true);
                refreshBinList();
            }
        }
        editBinPane.setVisible(false);
        editBinAlreadyExistsLabel.setVisible(false);
        binListPane.setVisible(true);
    }
    
    boolean deleteBinConfirm = false;
    @FXML
    public void deleteBin(ActionEvent e) throws SQLException{
        if (!deleteBinConfirm){
            deleteBinButton.setText("Confirm?");
            deleteBinConfirm = true;
        } else {
            String cmd = "DELETE from BinTable WHERE tagID = '"+foundCode+"'";
            executeUpdate(cmd);
            deleteBinButton.setText("Delete Bin");
            binListPane.setVisible(true);
            editBinPane.setVisible(false);
            refreshBinList();
            deleteBinConfirm = false;
        }   
    }
    
    @FXML
    public void editBinBack(ActionEvent e){
        binListPane.setVisible(true);
        editBinPane.setVisible(false);
        deleteBinConfirm = false;
        deleteBinButton.setText("Delete Bin");
    }
    @FXML
    public void addAccount(ActionEvent e) throws SQLException{
        addAccountPane.setVisible(true);
        blockPane.setVisible(true);
        accountNameTextField.clear();
        accountPassTextField.clear();
        addAccountErrorLabel.setVisible(false);
        roleChoiceBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void addAccountOK(ActionEvent e){
        String newUserName = accountNameTextField.getText();
        String newUserPass = accountPassTextField.getText();
        String newRole = roleChoiceBox.getValue().toString();
        addAccountErrorLabel.setVisible(true);
        try (Statement s = connectDB.createStatement();){
            ResultSet r = s.executeQuery("SELECT count(1) FROM UserAccounts WHERE Username = '"+newUserName+"'");
            while (r.next()){
                if (r.getInt(1) == 1){
                    addAccountErrorLabel.setText("Username exists in database!");
                    return;
                }
            }
            if (newUserName.isEmpty() || newUserPass.isEmpty()){
                addAccountErrorLabel.setText("Username/password is empty!");
                return;
            }
            executeUpdate("INSERT INTO UserAccounts (Username, Password, Role, UserAdd) VALUES "
                        + "('"+newUserName+"','"+newUserPass+"','"+newRole+"','"+username+"')");
            addAccountErrorLabel.setVisible(false);
            accountNameTextField.clear();
            accountPassTextField.clear();
            addAccountPane.setVisible(false);
            blockPane.setVisible(false);
        }
         catch (SQLException err) {
            addAccountErrorLabel.setText("Something went wrong!");
        }
    }

    @FXML
    public void addAccountCancel(ActionEvent e){
        addAccountPane.setVisible(false);
        blockPane.setVisible(false);
    }
    
    private accTable selectedAcc = new accTable(); // If not declared here, lambda expression means cannot assign
    @FXML
    public void viewAccount(ActionEvent e) throws SQLException{
        userViewPane.setVisible(true);
        blockPane.setVisible(true);
        removeAccountErrorLabel.setVisible(false);
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("pass"));
        userAddCol.setCellValueFactory(new PropertyValueFactory<>("UserAdd"));
        refreshAccList();

        accountTable.getSelectionModel().selectedItemProperty().addListener((observable, oldval, newval) -> {
            if (newval != null){
                editUsernameTextField.setText(newval.getUser());
                editUserPassTextField.setText(newval.getPass());
                roleEditChoiceBox.setValue(newval.getRole());
                selectedAcc = newval;
                
                roleEditChoiceBox.setDisable(false); 
                removeAccountButton.setDisable(false); //Enables buttons when user presses on rows
            } else {
                editUsernameTextField.setText("");
                editUserPassTextField.setText("");
                roleEditChoiceBox.setValue(null);
                roleEditChoiceBox.setDisable(true);
            }
        });
    }
    
    public void refreshAccList() throws SQLException{ //used in viewAccount and removeAccountOK
        ObservableList<accTable> acclist = FXCollections.observableArrayList();
        acclist.clear();
        accountTable.getItems().clear();
        ResultSet r = connectDB.createStatement().executeQuery("SELECT * FROM UserAccounts");
        while (r.next()){
            accTable a = new accTable(r.getString("Role"),r.getString("Username"),r.getString("Password"),r.getString("UserAdd"));
            acclist.add(a);
        }
        accountTable.setItems(acclist);
    }
    @FXML
    public void confirmAccountEdit(ActionEvent e){
        String user = editUsernameTextField.getText();
        String pass = editUserPassTextField.getText();
        String newrole = roleEditChoiceBox.getValue().toString();
        
        try {
            executeUpdate("UPDATE UserAccounts SET Username = '"+user+"', Password = '"+pass+"', Role = '"+newrole+"' WHERE Username = '"+selectedAcc.getUser()+"'");
            refreshAccList();
            editUsernameTextField.setText("");
            editUsernameTextField.setText("");
            selectedAcc = null;
            
        } catch (SQLException se){
            removeAccountErrorLabel.setVisible(true);
            removeAccountErrorLabel.setText("Something went wrong. Check connection.");
        }
    }
    @FXML
    public void removeAccountOK(ActionEvent e){
        String removeUsername = editUsernameTextField.getText();
        try {
            executeUpdate("DELETE FROM UserAccounts WHERE Username = '"+removeUsername+"'");
            refreshAccList();
            roleEditChoiceBox.setDisable(true); 
            removeAccountButton.setDisable(true);
            editUsernameTextField.setText("");
            editUsernameTextField.setText("");
        } catch (SQLException se){
            removeAccountErrorLabel.setVisible(true);
            removeAccountErrorLabel.setText("Something went wrong. Check connection.");
        }
    }
    @FXML
    public void removeAccountBack(ActionEvent e){
        userViewPane.setVisible(false);
        blockPane.setVisible(false);
    }

    @FXML
    public void transferOwnership(ActionEvent e) throws InterruptedException{
        errorLabel.setVisible(true);
        errorLabel.setText("Not available yet!");
        TimerTask task = new TimerTask(){
            @Override public void run(){
                errorLabel.setVisible(false);
            }
        };
        timer.schedule(task, 5000);
    }
    
    @FXML
    public void switchToFoodScene(ActionEvent e) throws IOException{
        App.setRoot("dataanalysispage");
    }
    
    @FXML
    public void logoutOnAction(ActionEvent e) throws Exception {
        Stage stage = (Stage)logoutButton.getScene().getWindow(); //lets the stage be defined
        timer.cancel();
        connectDB.close();
        stage.setHeight(400);
        stage.setWidth(600);
        stage.centerOnScreen();
        App.setRoot("login"); //switches to login.fxml
    }
} 