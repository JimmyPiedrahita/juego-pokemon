package core;

import java.util.ArrayList;
import java.util.List;
import items.domain.Objeto;

public class Entrenador {

    private String nombre;
    private int dinero;
    private List<Pokemon> equipo;
    private List<Pokemon> cajaPc;
    private List<Objeto> mochila;
    private Pokemon pokemonActivo;

    public Entrenador(String nombre, int dineroInicial) {
        this.nombre = nombre;
        this.dinero = dineroInicial;
        this.equipo = new ArrayList<>();
        this.cajaPc = new ArrayList<>();
        this.mochila = new ArrayList<>();
    }

    public Pokemon getPokemonActivo() {
        return pokemonActivo;
    }

    public void setPokemonActivo(Pokemon pokemonActivo) {
        this.pokemonActivo = pokemonActivo;
    }

    public void agregarPokemon(Pokemon nuevoPokemon) {
        if (this.equipo.size() < 6) {
            this.equipo.add(nuevoPokemon);
            core.events.GameEventManager.getInstance().notifyMessage(nuevoPokemon.getNombre() + " en equipo.");
        } else {
            this.cajaPc.add(nuevoPokemon);
            core.events.GameEventManager.getInstance().notifyMessage("Equipo lleno. " + nuevoPokemon.getNombre() + " a PC.");
        }
    }

    public void mostrarEquipo() {
        core.events.GameEventManager.getInstance().notifyMessage("\n[EQUIPO]");
        if (equipo.isEmpty()) {
            core.events.GameEventManager.getInstance().notifyMessage("> Vacio.");
            return;
        }
        
        for (int i = 0; i < equipo.size(); i++) {
            Pokemon p = equipo.get(i);
            String estado = p.isDebilitado() ? "[X]" : "[" + p.getHpActual() + "/" + p.getHpMaximo() + "]";
            core.events.GameEventManager.getInstance().notifyMessage((i + 1) + ". " + p.getNombre() + " " + estado);
        }
    }

    public void ganarDinero(int cantidad) {
        if (cantidad > 0) {
            this.dinero += cantidad;
            core.events.GameEventManager.getInstance().notifyMessage("+$" + cantidad + " (Total: $" + this.dinero + ")");
        }
    }

    public boolean gastarDinero(int cantidad) {
        if (cantidad > 0 && this.dinero >= cantidad) {
            this.dinero -= cantidad;
            return true;
        } else {
            core.events.GameEventManager.getInstance().notifyMessage("Sin dinero.");
            return false;
        }
    }

    public void agregarObjeto(Objeto objeto) {
        this.mochila.add(objeto);
        core.events.GameEventManager.getInstance().notifyMessage("Objeto en mochila.");
    }

    public boolean tienePokemonVivos() {
        for (Pokemon p : equipo) {
            if (!p.isDebilitado()) {
                return true; // Al menos uno puede pelear
            }
        }
        return false; // Todos estan debilitados
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getDinero() { return dinero; }
    public List<Pokemon> getEquipo() { return equipo; }
    public List<Pokemon> getCajaPc() { return cajaPc; }
    public List<Objeto> getMochila() { return mochila; }
}
