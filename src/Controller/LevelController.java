package Controller;
import Model.*;

import Model.*;
import javafx.animation.Animation;
import javafx.scene.control.ProgressBar;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Map;
import java.util.Random;


public class LevelController {

    public static final int NUMBER_OF_COLUMNS = 8;
    public static final int GRID_BLOCK_SIZE = 35;
    public static final int ROW_OFFSET = 2;
    public static final int COLUMN_OFFSET = 1;
    public static final int GRID_X_OFFSET = 100;
    public static final int GRID_Y_OFFSET = 70;
    public static final int SKY_ROW = -2;
    public static final int GROUND_ROW = 4;
    public static final int COLLISION_RADIUS = 20;
    public static final int ANIMATION_TIMEGAP = 50;
    public static final int NEXT_PURCHASE_TIME = 5000;

    private Level level;
    private Player player;
    private Scene scene;
    private static AnchorPane currentPanel;
    private volatile boolean isPlantPicked;
    private String plantName;
    private volatile boolean pause;

    public LevelController() {
        isPlantPicked = true;
        pause = false;
    }

    public static Position getPosition(int row, int column) {
        return new Position(GRID_X_OFFSET + GRID_BLOCK_SIZE * column, GRID_Y_OFFSET + GRID_BLOCK_SIZE * row);
    }

    public static Position getPositionGrid(Position position){
        return new Position((position.getY()-GRID_Y_OFFSET)/GRID_BLOCK_SIZE,(position.getX()-GRID_X_OFFSET)/GRID_BLOCK_SIZE);
    }

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


    public void setImageView(Placeable placeable, ImageView imageView) {
        Image image = new Image("Assets/"+placeable.getImageName());
        imageView.setFitWidth(placeable.getRelativeSize() * GRID_BLOCK_SIZE);
        imageView.setFitHeight(placeable.getRelativeSize() * GRID_BLOCK_SIZE);
        imageView.setImage(image);
    }
    public ImageView placeInGrid(Placeable placeable) {
        ImageView imageView = new ImageView();
        setImageView(placeable, imageView);
        GridPane grid = (GridPane) (scene.lookup("#grid"));
        Position gridPosition = getPositionGrid(placeable.getPosition());
        GridPane.setRowIndex(imageView, gridPosition.getX() );
        GridPane.setColumnIndex(imageView, gridPosition.getY());
        grid.getChildren().add(imageView);

        return imageView;
    }
    public ImageView placeInAnchor(Placeable placeable) {
        ImageView imageView = new ImageView();
        setImageView(placeable, imageView);
        AnchorPane grid = (AnchorPane) (scene.lookup("#mainPane"));
        AnchorPane.setTopAnchor(imageView, (double)placeable.getPosition().getY());
        AnchorPane.setLeftAnchor(imageView, (double)placeable.getPosition().getX());
        grid.getChildren().add(imageView);

        return imageView;
    }
    public ImageView getImageView(String imageName, int width, int height){
        ImageView imageView = new ImageView();

        Image image = new Image("Assets/"+imageName);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setImage(image);

        return imageView;
    }



    public void setUpLevel() throws IOException {
        level.setRunning(true);
        createPlantPanel();
        createTopBar();
        layGrass();
        setUpMenus();
        placePlaceables();

        RegularAction regularAction = new RegularAction(this);
        regularAction.start();

        DetectCollision detectCollision = new DetectCollision(this);
        detectCollision.setPriority(1);
        detectCollision.start();
    }

