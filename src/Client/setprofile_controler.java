package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;


public class setprofile_controler implements Initializable {
    private String img;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label lbl;
    @FXML
    private ImageView profile;
    @FXML
    private AnchorPane co;
    public void setprofile(MouseEvent mouseEvent) throws MalformedURLException {
        Stage stage = new Stage();
        FileChooser chooser = new FileChooser();
        File select = chooser.showOpenDialog(stage);
        chooser.setTitle("Open File");

        img = select.getPath();
        System.out.println(img);
        img = img.replaceAll("\\\\","\\\\\\\\");
        System.out.println(img);
        profile.setImage(new Image((new File(img)).toURI().toString()));
    }

    public void add(ActionEvent actionEvent) throws IOException {


        if(username.getText().equals("")||password.getText().equals("")){
            lbl.setText("Compelete all fields ... !!");

        }else{
            String message = "register"+","+username.getText()+","+password.getText()+","+img;
            client.clientWriter.println(message);
            client.clientWriter.flush();
            String respons = client.clientReader[0].nextLine();
            if(respons.equals("good")){
                System.out.println("hi");
                AnchorPane pane= FXMLLoader.load(getClass().getResource("signin.fxml"));
                co.getChildren().setAll(pane);
            }else{
                lbl.setText(respons);
            }
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
