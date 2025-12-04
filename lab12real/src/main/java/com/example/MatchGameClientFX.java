package com.example.matchgame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class MatchGameClientFX extends Application {
    private TextArea gameLog;
    private Label matchesLabel;
    private Label statusLabel;
    private Label playerLabel;
    private Button take1, take2, take3, take4, take5;
    private Button[] takeButtons;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Thread serverListener;
    private int playerNumber = 0;
    private boolean myTurn = false;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GAME 'The last match'");
        
        // Подключение к серверу
        connectToServer();
        
        // Создание элементов UI
        playerLabel = new Label("Player: connecting...");
        playerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        matchesLabel = new Label("Matches: 37");
        matchesLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #8B4513;");
        
        statusLabel = new Label("Status: waiting for connection...");
        statusLabel.setStyle("-fx-font-size: 14px;");
        
        // Кнопки для взятия спичек
        take1 = createTakeButton(1);
        take2 = createTakeButton(2);
        take3 = createTakeButton(3);
        take4 = createTakeButton(4);
        take5 = createTakeButton(5);
        
        takeButtons = new Button[]{take1, take2, take3, take4, take5};
        
        HBox buttonBox = new HBox(10, take1, take2, take3, take4, take5);
        
        gameLog = new TextArea();
        gameLog.setEditable(false);
        gameLog.setPrefHeight(200);
        
        // Создание визуализации спичек
        VBox matchesBox = createMatchesVisualization(37);
        
        // Создание макета
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
            playerLabel,
            new Separator(),
            matchesLabel,
            matchesBox,
            new Separator(),
            statusLabel,
            buttonBox,
            new Separator(),
            new Label("Сourse of the game:"),
            gameLog
        );
        
        Scene scene = new Scene(layout, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Обработка закрытия окна
        primaryStage.setOnCloseRequest(e -> disconnect());
    }
    
    private Button createTakeButton(int matches) {
        Button button = new Button("Get " + matches);
        button.setPrefWidth(80);
        button.setDisable(true);
        
        button.setOnAction(e -> {
            if (myTurn) {
                sendMove(matches);
            }
        });
        
        return button;
    }
    
    private VBox createMatchesVisualization(int count) {
        VBox container = new VBox(5);
        container.setPadding(new Insets(10));
        container.setStyle("-fx-border-color: #8B4513; -fx-border-width: 2px; -fx-border-radius: 5px;");
        
        HBox currentRow = new HBox(2);
        for (int i = 0; i < Math.min(count, 37); i++) {
            if (i > 0 && i % 10 == 0) {
                container.getChildren().add(currentRow);
                currentRow = new HBox(2);
            }
            
            // Создаем визуализацию спички
            Pane match = new Pane();
            match.setPrefSize(8, 25);
            match.setStyle("-fx-background-color: linear-gradient(to bottom, #D2691E, #8B4513); " +
                          "-fx-border-color: #A0522D; -fx-border-width: 1px;");
            currentRow.getChildren().add(match);
        }
        if (!currentRow.getChildren().isEmpty()) {
            container.getChildren().add(currentRow);
        }
        
        return container;
    }
    
    private void connectToServer() {
        try {
            socket = new Socket("localhost", 12347);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            serverListener = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("Server: " + message);
                        processServerMessage(message);
                    }
                } catch (IOException e) {
                    Platform.runLater(() -> 
                        log("Connection with server error")
                    );
                }
            });
            serverListener.setDaemon(true);
            serverListener.start();
            
        } catch (IOException e) {
            log("Connection to the server failed: " + e.getMessage());
        }
    }
    
    private void processServerMessage(String message) {
        Platform.runLater(() -> {
            if (message.startsWith("WELCOME:")) {
                String[] parts = message.split(":");
                playerLabel.setText(parts[1]);
                if (parts[1].contains("1")) playerNumber = 1;
                else if (parts[1].contains("2")) playerNumber = 2;
                
            } else if (message.startsWith("INFO:")) {
                log(message.substring(5));
                
            } else if (message.startsWith("TURN:")) {
                statusLabel.setText(message.substring(5));
                statusLabel.setTextFill(Color.BLACK);
                
            } else if (message.startsWith("MATCHES:")) {
                int matches = Integer.parseInt(message.substring(8));
                matchesLabel.setText("Matches: " + matches);
                updateMatchesVisualization(matches);
                
            } else if (message.startsWith("PLAYER_TURN:")) {
                int turnPlayer = Integer.parseInt(message.substring(12));
                myTurn = (turnPlayer == playerNumber);
                updateTakeButtons(myTurn);
                
                if (myTurn) {
                    statusLabel.setText("YOUR TURN! Get from 1 to 5 matches");
                    statusLabel.setTextFill(Color.RED);
                }
                
            } else if (message.startsWith("MOVE:")) {
                log(message.substring(5));
                
            } else if (message.startsWith("WINNER:")) {
                String winnerMessage = message.substring(7);
                log("=== " + winnerMessage + " ===");
                statusLabel.setText(winnerMessage);
                
                if (winnerMessage.contains("" + playerNumber)) {
                    statusLabel.setTextFill(Color.GREEN);
                    showAlert("Congratulations!", "You won!", Alert.AlertType.INFORMATION);
                } else {
                    statusLabel.setTextFill(Color.RED);
                    showAlert("Game over", "You lost!", Alert.AlertType.INFORMATION);
                }
                
                disableAllButtons();
                
            } else if (message.startsWith("ERROR:")) {
                log("ERROR: " + message.substring(6));
                showAlert("Error", message.substring(6), Alert.AlertType.ERROR);
                
            } else if (message.equals("GAME_START")) {
                log("=== Game started! ===");
            }
        });
    }
    
    private void updateMatchesVisualization(int matches) {
        // Здесь можно добавить обновление визуализации спичек
        // Для простоты просто обновляем лейбл
    }
    
    private void updateTakeButtons(boolean enabled) {
        for (Button btn : takeButtons) {
            btn.setDisable(!enabled);
        }
    }
    
    private void disableAllButtons() {
        for (Button btn : takeButtons) {
            btn.setDisable(true);
        }
    }
    
    private void sendMove(int matches) {
        if (out != null) {
            out.println("MOVE:" + matches);
            log("You get " + matches + " matches");
            myTurn = false;
            updateTakeButtons(false);
        }
    }
    
    private void log(String message) {
        gameLog.appendText(message + "\n");
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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