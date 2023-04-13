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
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 *
 * @author WINDOWS 10
 */
public class serviceCardController implements Initializable {

    @FXML
    private StackPane card_form;

    @FXML
    private ImageView card_imageView;

    @FXML
    private Label card_serviceName;

    @FXML
    private Label card_price;

    @FXML
    private TextField card_kilo;

    @FXML
    private Button card_addBtn;

    @FXML
    private Label card_clothType;

    private getServices getS;

    private Image image;

    private Alert alert;

//    DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private String serviceName;
    private String clothType;
    private double price;
    private String path;
    private int id;
    private int kilo;

    public void setData(getServices getS) {
        id = getS.getId();
        serviceName = getS.getService();
        price = getS.getPricePerKilo();
        String uri = "file:" + getS.getImage();
        clothType = getS.getClothType();
        path = getS.getImage();
        card_clothType.setText(clothType);

        card_serviceName.setText(serviceName);
        card_price.setText("$" + price);

        image = new Image(uri, 225, 102, false, true);
        card_imageView.setImage(image);

    }

    private double priceService;

    public void addBtn() {
        mainFormController mainF = new mainFormController();
        mainF.orderCustomerID();

        if (card_kilo.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please indicate the kilo(s) of your cloths");
            alert.showAndWait();
        } else {
// I FORGOT TO EDIT THIS BECAUSE WE ADDED TO OUR TABLE THE IMAGE COLUMN
            String insertData = "INSERT INTO customer "
                    + "(customer_id, clothType, service, kilo, price, image, date, status)"
                    + "VALUES(?,?,?,?,?,?,?,?)"; // 8 OF "?"

            connect = database.connectDB();

            try {

                String getPrice = "SELECT price FROM service WHERE clothType = '"
                        + clothType + "' AND service = '"
                        + serviceName + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(getPrice);

                double priceS = 0;

                if (result.next()) {
                    priceS = result.getFloat("price");
                }

                priceService = (priceS * Integer.parseInt(card_kilo.getText()));

                prepare = connect.prepareStatement(insertData);
                prepare.setString(1, String.valueOf(data.cID));
                prepare.setString(2, clothType);
                prepare.setString(3, serviceName);
                prepare.setString(4, card_kilo.getText());
                prepare.setString(5, String.valueOf(priceService));
                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                
                prepare.setString(6, path);
                
                prepare.setString(7, String.valueOf(sqlDate));
                prepare.setString(8, "Active");

                prepare.executeUpdate();
                
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
