package core;

public class ObjetoCuracion extends Objeto {
    private int puntosCuracion;
    private boolean puedeRevivir;

    public ObjetoCuracion(String nombre) {
        super(nombre);
    }

    public void setDatos(int puntos, boolean revivir) {
        this.puntosCuracion = puntos;
        this.puedeRevivir = revivir;
    }

    @Override
    public void usar(Pokemon p) {
        if (puedeRevivir) {
            System.out.println("Usando " + nombre + " en " + p.getNombre() + ". ¡Ha vuelto a la vida!");
        } else {
            System.out.println("Usando " + nombre + " en " + p.getNombre() + ". Curados " + puntosCuracion + " HP.");
        }
    }
}