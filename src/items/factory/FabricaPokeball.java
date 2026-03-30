package items.factory;

import items.domain.Objeto;
import items.domain.Pokeball;

public class FabricaPokeball extends FabricaObjetos{

    @Override
    protected Objeto creaObjeto() {
        return new Pokeball();
    }

}
