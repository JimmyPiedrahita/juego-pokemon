# Juego de Pokemon

## Como jugar

Al iniciar el juego se te pedira que ingrese un nombre para el entrenador
Por defecto se te dara 3 pokeballs y $200
El menu principal tendra 6 opciones 

1. Equipo: ver todos los pokemon que tienes
2. Batalla: genera un entrenador rival con pokemon aleatorios para enfrentar
3. Centro Pokemon: Cura todos tus pokemon, se te cobrara dinero
4. Tienda: Podras comprar pokeballs, pociones, o revivir
5. Explorar: podras explorar y elejir si lanzar una pokeball y si tienes suerte capturar un pokemon
6. Salir

Como al principio el entrenador no tiene pokemon deberia explorar y capturar pokemon y si se le acaban las
pokeball comprar en la tienda, con al menos un pokemon podras ir a batalla, tambien deberias comprar una pocion para utilizar en batalla, y con esto lograr una victoria y ganar dinero con el que podras curar tu pokemon y comprar mas pokeballs y capturar mas pokemon y asi formar un gran equipo.


## Estructura del proyecto
- src/: Es la carpeta principal del código fuente.
  - Main.java: Clase principal y punto de entrada de la aplicación.
  - battle/: Lógica de los combates.
    - MotorBatalla.java: Controlador que orquesta los combates (turnos, daños, recompensas).
  - core/: Entidades principales y modelos de negocio.
    - Entrenador.java: Representa a un jugador (dinero, inventario, equipo).
    - Pokemon.java: Representa a una criatura individual (hp, nivel, daño).
  - items/: Todo lo relacionado con el inventario y manejo de objetos.
    - domain/: Clases que definen el comportamiento de los objetos.
      - Objeto.java: Clase base para los objetos.
      - Pocion.java: Objeto para curar salud.
      - Pokeball.java: Objeto para capturar Pokémon.
      - Revivir.java: Objeto para curar un Pokémon debilitado.
    - factory/: Implementación del patrón de diseño Abstract Factory para la creación de objetos.
      - FabricaObjetos.java: Interfaz general o clase abstracta constructora.
      - FabricaPociones.java: Fábrica específica para pociones.
      - FabricaPokeball.java: Fábrica específica para pokeballs.
      - FabricaRevivir.java: Fábrica específica para revivir.
  - ui/: Interfaz de usuario e interacciones.
    - InterfazConsola.java: Maneja los menús, entradas y salidas de texto en la terminal.


## Análisis de Calidad Estática (SonarQube)

Este proyecto está configurado para someterse a auditorías de calidad de código utilizando SonarQube a través de Maven. Por razones de seguridad (prevención de fugas de credenciales), la configuración de autenticación no está expuesta en el archivo `pom.xml`.

Para ejecutar el análisis en tu entorno local, sigue estos pasos:

### 1. Levantar la Infraestructura Local
Asegúrate de tener Docker instalado y ejecuta el siguiente contenedor para inicializar un servidor SonarQube aislado:

```bash
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest