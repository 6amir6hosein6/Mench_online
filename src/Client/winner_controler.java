package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Techno Service on 7/9/2018.
 */
public class winner_controler implements Initializable {
    @FXML
    private Label winner;
    public void start(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("start.fxml"));
        Parent root = null;
        try {
            root = (Parent)fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        Stage stage1 = (Stage) winner.getScene().getWindow();
        stage1.close();
    }


    public void exit(ActionEvent actionEvent) {
        Stage stage1 = (Stage) winner.getScene().getWindow();
        stage1.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        board_controler.thread_of_getting_position.stop();
        board_controler.timeline_wining.stop();
        String[] mesage  = board_controler.winner_message.split(",");
        winner.setText("The winner is : "+mesage[2]);

    }
}
