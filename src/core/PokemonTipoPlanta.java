package core;

public class PokemonTipoPlanta extends Pokemon {

    // --- CONSTRUCTOR ---
    public PokemonTipoPlanta(String especieBase, int nivel) {
        super(especieBase, "Planta", nivel);
    }

    // --- CÁLCULO DE ESTADÍSTICAS ---
    @Override
    protected void actualizarEstadisticas() {
        // Validación de seguridad vital
        if (this.statsBase == null) {
            return;
        }

        int bonoEvolucion = (posiblesEvoluciones != null && !posiblesEvoluciones.isEmpty()) ? 10 : 0;

        // FÓRMULA DEL TIPO PLANTA: 
        // Prioridad máxima en Puntos de Vida o HP (x5). 
        // Crecimiento moderado en Defensa (x3). 
        // Son más lentos y pegan un poco menos (x2 y x1).
        this.hpMaximo = this.statsBase.hpMaximo + (this.nivel * 5) + bonoEvolucion; 
        this.ataque = this.statsBase.ataque + (this.nivel * 2) + bonoEvolucion;
        this.defensa = this.statsBase.defensa + (this.nivel * 3) + bonoEvolucion;
        this.velocidad = this.statsBase.velocidad + (this.nivel * 1) + bonoEvolucion;
        
        this.hpActual = this.hpMaximo;
    }
}