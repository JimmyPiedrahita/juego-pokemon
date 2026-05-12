package core.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import core.Movimiento;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {
    private static Map<String, List<Movimiento>> movimientosPorTipo = new HashMap<>();
    private static List<Map<String, String>> pokemonsBase = new ArrayList<>();

    static {
        cargarDatos();
    }

    private static void cargarDatos() {
        Gson gson = new Gson();
        try {
            // Cargar movimientos
            Reader movimientosReader = new InputStreamReader(
                    DataLoader.class.getClassLoader().getResourceAsStream("movimientos.json"));
            Type tipoMapa = new TypeToken<Map<String, List<Movimiento>>>() {}.getType();
            movimientosPorTipo = gson.fromJson(movimientosReader, tipoMapa);
            if (movimientosPorTipo == null) movimientosPorTipo = new HashMap<>();
            
            // Cargar pokemons
            Reader pokemonsReader = new InputStreamReader(
                    DataLoader.class.getClassLoader().getResourceAsStream("pokemons.json"));
            Type tipoLista = new TypeToken<List<Map<String, String>>>() {}.getType();
            pokemonsBase = gson.fromJson(pokemonsReader, tipoLista);
            if (pokemonsBase == null) pokemonsBase = new ArrayList<>();

        } catch (Exception e) {
            System.err.println("Error al cargar datos JSON: " + e.getMessage());
        }
    }

    public static List<Movimiento> getMovimientosPorTipo(String tipo) {
        return movimientosPorTipo.getOrDefault(tipo, new ArrayList<>());
    }

    public static List<Map<String, String>> getPokemonsBase() {
        return pokemonsBase;
    }
}
