package data;

import core.EstadisticasBase;
import core.EvolucionData;
import core.Movimiento;
import java.util.List;

public interface RepositorioDatos {
    // Busca en el archivo y devuelve las estadísticas de un Pokémon específico
    EstadisticasBase obtenerEstadisticasBase(String especie);
    
    // Busca qué ataques puede aprender hasta el nivel actual
    List<Movimiento> obtenerMovimientosPorNivel(String especie, int nivel);
    
    // Busca la ruta evolutiva de ese Pokémon
    List<EvolucionData> obtenerEvoluciones(String especie);
}