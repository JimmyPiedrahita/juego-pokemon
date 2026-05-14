package core.services;

import core.Pokemon;
import core.config.ConfiguracionJuego;
import core.events.GameEventManager;

public class ProgressionService {
    
    public void registrarVictoria(Pokemon pokemon) {
        if (!pokemon.isDebilitado()) {
            subirNivel(pokemon);
        }
    }

    private void subirNivel(Pokemon pokemon) {
        // Logica trasladada para evitar que la Entidad maneje reglas globales
        // Sin embargo, para esto necesitamos setters o metodos en Pokemon
        pokemon.setHpMaximo(pokemon.getHpMaximo() + ConfiguracionJuego.INCREMENTO_HP_NIVEL);
        pokemon.setHpActual(pokemon.getHpActual() + ConfiguracionJuego.INCREMENTO_HP_NIVEL);
        pokemon.setAtaque(pokemon.getAtaque() + ConfiguracionJuego.INCREMENTO_STATS_NIVEL);
        pokemon.setDefensa(pokemon.getDefensa() + ConfiguracionJuego.INCREMENTO_STATS_NIVEL);
        pokemon.setVelocidad(pokemon.getVelocidad() + ConfiguracionJuego.INCREMENTO_STATS_NIVEL);
        pokemon.setNivel(pokemon.getNivel() + 1);

        GameEventManager.getInstance().notifyMessage(pokemon.getNombre() + " ha subido al nivel " + pokemon.getNivel());

        if (pokemon.getNivel() == ConfiguracionJuego.NIVEL_EVOLUCION) {
            evolucionar(pokemon);
        }
    }

    private void evolucionar(Pokemon pokemon) {
        GameEventManager.getInstance().notifyMessage(pokemon.getNombre() + " esta evolucionando...");
        pokemon.setNombre("Gran " + pokemon.getNombre());

        pokemon.setHpMaximo(pokemon.getHpMaximo() + ConfiguracionJuego.INCREMENTO_HP_EVOLUCION);
        pokemon.setHpActual(pokemon.getHpActual() + ConfiguracionJuego.INCREMENTO_HP_EVOLUCION);
        pokemon.setAtaque(pokemon.getAtaque() + ConfiguracionJuego.INCREMENTO_STATS_EVOLUCION);
        pokemon.setDefensa(pokemon.getDefensa() + ConfiguracionJuego.INCREMENTO_STATS_EVOLUCION);
        pokemon.setVelocidad(pokemon.getVelocidad() + ConfiguracionJuego.INCREMENTO_STATS_EVOLUCION);

        GameEventManager.getInstance().notifyMessage("Evolucion completada! Ahora es " + pokemon.getNombre());
    }
}
