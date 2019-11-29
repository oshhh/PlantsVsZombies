package Model;

import java.io.Serializable;

public abstract class Plant extends Character implements Serializable {
    public Plant(Position position) {
        super(position);
        relativeSize = 1;
    }

}
