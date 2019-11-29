package Model;

import Model.Plant;
import Model.Position;
import javafx.geometry.Pos;

import java.util.*;
import java.io.*;

public class PeaShooter extends Plant {
    public PeaShooter(Position position) {
        super(position);
        imageName = "PeaShooter.gif";
        deadImageName = imageName;

    }
}
