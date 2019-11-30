package Model;

import java.io.Serializable;

public abstract class Character implements Placeable, Serializable {
    public static int Next_ID = 1;
    public static int INITIAL_HEALTH;

    protected volatile int health;
    protected volatile Position position;
    protected String imageName;
    protected double relativeSize;
    protected volatile boolean alive;
    protected final int ID;

    Character(Position position) {
        this.position = position;
        this.alive = true;
        this.ID = Next_ID ++;

    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(getClass())) {
            return false;
        }
        Character character = (Character) obj;
        return (
                health == character.health &
                position.equals(character.position) &
                imageName.equals(character.imageName) &
                relativeSize == character.relativeSize &
                alive == character.alive &
                ID == character.ID
                );
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

    public void setAlive(boolean alive) {
        this.alive = alive;
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
    public String toString() {
        return getClass() + " " + ID + " @@ " + position;
    }
}
