package ui;

import java.util.Scanner;
import core.Entrenador;
import core.services.ServicioBatalla;
import core.services.ServicioCentroPokemon;
import core.services.ServicioExploracion;
import core.services.ServicioTienda;

public class JuegoFacade {
    private Scanner scanner;
    private ServicioBatalla servicioBatalla;
    private ServicioCentroPokemon servicioCentroPokemon;
    private ServicioExploracion servicioExploracion;
    private ServicioTienda servicioTienda;

    public JuegoFacade() {
        this.scanner = new Scanner(System.in);
        this.servicioBatalla = new ServicioBatalla(scanner);
        this.servicioCentroPokemon = new ServicioCentroPokemon();
        this.servicioExploracion = new ServicioExploracion(scanner);
        this.servicioTienda = new ServicioTienda(scanner);
    }

    public void iniciarJuego(Entrenador jugador) {
        boolean jugando = true;
        
        System.out.println("\nEntrenador " + jugador.getNombre() + " listo para la aventura.");

        while (jugando) {
            System.out.println("\n[ MENU PRINCIPAL ] \n1. Equipo | 2. Batalla | 3. Centro Pokemon | 4. Tienda | 5. Explorar | 6. Salir");
            System.out.print("> Elige una opcion: ");
            
            String opcion = scanner.nextLine();
            
            switch (opcion) {
                case "1":
                    jugador.mostrarEquipo();
                    break;
                case "2":
                    if (!jugador.tienePokemonVivos()) {
                        System.out.println("\n¡No tienes Pokemones o no estan en condiciones para luchar! Ve al Centro Pokemon o a explorar.");
                        break;
                    }
                    System.out.println("\nBuscando entrenador para una batalla...");
                    Entrenador rival = servicioBatalla.generarEntrenadorRival(servicioExploracion);
                    servicioBatalla.iniciarBatallaInteractiva(jugador, rival);
                    break;
                case "3":
                    servicioCentroPokemon.curarEquipoCompleto(jugador);
                    break;
                case "4":
                    servicioTienda.abrirTienda(jugador);
                    break;
                case "5":
                    servicioExploracion.explorar(jugador);
                    break;
                case "6":
                    System.out.println("!Hasta pronto!");
                    jugando = false;
                    break;
                default:
                    System.out.println("Error: Opcion invalida. Por favor, introduce un numero del 1 al 6.");
            }
        }
    }
}