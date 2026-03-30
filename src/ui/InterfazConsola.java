package ui;

import java.util.Scanner;
import battle.MotorBatalla;
import core.Entrenador;
import core.Pokemon;
import items.domain.Objeto;

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
                    iniciarBatallaInteractiva(jugador, salvaje);
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
    }

    // Nuevo manejador central de la logica de interaccion
    private void iniciarBatallaInteractiva(Entrenador jugador, Entrenador rival) {
        System.out.println("\nUN " + rival.getNombre().toUpperCase() + " HA APARECIDO!");
        
        Pokemon activoJugador = obtenerSiguientePokemonApto(jugador);
        Pokemon activoRival = obtenerSiguientePokemonApto(rival);

        if (activoJugador == null || activoRival == null) return;

        System.out.println("Adelante, " + activoJugador.getNombre() + "!");

        // El bucle while se traslado aqui. Mantiene la batalla viva.
        while (jugador.tienePokemonVivos() && rival.tienePokemonVivos()) {
            System.out.println("\n=== BATALLA ===");
            System.out.println("Rival: " + activoRival.getNombre() + " [HP: " + activoRival.getHpActual() + "]");
            System.out.println("Tu: " + activoJugador.getNombre() + " [HP: " + activoJugador.getHpActual() + "]");
            System.out.println("1. Atacar");
            System.out.println("2. Mochila (Usar Objeto)");
            System.out.println("3. Cambiar Pokemon");
            System.out.print("Que deberia hacer " + activoJugador.getNombre() + "?: ");

            String opcion = scanner.nextLine();
            int accion = 0;

            switch (opcion) {
                case "1":
                    accion = 1;
                    break;
                case "2":
                    if (abrirMochila(jugador, activoJugador)) {
                        accion = 2;
                    } else {
                        continue; // Si el jugador cancela la mochila, se reinicia el menu de batalla
                    }
                    break;
                case "3":
                    Pokemon nuevo = rotarPokemonActivo(jugador, activoJugador);
                    if (nuevo != activoJugador) {
                        activoJugador = nuevo;
                        accion = 3;
                    } else {
                        continue;
                    }
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    continue;
            }

            // Delegacion estricta al arbitro
            motor.ejecutarTurno(jugador, activoJugador, accion, rival, activoRival);

            // Relevos
            if (activoJugador.isDebilitado()) {
                activoJugador = obtenerSiguientePokemonApto(jugador);
            }
            if (activoRival.isDebilitado()) {
                activoRival = obtenerSiguientePokemonApto(rival);
            }
        }

        if (jugador.tienePokemonVivos()) {
            System.out.println("\nHas ganado la batalla!");
        } else {
            System.out.println("\nHas perdido. Debes ir al Centro Pokemon para curarte.");
        }
    }

    // Resolucion del uso de fabrica de objetos
    private boolean abrirMochila(Entrenador jugador, Pokemon activo) {
        if (jugador.getMochila().isEmpty()) {
            System.out.println("Tu mochila esta vacia.");
            return false;
        }

        System.out.println("\n--- MOCHILA ---");
        for (int i = 0; i < jugador.getMochila().size(); i++) {
            System.out.println((i + 1) + ". " + jugador.getMochila().get(i).getNombre());
        }
        System.out.println("0. Cancelar");
        System.out.print("Elige un objeto: ");
        
        try {
            int seleccion = Integer.parseInt(scanner.nextLine());
            if (seleccion == 0) return false;
            
            if (seleccion > 0 && seleccion <= jugador.getMochila().size()) {
                Objeto obj = jugador.getMochila().remove(seleccion - 1);
                obj.usar(activo); // Pasamos la referencia real, no un String
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida.");
        }
        return false;
    }

    private Pokemon obtenerSiguientePokemonApto(Entrenador entrenador) {
        for (Pokemon p : entrenador.getEquipo()) {
            if (!p.isDebilitado()) return p;
        }
        return null;
    }

    private Pokemon rotarPokemonActivo(Entrenador jugador, Pokemon actual) {
        System.out.println("Funcionalidad en desarrollo. Manteniendo Pokemon actual.");
        return actual; 
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