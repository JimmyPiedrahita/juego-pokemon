package factory;
import data.RepositorioDatos;
import core.Pokemon;
import core.PokemonTipoAgua;

public class FabricaPokemonAgua extends FabricaPokemon {

    public FabricaPokemonAgua(RepositorioDatos repo) {
        super(repo);
    }

    @Override
    public Pokemon crearPokemon(String especieBase, int nivel) {
        // 1. Obtener estadísticas base del JSON
        Object stats = repositorio.obtenerEstadisticasBase(especieBase);
        
        // 2. Crear la instancia específica del tipo AGUA
        PokemonTipoAgua nuevoPokemon = new PokemonTipoAgua(especieBase, nivel);
        
        // 3. Cargar movimientos permitidos hasta el nivel actual
        nuevoPokemon.setMovimientos(
            repositorio.obtenerMovimientosPorNivel(especieBase, nivel)
        );
        
        // 4. Cargar la ruta evolutiva (ej. Squirtle -> Wartortle -> Blastoise)
        nuevoPokemon.setEvoluciones(
            repositorio.obtenerEvoluciones(especieBase)
        );

        // 5. Verificar si nace ya evolucionado (por si se crea en nivel 30, por ejemplo)
        nuevoPokemon.verificarEvolucion(); 
        
        return nuevoPokemon;
    }
}
