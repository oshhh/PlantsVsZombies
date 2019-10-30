package Model;

public abstract class Character {
    protected int health;
    Position position;

    Character(Position position) {
        this.health = 0;
        this.position = position;
    }

    public void changeHealth(int health) {
        this.health += health;
    }

    public int getHealth() {
        return health;
    }

    public Position getPosition() {
        return position;
    }
}
