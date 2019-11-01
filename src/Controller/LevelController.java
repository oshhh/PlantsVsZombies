package Controller;
import Model.*;

import Model.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class LevelController {

    private Level level;
    private Player player;
    private Scene scene;
    private static AnchorPane currentPanel;
    private boolean isPlantPicked = true;
    private String plantName;
    private static final int NUMBER_OF_COLUMNS = 9;
    private static final int GRID_BLOCK_SIZE = 34;
    private static final int ROW_OFFSET = 5;
    private static final int COLUMN_OFFSET = 4;
    private static ArrayList<RegularActionShootPeas> PeaShooterActions = new ArrayList<RegularActionShootPeas>();

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
    public void startShootingPeas(){
        RegularActionShootPeas regularActionShootPeas = new RegularActionShootPeas();
        regularActionShootPeas.start();
//        PeaShooterActions.add(regularActionShootPeas);
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
                GridPane.setRowIndex(imageView, row + ROW_OFFSET - level.NUMBER_OF_ROWS/2);
                GridPane.setColumnIndex(imageView, column + COLUMN_OFFSET);
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

            // Put LawnMower
            LawnMower lawnMower = new LawnMower(new Position(row + ROW_OFFSET - level.NUMBER_OF_ROWS/2, COLUMN_OFFSET - 1));
            level.addLawnMower(lawnMower);
            ImageView imageView = getImageView(lawnMower);
            imageView.setTranslateX(-15);

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
            int col=random.nextInt(7) + COLUMN_OFFSET;

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
            int row = random.nextInt(level.NUMBER_OF_ROWS)+ ROW_OFFSET - level.NUMBER_OF_ROWS/2;
            int column = COLUMN_OFFSET + NUMBER_OF_COLUMNS + random.nextInt(5);
            Position position = new Position(row, column);
            Zombie zombie = new Zombie(position);
            level.addZombie(zombie);

            // Add to UI
            ImageView imageView = getImageView(zombie);

            // Set Translation
            imageView.setTranslateY(-10);
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.seconds(30));
            translateTransition.setByX( - (column - 3) * GRID_BLOCK_SIZE );
//            translateTransition.setToX(-350);
            translateTransition.setNode(imageView);
            translateTransition.play();
        }

    }
    public static class CurrentPlantPosition{
        public static Position CurrentPlantPosition;
        public void CurrentPlant(Position plant){
            CurrentPlantPosition = plant;
        }
    }
    class ShootPeas implements Runnable{
        @Override
        public void run(){
            Position position = new Position(CurrentPlantPosition.CurrentPlantPosition.getX(),CurrentPlantPosition.CurrentPlantPosition.getY());
            Pea pea = new Pea(position);
            pea.setRelativeSize(0.5);
            ImageView imageView = getImageView(pea);
            imageView.setTranslateX(20);
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.seconds(7));
            translateTransition.setToX(scene.getWidth());
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
        Position position = new Position(row + ROW_OFFSET - level.getLEVEL(), column + COLUMN_OFFSET);
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
        switch (plantName) {
            case "PeaShooter":
                CurrentPlantPosition.CurrentPlantPosition=plant.getPosition();
                startShootingPeas();
        }

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

                    System.out.println(level.getGame().getScore().getSunPower());

                } catch (InterruptedException e) {}
             }
        }
    }
    class RegularActionShootPeas extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    ShootPeas shootPeas = new ShootPeas();
                    Platform.runLater(shootPeas);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }

            }
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Game game = level.getGame();

        game.resetLevel(level.getLEVEL());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/GameGUI.fxml"));
        Parent view = fxmlLoader.load();
        GameController controller = (GameController) fxmlLoader.getController();
        controller.setGame(game);
        Scene viewScene = new Scene(view,600, 300);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }

}