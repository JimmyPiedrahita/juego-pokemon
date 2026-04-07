package items.factory;

import items.domain.Objeto;
import items.domain.Revivir;

public class FabricaRevivir extends FabricaObjetos{

    @Override
    protected Objeto creaObjeto() {
        return new Revivir();
    }

}
