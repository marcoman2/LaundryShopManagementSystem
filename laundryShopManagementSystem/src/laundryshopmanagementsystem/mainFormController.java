/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundryshopmanagementsystem;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author WINDOWS 10
 */
public class mainFormController implements Initializable {

    // LETS GIVE NAME ALL FIELDS WE HAVE
    @FXML
    private Label username;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button order_btn;

    @FXML
    private Button service_btn;

    @FXML
    private Button customers_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_customerN;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label dashboard_TotalIncome;

    @FXML
    private Label dashboard_TO;

    @FXML
    private AreaChart<?, ?> dashbaord_NC_chart;

    @FXML
    private BarChart<?, ?> dashbaord_IPD_chart;

    @FXML
    private LineChart<?, ?> dashbaord_NOO_chart;

    @FXML
    private AnchorPane order_form;

    @FXML
    private ScrollPane order_scrollPane;

    @FXML
    private GridPane order_gridPane;

    @FXML
    private TableView<getServices> order_tableView;

    @FXML
    private TableColumn<getServices, String> order_col_orderID;

    @FXML
    private TableColumn<getServices, String> order_col_type;

    @FXML
    private TableColumn<getServices, String> order_col_service;

    @FXML
    private TableColumn<getServices, String> order_col_qty;

    @FXML
    private TableColumn<getServices, String> order_col_price;

    @FXML
    private Label order_total;

    @FXML
    private TextField order_amount;

    @FXML
    private Button order_payBtn;

    @FXML
    private Button order_removeBtn;

    @FXML
    private Button order_receiptBtn;

    @FXML
    private DatePicker order_dateP;

    @FXML
    private AnchorPane service_form;

    @FXML
    private TableView<getServices> service_tableView;

    @FXML
    private TableColumn<getServices, String> service_col_id;

    @FXML
    private TableColumn<getServices, String> service_col_clothType;

    @FXML
    private TableColumn<getServices, String> service_col_service;

    @FXML
    private TableColumn<getServices, String> service_col_PPK;

    @FXML
    private TableColumn<getServices, String> service_col_DI;

    @FXML
    private TableColumn<getServices, String> service_col_DU;

    @FXML
    private TextField service_id;

    @FXML
    private ComboBox<?> service_clothType;

    @FXML
    private ComboBox<?> service_service;

    @FXML
    private TextField service_PPK;

    @FXML
    private ImageView service_imageView;

    @FXML
    private Button service_importBtn;

    @FXML
    private Button service_insertBtn;

    @FXML
    private Button service_updateBtn;

    @FXML
    private Button service_clearBtn;

    @FXML
    private Button service_deleteBtn;

    @FXML
    private AnchorPane customers_form;

    @FXML
    private ScrollPane customers_scrollPane;

    @FXML
    private GridPane customers_gridPane;

    @FXML
    private Button order_refreshBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label order_change;

    private Alert alert;

    private Image image;

//    DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private final ObservableList<getServices> listD = FXCollections.observableArrayList();

//    DASHBOARD FORM 
    public void displayNumberOfCustomer() {
        int noc = 0;

        String sql = "SELECT COUNT(id) FROM receipt";

        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                noc = result.getInt("COUNT(id)");
            }
            dashboard_customerN.setText("" + noc);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayIncomeToday() {

        float it = 0;

        // TO GET THE DATE TODAY
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM receipt WHERE date = '" + sqlDate + "'";

        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                it = result.getFloat("SUM(total)");
            }
            dashboard_TI.setText("$" + it);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayTotalIncome() {

        float ti = 0;

        String sql = "SELECT SUM(total) FROM receipt";

        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                ti = result.getFloat("SUM(total)");
            }
            dashboard_TotalIncome.setText("$" + ti);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayTotalOrders() {
        int to = 0;

        String sql = "SELECT COUNT(id) FROM customer";

        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                to = result.getInt("COUNT(id)");
            }
            dashboard_TO.setText("" + to);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayNOCChart() {

        dashbaord_NC_chart.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM receipt GROUP BY TIMESTAMP(date) ORDER BY date ASC LIMIT 10";

        connect = database.connectDB();

        XYChart.Series chart = new XYChart.Series<>();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
            }
            // TO GET THE DATA FROM THE DATABASE VIA XYCHART
            dashbaord_NC_chart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayNOOChart() {

        dashbaord_NOO_chart.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM customer GROUP BY TIMESTAMP(date) ORDER BY date ASC LIMIT 10";

        connect = database.connectDB();

        try {

            XYChart.Series chart = new XYChart.Series<>();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
            }

            dashbaord_NOO_chart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayIPDChart() {

        dashbaord_IPD_chart.getData().clear();

        String sql = "SELECT date, SUM(total) FROM receipt GROUP BY TIMESTAMP(date) ORDER BY date ASC LIMIT 10";

        connect = database.connectDB();

        try {
            XYChart.Series chart = new XYChart.Series<>();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getFloat(2)));
            }

