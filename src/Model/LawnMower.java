package Model;

import java.util.*;
import java.io.*;

public class LawnMower implements Placeable, Serializable {
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
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(getClass())) {
            return false;
        }

        LawnMower lawnMower = (LawnMower) obj;
        return (
                position.equals(lawnMower.position) &
                relativeSize == lawnMower.relativeSize &
                imageName.equals(lawnMower.imageName) &
                deadImageName.equals(lawnMower.deadImageName) &
                alive == lawnMower.alive &
                mowing == lawnMower.mowing
        );
    }

    @Override
    public String toString() {
        return "Lawnmower @ " + position;
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

    public void move() {
        position.setX(position.getX() + 4);
        if(position.getX() >= 500) {
            mowing = false;
            alive = false;
        }
    }
}
