package battle;

import core.Entrenador;
import core.Pokemon;

public class MotorBatalla {

    // RF-10: Inicia el ciclo de turnos secuenciales
    public void iniciarBatalla(Entrenador jugador1, Entrenador jugador2) {
        System.out.println("--- ¡COMIENZA LA BATALLA! ---");
        System.out.println(jugador1.getNombre() + " VS " + jugador2.getNombre());

        // Validacion de integridad antes de iniciar
        if (!jugador1.tienePokemonVivos() || !jugador2.tienePokemonVivos()) {
            System.out.println("Error: Ambos entrenadores deben tener un equipo apto para luchar.");
            return;
        }

        Pokemon activoJ1 = obtenerSiguientePokemon(jugador1);
        Pokemon activoJ2 = obtenerSiguientePokemon(jugador2);

        int turno = 1;

        // RF-14: El bucle se mantiene mientras AMBOS tengan Pokemon vivos
        while (jugador1.tienePokemonVivos() && jugador2.tienePokemonVivos()) {
            System.out.println("\n[ TURNO " + turno + " ]");
            
            // RF-11 y RF-12: Ejecucion rigurosa del combate
            resolverIntercambioDeAtaques(activoJ1, activoJ2);

            // RF-13: Verificacion de estado post-combate
            if (activoJ1.isDebilitado()) {
                System.out.println("El Pokemon de " + jugador1.getNombre() + " cayo.");
                activoJ2.registrarVictoria(); // El rival gana la experiencia
                activoJ1 = obtenerSiguientePokemon(jugador1);
            }

            if (activoJ2.isDebilitado()) {
                System.out.println("El Pokemon de " + jugador2.getNombre() + " cayo.");
                if (activoJ1 != null && !activoJ1.isDebilitado()) {
                    activoJ1.registrarVictoria();
                }
                activoJ2 = obtenerSiguientePokemon(jugador2);
            }

            turno++;
        }

        // RF-14: Declaracion final
        declararGanador(jugador1, jugador2);
    }

    // RF-11: Determinar prioridad por velocidad
    private void resolverIntercambioDeAtaques(Pokemon p1, Pokemon p2) {
        Pokemon primero, segundo;

        if (p1.getVelocidad() >= p2.getVelocidad()) {
            primero = p1;
            segundo = p2;
        } else {
            primero = p2;
            segundo = p1;
        }

        // El primer ataque
        ejecutarAtaque(primero, segundo);

        // Validacion critica: El segundo solo ataca si sobrevivio al primer impacto
        if (!segundo.isDebilitado()) {
            ejecutarAtaque(segundo, primero);
        }
    }

    // RF-12: Calculo de dano
    private void ejecutarAtaque(Pokemon atacante, Pokemon defensor) {
        // Formula matematica protegida contra curacion accidental (danos negativos)
        int danoFinal = Math.max(1, atacante.getAtaque() - defensor.getDefensa());
        
        System.out.println("> " + atacante.getNombre() + " ataca a " + defensor.getNombre() + "!");
        System.out.println("  Inflige " + danoFinal + " de dano.");
        
        defensor.recibirDano(danoFinal);
    }

    // Utilidad del arbitro para rotar el equipo
    private Pokemon obtenerSiguientePokemon(Entrenador entrenador) {
        for (Pokemon p : entrenador.getEquipo()) {
            if (!p.isDebilitado()) {
                System.out.println("Adelante, " + p.getNombre() + "!");
                return p;
            }
        }
        return null;
    }

    // RF-14: Conclusion logica
    private void declararGanador(Entrenador j1, Entrenador j2) {
        System.out.println("\n=================================");
        if (j1.tienePokemonVivos()) {
            System.out.println(" VICTORIA PARA " + j1.getNombre().toUpperCase() + " ");
        } else if (j2.tienePokemonVivos()) {
            System.out.println(" VICTORIA PARA " + j2.getNombre().toUpperCase() + " ");
        } else {
            System.out.println(" EMPATE MUTUO ");
        }
        System.out.println("=================================");
    }
}