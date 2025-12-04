package com.example.matchgame;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class MatchGameServer {
    private ServerSocket serverSocket;
    private ExecutorService pool;
    private GameRoom currentGame;
    
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            pool = Executors.newFixedThreadPool(4);
            System.out.println("Game server is running on port " + port);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection: " + clientSocket.getInetAddress());
                
                ClientHandler handler = new ClientHandler(clientSocket);
                pool.execute(handler);
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
    
    private synchronized GameRoom getOrCreateGame() {
        if (currentGame == null || currentGame.isFull()) {
            currentGame = new GameRoom();
            System.out.println("New playroom was created");
        }
        return currentGame;
    }
    
    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private GameRoom.Player player;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                
                // Добавляем игрока в игру
                GameRoom game = getOrCreateGame();
                player = game.addPlayer(out);
                
                out.println("WELCOME:You are a player " + player.getNumber());
                out.println("INFO:Waiting for other player...");
                
                // Основной игровой цикл
                String input;
                while ((input = in.readLine()) != null) {
                    if (input.startsWith("MOVE:")) {
                        try {
                            int matches = Integer.parseInt(input.substring(5));
                            game.makeMove(player, matches);
                        } catch (NumberFormatException e) {
                            out.println("ERROR:Incorrect amount of matches");
                        }
                    } else if (input.equals("STATUS")) {
                        out.println("STATUS:" + game.getStatus());
                    }
                }
                
            } catch (IOException e) {
                System.out.println("Client error: " + e.getMessage());
            } finally {
                // Удаляем игрока при отключении
                if (player != null && currentGame != null) {
                    currentGame.removePlayer(player);
                    if (currentGame.isEmpty()) {
                        currentGame = null;
                    }
                }
                try { clientSocket.close(); } catch (IOException e) {}
            }
        }
    }
    
    public static void main(String[] args) {
        MatchGameServer server = new MatchGameServer();
        server.start(12347);
    }
}

class GameRoom {
    private static final int TOTAL_MATCHES = 37;
    private static final int MAX_TAKE = 5;
    
    private int matchesLeft = TOTAL_MATCHES;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameOver = false;
    
    public synchronized Player addPlayer(PrintWriter out) {
        Player player = new Player(out);
        
        if (player1 == null) {
            player1 = player;
            player.setNumber(1);
            broadcast("INFO:Player 1 is connected");
        } else if (player2 == null) {
            player2 = player;
            player.setNumber(2);
            broadcast("INFO:Player 2 is connected");
            startGame();
        }
        
        return player;
    }
    
    public synchronized void removePlayer(Player player) {
        if (player1 == player) {
            player1 = null;
            broadcast("INFO:Player 1 is disconnected");
            gameOver = true;
        } else if (player2 == player) {
            player2 = null;
            broadcast("INFO:Player 1 is disconnected");
            gameOver = true;
        }
    }
    
    private void startGame() {
        currentPlayer = player1;
        broadcast("GAME_START");
        broadcast("TURN:Player 1 is starting the game");
        broadcast("MATCHES:" + matchesLeft);
        broadcast("PLAYER_TURN:1");
    }
    
    public synchronized void makeMove(Player player, int matches) {
        if (gameOver) {
            player.send("ERROR: Game over");
            return;
        }
        
        if (player != currentPlayer) {
            player.send("ERROR: Not your turn");
            return;
        }
        
        if (matches < 1 || matches > MAX_TAKE) {
            player.send("ERROR:You can get from 1 to " + MAX_TAKE + " matches");
            return;
        }
        
        if (matches > matchesLeft) {
            player.send("ERROR:Cannot take more matches than left");
            return;
        }
        
        // Выполняем ход
        matchesLeft -= matches;
        broadcast("MOVE:Player " + player.getNumber() + " take " + matches + " matches");
        broadcast("MATCHES:" + matchesLeft);
        
        // Проверка конца игры
        if (matchesLeft == 0) {
            gameOver = true;
            broadcast("WINNER:Player " + player.getNumber() + " won!");
            return;
        }
        
        // Переход хода
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
        broadcast("TURN:It's player " + currentPlayer.getNumber() + " turn.");
        broadcast("PLAYER_TURN:" + currentPlayer.getNumber());
    }
    
    private void broadcast(String message) {
        if (player1 != null) player1.send(message);
        if (player2 != null) player2.send(message);
    }
    
    public String getStatus() {
        return "Matches left: " + matchesLeft + 
               ", Current turn: player " + (currentPlayer != null ? currentPlayer.getNumber() : "waiting");
    }
    
    public boolean isFull() {
        return player1 != null && player2 != null;
    }
    
    public boolean isEmpty() {
        return player1 == null && player2 == null;
    }
    
    class Player {
        private PrintWriter out;
        private int number;
        
        public Player(PrintWriter out) {
            this.out = out;
        }
        
        public void send(String message) {
            out.println(message);
        }
        
        public int getNumber() {
            return number;
        }
        
        public void setNumber(int number) {
            this.number = number;
        }
    }
}