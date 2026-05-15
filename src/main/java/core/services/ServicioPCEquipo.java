package core.services;

import java.util.List;
import java.util.Scanner;
import core.Entrenador;
import core.Pokemon;
import core.events.GameEventManager;

public class ServicioPCEquipo {
    private Scanner scanner;

    public ServicioPCEquipo(Scanner scanner) {
        this.scanner = scanner;
    }

    public void mostrarCaja(Entrenador jugador) {
        GameEventManager.getInstance().notifyMessage("\n[CAJA PC]");
        List<Pokemon> pc = jugador.getCajaPc();
        if (pc.isEmpty()) {
            GameEventManager.getInstance().notifyMessage("> Vacia.");
            return;
        }
        for (int i = 0; i < pc.size(); i++) {
            Pokemon p = pc.get(i);
            GameEventManager.getInstance().notifyMessage((i + 1) + ". " + p.getNombre() + " [HP: " + p.getHpActual() + "/" + p.getHpMaximo() + "]");
        }
    }

    public void gestionarEquipo(Entrenador jugador) {
        boolean gestionando = true;
        while (gestionando) {
            GameEventManager.getInstance().notifyMessage("\n[GESTIONAR EQUIPO] 1.Depositar a PC  2.Retirar de PC  3.Intercambiar  4.Volver");
            GameEventManager.getInstance().notifyMessageInline("> ");
            String opc = scanner.nextLine();

            switch (opc) {
                case "1": depositar(jugador); break;
                case "2": retirar(jugador); break;
                case "3": intercambiar(jugador); break;
                case "4": gestionando = false; break;
                default: GameEventManager.getInstance().notifyMessage("Opcion invalida.");
            }
        }
    }

    private void depositar(Entrenador jugador) {
        List<Pokemon> equipo = jugador.getEquipo();
        if (equipo.size() <= 1) {
            GameEventManager.getInstance().notifyMessage("No puedes quedarte sin Pokemons en el equipo.");
            return;
        }
        jugador.mostrarEquipo();
        GameEventManager.getInstance().notifyMessage("Elige el Pokemon a depositar (numero), 0 para cancelar:");
        GameEventManager.getInstance().notifyMessageInline("> ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < equipo.size()) {
                Pokemon p = equipo.remove(index);
                jugador.getCajaPc().add(p);
                GameEventManager.getInstance().notifyMessage(p.getNombre() + " depositado en la Caja PC.");
                if (jugador.getPokemonActivo() == p) {
                    jugador.setPokemonActivo(equipo.get(0));
                }
            }
        } catch (NumberFormatException e) {
            GameEventManager.getInstance().notifyMessage("Opcion invalida.");
        }
    }

    private void retirar(Entrenador jugador) {
        List<Pokemon> equipo = jugador.getEquipo();
        List<Pokemon> pc = jugador.getCajaPc();
        if (equipo.size() >= 6) {
            GameEventManager.getInstance().notifyMessage("Tu equipo ya esta lleno (6/6).");
            return;
        }
        if (pc.isEmpty()) {
            GameEventManager.getInstance().notifyMessage("Tu Caja PC esta vacia.");
            return;
        }
        mostrarCaja(jugador);
        GameEventManager.getInstance().notifyMessage("Elige el Pokemon a retirar (numero), 0 para cancelar:");
        GameEventManager.getInstance().notifyMessageInline("> ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < pc.size()) {
                Pokemon p = pc.remove(index);
                equipo.add(p);
                GameEventManager.getInstance().notifyMessage(p.getNombre() + " se unio a tu equipo.");
            }
        } catch (NumberFormatException e) {
            GameEventManager.getInstance().notifyMessage("Opcion invalida.");
        }
    }

    private void intercambiar(Entrenador jugador) {
        List<Pokemon> equipo = jugador.getEquipo();
        List<Pokemon> pc = jugador.getCajaPc();
        if (pc.isEmpty()) {
            GameEventManager.getInstance().notifyMessage("Tu Caja PC esta vacia. No puedes intercambiar.");
            return;
        }
        jugador.mostrarEquipo();
        GameEventManager.getInstance().notifyMessage("Elige el Pokemon del EQUIPO a cambiar (numero), 0 para cancelar:");
        GameEventManager.getInstance().notifyMessageInline("> ");
        try {
            int indexEq = Integer.parseInt(scanner.nextLine()) - 1;
            if (indexEq < 0 || indexEq >= equipo.size()) return;

            mostrarCaja(jugador);
            GameEventManager.getInstance().notifyMessage("Elige el Pokemon de la PC a cambiar (numero), 0 para cancelar:");
            GameEventManager.getInstance().notifyMessageInline("> ");
            int indexPc = Integer.parseInt(scanner.nextLine()) - 1;
            if (indexPc < 0 || indexPc >= pc.size()) return;

            Pokemon pEquipo = equipo.get(indexEq);
            Pokemon pPc = pc.get(indexPc);

            equipo.set(indexEq, pPc);
            pc.set(indexPc, pEquipo);

            GameEventManager.getInstance().notifyMessage("Intercambio exitoso: " + pPc.getNombre() + " subio al equipo por " + pEquipo.getNombre() + ".");
            if (jugador.getPokemonActivo() == pEquipo) {
                jugador.setPokemonActivo(pPc);
            }
        } catch (NumberFormatException e) {
            GameEventManager.getInstance().notifyMessage("Opcion invalida.");
        }
    }
}
