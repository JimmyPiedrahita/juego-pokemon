package core.services;

import core.Entrenador;
import core.Pokemon;
import items.domain.Objeto;
import java.security.SecureRandom;
import java.util.Scanner;

public class ServicioExploracion {
    private Scanner scanner;
    private SecureRandom rand;

    public ServicioExploracion(Scanner scanner) {
        this.scanner = scanner;
        this.rand = new SecureRandom();
    }

    public void explorar(Entrenador jugador) {
        if (!tienePokeball(jugador)) {
            core.events.GameEventManager.getInstance().notifyMessage("\nSin Pokeballs.");
            return;
        }

        boolean explorando = true;
        while (explorando) {
            core.events.GameEventManager.getInstance().notifyMessage("\n[ZONA]");
            core.events.GameEventManager.getInstance().notifyMessage("1.Lanzar PB 2.Salir");
            core.events.GameEventManager.getInstance().notifyMessageInline("> ");
            String opcion = scanner.nextLine();

            if (opcion.equals("1")) {
                if (!tienePokeball(jugador)) {
                    core.events.GameEventManager.getInstance().notifyMessage("Sin Pokeballs en mochila.");
                    break;
                }

                Pokemon salvaje = generarPokemonAleatorio();
                core.events.GameEventManager.getInstance().notifyMessage("\n" + salvaje.getNombre() + " salvaje aparecio!");

                usarYDescartarPokeball(jugador);

                int distanciaPokemon = rand.nextInt(20) + 1;
                int rangoPokeball = rand.nextInt(15) + 6;

                core.events.GameEventManager.getInstance().notifyMessage("Obj a " + distanciaPokemon + "m | Lanzamiento a " + rangoPokeball + "m");

                if (rangoPokeball >= distanciaPokemon) {
                    core.events.GameEventManager.getInstance().notifyMessage("Atrapaste a " + salvaje.getNombre() + "!");
                    salvaje.setCapturado(true);
                    jugador.agregarPokemon(salvaje);
                } else {
                    core.events.GameEventManager.getInstance().notifyMessage("Fallo. " + salvaje.getNombre() + " huyo.");
                }

            } else if (opcion.equals("2")) {
                explorando = false;
            } else {
                core.events.GameEventManager.getInstance().notifyMessage("Inválido.");
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
        java.util.List<java.util.Map<String, String>> pokemonsBase = core.config.DataLoader.getInstance().getPokemonsBase();
        
        if (pokemonsBase.isEmpty()) {
            // Recurso de respaldo si el JSON falla
            java.util.List<core.Movimiento> movsRespaldo = core.config.DataLoader.getInstance().getMovimientosPorTipo("Normal");
            return new Pokemon.PokemonBuilder("Rattata")
                    .tipo("Normal")
                    .nivel(5)
                    .hpMaximo(20)
                    .hpActual(20)
                    .ataque(10)
                    .defensa(5)
                    .velocidad(10)
                    .movimientos(movsRespaldo.size() > 4 ? movsRespaldo.subList(0, 4) : new java.util.ArrayList<>(movsRespaldo))
                    .build();
        }

        int r = rand.nextInt(pokemonsBase.size());
        java.util.Map<String, String> base = pokemonsBase.get(r);
        String tipo = base.get("tipo");
        java.util.List<core.Movimiento> movs = core.config.DataLoader.getInstance().getMovimientosPorTipo(tipo);

        int hpMax = base.containsKey("hp") ? Integer.parseInt(base.get("hp")) : 20;
        int atk = base.containsKey("ataque") ? Integer.parseInt(base.get("ataque")) : 10;
        int def = base.containsKey("defensa") ? Integer.parseInt(base.get("defensa")) : 5;
        int vel = base.containsKey("velocidad") ? Integer.parseInt(base.get("velocidad")) : 10;

        return new Pokemon.PokemonBuilder(base.get("nombre"))
                .tipo(tipo)
                .nivel(5)
                .hpMaximo(hpMax)
                .hpActual(hpMax)
                .ataque(atk)
                .defensa(def)
                .velocidad(vel)
                .movimientos(movs.size() > 4 ? movs.subList(0, 4) : new java.util.ArrayList<>(movs))
                .build();
    }
}

