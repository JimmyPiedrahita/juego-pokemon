package battle;

import core.Entrenador;
import core.Pokemon;

public class MotorBatalla {

    public void ejecutarTurno(Entrenador jugador, Pokemon activoJugador, int accionJugador,
            Entrenador rival, Pokemon activoRival) {

        System.out.println("\n--- RESOLUCION DEL TURNO ---");

        // Regla de negocio: Usar objeto (2) o cambiar Pokemon (3) tiene prioridad.
        // Si el jugador no ataco, solo el rival ejecuta su ataque este turno.
        if (accionJugador != 1) {
            ejecutarAtaque(activoRival, activoJugador);
            verificarDebilitamiento(activoJugador, activoRival);
            return;
        }

        // Si la accion es 1, ambos atacan. Se define prioridad por velocidad.
        if (activoJugador.getVelocidad() >= activoRival.getVelocidad()) {
            ejecutarAtaque(activoJugador, activoRival);
            if (!activoRival.isDebilitado()) {
                ejecutarAtaque(activoRival, activoJugador);
            }
        } else {
            ejecutarAtaque(activoRival, activoJugador);
            if (!activoJugador.isDebilitado()) {
                ejecutarAtaque(activoJugador, activoRival);
            }
        }

        // Evaluar caidas para repartir experiencia
        verificarDebilitamiento(activoJugador, activoRival);
        verificarDebilitamiento(activoRival, activoJugador);
    }

    private void ejecutarAtaque(Pokemon atacante, Pokemon defensor) {
        if (atacante.isDebilitado() || defensor.isDebilitado())
            return;

        int danoFinal = Math.max(1, atacante.getAtaque() - defensor.getDefensa());
        System.out.println("> " + atacante.getNombre() + " ataca a " + defensor.getNombre() + "!");
        System.out.println("  Inflige " + danoFinal + " de dano.");

        defensor.recibirDano(danoFinal);
    }

    private void verificarDebilitamiento(Pokemon p1, Pokemon p2) {
        if (p1.isDebilitado() && !p2.isDebilitado()) {
            p2.registrarVictoria();
        }
    }
}