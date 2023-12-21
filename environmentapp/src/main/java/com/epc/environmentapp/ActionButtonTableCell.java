package com.epc.environmentapp;

import java.util.function.Function;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.util.Callback;
import javafx.scene.image.ImageView;

//inspired from https://stackoverflow.com/questions/29489366/how-to-add-button-in-javafx-table-view

public class ActionButtonTableCell<S> extends TableCell<S, Button> { 

    private final Button actionButton;
    private final ImageView view;
    
    public ActionButtonTableCell(String label, Function<S, S> function, int width, ImageView v) { //Constructor for ActionButtonTableCell but with custom image
        this.getStyleClass().add("action-button-table-cell"); //
        view = v; //Set ImageView to parameter ImageView (what button looks like)
        view.setFitWidth(width/5); 
        view.setPreserveRatio(true); //Resize ImageView to fit inside button
        this.actionButton = new Button(label, view); //Button instantiated with label (text) and image, assigned to actionButton
        this.actionButton.setOnAction((ActionEvent e) -> { //When button is pressed
            function.apply(getCurrentItem()); 
            //Function (in this case, method defined by GUI class that takes selected row as argument to do something/produce result)
        });
        this.actionButton.setPrefWidth(width); 
    }
    
    public ActionButtonTableCell(String label, Function<S, S> function, int width) { //Constructor for ActionButtonTableCell, with default magnifying glass icon
        this.getStyleClass().add("action-button-table-cell");
        view = new ImageView(new Image("magnifying_glass.png"));
        view.setFitWidth(width/5);
        view.setPreserveRatio(true);
        this.actionButton = new Button(label, view);
        this.actionButton.setOnAction((ActionEvent e) -> {
            function.apply(getCurrentItem());
        });
        this.actionButton.setPrefWidth(width); 
    }

    public S getCurrentItem() {
        return (S) getTableView().getItems().get(getIndex());
    }

    public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>> forTableColumn(String label, 
            Function<S, S> function, int width, ImageView view) {
        return param -> new ActionButtonTableCell<>(label, function, width, view);
    } //
    
    public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>> forTableColumn(String label, Function<S, S> function, int width) {
        return param -> new ActionButtonTableCell<>(label, function, width);
    }

    @Override
    public void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) setGraphic(null); //If TableView row is empty, no button is visible
        else setGraphic(actionButton); //Set graphic to actionButton (button containing text and image) if row exists
    }
}



