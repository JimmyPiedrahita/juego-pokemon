import core.Objeto;
import core.Pokemon;
import data.GestorDocumento;
import data.RepositorioDatos;
import factory.FabricaObjeto;
import factory.FabricaObjetoCaptura;
import factory.FabricaObjetoCuracion;
import factory.FabricaPokemon;
import factory.FabricaPokemonAgua;
import factory.FabricaPokemonFuego;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("=== INICIANDO EL JUEGO ===\n");

        // 1. INICIALIZAR LA BASE DE DATOS
        // Creamos el gestor que leerá el JSON. Es el único que sabe de archivos.
        RepositorioDatos repositorio = new GestorDocumento("pokemon_data.json");


        // 2. CONFIGURAR LAS FÁBRICAS
        // Creamos las fábricas y les entregamos el repositorio para que tengan los datos.
        System.out.println("--- Configurando las Fábricas ---");
        FabricaPokemon fabricaFuego = new FabricaPokemonFuego(repositorio);
        FabricaPokemon fabricaAgua = new FabricaPokemonAgua(repositorio);
        
        FabricaObjeto fabricaCuracion = new FabricaObjetoCuracion(repositorio);
        FabricaObjeto fabricaCaptura = new FabricaObjetoCaptura(repositorio);
        System.out.println("¡Fábricas listas!\n");


        // 3. CREAR POKÉMON
        // Pedimos a las fábricas que nos ensamblen los personajes.
        System.out.println("--- Creando Pokémon ---");
        Pokemon miCharmander = fabricaFuego.crearPokemon("Charmander", 5);
        Pokemon miSquirtle = fabricaAgua.crearPokemon("Squirtle", 5);
        
        // Vemos cómo nacieron
        miCharmander.mostrarEstado();
        miSquirtle.mostrarEstado();
        System.out.println();


        // 4. SIMULAR SUBIDA DE NIVEL Y EVOLUCIÓN
        System.out.println("--- Entrenamiento ---");
        // Supongamos que Charmander evoluciona en el nivel 16. Vamos a subirlo de golpe.
        for (int i = 0; i < 11; i++) {
            miCharmander.subirNivel(); 
        }
        
        System.out.println("\nEstado después de entrenar:");
        miCharmander.mostrarEstado();
        System.out.println();


        // 5. CREAR Y USAR OBJETOS
        System.out.println("--- Inventario y Objetos ---");
        Objeto miPocion = fabricaCuracion.crearObjeto("Pocion");
        Objeto miPokeball = fabricaCaptura.crearObjeto("Pokeball");

        // Usamos la poción en Charmander (ahora Charmeleon si el JSON tenía el nivel de evolución)
        miPocion.usar(miCharmander);
        
        // Intentamos atrapar a Squirtle
        miPokeball.usar(miSquirtle);
        
        System.out.println("\n=== FIN DEL JUEGO ===");
    }
}
