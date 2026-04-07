import java.util.Scanner;
import core.Entrenador;
import core.Pokemon;
import items.factory.FabricaPociones;
import items.factory.FabricaPokeball;
import items.factory.FabricaRevivir;
import ui.InterfazConsola;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=================================");
        System.out.println("   BIENVENIDO AL JUEGO POKEMON   ");
        System.out.println("=================================");
        System.out.print("Por favor, ingresa tu nombre de entrenador: ");
        String nombre = scanner.nextLine();

        // 1. Configuracion inicial del estado del juego
        // Damos 200 de dinero inicial, suficiente para al menos una pokeball
        Entrenador jugador = new Entrenador(nombre, 200);
        Pokemon inicial = new Pokemon("Charmander", "Fuego", 5, 20, 10, 8, 12);
        jugador.agregarPokemon(inicial);

        FabricaPokeball fPokeball = new FabricaPokeball();

        // Se le regalan 3 pokeballs por defecto al inicio
        jugador.agregarObjeto(fPokeball.entregarObjeto());
        jugador.agregarObjeto(fPokeball.entregarObjeto());
        jugador.agregarObjeto(fPokeball.entregarObjeto());
        
        // 2. Inyeccion y arranque de la interfaz
        InterfazConsola ui = new InterfazConsola();
        ui.iniciarJuego(jugador);
    }
}