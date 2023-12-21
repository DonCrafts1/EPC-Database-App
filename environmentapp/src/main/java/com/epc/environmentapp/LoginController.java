package com.epc.environmentapp;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.cj.exceptions.CJCommunicationsException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.concurrent.Task;
//import java.awt.AWTException;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Notifications;

public class LoginController {

    public static DatabaseConnection connectNow; //created object of class DatabaseConnection
    public static Connection connectDB;
     
    @FXML
    private void initialize() throws IOException{

        connectNow = new DatabaseConnection();
        
        loginMessageLabel.setVisible(true);
        loginButton.setDisable(true);

        new Thread(() -> {
            connectToDB();
        }).start();
    }
    
    public void connectToDB(){
        Platform.runLater(() -> {
            loginMessageLabel.setText("Connecting...");
        });
        try {
            connectDB = connectNow.getConnection();
            Thread.sleep(1250);
            while (connectDB == null){
                connectDB = connectNow.getConnection();
                Thread.sleep(1250);
            }
        } catch (InterruptedException err){}
        Platform.runLater(() -> {
            loginMessageLabel.setText("");
            loginButton.setDisable(false);
        });
        
    }
    
    
    @FXML
    private Button cancelButton;
    @FXML
    private Button proceedButton;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    
    public static String username;
    public static String role;
    
    
    public void validateLogin(){
        loginMessageLabel.setText("Loading... ");
        loginButton.setDisable(true);

        String verifyLogin = "SELECT Role FROM UserAccounts WHERE Username = BINARY'"+usernameTextField.getText()+"' AND Password = BINARY'"+passwordField.getText()+"'";
        //the query to mySQL
        if (usernameTextField.getText().length() > 40){
            loginMessageLabel.setText("Username is too long!");
        } else {
            try{
                if (connectDB.isValid(3)){
                    try (Statement statement = connectDB.createStatement()) //statements allow to issue SQL queries to the database
                    {
                        ResultSet rs = statement.executeQuery(verifyLogin); //executes the query (verifyLogin string)
                        //resultset also gets the result of the sql query
                        if (rs.next()){
                            role = rs.getString("Role");
                            loginMessageLabel.setText("Welcome, "+usernameTextField.getText());
                            username = usernameTextField.getText();
                            proceedButton.setVisible(true);
                        } else {
                            loginMessageLabel.setText("Invalid login. Please try again!");
                            proceedButton.setVisible(false);
                        }
                    } //executes the query (verifyLogin string)
                } else {
                    loginMessageLabel.setText("Make sure you are connected to a network!");
                    connectDB.close();
                    connectDB = connectNow.getConnection();
                }
            } catch (SQLException | CJCommunicationsException | NullPointerException e) {
                loginMessageLabel.setText("Make sure you are connected to a network!");
                new Thread(() -> {
                    connectToDB();
                }).start();
                
            }
        }
        loginButton.setDisable(false);
    }
    // else if (usernameTextField.getText().length() == 0){
    //        loginMessageLabel.setText("Enter username!");
    //    } 
    
    public void loginButtonOnAction(ActionEvent e) throws SQLException{
        validateLogin();
    }
    
    public void proceedButtonOnAction(ActionEvent e) throws IOException{
        Notifications.create()
            .title("EPC DATABASE SYSTEM")
            .text("Welcome, "+username+"!")
            .position(Pos.TOP_RIGHT)
            .graphic(new ImageView(new Image("EPClogo.png", 64, 64, false, false))) //boolean -> aspect ratio preserved boolean
            .show();
        Stage stage = (Stage)passwordField.getScene().getWindow(); //This is for letting the stage be defined
        loginMessageLabel.setText("Loading... ");
        App.setRoot("recyclepage");
        stage.setHeight(675);
        stage.setWidth(1000); //stage exists because of line after validateLogin()
        stage.centerOnScreen();
    }
    
    
    
    public void cancelButtonOnAction(ActionEvent e) throws Exception{ //exit button action
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        if (connectDB != null)
            connectDB.close();
        stage.close();
        System.exit(0);
    }
}
