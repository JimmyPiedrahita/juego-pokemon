package data;

import java.util.List;
// Necesitarías crear una clase 'PokemonDataDTO' que agrupe especie, tipo, stats, etc.

public class PokemonContenedorJson {
    // El nombre de esta lista debe coincidir con el primer nivel del JSON ("pokemon")
    private List<PokemonDataDTO> pokemon; 

    public List<PokemonDataDTO> getPokemon() {
        return pokemon;
    }
}