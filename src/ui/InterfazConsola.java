package ui;
import java.util.Scanner;

import battle.MotorBatalla;
import core.Entrenador;
import core.Pokemon;

public class InterfazConsola {
    private Scanner scanner;
    private MotorBatalla motor;

    public InterfazConsola() {
        this.scanner = new Scanner(System.in);
        this.motor = new MotorBatalla();
    }

    public void iniciarJuego(Entrenador jugador) {
        boolean jugando = true;
        
        System.out.println("=================================");
        System.out.println("   BIENVENIDO AL JUEGO POKEMON   ");
        System.out.println("=================================");
        System.out.println("Entrenador " + jugador.getNombre() + " listo para la aventura.");

        while (jugando) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Ver mi Equipo");
            System.out.println("2. Explorar (Buscar Batalla)");
            System.out.println("3. Ir al Centro Pokemon (Curar Equipo)");
            System.out.println("4. Salir del Juego");
            System.out.print("Elige una opcion: ");
            
            String opcion = scanner.nextLine();
            
            switch (opcion) {
                case "1":
                    jugador.mostrarEquipo();
                    break;
                case "2":
                    System.out.println("\nCaminando por la hierba alta...");
                    Entrenador salvaje = generarEncuentroSalvaje();
                    motor.iniciarBatalla(jugador, salvaje);
                    break;
                case "3":
                    curarEquipoCompleto(jugador);
                    break;
                case "4":
                    System.out.println("Guardando partida... Hasta pronto!");
                    jugando = false;
                    break;
                default:
                    System.out.println("Error: Opcion invalida. Por favor, introduce un numero del 1 al 4.");
            }
        }
        scanner.close();
    }

    private Entrenador generarEncuentroSalvaje() {
        Entrenador entorno = new Entrenador("Pokemon Salvaje", 0);
        Pokemon pidgey = new Pokemon("Pidgey", "Normal", 3, 15, 8, 6, 10);
        entorno.agregarPokemon(pidgey);
        return entorno;
    }

    private void curarEquipoCompleto(Entrenador entrenador) {
        System.out.println("Enfermera Joy: Restaurando la salud de tus Pokemon...");
        for(Pokemon p : entrenador.getEquipo()) {
            p.revivir(100); 
            p.curar(999);
        }
        System.out.println("Tu equipo esta en perfectas condiciones.");
    }
}