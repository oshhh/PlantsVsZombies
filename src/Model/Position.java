package Model;

import java.io.Serializable;

public class Position implements Serializable {
    private volatile int x;
    private volatile int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(getClass())) {
            return false;
        }

        Position position = (Position) obj;
        return (
                x == position.x &
                y == position.y
                );
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
