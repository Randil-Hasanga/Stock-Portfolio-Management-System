package com.stockportfoliomanagementsystem.HRManager;

import com.stockportfoliomanagementsystem.MainController;
import com.stockportfoliomanagementsystem.MySqlCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HRManagerController implements Initializable {

    private Connection conn = MySqlCon.MysqlMethod();
    MainController mc = new MainController();
    private String username = mc.getUsername();

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private PieChart pieChart;
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView imageView;

    @FXML
    void onReportsButton(MouseEvent event) {
        //Complete this
    }
    @FXML
    void onManageCustomers(MouseEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/HRManager/ManageCustomers.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setHeight(700);
            stage.setWidth(1210);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {

        }
    }

    @FXML
    void onManageSuppliers(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/HRManager/ManageSuppliers.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setHeight(700);
        stage.setWidth(1210);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    @FXML
    void onStockButton(MouseEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/stockportfoliomanagementsystem/HRManager/viewStock.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setHeight(700);
            stage.setWidth(1210);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
        }
    }
    private void showPicture(){
        String sql = "SELECT Pic FROM Users WHERE Username = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                InputStream is = rs.getBinaryStream("Pic");

                if(is!=null) {
                    // Read the image data and save it to a file
                    OutputStream os = new FileOutputStream(new File("photo.jpg"));
                    byte[] content = new byte[1024];
                    int size = 0;

                    while ((size = is.read(content)) != -1) {
                        os.write(content, 0, size);
                    }
                    os.close();
                    is.close();

                    // Create a circular mask for the ImageView
                    Circle clip = new Circle(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2, imageView.getFitWidth() / 2);
                    imageView.setClip(clip);

                    // Load the image and set it to the ImageView
                    imageView.setImage(new Image("file:photo.jpg"));
                    imageView.setPreserveRatio(true);

                    // Set the dimensions of the ImageView
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);

                    // Set a border and make the image circular
                    imageView.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-color: white;");
                }else{
                    System.out.println("No image");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException f) {
            throw new RuntimeException(f);
        } catch (IOException g) {
            throw new RuntimeException(g);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPicture();
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Pens", 1200),
                        new PieChart.Data("Pencils", 1500),
                        new PieChart.Data("CR pg80 Single", 600),
                        new PieChart.Data("CR pg 80 Square", 400),
                        new PieChart.Data("CR pg 120 Single", 1000),
                        new PieChart.Data("CR pg 120 Square", 350),
                        new PieChart.Data("Pastel Large", 400)
                );

        pieChartData.forEach(data ->
                data.nameProperty().setValue(data.getName() + "\namount: " + data.getPieValue())
        );


        pieChart.getData().addAll(pieChartData);
        //pieChart.setLegendVisible(true);

        //Inventory
        ObservableList<LineChart.Data> lineChartData =
                FXCollections.observableArrayList(
                        new LineChart.Data("Jan", 35000),
                        new LineChart.Data("Feb", 22500),
                        new LineChart.Data("Mar", 40000),
                        new LineChart.Data("Apr", 44000),
                        new LineChart.Data("May", 11000),
                        new LineChart.Data("Jun", 38500),
                        new LineChart.Data("Jul", 40000),
                        new LineChart.Data("Aug", 12000),
                        new LineChart.Data("Sep", 15000),
                        new LineChart.Data("Oct", 29000),
                        new LineChart.Data("Nov", 41000),
                        new LineChart.Data("Dec", 26000)
                );

        //Sales
        ObservableList<LineChart.Data> lineChartData1 =
                FXCollections.observableArrayList(
                        new LineChart.Data("Jan", 45000),
                        new LineChart.Data("Feb", 30000),
                        new LineChart.Data("Mar", 35000),
                        new LineChart.Data("Apr", 45000),
                        new LineChart.Data("May", 10000),
                        new LineChart.Data("Jun", 35000),
                        new LineChart.Data("Jul", 48000),
                        new LineChart.Data("Aug", 12500),
                        new LineChart.Data("Sep", 20000),
                        new LineChart.Data("Oct", 25000),
                        new LineChart.Data("Nov", 45000),
                        new LineChart.Data("Dec", 35000)
                );

        //Profit of Loss
        ObservableList<LineChart.Data> lineChartData2 =
                FXCollections.observableArrayList(
                        new LineChart.Data("Jan", 10000),
                        new LineChart.Data("Feb", 7500),
                        new LineChart.Data("Mar", -5000),
                        new LineChart.Data("Apr", 1000),
                        new LineChart.Data("May", -1000),
                        new LineChart.Data("Jun", -3500),
                        new LineChart.Data("Jul", 8000),
                        new LineChart.Data("Aug", 500),
                        new LineChart.Data("Sep", 5000),
                        new LineChart.Data("Oct", -4000),
                        new LineChart.Data("Nov", 4000),
                        new LineChart.Data("Dec", 9000)
                );

        lineChart.getData().addAll(new LineChart.Series("Inventory at\nBeginning of the month", lineChartData));
        lineChart.getData().addAll(new LineChart.Series("Sales at \nEnd of the month", lineChartData1));
        lineChart.getData().addAll(new LineChart.Series("Profit or Loss", lineChartData2));
    }
}
