package com.developersstack.medex.controller;

import com.developersstack.medex.dto.User;
import com.developersstack.medex.enums.AccountType;
import com.developersstack.medex.util.CrudUtil;
import com.developersstack.medex.util.IdGenerator;
import com.developersstack.medex.util.PasswordConfig;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpFormController {
    public AnchorPane signUpContext;
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;
    public JFXRadioButton rBtnDoctor;

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        User user = new User(txtFirstName.getText(),
                txtLastName.getText(),
                txtEmail.getText().trim().toLowerCase(),
                new PasswordConfig().encrypt(txtPassword.getText()),
                rBtnDoctor.isSelected() ? AccountType.DOCTOR : AccountType.PATIENT);

        try {
            String id = new IdGenerator().generateId("SELECT user_id FROM user ORDER BY user_id DESC 1","U");
            boolean isSaved = CrudUtil.execute(
                    "INSERT INTO user VALUES (?,?,?,?,?,?)",id
                    ,user.getFirstName(),user.getLastName(),user.getEmail(),
                    user.getPassword(),user.getAccountType().name()
            );
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                setUi();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void alreadyHaveAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi();
    }

    private void setUi() throws IOException {
        Stage stage = (Stage) signUpContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));

    }
}
