package core.services;

import java.security.SecureRandom;
import battle.MotorBatalla;
import core.Entrenador;
import core.Pokemon;

public class ServicioBatalla {
    private MotorBatalla motor;
    private SecureRandom rand;
    private battle.strategy.RivalStrategy rivalStrategy;

    public ServicioBatalla() {
        this.motor = new MotorBatalla();
        this.rand = new SecureRandom();
        this.rivalStrategy = new battle.strategy.RandomRivalStrategy();
    }

    public Entrenador generarEntrenadorRival(ServicioExploracion exploracion) {
        java.util.List<java.util.Map<String, Object>> entrenadoresBase = core.config.DataLoader.getInstance().getEntrenadoresBase();
        
        String nombreRival = "Gary"; // default
        if (!entrenadoresBase.isEmpty()) {
            java.util.Map<String, Object> base = entrenadoresBase.get(rand.nextInt(entrenadoresBase.size()));
            nombreRival = (String) base.get("nombre");
            // Se podria usar base.get("clase") para mas variedad
        }

        Entrenador rival = new Entrenador(nombreRival, 0); 
        
        int cantidadPokemones = rand.nextInt(6) + 1; 
        for (int i = 0; i < cantidadPokemones; i++) {
            rival.agregarPokemon(exploracion.generarPokemonAleatorio());
        }
        return rival;
    }

    public void ejecutarTurno(Entrenador jugador, Entrenador rival, battle.command.ComandoTurno cmdJugador) {
        battle.command.ComandoTurno cmdRival = rivalStrategy.decidirAccion(rival, jugador);
        motor.ejecutarTurno(cmdJugador, cmdRival);

        Pokemon activoRivalActual = rival.getPokemonActivo();

        if (activoRivalActual.isCapturado()) {
            jugador.agregarPokemon(activoRivalActual);
            activoRivalActual.curar(999);
            rival.getEquipo().remove(activoRivalActual);
            if (rival.tienePokemonVivos()) {
                rival.setPokemonActivo(obtenerSiguientePokemonApto(rival));
            } else {
                rival.setPokemonActivo(null);
            }
        }

        if (jugador.getPokemonActivo().isDebilitado()) {
            jugador.setPokemonActivo(obtenerSiguientePokemonApto(jugador));
        }
        if (rival.getPokemonActivo() != null && rival.getPokemonActivo().isDebilitado()) {
            rival.setPokemonActivo(obtenerSiguientePokemonApto(rival));
        }
    }

    public void evaluarFinDeBatalla(Entrenador jugador, Entrenador rival) {
        if (jugador.tienePokemonVivos() && !rival.tienePokemonVivos()) {
            core.events.GameEventManager.getInstance().notifyMessage("\nGanaste!");
            int recompensa = rand.nextInt(100) + 50; 
            jugador.ganarDinero(recompensa);
        } else if (!jugador.tienePokemonVivos()) {
            core.events.GameEventManager.getInstance().notifyMessage("\nPerdiste.");
        }
    }

    public Pokemon obtenerSiguientePokemonApto(Entrenador entrenador) {
        for (Pokemon p : entrenador.getEquipo()) {
            if (!p.isDebilitado()) return p;
        }
        return null;
    }
}
