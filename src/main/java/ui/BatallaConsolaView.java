package ui;

import java.util.Scanner;
import core.Entrenador;
import core.Movimiento;
import core.Pokemon;
import core.events.GameEventManager;
import core.services.ServicioBatalla;
import items.domain.Objeto;

public class BatallaConsolaView {
    private Scanner scanner;
    private ServicioBatalla servicioBatalla;
    static final String OPCION_INVALIDA = "Opcion invalida.";

    public BatallaConsolaView(Scanner scanner, ServicioBatalla servicioBatalla) {
        this.scanner = scanner;
        this.servicioBatalla = servicioBatalla;
    }

    public void iniciarBatalla(Entrenador jugador, Entrenador rival) {
        GameEventManager.getInstance().notifyMessage("\n" + rival.getNombre().toUpperCase() + " APARECIO");

        jugador.setPokemonActivo(servicioBatalla.obtenerSiguientePokemonApto(jugador));
        rival.setPokemonActivo(servicioBatalla.obtenerSiguientePokemonApto(rival));

        if (jugador.getPokemonActivo() == null || rival.getPokemonActivo() == null)
            return;

        GameEventManager.getInstance().notifyMessage("Ve, " + jugador.getPokemonActivo().getNombre());

        while (jugador.tienePokemonVivos() && rival.tienePokemonVivos()) {
            GameEventManager.getInstance().notifyMessage("\n[BATALLA]");
            Pokemon activoRival = rival.getPokemonActivo();
            Pokemon activoJugador = jugador.getPokemonActivo();
            GameEventManager.getInstance()
                    .notifyMessage("Riv: " + activoRival.getNombre() + " [" + activoRival.getHpActual() + "] | Tu: "
                            + activoJugador.getNombre() + " [" + activoJugador.getHpActual() + "]");
            GameEventManager.getInstance().notifyMessageInline("1.Atacar 2.Mochila 3.Pkmn\n> ");
            String opcion = scanner.nextLine();
            battle.command.ComandoTurno cmdJugador = obtenerComandoJugador(opcion, jugador, rival, activoJugador,
                    activoRival);
            if (cmdJugador == null) {
                continue;
            }
            servicioBatalla.ejecutarTurno(jugador, rival, cmdJugador);
        }
        servicioBatalla.evaluarFinDeBatalla(jugador, rival);
    }

    private battle.command.ComandoTurno procesarOpcionAtacar(Entrenador jugador, Entrenador rival,
            Pokemon activoJugador) {
        java.util.List<Movimiento> movs = activoJugador.getMovimientos();
        if (movs == null || movs.isEmpty()) {
            GameEventManager.getInstance().notifyMessage("Sin mov.");
            return null;
        }

        GameEventManager.getInstance().notifyMessage("Elige mov:");
        for (int i = 0; i < movs.size(); i++) {
            GameEventManager.getInstance().notifyMessage((i + 1) + ". " + movs.get(i).getNombre() + " (P: "
                    + movs.get(i).getPotencia() + " T: " + movs.get(i).getTipo() + ")");
        }
        GameEventManager.getInstance().notifyMessage((movs.size() + 1) + ". Cancelar");
        GameEventManager.getInstance().notifyMessageInline("> ");

        String oppAtaque = scanner.nextLine();
        int indiceAtaque = -1;
        try {
            indiceAtaque = Integer.parseInt(oppAtaque) - 1;
        } catch (NumberFormatException _) {
            // Se ignora, el índice queda en -1 y devolverá null
        }

        if (indiceAtaque >= 0 && indiceAtaque < movs.size()) {
            return new battle.command.ComandoAtacar(jugador, rival, movs.get(indiceAtaque));
        }

        return null;
    }

    private battle.command.ComandoTurno obtenerComandoJugador(String opcion, Entrenador jugador, Entrenador rival,
            Pokemon activoJugador, Pokemon activoRival) {
        switch (opcion) {
            case "1":
                return procesarOpcionAtacar(jugador, rival, activoJugador);
            case "2":
                return abrirMochila(jugador, activoJugador, activoRival);
            case "3":
                Pokemon nuevo = rotarPokemonActivo(jugador, activoJugador);
                if (nuevo != activoJugador) {
                    return new battle.command.ComandoCambiarPokemon(jugador, nuevo);
                }
                return null;
            default:
                GameEventManager.getInstance().notifyMessage(OPCION_INVALIDA);
                return null;
        }
    }

