package ui;

import java.util.Scanner;
import core.Entrenador;
import core.services.ServicioBatalla;
import core.services.ServicioCentroPokemon;
import core.services.ServicioExploracion;
import core.services.ServicioTienda;
import core.services.ServicioPCEquipo;

public class JuegoFacade {
    private Scanner scanner;
    private ServicioBatalla servicioBatalla;
    private ServicioCentroPokemon servicioCentroPokemon;
    private ServicioExploracion servicioExploracion;
    private ServicioTienda servicioTienda;
    private ServicioPCEquipo servicioPCEquipo;

    public JuegoFacade() {
        this.scanner = new Scanner(System.in);
        this.servicioBatalla = new ServicioBatalla();
        this.servicioCentroPokemon = new ServicioCentroPokemon();
        this.servicioExploracion = new ServicioExploracion(scanner);
        this.servicioTienda = new ServicioTienda(scanner);
        this.servicioPCEquipo = new ServicioPCEquipo(scanner);
    }

    public void iniciarJuego(Entrenador jugador) {
        boolean jugando = true;
        
        System.out.println("Inicio: " + jugador.getNombre());

        while (jugando) {
            System.out.println("\n[MENU] 1.Equipo 2.Batalla 3.Centro Pkmn 4.Tienda 5.Explorar 6.Mostrar PC 7.Gestionar Equipo 8.Salir");
            System.out.print("> ");
            
            String opcion = scanner.nextLine();
            
            switch (opcion) {
                case "1":
                    jugador.mostrarEquipo();
                    break;
                case "2":
                    if (!jugador.tienePokemonVivos()) {
                        System.out.println("Sin Pkmn vivos.");
                        break;
                    }
                    System.out.println("Buscando rival...");
                    Entrenador rival = servicioBatalla.generarEntrenadorRival(servicioExploracion);
                    BatallaConsolaView batallaView = new BatallaConsolaView(scanner, servicioBatalla);
                    batallaView.iniciarBatalla(jugador, rival);
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
                    servicioPCEquipo.mostrarCaja(jugador);
                    break;
                case "7":
                    servicioPCEquipo.gestionarEquipo(jugador);
                    break;
                case "8":
                    jugando = false;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }
}