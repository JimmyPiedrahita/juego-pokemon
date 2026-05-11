package items.domain;

import core.Pokemon;

public class Pocion implements Objeto{
    private int puntosCuracion = 20;

    @Override
    public void usar(Pokemon pokemon) {
        if (esAplicable(pokemon)) {
            core.events.GameEventManager.getInstance().notifyMessage("Pocion usada en " + pokemon.getNombre());
            pokemon.curar(puntosCuracion);
            core.events.GameEventManager.getInstance().notifyMessage("Nueva cantidad de vida " + pokemon.getHpActual());
        } else {
            core.events.GameEventManager.getInstance().notifyMessage(getMensajeErrorAplicacion());
        }
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

