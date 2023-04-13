/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundryshopmanagementsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author WINDOWS 10
 */
public class moreInfoController implements Initializable {

    @FXML
    private AnchorPane moreInfo_form;

    @FXML
    private Label moreInfo_customerID;

    @FXML
    private TableView<getCustomer> moreInfo_tableView;

    @FXML
    private TableColumn<getCustomer, String> moreInfo_clothType;

    @FXML
    private TableColumn<getCustomer, String> moreInfo_service;

    @FXML
    private TableColumn<getCustomer, String> moreInfo_kilo;

    @FXML
    private TableColumn<getCustomer, String> moreInfo_price;

    @FXML
    private TableColumn<getCustomer, String> moreInfo_date;

    private Connection connect;
    private Statement statement;
    private ResultSet result;

    public ObservableList<getCustomer> customerList() {

        ObservableList<getCustomer> listData = FXCollections.observableArrayList();

        // SELECT THE CUSTOMER ID'S DATA
        String sql = "SELECT * FROM customer WHERE customer_id = " + data.cID;

        connect = database.connectDB();

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            getCustomer getC;

//            getCustomer(Integer id, Integer customerId, String clothType,
//            String service, Integer kilo, Double price, Date date)
            while (result.next()) {
                getC = new getCustomer(result.getInt("id"),
                         result.getInt("customer_id"),
                         result.getString("clothType"),
                         result.getString("service"),
                         result.getInt("kilo"),
                         result.getDouble("price"),
                         result.getDate("date"));
                
                listData.add(getC);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    
    private ObservableList<getCustomer> listC = FXCollections.observableArrayList();
    public void displayCustomer(){
        listC = customerList();
        
        moreInfo_clothType.setCellValueFactory(new PropertyValueFactory<>("clothType"));
        moreInfo_service.setCellValueFactory(new PropertyValueFactory<>("service"));
        moreInfo_kilo.setCellValueFactory(new PropertyValueFactory<>("kilo"));
        moreInfo_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        moreInfo_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        moreInfo_tableView.setItems(listC);
        
    }
    
    public void displayCustomerId(){
        moreInfo_customerID.setText("" + data.cID);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayCustomer();
        displayCustomerId();
        
    }

}
