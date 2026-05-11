package battle.command;

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
        core.events.GameEventManager.getInstance().notifyMessage("\n> Se ha usado el objeto " + objeto.getNombre() + " en " + objetivo.getNombre() + ".");
        objeto.usar(objetivo);
    }
}
