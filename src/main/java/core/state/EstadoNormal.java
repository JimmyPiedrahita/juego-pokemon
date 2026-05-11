package core.state;

import core.Pokemon;

public class EstadoNormal implements EstadoPokemon {

    @Override
    public void recibirDano(Pokemon pokemon, int dano) {
        int nuevoHp = pokemon.getHpActual() - dano;
        if (nuevoHp <= 0) {
            pokemon.setHpActual(0);
            pokemon.setEstado(new EstadoDebilitado());
            System.out.println(pokemon.getNombre() + " se ha debilitado.");
        } else {
            pokemon.setHpActual(nuevoHp);
        }
    }

    @Override
    public void curar(Pokemon pokemon, int cantidad) {
        int nuevoHp = pokemon.getHpActual() + cantidad;
        if (nuevoHp > pokemon.getHpMaximo()) {
            nuevoHp = pokemon.getHpMaximo();
        }
        pokemon.setHpActual(nuevoHp);
        System.out.println(pokemon.getNombre() + " ha recuperado salud. HP actual: " + pokemon.getHpActual());
    }

    @Override
    public void revivir(Pokemon pokemon, int porcentaje) {
        System.out.println(pokemon.getNombre() + " no esta debilitado y no necesita revivir.");
    }

    @Override
    public boolean isDebilitado() {
        return false;
    }
}