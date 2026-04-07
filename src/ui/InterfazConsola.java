package ui;

import java.util.Scanner;
import java.util.Random;
import battle.MotorBatalla;
import core.Entrenador;
import core.Pokemon;
import items.domain.Objeto;
import items.factory.FabricaPociones;
import items.factory.FabricaPokeball;
import items.factory.FabricaRevivir;

public class InterfazConsola {
    private Scanner scanner;
    private MotorBatalla motor;

    public InterfazConsola() {
        this.scanner = new Scanner(System.in);
        this.motor = new MotorBatalla();
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
                    Entrenador rival = generarEntrenadorRival();
                    iniciarBatallaInteractiva(jugador, rival);
                    break;
                case "3":
                    curarEquipoCompleto(jugador);
                    break;
                case "4":
                    irALaTienda(jugador);
                    break;
                case "5":
                    explorar(jugador);
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

    // Nuevo manejador central de la logica de interaccion
    private void iniciarBatallaInteractiva(Entrenador jugador, Entrenador rival) {
        System.out.println("\nUN " + rival.getNombre().toUpperCase() + " HA APARECIDO!");
        
        Pokemon activoJugador = obtenerSiguientePokemonApto(jugador);
        Pokemon activoRival = obtenerSiguientePokemonApto(rival);

        if (activoJugador == null || activoRival == null) return;

        System.out.println("Adelante, " + activoJugador.getNombre() + "!");

        // El bucle while se traslado aqui. Mantiene la batalla viva.
        while (jugador.tienePokemonVivos() && rival.tienePokemonVivos()) {
            System.out.println("\n[ BATALLA ]");
            System.out.println("Rival: " + activoRival.getNombre() + " [HP: " + activoRival.getHpActual() + "] | Tu: " + activoJugador.getNombre() + " [HP: " + activoJugador.getHpActual() + "]");
            System.out.print("1. Atacar | 2. Mochila | 3. Cambiar Pokemon\n> Que haras?: ");

            String opcion = scanner.nextLine();
            int accion = 0;

            switch (opcion) {
                case "1":
                    accion = 1;
                    break;
                case "2":
                    if (abrirMochila(jugador, activoJugador, activoRival)) {
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

            // IA Basica del rival
            int accionRival = 1; // Por defecto atacar
            Random rand = new Random();
            if (rand.nextInt(10) < 2) { // 20% de probabilidad de cambiar
                Pokemon posibleCambio = null;
                for (Pokemon p : rival.getEquipo()) {
                    if (!p.isDebilitado() && p != activoRival) {
                        posibleCambio = p;
                        break;
                    }
                }
                if (posibleCambio != null) {
                    System.out.println("Â¡El rival " + rival.getNombre() + " retira a " + activoRival.getNombre() + " y envÃ­a a " + posibleCambio.getNombre() + "!");
                    activoRival = posibleCambio;
                    accionRival = 3;
                }
            }

            // Delegacion estricta al arbitro
            motor.ejecutarTurno(jugador, activoJugador, accion, rival, activoRival, accionRival);

            if (activoRival.isCapturado()) {
                jugador.agregarPokemon(activoRival);
                activoRival.curar(999);
                break;
            }

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
            int recompensa = new Random().nextInt(100) + 50; // 50 a 149 de premio
            jugador.ganarDinero(recompensa);
        } else {
            System.out.println("\nHas perdido. Debes ir al Centro Pokemon para curarte.");
        }
    }

    // Resolucion del uso de fabrica de objetos
    private boolean abrirMochila(Entrenador jugador, Pokemon activo, Pokemon activoRival) {
        if (jugador.getMochila().isEmpty()) {
            System.out.println("Tu mochila esta vacia.");
            return false;
        }

        System.out.println("\n[ MOCHILA ]");
        for (int i = 0; i < jugador.getMochila().size(); i++) {
            System.out.print((i + 1) + ". " + jugador.getMochila().get(i).getNombre() + " | ");
        }
        System.out.println("0. Cancelar");
        System.out.print("> Elige un objeto: ");
        
        try {
            int seleccion = Integer.parseInt(scanner.nextLine());
            if (seleccion == 0) return false;
            
            if (seleccion > 0 && seleccion <= jugador.getMochila().size()) {
                Objeto obj = jugador.getMochila().get(seleccion - 1);
                Pokemon objetivo = activo;

                if (obj.getNombre().equalsIgnoreCase("Pokeball")) {
                    objetivo = activoRival;
                } else if (obj.getNombre().equalsIgnoreCase("Pocion") || obj.getNombre().equalsIgnoreCase("Revivir")) {
                    System.out.println("\n[ ELEGIR POKEMON - " + obj.getNombre().toUpperCase() + " ]");
                    for (int i = 0; i < jugador.getEquipo().size(); i++) {
                        Pokemon p = jugador.getEquipo().get(i);
                        String estado = p.isDebilitado() ? "[X]" : "[HP: " + p.getHpActual() + "/" + p.getHpMaximo() + "]";
                        System.out.println((i + 1) + ". " + p.getNombre() + " " + estado);
                    }
                    System.out.println("0. Cancelar");
                    System.out.print("> Elige un Pokemon: ");
                    
                    int opc = Integer.parseInt(scanner.nextLine());
                    if (opc == 0) return false;
                    
                    if (opc > 0 && opc <= jugador.getEquipo().size()) {
                        objetivo = jugador.getEquipo().get(opc - 1);
                        if (obj.getNombre().equalsIgnoreCase("Pocion") && objetivo.isDebilitado()) {
                            System.out.println("No puedes usar una pocion en un Pokemon debilitado.");
                            return false;
                        }
                        if (obj.getNombre().equalsIgnoreCase("Revivir") && !objetivo.isDebilitado()) {
                            System.out.println("No puedes usar revivir en un Pokemon que no esta debilitado.");
                            return false;
                        }
                    } else {
                        System.out.println("Opcion invalida.");
                        return false;
                    }
                }
                
                // Si la logica fue correcta, se remueve y usa
                jugador.getMochila().remove(seleccion - 1);
                obj.usar(objetivo);
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
        System.out.println("\n[ CAMBIAR POKEMON ]");
        for (int i = 0; i < jugador.getEquipo().size(); i++) {
            Pokemon p = jugador.getEquipo().get(i);
            String estado = p.isDebilitado() ? "[X]" : "[HP: " + p.getHpActual() + "/" + p.getHpMaximo() + "]";
            String marcaActual = (p == actual) ? " (ACTUAL)" : "";
            System.out.println((i + 1) + ". " + p.getNombre() + " " + estado + marcaActual);
        }
        System.out.println("0. Cancelar");
        System.out.print("> Elige un Pokemon: ");
        
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion == 0) {
                return actual;
            }
            if (opcion > 0 && opcion <= jugador.getEquipo().size()) {
                Pokemon seleccionado = jugador.getEquipo().get(opcion - 1);
                if (seleccionado == actual) {
                    System.out.println("¡" + seleccionado.getNombre() + " ya está en combate!");
                    return actual;
                }
                if (seleccionado.isDebilitado()) {
                    System.out.println("¡" + seleccionado.getNombre() + " está debilitado y no puede luchar!");
                    return actual;
                }
                System.out.println("¡Regresa " + actual.getNombre() + "! ¡Adelante " + seleccionado.getNombre() + "!");
                return seleccionado;
            } else {
                System.out.println("Opción inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
        return actual; 
    }

    private Entrenador generarEntrenadorRival() {
        Random rand = new Random();
        String[] nombresRivales = {"Gary", "Red", "Blue", "Ash", "Misty", "Brock"};
        String nombreRival = nombresRivales[rand.nextInt(nombresRivales.length)];
        Entrenador rival = new Entrenador(nombreRival, 0); // Sin dinero y sin objetos por defecto
        
        int cantidadPokemones = rand.nextInt(6) + 1; // Entre 1 y 6
        for (int i = 0; i < cantidadPokemones; i++) {
            rival.agregarPokemon(generarPokemonAleatorio());
        }
        return rival;
    }

    private void curarEquipoCompleto(Entrenador entrenador) {
        if (entrenador.getEquipo().isEmpty()) {
            System.out.println("\n¡No tienes Pokemon en tu equipo para curar!");
            return;
        }
        
        int costoCuracion = 50;
        System.out.println("\n[ CENTRO POKEMON ] Curar equipo: $" + costoCuracion + " | Fondos: $" + entrenador.getDinero());
        
        if (entrenador.getDinero() < costoCuracion) {
            System.out.println("> No tienes suficiente dinero.");
            return;
        }

        entrenador.gastarDinero(costoCuracion);
        System.out.println("Restaurando la salud de tus Pokemon...");
        for(Pokemon p : entrenador.getEquipo()) {
            p.revivir(100); 
            p.curar(999);
        }
        System.out.println("Tu equipo esta en perfectas condiciones.");
    }

    private void irALaTienda(Entrenador jugador) {
        boolean enTienda = true;
        FabricaPociones fPociones = new FabricaPociones();
        FabricaPokeball fPokeball = new FabricaPokeball();
        FabricaRevivir fRevivir = new FabricaRevivir();

        while (enTienda) {
            System.out.println("\n[ TIENDA POKEMON ] Fondos: $" + jugador.getDinero());
            System.out.println("1. Pokeball ($50) | 2. Pocion ($20) | 3. Revivir ($100) | 4. Salir");
            System.out.print("> Elige una opcion: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    if (jugador.gastarDinero(50)) {
                        jugador.agregarObjeto(fPokeball.entregarObjeto());
                        System.out.println("¡Compraste una Pokeball!");
                    }
                    break;
                case "2":
                    if (jugador.gastarDinero(20)) {
                        jugador.agregarObjeto(fPociones.entregarObjeto());
                        System.out.println("¡Compraste una Pocion!");
                    }
                    break;
                case "3":
                    if (jugador.gastarDinero(100)) {
                        jugador.agregarObjeto(fRevivir.entregarObjeto());
                        System.out.println("¡Compraste un Revivir!");
                    }
                    break;
                case "4":
                    System.out.println("¡Vuelve pronto!");
                    enTienda = false;
                    break;
                default:
                    System.out.println("Error: Opcion invalida. Por favor, introduce un numero del 1 al 4.");
            }
        }
    }

    private void explorar(Entrenador jugador) {
        if (!tienePokeball(jugador)) {
            System.out.println("\n¡No tienes Pokeballs! No puedes salir a explorar sin ellas. Visita la Tienda.");
            return;
        }

        boolean explorando = true;
        while (explorando) {
            System.out.println("\n[ EXPLORANDO ZONA ]");
            System.out.println("1. Buscar / Lanzar Pokeball | 2. Salir");
            System.out.print("> Elige una opcion: ");
            String opcion = scanner.nextLine();

            if (opcion.equals("1")) {
                if (!tienePokeball(jugador)) {
                    System.out.println("Ya no te quedan Pokeballs en la mochila.");
                    break;
                }

                // Generar pokemon aleatorio
                Pokemon salvaje = generarPokemonAleatorio();
                System.out.println("\n¡Un " + salvaje.getNombre() + " salvaje ha aparecido frente a ti!");

                // Consumir pokeball
                usarYDescartarPokeball(jugador);

                // Distancias
                Random rand = new Random();
                int distanciaPokemon = rand.nextInt(20) + 1; // 1 a 20 metros
                int rangoPokeball = rand.nextInt(15) + 6; // 6 a 20 metros alcance

                System.out.println("El " + salvaje.getNombre() + " se encuentra a " + distanciaPokemon + " metros.");
                System.out.println("Has lanzado tu Pokeball a " + rangoPokeball + " metros.");

                if (rangoPokeball >= distanciaPokemon) {
                    System.out.println("¡Felicidades! ¡Has capturado al " + salvaje.getNombre() + "!");
                    salvaje.setCapturado(true);
                    jugador.agregarPokemon(salvaje);
                } else {
                    System.out.println("¡Oh no! El lanzamiento ha caido lejos y el " + salvaje.getNombre() + " ha huido.");
                }

            } else if (opcion.equals("2")) {
                System.out.println("Regresando a una zona segura...");
                explorando = false;
            } else {
                System.out.println("Opcion invalida.");
            }
        }
    }

    private boolean tienePokeball(Entrenador jugador) {
        for (Objeto obj : jugador.getMochila()) {
            if (obj.getNombre().equalsIgnoreCase("Pokeball")) {
                return true;
            }
        }
        return false;
    }

    private void usarYDescartarPokeball(Entrenador jugador) {
        for (int i = 0; i < jugador.getMochila().size(); i++) {
            if (jugador.getMochila().get(i).getNombre().equalsIgnoreCase("Pokeball")) {
                jugador.getMochila().remove(i);
                break;
            }
        }
    }

    private Pokemon generarPokemonAleatorio() {
        String[] nombres = {"Gastly", "Rattata", "Caterpie", "Weedle", "Pikachu", "Meowth", "Zubat"};
        String[] tipos = {"Fantasma", "Normal", "Bicho", "Bicho", "Electrico", "Normal", "Veneno"};
        int r = new java.util.Random().nextInt(nombres.length);
        return new Pokemon(nombres[r], tipos[r], 5, 20, 10, 5, 10);
    }
}