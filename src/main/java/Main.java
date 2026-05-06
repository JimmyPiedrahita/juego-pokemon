import java.util.Scanner;
import core.Entrenador;
import items.factory.FabricaPokeball;
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
        Entrenador jugador = new Entrenador(nombre, 200);

        FabricaPokeball fPokeball = new FabricaPokeball();

        // Se le regalan 3 pokeballs por defecto al inicio
        jugador.agregarObjeto(fPokeball.entregarObjeto());
        jugador.agregarObjeto(fPokeball.entregarObjeto());
        jugador.agregarObjeto(fPokeball.entregarObjeto());
        
        // 2. Inyeccion y arranque de la interfaz
        InterfazConsola ui = new InterfazConsola();
        ui.iniciarJuego(jugador);
        scanner.close();
    }
}