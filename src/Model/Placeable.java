package Model;

import Model.Position;

import java.io.Serializable;

public interface Placeable extends Serializable{
    public Position getPosition();
    public String getImageName();
    public double getRelativeSize();
    public boolean isAlive();
}
