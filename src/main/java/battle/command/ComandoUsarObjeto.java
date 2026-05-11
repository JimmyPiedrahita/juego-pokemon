package battle.command;

import core.Entrenador;
import items.domain.Objeto;
import core.Pokemon;

public class ComandoUsarObjeto implements ComandoTurno {
    private Objeto objeto;
    private Pokemon objetivo;

    public ComandoUsarObjeto(Objeto objeto, Pokemon objetivo) {
        this.objeto = objeto;
        this.objetivo = objetivo;
    }

    @Override
    public int getPrioridad() {
        return 999;
    }

    @Override
    public void ejecutar() {
        System.out.println("\n> Se ha usado el objeto " + objeto.getNombre() + " en " + objetivo.getNombre() + ".");
        objeto.usar(objetivo);
    }
}