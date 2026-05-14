package battle;

import battle.command.ComandoTurno;
import java.util.Random;

public class MotorBatalla {

    private Random random = new Random();

    public void ejecutarTurno(ComandoTurno cmdJugador, ComandoTurno cmdRival) {
        core.events.GameEventManager.getInstance().notifyMessage("\n[RES]");
        
        if (cmdJugador.getPrioridad() > cmdRival.getPrioridad()) {
            cmdJugador.ejecutar();
            cmdRival.ejecutar();
        } else if (cmdJugador.getPrioridad() < cmdRival.getPrioridad()) {
            cmdRival.ejecutar();
            cmdJugador.ejecutar();
        } else {
            if (random.nextBoolean()) {
                cmdJugador.ejecutar();
                cmdRival.ejecutar();
            } else {
                cmdRival.ejecutar();
                cmdJugador.ejecutar();
            }
        }
    }
}
