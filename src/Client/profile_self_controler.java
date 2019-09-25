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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by Techno Service on 7/2/2018.
 */
public class profile_self_controler implements Initializable {
    @FXML
    private Label username;
    @FXML
    private Label coin;
    @FXML
    private ImageView profile1;
    @FXML
    private Button closeButton;
    @FXML
    private TextArea friend_text;
    static String friends;
    public void exit(ActionEvent actionEvent) {
        Stage stage1 = (Stage) closeButton.getScene().getWindow();
        stage1.close();

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        friend_text.setText("Friends are : ");
        String[] message = start_controler.message_of_profile_self.split(",");
        String username_txt = message[1];
        String img  = message[2];
        String coin_txt = message[3];
        friends = message[4];
        String[] friend = friends.split("/");
        System.out.println(friends);
        for (int i =0 ; i < friend.length ; i++){
            System.out.println(friend[i]);
            friend_text.setText(friend_text.getText() + " \n " + friend[i]);
        }
        username.setText(username_txt);
        coin.setText(coin_txt);
        profile1.setImage(new Image((new File(img)).toURI().toString()));
    }
}
