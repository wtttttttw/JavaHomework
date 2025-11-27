package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BlockingGUI extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Задание 2a: Блокирующий GUI");
        
        Button blockingButton = new Button("Запустить бесконечный цикл (заблокирует GUI)");
        Label statusLabel = new Label("Статус: Ожидание");
        
        blockingButton.setOnAction(e -> {
            statusLabel.setText("Статус: Бесконечный цикл запущен - GUI ЗАВИС!");
            System.out.println("Начало бесконечного цикла... GUI должен зависнуть");
            
            // Бесконечный цикл, который заблокирует GUI
            int count = 0;
            while (true) {
                count++;
                if (count % 100000000 == 0) {
                    System.out.println("Итерация: " + count);
                    // GUI уже не отвечает, это сообщение может не появиться
                }
            }
        });
        
        VBox root = new VBox(10, blockingButton, statusLabel);
        Scene scene = new Scene(root, 400, 200);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}