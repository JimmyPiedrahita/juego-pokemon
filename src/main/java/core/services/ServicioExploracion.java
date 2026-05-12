package core.services;

import core.Entrenador;
import core.Pokemon;
import items.domain.Objeto;
import java.util.Random;
import java.util.Scanner;

public class ServicioExploracion {
    private Scanner scanner;
    private Random rand;

    public ServicioExploracion(Scanner scanner) {
        this.scanner = scanner;
        this.rand = new Random();
    }

    public void explorar(Entrenador jugador) {
        if (!tienePokeball(jugador)) {
            core.events.GameEventManager.getInstance().notifyMessage("\nNo tienes Pokeballs");
            return;
        }

        boolean explorando = true;
        while (explorando) {
            core.events.GameEventManager.getInstance().notifyMessage("\n[ EXPLORANDO ZONA ]");
            core.events.GameEventManager.getInstance().notifyMessage("1. Lanzar Pokeball | 2. Salir");
            System.out.print("> ");
            String opcion = scanner.nextLine();

            if (opcion.equals("1")) {
                if (!tienePokeball(jugador)) {
                    core.events.GameEventManager.getInstance().notifyMessage("Ya no te quedan Pokeballs en la mochila.");
                    break;
                }

                Pokemon salvaje = generarPokemonAleatorio();
                core.events.GameEventManager.getInstance().notifyMessage("\nUn " + salvaje.getNombre() + " salvaje ha aparecido frente a ti");

                usarYDescartarPokeball(jugador);

                int distanciaPokemon = rand.nextInt(20) + 1;
                int rangoPokeball = rand.nextInt(15) + 6;

                core.events.GameEventManager.getInstance().notifyMessage("El " + salvaje.getNombre() + " se encuentra a " + distanciaPokemon + " metros.");
                core.events.GameEventManager.getInstance().notifyMessage("Has lanzado tu Pokeball a " + rangoPokeball + " metros.");

                if (rangoPokeball >= distanciaPokemon) {
                    core.events.GameEventManager.getInstance().notifyMessage("¡Felicidades, capturaste a " + salvaje.getNombre());
                    salvaje.setCapturado(true);
                    jugador.agregarPokemon(salvaje);
                } else {
                    core.events.GameEventManager.getInstance().notifyMessage("El lanzamiento ha caido lejos y el " + salvaje.getNombre() + " ha huido.");
                }

            } else if (opcion.equals("2")) {
                explorando = false;
            } else {
                core.events.GameEventManager.getInstance().notifyMessage("Opcion invalida.");
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

    public Pokemon generarPokemonAleatorio() {
        java.util.List<java.util.Map<String, String>> pokemonsBase = core.config.DataLoader.getPokemonsBase();
        
        if (pokemonsBase.isEmpty()) {
            // Recurso de respaldo si el JSON falla
            return new Pokemon.PokemonBuilder("Rattata")
                    .tipo("Normal")
                    .nivel(5)
                    .hpMaximo(20)
                    .hpActual(20)
                    .ataque(10)
                    .defensa(5)
                    .velocidad(10)
                    .build();
        }

        int r = rand.nextInt(pokemonsBase.size());
        java.util.Map<String, String> base = pokemonsBase.get(r);

        return new Pokemon.PokemonBuilder(base.get("nombre"))
                .tipo(base.get("tipo"))
                .nivel(5)
                .hpMaximo(20)
                .hpActual(20)
                .ataque(10)
                .defensa(5)
                .velocidad(10)
                .build();
    }
}

