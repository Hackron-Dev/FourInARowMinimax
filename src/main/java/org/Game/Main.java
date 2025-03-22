package org.Game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static Thread currentGameThread;

    public static void main(String[] args) {


        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            // Принудительно закрываем все процессы chrome.exe
            try {
                Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
                System.out.println("Все процессы chrome.exe закрыты.");
            } catch (Exception e) {
                System.out.println("Ошибка при закрытии chrome.exe: " + e.getMessage());
            }

            // Прерываем старый поток, если он еще активен
            if (currentGameThread != null && currentGameThread.isAlive()) {
                currentGameThread.interrupt();
            }

            // Запускаем новый поток
            currentGameThread = new Thread(() -> {
                Game game = new Game();
                game.setPlayers();
                game.GameCycle();
            });
            currentGameThread.start();
        }, 0, 6, TimeUnit.MINUTES);
    }
}