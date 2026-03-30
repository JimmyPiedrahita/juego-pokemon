package core;

import java.util.ArrayList;
import java.util.List;
import items.domain.Objeto;

public class Entrenador {
    // RF-01: Perfil
    private String nombre;
    
    // RF-02: Recursos
    private int dinero;
    
    // RF-04 y RF-05: Gestion de equipo y almacenamiento
    private List<Pokemon> equipo;
    private List<Pokemon> cajaPc;
    
    // RF-03: Inventario
    private List<Objeto> mochila;

    public Entrenador(String nombre, int dineroInicial) {
        this.nombre = nombre;
        this.dinero = dineroInicial;
        this.equipo = new ArrayList<>();
        this.cajaPc = new ArrayList<>();
        this.mochila = new ArrayList<>();
    }

    // RF-04 y RF-05: Logica de restriccion estricta
    public void agregarPokemon(Pokemon nuevoPokemon) {
        if (this.equipo.size() < 6) {
            this.equipo.add(nuevoPokemon);
            System.out.println(nuevoPokemon.getNombre() + " se ha unido al equipo de " + this.nombre);
        } else {
            this.cajaPc.add(nuevoPokemon);
            System.out.println("El equipo esta lleno. " + nuevoPokemon.getNombre() + " ha sido enviado a la Caja PC.");
        }
    }

    public void mostrarEquipo() {
        System.out.println("--- Equipo de " + this.nombre + " ---");
        if (equipo.isEmpty()) {
            System.out.println("El equipo esta vacio.");
            return;
        }
        
        for (int i = 0; i < equipo.size(); i++) {
            Pokemon p = equipo.get(i);
            String estado = p.isDebilitado() ? "[DEBILITADO]" : "[HP: " + p.getHpActual() + "]";
            // Nota: Asumo que creaste el metodo getNivel() en la clase Pokemon
            System.out.println((i + 1) + ". " + p.getNombre() + " " + estado);
        }
    }

    // RF-02: Transacciones seguras
    public void ganarDinero(int cantidad) {
        if (cantidad > 0) {
            this.dinero += cantidad;
            System.out.println(this.nombre + " ha ganado $" + cantidad + ". Total: $" + this.dinero);
        }
    }

    public boolean gastarDinero(int cantidad) {
        if (cantidad > 0 && this.dinero >= cantidad) {
            this.dinero -= cantidad;
            return true;
        } else {
            System.out.println("Fondos insuficientes o cantidad invalida.");
            return false;
        }
    }

    // RF-03: Gestion simple de mochila
    public void agregarObjeto(Objeto objeto) {
        this.mochila.add(objeto);
        System.out.println("Se ha añadido un objeto a la mochila.");
    }

    // Metodo fundamental para evaluar el RF-14 (Condicion de Derrota) en el futuro
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