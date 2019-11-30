package Model;

import Model.Plant;
import Model.Position;

import java.util.*;
import java.io.*;

public class WallNut extends Plant  implements Serializable {
    public WallNut(Position position) {
        super(position);
        this.health = 150;
        imageName = "WallNut.gif";
    }
}
