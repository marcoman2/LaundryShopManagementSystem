/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundryshopmanagementsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author WINDOWS 10
 */
public class customerCardController implements Initializable{

    @FXML
    private AnchorPane card_form;

    @FXML
    private Label card_customerID;

    @FXML
    private Label card_datOrder;
    
    @FXML
    private Button card_moreInfo;
    
    private int id;
    
    public void setData(getCustomer customer){
        
        card_customerID.setText(String.valueOf(customer.getCustomerId()));
        card_datOrder.setText(String.valueOf(customer.getDate()));
        
        id = customer.getCustomerId();
        
    }
    
    public void moreInfoBtn(){
        
        try{
            // TO GET THE CUSTOMER ID OF THE CUSTOMER
            data.cID = id;
            
            Parent root = FXMLLoader.load(getClass().getResource("moreInfo.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(scene);
            stage.show();
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
