package core.services;

import core.Entrenador;
import core.Pokemon;

public class ServicioCentroPokemon {
    private static final int COSTO_CURACION = 50;

    public void curarEquipoCompleto(Entrenador entrenador) {
        if (entrenador.getEquipo().isEmpty()) {
            System.out.println("\n¡No tienes Pokemon en tu equipo para curar!");
            return;
        }
        
        System.out.println("\n[ CENTRO POKEMON ] Curar equipo: $" + COSTO_CURACION + " | Fondos: $" + entrenador.getDinero());
        
        if (entrenador.getDinero() < COSTO_CURACION) {
            System.out.println("> No tienes suficiente dinero.");
            return;
        }

        entrenador.gastarDinero(COSTO_CURACION);
        System.out.println("Restaurando la salud de tus Pokemon...");
        for(Pokemon p : entrenador.getEquipo()) {
            p.revivir(100); 
            p.curar(999);
        }
        System.out.println("Tu equipo esta en perfectas condiciones.");
    }
}