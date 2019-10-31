package Model;

import java.util.*;
import java.io.*;

public class LawnMower implements Placeable {
    private Position position;
    private double relativeSize;
    private String imageName;

    public LawnMower(Position position) {
        this.position = position;
        relativeSize = 1;
        imageName = "LawnMower.png";
    }

    @Override
    public String getImageName() {
        return imageName;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public double getRelativeSize() {
        return relativeSize;
    }
}
