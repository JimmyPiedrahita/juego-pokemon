package data;

import core.EstadisticasBase;
import core.EvolucionData;
import core.Movimiento;
import java.util.List;
import java.util.ArrayList;

public class GestorDocumento implements RepositorioDatos {
    
    private String rutaArchivo;
    // Aquí normalmente tendrías una variable para guardar todos los datos 
    // del JSON cargados en memoria para no leer el archivo a cada rato.

    public GestorDocumento(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        cargarDatos(); // Método interno que lee el archivo JSON al iniciar
    }

    private void cargarDatos() {
        // Aquí usas Gson o Jackson para leer "pokemon_data.json"
        System.out.println("Cargando base de datos desde: " + rutaArchivo);
    }

    @Override
    public EstadisticasBase obtenerEstadisticasBase(String especie) {
        // Buscas en los datos cargados la especie y devuelves sus stats
        return new EstadisticasBase(); 
    }

    @Override
    public List<Movimiento> obtenerMovimientosPorNivel(String especie, int nivel) {
        // Buscas la especie, filtras los MovimientoData por nivel y devuelves los Movimientos
        return new ArrayList<>(); 
    }

    @Override
    public List<EvolucionData> obtenerEvoluciones(String especie) {
        // Buscas la especie y devuelves su lista de evoluciones
        return new ArrayList<>();
    }
}
