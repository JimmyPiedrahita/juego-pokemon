package ui;

import core.events.GameEventListener;

public class ConsolaLogListener implements GameEventListener {
    @Override
    public void onMessage(String mensaje) {
        System.out.println(mensaje);
    }
}