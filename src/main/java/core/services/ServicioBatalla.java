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
        
        jugador.setPokemonActivo(obtenerSiguientePokemonApto(jugador));
        rival.setPokemonActivo(obtenerSiguientePokemonApto(rival));

        if (jugador.getPokemonActivo() == null || rival.getPokemonActivo() == null) return;

        System.out.println("Adelante, " + jugador.getPokemonActivo().getNombre() + "!");

        while (jugador.tienePokemonVivos() && rival.tienePokemonVivos()) {
            System.out.println("\n[ BATALLA ]");
            Pokemon activoRival = rival.getPokemonActivo();
            Pokemon activoJugador = jugador.getPokemonActivo();
            System.out.println("Rival: " + activoRival.getNombre() + " [HP: " + activoRival.getHpActual() + "] | Tu: " + activoJugador.getNombre() + " [HP: " + activoJugador.getHpActual() + "]");
            System.out.print("1. Atacar | 2. Mochila | 3. Cambiar Pokemon\n> Que haras?: ");

            String opcion = scanner.nextLine();
            battle.command.ComandoTurno cmdJugador = null;

            switch (opcion) {
                case "1":
                    cmdJugador = new battle.command.ComandoAtacar(jugador, rival);
                    break;
                case "2":
                    cmdJugador = abrirMochila(jugador, activoJugador, activoRival);
                    if (cmdJugador == null) {
                        continue; 
                    }
                    break;
                case "3":
                    Pokemon nuevo = rotarPokemonActivo(jugador, activoJugador);
                    if (nuevo != activoJugador) {
                        cmdJugador = new battle.command.ComandoCambiarPokemon(jugador, nuevo);
                    } else {
                        continue;
                    }
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    continue;
            }

            battle.command.ComandoTurno cmdRival = new battle.command.ComandoAtacar(rival, jugador); 
            if (rand.nextInt(10) < 2) { 
                Pokemon posibleCambio = null;
                for (Pokemon p : rival.getEquipo()) {
                    if (!p.isDebilitado() && p != activoRival) {
                        posibleCambio = p;
                        break;
                    }
                }
                if (posibleCambio != null) {
                    cmdRival = new battle.command.ComandoCambiarPokemon(rival, posibleCambio);
                }
            }

            motor.ejecutarTurno(cmdJugador, cmdRival);

            Pokemon activoRivalActual = rival.getPokemonActivo();

            if (activoRivalActual.isCapturado()) {
                jugador.agregarPokemon(activoRivalActual);
                activoRivalActual.curar(999);
                break;
            }

            if (jugador.getPokemonActivo().isDebilitado()) {
                jugador.setPokemonActivo(obtenerSiguientePokemonApto(jugador));
            }
            if (rival.getPokemonActivo().isDebilitado()) {
                rival.setPokemonActivo(obtenerSiguientePokemonApto(rival));
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

    private battle.command.ComandoTurno abrirMochila(Entrenador jugador, Pokemon activo, Pokemon activoRival) {
        if (jugador.getMochila().isEmpty()) {
            System.out.println("Tu mochila esta vacia.");
            return null;
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
            if (seleccion == 0) return null;
            
            if (seleccion > 0 && seleccion <= nombresUnicos.size()) {
                String nombreSeleccionado = nombresUnicos.get(seleccion - 1);
                Objeto obj = tipoObjetos.get(nombreSeleccionado);
                Pokemon objetivo = activo;

                if (obj.getTipoObjetivo() == items.domain.TipoObjetivo.RIVAL) {
                    objetivo = activoRival;
                } else if (obj.getTipoObjetivo() == items.domain.TipoObjetivo.ALIADO) {
                    System.out.println("\n[ ELEGIR POKEMON - " + obj.getNombre().toUpperCase() + " ]");
                    for (int i = 0; i < jugador.getEquipo().size(); i++) {
                        Pokemon p = jugador.getEquipo().get(i);
                        String estado = p.isDebilitado() ? "[X]" : "[HP: " + p.getHpActual() + "/" + p.getHpMaximo() + "]";
                        System.out.println((i + 1) + ". " + p.getNombre() + " " + estado);
                    }
                    System.out.println("0. Cancelar");
                    System.out.print("> Elige un Pokemon: ");
                    
                    int opc = Integer.parseInt(scanner.nextLine());
                    if (opc == 0) return null;
                    
                    if (opc > 0 && opc <= jugador.getEquipo().size()) {
                        objetivo = jugador.getEquipo().get(opc - 1);
                        if (!obj.esAplicable(objetivo)) {
                            System.out.println(obj.getMensajeErrorAplicacion());
                            return null;
                        }
                    } else {
                        System.out.println("Opcion invalida.");
                        return null;
                    }
                }
                
                for (int i = 0; i < jugador.getMochila().size(); i++) {
                    if (jugador.getMochila().get(i).getNombre().equals(nombreSeleccionado)) {
                        jugador.getMochila().remove(i);
                        break;
                    }
                }
                return new battle.command.ComandoUsarObjeto(obj, objetivo);
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida.");
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
}