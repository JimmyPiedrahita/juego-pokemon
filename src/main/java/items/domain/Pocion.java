package items.domain;

import core.Pokemon;

public class Pocion implements Objeto{
    private int puntosCuracion = 20;

    @Override
    public void usar(Pokemon pokemon) {
        if (esAplicable(pokemon)) {
            System.out.println("Pocion usada en " + pokemon.getNombre());
            pokemon.curar(puntosCuracion);
            System.out.println("Nueva cantidad de vida " + pokemon.getHpActual());
        } else {
            System.out.println(getMensajeErrorAplicacion());
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
        return "No puedes usar una pocion en un Pokemon debilitado o con salud maxima.";
    }
}
