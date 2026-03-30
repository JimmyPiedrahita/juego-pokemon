import core.Entrenador;
import core.Pokemon;
import items.factory.FabricaPociones;
import items.factory.FabricaPokeball;
import items.factory.FabricaRevivir;
import ui.InterfazConsola;

public class Main {
    public static void main(String[] args) throws Exception {
        // 1. Configuracion inicial del estado del juego
        Entrenador jugador = new Entrenador("Ash", 1000);
        Pokemon inicial = new Pokemon("Charmander", "Fuego", 5, 20, 10, 8, 12);
        jugador.agregarPokemon(inicial);

        FabricaPociones fPociones = new FabricaPociones();
        FabricaPokeball fPokeball = new FabricaPokeball();
        FabricaRevivir fRevivir = new FabricaRevivir();

        jugador.agregarObjeto(fPociones.entregarObjeto());
        jugador.agregarObjeto(fPokeball.entregarObjeto());
        jugador.agregarObjeto(fPokeball.entregarObjeto());
        jugador.agregarObjeto(fRevivir.entregarObjeto());
        
        // 2. Inyeccion y arranque de la interfaz
        InterfazConsola ui = new InterfazConsola();
        ui.iniciarJuego(jugador);
    }
}