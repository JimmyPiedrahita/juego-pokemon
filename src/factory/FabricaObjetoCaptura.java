package factory;

import data.RepositorioDatos;
import core.Objeto;
import core.ObjetoCaptura;

public class FabricaObjetoCaptura extends FabricaObjeto {
    public FabricaObjetoCaptura(RepositorioDatos repo) { super(repo); }

    @Override
    public Objeto crearObjeto(String nombre) {
        ObjetoCaptura obj = new ObjetoCaptura(nombre);
        if (nombre.equalsIgnoreCase("Pokeball")) {
            obj.setRatio(1.0);
            obj.setDescripcion("Objeto para capturar Pokemon salvajes.");
        }
        return obj;
    }
}
