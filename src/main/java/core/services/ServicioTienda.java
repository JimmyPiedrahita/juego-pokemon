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
            System.out.println("\n[ TIENDA POKEMON ] Fondos: $" + jugador.getDinero());
            System.out.println("1. Pokeball ($" + ConfiguracionJuego.PRECIOS_OBJETOS.get("Pokeball") + ") | 2. Pocion ($" + ConfiguracionJuego.PRECIOS_OBJETOS.get("Pocion") + ") | 3. Revivir ($" + ConfiguracionJuego.PRECIOS_OBJETOS.get("Revivir") + ") | 4. Salir");
            System.out.print("> Elige una opcion: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    if (jugador.gastarDinero(ConfiguracionJuego.PRECIOS_OBJETOS.get("Pokeball"))) {
                        jugador.agregarObjeto(fPokeball.entregarObjeto());
                        System.out.println("¡Compraste una Pokeball!");
                    }
                    break;
                case "2":
                    if (jugador.gastarDinero(ConfiguracionJuego.PRECIOS_OBJETOS.get("Pocion"))) {
                        jugador.agregarObjeto(fPociones.entregarObjeto());
                        System.out.println("¡Compraste una Pocion!");
                    }
                    break;
                case "3":
                    if (jugador.gastarDinero(ConfiguracionJuego.PRECIOS_OBJETOS.get("Revivir"))) {
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
}