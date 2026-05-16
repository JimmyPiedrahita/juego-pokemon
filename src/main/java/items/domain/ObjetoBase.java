package items.domain;

import core.Pokemon;
import core.events.GameEventManager;

public abstract class ObjetoBase implements Objeto {

    @Override
    public void usar(Pokemon pokemon) {
        if (esAplicable(pokemon)) {
            aplicarEfecto(pokemon);
        } else {
            GameEventManager.getInstance().notifyMessage(getMensajeErrorAplicacion());
        }
    }

    protected abstract void aplicarEfecto(Pokemon pokemon);
}