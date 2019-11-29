package Model;

public class SunToken implements Placeable {
    private Position position;
    private String imageName;
    private double relativeSize;
    private boolean alive;

    public SunToken(Position position) {
        imageName = "SunToken.png";
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
}
