package core.services;

import java.util.Scanner;
import java.util.Random;
import battle.MotorBatalla;
import core.Entrenador;
import core.Pokemon;
import items.domain.Objeto;

public class ServicioBatalla {
    private Scanner scanner;
    private MotorBatalla motor;
    private Random rand;

    public ServicioBatalla(Scanner scanner) {
        this.scanner = scanner;
        this.motor = new MotorBatalla();
        this.rand = new Random();
    }

    public Entrenador generarEntrenadorRival(ServicioExploracion exploracion) {
        String[] nombresRivales = {"Gary", "Red", "Blue", "Ash", "Misty", "Brock"};
        String nombreRival = nombresRivales[rand.nextInt(nombresRivales.length)];
        Entrenador rival = new Entrenador(nombreRival, 0); 
        
        int cantidadPokemones = rand.nextInt(6) + 1; 
        for (int i = 0; i < cantidadPokemones; i++) {
            rival.agregarPokemon(exploracion.generarPokemonAleatorio());
        }
        return rival;
    }

    public void iniciarBatallaInteractiva(Entrenador jugador, Entrenador rival) {
        System.out.println("\nUN " + rival.getNombre().toUpperCase() + " HA APARECIDO!");
        
        Pokemon activoJugador = obtenerSiguientePokemonApto(jugador);
        Pokemon activoRival = obtenerSiguientePokemonApto(rival);

        if (activoJugador == null || activoRival == null) return;

        System.out.println("Adelante, " + activoJugador.getNombre() + "!");

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
                        continue; 
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

            int accionRival = 1; 
            if (rand.nextInt(10) < 2) { 
                Pokemon posibleCambio = null;
                for (Pokemon p : rival.getEquipo()) {
                    if (!p.isDebilitado() && p != activoRival) {
                        posibleCambio = p;
                        break;
                    }
                }
                if (posibleCambio != null) {
                    System.out.println("¡El rival " + rival.getNombre() + " retira a " + activoRival.getNombre() + " y envía a " + posibleCambio.getNombre() + "!");
                    activoRival = posibleCambio;
                    accionRival = 3;
                }
            }

            motor.ejecutarTurno(jugador, activoJugador, accion, rival, activoRival, accionRival);

            if (activoRival.isCapturado()) {
                jugador.agregarPokemon(activoRival);
                activoRival.curar(999);
                break;
            }

            if (activoJugador.isDebilitado()) {
                activoJugador = obtenerSiguientePokemonApto(jugador);
            }
            if (activoRival.isDebilitado()) {
                activoRival = obtenerSiguientePokemonApto(rival);
            }
        }

        if (jugador.tienePokemonVivos()) {
            System.out.println("\nHas ganado la batalla!");
            int recompensa = rand.nextInt(100) + 50; 
            jugador.ganarDinero(recompensa);
        } else {
            System.out.println("\nHas perdido. Debes ir al Centro Pokemon para curarte.");
        }
    }

    private Pokemon obtenerSiguientePokemonApto(Entrenador entrenador) {
        for (Pokemon p : entrenador.getEquipo()) {
            if (!p.isDebilitado()) return p;
        }
        return null;
    }

    private boolean abrirMochila(Entrenador jugador, Pokemon activo, Pokemon activoRival) {
        if (jugador.getMochila().isEmpty()) {
            System.out.println("Tu mochila esta vacia.");
            return false;
        }

        System.out.println("\n[ MOCHILA ]");
        java.util.Map<String, Integer> conteoObjetos = new java.util.LinkedHashMap<>();
        java.util.Map<String, Objeto> tipoObjetos = new java.util.HashMap<>();
        
        for (Objeto obj : jugador.getMochila()) {
            conteoObjetos.put(obj.getNombre(), conteoObjetos.getOrDefault(obj.getNombre(), 0) + 1);
            tipoObjetos.put(obj.getNombre(), obj);
        }
        
        java.util.List<String> nombresUnicos = new java.util.ArrayList<>(conteoObjetos.keySet());
        
        for (int i = 0; i < nombresUnicos.size(); i++) {
            System.out.print((i + 1) + ". " + nombresUnicos.get(i) + " x" + conteoObjetos.get(nombresUnicos.get(i)) + " | ");
        }
        System.out.println("\n0. Cancelar");
        System.out.print("> Elige un objeto: ");
        
        try {
            int seleccion = Integer.parseInt(scanner.nextLine());
            if (seleccion == 0) return false;
            
            if (seleccion > 0 && seleccion <= nombresUnicos.size()) {
                String nombreSeleccionado = nombresUnicos.get(seleccion - 1);
                Objeto obj = tipoObjetos.get(nombreSeleccionado);
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
                
                for (int i = 0; i < jugador.getMochila().size(); i++) {
                    if (jugador.getMochila().get(i).getNombre().equals(nombreSeleccionado)) {
                        jugador.getMochila().remove(i);
                        break;
                    }
                }
                obj.usar(objetivo);
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida.");
        }
        return false;
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
}