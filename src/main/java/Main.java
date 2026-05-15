import java.util.Scanner;
import core.Entrenador;
import items.factory.FabricaPokeball;
import ui.InterfazConsola;
import core.events.GameEventManager;
import ui.ConsolaLogListener;

public class Main {
    public static void main(String[] args) throws Exception {
        GameEventManager.getInstance().subscribe(new ConsolaLogListener());

        Scanner scanner = new Scanner(System.in);
        GameEventManager.getInstance().notifyMessage("=================================");
        GameEventManager.getInstance().notifyMessage("        JUEGO POKEMON            ");
        GameEventManager.getInstance().notifyMessage("=================================");
        GameEventManager.getInstance().notifyMessageInline("Nombre: ");
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