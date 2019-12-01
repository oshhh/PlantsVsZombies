package Model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.*;
import java.io.*;

public class PlantPanel implements Serializable {
    private static HashMap<Class, String> imageName;

    static {
        imageName = new HashMap<Class, String>();
        imageName.put(PeaShooter.class, "PeaShooter.png");
        imageName.put(SunFlower.class, "SunFlower.png");
        imageName.put(WallNut.class, "WallNut.png");
        imageName.put(CherryBomb.class, "CherryBomb.png");
        imageName.put(TallNut.class, "TallNut.png");
    }

    public static String getImageName(Class plant) {
        return imageName.get(plant);
    }

    private static HashMap<Class, String> name;
    static {
        name = new HashMap<Class, String>();
        name.put(PeaShooter.class, "PeaShooter");
        name.put(SunFlower.class, "SunFlower");
        name.put(WallNut.class, "WallNut");
        name.put(CherryBomb.class, "CherryBomb");
        name.put(TallNut.class, "TallNut");
    }

    public static String getName(Class plant) {
        return name.get(plant);
    }

    private static HashMap<Class, Integer> price;
    static {
        price = new HashMap<Class, Integer>();
        price.put(PeaShooter.class, 100);
        price.put(SunFlower.class, 50);
        price.put(WallNut.class, 50);
        price.put(CherryBomb.class, 150);
    }

    public static Integer getPrice(Class plant) {
        return price.get(plant);
    }



    private ArrayList<Class> availablePlants;
    private boolean selected;
    private Class selectedPlant;

    private HashMap<Class, Boolean> plantSelected;
    private HashMap<Class, Boolean> plantDisabled;
    private HashMap<Class, Long> lastPlaced;
    private transient HashMap<Class, AnchorPane> anchorPaneHashMap;

    public PlantPanel(ArrayList<Class> availablePlants) {
        this.availablePlants = availablePlants;
        this.selected = false;
        this.selectedPlant = null;
        plantSelected = new HashMap<Class, Boolean>();
        plantDisabled = new HashMap<Class, Boolean>();
        lastPlaced = new HashMap<Class, Long>();

        for(Class plant : availablePlants) {
            plantSelected.put(plant, false);
            plantDisabled.put(plant, true);
            lastPlaced.put(plant, System.currentTimeMillis());
        }
    }

    public void selectPlant(Class plant) {
        selectedPlant = plant;
        selected = true;
        for(Map.Entry<Class, Boolean> e: plantSelected.entrySet()) {
            plantSelected.put(e.getKey(), e.getKey().equals(plant));
        }
    }

    public void placePlant() {
        plantSelected.put(selectedPlant, false);
        lastPlaced.put(selectedPlant, System.currentTimeMillis());
        selectedPlant = null;
    }

    public ArrayList<Class> getAvailablePlants() {
        return availablePlants;
    }

    public boolean isPlantDisabled(Class plant) {
        return plantDisabled.get(plant);
    }

    public boolean isPlantSelected(Class plant) {
        return plantSelected.get(plant);
    }

    public long plantLastPlaced(Class plant) {
        return lastPlaced.get(plant);
    }

    public void setAnchorPaneHashMap(HashMap<Class, AnchorPane> anchorPaneHashMap) {
        this.anchorPaneHashMap = anchorPaneHashMap;
    }

    public HashMap<Class, AnchorPane> getAnchorPaneHashMap() {
        return anchorPaneHashMap;
    }

    public HashMap<Class, Boolean> getPlantDisabled() {
        return plantDisabled;
    }

    public boolean isSelected() {
        return selected;
    }

    public Class getSelectedPlant() {
        return selectedPlant;
    }
}
