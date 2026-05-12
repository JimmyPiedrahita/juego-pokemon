package core.services;

import java.util.Scanner;
import java.util.Random;
import battle.MotorBatalla;
import core.Entrenador;
import core.Movimiento;
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
        core.events.GameEventManager.getInstance().notifyMessage("\n" + rival.getNombre().toUpperCase() + " HA APARECIDO");
        
        jugador.setPokemonActivo(obtenerSiguientePokemonApto(jugador));
        rival.setPokemonActivo(obtenerSiguientePokemonApto(rival));

        if (jugador.getPokemonActivo() == null || rival.getPokemonActivo() == null) return;

        core.events.GameEventManager.getInstance().notifyMessage("Adelante, " + jugador.getPokemonActivo().getNombre());

        while (jugador.tienePokemonVivos() && rival.tienePokemonVivos()) {
            core.events.GameEventManager.getInstance().notifyMessage("\n[ BATALLA ]");
            Pokemon activoRival = rival.getPokemonActivo();
            Pokemon activoJugador = jugador.getPokemonActivo();
            core.events.GameEventManager.getInstance().notifyMessage("Rival: " + activoRival.getNombre() + " [HP: " + activoRival.getHpActual() + "] | Tu: " + activoJugador.getNombre() + " [HP: " + activoJugador.getHpActual() + "]");
            System.out.print("1. Atacar | 2. Mochila | 3. Cambiar Pokemon\n> ");

            String opcion = scanner.nextLine();
            battle.command.ComandoTurno cmdJugador = null;

            switch (opcion) {
                case "1":
                    java.util.List<Movimiento> movs = activoJugador.getMovimientos();
                    if (movs == null || movs.isEmpty()) {
                        System.out.println("Este Pokemon no tiene movimientos.");
                        continue;
                    }
                    System.out.println("Elige un movimiento:");
                    for (int i = 0; i < movs.size(); i++) {
                        System.out.println((i + 1) + ". " + movs.get(i).getNombre() + " (Poder: " + movs.get(i).getPotencia() + ", Tipo: " + movs.get(i).getTipo() + ")");
                    }
                    System.out.println((movs.size() + 1) + ". Cancelar");
                    System.out.print("> ");
                    String oppAtaque = scanner.nextLine();
                    int indiceAtaque = -1;
                    try {
                        indiceAtaque = Integer.parseInt(oppAtaque) - 1;
                    } catch (NumberFormatException e) {
                        // ignore
                    }

                    if (indiceAtaque >= 0 && indiceAtaque < movs.size()) {
                        cmdJugador = new battle.command.ComandoAtacar(jugador, rival, movs.get(indiceAtaque));
                    } else {
                        continue;
                    }
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
                    core.events.GameEventManager.getInstance().notifyMessage("Opcion invalida.");
                    continue;
            }

            // Seleccionar movimiento aleatorio para el rival
            Movimiento movRival = null;
            java.util.List<Movimiento> movsRival = activoRival.getMovimientos();
            if (movsRival != null && !movsRival.isEmpty()) {
                movRival = movsRival.get(rand.nextInt(movsRival.size()));
            }
            battle.command.ComandoTurno cmdRival = new battle.command.ComandoAtacar(rival, jugador, movRival); 
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
            core.events.GameEventManager.getInstance().notifyMessage("\nHas ganado la batalla");
            int recompensa = rand.nextInt(100) + 50; 
            jugador.ganarDinero(recompensa);
        } else {
            core.events.GameEventManager.getInstance().notifyMessage("\nHas perdido.");
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
            core.events.GameEventManager.getInstance().notifyMessage("Tu mochila esta vacia.");
            return null;
        }

        core.events.GameEventManager.getInstance().notifyMessage("\n[ MOCHILA ]");
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
        core.events.GameEventManager.getInstance().notifyMessage("\n0. Cancelar");
        System.out.print("> ");
        
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
                    core.events.GameEventManager.getInstance().notifyMessage("\n[ ELEGIR POKEMON - " + obj.getNombre().toUpperCase() + " ]");
                    for (int i = 0; i < jugador.getEquipo().size(); i++) {
                        Pokemon p = jugador.getEquipo().get(i);
                        String estado = p.isDebilitado() ? "[X]" : "[HP: " + p.getHpActual() + "/" + p.getHpMaximo() + "]";
                        core.events.GameEventManager.getInstance().notifyMessage((i + 1) + ". " + p.getNombre() + " " + estado);
                    }
                    core.events.GameEventManager.getInstance().notifyMessage("0. Cancelar");
                    System.out.print("> ");
                    
                    int opc = Integer.parseInt(scanner.nextLine());
                    if (opc == 0) return null;
                    
                    if (opc > 0 && opc <= jugador.getEquipo().size()) {
                        objetivo = jugador.getEquipo().get(opc - 1);
                        if (!obj.esAplicable(objetivo)) {
                            core.events.GameEventManager.getInstance().notifyMessage(obj.getMensajeErrorAplicacion());
                            return null;
                        }
                    } else {
                        core.events.GameEventManager.getInstance().notifyMessage("Opcion invalida.");
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
            core.events.GameEventManager.getInstance().notifyMessage("Entrada invalida.");
        }
        return null;
    }

    private Pokemon rotarPokemonActivo(Entrenador jugador, Pokemon actual) {
        core.events.GameEventManager.getInstance().notifyMessage("\n[ CAMBIAR POKEMON ]");
        for (int i = 0; i < jugador.getEquipo().size(); i++) {
            Pokemon p = jugador.getEquipo().get(i);
            String estado = p.isDebilitado() ? "[X]" : "[HP: " + p.getHpActual() + "/" + p.getHpMaximo() + "]";
            String marcaActual = (p == actual) ? " (ACTUAL)" : "";
            core.events.GameEventManager.getInstance().notifyMessage((i + 1) + ". " + p.getNombre() + " " + estado + marcaActual);
        }
        core.events.GameEventManager.getInstance().notifyMessage("0. Cancelar");
        System.out.print("> ");
        
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion == 0) {
                return actual;
            }
            if (opcion > 0 && opcion <= jugador.getEquipo().size()) {
                Pokemon seleccionado = jugador.getEquipo().get(opcion - 1);
                if (seleccionado == actual) {
                    core.events.GameEventManager.getInstance().notifyMessage(seleccionado.getNombre() + " ya esta en combate");
                    return actual;
                }
                if (seleccionado.isDebilitado()) {
                    core.events.GameEventManager.getInstance().notifyMessage(seleccionado.getNombre() + " esta debilitado");
                    return actual;
                }
                core.events.GameEventManager.getInstance().notifyMessage("Regresa " + actual.getNombre() + " Adelante " + seleccionado.getNombre());
                return seleccionado;
            } else {
                core.events.GameEventManager.getInstance().notifyMessage("Opcion invalida.");
            }
        } catch (NumberFormatException e) {
            core.events.GameEventManager.getInstance().notifyMessage("Entrada invalida.");
        }
        return actual; 
    }
}

