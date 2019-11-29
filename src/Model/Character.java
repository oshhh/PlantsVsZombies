package Model;

public abstract class Character implements Placeable {
    public static int Next_ID = 1;
    public static final int INITIAL_HEALTH = 100;

    protected volatile int health;
    protected volatile Position position;
    protected String imageName;
    protected String deadImageName;
    protected double relativeSize;
    protected volatile boolean alive;
    protected final int ID;

    Character(Position position) {
        this.health = INITIAL_HEALTH;
        this.position = position;
        this.alive = true;
        this.ID = Next_ID ++;
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

    @Override
    public String toString() {
        return getClass() + " " + ID;
    }
}
