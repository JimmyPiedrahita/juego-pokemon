package items.domain;

import java.util.Random;

import core.Pokemon;

public class Pokeball implements Objeto {

    @Override
    public void usar(Pokemon pokemon) {
        System.out.println("Lanzando Pokeball a " + pokemon.getNombre());
        
        // Probabilidad de captura basada en el daño recibido
        double porcentajeSalud = (double) pokemon.getHpActual() / pokemon.getHpMaximo(); 
        Random rand = new Random();
        double probabilidad = rand.nextDouble(); 

        double umbralExito = (porcentajeSalud < 0.5) ? 0.7 : 0.3;

        if (probabilidad <= umbralExito) {
            System.out.println("Atrapaste a " + pokemon.getNombre() + ".");
            pokemon.setCapturado(true);
        } else {
            System.out.println("El " + pokemon.getNombre() + " se ha escapado de la Pokeball.");
        }
    }

    @Override
    public String getNombre() {
        return "Pokeball";
    }
    
}