    private battle.command.ComandoTurno abrirMochila(Entrenador jugador, Pokemon activo, Pokemon activoRival) {
        if (jugador.getMochila().isEmpty()) {
            GameEventManager.getInstance().notifyMessage("Mochila vacia.");
            return null;
        }

        GameEventManager.getInstance().notifyMessage("\n[MOCHILA]");
        java.util.Map<String, Integer> conteoObjetos = new java.util.LinkedHashMap<>();
        java.util.Map<String, Objeto> tipoObjetos = new java.util.HashMap<>();

        for (Objeto obj : jugador.getMochila()) {
            conteoObjetos.put(obj.getNombre(), conteoObjetos.getOrDefault(obj.getNombre(), 0) + 1);
            tipoObjetos.put(obj.getNombre(), obj);
        }

        java.util.List<String> nombresUnicos = new java.util.ArrayList<>(conteoObjetos.keySet());

        for (int i = 0; i < nombresUnicos.size(); i++) {
            GameEventManager.getInstance().notifyMessageInline(
                    (i + 1) + ". " + nombresUnicos.get(i) + " x" + conteoObjetos.get(nombresUnicos.get(i)) + " | ");
        }
        GameEventManager.getInstance().notifyMessage("\n0. Cancelar");
        GameEventManager.getInstance().notifyMessageInline("> ");

        try {
            int seleccion = Integer.parseInt(scanner.nextLine());
            if (seleccion == 0)
                return null;

            if (seleccion > 0 && seleccion <= nombresUnicos.size()) {
                String nombreSeleccionado = nombresUnicos.get(seleccion - 1);
                Objeto obj = tipoObjetos.get(nombreSeleccionado);
                Pokemon objetivo = activo;

                if (obj.getTipoObjetivo() == items.domain.TipoObjetivo.RIVAL) {
                    objetivo = activoRival;
                } else if (obj.getTipoObjetivo() == items.domain.TipoObjetivo.ALIADO) {
                    // AQUÍ USAMOS EL PRIMER MÉTODO EXTRAÍDO
                    objetivo = seleccionarObjetivoAliado(jugador, obj);
                    if (objetivo == null)
                        return null; // Si canceló o hubo error
                }

                // AQUÍ USAMOS EL SEGUNDO MÉTODO EXTRAÍDO
                removerObjetoDeMochila(jugador, nombreSeleccionado);

                return new battle.command.ComandoUsarObjeto(obj, objetivo);
            }
        } catch (NumberFormatException _) {
            GameEventManager.getInstance().notifyMessage("Entrada invalida.");
        }
        return null;
    }

    private Pokemon seleccionarObjetivoAliado(Entrenador jugador, Objeto obj) throws NumberFormatException {
        GameEventManager.getInstance().notifyMessage("\n[OBJ: " + obj.getNombre().toUpperCase() + "]");
        for (int i = 0; i < jugador.getEquipo().size(); i++) {
            Pokemon p = jugador.getEquipo().get(i);
            String estado = p.isDebilitado() ? "[X]" : "[" + p.getHpActual() + "/" + p.getHpMaximo() + "]";
            GameEventManager.getInstance().notifyMessage((i + 1) + ". " + p.getNombre() + " " + estado);
        }
        GameEventManager.getInstance().notifyMessage("0. Cancelar");
        GameEventManager.getInstance().notifyMessageInline("> ");

        int opc = Integer.parseInt(scanner.nextLine());
        if (opc == 0)
            return null;

        if (opc > 0 && opc <= jugador.getEquipo().size()) {
            Pokemon objetivo = jugador.getEquipo().get(opc - 1);
            if (!obj.esAplicable(objetivo)) {
                GameEventManager.getInstance().notifyMessage("No aplicable.");
                return null;
            }
            return objetivo;
        }

        GameEventManager.getInstance().notifyMessage(OPCION_INVALIDA);
        return null;
    }

    private void removerObjetoDeMochila(Entrenador jugador, String nombreObjeto) {
        for (int i = 0; i < jugador.getMochila().size(); i++) {
            if (jugador.getMochila().get(i).getNombre().equals(nombreObjeto)) {
                jugador.getMochila().remove(i);
                break;
            }
        }
    }

    private Pokemon rotarPokemonActivo(Entrenador jugador, Pokemon actual) {
        GameEventManager.getInstance().notifyMessage("\n[CAMBIAR PKMN]");
        for (int i = 0; i < jugador.getEquipo().size(); i++) {
            Pokemon p = jugador.getEquipo().get(i);
            String estado = p.isDebilitado() ? "[X]" : "[" + p.getHpActual() + "/" + p.getHpMaximo() + "]";
            String marcaActual = (p == actual) ? " (ACTIVO)" : "";
            GameEventManager.getInstance().notifyMessage((i + 1) + ". " + p.getNombre() + " " + estado + marcaActual);
        }
        GameEventManager.getInstance().notifyMessage("0. Cancelar");
        GameEventManager.getInstance().notifyMessageInline("> ");

        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion == 0) {
                return actual;
            }
            if (opcion > 0 && opcion <= jugador.getEquipo().size()) {
                Pokemon seleccionado = jugador.getEquipo().get(opcion - 1);
                if (seleccionado == actual) {
                    GameEventManager.getInstance().notifyMessage(seleccionado.getNombre() + " ya activo.");
                    return actual;
                }
                if (seleccionado.isDebilitado()) {
                    GameEventManager.getInstance().notifyMessage(seleccionado.getNombre() + " K.O.");
                    return actual;
                }
                GameEventManager.getInstance().notifyMessage("Ve " + seleccionado.getNombre() + "!");
                return seleccionado;
            } else {
                GameEventManager.getInstance().notifyMessage(OPCION_INVALIDA);
            }
        } catch (NumberFormatException _) {
            GameEventManager.getInstance().notifyMessage("Input invalido.");
        }
        return actual;
    }
}