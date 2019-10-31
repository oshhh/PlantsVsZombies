package Controller;
import Model.*;

import Model.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;


public class LevelController {

    Level level;
    Player player;
    Scene scene;
    private static AnchorPane currentPanel;
//    StickyPlant stickyPlant;
    private boolean isPlantPicked = true;
    private String plantName;
    private static final int NUMBER_OF_COLUMNS = 7;
    private static final int GRID_BLOCK_SIZE = 50;

    public static void setCurrentPanel(AnchorPane currentPanel) {
        LevelController.currentPanel = currentPanel;
    }

    public static AnchorPane getCurrentPanel() {
        return currentPanel;
    }

    public void setLevel(Level level) {
        this.level = level;
        this.player = level.getPlayer();
    }
    public void setScene(Scene scene){
        this.scene = scene;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantName() {
        return plantName;
    }

    public void createLevel() throws IOException {
        createPlantPanel();
        layGrass();

        RegularAction regularAction = new RegularAction();
        regularAction.start();
    }
    public void setDropShadow(AnchorPane anchorPane, Color color){
        int depth = 50;
        DropShadow borderGlow= new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(color);
        borderGlow.setWidth(depth);
        borderGlow.setHeight(depth);
        anchorPane.setEffect(borderGlow);
//        return anchorPane;
    }
    public void createPlantPanel() throws IOException {
        // Create Plant panel

        for (String plant:  level.getAvailablePlants()) {
            // Load plant panel
//            FileInputStream fileInputStream = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Assets/" + plant + ".png");
            FileInputStream fileInputStream = new FileInputStream("/home/isha/PlantsZombies/src/Assets/" + plant + ".png");
            Image plantImage = new Image(fileInputStream);
            ImageView plantImageView = new ImageView();
            plantImageView.setFitWidth(60);
            plantImageView.setFitHeight(60);
            plantImageView.setImage(plantImage);

            AnchorPane plantPanel = (AnchorPane) scene.lookup("#plantPanel"+plant);

            plantPanel.getChildren().add(plantImageView);
            plantPanel.onMouseClickedProperty().setValue(e->{
//                setDropShadow(plantPane,Color.RED);
//                int depth = 50;
//                DropShadow borderGlow= new DropShadow();
//                borderGlow.setOffsetY(0f);
//                borderGlow.setOffsetX(0f);
//                borderGlow.setColor(Color.RED);
//                borderGlow.setWidth(depth);
//                borderGlow.setHeight(depth);
//                plantPanel.setEffect(borderGlow);
                Color color=Color.rgb(255, 204, 0);
                setDropShadow(plantPanel,color);
                setCurrentPanel(plantPanel);
                setPlantPicked(true);
                setPlantName(plant);
            });
        }

    }

    //todo
    public void layGrass() throws IOException {
//        FileInputStream grassInput = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Assets/grass.jpg");
        FileInputStream grassInput = new FileInputStream("/home/isha/PlantsZombies/src/Assets/grass.jpg");
        Image grass1Image = new Image(grassInput);
//        FileInputStream grass2Input = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Assets/grass2.jpg");
        FileInputStream grass2Input = new FileInputStream("/home/isha/PlantsZombies/src/Assets/grass2.jpg");
        Image grass2Image = new Image(grass2Input);

        GridPane gridPane = (GridPane) scene.lookup("#grid");

        // Put grass and zombie in each row
        for(int i = 0; i < level.NUMBER_OF_ROWS; i ++) {
            final int row = i;

            // Lay grass for row
            for(int j = 0; j < NUMBER_OF_COLUMNS; j ++) {
                final int column = j;
                ImageView imageView = new ImageView();
                imageView.onMouseClickedProperty().setValue(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try{
                            placePlant(mouseEvent,row,column);}
                        catch (IOException e){
                            System.out.println(e.getMessage());
                        }
                    }
                });
                GridPane.setRowIndex(imageView, row + 3 - level.getLEVEL());
                GridPane.setColumnIndex(imageView, column + 3);
                imageView.setFitWidth(GRID_BLOCK_SIZE);
                imageView.setFitHeight(GRID_BLOCK_SIZE);

                if (((i+j)%2) == 0) {
                    imageView.setImage(grass1Image);
                }
                else{
                    imageView.setImage(grass2Image);
                }
                gridPane.getChildren().add(imageView);
            }

        }
    }

    public void setPlantPicked(boolean plantPicked) {
        isPlantPicked = plantPicked;
    }

    public ImageView getImageView(Placeable placeable) {
        try {
            GridPane grid = (GridPane) (scene.lookup("#grid"));
//            FileInputStream inputStream = new FileInputStream("/Users/osheensachdev/Documents/GitHub/PlantsVsZombies/src/Assets/" + placeable.getImageName());
            FileInputStream inputStream = new FileInputStream("/home/isha/PlantsZombies/src/Assets/" + placeable.getImageName());
            Image image = new Image(inputStream);
            ImageView imageView = new ImageView();
            imageView.setFitWidth(placeable.getRelativeSize() * GRID_BLOCK_SIZE);
            imageView.setFitHeight(placeable.getRelativeSize() * GRID_BLOCK_SIZE);

            GridPane.setRowIndex(imageView, placeable.getPosition().getX() );
            GridPane.setColumnIndex(imageView, placeable.getPosition().getY());
            imageView.setImage(image);
            grid.getChildren().add(imageView);

            return imageView;

        } catch (IOException e) {}

        return null;
    }

    // Runnable that generates sun when run
    class GenerateSun implements Runnable {

        @Override
        public void run() {
            Random random = new Random();
            int col=random.nextInt(7)+3;

            ImageView imageView = getImageView(new SunToken(new Position(0, col)));

            // Add listener
            imageView.setOnMouseClicked(mouseEvent -> {
                level.collectSun();
                GridPane gridPane = (GridPane) (scene.lookup("#grid"));
                gridPane.getChildren().remove(imageView);
            });

            // Add transition
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.seconds(15));
            translateTransition.setToY(imageView.getY() + 220);
            translateTransition.setNode(imageView);
            translateTransition.play();
        }

    }

    // Runnable that generates sun when run
    class GenerateZombie implements Runnable {

        @Override
        public void run() {
            // Make Zombie and add to level
            Random random = new Random();
            int row = random.nextInt(level.NUMBER_OF_ROWS)+ 3 - level.getLEVEL();
            int column = 11;
            Position position = new Position(row, column);
            Zombie zombie = new Zombie(position);
            level.addZombie(zombie);

            // Add to UI
            ImageView imageView = getImageView(zombie);

            // Set Translation
            imageView.setTranslateY(-10);
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.seconds(20));
            translateTransition.setToX(imageView.getX() - 350);
            translateTransition.setNode(imageView);
            translateTransition.play();
        }

    }

    public void placePlant(javafx.scene.input.MouseEvent mouseEvent, int row, int column) throws IOException{
        if (!isPlantPicked){
            return;
        }
        if (currentPanel!=null){
            setDropShadow(currentPanel,Color.WHITE);
        }
        isPlantPicked=false;
        System.out.println(plantName + " level controller");

        Plant plant = null;
        Position position = new Position(row + 3 - level.getLEVEL(), column + 3);
        switch (plantName) {
            case "PeaShooter":
                plant = new PeaShooter(position);
                break;
            case "SunFlower":
                plant = new SunFlower(position);
                break;
            case "WallNut":
                plant = new WallNut(position);
                break;
            case "CherryBomb":
                plant = new CherryBomb(position);
                break;
        }

        level.addPlant(plant);

        ImageView plantImageView = getImageView(plant);

    }

    // Thread that is running all the time to execute regular action
    class RegularAction extends Thread {
        @Override
        public void run() {
            while (true) {
                try {

                    // We can't change UI in any other thread except JavaFX thread
                    // We need to add all UI changes to platform.runLater()
                    // which will run those UI changing threads in the JavaFX thread

                    // Run thread that generates zombie
                    GenerateZombie generateZombie = new GenerateZombie();
                    Platform.runLater(generateZombie);

                    Thread.sleep(3000);

                    // Run thread that generates sun
                    GenerateSun generateSun = new GenerateSun();
                    Platform.runLater(generateSun);

                    Thread.sleep(2000);

                } catch (InterruptedException e) {}
             }
        }
    }
}