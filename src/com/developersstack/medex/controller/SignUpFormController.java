package com.developersstack.medex.controller;

import com.developersstack.medex.db.Database;
import com.developersstack.medex.dto.User;
import com.developersstack.medex.enums.AccountType;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class SignUpFormController {
    public AnchorPane signUpContext;
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;
    public JFXRadioButton rBtnDoctor;

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        String email = txtEmail.getText().trim().toLowerCase();
        //first method
        /*for (UserDto dto:Database.userTable){
            if (dto.getEmail().equals(email.trim().toLowerCase())) {
                new Alert(Alert.AlertType.WARNING, "Email is already exists!").show();
                return;
            }
        }*/

        /*Second Method*/
        Optional<User> selectedUser = Database.userTable.stream().filter(e -> e.getEmail().equals(email)).findFirst();
        if (selectedUser.isPresent()) {
            new Alert(Alert.AlertType.WARNING, "Email is already exists!").show();
            return;
        }
//        Database.userTable.add(
//
//        );
        new Alert(Alert.AlertType.CONFIRMATION, "Welcome").show();
        setUi();
        // driver load =>
        User user = new User(txtFirstName.getText(), txtLastName.getText(), email, txtPassword.getText(), rBtnDoctor.isSelected() ? AccountType.DOCTOR : AccountType.PATIENT);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/medex",
                    "root",
                    "1234"
            );
            String sql = "INSERT INTO user VALUES (?,?,?,?,?,?)";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, 1001);
            pstm.setString(2, user.getFirstName());
            pstm.setString(3, user.getLastName());
            pstm.setString(4, user.getEmail());
            pstm.setString(5, user.getPassword());
            pstm.setString(6, user.getAccountType().name());

            int isSaved = pstm.executeUpdate();
            if (isSaved>0){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again").show();
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
