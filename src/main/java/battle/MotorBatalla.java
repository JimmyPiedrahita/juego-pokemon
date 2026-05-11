package battle;

import battle.command.ComandoTurno;

public class MotorBatalla {

    public void ejecutarTurno(ComandoTurno cmdJugador, ComandoTurno cmdRival) {
        System.out.println("\n[ RESOLUCION ]");
        
        if (cmdJugador.getPrioridad() >= cmdRival.getPrioridad()) {
            cmdJugador.ejecutar();
            cmdRival.ejecutar();
        } else {
            cmdRival.ejecutar();
            cmdJugador.ejecutar();
        }
    }
}