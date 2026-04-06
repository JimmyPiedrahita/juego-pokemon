package core;
import core.EstadisticasBase;


public class PokemonTipoFuego extends Pokemon {


    // --- CONSTRUCTOR ---
    public PokemonTipoFuego(String especieBase, int nivel) {
        // Llama al constructor de la clase padre (Pokemon)
        // Automáticamente le asignamos el tipo "Fuego" a todos los de esta clase
        super(especieBase, "Fuego", nivel);
    }

    // --- CÁLCULO DE ESTADÍSTICAS ---
    @Override
    protected void actualizarEstadisticas() {
        // Medida de seguridad: Si la fábrica aún no ha inyectado los datos 
        // del JSON, no hacemos el cálculo para evitar errores.
        if (this.statsBase == null) {
            return;
        }

        Object posiblesEvoluciones;
        // Un pequeño bono ficticio: Si el Pokémon tiene evoluciones pendientes, 
        // le damos un ligero empujón a sus stats base.
        int bonoEvolucion = (posiblesEvoluciones != null && !posiblesEvoluciones.isEmpty()) ? 10 : 0;

        // FÓRMULA DEL TIPO FUEGO: 
        // Prioridad máxima en Ataque (x4) y Velocidad (x3). 
        // Crecimiento lento en Defensa (x1).
        this.hpMaximo = this.statsBase.hpMaximo + (this.nivel * 2) + bonoEvolucion;
        this.ataque = this.statsBase.ataque + (this.nivel * 4) + bonoEvolucion; 
        this.defensa = this.statsBase.defensa + (this.nivel * 1) + bonoEvolucion;
        this.velocidad = this.statsBase.velocidad + (this.nivel * 3) + bonoEvolucion;
        
        // Cada vez que sus estadísticas se recalculan (ej. al subir de nivel),
        // curamos al Pokémon a su nuevo HP máximo.
        this.hpActual = this.hpMaximo;
    }

    
}