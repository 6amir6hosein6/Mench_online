package Client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Created by Techno Service on 6/4/2018.
 */
public class client extends Application implements Initializable {
    @FXML
    private AnchorPane content;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label lbl;
    static Scanner[] clientReader = new Scanner[3];
    static PrintWriter clientWriter;

    static int id;
    static int group;
    static int coin;
    static int person_ingoup;
    static Socket socket_sending_someThing;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("signin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void register(ActionEvent actionEvent) throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("setProfile.fxml"));
        content.getChildren().setAll(pane);
    }

    public void signin(ActionEvent actionEvent) throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();
        clientWriter.println("signin,"+username+","+password+","+"");
        clientWriter.flush();
        String respons[] = clientReader[0].nextLine().split(",");


        if(respons[0].equals("good")){
            coin = Integer.parseInt(respons[1]);
            System.out.println("your money is : "+coin);
            AnchorPane pane = FXMLLoader.load(getClass().getResource("start.fxml"));
            content.getChildren().setAll(pane);
        }else{
           lbl.setText(respons[0]);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            socket_sending_someThing = new Socket("localhost",3659);
            clientReader[0] = new Scanner(socket_sending_someThing.getInputStream());
            clientReader[1] = new Scanner(socket_sending_someThing.getInputStream());
            clientReader[2] = new Scanner(socket_sending_someThing.getInputStream());
            clientWriter = new PrintWriter(socket_sending_someThing.getOutputStream());


            id = Integer.parseInt(clientReader[0].nextLine());
            id++;
            System.out.println("your id is : "+id);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
