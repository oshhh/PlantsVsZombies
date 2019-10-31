package Model;

public abstract class Character implements Placeable {
    protected int health;
    protected Position position;
    protected String imageName;
    protected double relativeSize;

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

    @Override
    public String getImageName() {
        return imageName;
    }

    @Override
    public double getRelativeSize() {
        return relativeSize;
    }
}
