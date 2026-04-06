package core;

public class EvolucionData {
    private String nombreEvolucion;
    private int nivelRequerido;

    public EvolucionData(String nombreEvolucion, int nivelRequerido) {
        this.nombreEvolucion = nombreEvolucion;
        this.nivelRequerido = nivelRequerido;
    }

    public String getNombreEvolucion() { return nombreEvolucion; }
    public int getNivelRequerido() { return nivelRequerido; }
}