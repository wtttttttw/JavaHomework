package com.example.calculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CalculatorGUI extends Application {
    private TextField expressionField;
    private TextArea resultArea;
    private Button calculateButton;
    private SocketClient socketClient;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculator (Client-server)");
        
        // Подключение к серверу
        socketClient = new SocketClient("localhost", 12345);
        
        // Создание элементов UI
        Label titleLabel = new Label("Calculator");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        expressionField = new TextField();
        expressionField.setPromptText("Input expression (example: 10 + 5)");
        
        calculateButton = new Button("Solve");
        calculateButton.setStyle("-fx-font-size: 14px;");
        
        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefHeight(100);
        
        Label historyLabel = new Label("Solves history:");
        
        // Обработка нажатия кнопки
        calculateButton.setOnAction(e -> calculate());
        
        // Обработка нажатия Enter в поле ввода
        expressionField.setOnAction(e -> calculate());
        
        // Создание макета
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
            titleLabel,
            expressionField,
            calculateButton,
            new Separator(),
            historyLabel,
            resultArea
        );
        
        Scene scene = new Scene(layout, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Обработка закрытия окна
        primaryStage.setOnCloseRequest(e -> {
            socketClient.sendMessage("exit");
            socketClient.disconnect();
        });
    }
    
    private void calculate() {
        String expression = expressionField.getText().trim();
        if (expression.isEmpty()) return;
        
        String result = socketClient.sendMessage(expression);
        if (result != null) {
            String history = resultArea.getText();
            resultArea.setText(expression + " = " + result + "\n" + history);
        } else {
            resultArea.setText("Connection to server failed\n" + resultArea.getText());
        }
        
        expressionField.clear();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    // Вспомогательный класс для работы с сокетами
    private static class SocketClient {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        
        public SocketClient(String serverAddress, int port) {
            try {
                socket = new Socket(serverAddress, port);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                System.err.println("Cannot connect to the server");
            }
        }
        
        public String sendMessage(String message) {
            try {
                out.println(message);
                return in.readLine();
            } catch (IOException e) {
                return null;
            }
        }
        
        public void disconnect() {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (socket != null) socket.close();
            } catch (IOException e) {}
        }
    }
}