            dashbaord_IPD_chart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    ORDER FORM BEHAVIORS
    public ObservableList<getServices> orderGetData() {

        ObservableList<getServices> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM service";

        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            getServices getS;
            while (result.next()) {
                getS = new getServices(result.getInt("id"),
                        result.getString("clothType"),
                        result.getString("service"),
                        result.getDouble("price"),
                        result.getString("image"));

                listData.add(getS);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void orderDisplayCard() {

        listD.clear();
        listD.addAll(orderGetData());

        int row = 0;
        int column = 0;

        try {

            order_gridPane.getColumnConstraints().clear();
            order_gridPane.getRowConstraints().clear();
            order_gridPane.getChildren().clear();

            for (int q = 0; q < listD.size(); q++) {

                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("serviceCard.fxml"));
                StackPane pane = load.load();

                serviceCardController serviceCC = load.getController();

                serviceCC.setData(listD.get(q));

                if (column == 3) {
                    column = 0;
                    row++;
                }

                order_gridPane.add(pane, column++, row);

                order_gridPane.setMinHeight(GridPane.USE_COMPUTED_SIZE);
                order_gridPane.setPrefHeight(GridPane.USE_COMPUTED_SIZE);
                order_gridPane.setMaxHeight(GridPane.USE_PREF_SIZE);

                order_gridPane.setMinWidth(GridPane.USE_COMPUTED_SIZE);
                order_gridPane.setPrefWidth(GridPane.USE_COMPUTED_SIZE);
                order_gridPane.setMaxWidth(GridPane.USE_PREF_SIZE);

                GridPane.setMargin(pane, new Insets(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ObservableList<getServices> orderOrderList() {
        orderCustomerID();
        ObservableList<getServices> listData = FXCollections.observableArrayList();
// ONLY MATCHED CUSTOMER ID AND THE STATUS IS ACTIVE WILL DISPLAY
        String sql = "SELECT * FROM customer WHERE customer_id = " + customerID + " AND status = 'Active'";
        connect = database.connectDB();
        getServices getS;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                getS = new getServices(result.getInt("id"),
                        result.getString("clothType"),
                        result.getString("service"),
                        result.getInt("kilo"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));

                listData.add(getS);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<getServices> orderListData;

    public void orderDisplayOrder() {
        orderListData = orderOrderList();

        order_col_orderID.setCellValueFactory(new PropertyValueFactory<>("id"));
        order_col_type.setCellValueFactory(new PropertyValueFactory<>("clothType"));
        order_col_service.setCellValueFactory(new PropertyValueFactory<>("service"));
        order_col_qty.setCellValueFactory(new PropertyValueFactory<>("kilo"));
        order_col_price.setCellValueFactory(new PropertyValueFactory<>("pricePerKilo"));

        order_tableView.setItems(orderListData);
    }

    private int orderID;
    private String orderClothType;
    private String orderService;
    private int orderKilo;
    private double orderPrice;
    private String orderImage;
    private String orderDate;

    public void orderSelectOrder() {
        getServices getS = order_tableView.getSelectionModel().getSelectedItem();
        int num = order_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        orderID = getS.getId();
        orderClothType = getS.getClothType();
        orderService = getS.getService();
        orderKilo = getS.getKilo();
        orderPrice = getS.getPricePerKilo();
        orderImage = getS.getImage();
        orderDate = String.valueOf(getS.getDate());

    }

//    DISPLAY TOTAL AMOUNT
    private float totalP;

    public void orderTotal() {
        orderCustomerID();
        String sql = "SELECT SUM(price) FROM customer WHERE customer_id = " + customerID + " AND status = 'Active'";

        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getFloat("SUM(price)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayTotal() {
        orderTotal();
        order_total.setText("$" + totalP);
    }

    private float orderAmount;
    private float orderChange;

    public void orderAmount() {
        orderTotal();

        if (order_amount.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Something wrong :3");
            alert.showAndWait();
        } else if (totalP > Float.parseFloat(order_amount.getText())) {
            order_amount.setText("");
        } else {
            orderAmount = Float.parseFloat(order_amount.getText());
            orderChange = (orderAmount - totalP);
            order_change.setText("$" + orderChange);
        }
    }

//    LETS CREATE OUR CUSTOMER ID
    private int customerID;

    public void orderCustomerID() {

        String sql = "SELECT MAX(customer_id) FROM customer";

        connect = database.connectDB();
        int cID = 0;
        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                customerID = result.getInt("MAX(customer_id)");
            }

            String checkID = "SELECT MAX(customer_id) FROM receipt";

            statement = connect.createStatement();
            result = statement.executeQuery(checkID);

            if (result.next()) {
                cID = result.getInt("MAX(customer_id)");
            }

            if (customerID == 0) {
                customerID += 1;
            } else if (cID == customerID) {
                customerID = cID + 1;
            }

            data.cID = customerID;

        } catch (Exception e) {
        };

    }

    public void orderRefreshBtn() {
        orderDisplayCard();
        orderDisplayOrder();
        displayTotal();
    }

    public void orderPayBtn() {
        // TO GET THE TOTAL VALUE
        orderTotal();
        // TO GET THE CUSTOMER ID
        orderCustomerID();

        if (totalP == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please order first");
            alert.showAndWait();
        } else if (order_dateP.getValue() == null) { // CHECK IF THE CALENDAR CHOOSER IS EMPTY
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the date of pick-up you want");
            alert.showAndWait();
        } else {

            // CHECKING IF THE CUSTOMER WANTS TO CONTINUE
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String getDate = "SELECT date FROM customer WHERE customer_id = " + customerID;

                connect = database.connectDB();
                Date getOrderdate;
                try {
                    statement = connect.createStatement();
                    result = statement.executeQuery(getDate);

                    if (result.next()) {
                        getOrderdate = result.getDate("date");

                        // CHECK IF THE CURRENT DATE IS GREATER THAN TO THE CALENDAR CHOOSER VALUE
                        if (getOrderdate.compareTo(java.sql.Date.valueOf(order_dateP.getValue())) < 0) {
                            String insertData = "INSERT INTO receipt (customer_id, total, date, pickup_date) "
                                    + "VALUES(?,?,?,?)"; // 4 OF ? needed
                            prepare = connect.prepareStatement(insertData);
                            prepare.setString(1, String.valueOf(customerID));
                            prepare.setString(2, String.valueOf(totalP));
                            prepare.setString(3, String.valueOf(getOrderdate));
                            prepare.setString(4, String.valueOf(order_dateP.getValue()));
                            prepare.executeUpdate();

                            // MESSAGE THAT INDICATES YOUR ORDER IS SUCCESSFULLLY TO BE ORDERED
                            alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Ordered Successfully!");
                            alert.showAndWait();

                        } else {
                            alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Invalid calendar");
                            alert.showAndWait();

                            order_dateP.setValue(null);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled order");
                alert.showAndWait();
            }

        }
    }

    public void orderRemoveBtn() {
        orderCustomerID();

        if (orderID == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Please select item first");
            alert.showAndWait();
        } else {

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE order ID " + orderID + "?");
            Optional<ButtonType> option = alert.showAndWait();

            System.out.println(orderDate);

            if (option.get().equals(ButtonType.OK)) {
                String updateData = "UPDATE customer SET customer_id = " + customerID + ", clothType = '"
                        + orderClothType + "', service = '" + orderService + "', kilo = "
                        + orderKilo + ", price = " + orderPrice + ", image = '"
                        + orderImage + "', date = '" + orderDate + "', status = 'Deleted' WHERE id = "
                        + orderID;

                connect = database.connectDB();

                try {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Order ID: " + orderID + " is removed Successfully!");
                    alert.showAndWait();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void orderReceiptBtn() {

        if (totalP == 0 && order_amount.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please order first");
            alert.showAndWait();
        } else {
            orderCustomerID();

            HashMap hash = new HashMap();
            hash.put("receiptValue", customerID - 1);

            try {

                JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\WINDOWS 10\\Documents\\NetBeansProjects\\laundryShopManagementSystem\\src\\laundryshopmanagementsystem\\report.jrxml");
                JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                JasperPrint jPrint = JasperFillManager.fillReport(jReport, hash, connect);

                JasperViewer.viewReport(jPrint, false);

                // TO CLEAR ALL FIELDS
                order_dateP.setValue(null);
                totalP = 0;
                order_total.setText("$0.0");
                order_amount.setText("");
                order_change.setText("$0.0");

                // TO UPDATE THE TABLEVIEW
                orderDisplayOrder();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    SERVICE FORM BEHAVIORS
    // NOW, LETS CREATE THE BEHAVIOR FOR INSERT BUTTON
    public void servicesInsertBtn() {

        if (service_id.getText().isEmpty()
                || service_clothType.getSelectionModel().getSelectedItem() == null
                || service_service.getSelectionModel().getSelectedItem() == null
                || service_PPK.getText().isEmpty() || data.path == null || data.path.equals("")) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("All fields are necessary to be filled");
            alert.showAndWait();
        } else {
            // CHECK IF THE SERVICE ID IS TAKEN
            String checkServiceID = "SELECT serviceID FROM service WHERE serviceID = '"
                    + service_id.getText() + "'";

            String insertData = "INSERT INTO service "
                    + "(serviceID, clothType, service, price, image, date_update, status) "
                    + "VALUES(?,?,?,?,?,?,?)";

            connect = database.connectDB();
            try {
                prepare = connect.prepareStatement(checkServiceID);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Service ID: " + service_id.getText() + " is already taken");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, service_id.getText());
                    prepare.setString(2, (String) service_clothType.getSelectionModel().getSelectedItem());
                    prepare.setString(3, (String) service_service.getSelectionModel().getSelectedItem());
                    prepare.setString(4, service_PPK.getText());

                    String path = data.path;
                    path = path.replace("\\", "\\\\");

                    prepare.setString(5, path);

//                Date date = new Date();
//                // TO GET THE DATE AND TIME TODAY
//                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(6, null);
                    prepare.setString(7, null);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    prepare.executeUpdate();

                    // TO UPDATE THE TABLEVIEW ONCE WE INSERT THE DATA
                    servicesShowData();
                    // TO CLEAR ALL FIELDS
                    servicesClearBtn();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void servicesUpdateBtn() {

        if (service_id.getText().isEmpty()
                || service_clothType.getSelectionModel().getSelectedItem() == null
                || service_service.getSelectionModel().getSelectedItem() == null
                || service_PPK.getText().isEmpty() || data.path == null || data.path.equals("")) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("All fields are necessary to be filled");
            alert.showAndWait();
        } else {

            String path = data.path;
            path = path.replace("\\", "\\\\");

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            String updateData = "UPDATE service SET serviceID = '"
                    + service_id.getText() + "', clothType = '"
                    + service_clothType.getSelectionModel().getSelectedItem() + "', service = '"
                    + service_service.getSelectionModel().getSelectedItem() + "', price = '"
                    + service_PPK.getText() + "', image = '"
                    + path + "', date_update = '"
                    + sqlDate + "', status = '" + null + "' WHERE id = " + id;

            connect = database.connectDB();

            try {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Service ID: " + service_id.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    // TO UPDATE THE TABLEVIEW ONCE WE INSERT THE DATA
                    servicesShowData();
                    // TO CLEAR ALL FIELDS
                    servicesClearBtn();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                } else {
                    alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled Update");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void servicesDeleteBtn() {

        if (id == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the item first");
            alert.showAndWait();
        } else {

            String deleteData = "DELETE FROM service WHERE id = " + id;

            connect = database.connectDB();

            try {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Service ID: " + service_id.getText());
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    // TO UPDATE THE TABLEVIEW ONCE WE INSERT THE DATA
                    servicesShowData();
                    // TO CLEAR ALL FIELDS
                    servicesClearBtn();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                } else {
                    alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void servicesClearBtn() {
        service_id.setText("");
        service_clothType.getSelectionModel().clearSelection();
        service_service.getSelectionModel().clearSelection();
        service_PPK.setText("");
        data.path = "";
        service_imageView.setImage(null);
    }

    // IMPORT IMAGE
    public void servicesImportBtn() {

        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            // GET THE IMAGE PATH
            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 103, 115, false, true);
            service_imageView.setImage(image);
        }

    }

    private String[] clothTypeList = {"Cloth", "Silk", "Cotton", "Linen", "Wool", "Worsted"};

    public void servicesClothTypeList() {
        List<String> listCT = new ArrayList<>();

        for (String data : clothTypeList) {
            listCT.add(data);
        }

        ObservableList listData = FXCollections.observableList(listCT);
        service_clothType.setItems(listData);
    }

    // SORRY, I FORGOT TO POPULATE THE LIST FOR OUR COMBOBOX
    private String[] servicesList = {"Washing", "Washing and Drying", "Washing, Drying and Ironing"};

    public void servicesServiceList() {

        List<String> listS = new ArrayList<>();

        for (String data : servicesList) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        service_service.setItems(listData);
    }

    public ObservableList<getServices> servicesDataList() {

        String sql = "SELECT * FROM service";

        ObservableList<getServices> listData = FXCollections.observableArrayList();

        try {
            connect = database.connectDB();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            getServices getS;

            while (result.next()) {
                // FOLLOW THE PARAMETER ON OUR CONSTRACTOR
                getS = new getServices(result.getInt("id"),
                        result.getString("serviceID"),
                        result.getString("clothType"),
                        result.getString("service"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date_insert"),
                        result.getDate("date_update"));

                listData.add(getS);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<getServices> servicesListData;

    // TO SHOW YOUR DATA IN OUR TABLEVIEW
    public void servicesShowData() {
        servicesListData = servicesDataList();

        service_col_id.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        service_col_clothType.setCellValueFactory(new PropertyValueFactory<>("clothType"));
        service_col_service.setCellValueFactory(new PropertyValueFactory<>("service"));
        service_col_PPK.setCellValueFactory(new PropertyValueFactory<>("pricePerKilo"));
        service_col_DI.setCellValueFactory(new PropertyValueFactory<>("date"));
        service_col_DU.setCellValueFactory(new PropertyValueFactory<>("uDate"));

        service_tableView.setItems(servicesListData);
    }

    private int id;

    // SELECT ITEM 
    public void servicesSelectData() {

        getServices getS = service_tableView.getSelectionModel().getSelectedItem();
        int num = service_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        id = getS.getId();

        service_id.setText(getS.getServiceID());
        service_PPK.setText(String.valueOf(getS.getPricePerKilo()));

        data.path = getS.getImage();

        String path = "File:" + data.path;

        image = new Image(path, 103, 115, false, true);
        service_imageView.setImage(image);

    }

//    CUSTOMERS FORM
    // TO GET THE DATA FROM THE RECEIPT TABLE
    public ObservableList<getCustomer> customerGetData() {
        orderCustomerID();

        String sql = "SELECT * FROM receipt";

        ObservableList<getCustomer> listData = FXCollections.observableArrayList();

        connect = database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            getCustomer getC;

            while (result.next()) {
                // SET THE MATCH DATA TYPE TO OUR ARGUMENTS
                getC = new getCustomer(result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getDate("date"));

                listData.add(getC);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<getCustomer> listC = FXCollections.observableArrayList();

    public void customerDisplay() {
        listC.clear();
        listC.addAll(customerGetData());
        int column = 0;
        int row = 0;
        try {

            customers_gridPane.getRowConstraints().clear();
            customers_gridPane.getColumnConstraints().clear();
            customers_gridPane.getChildren().clear();

            for (int q = 0; q < listC.size(); q++) {

                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("customerCard.fxml"));
                AnchorPane pane = load.load();

                customerCardController customerCC = load.getController();
                customerCC.setData(listC.get(q));

                if (column == 4) {
                    row++;
                    column = 0;
                }

                customers_gridPane.setMinHeight(GridPane.USE_COMPUTED_SIZE);
                customers_gridPane.setPrefHeight(GridPane.USE_COMPUTED_SIZE);
                customers_gridPane.setMaxHeight(GridPane.USE_PREF_SIZE);

                customers_gridPane.setMinWidth(GridPane.USE_COMPUTED_SIZE);
                customers_gridPane.setPrefWidth(GridPane.USE_COMPUTED_SIZE);
                customers_gridPane.setMaxWidth(GridPane.USE_PREF_SIZE);

                customers_gridPane.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(10));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {

        try {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                // LINK YOUR LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();

                // TO HIDE YOUR MAIN FORM
                logout_btn.getScene().getWindow().hide();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            order_form.setVisible(false);
            service_form.setVisible(false);
            customers_form.setVisible(false);

            // TO DISPLAY THE NUMBER OF CUSTOMERS
            displayNumberOfCustomer();
            // TO DISPLAY THE TODAY'S INCOME
            displayIncomeToday();
            // TO DISPLAY THE OVERALL INCOME
            displayTotalIncome();
            // TO DISPLAY TOTAL ORDERS
            displayTotalOrders();
            // TO DISPLAY THE CHART OF NUMBER OF CUSTOMERS
            displayNOCChart();
            // TO DISPLAY THE CHART OF NUMBER OF ORDERS
            displayNOOChart();
            // TO DISPLAY THE CHART OF INCOME PER DAY
            displayIPDChart();

        } else if (event.getSource() == order_btn) {
            dashboard_form.setVisible(false);
            order_form.setVisible(true);
            service_form.setVisible(false);
            customers_form.setVisible(false);

            orderDisplayCard();
            orderDisplayOrder();
            displayTotal();

        } else if (event.getSource() == service_btn) {
            dashboard_form.setVisible(false);
            order_form.setVisible(false);
            service_form.setVisible(true);
            customers_form.setVisible(false);

            // TO SHOW WHEN YOU CLICKED THE SERVICE BTN
            servicesShowData();
            servicesServiceList();
            servicesClothTypeList();

        } else if (event.getSource() == customers_btn) {
            dashboard_form.setVisible(false);
            order_form.setVisible(false);
            service_form.setVisible(false);
            customers_form.setVisible(true);

            customerDisplay();
        }

    }

    public void displayUsername() {

        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // DISPLAY USERNAME OF THE USER WHO LOGGED IN
        displayUsername();

        // TO DISPLAY THE NUMBER OF CUSTOMERS
        displayNumberOfCustomer();
        // TO DISPLAY THE TODAY'S INCOME
        displayIncomeToday();
        // TO DISPLAY THE OVERALL INCOME
        displayTotalIncome();
        // TO DISPLAY TOTAL ORDERS
        displayTotalOrders();
        // TO DISPLAY THE CHART OF NUMBER OF CUSTOMERS
        displayNOCChart();
        // TO DISPLAY THE CHART OF NUMBER OF ORDERS
        displayNOOChart();
        // TO DISPLAY THE CHART OF INCOME PER DAY
        displayIPDChart();

        // TO SHOW IMMEDIATELY THE DATA 
        servicesShowData();
        servicesServiceList();
        servicesClothTypeList();

        orderDisplayCard();
        orderDisplayOrder();
        displayTotal();

        customerDisplay();
    }

}


// THATS IT FOR THESE TUTORIALS, THANKS FOR WATCHING! 
// HIT THE LIKE BUTTON AND SUBSCRIBE FOR MORE JAVAFX TUTORIALS!
// THANKS !! : ) 
// BTW, WE HIT THE 1K SUBSCRIBERS 
// I JUST WANT TO SAY THAT THANK YOU GUYS FOR SUPPORT !!!
// SEE TO THE NEXT VIDEO : ) 
