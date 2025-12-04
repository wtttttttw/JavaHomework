package com.example.calculator;

import java.io.*;
import java.net.*;

public class CalculatorServer {
    private ServerSocket serverSocket;
    private boolean running = true;
    
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Calculator server is running on port" + port);
            
            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection: " + clientSocket.getInetAddress());
                
                ClientHandler handler = new ClientHandler(clientSocket);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
    
    public void stop() {
        running = false;
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {}
    }
    
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equalsIgnoreCase("exit")) {
                        break;
                    }
                    
                    String result = processCalculation(inputLine);
                    out.println(result);
                }
            } catch (IOException e) {
                System.out.println("Client proccessing error: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {}
            }
        }
        
        private String processCalculation(String expression) {
            try {
                // Убираем пробелы и разделяем по оператору
                expression = expression.replaceAll("\\s+", "");
                
                if (expression.contains("+")) {
                    String[] parts = expression.split("\\+");
                    double a = Double.parseDouble(parts[0]);
                    double b = Double.parseDouble(parts[1]);
                    return String.valueOf(a + b);
                } else if (expression.contains("-")) {
                    // Проверка на отрицательное число
                    int index = expression.indexOf('-', 1);
                    if (index > 0) {
                        double a = Double.parseDouble(expression.substring(0, index));
                        double b = Double.parseDouble(expression.substring(index + 1));
                        return String.valueOf(a - b);
                    } else {
                        return "Error: wrong format";
                    }
                } else if (expression.contains("*")) {
                    String[] parts = expression.split("\\*");
                    double a = Double.parseDouble(parts[0]);
                    double b = Double.parseDouble(parts[1]);
                    return String.valueOf(a * b);
                } else if (expression.contains("/")) {
                    String[] parts = expression.split("/");
                    double a = Double.parseDouble(parts[0]);
                    double b = Double.parseDouble(parts[1]);
                    
                    if (b == 0) {
                        return "Error: division by zero";
                    }
                    return String.valueOf(a / b);
                } else {
                    return "Error: unknown operation";
                }
            } catch (Exception e) {
                return "Error: wrong expression format";
            }
        }
    }
    
    public static void main(String[] args) {
        CalculatorServer server = new CalculatorServer();
        server.start(12345);
    }
}