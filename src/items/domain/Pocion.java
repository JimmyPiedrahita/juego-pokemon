package items.domain;

import core.Pokemon;

public class Pocion implements Objeto{
    private int puntosCuracion = 20;

    @Override
    public void usar(Pokemon pokemon) {
        if (!pokemon.isDebilitado()) {
            System.out.println("Pocion usada en " + pokemon.getNombre());
            pokemon.curar(puntosCuracion);
            System.out.println("Nueva cantidad de vida " + pokemon.getHpActual());
        } else {
            System.out.println("No puede usar una pocion en pokemon debilitado");
        }
    }

    @Override
    public String getNombre() {
        return "Pocion";
    }
    
}
