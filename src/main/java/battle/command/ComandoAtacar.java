package battle.command;

import core.Entrenador;
import core.Pokemon;

public class ComandoAtacar implements ComandoTurno {
    private Entrenador atacante;
    private Entrenador defensor;

    public ComandoAtacar(Entrenador atacante, Entrenador defensor) {
        this.atacante = atacante;
        this.defensor = defensor;
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

        int danoFinal = Math.max(1, pAtacante.getAtaque() - pDefensor.getDefensa());
        System.out.println("> " + pAtacante.getNombre() + " ataca a " + pDefensor.getNombre() + "!");
        System.out.println("  Inflige " + danoFinal + " de dano.");

        pDefensor.recibirDano(danoFinal);
        
        if (pDefensor.isDebilitado()) {
            pAtacante.registrarVictoria();
        }
    }
}