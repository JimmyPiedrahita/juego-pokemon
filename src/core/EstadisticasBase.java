package core;

public class EstadisticasBase {
    public int hpMaximo;
    public int ataque;
    public int defensa;
    public int velocidad;
    
    // --- CONSTRUCTORES ---

    // Constructor vacío: Es OBLIGATORIO tenerlo para que librerías como Gson 
    // o Jackson puedan crear el objeto al leer tu archivo pokemon_data.json.
    public EstadisticasBase() {
    }

    // Constructor con parámetros: Opcional, pero muy útil por si en algún 
    // momento necesitas crear estadísticas manualmente en tu código.
    public EstadisticasBase(int hpMaximo, int ataque, int defensa, int velocidad) {
        this.hpMaximo = hpMaximo;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
    }
}