package items.factory;

import items.domain.Objeto;
import items.domain.Pocion;

public class FabricaPociones extends FabricaObjetos {

    @Override
    protected Objeto creaObjeto() {
        return new Pocion();
    }
    
}
