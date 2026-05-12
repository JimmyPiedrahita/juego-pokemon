package battle.command;

import core.Entrenador;
import core.Movimiento;
import core.Pokemon;

public class ComandoAtacar implements ComandoTurno {
    private Entrenador atacante;
    private Entrenador defensor;
    private Movimiento movimiento;

    public ComandoAtacar(Entrenador atacante, Entrenador defensor, Movimiento movimiento) {
        this.atacante = atacante;
        this.defensor = defensor;
        this.movimiento = movimiento;
    }

    @Override
    public int getPrioridad() {
        Pokemon pActivo = atacante.getPokemonActivo();
        if (pActivo == null) return 0;
        return pActivo.getVelocidad();
    }

    @Override
    public void ejecutar() {
        Pokemon pAtacante = atacante.getPokemonActivo();
        Pokemon pDefensor = defensor.getPokemonActivo();

        if (pAtacante == null || pAtacante.isDebilitado() || pDefensor == null || pDefensor.isDebilitado()) {
            return;
        }

        int danoBase = movimiento != null ? movimiento.getPotencia() : 40;
        int danoFinal = Math.max(1, (pAtacante.getAtaque() + danoBase / 2) - pDefensor.getDefensa());
        String nombreAtaque = movimiento != null ? movimiento.getNombre() : "Struggle";

        core.events.GameEventManager.getInstance().notifyMessage("> " + pAtacante.getNombre() + " ataca a " + pDefensor.getNombre() + " usando " + nombreAtaque);
        core.events.GameEventManager.getInstance().notifyMessage("  Inflige " + danoFinal + " de dano.");

        pDefensor.recibirDano(danoFinal);
        
        if (pDefensor.isDebilitado()) {
            pAtacante.registrarVictoria();
        }
    }
}
