package Client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by Techno Service on 7/2/2018.
 */
public class profile_controler implements Initializable {
    @FXML
    private Label username;
    @FXML
    private Label coin;
    @FXML
    private ImageView profile1;
    @FXML
    private Button closeButton;
    public void exit(ActionEvent actionEvent) {
        Stage stage1 = (Stage) closeButton.getScene().getWindow();
        stage1.close();

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("prooooooooooooo : "+board_controler.message_of_profile);
        String[] message = board_controler.message_of_profile.split(",");
        String username_txt = message[1];
        String img  = message[2];
        String coin_txt = message[3];
        username.setText(username_txt);
        coin.setText(coin_txt);
        profile1.setImage(new Image((new File(img)).toURI().toString()));
    }
}
