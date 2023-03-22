package com.developersstack.medex.controller;

import com.developersstack.medex.dto.User;
import com.developersstack.medex.enums.GenderType;
import com.developersstack.medex.util.Cookie;
import com.developersstack.medex.util.CrudUtil;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientRegistrationFormController {

    public AnchorPane patientRegContext;
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public JFXTextField txtNIC;
    public JFXTextField txtEmail;
    public JFXRadioButton rBtnMale;
    public JFXTextArea txtAddress;
    public JFXDatePicker txtDob;
    public JFXTextField txtContact;

    public void initialize() {
        loadUserData();
    }

    private void loadUserData() {
        User selectedUser = Cookie.selectedUser;
        txtFirstName.setText(selectedUser.getFirstName());
        txtLastName.setText(selectedUser.getLastName());
        txtEmail.setText(selectedUser.getEmail());
    }


    private String generatePatientId() throws SQLException, ClassNotFoundException {
        ResultSet result =
                CrudUtil.execute("SELECT patient_id FROM patient ORDER BY" +
                        " patient_id DESC LIMIT 1");
        if (result.next()) {
            int lastId = Integer.parseInt(
                    result.getString("patient_id").split("-")[1]
            );
            lastId++;
            return "P-" + lastId; // String builder, String buffer
        }
        return new StringBuilder().append("P-1").toString();
    }

    public void submitDataOnAction(ActionEvent actionEvent) {
        try {
            String patientId = generatePatientId();
            boolean isSaved = CrudUtil.execute("INSERT INTO patient VALUES(?,?,?,?,?,?,?,?,?)",
                    patientId,
                    txtFirstName.getText(), txtLastName.getText(),
                    txtEmail.getText(), txtContact.getText(),
                    txtNIC.getText(), txtAddress.getText(),
                    txtDob.getValue(),
                    rBtnMale.isSelected() ? GenderType.MALE.name() : GenderType.FE_MALE.name()
            );
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Welcome Patient...").show();
                setUi("PatientDashboardForm");
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) patientRegContext.getScene().getWindow();
        System.out.println(stage);
        stage.setScene(new Scene(FXMLLoader.
                load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

}
