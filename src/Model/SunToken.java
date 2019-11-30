package Model;

import javafx.geometry.Pos;

public class SunToken implements Placeable {
    private Position position;
    private String imageName;
    private String deadImageName;
    private double relativeSize;
    private volatile boolean alive;
    private volatile boolean moving;

    public SunToken(Position position, boolean moving) {
        imageName = "SunToken.png";
        deadImageName = imageName;
        relativeSize = 1;
        this.position = position;
        this.moving = moving;
        this.alive = true;
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

    public void move() {
        position.setY(position.getY() + 2);
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public String toString(){
        return "Position of Suntoken : "+position.getX()+" , "+position.getY();
    }
    @Override
    public boolean equals(Object obj){
        if(!obj.getClass().equals(getClass())) {
            return false;
        }
        SunToken sunToken = (SunToken) obj;
        return (position.equals(sunToken.position) && imageName==sunToken.imageName && deadImageName==sunToken.deadImageName && alive==sunToken.alive && moving==sunToken.moving);
    }
}
