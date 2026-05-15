package core.events;

public interface GameEventListener {
    void onMessage(String mensaje);
    void onMessageInline(String mensaje);
}