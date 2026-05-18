package battle.strategy;

import battle.command.ComandoTurno;
import battle.command.ComandoAtacar;
import battle.command.ComandoCambiarPokemon;
import core.Entrenador;
import core.Movimiento;
import core.Pokemon;
import java.security.SecureRandom;
import java.util.List;

public class RandomRivalStrategy implements RivalStrategy {
    private SecureRandom rand;

    public RandomRivalStrategy() {
        this.rand = new SecureRandom();
    }

    @Override
    public ComandoTurno decidirAccion(Entrenador rival, Entrenador jugador) {
        Pokemon activoRival = rival.getPokemonActivo();
        Movimiento movRival = null;
        List<Movimiento> movsRival = activoRival.getMovimientos();
        
        if (movsRival != null && !movsRival.isEmpty()) {
            movRival = movsRival.get(rand.nextInt(movsRival.size()));
        }
        
        ComandoTurno cmdRival = new ComandoAtacar(rival, jugador, movRival); 
        
        if (rand.nextInt(10) < 2) { // 20% probabilidad de cambiar
            Pokemon posibleCambio = null;
            for (Pokemon p : rival.getEquipo()) {
                if (!p.isDebilitado() && p != activoRival) {
                    posibleCambio = p;
                    break;
                }
            }
            if (posibleCambio != null) {
                cmdRival = new ComandoCambiarPokemon(rival, posibleCambio);
            }
        }
        return cmdRival;
    }
}
