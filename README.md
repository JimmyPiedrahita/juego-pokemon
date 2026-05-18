# Juego de Pokemon

## Como jugar

Al iniciar el juego se te pedira que ingrese un nombre para el entrenador
Por defecto se te dara 3 pokeballs y $200
El menu principal tendra 8 opciones 

1. Equipo: ver todos los pokemon que tienes actualmente en el grupo
2. Batalla: genera un entrenador rival con pokemon aleatorios para enfrentar
3. Centro Pkmn: Cura todos tus pokemon, se te cobrara dinero
4. Tienda: Podras comprar pokeballs, pociones, o revivir
5. Explorar: podras explorar y elejir si lanzar una pokeball y si tienes suerte capturar un pokemon
6. Mostrar PC: muestra todos los pokemon que has atrapado pero no estan en tu equipo principal
7. Gestionar Equipo: intercambia pokemon entre tu PC y tu equipo principal
8. Salir

Como al principio el entrenador no tiene pokemon deberia explorar y capturar pokemon y si se le acaban las
pokeball comprar en la tienda, con al menos un pokemon podras ir a batalla. Tambien deberias comprar una pocion para utilizar en batalla, y con esto lograr una victoria y ganar dinero con el que podras curar tu pokemon en el centro pokemon, comprar mas objetos y armar el equipo ideal utilizando la funcionalidad de PC y Gestion de Equipo.


## Estructura del proyecto
- src/: Es la carpeta principal del código fuente.
  - main/java/:
    - battle/: Lógica de los combates.
      - command/: Clases que implementan el patrón Command (por ejemplo, ComandoAtacar, ComandoUsarObjeto) para las acciones de batalla.
      - strategy/: Estrategias de IA del rival y comportamientos (patrón Strategy).
      - MotorBatalla.java: Controlador que orquesta los combates (turnos, daños, recompensas).
    - core/: Entidades principales y modelos de negocio.
      - config/: Archivos de configuración como `ConfiguracionJuego` y `DataLoader`.
      - events/: Gestor de eventos y escuchas, utilizando el patrón Observer (`GameEventManager`, `GameEventListener`).
      - services/: Lógica de los diversos servicios que proveen las opciones del juego (`ServicioBatalla`, `ServicioCentroPokemon`, `ServicioExploracion`, `ServicioPCEquipo`, `ServicioTienda`).
      - state/: Patrón State para el estado de los Pokémon (por ejemplo, `EstadoNormal`, `EstadoDebilitado`).
      - Entrenador.java: Representa a un jugador (dinero, inventario, equipo, PC).
      - Pokemon.java: Representa a una criatura individual (hp, nivel, daño, etc.).
      - Movimiento.java: Representa los ataques y habilidades de los Pokémon.
    - items/: Todo lo relacionado con el inventario y manejo de objetos.
      - domain/: Clases que definen el comportamiento de los objetos (`Objeto`, `Pocion`, `Pokeball`, `Revivir`).
      - factory/: Implementación del patrón de diseño Abstract Factory para la creación de objetos (`FabricaObjetos`, etc.).
    - main/:
      - Main.java: Clase principal y punto de entrada de la aplicación.
    - ui/: Interfaz de usuario e interacciones.
      - InterfazConsola.java: Punto de arranque de la interfaz.
      - JuegoFacade.java: Facade que centraliza y simplifica las interacciones del menú.
      - BatallaConsolaView.java: Lógica de la vista para la batalla en consola.
      - ConsolaLogListener.java: Listener de los eventos del juego para imprimir en consola.
  - main/resources/:
    - entrenadores.json, movimientos.json, pokemons.json: Archivos de configuración inicial y datos del juego.