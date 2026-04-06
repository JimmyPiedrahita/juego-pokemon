package core;
import java.util.ArrayList;
import java.util.List;

public abstract class Pokemon {
    
    // --- DATOS BÁSICOS ---
    protected String nombre;
    protected String especieBase;
    protected String tipo;
    protected int nivel;

    // --- ESTADÍSTICAS ACTUALES EN BATALLA ---
    protected int hpActual;
    protected int hpMaximo;
    protected int ataque;
    protected int defensa;
    protected int velocidad;

    // --- DATOS INYECTADOS DESDE EL JSON ---
    protected EstadisticasBase statsBase;
    protected List<Movimiento> movimientos = new ArrayList<>();
    protected List<EvolucionData> posiblesEvoluciones = new ArrayList<>();

    // --- CONSTRUCTOR ---
    public Pokemon(String especieBase, String tipo, int nivel) {
        this.especieBase = especieBase;
        this.tipo = tipo;
        this.nivel = nivel;
        this.nombre = especieBase; // Por defecto, su nombre es su especie al nacer
    }

    // --- MÉTODOS DE CONFIGURACIÓN (Usados por la Fábrica) ---
    
    public void setEstadisticasBase(EstadisticasBase stats) {
        this.statsBase = stats;
        // En cuanto la fábrica nos da las stats del JSON, calculamos las reales
        this.actualizarEstadisticas();
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public void setEvoluciones(List<EvolucionData> evoluciones) {
        this.posiblesEvoluciones = evoluciones;
    }

    // --- LÓGICA DE JUEGO ---
    
    public void subirNivel() {
        this.nivel++;
        System.out.println("¡" + this.nombre + " subió al nivel " + this.nivel + "!");
        
        // Cada vez que sube de nivel, comprobamos si debe evolucionar
        verificarEvolucion();
        
        // Y recalculamos sus estadísticas para que sea más fuerte
        actualizarEstadisticas();
    }

    public void verificarEvolucion() {
        // Si no tiene evoluciones configuradas, salimos del método
        if (posiblesEvoluciones == null || posiblesEvoluciones.isEmpty()) {
            return;
        }

        // Recorremos la lista de evoluciones leída del JSON
        for (EvolucionData evo : posiblesEvoluciones) {
            // Si nuestro nivel alcanzó o superó el nivel requerido...
            if (this.nivel >= evo.getNivelRequerido()) {
                // ...y nuestro nombre aún no es el de la evolución
                if (!this.nombre.equals(evo.getNombreEvolucion())) {
                    this.nombre = evo.getNombreEvolucion();
                    System.out.println("¡Qué es esto! Tu Pokémon ha evolucionado a " + this.nombre + "!");
                }
            }
        }
    }

    // --- EL ÚNICO MÉTODO ABSTRACTO ---
    // Cada hijo (Fuego, Agua, Planta) decidirá la fórmula matemática
    // para mezclar las "statsBase" con el "nivel" actual.
    protected abstract void actualizarEstadisticas();

    // --- GETTERS ÚTILES ---
    public String getNombre() { return nombre; }
    public int getNivel() { return nivel; }
    public int getHpActual() { return hpActual; }
    public String getTipo() { return tipo; }
    
    public void mostrarEstado() {
        System.out.println("--- " + nombre + " (Nv." + nivel + ") ---");
        System.out.println("HP: " + hpActual + "/" + hpMaximo + " | ATK: " + ataque + " | DEF: " + defensa + " | VEL: " + velocidad);
        System.out.println("Ataques: " + movimientos.toString());
    }
}