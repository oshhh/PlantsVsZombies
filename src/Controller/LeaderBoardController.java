package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.*;
import java.io.*;

public class LeaderBoardController {
    private App app;
    private LeaderBoard leaderBoard;

    public LeaderBoardController() {
    }

    public void setUpLeaderBoard(App app, Scene scene) {
        this.app = app;
        leaderBoard = app.getLeaderBoard();
        leaderBoard.updateLeaderBoard();
        TableView tableView = (TableView) scene.lookup("#leaderBoardTable");

        TableColumn nameColumn = new TableColumn("PlayerName");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn currentLevelColumn = new TableColumn("CurrentLevel");
        currentLevelColumn.setCellValueFactory(new PropertyValueFactory<>("currentLevel"));

        TableColumn tableColumn = new TableColumn("Coins");
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("coins"));

        tableView.getColumns().addAll(nameColumn, currentLevelColumn, tableColumn);

        tableView.getItems().addAll(app.getLeaderBoard().getScores());
    }

    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/AppGUI.fxml"));
        Parent view = fxmlLoader.load();
        AppController controller = (AppController) fxmlLoader.getController();
        controller.setApp(app);
        Scene viewScene = new Scene(view,1200, 600);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(viewScene);
        window.show();
    }
}
