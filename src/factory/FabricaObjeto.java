package factory;

import data.RepositorioDatos;
import core.Objeto;

public abstract class FabricaObjeto {
    protected RepositorioDatos repositorio;

    public FabricaObjeto(RepositorioDatos repo) {
        this.repositorio = repo;
    }

    public abstract Objeto crearObjeto(String nombre);
}
