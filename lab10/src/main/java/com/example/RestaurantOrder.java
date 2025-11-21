package com.example;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RestaurantOrder extends Application {
    
    public static class Dish {
        private final SimpleStringProperty name;
        private final SimpleDoubleProperty price;
        private final SimpleIntegerProperty quantity;
        
        public Dish(String name, double price, int quantity) {
            this.name = new SimpleStringProperty(name);
            this.price = new SimpleDoubleProperty(price);
            this.quantity = new SimpleIntegerProperty(quantity);
        }
        
        // Геттеры
        public String getName() { return name.get(); }
        public double getPrice() { return price.get(); }
        public int getQuantity() { return quantity.get(); }
        public double getTotal() { return price.get() * quantity.get(); }
        
        // Сеттеры
        public void setQuantity(int quantity) { this.quantity.set(quantity); }
        
        // Property геттеры для TableView
        public SimpleStringProperty nameProperty() { return name; }
        public SimpleDoubleProperty priceProperty() { return price; }
        public SimpleIntegerProperty quantityProperty() { return quantity; }
    }

    private ObservableList<Dish> orderList = FXCollections.observableArrayList();
    private Label totalLabel;

    @Override
    public void start(Stage primaryStage) {
        // Создаем список блюд
        VBox menuBox = new VBox(10);
        menuBox.setPadding(new Insets(15));
        menuBox.setPrefWidth(250);
        
        Label menuLabel = new Label("Меню:");
        menuLabel.setFont(Font.font(16));
        
        // Блюда с чекбоксами и спиннерами
        CheckBox[] dishCheckboxes = new CheckBox[5];
        Spinner<Integer>[] dishSpinners = new Spinner[5];
        
        String[] dishNames = {"Салат кака", "Картошка по-инопланетянски", "Паста с батарейками", "Суп", "Вода"};
        double[] prices = {8.50, 15.00, 12.00, 6.50, 5.00};
        
        for (int i = 0; i < 5; i++) {
            HBox dishBox = new HBox(10);
            
            dishCheckboxes[i] = new CheckBox(dishNames[i] + " - $" + prices[i]);
            dishSpinners[i] = new Spinner<>(0, 10, 0);
            dishSpinners[i].setPrefWidth(60);
            dishSpinners[i].setDisable(true);
            
            final int index = i;
            dishCheckboxes[i].selectedProperty().addListener((obs, oldVal, newVal) -> {
                dishSpinners[index].setDisable(!newVal);
                if (newVal) {
                    dishSpinners[index].getValueFactory().setValue(1);
                    updateOrder(dishNames[index], prices[index], 1);
                } else {
                    removeFromOrder(dishNames[index]);
                }
            });
            
            dishSpinners[i].valueProperty().addListener((obs, oldVal, newVal) -> {
                if (dishCheckboxes[index].isSelected()) {
                    updateOrder(dishNames[index], prices[index], newVal);
                }
            });
            
            dishBox.getChildren().addAll(dishCheckboxes[i], dishSpinners[i]);
            menuBox.getChildren().add(dishBox);
        }

        // Создаем таблицу для чека
        TableView<Dish> orderTable = new TableView<>();
        orderTable.setItems(orderList);
        orderTable.setPrefHeight(200);
        
        TableColumn<Dish, String> nameCol = new TableColumn<>("Блюдо");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(150);
        
        TableColumn<Dish, Double> priceCol = new TableColumn<>("Цена");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(80);
        
        TableColumn<Dish, Integer> quantityCol = new TableColumn<>("Кол-во");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityCol.setPrefWidth(80);
        
        TableColumn<Dish, Double> totalCol = new TableColumn<>("Сумма");
        totalCol.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getTotal()).asObject());
        totalCol.setPrefWidth(80);
        
        orderTable.getColumns().addAll(nameCol, priceCol, quantityCol, totalCol);

        // Итоговая сумма
        totalLabel = new Label("Общая сумма: $0.00");
        totalLabel.setFont(Font.font(16));
        totalLabel.setStyle("-fx-font-weight: bold;");

        // Основной layout
        VBox receiptBox = new VBox(10);
        receiptBox.setPadding(new Insets(15));
        receiptBox.getChildren().addAll(new Label("Чек:"), orderTable, totalLabel);
        
        HBox root = new HBox(20, menuBox, receiptBox);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Ресторанный заказ");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateOrder(String name, double price, int quantity) {
        // Удаляем если уже есть
        removeFromOrder(name);
        
        // Добавляем новую запись
        if (quantity > 0) {
            orderList.add(new Dish(name, price, quantity));
        }
        
        calculateTotal();
    }

    private void removeFromOrder(String name) {
        orderList.removeIf(dish -> dish.getName().equals(name));
        calculateTotal();
    }

    private void calculateTotal() {
        double total = orderList.stream()
                .mapToDouble(Dish::getTotal)
                .sum();
        totalLabel.setText(String.format("Общая сумма: $%.2f", total));
    }

    public static void main(String[] args) {
        launch(args);
    }
}