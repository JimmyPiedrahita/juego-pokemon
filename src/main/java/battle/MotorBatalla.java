package battle;

import core.Entrenador;
import core.Pokemon;

public class MotorBatalla {

    public void ejecutarTurno(Entrenador jugador, Pokemon activoJugador, int accionJugador,
            Entrenador rival, Pokemon activoRival, int accionRival) {

        System.out.println("\n[ RESOLUCION ]");

        // Regla de negocio: Usar objeto (2) o cambiar Pokemon (3) tiene prioridad.
        boolean atacaJugador = (accionJugador == 1);
        boolean atacaRival = (accionRival == 1);

        if (!atacaJugador && !atacaRival) {
            // Ninguno ataca (ej. ambos cambian pokemon o usan objeto)
            return;
        }

        if (!atacaJugador && atacaRival) {
            ejecutarAtaque(activoRival, activoJugador);
            verificarDebilitamiento(activoJugador, activoRival);
            return;
        }
        
        if (atacaJugador && !atacaRival) {
            ejecutarAtaque(activoJugador, activoRival);
            verificarDebilitamiento(activoRival, activoJugador);
            return;
        }

        // Si ambos atacan, se define prioridad por velocidad.
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