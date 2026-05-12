package core;

public class Movimiento {
    private String nombre;
    private int potencia;
    private String tipo;

    public Movimiento(String nombre, int potencia, String tipo) {
        this.nombre = nombre;
        this.potencia = potencia;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPotencia() {
        return potencia;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return nombre + " (Poder: " + potencia + ", Tipo: " + tipo + ")";
    }
}
