package Model;

public class Pea implements Placeable {
    public static final int PEA_ATTACK_POWER = 10;

    private Position position;
    private String imageName;
    private String deadImageName;
    private double relativeSize;
    private boolean alive;

    public Pea(Position position) {
        imageName = "Pea.png";
        deadImageName = imageName;
        relativeSize = 0.5;
        this.position = position;
        alive = true;
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

    @Override
    public String getDeadImageName() {
        return deadImageName;
    }
}
