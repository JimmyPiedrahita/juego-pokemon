package core;

public class MovimientoData {
    
    // Estas variables deben llamarse EXACTAMENTE igual que en tu archivo JSON
    private int nivelRequerido;
    private Movimiento movimiento;

    // --- CONSTRUCTORES ---
    
    // Constructor vacío: OBLIGATORIO para que librerías como Gson o Jackson 
    // puedan leer el JSON y crear el objeto sin errores.
    public MovimientoData() {
    }

    // Constructor con parámetros (opcional, pero buena práctica)
    public MovimientoData(int nivelRequerido, Movimiento movimiento) {
        this.nivelRequerido = nivelRequerido;
        this.movimiento = movimiento;
    }

    // --- GETTERS ---
    
    public int getNivelRequerido() {
        return nivelRequerido;
    }

    public Movimiento getMovimiento() {
        return movimiento;
    }

    // --- SETTERS ---
    // (Necesarios también para algunas librerías de mapeo JSON)
    
    public void setNivelRequerido(int nivelRequerido) {
        this.nivelRequerido = nivelRequerido;
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }
}