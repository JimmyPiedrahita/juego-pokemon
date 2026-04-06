package core;

public abstract class Objeto {
    protected String nombre;
    protected String descripcion;

    public Objeto(String nombre) {
        this.nombre = nombre;
    }

    public abstract void usar(Pokemon objetivo);

    // Getters y Setters
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getNombre() { return nombre; }
}
