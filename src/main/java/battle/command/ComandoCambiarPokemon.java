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
            System.out.println("¡Regresa " + actual.getNombre() + "!");
        }
        System.out.println("¡Adelante " + nuevoPokemon.getNombre() + "! ");
        entrenador.setPokemonActivo(nuevoPokemon);
    }
}