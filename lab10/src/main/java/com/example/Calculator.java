package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculator extends Application {
    private TextField display;
    private double firstNumber = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    @Override
    public void start(Stage primaryStage) {
        display = new TextField("0");
        display.setEditable(false);
        display.setStyle("-fx-font-size: 18px; -fx-alignment: center-right;");
        display.setPrefHeight(50);

        // Создаем кнопки
        String[][] buttonLabels = {
            {"7", "8", "9", "/"},
            {"4", "5", "6", "*"},
            {"1", "2", "3", "-"},
            {"0", ".", "=", "+"}
        };

        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(5);
        buttonGrid.setVgap(5);
        buttonGrid.setPadding(new Insets(10));

        // Создаем кнопки очистки
        HBox clearBox = new HBox(5);
        Button clearButton = new Button("C");
        Button clearEntryButton = new Button("CE");
        Button signButton = new Button("+/-");
        
        clearButton.setPrefSize(70, 50);
        clearEntryButton.setPrefSize(70, 50);
        signButton.setPrefSize(70, 50);
        
        clearButton.setOnAction(e -> clearAll());
        clearEntryButton.setOnAction(e -> clearEntry());
        signButton.setOnAction(e -> changeSign());
        
        clearBox.getChildren().addAll(clearButton, clearEntryButton, signButton);

        // Добавляем цифровые кнопки и операторы
        for (int i = 0; i < buttonLabels.length; i++) {
            for (int j = 0; j < buttonLabels[i].length; j++) {
                Button button = new Button(buttonLabels[i][j]);
                button.setPrefSize(70, 50);
                button.setStyle("-fx-font-size: 14px;");
                
                final String label = buttonLabels[i][j];
                button.setOnAction(e -> handleButtonClick(label));
                
                buttonGrid.add(button, j, i);
            }
        }

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(display, clearBox, buttonGrid);

        Scene scene = new Scene(root, 300, 350);
        primaryStage.setTitle("Калькулятор");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void handleButtonClick(String label) {
        if (isNumber(label)) {
            handleNumberInput(label);
        } else if (isOperator(label)) {
            handleOperatorInput(label);
        } else if (label.equals(".")) {
            handleDecimalPoint();
        } else if (label.equals("=")) {
            handleEquals();
        }
    }

    private boolean isNumber(String input) {
        return input.matches("[0-9]");
    }

    private boolean isOperator(String input) {
        return input.matches("[+\\-*/]");
    }

    private void handleNumberInput(String number) {
        if (startNewNumber) {
            display.setText(number);
            startNewNumber = false;
        } else {
            display.setText(display.getText() + number);
        }
    }

    private void handleOperatorInput(String op) {
        if (!operator.isEmpty()) {
            handleEquals();
        }
        firstNumber = Double.parseDouble(display.getText());
        operator = op;
        startNewNumber = true;
    }

    private void handleDecimalPoint() {
        if (startNewNumber) {
            display.setText("0.");
            startNewNumber = false;
        } else if (!display.getText().contains(".")) {
            display.setText(display.getText() + ".");
        }
    }

    private void handleEquals() {
        if (operator.isEmpty()) return;
        
        double secondNumber = Double.parseDouble(display.getText());
        double result = 0;
        
        try {
            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber == 0) {
                        display.setText("Ошибка: деление на 0");
                        operator = "";
                        startNewNumber = true;
                        return;
                    }
                    result = firstNumber / secondNumber;
                    break;
            }
            
            // Форматируем результат
            if (result == (long) result) {
                display.setText(String.format("%d", (long) result));
            } else {
                display.setText(String.format("%.6f", result).replaceAll("0*$", "").replaceAll("\\.$", ""));
            }
            
        } catch (Exception e) {
            display.setText("Ошибка");
        }
        
        operator = "";
        startNewNumber = true;
    }

    private void clearAll() {
        display.setText("0");
        firstNumber = 0;
        operator = "";
        startNewNumber = true;
    }

    private void clearEntry() {
        display.setText("0");
        startNewNumber = true;
    }

    private void changeSign() {
        double value = Double.parseDouble(display.getText());
        value = -value;
        
        if (value == (long) value) {
            display.setText(String.format("%d", (long) value));
        } else {
            display.setText(String.valueOf(value));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}