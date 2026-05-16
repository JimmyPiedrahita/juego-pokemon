package items.domain;

import core.Pokemon;

public class Revivir extends ObjetoBase {
    private int porcentajeRecuperacion = 50;

    @Override
    protected void aplicarEfecto(Pokemon pokemon) {
        core.events.GameEventManager.getInstance().notifyMessage("Se revivio a " + pokemon.getNombre());
        pokemon.revivir(porcentajeRecuperacion);
    }

    @Override
    public String getNombre() {
        return "Revivir";
    }

    @Override
    public TipoObjetivo getTipoObjetivo() {
        return TipoObjetivo.ALIADO;
    }

    @Override
    public boolean esAplicable(Pokemon pokemon) {
        return pokemon.isDebilitado();
    }

    @Override
    public String getMensajeErrorAplicacion() {
        return "No se puede usar en un Pokemon que no esta debilitado.";
    }
}

