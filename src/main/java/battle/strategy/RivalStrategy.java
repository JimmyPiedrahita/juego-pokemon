package battle.strategy;

import battle.command.ComandoTurno;
import core.Entrenador;

public interface RivalStrategy {
    ComandoTurno decidirAccion(Entrenador rival, Entrenador jugador);
}
