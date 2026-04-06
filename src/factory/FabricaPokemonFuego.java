package factory;
import data.RepositorioDatos;
import core.Pokemon;
import core.PokemonTipoFuego;

public class FabricaPokemonFuego extends FabricaPokemon {

    public FabricaPokemonFuego(RepositorioDatos repo) {
        super(repo);
    }

    @Override
    public Pokemon crearPokemon(String especieBase, int nivel) {
        // 1. Obtener estadísticas base del JSON
        Object stats = repositorio.obtenerEstadisticasBase(especieBase);
        
        // 2. Crear la instancia básica del tipo Fuego
        PokemonTipoFuego nuevoPokemon = new PokemonTipoFuego(especieBase, nivel);
        // (Aquí podrías inyectar 'stats' al nuevoPokemon si hicieras un setEstadisticas)
        
        // 3. Cargar movimientos permitidos hasta ese nivel
        nuevoPokemon.setMovimientos(
            repositorio.obtenerMovimientosPorNivel(especieBase, nivel)
        );
        
        // 4. ¡NUEVO! Cargar la lista de evoluciones posibles desde el JSON
        nuevoPokemon.setEvoluciones(
            repositorio.obtenerEvoluciones(especieBase)
        );

        // 5. Forzar una verificación inicial por si el Pokémon fue creado 
        // directamente en un nivel alto (ej. nivel 30, ya debería nacer evolucionado)
        nuevoPokemon.verificarEvolucion(); 
        
        return nuevoPokemon;
    }
}
