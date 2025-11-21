package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TextFlag extends Application {
    private ToggleGroup[] colorGroups;
    private String[] colorNames = {"Красный", "Синий", "Зеленый", "Желтый", "Белый"};
    private Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.WHITE};
    private Rectangle[] flagStripes;

    @Override
    public void start(Stage primaryStage) {
        colorGroups = new ToggleGroup[3];
        flagStripes = new Rectangle[3];
        
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Создаем группы RadioButton для каждой полосы
        VBox controlsBox = new VBox(15);
        for (int i = 0; i < 3; i++) {
            Label stripeLabel = new Label("Полоса " + (i + 1) + ":");
            colorGroups[i] = new ToggleGroup();
            
            HBox radioBox = new HBox(10);
            for (int j = 0; j < colorNames.length; j++) {
                RadioButton radio = new RadioButton(colorNames[j]);
                radio.setToggleGroup(colorGroups[i]);
                radio.setUserData(j);
                if (j == i) radio.setSelected(true); // Устанавливаем разные цвета по умолчанию
                radioBox.getChildren().add(radio);
            }
            
            controlsBox.getChildren().addAll(stripeLabel, radioBox);
        }

        // Создаем визуальное представление флага
        HBox flagBox = new HBox();
        flagBox.setAlignment(Pos.CENTER);
        flagBox.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        flagBox.setPrefSize(300, 100);
        
        for (int i = 0; i < 3; i++) {
            flagStripes[i] = new Rectangle(100, 100);
            // Устанавливаем начальные цвета
            int defaultColorIndex = i;
            flagStripes[i].setFill(colors[defaultColorIndex]);
            flagStripes[i].setStroke(Color.BLACK);
            flagBox.getChildren().add(flagStripes[i]);
        }

        // Кнопка для отрисовки
        Button drawButton = new Button("Нарисовать");
        drawButton.setOnAction(e -> drawFlag());

        // Поле для вывода результата
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        drawButton.setOnAction(e -> {
            drawFlag();
            resultLabel.setText(getColorDescription());
        });

        root.getChildren().addAll(controlsBox, flagBox, drawButton, resultLabel);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Текстовый флаг");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Запрещаем изменение размера окна
        primaryStage.show();
    }

    private void drawFlag() {
        for (int i = 0; i < 3; i++) {
            RadioButton selected = (RadioButton) colorGroups[i].getSelectedToggle();
            if (selected != null) {
                int colorIndex = (int) selected.getUserData();
                flagStripes[i].setFill(colors[colorIndex]);
            }
        }
    }

    private String getColorDescription() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            RadioButton selected = (RadioButton) colorGroups[i].getSelectedToggle();
            if (selected != null) {
                if (i > 0) sb.append(", ");
                sb.append(selected.getText());
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }
}