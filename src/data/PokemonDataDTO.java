package data;

import core.EstadisticasBase;
import core.EvolucionData;
import core.MovimientoData;
import java.util.List;

public class PokemonDataDTO {
    
    // Las variables coinciden EXACTAMENTE con las llaves del JSON
    private String especieBase;
    private String tipo;
    private EstadisticasBase estadisticasBase;
    private List<EvolucionData> evoluciones;
    private List<MovimientoData> movimientosDisponibles;

    // Constructor vacío obligatorio para la librería JSON
    public PokemonDataDTO() {}

    // --- GETTERS ---
    public String getEspecieBase() { return especieBase; }
    public String getTipo() { return tipo; }
    public EstadisticasBase getEstadisticasBase() { return estadisticasBase; }
    public List<EvolucionData> getEvoluciones() { return evoluciones; }
    public List<MovimientoData> getMovimientosDisponibles() { return movimientosDisponibles; }

    // --- SETTERS ---
    public void setEspecieBase(String especieBase) { this.especieBase = especieBase; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setEstadisticasBase(EstadisticasBase estadisticasBase) { this.estadisticasBase = estadisticasBase; }
    public void setEvoluciones(List<EvolucionData> evoluciones) { this.evoluciones = evoluciones; }
    public void setMovimientosDisponibles(List<MovimientoData> movimientosDisponibles) { this.movimientosDisponibles = movimientosDisponibles; }
}
