package Client;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Techno Service on 6/5/2018.
 */
public class start_controler extends Thread implements Initializable{
    static int game_number,getting_money;
    static String message_of_profile_self;
    static Timeline timeline1;
    @FXML
    private AnchorPane parent;
    @FXML
    private Button closeButton;
    @FXML
    private Label onlineperson;
    @FXML
    private ChoiceBox ch;
    static Thread thth;
    int p=0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        client.clientWriter.println("online-person");
        client.clientWriter.flush();
        String oline = client.clientReader[0].nextLine();
        oline = client.clientReader[0].nextLine();
        onlineperson.setText(oline);
        ObservableList<String> availableChoices = FXCollections.observableArrayList("منچ 25 سکه ایی","منچ 50 سکه ایی","منچ 200 سکه ایی","منچ دوستانه");
        ch.setItems(availableChoices);

        thth = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    String mes = client.clientReader[1].nextLine();
                    String[] mm = mes.split(",");
                    System.out.println(mes);
                    if (mes.contains("friend_play")) {
                        int answer_for_plsy = JOptionPane.showConfirmDialog(null,"your friend send for you gsmr ruquest" +
                                "\n do you accept??");
                        if (answer_for_plsy==0){
                            client.group = (Integer.parseInt(mm[2]));
                            client.person_ingoup = Integer.parseInt(mm[4]);
                            start_controler.game_number=3;
                            System.out.println("your group : " + client.group);
                            System.out.println("in group : " + client.person_ingoup);
                            client.clientWriter.println("play_friend_request_answer,accept," + mm[1] + "," + mm[2] + "," + mm[3] +","+ client.id);
                            client.clientWriter.flush();
                            p=1;
                            thth.stop();
                            break;

                        }
                    }
                }
            }
        });thth.start();

        timeline1 = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (p==1){
                    System.out.println("Start!!!");
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loading.fxml"));
                    Parent root = null;
                    try {
                        root = (Parent)fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    Stage stage1 = (Stage) onlineperson.getScene().getWindow();
                    stage1.close();
                    System.out.println("End");

                }
            }
        }));
        timeline1.setCycleCount(Animation.INDEFINITE);
        timeline1.play();
    }


    public void start(ActionEvent actionEvent) throws IOException {
        thth.stop();
        client.clientWriter.println("profile_myself,"+(client.group)+","+client.person_ingoup+","+start_controler.game_number);
        client.clientWriter.flush();

        String message = client.clientReader[0].nextLine();
        message_of_profile_self = message;
        if(ch.getValue()==null){
            JOptionPane.showMessageDialog(null,"یک نوع منچ را انتخاب کنید!!!","message",JOptionPane.ERROR_MESSAGE);
        }else{
            if (ch.getValue().equals("منچ دوستانه")){

                game_number = 3;
                getting_money = 0;
                client.clientWriter.println("start_friend_play," + client.id + "," + game_number + "," + getting_money);
                client.clientWriter.flush();

                String[] mess = client.clientReader[0].nextLine().split(",");
                client.group = (Integer.parseInt(mess[1]) - 1);
                client.person_ingoup = Integer.parseInt(mess[0]);

                System.out.println("your group : " + client.group);
                System.out.println("in group : " + client.person_ingoup);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loading_friend.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                Stage stage1 = (Stage) closeButton.getScene().getWindow();
                stage1.close();

            }
            else {
                if ((ch.getValue().equals("منچ 25 سکه ایی") && client.coin > 25) || (ch.getValue().equals("منچ 50 سکه ایی") && client.coin > 50) || (ch.getValue().equals("منچ 200 سکه ایی") && client.coin > 200)) {
                    if (ch.getValue().equals("منچ 25 سکه ایی")) {
                        game_number = 0;
                        getting_money = 25;
                    } else if (ch.getValue().equals("منچ 50 سکه ایی")) {
                        game_number = 1;
                        getting_money = 50;
                    } else if (ch.getValue().equals("منچ 200 سکه ایی")) {
                        game_number = 2;
                        getting_money = 200;
                    }
                    client.clientWriter.println("start," + client.id + "," + game_number + "," + getting_money);
                    client.clientWriter.flush();

                    String[] mess = client.clientReader[0].nextLine().split(",");
                    client.group = (Integer.parseInt(mess[1]) - 1);
                    client.person_ingoup = Integer.parseInt(mess[0]);

                    System.out.println("your group : " + client.group);
                    System.out.println("in group : " + client.person_ingoup);

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loading.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    Stage stage1 = (Stage) closeButton.getScene().getWindow();
                    stage1.close();
                } else {
                    JOptionPane.showMessageDialog(null, "پول شما به این منچ نمیرسد!!!", "message", JOptionPane.ERROR_MESSAGE);
                }
            }
        }


    }

    public void profile(ActionEvent actionEvent) throws InterruptedException {
        client.clientWriter.println("profile_myself,"+(client.group)+","+client.person_ingoup+","+start_controler.game_number);
        client.clientWriter.flush();

        String message = client.clientReader[0].nextLine();

        message_of_profile_self = message;

        Thread.sleep(1500);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile-self.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }
}
