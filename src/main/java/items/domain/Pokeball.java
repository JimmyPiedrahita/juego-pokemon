package items.domain;

import java.util.Random;

import core.Pokemon;

public class Pokeball implements Objeto {

        private static final Random RAND = new Random();

    @Override
    public void usar(Pokemon pokemon) {
        core.events.GameEventManager.getInstance().notifyMessage("Lanzando Pokeball a " + pokemon.getNombre());
        
        // Probabilidad de captura basada en el daño recibido
        double porcentajeSalud = (double) pokemon.getHpActual() / pokemon.getHpMaximo();

        double probabilidad = RAND.nextDouble(); 

        double umbralExito = (porcentajeSalud < 0.5) ? 0.7 : 0.3;

        if (probabilidad <= umbralExito) {
            core.events.GameEventManager.getInstance().notifyMessage("Atrapaste a " + pokemon.getNombre() + ".");
            pokemon.setCapturado(true);
        } else {
            core.events.GameEventManager.getInstance().notifyMessage("El " + pokemon.getNombre() + " se ha escapado.");
        }
    }

    @Override
    public String getNombre() {
        return "Pokeball";
    }

    @Override
    public TipoObjetivo getTipoObjetivo() {
        return TipoObjetivo.RIVAL;
    }

    @Override
    public boolean esAplicable(Pokemon pokemon) {
        return !pokemon.isDebilitado() && !pokemon.isCapturado();
    }

    @Override
    public String getMensajeErrorAplicacion() {
        return "No puedes usar una Pokeball en este objetivo.";
    }
}

