package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WordSwitcher extends Application {
    private TextField leftField;
    private TextField rightField;
    private Button switchButton;
    private boolean isLeftToRight = true;

    @Override
    public void start(Stage primaryStage) {
        // Создаем поля ввода
        leftField = new TextField();
        leftField.setPromptText("Введите текст здесь");
        leftField.setPrefWidth(150);
        
        rightField = new TextField();
        rightField.setPromptText("Текст появится здесь");
        rightField.setPrefWidth(150);
        rightField.setEditable(false);

        // Создаем кнопку со стрелкой
        switchButton = new Button("→");
        switchButton.setFont(Font.font(20));
        switchButton.setPrefSize(60, 40);
        
        // Обработчик кнопки
        switchButton.setOnAction(e -> switchText());

        // Размещаем элементы
        HBox root = new HBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(leftField, switchButton, rightField);

        Scene scene = new Scene(root, 500, 100);
        primaryStage.setTitle("Перекидыватель слов");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void switchText() {
        if (isLeftToRight) {
            // Перекидываем слева направо
            rightField.setText(leftField.getText());
            leftField.clear();
            switchButton.setText("←");
        } else {
            // Перекидываем справа налево
            leftField.setText(rightField.getText());
            rightField.clear();
            switchButton.setText("→");
        }
        isLeftToRight = !isLeftToRight;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
