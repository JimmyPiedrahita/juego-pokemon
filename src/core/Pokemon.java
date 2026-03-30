package core;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    // RF-06: Atributos definidos
    private String nombre;
    private String tipo;
    private int nivel;
    private int hpActual;
    private int hpMaximo;
    private int ataque;
    private int defensa;
    private int velocidad;
    private boolean debilitado;

    private List<String> movimientos;

    public Pokemon(String nombre, String tipo, int nivel, int hpMaximo, int ataque, int defensa, int velocidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivel = nivel;
        this.hpMaximo = hpMaximo;
        this.hpActual = hpMaximo;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.debilitado = false;
        this.movimientos = new ArrayList<>();
    }

    // RF-07: Lista de movimientos
    public void aprenderMovimiento(String movimiento) {
        if (this.movimientos.size() < 4) {
            this.movimientos.add(movimiento);
        } else {
            System.out.println(this.nombre + " ya conoce 4 movimientos. No puede aprender mas.");
        }
    }

    // RF-08: Progresion al ganar batallas
    public void registrarVictoria() {
        if (!this.debilitado) {
            subirNivel();
        }
    }

    private void subirNivel() {
        this.nivel++;
        this.hpMaximo += 5;
        this.hpActual += 5;
        this.ataque += 2;
        this.defensa += 2;
        this.velocidad += 2;

        System.out.println(this.nombre + " ha subido al nivel " + this.nivel);

        // Evaluacion de condicion de evolucion
        if (this.nivel == 10) {
            evolucionar();
        }
    }

    // RF-09: Evolucion
    private void evolucionar() {
        System.out.println(this.nombre + " esta evolucionando...");
        this.nombre = "Gran " + this.nombre;

        this.hpMaximo += 15;
        this.hpActual += 15;
        this.ataque += 10;
        this.defensa += 10;
        this.velocidad += 10;

        System.out.println("Evolucion completada! Ahora es " + this.nombre);
    }

    public void recibirDano(int dano) {
        this.hpActual -= dano;
        if (this.hpActual <= 0) {
            this.hpActual = 0;
            this.debilitado = true;
            System.out.println(this.nombre + " se ha debilitado.");
        }
    }

    public void curar(int cantidad) {
        if (!this.debilitado) {
            this.hpActual += cantidad;
            if (this.hpActual > this.hpMaximo) {
                this.hpActual = this.hpMaximo;
            }
            System.out.println(this.nombre + " ha recuperado salud. HP actual: " + this.hpActual);
        }
    }

    public void revivir(int porcentaje) {
        if (this.debilitado) {
            this.debilitado = false;
            this.hpActual = (this.hpMaximo * porcentaje) / 100;
            System.out.println(this.nombre + " ha revivido con " + this.hpActual + " HP.");
        }
    }

    // Getters necesarios para el motor de batalla y la UI
    public String getNombre() {
        return nombre;
    }

    public boolean isDebilitado() {
        return debilitado;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getHpActual() {
        return hpActual;
    }

    public String getTipo() {
        return tipo;
    }
}