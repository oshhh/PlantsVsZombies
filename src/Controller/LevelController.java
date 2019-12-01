package Controller;
import Database.Main;
import Model.*;
import Model.*;
import javafx.animation.Animation;
import javafx.geometry.Insets;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class LevelController {

    public static final int NUMBER_OF_COLUMNS = 8;
    public static final int GRID_BLOCK_SIZE = 35*2;
    public static final int ROW_OFFSET = 2;
    public static final int COLUMN_OFFSET = 1;
    public static final int GRID_X_OFFSET = 100*2;
    public static final int GRID_Y_OFFSET = 70*2;
    public static final int SKY_ROW = -2;
    public static final int GROUND_ROW = 4;
    public static final int COLLISION_RADIUS = 5;
    public static final int ANIMATION_TIMEGAP = 100;

    private Level level;
    private Scene scene;
    private volatile boolean isPlantPicked;
    private Class plantPicked;
    private volatile boolean pause;

    public LevelController() {
        isPlantPicked = false;
        pause = false;
    }

    public static Position getPosition(int row, int column) {
        return new Position(GRID_X_OFFSET + GRID_BLOCK_SIZE * column, GRID_Y_OFFSET + GRID_BLOCK_SIZE * row);
    }

    public static GridPosition getGridPosition(Position position){
        return new GridPosition((position.getY()-GRID_Y_OFFSET)/GRID_BLOCK_SIZE,(position.getX()-GRID_X_OFFSET)/GRID_BLOCK_SIZE);
    }

    public void setScore() {
        ((Label)scene.lookup("#currentLevel")).setText(Integer.toString(level.getGame().getScore().getCurrentLevel()));
        ((Label)scene.lookup("#coins")).setText(Integer.toString(level.getGame().getScore().getCoins()));
    }

    public void setImageView(Placeable placeable, ImageView imageView) {
        Image image = new Image("Assets/" + placeable.getImageName());
        imageView.setFitWidth(placeable.getRelativeSize() * GRID_BLOCK_SIZE);
        imageView.setFitHeight(placeable.getRelativeSize() * GRID_BLOCK_SIZE);
        imageView.setImage(image);
    }
    public ImageView placeInGrid(Placeable placeable) {
        ImageView imageView = new ImageView();
        setImageView(placeable, imageView);
        GridPane grid = (GridPane) (scene.lookup("#grid"));
        GridPosition gridPosition = getGridPosition(placeable.getPosition());
        GridPane.setRowIndex(imageView, gridPosition.getRow() );
        GridPane.setColumnIndex(imageView, gridPosition.getColumn());
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



    public void setUpLevel(Level level, Scene scene) throws IOException {
        this.level = level;
        this.scene = scene;
        level.setRunning(true);
        createPlantPanel();
        createTopBar();
        layGrass();
        setUpMenus();
        placePlaceables();
        setScore();

        RegularAction regularAction = new RegularAction(this);
        regularAction.start();

        DetectCollision detectCollision = new DetectCollision(this);
        detectCollision.setPriority(1);
        detectCollision.start();

        PlantPanelController plantPanelController = new PlantPanelController(level.getPlantPanel(), this);
        plantPanelController.start();
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
                imageView.setOnMouseClicked(mouseEvent -> {
                    if(isPause())   return;
                    level.collectSun();
                    setSunScore();
                    ((SunToken)placeable).setAlive(false);
                    AnchorPane anchorPane = (AnchorPane) (scene.lookup("#mainPane"));
                    anchorPane.getChildren().remove(imageView);
                });                SunTokenController sunTokenController = new SunTokenController(this, (SunToken) placeable, imageView);
                sunTokenController.start();
            }
        }

        synchronized (level.getLawnMowers()) {
            for(Placeable placeable : level.getLawnMowers()) {
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
        scene.lookup("#finalWaveText").setVisible(false);

    }

    public void endLevel() {
        level.setRunning(false);
    }
    public void createPlantPanel() throws IOException {
        // Create Plant panel
        HashMap<Class, AnchorPane> anchorPaneHashMap = new HashMap<Class, AnchorPane>();
        for (Class plant:  level.getPlantPanel().getAvailablePlants()) {
            // Load plant panel
            Image plantImage = new Image("Assets/" + PlantPanel.getImageName(plant));
            ImageView plantImageView = new ImageView();
            plantImageView.setFitWidth(80);
            plantImageView.setFitHeight(60);
            plantImageView.setImage(plantImage);

            System.out.println("#plantPanel" + PlantPanel.getName(plant));
            AnchorPane anchorPane = (AnchorPane) scene.lookup("#plantPanel" + PlantPanel.getName(plant));
            anchorPane.getChildren().add(plantImageView);
            anchorPaneHashMap.put(plant, anchorPane);
            class Handler implements EventHandler<MouseEvent> {
                private Class plant;

                Handler(Class plant) {
                    System.out.println(plant);
                    this.plant = plant;
                }

                @Override
                public void handle(MouseEvent mouseEvent) {
                    // If disabled
                    if(level.getPlantPanel().isPlantDisabled(plant)) return;
                    System.out.println(plant + " " + anchorPane);
                    level.getPlantPanel().selectPlant(plant);
                }
            }
            anchorPane.onMouseClickedProperty().setValue(new Handler(plant));
        }
        level.getPlantPanel().setAnchorPaneHashMap(anchorPaneHashMap);

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
                imageView.setTranslateY(20);
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

    public void placePlant(int row, int column) {
        if (!level.getPlantPanel().isSelected()){
            return;
        }

        Position position = getPosition(row + ROW_OFFSET - level.getLEVEL(), column + COLUMN_OFFSET);
        Plant plant = null;

        switch (level.getPlantPanel().getSelectedPlant().getName()) {
            case "Model.PeaShooter":
                plant = new PeaShooter(position);
                break;
            case "Model.SunFlower":
                plant = new SunFlower(position);
                break;
            case "Model.WallNut":
                plant = new WallNut(position);
                break;
            case "Model.CherryBomb":
                plant = new CherryBomb(position);
                break;
        }

        level.useSunTokens(PlantPanel.getPrice(level.getPlantPanel().getSelectedPlant()));
        setSunScore();
        level.addPlant(plant);
        ImageView plantImageView = placeInGrid(plant);
        PlantController plantController = new PlantController(this, plant, plantImageView);
        plantController.start();

        level.getPlantPanel().placePlant();
    }

    public void setSunScore(){
        AnchorPane anchorPane = (AnchorPane) scene.lookup("#SunScore");
        Label label = (Label) scene.lookup("#SunScoreLabel");
        label.setText(String.valueOf(level.getGame().getScore().getSunPower()));
    }
    class UpdateTimer extends Thread {
        @Override
        public void run() {
            float maxTime = level.getMaxZombiesBeforeWave();
            float lastPosVal = 0;
            while (level.getCurrentNumberOfZombies()-level.getZombies().size()<=maxTime){
                while (pause & level.isRunning()) {}
                if(!level.isRunning())  break;
                final float pos = level.getNumberOfDeadZombies();
                if (pos!=lastPosVal & pos>0) {
                    for (float i = pos-1; i < pos; i += 0.2) {
                        while (pause & level.isRunning()) {}
                        final float barVal = i;
                        Platform.runLater(() -> {
                            ProgressBar progressBar = (ProgressBar) scene.lookup("#Timer");
                            progressBar.setProgress(barVal / maxTime);
                        });
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {}
                    }
                    lastPosVal = pos;
                }

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
        level = level.getGame().getLevel(level.getLEVEL());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/LevelGUI.fxml"));
        Parent view = fxmlLoader.load();
        LevelController controller = (LevelController) fxmlLoader.getController();
        Scene viewScene = new Scene(view,1200, 600);
        controller.setUpLevel(level, viewScene);

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
        Main.serialize();
        level.setRunning(false);
        Game game = level.getGame();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/GameGUI.fxml"));
        Parent view = fxmlLoader.load();
        GameController controller = (GameController) fxmlLoader.getController();
        Scene viewScene = new Scene(view,1200, 600);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        controller.setUpGame(game, viewScene);
        window.setScene(viewScene);
        window.show();
    }

    public void exit(ActionEvent actionEvent) {
        level.setRunning(false);
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