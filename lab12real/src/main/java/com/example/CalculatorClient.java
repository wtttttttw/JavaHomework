package com.example.calculator;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CalculatorClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;
    
    public void connect(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            scanner = new Scanner(System.in);
            
            System.out.println("Connected to the calculator server");
            System.out.println("Input your expression in format: number operator number");
            System.out.println("Operator: + - * /");
            System.out.println("Example: 10.5 + 3.2");
            System.out.println("Input 'exit' to leave\n");
            
            String userInput;
            while (true) {
                System.out.print("> ");
                userInput = scanner.nextLine();
                
                if (userInput.equalsIgnoreCase("exit")) {
                    out.println("exit");
                    break;
                }
                
                out.println(userInput);
                String response = in.readLine();
                System.out.println("Result: " + response);
            }
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        } finally {
            disconnect();
        }
    }
    
    private void disconnect() {
        try {
            if (scanner != null) scanner.close();
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {}
    }
    
    public static void main(String[] args) {
        CalculatorClient client = new CalculatorClient();
        client.connect("localhost", 12345);
    }
}