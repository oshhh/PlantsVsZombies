package Model;

public abstract class Character implements Placeable {
    public static final int INITIAL_HEALTH = 100;

    protected int health;
    protected Position position;
    protected String imageName;
    protected String deadImageName;
    protected double relativeSize;
    protected boolean alive;

    Character(Position position) {
        this.health = INITIAL_HEALTH;
        this.position = position;
    }

    public void changeHealth(int health) {
        this.health += health;
        if(this.health <= 0) {
            this.health = 0;
            this.alive = false;
        }
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

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public String getDeadImageName() {
        return deadImageName;
    }
}
