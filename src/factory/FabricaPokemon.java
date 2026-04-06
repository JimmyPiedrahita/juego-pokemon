package factory;

import data.RepositorioDatos;
import core.Pokemon;

public abstract class FabricaPokemon {
    
    // Este repositorio es 'protected' para que las fábricas hijas 
    // (FabricaPokemonFuego, FabricaPokemonAgua, FabricaPokemonPlanta)
    // hereden esta variable y puedan usarla para leer el JSON.
    protected RepositorioDatos repositorio;

    // Constructor: Obliga a que al instanciar cualquier fábrica, 
    // se le deba pasar el repositorio (tu GestorDocumento) obligatoriamente.
    public FabricaPokemon(RepositorioDatos repo) {
        this.repositorio = repo;
    }

    // El método "contrato". Toda fábrica hija ESTÁ OBLIGADA a programar
    // cómo crear al Pokémon usando la especie y el nivel.
    public abstract Pokemon crearPokemon(String especieBase, int nivel);
}