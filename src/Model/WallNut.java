package Model;

import Model.Plant;
import Model.Position;

import java.util.*;
import java.io.*;

public class WallNut extends Plant  implements Serializable {
    public WallNut(Position position) {
        super(position);
        imageName = "WallNut.gif";
    }
}
