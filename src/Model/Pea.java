package Model;

public class Pea implements Placeable {
    public static final int PEA_ATTACK_POWER = 10;

    private volatile Position position;
    private String imageName;
    private String deadImageName;
    private double relativeSize;
    private volatile boolean alive;

    public Pea(Position position) {
        imageName = "Pea.png";
        deadImageName = imageName;
        relativeSize = 0.5;
        this.position = position;
        alive = true;
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(getClass())) {
            return false;
        }

        Pea pea = (Pea) obj;
        return (
                imageName.equals(pea.imageName) &
                deadImageName.equals(pea.deadImageName) &
                relativeSize == pea.relativeSize &
                position.equals(pea.position) &
                alive == pea.alive
        );
    }

    @Override
    public String toString() {
        return "Pea @ " + position;
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

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void move() {
        position.setX(position.getX() + 4);
    }
}
