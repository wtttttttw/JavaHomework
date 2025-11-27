package com.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProgressBarWithThreads extends Application {
    private ProgressBar progressBar;
    private Button startButton, pauseButton, stopButton;
    private Worker worker;
    
    class Worker extends Thread {
        private static final int TOTAL_ITERATIONS = 1000;
        private volatile boolean running = true;
        private volatile boolean paused = false;
        private int currentIteration = 0;
        private final Object pauseLock = new Object();
        
        @Override
        public void run() {
            try {
                for (currentIteration = 0; currentIteration < TOTAL_ITERATIONS && running; currentIteration++) {
                    // Проверка паузы
                    synchronized (pauseLock) {
                        while (paused && running) {
                            pauseLock.wait();
                        }
                    }
                    
                    if (!running) break;
                    
                    // Имитация работы
                    Thread.sleep(20);
                    
                    // Обновление ProgressBar
                    final double progress = (double) currentIteration / TOTAL_ITERATIONS;
                    Platform.runLater(() -> progressBar.setProgress(progress));
                }
                
                // Завершение работы
                Platform.runLater(() -> {
                    if (running) {
                        progressBar.setProgress(1.0);
                        updateButtons(false, false, true);
                        pauseButton.setText("Пауза");
                    }
                });
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        public void pauseWorker() {
            paused = true;
        }
        
        public void resumeWorker() {
            synchronized (pauseLock) {
                paused = false;
                pauseLock.notifyAll();
            }
        }
        
        public void stopWorker() {
            running = false;
            resumeWorker();
            this.interrupt();
        }
        
        public boolean isPaused() {
            return paused;
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Задание 3: ProgressBar с управлением потоками");
        
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);
        
        startButton = new Button("Старт");
        pauseButton = new Button("Пауза");
        stopButton = new Button("Стоп");
        
        updateButtons(true, false, false);
        
        startButton.setOnAction(e -> startWorker());
        pauseButton.setOnAction(e -> togglePause());
        stopButton.setOnAction(e -> stopWorker());
        
        VBox root = new VBox(10, progressBar, startButton, pauseButton, stopButton);
        Scene scene = new Scene(root, 350, 150);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void startWorker() {
        if (worker != null && worker.isAlive()) {
            worker.stopWorker();
        }
        
        progressBar.setProgress(0);
        worker = new Worker();
        worker.setDaemon(true);
        worker.start();
        
        updateButtons(false, true, true);
        pauseButton.setText("Пауза");
    }
    
    private void togglePause() {
        if (worker != null && worker.isAlive()) {
            if (worker.isPaused()) {
                worker.resumeWorker();
                pauseButton.setText("Пауза");
            } else {
                worker.pauseWorker();
                pauseButton.setText("Продолжить");
            }
        }
    }
    
    private void stopWorker() {
        if (worker != null && worker.isAlive()) {
            worker.stopWorker();
        }
        
        progressBar.setProgress(0);
        updateButtons(true, false, false);
        pauseButton.setText("Пауза");
    }
    
    private void updateButtons(boolean startEnabled, boolean pauseEnabled, boolean stopEnabled) {
        startButton.setDisable(!startEnabled);
        pauseButton.setDisable(!pauseEnabled);
        stopButton.setDisable(!stopEnabled);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}