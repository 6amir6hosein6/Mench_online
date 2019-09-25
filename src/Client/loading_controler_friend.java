package Client;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Techno Service on 7/5/2018.
 */
public class loading_controler_friend implements Initializable {
    static Thread th;

    static Timeline timeline;
    @FXML
    private Label online;
    @FXML
    private TableView<friend_username> username;
    @FXML
    private Button play;
    int p=0;
    static int online_person_ready_to_play;
    ObservableList<friend_username> data;
    public void play(ActionEvent actionEvent) throws IOException {
        if (client.person_ingoup == 1){
            //if (loading_controler.online_person_ready_to_play>1){
                client.clientWriter.println("play,"+client.group+","+start_controler.game_number);
                client.clientWriter.flush();
            //}else{
              //  JOptionPane.showMessageDialog(null,"حداقل تعداد شرکت کننده برای مسابقه 2 نفر می باشد!!!","message",JOptionPane.ERROR_MESSAGE);
            //}
        }else{
            JOptionPane.showMessageDialog(null,"شما نمیتوانید بازی را شرروع کنید!!! \n این کار مخصوص اولین شرکت کننده وارد شده است !!!","message",JOptionPane.ERROR_MESSAGE);
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start_controler.thth.stop();
        String[] message = start_controler.message_of_profile_self.split(",");
        String friends = message[4];
        String[] friend = friends.split("/");
        data = username.getItems();
        username.setEditable(true);
        username.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("username"));
        for (int i=0;i<friend.length;i++){
            data.add(new friend_username(friend[i]));
        }

        th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String message = client.clientReader[0].nextLine();
                    System.out.println("mesage is : "+message);
                    String[] position = message.split(",");
                    if (position[0].equals("online")) {
                        online_person_ready_to_play = Integer.parseInt(position[1]);
                        System.out.println("joint in game is : " + online_person_ready_to_play);
                    }else if(position[0].equals("play")){
                        p=1;
                        th.stop();
                        break;

                    }else if (position[1].equals("play_request_of_friend")){
                        System.out.println(position[0]);
                    }
                }
            }
        });
        th.start();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                online.setText(online_person_ready_to_play+"");
                if (p==1){
                    System.out.println("Start!!!");
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("board.fxml"));
                    Parent root = null;
                    try {
                        root = (Parent)fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    Stage stage1 = (Stage) play.getScene().getWindow();
                    stage1.close();
                    System.out.println("End");

                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void request(ActionEvent actionEvent) {
        String usernamef;
        usernamef = username.getSelectionModel().getSelectedItem().getUsername();
        client.clientWriter.println("friend_request_play,"+usernamef+","+start_controler.game_number+","+client.group+","+client.person_ingoup);
        client.clientWriter.flush();
    }
}
