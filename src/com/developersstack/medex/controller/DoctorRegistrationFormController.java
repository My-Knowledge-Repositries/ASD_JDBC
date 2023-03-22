package com.developersstack.medex.controller;

import com.developersstack.medex.dto.User;
import com.developersstack.medex.enums.GenderType;
import com.developersstack.medex.util.Cookie;
import com.developersstack.medex.util.CrudUtil;
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

public class DoctorRegistrationFormController {
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public JFXTextField txtNic;
    public JFXTextField txtEmail;
    public JFXTextField txtContact;
    public JFXTextField txtSpecializations;
    public JFXRadioButton rBtnMale;
    public JFXTextArea txtAddress;
    public AnchorPane doctorRegistrationContext;

    public void initialize() {
        loadUserData();
    }

    private void loadUserData() {
        User selectedUser = Cookie.selectedUser;
        txtFirstName.setText(selectedUser.getFirstName());
        txtLastName.setText(selectedUser.getLastName());
        txtEmail.setText(selectedUser.getEmail());
    }

    private String generateDoctorId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT doctor_id FROM doctor ORDER BY doctor_id DESC LIMIT 1");
        if (rst.next()) {
            String selectedId = rst.getString(1); //D-1**
            String[] splitData = selectedId.split("-");//string tokenizer, String format
            String splitId = splitData[1];
            int id = Integer.parseInt(splitId); //unboxing
            id++;
            return "D-" + id;
        }
        return "D-1";
    }


    public void submitDataOnAction(ActionEvent actionEvent) {
        try {
            String docId = generateDoctorId();
            boolean isSaved = CrudUtil.execute("INSERT INTO doctor VALUES(?,?,?,?,?,?,?,?)",
                    docId,
                    txtFirstName.getText(), txtLastName.getText(),
                    txtContact.getText(), txtEmail.getText(),
                    txtSpecializations.getText(),
                    txtAddress.getText(),
                    rBtnMale.isSelected() ? GenderType.MALE.name() : GenderType.FE_MALE.name()
            );
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Welcome Doctor...").show();
                setUi("DoctorDashboardForm");
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) doctorRegistrationContext.getScene().getWindow();
        System.out.println(stage);
        stage.setScene(new Scene(FXMLLoader.
                load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
