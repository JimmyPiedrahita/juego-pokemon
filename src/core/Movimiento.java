package core;

public class Movimiento {
    private String nombre;
    private int poder;
    private String tipo;

    // Constructor vacío (Muy importante para que librerías como Gson o Jackson puedan leer el JSON)
    public Movimiento() {
    }

    // Constructor con parámetros
    public Movimiento(String nombre, int poder, String tipo) {
        this.nombre = nombre;
        this.poder = poder;
        this.tipo = tipo;
    }

    // Getters para usar los ataques en las batallas
    public String getNombre() {
        return nombre;
    }

    public int getPoder() {
        return poder;
    }

    public String getTipo() {
        return tipo;
    }

    // Setters (opcionales, útiles si los modificas en tiempo de ejecución)
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Un toString para imprimir el ataque fácilmente en consola
    @Override
    public String toString() {
        return nombre + " (Poder: " + poder + " | Tipo: " + tipo + ")";
    }
}