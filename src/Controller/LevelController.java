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

    private static final int NUMBER_OF_COLUMNS = 8;
    private static final int GRID_BLOCK_SIZE = 35;
    private static final int ROW_OFFSET = 2;
    private static final int COLUMN_OFFSET = 1;
    private static final int GRID_X_OFFSET = 100;
    private static final int GRID_Y_OFFSET = 70;
    private static final int SKY_ROW = -2;
    private static final int COLLISION_RADIUS = 20;

    public Position getPosition(int row, int column) {
        return new Position(GRID_X_OFFSET + GRID_BLOCK_SIZE * column, GRID_Y_OFFSET + GRID_BLOCK_SIZE * row);
    }

    public Position getPositionGrid(int x, int y){
        return new Position((y-GRID_Y_OFFSET)/GRID_BLOCK_SIZE,(x-GRID_X_OFFSET)/GRID_BLOCK_SIZE);
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

    public ImageView getImageView(Placeable placeable) {

        GridPane grid = (GridPane) (scene.lookup("#grid"));
        Image image = new Image("Assets/"+placeable.getImageName());
        ImageView imageView = new ImageView();
        imageView.setFitWidth(placeable.getRelativeSize() * GRID_BLOCK_SIZE);
        imageView.setFitHeight(placeable.getRelativeSize() * GRID_BLOCK_SIZE);
        Position gridPosition = getPositionGrid(placeable.getPosition().getX(),placeable.getPosition().getY());
        GridPane.setRowIndex(imageView, gridPosition.getX() );
        GridPane.setColumnIndex(imageView, gridPosition.getY());
        imageView.setImage(image);
        grid.getChildren().add(imageView);

        return imageView;
    }
    public ImageView getImageViewAnchor(Placeable placeable) {

        AnchorPane grid = (AnchorPane) (scene.lookup("#mainPane"));
        Image image = new Image("Assets/"+placeable.getImageName());
        ImageView imageView = new ImageView();
        imageView.setFitWidth(placeable.getRelativeSize() * GRID_BLOCK_SIZE);
        imageView.setFitHeight(placeable.getRelativeSize() * GRID_BLOCK_SIZE);

        AnchorPane.setTopAnchor(imageView, (double)placeable.getPosition().getY());
        AnchorPane.setLeftAnchor(imageView, (double)placeable.getPosition().getX());

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

        RegularAction regularAction = new RegularAction();
        regularAction.start();

        DetectCollision detectCollision = new DetectCollision();
        detectCollision.setLevelController(this);
        detectCollision.setPriority(1);
        detectCollision.start();
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

            // Put LawnMower
            LawnMower lawnMower = new LawnMower(getPosition(row + ROW_OFFSET - level.NUMBER_OF_ROWS/2, COLUMN_OFFSET - 1));
            level.addLawnMower(lawnMower);
            ImageView imageView = getImageViewAnchor(lawnMower);
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

        ImageView plantImageView = getImageView(plant);


    }

    public void handlePeaZombieCollision(Pea pea, Zombie zombie) {
        if(!pea.isAlive() | !zombie.isAlive())  return;
        System.out.println("handlePeaZombieCollision");
        pea.setAlive(false);
        zombie.changeHealth(-1*Pea.PEA_ATTACK_POWER);
    }
    public void handlePlantZombieCollision(Plant plant, Zombie zombie) {
        if(!plant.isAlive() | !zombie.isAlive())  return;
        System.out.println("handlePlantZombieCollision");
        ZombiePlantFight zombiePlantFight = new ZombiePlantFight(plant, zombie);
    }
    public void handleLawnMowerZombieCollision(LawnMower lawnMower, Zombie zombie) {
        if(!lawnMower.isAlive() | !zombie.isAlive())  return;
        System.out.println("handleLawnMowerZombieCollision");
        if(!lawnMower.isMowing()) {
            lawnMower.setMowing(true);
        }
        zombie.setAlive(false);
    }

    class ZombiePlantFight extends Thread {
        private Plant plant;
        private Zombie zombie;

        public ZombiePlantFight(Plant plant, Zombie zombie) {
            this.plant = plant;
            this.zombie = zombie;
        }

        @Override
        public void run() {
            zombie.setAttacking(true);
            while (plant.isAlive() & zombie.isAlive()) {
                plant.changeHealth(-1*zombie.attack());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }
        }
    }

    // Runnable that generates falling sun when run
    class GenerateSun implements Runnable {

        @Override
        public void run() {
            Random random = new Random();
            int col=random.nextInt(7) + COLUMN_OFFSET;

            ImageView imageView = getImageViewAnchor(new SunToken(getPosition(SKY_ROW, col)));

            // Add listener
            imageView.setOnMouseClicked(mouseEvent -> {
                if(pause)   return;
                level.collectSun();
                setSunScore();
                AnchorPane anchorPane = (AnchorPane) (scene.lookup("#mainPane"));
                anchorPane.getChildren().remove(imageView);
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
    // Runnable that generates sun on sunflower when run
    class GenerateSunSunFlower implements Runnable{
        @Override
        public void run() {
            for (Plant plant : level.getPlants()) {
                if (!plant.getClass().equals(SunFlower.class)) continue;
                SunFlower sunFlower = (SunFlower) plant;
                if (sunFlower.getSunFlowerFlag()) continue;
                Position position = plant.getPosition();
                SunToken sunToken = new SunToken(position);
                ImageView imageView = getImageView(sunToken);
                imageView.setTranslateX(-10);
                imageView.setTranslateY(-10);
                sunFlower.setSunFlowerFlag(true);
                imageView.setOnMouseClicked(mouseEvent -> {
                    if(pause)   return;
                    level.collectSun();
                    setSunScore();
                    GridPane gridPane = (GridPane) (scene.lookup("#grid"));
                    gridPane.getChildren().remove(imageView);
                    sunFlower.setSunFlowerFlag(false);
                });

            }
        }
    }
    // Runnable that generates sun when run
    class GenerateZombie implements Runnable {

        @Override
        public void run() {
            // Make Zombie and add to level
            Random random = new Random();
            int row = ROW_OFFSET + random.nextInt(level.NUMBER_OF_ROWS) - level.NUMBER_OF_ROWS / 2;
            int column = COLUMN_OFFSET + NUMBER_OF_COLUMNS + random.nextInt(5);
            Position position = getPosition(row, column);
            Zombie zombie = new Zombie(position);
            level.addZombie(zombie);

            // Add to UI
            ImageView imageView = getImageViewAnchor(zombie);

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
                level.addPea(pea);
                ImageView imageView = getImageViewAnchor(pea);

                imageView.setTranslateX(30);
                imageView.setTranslateY(-5);
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

    class DetectCollision extends Thread{
        private LevelController levelController;

        @Override
        public void run(){
            while (level.isRunning()){
                while (pause & level.isRunning()) {}
                synchronized (level.getZombies()) {
//                    System.out.println("check");
                    for (Zombie zombie: level.getZombies()){
                        Position zombiePosition = zombie.getPosition();
                        synchronized (level.getPlants()) {
                            for (Plant plant: level.getPlants()){
                                Position plantPosition = plant.getPosition();
                                if (Math.abs(zombiePosition.getX()-plantPosition.getX())<=COLLISION_RADIUS && Math.abs(zombiePosition.getY()-plantPosition.getY())<=COLLISION_RADIUS){//collision detected, action to be performed
                                    levelController.handlePlantZombieCollision(plant, zombie);
                                }
                            }

                        }
                        synchronized (level.getPeas()) {
                            for (Pea pea: level.getPeas()){
                                Position peaPosition = pea.getPosition();
                                System.out.println(zombiePosition.getX()+" "+zombiePosition.getY()+" : "+peaPosition.getX()+" "+peaPosition.getY());
                                if (Math.abs(zombiePosition.getX()-peaPosition.getX())<=COLLISION_RADIUS && Math.abs(zombiePosition.getY()-peaPosition.getY())<=COLLISION_RADIUS){//collision detected, action to be performed
                                    System.out.println("blah");
                                    levelController.handlePeaZombieCollision(pea, zombie);
                                }
                            }
                        }
                        synchronized (level.getLawnMowers()) {
                            for (LawnMower lawnMower: level.getLawnMowers()){
                                Position lawnMowerPosition = lawnMower.getPosition();
                                if (Math.abs(zombiePosition.getX()-lawnMowerPosition.getX())<=COLLISION_RADIUS && Math.abs(zombiePosition.getY()-lawnMowerPosition.getY())<=COLLISION_RADIUS){//collision detected, action to be performed
                                    levelController.handleLawnMowerZombieCollision(lawnMower, zombie);
                                }
                            }
                        }
                    }
                }
            }
        }

        public void setLevelController(LevelController levelController) {
            this.levelController = levelController;
        }
    }

    // Thread that is running all the time to execute regular action
    class RegularAction extends Thread {
        @Override
        public void run() {
            while (level.isRunning()) {
                try {

                    // We can't change UI in any other thread except JavaFX thread
                    // We need to add all UI changes to platform.runLater()
                    // which will run those UI changing threads in the JavaFX thread

                    // Run thread that generates zombie
                    while (pause & level.isRunning()) {}

                    GenerateZombie generateZombie = new GenerateZombie();
                    Platform.runLater(generateZombie);

                    Thread.sleep(3000);

                    while (pause & level.isRunning()) {}

                    // Run thread that generates sun
                    GenerateSun generateSun = new GenerateSun();
                    Platform.runLater(generateSun);


                    Thread.sleep(2000);

                    while (pause & level.isRunning()) {}

                    ShootPeas shootPeas = new ShootPeas();
                    Platform.runLater(shootPeas);

                    GenerateSunSunFlower generateSunSunFlower = new GenerateSunSunFlower();
                    Platform.runLater(generateSunSunFlower);


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

    }
    public void closeMenu(ActionEvent actionEvent) {
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