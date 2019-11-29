package Controller;

import Model.Level;
import Model.Placeable;
import javafx.scene.image.ImageView;

import java.util.*;
import java.io.*;

public abstract class PlaceableController extends Thread {
    protected LevelController levelController;
    protected Placeable placeable;
    protected ImageView imageView;

    public PlaceableController(LevelController levelController, Placeable placeable, ImageView imageView) {
        this.levelController = levelController;
        this.placeable = placeable;
        this.imageView = imageView;
    }
}

