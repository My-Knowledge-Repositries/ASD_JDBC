package com.developersstack.medex.controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NewAppointmentFormController {
    public AnchorPane newAppointmentContext;
    public JFXTextField txtAmount;
    public JFXTextArea txtMessage;
    public JFXComboBox cmbDoctor;
    public JFXDatePicker txtDate;
    public JFXTimePicker txtTime;

    public void initialize() {
        cmbDoctor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

        });
    }

    public void seeAvailabilityOnAction(ActionEvent actionEvent) {
    }

    public void makeTheAppointmentOnAction(ActionEvent actionEvent) {
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("PatientDashboardForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) newAppointmentContext.getScene().getWindow();
        System.out.println(stage);
        stage.setScene(new Scene(FXMLLoader.
                load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

}
