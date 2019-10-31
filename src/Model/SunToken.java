package Model;

public class SunToken implements Placeable {
    private Position position;
    private String imageName;
    private double relativeSize;

    public SunToken(Position position) {
        imageName = "SunToken.png";
        relativeSize = 1;
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
}
