package ui;

import core.Entrenador;

public class InterfazConsola {
    private JuegoFacade facade;

    public InterfazConsola() {
        this.facade = new JuegoFacade();
    }

    public void iniciarJuego(Entrenador jugador) {
        this.facade.iniciarJuego(jugador);
    }
}