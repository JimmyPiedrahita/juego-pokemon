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
    private core.state.EstadoPokemon estado;
    private boolean capturado = false;

    private List<Movimiento> movimientos;

    private Pokemon(PokemonBuilder builder) {
        this.nombre = builder.nombre;
        this.tipo = builder.tipo;
        this.nivel = builder.nivel;
        this.hpMaximo = builder.hpMaximo;
        this.hpActual = builder.hpActual != 0 ? builder.hpActual : builder.hpMaximo;
        this.ataque = builder.ataque;
        this.defensa = builder.defensa;
        this.velocidad = builder.velocidad;
        this.estado = this.hpActual <= 0 ? new core.state.EstadoDebilitado() : new core.state.EstadoNormal();
        
        // Carga automática de movimientos según el tipo
        List<Movimiento> movs = core.config.DataLoader.getMovimientosPorTipo(this.tipo);
        this.movimientos = movs.size() > 4 ? movs.subList(0, 4) : new ArrayList<>(movs);
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

        public Pokemon build() {
            return new Pokemon(this);
        }
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void registrarVictoria() {
        if (!this.estado.isDebilitado()) {
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

        core.events.GameEventManager.getInstance().notifyMessage(this.nombre + " ha subido al nivel " + this.nivel);

        // Evaluacion de condicion de evolucion
        if (this.nivel == ConfiguracionJuego.NIVEL_EVOLUCION) {
            evolucionar();
        }
    }

    private void evolucionar() {
        core.events.GameEventManager.getInstance().notifyMessage(this.nombre + " esta evolucionando...");
        this.nombre = "Gran " + this.nombre;

        this.hpMaximo += ConfiguracionJuego.INCREMENTO_HP_EVOLUCION;
        this.hpActual += ConfiguracionJuego.INCREMENTO_HP_EVOLUCION;
        this.ataque += ConfiguracionJuego.INCREMENTO_STATS_EVOLUCION;
        this.defensa += ConfiguracionJuego.INCREMENTO_STATS_EVOLUCION;
        this.velocidad += ConfiguracionJuego.INCREMENTO_STATS_EVOLUCION;

        core.events.GameEventManager.getInstance().notifyMessage("Evolucion completada! Ahora es " + this.nombre);
    }

    public void recibirDano(int dano) {
        this.estado.recibirDano(this, dano);
    }

    public void curar(int cantidad) {
        this.estado.curar(this, cantidad);
    }

    public void revivir(int porcentaje) {
        this.estado.revivir(this, porcentaje);
    }

    public void setHpActual(int hpActual) {
        this.hpActual = hpActual;
    }

    public void setEstado(core.state.EstadoPokemon estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isDebilitado() {
        return estado.isDebilitado();
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
