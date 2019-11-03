package Model;

public class Pea implements Placeable {
    private Position position;
    private String imageName;
    private double relativeSize;

    public Pea(Position position) {
        imageName = "Pea.png";
        relativeSize = 0.5;
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
