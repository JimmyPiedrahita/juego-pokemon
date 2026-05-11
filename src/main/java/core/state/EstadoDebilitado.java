package core.state;

import core.Pokemon;

public class EstadoDebilitado implements EstadoPokemon {

    @Override
    public void recibirDano(Pokemon pokemon, int dano) {
        core.events.GameEventManager.getInstance().notifyMessage(pokemon.getNombre() + " ya esta debilitado y no puede recibir mas dano.");
    }

    @Override
    public void curar(Pokemon pokemon, int cantidad) {
        core.events.GameEventManager.getInstance().notifyMessage(pokemon.getNombre() + " esta debilitado y no puede ser curado con una pocion.");
    }

    @Override
    public void revivir(Pokemon pokemon, int porcentaje) {
        int hpRevivido = (pokemon.getHpMaximo() * porcentaje) / 100;
        pokemon.setHpActual(hpRevivido);
        pokemon.setEstado(new EstadoNormal());
        core.events.GameEventManager.getInstance().notifyMessage(pokemon.getNombre() + " ha revivido con " + pokemon.getHpActual() + " HP.");
    }

    @Override
    public boolean isDebilitado() {
        return true;
    }
}
