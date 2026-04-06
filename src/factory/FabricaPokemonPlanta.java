package factory;
import data.RepositorioDatos;
import core.Pokemon;
import core.PokemonTipoPlanta;

public class FabricaPokemonPlanta extends FabricaPokemon {

    public FabricaPokemonPlanta(RepositorioDatos repo) {
        super(repo);
    }

    @Override
    public Pokemon crearPokemon(String especieBase, int nivel) {
        // 1. Obtener estadísticas base del JSON
        Object stats = repositorio.obtenerEstadisticasBase(especieBase);
        
        // 2. Crear la instancia específica del tipo PLANTA
        PokemonTipoPlanta nuevoPokemon = new PokemonTipoPlanta(especieBase, nivel);
        
        // 3. Cargar movimientos permitidos hasta el nivel actual
        nuevoPokemon.setMovimientos(
            repositorio.obtenerMovimientosPorNivel(especieBase, nivel)
        );
        
        // 4. Cargar la ruta evolutiva (ej. Bulbasaur -> Ivysaur -> Venusaur)
        nuevoPokemon.setEvoluciones(
            repositorio.obtenerEvoluciones(especieBase)
        );

        // 5. Verificar si nace ya evolucionado
        nuevoPokemon.verificarEvolucion(); 
        
        return nuevoPokemon;
    }
}