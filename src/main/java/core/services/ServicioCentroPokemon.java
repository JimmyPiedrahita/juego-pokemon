package core.services;

import core.Entrenador;
import core.Pokemon;

public class ServicioCentroPokemon {
    private static final int COSTO_CURACION = 50;

    public void curarEquipoCompleto(Entrenador entrenador) {
        if (entrenador.getEquipo().isEmpty()) {
            core.events.GameEventManager.getInstance().notifyMessage("\nSin Pkmn.");
            return;
        }
        
        core.events.GameEventManager.getInstance().notifyMessage("\n[CENTRO POKEMON] Curar: $" + COSTO_CURACION + " | Dinero: $" + entrenador.getDinero());
        
        if (entrenador.getDinero() < COSTO_CURACION) {
            core.events.GameEventManager.getInstance().notifyMessage("Sin dinero.");
            return;
        }

        entrenador.gastarDinero(COSTO_CURACION);
        core.events.GameEventManager.getInstance().notifyMessage("Curando...");
        for(Pokemon p : entrenador.getEquipo()) {
            p.revivir(100); 
            p.curar(999);
        }
        core.events.GameEventManager.getInstance().notifyMessage("Pkmn sanos.");
    }
}

