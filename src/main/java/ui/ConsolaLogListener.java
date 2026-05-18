package ui;

import core.events.GameEventListener;

@SuppressWarnings("java:S106")
public class ConsolaLogListener implements GameEventListener {
    @Override
    public void onMessage(String mensaje) {
        System.out.println(mensaje);
    }

    @Override
    public void onMessageInline(String mensaje) {
        System.out.print(mensaje);
    }
}