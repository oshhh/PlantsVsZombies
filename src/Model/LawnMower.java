package Model;

import java.util.*;
import java.io.*;

public class LawnMower implements Placeable {
    private volatile Position position;
    private double relativeSize;
    private String imageName;
    private String deadImageName;
    private volatile boolean alive;
    private volatile boolean mowing;

    public LawnMower(Position position) {
        this.position = position;
        relativeSize = 1.3;
        imageName = "LawnMower.png";
        deadImageName = "LawnMower.png";
        alive = true;
        mowing = false;
    }

    @Override
    public String getImageName() {
        return imageName;
    }

    @Override
    public String getDeadImageName() {
        return deadImageName;
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

    public void move() {
        position.setX(position.getX() + 4);
        if(position.getX() >= 500) {
            mowing = false;
        }
    }
}
