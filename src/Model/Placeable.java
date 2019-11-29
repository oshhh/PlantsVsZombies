package Model;

import Model.Position;

public interface Placeable {
    public Position getPosition();
    public String getImageName();
    public String getDeadImageName();
    public double getRelativeSize();
    public boolean isAlive();
}
