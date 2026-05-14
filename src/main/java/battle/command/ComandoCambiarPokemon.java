package battle.command;

import core.Entrenador;
import core.Pokemon;

public class ComandoCambiarPokemon implements ComandoTurno {
    private Entrenador entrenador;
    private Pokemon nuevoPokemon;

    public ComandoCambiarPokemon(Entrenador entrenador, Pokemon nuevoPokemon) {
        this.entrenador = entrenador;
        this.nuevoPokemon = nuevoPokemon;
    }

    @Override
    public int getPrioridad() {
        return 999;
    }

    @Override
    public void ejecutar() {
        Pokemon actual = entrenador.getPokemonActivo();
        if (actual != null && !actual.isDebilitado()) {
            core.events.GameEventManager.getInstance().notifyMessage("Vuelve " + actual.getNombre());
        }
        core.events.GameEventManager.getInstance().notifyMessage("Ve " + nuevoPokemon.getNombre());
        entrenador.setPokemonActivo(nuevoPokemon);
    }
}
