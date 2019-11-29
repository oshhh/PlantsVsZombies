package Model;

import java.util.*;
import java.io.*;

public class LawnMower implements Placeable {
    private Position position;
    private double relativeSize;
    private String imageName;
    private boolean alive;
    private boolean mowing;

    public LawnMower(Position position) {
        this.position = position;
        relativeSize = 1.3;
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

    public boolean isMowing() {
        return mowing;
    }

    public void setMowing(boolean mowing) {
        this.mowing = mowing;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }
}
