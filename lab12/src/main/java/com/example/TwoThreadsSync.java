package com.example;

public class TwoThreadsSync {
    private static final Object lock = new Object();
    private static boolean firstThreadTurn = true;
    
    public static void main(String[] args) {
        System.out.println("=== Задание 1: Два потока выводят имена по очереди ===");
        System.out.println("Для остановки нажмите Ctrl+C\n");
        
        Thread thread1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (lock) {
                    while (!firstThreadTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    
                    System.out.println("Поток1");
                    firstThreadTurn = false;
                    lock.notifyAll();
                    
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (lock) {
                    while (firstThreadTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    
                    System.out.println("Поток2");
                    firstThreadTurn = true;
                    lock.notifyAll();
                    
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        });

        thread1.start();
        thread2.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nЗавершение программы...");
            thread1.interrupt();
            thread2.interrupt();
        }));
    }
}