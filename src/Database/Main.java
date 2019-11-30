package Database;

import Controller.*;
import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {
    private static App app;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        deserialize();
//        app = new App();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/AppGUI.fxml"));
        Parent view = fxmlLoader.load();
        AppController controller = (AppController) fxmlLoader.getController();
        controller.setApp(app);
        Scene viewScene = new Scene(view,1200, 600);
        stage.setScene(viewScene);
        stage.show();
    }

    public static void serialize() throws IOException {
        System.out.println("entered");
        ObjectOutputStream outputStream = null;
        try{
            outputStream = new ObjectOutputStream(new FileOutputStream("savedGame.txt"));
            outputStream.writeObject(app);
        }
        finally {

            if (outputStream!=null) {
                outputStream.close();
            }
            System.out.println("end");
        }
    }

    public static void deserialize() throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = null;
        try{
            inputStream = new ObjectInputStream(new FileInputStream("savedGame.txt"));
            app = (App) inputStream.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            app = new App();
        }
        finally {
            if (inputStream!=null) {
                inputStream.close();
            }
            else {
                app = new App();
            }
        }
    }

}