    public void placePlaceables() {
        synchronized (level.getPlants()) {
            for(Placeable placeable : level.getPlants()) {
                ImageView imageView = placeInGrid(placeable);
                PlantController plantController = new PlantController(this, (Plant) placeable, imageView);
                plantController.start();
            }
        }

        synchronized (level.getZombies()) {
            for(Placeable placeable : level.getZombies()) {
                ImageView imageView = placeInAnchor(placeable);
                ZombieController zombieController = new ZombieController(this, (Zombie) placeable, imageView);
                zombieController.start();
            }
        }

        synchronized (level.getPeas()) {
            for(Placeable placeable : level.getPeas()) {
                ImageView imageView = placeInAnchor(placeable);
                PeaController peaController = new PeaController(this, (Pea) placeable, imageView);
                peaController.start();
            }
        }

        synchronized (level.getSunTokens()) {
            for(Placeable placeable : level.getSunTokens()) {
                ImageView imageView = placeInAnchor(placeable);
                SunTokenController sunTokenController = new SunTokenController(this, (SunToken) placeable, imageView);
                sunTokenController.start();
            }
        }

        synchronized (level.getLawnMowers()) {
            for(Placeable placeable : level.getLawnMowers()) {
                System.out.println("lawnmower");
                ImageView imageView = placeInAnchor(placeable);
                LawnMowerController lawnMowerController = new LawnMowerController(this, (LawnMower) placeable, imageView);
                lawnMowerController.start();
            }
        }

    }

    public void setUpMenus() {
        scene.lookup("#menu").setVisible(false);
        scene.lookup("#menu").setDisable(true);
        scene.lookup("#gameOverMenu").setVisible(false);
        scene.lookup("#gameOverMenu").setDisable(true);
        scene.lookup("#gameWinnerMenu").setVisible(false);
        scene.lookup("#gameWinnerMenu").setDisable(true);

    }

