package items.domain;

import core.Pokemon;

public class Revivir implements Objeto {
    private int porcentajeRecuperacion = 50;

    @Override
    public void usar(Pokemon pokemon) {
        if (!pokemon.isDebilitado()) {
            System.out.println("Se revivio a " + pokemon.getNombre());
            pokemon.revivir(porcentajeRecuperacion);
        } else {
            System.out.println(pokemon.getNombre() + " No esta debilitado");
        }
    }

    @Override
    public String getNombre() {
        return "Revivir";
    }
    
}
