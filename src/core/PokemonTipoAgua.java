package core;

public class PokemonTipoAgua extends Pokemon {

    // --- CONSTRUCTOR ---
    public PokemonTipoAgua(String especieBase, int nivel) {
        super(especieBase, "Agua", nivel);
    }

    // --- CÁLCULO DE ESTADÍSTICAS ---
    @Override
    protected void actualizarEstadisticas() {
        // Validación de seguridad vital
        if (this.statsBase == null) {
            return;
        }

        int bonoEvolucion = (posiblesEvoluciones != null && !posiblesEvoluciones.isEmpty()) ? 10 : 0;

        // FÓRMULA DEL TIPO AGUA: 
        // Prioridad máxima en Defensa (x4). 
        // Crecimiento moderado en HP (x3), Ataque (x2) y Velocidad (x2).
        this.hpMaximo = this.statsBase.hpMaximo + (this.nivel * 3) + bonoEvolucion;
        this.ataque = this.statsBase.ataque + (this.nivel * 2) + bonoEvolucion;
        this.defensa = this.statsBase.defensa + (this.nivel * 4) + bonoEvolucion; 
        this.velocidad = this.statsBase.velocidad + (this.nivel * 2) + bonoEvolucion;
        
        this.hpActual = this.hpMaximo;
    }
}