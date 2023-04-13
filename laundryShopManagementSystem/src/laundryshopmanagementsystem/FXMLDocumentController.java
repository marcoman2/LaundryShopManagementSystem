/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundryshopmanagementsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author WINDOWS 10
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login_btn;
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    private Alert alert;
    
    // NOW, LETS CREATE OUR MAIN FORM 
    
    public void loginAccount(){
        
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username/Password");
            alert.showAndWait();
        }else{
            
            String sql = "SELECT * FROM employee WHERE username = '" + username.getText() 
                    + "' AND password = '" + password.getText() + "'";
            
            connect = database.connectDB();
            
            try{
                prepare = connect.prepareStatement(sql);
                result = prepare.executeQuery();
                
                if(result.next()){
                    // IF CORRECT USERNAME AND PASSWORD
                    
                    data.username = username.getText();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    // LINK YOUR MAIN FORM
                    Parent root = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    stage.setMinWidth(1100);
                    stage.setMinHeight(600);
                    
                    stage.setTitle("Laundry Shop Management System");
                    
                    stage.setScene(scene);
                    stage.show();
                    
                    // TO HIDE YOUR LOGIN FORM
                    login_btn.getScene().getWindow().hide();
                    
                }else{
                    // IF INCORRECT USERNAME OR PASSWORD
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                }
                
            }catch(Exception e){e.printStackTrace();}
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
