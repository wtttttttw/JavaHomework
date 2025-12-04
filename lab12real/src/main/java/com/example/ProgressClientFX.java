package com.example.progress;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class ProgressClientFX extends Application {
    private ProgressBar progressBar;
    private Button startButton, pauseButton, stopButton;
    private TextArea logArea;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Thread serverListener;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Client ProgressBar");
        
        // Подключение к серверу
        connectToServer();
        
        // Создание элементов UI
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(400);
        
        startButton = new Button("Start");
        pauseButton = new Button("Pause");
        stopButton = new Button("Stop");
        
        startButton.setPrefWidth(100);
        pauseButton.setPrefWidth(100);
        stopButton.setPrefWidth(100);
        
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(150);
        
        // Обработчики кнопок
        startButton.setOnAction(e -> sendCommand("START"));
        pauseButton.setOnAction(e -> {
            if (pauseButton.getText().equals("Pause")) {
                sendCommand("PAUSE");
                pauseButton.setText("Continue");
            } else {
                sendCommand("RESUME");
                pauseButton.setText("Pause");
            }
        });
        stopButton.setOnAction(e -> sendCommand("STOP"));
        
        updateButtons(true, true, false);
        
        // Создание макета
        HBox buttonBox = new HBox(10, startButton, pauseButton, stopButton);
        
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
            new Label("Realization progress:"),
            progressBar,
            buttonBox,
            new Separator(),
            new Label("Log:"),
            logArea
        );
        
        Scene scene = new Scene(layout, 450, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Обработка закрытия окна
        primaryStage.setOnCloseRequest(e -> {
            sendCommand("EXIT");
            disconnect();
        });
    }
    
    private void connectToServer() {
        try {
            socket = new Socket("localhost", 12346);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Запускаем поток для прослушивания сервера
            serverListener = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Server: " + message);
                        processServerMessage(message);
                    }
                } catch (IOException e) {
                    Platform.runLater(() -> 
                        log("Connection to the server failed: " + e.getMessage())
                    );
                }
            });
            serverListener.setDaemon(true);
            serverListener.start();
            
            log("Connected to the server");
            
        } catch (IOException e) {
            log("Cannot connect to the server: " + e.getMessage());
        }
    }
    
    private void processServerMessage(String message) {
        Platform.runLater(() -> {
            if (message.startsWith("PROGRESS:")) {
                try {
                    double progress = Double.parseDouble(message.substring(9));
                    progressBar.setProgress(progress);
                } catch (NumberFormatException e) {
                    log("Wrong proccess format: " + message);
                }
            } else if (message.equals("COMPLETED")) {
                log("Task completed!");
                updateButtons(true, true, false);
                pauseButton.setText("Pause");
            } else if (message.equals("PAUSED")) {
                log("Execution suspended");
            } else if (message.equals("RESUMED")) {
                log("Execution has resumed");
            } else if (message.equals("STOPPED")) {
                log("Execution stopped");
                progressBar.setProgress(0);
                updateButtons(true, true, false);
                pauseButton.setText("Pause");
            } else if (message.equals("CONNECTED")) {
                log("Connection to the server established");
            } else {
                log("Server: " + message);
            }
        });
    }
    
    private void sendCommand(String command) {
        if (out != null) {
            out.println(command);
            
            if (command.equals("START")) {
                updateButtons(false, false, true);
                pauseButton.setText("Pause");
            } else if (command.equals("STOP")) {
                updateButtons(true, true, false);
                pauseButton.setText("Pause");
            }
        } else {
            log("No connection to the server");
        }
    }
    
    private void updateButtons(boolean startEnabled, boolean pauseEnabled, boolean stopEnabled) {
        startButton.setDisable(!startEnabled);
        pauseButton.setDisable(!pauseEnabled);
        stopButton.setDisable(!stopEnabled);
    }
    
    private void log(String message) {
        logArea.appendText(message + "\n");
    }
    
    private void disconnect() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {}
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}