package me.arcanecici.irc.client.ui;

import lombok.SneakyThrows;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ArcaneCiCi ~ 10/5/2021
 */

public abstract class AbstractMenu {

    // Executes code for the menu right before you render the menu itself to the screen.
    abstract public void init();

    // Displays what should be shown on the screen, and any extra code for it.
    public void render() {
    }

    // Reloads the current screen after 500ms
    @SneakyThrows
    public void reload() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                init();
            }
        }, 500);
    }

    // Clears the screen by sending 50 empty messages, "cls" doesn't really work for some reason.
    public void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
