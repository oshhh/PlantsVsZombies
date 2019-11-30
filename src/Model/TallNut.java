package Model;

import Model.Plant;
import Model.Position;

import java.util.*;
import java.io.*;

public class TallNut extends Plant  implements Serializable {
    public TallNut(Position position) {
        super(position);
        this.health = 150;
        imageName = "TallNutPlant.png";
    }
}