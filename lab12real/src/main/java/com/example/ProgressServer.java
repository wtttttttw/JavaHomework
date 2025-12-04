package com.example.progress;

import java.io.*;
import java.net.*;

public class ProgressServer {
    private ServerSocket serverSocket;
    private boolean running = true;
    private Thread workerThread;
    private volatile boolean workerRunning = false;
    private volatile boolean workerPaused = false;
    private final Object pauseLock = new Object();
    private PrintWriter clientOut;
    
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("ProgressBar server is running on port " + port);
            
            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection: " + clientSocket.getInetAddress());
                
                // Обработка одного клиента
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
    
    private void handleClient(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            this.clientOut = out;
            out.println("CONNECTED");
            
            String command;
            while ((command = in.readLine()) != null) {
                System.out.println("Command received: " + command);
                
                switch (command) {
                    case "START":
                        startWorker();
                        break;
                    case "PAUSE":
                        pauseWorker();
                        break;
                    case "RESUME":
                        resumeWorker();
                        break;
                    case "STOP":
                        stopWorker();
                        break;
                    case "EXIT":
                        stopWorker();
                        return;
                    default:
                        out.println("ERROR: Unknown command");
                }
            }
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {}
            clientOut = null;
        }
    }
    
    private void startWorker() {
        if (workerThread != null && workerThread.isAlive()) {
            stopWorker();
        }
        
        workerRunning = true;
        workerPaused = false;
        
        workerThread = new Thread(() -> {
            try {
                for (int i = 0; i <= 1000 && workerRunning; i++) {
                    synchronized (pauseLock) {
                        while (workerPaused && workerRunning) {
                            pauseLock.wait();
                        }
                    }
                    
                    if (!workerRunning) break;
                    
                    Thread.sleep(20);
                    
                    // Отправляем прогресс клиенту
                    if (clientOut != null) {
                        clientOut.println("PROGRESS:" + ((double)i / 1000));
                    }
                }
                
                if (clientOut != null) {
                    clientOut.println("COMPLETED");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        workerThread.start();
    }
    
    private void pauseWorker() {
        workerPaused = true;
        if (clientOut != null) {
            clientOut.println("PAUSED");
        }
    }
    
    private void resumeWorker() {
        synchronized (pauseLock) {
            workerPaused = false;
            pauseLock.notifyAll();
        }
        if (clientOut != null) {
            clientOut.println("RESUMED");
        }
    }
    
    private void stopWorker() {
        workerRunning = false;
        synchronized (pauseLock) {
            workerPaused = false;
            pauseLock.notifyAll();
        }
        
        if (workerThread != null) {
            workerThread.interrupt();
        }
        
        if (clientOut != null) {
            clientOut.println("STOPPED");
        }
    }
    
    public void stop() {
        running = false;
        stopWorker();
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {}
    }
    
    public static void main(String[] args) {
        ProgressServer server = new ProgressServer();
        server.start(12346);
    }
}