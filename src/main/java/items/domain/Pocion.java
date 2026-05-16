package items.domain;

import core.Pokemon;

public class Pocion extends ObjetoBase {
    private int puntosCuracion = 20;

    @Override
    protected void aplicarEfecto(Pokemon pokemon) {
        core.events.GameEventManager.getInstance().notifyMessage("Pocion usada en " + pokemon.getNombre());
        pokemon.curar(puntosCuracion);
        core.events.GameEventManager.getInstance().notifyMessage("Nueva cantidad de vida " + pokemon.getHpActual());
    }

    @Override
    public String getNombre() {
        return "Pocion";
    }

    @Override
    public TipoObjetivo getTipoObjetivo() {
        return TipoObjetivo.ALIADO;
    }

    @Override
    public boolean esAplicable(Pokemon pokemon) {
        return !pokemon.isDebilitado() && pokemon.getHpActual() < pokemon.getHpMaximo();
    }

    @Override
    public String getMensajeErrorAplicacion() {
        return "No se puede usar en un Pokemon debilitado o con salud maxima.";
    }
}

