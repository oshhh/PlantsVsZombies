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
import java.util.Random;


public class LevelController {

    private Level level;
    private Player player;
    private Scene scene;
    private static AnchorPane currentPanel;
    private boolean isPlantPicked = true;
    private String plantName;

    private volatile boolean pause;

    private static final int NUMBER_OF_COLUMNS = 9;
    private static final int GRID_BLOCK_SIZE = 34;
    private static final int ROW_OFFSET = 5;
    private static final int COLUMN_OFFSET = 4;


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

    public ImageView getImageView(Placeable placeable) {

        GridPane grid = (GridPane) (scene.lookup("#grid"));
        Image image = new Image("Assets/"+placeable.getImageName());
        ImageView imageView = new ImageView();
        imageView.setFitWidth(placeable.getRelativeSize() * GRID_BLOCK_SIZE);
        imageView.setFitHeight(placeable.getRelativeSize() * GRID_BLOCK_SIZE);

        GridPane.setRowIndex(imageView, placeable.getPosition().getX() );
        GridPane.setColumnIndex(imageView, placeable.getPosition().getY());
        imageView.setImage(image);
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

    public void createLevel() throws IOException {
        level.setRunning(true);
        createPlantPanel();
        createTopBar();
        layGrass();
        scene.lookup("#menu").setVisible(false);
        scene.lookup("#menu").setDisable(true);
        RegularAction regularAction = new RegularAction(); regularAction.start();
    }

    public void endLevel() {
        level.setRunning(false);
    }

    public void createPlantPanel() throws IOException {
        // Create Plant panel

        for (String plant:  level.getAvailablePlants()) {
            // Load plant panel
            Image plantImage = new Image("Assets/" + plant + ".png");
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
    public void createTopBar() {
        // SunToken logo beside SunToken Points Count
        AnchorPane anchorPane = (AnchorPane) scene.lookup("#SunTokenLogo");
        ImageView imageView = getImageView("SunToken.png",40,40);
        anchorPane.getChildren().add(imageView);
        // Zombie Head beside progress bar
        imageView = getImageView("ZombieHead.png",40,40);
        anchorPane = (AnchorPane) scene.lookup("#ZombieLogo");
        anchorPane.getChildren().add(imageView);
        // Update SunToken points and Progress Bar
        UpdateTimer updateTimer = new UpdateTimer();
        updateTimer.start();
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

            // Put LawnMower
            LawnMower lawnMower = new LawnMower(new Position(row + ROW_OFFSET - level.NUMBER_OF_ROWS/2, COLUMN_OFFSET - 1));
            level.addLawnMower(lawnMower);
            ImageView imageView = getImageView(lawnMower);
            imageView.setTranslateX(-15);

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
//        return anchorPane;
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
                setSunScore();
                GridPane gridPane = (GridPane) (scene.lookup("#grid"));
                gridPane.getChildren().remove(imageView);
            });

            // Add transition
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.seconds(15));
            translateTransition.setToY(imageView.getY() + 220);
            translateTransition.setNode(imageView);
            translateTransition.play();
            ControlTranslation controlTranslation = new ControlTranslation();
            controlTranslation.setTranslateTransition(translateTransition);
            controlTranslation.start();
        }

    }
    // Runnable that generates sun when run
    class GenerateZombie implements Runnable {

        @Override
        public void run() {
            // Make Zombie and add to level
            Random random = new Random();
            int row = random.nextInt(level.NUMBER_OF_ROWS) + ROW_OFFSET - level.NUMBER_OF_ROWS / 2;
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
            translateTransition.setByX(-(column - 3) * GRID_BLOCK_SIZE);
//            translateTransition.setToX(-350);
            translateTransition.setNode(imageView);
            translateTransition.play();
            ControlTranslation controlTranslation = new ControlTranslation();
            controlTranslation.setTranslateTransition(translateTransition);
            controlTranslation.start();
        }
    }
    // Runnable that shoots a pea from each peashooter
    class ShootPeas implements Runnable{
        @Override
        public void run(){
            for(Plant plant: level.getPlants()) {
                if(!plant.getClass().equals(PeaShooter.class))  continue;

                Position position = plant.getPosition();
                Pea pea = new Pea(position);
                ImageView imageView = getImageView(pea);

                imageView.setTranslateX(30);
                imageView.setTranslateY(-10);
                TranslateTransition translateTransition = new TranslateTransition();
                translateTransition.setDuration(Duration.seconds(7));
                translateTransition.setToX(scene.getWidth());
                translateTransition.setNode(imageView);
                translateTransition.play();
                ControlTranslation controlTranslation = new ControlTranslation();
                controlTranslation.setTranslateTransition(translateTransition);
                controlTranslation.start();
            }

        }
    }

    // Thread that is running all the time to execute regular action
    class RegularAction extends Thread {
        @Override
        public void run() {
            while (level.isRunning()) {
                System.out.println(pause);
                try {

                    // We can't change UI in any other thread except JavaFX thread
                    // We need to add all UI changes to platform.runLater()
                    // which will run those UI changing threads in the JavaFX thread

                    // Run thread that generates zombie
                    while (pause) {}

                    GenerateZombie generateZombie = new GenerateZombie();
                    Platform.runLater(generateZombie);

                    Thread.sleep(3000);

                    // Run thread that generates sun
                    GenerateSun generateSun = new GenerateSun();
                    Platform.runLater(generateSun);

                    Thread.sleep(2000);
                    ShootPeas shootPeas = new ShootPeas();
                    Platform.runLater(shootPeas);

                    System.out.println(level.getGame().getScore().getSunPower());

                } catch (InterruptedException e) {}
             }
        }
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
                while (pause) {}
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
    class ControlTranslation extends Thread{
        private TranslateTransition translateTransition;
        @Override
        public void run() {
            while (!translateTransition.getStatus().equals(Animation.Status.STOPPED) & level.isRunning()) {
                if(translateTransition.getStatus().equals(Animation.Status.RUNNING) & pause) {
                    translateTransition.pause();
                }
                else if (translateTransition.getStatus().equals(Animation.Status.PAUSED) & !pause){
                    translateTransition.play();
                }
            }
        }

        public void setTranslateTransition(TranslateTransition translateTransition) {
            this.translateTransition = translateTransition;
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
        controller.createLevel();
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
        createLevel();
    }
    public void closeMenu(ActionEvent actionEvent) {
        System.out.println("closing");
        pause = false;
        Node node = scene.lookup("#menu");
        node.setDisable(true);
        node.setVisible(false);
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

    public void exit(ActionEvent actionEvent) {
        Game game = level.getGame();
        game.resetLevel(level.getLEVEL());
        Platform.exit();
    }
}