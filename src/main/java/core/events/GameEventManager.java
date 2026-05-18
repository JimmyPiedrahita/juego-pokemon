package core.events;

import java.util.ArrayList;
import java.util.List;

public class GameEventManager {
    private static GameEventManager instance;
    private final List<GameEventListener> listeners;

    private GameEventManager() {
        this.listeners = new ArrayList<>();
    }

    public static GameEventManager getInstance() {
        if (instance == null) {
            instance = new GameEventManager();
        }
        return instance;
    }

    public void subscribe(GameEventListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(GameEventListener listener) {
        listeners.remove(listener);
    }

    public void notifyMessage(String mensaje) {
        for (GameEventListener listener : listeners) {
            listener.onMessage(mensaje);
        }
    }

    public void notifyMessageInline(String mensaje) {
        for (GameEventListener listener : listeners) {
            listener.onMessageInline(mensaje);
        }
    }
}