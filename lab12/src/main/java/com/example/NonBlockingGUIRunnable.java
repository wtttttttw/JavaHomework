package com.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NonBlockingGUIRunnable extends Application {
    private Thread workerThread;
    private volatile boolean running = false;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Задание 2b: Неблокирующий GUI - Runnable");
        
        Button startButton = new Button("Запустить поток (Runnable)");
        Button stopButton = new Button("Остановить");
        Label statusLabel = new Label("Статус: Остановлен");
        
        // Реализация через Runnable
        Runnable task = () -> {
            running = true;
            int count = 0;
            while (running && count < 50) {
                count++;
                final int currentCount = count;
                
                Platform.runLater(() -> 
                    statusLabel.setText("Статус: Выполняется. Итерация: " + currentCount + "/50")
                );
                
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    break;
                }
            }
            
            Platform.runLater(() -> {
                statusLabel.setText("Статус: Завершен");
                startButton.setDisable(false);
                stopButton.setDisable(true);
            });
            running = false;
        };
        
        startButton.setOnAction(e -> {
            if (workerThread == null || !workerThread.isAlive()) {
                startButton.setDisable(true);
                stopButton.setDisable(false);
                workerThread = new Thread(task);
                workerThread.setDaemon(true);
                workerThread.start();
            }
        });
        
        stopButton.setOnAction(e -> {
            running = false;
            if (workerThread != null) {
                workerThread.interrupt();
            }
            statusLabel.setText("Статус: Остановлен");
            startButton.setDisable(false);
            stopButton.setDisable(true);
        });
        
        stopButton.setDisable(true);
        
        VBox root = new VBox(10, startButton, stopButton, statusLabel);
        Scene scene = new Scene(root, 400, 200);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}