    public void endLevel() {
        level.setRunning(false);
    }
    public void createPlantPanel() throws IOException {
        // Create Plant panel
        for (Map.Entry<String, Long> plant:  level.getAvailablePlants().entrySet()) {
            // Load plant panel
            Image plantImage = new Image("Assets/" + plant.getKey() + ".png");
            ImageView plantImageView = new ImageView();
            plantImageView.setFitWidth(60);
            plantImageView.setFitHeight(60);
            plantImageView.setImage(plantImage);

            AnchorPane plantPanel = (AnchorPane) scene.lookup("#plantPanel"+plant.getKey());

//            System.out.println(plantPanel + " " + plantImage);
            plantPanel.getChildren().add(plantImageView);

            plantPanel.onMouseClickedProperty().setValue(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(System.currentTimeMillis() - plant.getValue() < NEXT_PURCHASE_TIME) return;
                    Color color=Color.rgb(255, 204, 0);
                    setDropShadow(plantPanel,color);
                    setCurrentPanel(plantPanel);
                    setPlantPicked(true);
                    setPlantName(plant.getKey());
                    level.getAvailablePlants().put(plant.getKey(), System.currentTimeMillis());
                }
            });
        }
    }


    public void createTopBar() {
        // SunToken logo beside SunToken Points Count
        AnchorPane anchorPane = (AnchorPane) scene.lookup("#SunTokenLogo");
        ImageView imageView = getImageView("SunToken.png",40,40);
        anchorPane.getChildren().add(imageView);
        // Zombie Head beside progress bar
        imageView = getImageView("ZombieHead.png",40,40);
        anchorPane = (AnchorPane) scene.lookup("#ZombieLogo");
        anchorPane.getChildren().add(imageView);
        // Update Progress Bar
        UpdateTimer updateTimer = new UpdateTimer();
        updateTimer.start();
        // Suntoken points
        setSunScore();
    }
    public void layGrass() {
        GridPane gridPane = (GridPane) scene.lookup("#grid");

        // Put grass and zombie in each row
        for(int i = 0; i < level.NUMBER_OF_ROWS; i ++) {
            final int row = i;
            // Lay grass for row
            for(int j = 0; j < NUMBER_OF_COLUMNS; j ++) {
                final int column = j;
                ImageView imageView;
                if (((i+j)%2) == 0) {
                    imageView = getImageView("grass.jpg", GRID_BLOCK_SIZE, GRID_BLOCK_SIZE);
                }
                else{
                    imageView = getImageView("grass2.jpg", GRID_BLOCK_SIZE, GRID_BLOCK_SIZE);
                }
                imageView.onMouseClickedProperty().setValue(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        placePlant(row, column);
                    }
                });
                GridPane.setRowIndex(imageView, row + ROW_OFFSET - level.NUMBER_OF_ROWS/2);
                GridPane.setColumnIndex(imageView, column + COLUMN_OFFSET);
                gridPane.getChildren().add(imageView);
            }

        }
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
    }
    public void setPlantPicked(boolean plantPicked) {
        isPlantPicked = plantPicked;
    }
    public void placePlant(int row, int column) {
        if (!isPlantPicked){
            return;
        }
        if (currentPanel!=null){
            setDropShadow(currentPanel,Color.WHITE);
        }
        isPlantPicked=false;

        Plant plant = null;
        Position position = getPosition(row + ROW_OFFSET - level.getLEVEL(), column + COLUMN_OFFSET);
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
        ImageView plantImageView = placeInGrid(plant);
        PlantController plantController = new PlantController(this, plant, plantImageView);
        plantController.start();
    }

    public void handlePeaZombieCollision(Pea pea, Zombie zombie) {
        if(!pea.isAlive() | !zombie.isAlive())  return;
        System.out.println("handlePeaZombieCollision");
        pea.setAlive(false);
        zombie.changeHealth(-1*Pea.PEA_ATTACK_POWER);
    }
    public void handlePlantZombieCollision(Plant plant, Zombie zombie) {
        if(!plant.isAlive() | !zombie.isAlive())  return;
        System.out.println("handlePlantZombieCollision " + plant + " " + zombie);
        ZombiePlantFight zombiePlantFight = new ZombiePlantFight(plant, zombie);
        zombiePlantFight.start();
    }
    public void handleLawnMowerZombieCollision(LawnMower lawnMower, Zombie zombie) {
        if(!lawnMower.isAlive() | !zombie.isAlive())  return;
        System.out.println("handleLawnMowerZombieCollision");
        if(!lawnMower.isMowing()) {
            lawnMower.setMowing(true);
        }
        zombie.setAlive(false);
    }

    public void setSunScore(){
        AnchorPane anchorPane = (AnchorPane) scene.lookup("#SunScore");
        Label label = (Label) scene.lookup("#SunScoreLabel");
        label.setText(String.valueOf(level.getGame().getScore().getSunPower()));
    }
    class UpdateTimer extends Thread {
        @Override
        public void run() {
            for(int i=0;i<300;i++){
                while (pause & level.isRunning()) {}
                if(!level.isRunning())  break;
                final int pos = i;
                Platform.runLater(() -> {
                    ProgressBar progressBar = (ProgressBar) scene.lookup("#Timer");
                    progressBar.setProgress(pos/300.0);
                });
                try{
                    Thread.sleep(100);
                }
                catch (InterruptedException e){}
            }
        }

    }

    public void openMenu(ActionEvent actionEvent) {
        pause = true;
        Node node = scene.lookup("#menu");
        node.setDisable(false);
        node.setVisible(true);
    }
    public void restartLevel(ActionEvent actionEvent) throws IOException {
        level.getGame().resetLevel(level.getLEVEL());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/LevelGUI.fxml"));
        Parent view = fxmlLoader.load();
        LevelController controller = (LevelController) fxmlLoader.getController();
        controller.setLevel(level);
        Scene viewScene = new Scene(view,600, 300);
        controller.setScene(viewScene);

        level.reset();
        controller.setUpLevel();

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();

    }
    public void closeMenu(ActionEvent actionEvent) {
        pause = false;
        Node node = scene.lookup("#menu");
        node.setDisable(true);
        node.setVisible(false);
    }
    public void back(ActionEvent actionEvent) throws IOException {
        Game game = level.getGame();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/GameGUI.fxml"));
        Parent view = fxmlLoader.load();
        GameController controller = (GameController) fxmlLoader.getController();
        controller.setGame(game);
        Scene viewScene = new Scene(view,600, 300);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }

    public void exit(ActionEvent actionEvent) {
        Game game = level.getGame();
        game.resetLevel(level.getLEVEL());
        Platform.exit();
    }

    public Level getLevel() {
        return level;
    }
    public Scene getScene() {
        return scene;
    }
    public boolean isPause() {
        return pause;
    }
}