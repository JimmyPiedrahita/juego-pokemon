package core;

import java.util.ArrayList;
import java.util.List;

import core.config.ConfiguracionJuego;

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
    private boolean capturado = false;

    private List<String> movimientos;

    private Pokemon(PokemonBuilder builder) {
        this.nombre = builder.nombre;
        this.tipo = builder.tipo;
        this.nivel = builder.nivel;
        this.hpMaximo = builder.hpMaximo;
        this.hpActual = builder.hpActual != 0 ? builder.hpActual : builder.hpMaximo;
        this.ataque = builder.ataque;
        this.defensa = builder.defensa;
        this.velocidad = builder.velocidad;
        this.debilitado = this.hpActual <= 0;
        this.movimientos = builder.movimientos != null ? builder.movimientos : new ArrayList<>();
    }

    public static class PokemonBuilder {
        private String nombre;
        private String tipo;
        private int nivel = 1;
        private int hpActual;
        private int hpMaximo;
        private int ataque;
        private int defensa;
        private int velocidad;
        private List<String> movimientos;

        public PokemonBuilder(String nombre) {
            this.nombre = nombre;
        }

        public PokemonBuilder tipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        public PokemonBuilder nivel(int nivel) {
            this.nivel = nivel;
            return this;
        }

        public PokemonBuilder hpMaximo(int hpMaximo) {
            this.hpMaximo = hpMaximo;
            return this;
        }
        
        public PokemonBuilder hpActual(int hpActual) {
            this.hpActual = hpActual;
            return this;
        }

        public PokemonBuilder ataque(int ataque) {
            this.ataque = ataque;
            return this;
        }

        public PokemonBuilder defensa(int defensa) {
            this.defensa = defensa;
            return this;
        }

        public PokemonBuilder velocidad(int velocidad) {
            this.velocidad = velocidad;
            return this;
        }

        public PokemonBuilder movimientos(List<String> movimientos) {
            this.movimientos = movimientos;
            return this;
        }

        public Pokemon build() {
            return new Pokemon(this);
        }
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
        this.hpMaximo += ConfiguracionJuego.INCREMENTO_HP_NIVEL;
        this.hpActual += ConfiguracionJuego.INCREMENTO_HP_NIVEL;
        this.ataque += ConfiguracionJuego.INCREMENTO_STATS_NIVEL;
        this.defensa += ConfiguracionJuego.INCREMENTO_STATS_NIVEL;
        this.velocidad += ConfiguracionJuego.INCREMENTO_STATS_NIVEL;

        System.out.println(this.nombre + " ha subido al nivel " + this.nivel);

        // Evaluacion de condicion de evolucion
        if (this.nivel == ConfiguracionJuego.NIVEL_EVOLUCION) {
            evolucionar();
        }
    }

    // RF-09: Evolucion
    private void evolucionar() {
        System.out.println(this.nombre + " esta evolucionando...");
        this.nombre = "Gran " + this.nombre;

        this.hpMaximo += ConfiguracionJuego.INCREMENTO_HP_EVOLUCION;
        this.hpActual += ConfiguracionJuego.INCREMENTO_HP_EVOLUCION;
        this.ataque += ConfiguracionJuego.INCREMENTO_STATS_EVOLUCION;
        this.defensa += ConfiguracionJuego.INCREMENTO_STATS_EVOLUCION;
        this.velocidad += ConfiguracionJuego.INCREMENTO_STATS_EVOLUCION;

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

    public int getHpMaximo() {
        return hpMaximo;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isCapturado() {
        return capturado;
    }

    public void setCapturado(boolean capturado) {
        this.capturado = capturado;
    }
}