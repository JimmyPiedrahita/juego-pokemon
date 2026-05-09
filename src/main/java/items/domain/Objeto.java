package items.domain;

import core.Pokemon;

public interface Objeto {
    void usar(Pokemon pokemon);
    String getNombre();
}