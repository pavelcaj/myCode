package org.example;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalKeyboardMonitor implements NativeKeyListener {
    //переменная для тригера действия
    private static final String targetWord = "выключение";
    private StringBuilder currentWord = new StringBuilder();

    public static void main(String[] args) {
        // Настройки логирования
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.ALL);  // Уровень логирования

        // Отключение родительских обработчиков
        logger.setUseParentHandlers(false);
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("Не удалось зарегистрировать хук клавиатуры: " + e.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyboardMonitor());
    }
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        char c = Character.toLowerCase(e.getKeyChar());
        currentWord.append(c);

        if (currentWord.toString().endsWith(targetWord)) {
            try {
                // Выполняем команду для выключения компьютера
                Process process = Runtime.getRuntime().exec("shutdown /s /t 0");
                process.waitFor();  // Ждем завершения процесса
            } catch (Exception с) {
                с.printStackTrace();
            }
            // Очищаем текущее слово после обработки
            currentWord.setLength(0);
        }
    }
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
    }
}