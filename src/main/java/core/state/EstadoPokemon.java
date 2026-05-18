package core.state;

import core.Pokemon;

public interface EstadoPokemon {
    void recibirDano(Pokemon pokemon, int dano);
    void curar(Pokemon pokemon, int cantidad);
    void revivir(Pokemon pokemon, int porcentaje);
    boolean isDebilitado();
}