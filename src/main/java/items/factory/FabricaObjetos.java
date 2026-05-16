package items.factory;

import items.domain.Objeto;

public abstract class FabricaObjetos {

    protected abstract Objeto creaObjeto();

    public Objeto entregarObjeto() {
        return creaObjeto();
    }

}
