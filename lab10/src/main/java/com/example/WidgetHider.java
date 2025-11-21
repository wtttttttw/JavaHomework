package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class WidgetHider extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Создаем виджеты
        Label label = new Label("Пример текста");
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        ProgressBar progressBar = new ProgressBar(0.7);
        progressBar.setPrefWidth(150);
        
        Slider slider = new Slider(0, 100, 50);
        slider.setPrefWidth(150);
        
        Rectangle rectangle = new Rectangle(80, 60, Color.LIGHTBLUE);
        rectangle.setStroke(Color.DARKBLUE);

        // Создаем чекбоксы
        CheckBox labelCheckBox = new CheckBox("Показать текст");
        CheckBox progressCheckBox = new CheckBox("Показать прогресс-бар");
        CheckBox sliderCheckBox = new CheckBox("Показать слайдер");
        CheckBox shapeCheckBox = new CheckBox("Показать фигуру");

        // Устанавливаем начальное состояние
        labelCheckBox.setSelected(true);
        progressCheckBox.setSelected(true);
        sliderCheckBox.setSelected(true);
        shapeCheckBox.setSelected(true);

        // Обработчики чекбоксов
        labelCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> 
            label.setVisible(newVal));
        
        progressCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> 
            progressBar.setVisible(newVal));
        
        sliderCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> 
            slider.setVisible(newVal));
        
        shapeCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> 
            rectangle.setVisible(newVal));

        // Размещаем элементы
        VBox widgetsBox = new VBox(15, label, progressBar, slider, rectangle);
        widgetsBox.setPadding(new Insets(20));
        widgetsBox.setPrefWidth(200);
        
        VBox checkboxesBox = new VBox(20, labelCheckBox, progressCheckBox, 
                                     sliderCheckBox, shapeCheckBox);
        checkboxesBox.setPadding(new Insets(20));
        
        HBox root = new HBox(20, widgetsBox, checkboxesBox);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Скрытие виджетов");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}