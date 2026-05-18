package core.config;

import java.util.Map;

public class ConfiguracionJuego {

    private ConfiguracionJuego() {
        throw new IllegalStateException("Clase utilitaria");
    }

    // Precios de objetos en la tienda
    public static final Map<String, Integer> PRECIOS_OBJETOS = Map.of(
        "Pokeball", 50,
        "Pocion", 20,
        "Revivir", 100
    );

    // Niveles de evolución por defecto u otros magic numbers
    public static final int NIVEL_EVOLUCION = 10;
    
    // Estadísticas al subir de nivel
    public static final int INCREMENTO_HP_NIVEL = 5;
    public static final int INCREMENTO_STATS_NIVEL = 2;
    
    // Incrementos por evolución
    public static final int INCREMENTO_HP_EVOLUCION = 15;
    public static final int INCREMENTO_STATS_EVOLUCION = 10;
}