package core.services;

import core.Entrenador;
import core.Pokemon;

public class ServicioCentroPokemon {
    private static final int COSTO_CURACION = 50;

    public void curarEquipoCompleto(Entrenador entrenador) {
        if (entrenador.getEquipo().isEmpty()) {
            core.events.GameEventManager.getInstance().notifyMessage("\nNo tienes Pokemon en tu equipo para curar");
            return;
        }
        
        core.events.GameEventManager.getInstance().notifyMessage("\n[ CENTRO POKEMON ] Curar equipo: $" + COSTO_CURACION + " | Fondos: $" + entrenador.getDinero());
        
        if (entrenador.getDinero() < COSTO_CURACION) {
            core.events.GameEventManager.getInstance().notifyMessage("No tienes suficiente dinero.");
            return;
        }

        entrenador.gastarDinero(COSTO_CURACION);
        core.events.GameEventManager.getInstance().notifyMessage("Restaurando la salud de tus Pokemon");
        for(Pokemon p : entrenador.getEquipo()) {
            p.revivir(100); 
            p.curar(999);
        }
        core.events.GameEventManager.getInstance().notifyMessage("Tu equipo esta sano.");
    }
}

