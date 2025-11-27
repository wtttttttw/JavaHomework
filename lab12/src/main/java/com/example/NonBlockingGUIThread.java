package com.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NonBlockingGUIThread extends Application {
    private WorkerThread workerThread;
    
    class WorkerThread extends Thread {
        private volatile boolean running = true;
        private int count = 0;
        
        @Override
        public void run() {
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
        }
        
        public void stopWorker() {
            running = false;
            this.interrupt();
        }
    }
    
    private Label statusLabel;
    private Button startButton, stopButton;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Задание 2b: Неблокирующий GUI - Thread");
        
        startButton = new Button("Запустить поток (Thread)");
        stopButton = new Button("Остановить");
        statusLabel = new Label("Статус: Остановлен");
        
        startButton.setOnAction(e -> {
            if (workerThread == null || !workerThread.isAlive()) {
                startButton.setDisable(true);
                stopButton.setDisable(false);
                workerThread = new WorkerThread();
                workerThread.setDaemon(true);
                workerThread.start();
            }
        });
        
        stopButton.setOnAction(e -> {
            if (workerThread != null) {
                workerThread.stopWorker();
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