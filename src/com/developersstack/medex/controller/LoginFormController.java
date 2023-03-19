package com.developersstack.medex.controller;

import com.developersstack.medex.db.Database;
import com.developersstack.medex.db.DbConnection;
import com.developersstack.medex.dto.User;
import com.developersstack.medex.enums.AccountType;
import com.developersstack.medex.util.Cookie;
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
import java.sql.*;

public class LoginFormController {
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;
    public JFXRadioButton rBtnDoctor;
    public AnchorPane loginContext;

    public void signInOnAction(ActionEvent actionEvent) throws IOException {
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        AccountType accountType = rBtnDoctor.isSelected() ? AccountType.DOCTOR : AccountType.PATIENT;

        try{
            String sql = "SELECT * FROM user WHERE email=? AND account_type=?";
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1, email);
            pstm.setString(2, accountType.name());
            ResultSet rst = pstm.executeQuery();
            if (rst.next()){
                if(new PasswordConfig().decrypt(password, rst.getString("password"))){
                    if (accountType.equals(AccountType.DOCTOR)){
                        setUi("DoctorDashboardForm");
                    }else{
                        setUi("PatientDashboardForm");
                    }
                }
            }else {
                new Alert(Alert.AlertType.WARNING, String.format("we can't find an email (%s)", email)).show();
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {
//        Stage stage = (Stage) loginContext.getScene().getWindow();
//        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/SignUpForm.fxml"))));
        setUi("SignUpForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage =(Stage) loginContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.
                load(getClass().getResource("../view/"+location+".fxml"))));
    }
}
