package Model;

public class SunToken implements Placeable {
    private Position position;
    private String imageName;
    private String deadImageName;
    private double relativeSize;
    private volatile boolean alive;

    public SunToken(Position position) {
        imageName = "SunToken.png";
        deadImageName = imageName;
        relativeSize = 0.7;
        this.position = position;

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
