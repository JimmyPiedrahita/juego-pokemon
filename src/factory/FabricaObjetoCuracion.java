package factory;

import data.RepositorioDatos;
import core.Objeto;
import core.ObjetoCuracion;

public class FabricaObjetoCuracion extends FabricaObjeto {
    public FabricaObjetoCuracion(RepositorioDatos repo) { super(repo); }

    @Override
    public Objeto crearObjeto(String nombre) {
        ObjetoCuracion obj = new ObjetoCuracion(nombre);
        // Supongamos que tu repositorio devuelve estos datos del JSON
        // Ejemplo: Pocion -> 20 pts, false | Revivir -> 0 pts (cura todo), true
        if (nombre.equalsIgnoreCase("Pocion")) {
            obj.setDatos(20, false);
            obj.setDescripcion("Restaura 20 puntos de salud.");
        } else if (nombre.equalsIgnoreCase("Revivir")) {
            obj.setDatos(0, true);
            obj.setDescripcion("Reanima a un Pokemon debilitado.");
        }
        return obj;
    }
}