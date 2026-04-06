package core;

public class ObjetoCaptura extends Objeto {
    private double ratioCaptura;

    public ObjetoCaptura(String nombre) {
        super(nombre);
    }

    public void setRatio(double ratio) {
        this.ratioCaptura = ratio;
    }

    @Override
    public void usar(Pokemon p) {
        System.out.println("Lanzando " + nombre + " a " + p.getNombre() + " (Ratio: " + ratioCaptura + ")");
    }
}