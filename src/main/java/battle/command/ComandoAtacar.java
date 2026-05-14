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
        
        // Formula de dano
        int nivel = pAtacante.getNivel();
        int ataque = pAtacante.getAtaque();
        int defensa = pDefensor.getDefensa();
        
        int danoFinal = (int) (((((2.0 * nivel / 5.0) + 2.0) * danoBase * ((double)ataque / defensa)) / 50.0) + 2);
        
        if (danoBase == 0) {
            danoFinal = 0; // Movimientos de estado que no hacen daño directo
        }
        danoFinal = Math.max(danoBase > 0 ? 1 : 0, danoFinal);

        String nombreAtaque = movimiento != null ? movimiento.getNombre() : "Struggle";

        core.events.GameEventManager.getInstance().notifyMessage("> " + pAtacante.getNombre() + " usa " + nombreAtaque + " en " + pDefensor.getNombre());
        core.events.GameEventManager.getInstance().notifyMessage("  Dano: " + danoFinal);

        pDefensor.recibirDano(danoFinal);
        
        if (pDefensor.isDebilitado()) {
            new core.services.ProgressionService().registrarVictoria(pAtacante);
        }
    }
}
