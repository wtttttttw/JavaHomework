@echo off
echo Компиляция и запуск проекта JavaFX Multithreading
echo.

echo 1. Задание 1: Два потока по очереди
javac -cp "target/classes" src/main/java/com/example/TwoThreadsSync.java
java -cp "target/classes;src/main/java" com.example.TwoThreadsSync
pause

echo 2. Задание 2a: Блокирующий GUI (запускать осторожно!)
echo ВНИМАНИЕ: Это окно зависнет и потребует принудительного закрытия!
pause
javac --module-path %PATH_TO_FX% --add-modules javafx.controls -d target/classes src/main/java/com/example/BlockingGUI.java
java --module-path %PATH_TO_FX% --add-modules javafx.controls -cp target/classes com.example.BlockingGUI
pause

echo 3. Задание 2b: Неблокирующий GUI - Runnable
javac --module-path %PATH_TO_FX% --add-modules javafx.controls -d target/classes src/main/java/com/example/NonBlockingGUIRunnable.java
java --module-path %PATH_TO_FX% --add-modules javafx.controls -cp target/classes com.example.NonBlockingGUIRunnable
pause

echo 4. Задание 2b: Неблокирующий GUI - Thread
javac --module-path %PATH_TO_FX% --add-modules javafx.controls -d target/classes src/main/java/com/example/NonBlockingGUIThread.java
java --module-path %PATH_TO_FX% --add-modules javafx.controls -cp target/classes com.example.NonBlockingGUIThread
pause

echo 5. Задание 3: ProgressBar с управлением потоками
javac --module-path %PATH_TO_FX% --add-modules javafx.controls -d target/classes src/main/java/com/example/ProgressBarWithThreads.java
java --module-path %PATH_TO_FX% --add-modules javafx.controls -cp target/classes com.example.ProgressBarWithThreads