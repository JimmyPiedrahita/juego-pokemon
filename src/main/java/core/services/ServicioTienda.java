package core.services;

import core.Entrenador;
import core.config.ConfiguracionJuego;
import items.factory.FabricaObjetos;
import items.factory.FabricaPociones;
import items.factory.FabricaPokeball;
import items.factory.FabricaRevivir;
import java.util.Scanner;

public class ServicioTienda {
    private Scanner scanner;
    private FabricaObjetos fPociones;
    private FabricaObjetos fPokeball;
    private FabricaObjetos fRevivir;

    public ServicioTienda(Scanner scanner) {
        this.scanner = scanner;
        this.fPociones = new FabricaPociones();
        this.fPokeball = new FabricaPokeball();
        this.fRevivir = new FabricaRevivir();
    }

    public void abrirTienda(Entrenador jugador) {
        boolean enTienda = true;

        while (enTienda) {
            core.events.GameEventManager.getInstance().notifyMessage("\n[TIENDA] $" + jugador.getDinero());
            core.events.GameEventManager.getInstance().notifyMessage("1.Pokeball($" + ConfiguracionJuego.PRECIOS_OBJETOS.get("Pokeball") + ") 2.Pocion($" + ConfiguracionJuego.PRECIOS_OBJETOS.get("Pocion") + ") 3.Revivir($" + ConfiguracionJuego.PRECIOS_OBJETOS.get("Revivir") + ") 4.Salir");
            core.events.GameEventManager.getInstance().notifyMessageInline("> ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    if (jugador.gastarDinero(ConfiguracionJuego.PRECIOS_OBJETOS.get("Pokeball"))) {
                        jugador.agregarObjeto(fPokeball.entregarObjeto());
                        core.events.GameEventManager.getInstance().notifyMessage("+1 Pokeball");
                    }
                    break;
                case "2":
                    if (jugador.gastarDinero(ConfiguracionJuego.PRECIOS_OBJETOS.get("Pocion"))) {
                        jugador.agregarObjeto(fPociones.entregarObjeto());
                        core.events.GameEventManager.getInstance().notifyMessage("+1 Pocion");
                    }
                    break;
                case "3":
                    if (jugador.gastarDinero(ConfiguracionJuego.PRECIOS_OBJETOS.get("Revivir"))) {
                        jugador.agregarObjeto(fRevivir.entregarObjeto());
                        core.events.GameEventManager.getInstance().notifyMessage("+1 Revivir");
                    }
                    break;
                case "4":
                    enTienda = false;
                    break;
                default:
                    core.events.GameEventManager.getInstance().notifyMessage("Invalido.");
            }
        }
    }
}

