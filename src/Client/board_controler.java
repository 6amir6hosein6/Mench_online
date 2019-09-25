package Client;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Techno Service on 6/28/2018.
 */
public class board_controler extends Thread implements Initializable {
    static Thread thread_of_getting_position;
    static Timeline timeline_wining;
    private Circle first_place;
    Random rand;
    String your_color,propertise_of_shuted_circle;
    boolean tasing=false , is_inside[] = {false,false,false,false};
    int[] moving = new int[4] ;
    static String message_of_profile,winner_message;
    double shuter_x=0 , shuter_y=0;
    int[] gone_places= new int[4];
    int turn=1,inside=3,p=0,end=40,last_place=4,tas_number,mohre, moving_in_total=0;
    static int how_many_person_in_game;
    double[][] blue_first_palce = {{81,372},{125,372},{125,414},{81,414}};
    double[][] yellow_first_palce = {{79,69},{79,111},{123,69},{123,111}};
    double[][] green_first_palce = {{408,412},{408,374},{451,374},{452,412}};
    double[][] red_first_palce = {{409,111},{409,69},{453,69},{453,111}};
    Circle restore_circle=null;
    @FXML
    private Circle bplace0,rplace0,yplace0,gplace0;
    @FXML
    private Label tas;
    @FXML
    private Button closeButton;
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField insert_number;
    @FXML
    private Button want_number;
    @FXML
    private Button six_number;
    @FXML
    private Button pbtn1,pbtn2,pbtn3,pbtn4;
    @FXML
    private Button unShut;
    public void exit1(ActionEvent actionEvent) throws IOException {
       int x = JOptionPane.showConfirmDialog(null,"Are you sure?","",JOptionPane.YES_NO_OPTION);
       if (x==0){
           System.out.println("yeeeeees");
           FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("start.fxml"));
           Parent root1 = (Parent)fxmlLoader1.load();
           Stage stage = new Stage();
           stage.setScene(new Scene(root1));
           stage.show();

           Stage stage1 = (Stage) closeButton.getScene().getWindow();
           stage1.close();

        }
    }
    public void click(MouseEvent mouseEvent) {

        Circle circle = (Circle) mouseEvent.getSource();
        String clicked_id = circle.getId();

        if(clicked_id.contains("1")) mohre = 1;
        if(clicked_id.contains("2")) mohre = 2;
        if(clicked_id.contains("3")) mohre = 3;
        if(clicked_id.contains("4")) mohre = 4;
        System.out.println("id : "+clicked_id);
        System.out.println("mohre : "+mohre);

        if (!clicked_id.contains(your_color)){
            JOptionPane.showMessageDialog(null,"مهره خود را انتخاب کنید!!","message",JOptionPane.ERROR_MESSAGE);
        }else{
            if (tasing==true){
                moving[mohre-1] = moving[mohre-1] + moving_in_total;

                if (clicked_id.contains("s")&&tas_number==6){
                    clicked_id = clicked_id.replace("s","t");
                    circle.setId(clicked_id);
                    circle.setLayoutX(first_place.getLayoutX());
                    circle.setLayoutY(first_place.getLayoutY());

                    if(your_color.equals("red")) moving[mohre-1] = 18;
                    else if(your_color.equals("yellow")) moving[mohre-1] = 9;
                    else if(your_color.equals("blue")) moving[mohre-1] = 0;
                    else if(your_color.equals("green")) moving[mohre-1] = 27;

                    moving_in_total = 0;
                    tasing=false;

                    String position_send = "position,"+client.group+","+your_color+","+mohre+","+circle.getLayoutX()+","+circle.getLayoutY()+","+start_controler.game_number;
                    client.clientWriter.println(position_send);
                    client.clientWriter.flush();

                }else if (!clicked_id.contains("s")){
                    double x = 0,y =0;
                    if (moving[mohre-1]>36) moving[mohre-1]= moving[mohre-1]-36;

                    if(end-gone_places[mohre-1]<tas_number){
                        System.out.println("where is the ball : x : "+circle.getLayoutX()+" y : "+circle.getLayoutY());
                        x = circle.getLayoutX();
                        y = circle.getLayoutY();
                        JOptionPane.showMessageDialog(null,"ای مهره را نمیتوانید حرکت دهید!!!","message",JOptionPane.ERROR_MESSAGE);
                    }else{
                        if(gone_places[mohre-1]+tas_number>36){
                            if (is_inside[mohre-1]==false){
                                inside++;
                                System.out.println("the number of mohre inside is : "+inside);
                            }
                            is_inside[mohre-1]=true;
                            int go_inside = gone_places[mohre-1]+tas_number-36;
                            if(go_inside==last_place){
                                last_place--;
                                end--;
                            }
                            System.out.println("go inside is : "+go_inside);
                            x = pane.lookup("#"+your_color+"place"+go_inside).getLayoutX();
                            y = pane.lookup("#"+your_color+"place"+go_inside).getLayoutY();
                            circle.setLayoutX(x);
                            circle.setLayoutY(y);

                            if(inside==4){

                                System.out.println("you win!!!");
                                client.clientWriter.println("end,"+client.group+","+your_color+","+start_controler.game_number+","+(how_many_person_in_game*start_controler.getting_money));
                                client.clientWriter.flush();

                            }
                        }else{

                            x = pane.lookup("#place"+moving[mohre-1]).getLayoutX();
                            y = pane.lookup("#place"+moving[mohre-1]).getLayoutY();
                            circle.setLayoutX(x);
                            circle.setLayoutY(y);
                            String shuted_color = null;
                            for (int j=1;j<=4;j++){
                                if (j==1){
                                    shuted_color = "blue";
                                }else if (j==2){
                                    shuted_color = "yellow";
                                }else if (j==3){
                                    shuted_color = "green";
                                }else if (j==4){
                                    shuted_color = "red";
                                }
                                if (!shuted_color.equals(your_color)){
                                    for (int i=1;i<=4;i++){
                                        System.out.println("wanted shape : "+shuted_color+i+"s");
                                        if((x == pane.lookup("#"+shuted_color+i+"s").getLayoutX()&& y == pane.lookup("#"+shuted_color+i+"s").getLayoutY())){

                                            System.out.println("you shuted + "+shuted_color+i);
                                            client.clientWriter.println("shut,"+shuted_color+","+i+","+start_controler.game_number+","+client.group+","+x+","+y);
                                            client.clientWriter.flush();
                                        }
                                    }
                                }
                            }


                        }
                        System.out.println("moving : "+moving[mohre-1]);

                        moving_in_total=0;
                        tasing=false;
                        gone_places[mohre-1]=gone_places[mohre-1] + tas_number;
                    }

                    System.out.println("you have gone : "+gone_places[mohre-1]);
                    String position_send = "position,"+client.group+","+your_color+","+mohre+","+x+","+y+","+start_controler.game_number;
                    client.clientWriter.println(position_send);
                    client.clientWriter.flush();
                }else{
                    JOptionPane.showMessageDialog(null,"ای مهره را نمیتوانید حرکت دهید!!!","message",JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null,"اول تاس بیانداز!!!","message",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public void tas(ActionEvent actionEvent) {
        System.out.println(turn);
        System.out.println(your_color);

        if((turn%4==1&&your_color.equals("blue"))||(turn%4==2&&your_color.equals("yellow"))||
                (turn%4==3&&your_color.equals("green"))||(turn%4==0&&your_color.equals("red"))){
            rand = new Random();
            tas_number = rand.nextInt(6)+1;
            if(tas_number != 6){
                client.clientWriter.println("turn,"+client.group+","+client.person_ingoup+","+start_controler.game_number);
                client.clientWriter.flush();
            }
            tas.setText(tas_number+"");
            moving_in_total = moving_in_total + tas_number;
            tasing = true;

        }else{
            JOptionPane.showMessageDialog(null,"نوبت شما نیست!!","message",JOptionPane.ERROR_MESSAGE);
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            loading_controler.th.stop();
            loading_controler.timeline.stop();
        }catch (Exception e){
            loading_controler_friend.th.stop();
            loading_controler_friend.timeline.stop();
            pbtn1.setDisable(true);
            pbtn2.setDisable(true);
            pbtn3.setDisable(true);
            pbtn4.setDisable(true);
            six_number.setDisable(true);
            want_number.setDisable(true);
            unShut.setDisable(true);
        }


        timeline_wining = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (p==1){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("winner.fxml"));
                    Parent root = null;
                    try {
                        root = (Parent)fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    Stage stage1 = (Stage) tas.getScene().getWindow();
                    stage1.close();
                }
            }
        }));
        timeline_wining.setCycleCount(Animation.INDEFINITE);
        timeline_wining.play();



        thread_of_getting_position = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    String message = client.clientReader[1].nextLine();
                    System.out.println("message : "+message);
                    String[] position = message.split(",");
                    if(position[0].equals("position")){
                        System.out.println("position");
                        double x = Double.parseDouble(position[4]);
                        double y = Double.parseDouble(position[5]);
                        String mohre = position[3];
                        String color = position[2];
                        try{
                            pane.lookup("#"+color+mohre+"s").setLayoutX(x);
                            pane.lookup("#"+color+mohre+"s").setLayoutY(y);
                        }catch (Exception e){
                            pane.lookup("#"+color+mohre+"t").setLayoutX(x);
                            pane.lookup("#"+color+mohre+"t").setLayoutY(y);
                        }
                    }else if(position[0].equals("profile")){
                        message_of_profile = message;

                    }else if(position[0].equals("turn")){
                        turn = Integer.parseInt(position[1]);
                        System.out.println("turn is : "+ turn);
                    }else if(position[0].equals("friend")){

                        int x = JOptionPane.showConfirmDialog(null,"the "+position[5]+" color send for you friend request /n" +
                                "(User name : "+position[6]+"/n do you acceppt???","friend",JOptionPane.YES_NO_OPTION);
                        if (x==0){
                            client.clientWriter.println("friend_answer,accept,"+start_controler.game_number+","+client.group+","+position[4]+","+position[6]);
                            client.clientWriter.flush();
                        }else{
                            client.clientWriter.println("friend_answer,notaccept,"+start_controler.game_number+","+client.group+","+position[4]+","+position[6]);
                            client.clientWriter.flush();
                        }
                    }else if(position[0].equals("friend_answer")){
                        String friend_request_message = position[1];
                        System.out.println("your freind message is : "+friend_request_message);
                    }else if(position[0].equals("end")){
                        winner_message = message;
                        p=1;
                    }else if (position[0].equals("shut")){
                        propertise_of_shuted_circle = position[1]+position[2];
                        Circle shuted_circle;
                        if ((Circle) pane.lookup("#"+propertise_of_shuted_circle+"s")!=null){
                            shuted_circle = (Circle) pane.lookup("#"+propertise_of_shuted_circle+"s");
                        }else{
                            shuted_circle = (Circle) pane.lookup("#"+propertise_of_shuted_circle+"t");
                        }
                        if(position[1].equals("blue")){
                                shuted_circle.setLayoutX(blue_first_palce[Integer.parseInt(position[2])-1][0]);
                                shuted_circle.setLayoutY(blue_first_palce[Integer.parseInt(position[2])-1][1]);
                        }else if (position[1].equals("yellow")){
                                shuted_circle.setLayoutX(yellow_first_palce[Integer.parseInt(position[2])-1][0]);
                                shuted_circle.setLayoutY(yellow_first_palce[Integer.parseInt(position[2])-1][1]);
                        }else if (position[1].equals("green")){
                                shuted_circle.setLayoutX(green_first_palce[Integer.parseInt(position[2])-1][0]);
                                shuted_circle.setLayoutY(green_first_palce[Integer.parseInt(position[2])-1][1]);
                        }else if (position[1].equals("red")){
                                shuted_circle.setLayoutX(red_first_palce[Integer.parseInt(position[2])-1][0]);
                                shuted_circle.setLayoutY(red_first_palce[Integer.parseInt(position[2])-1][1]);
                        }if (position[1].equals(your_color)) {
                            shuted_circle.setId(propertise_of_shuted_circle+"s");
                            if(your_color.equals("red")) moving[mohre-1] = 18;
                            else if(your_color.equals("yellow")) moving[mohre-1] = 9;
                            else if(your_color.equals("blue")) moving[mohre-1] = 0;
                            else if(your_color.equals("green")) moving[mohre-1] = 27;
                            shuter_x= Double.parseDouble(position[5]);
                            shuter_y= Double.parseDouble(position[6]);
                            restore_circle = shuted_circle;
                        }

                    }
                }
            }
        });
        thread_of_getting_position.start();
        how_many_person_in_game = loading_controler.online_person_ready_to_play;
        if (client.person_ingoup%4==0) {
            your_color="red";
            first_place = rplace0;
        }
        else if (client.person_ingoup%4==1) {
            your_color="blue";
            first_place = bplace0;
        }
        else if (client.person_ingoup%4==2) {
            your_color="yellow";
            first_place = yplace0;
        }
        else if (client.person_ingoup%4==3){
            your_color="green";
            first_place = gplace0;
        }


    }
    public void yellow_profile(ActionEvent actionEvent) throws IOException, InterruptedException {
        client.clientWriter.println("profile,"+(client.group)+","+1+","+start_controler.game_number);
        client.clientWriter.flush();
        Thread.sleep(1500);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
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
    public void red_profile(ActionEvent actionEvent) throws IOException, InterruptedException {
        client.clientWriter.println("profile,"+(client.group)+","+3+","+start_controler.game_number);
        client.clientWriter.flush();
        Thread.sleep(1500);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
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
    public void green_profile(ActionEvent actionEvent) throws IOException, InterruptedException {
        client.clientWriter.println("profile,"+(client.group)+","+2+","+start_controler.game_number);
        client.clientWriter.flush();
        Thread.sleep(1500);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
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
    public void blue_profile(ActionEvent actionEvent) throws IOException, InterruptedException {
        client.clientWriter.println("profile,"+(client.group)+","+0+","+start_controler.game_number);
        client.clientWriter.flush();
        Thread.sleep(1500);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
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
    public void six_number(ActionEvent actionEvent) {
        if((turn%4==1&&your_color.equals("blue"))||(turn%4==2&&your_color.equals("yellow"))||
                (turn%4==3&&your_color.equals("green"))||(turn%4==0&&your_color.equals("red"))){

            client.clientWriter.println("hint,"+((how_many_person_in_game*start_controler.getting_money)/10));
            client.clientWriter.println("turn,"+client.group+","+client.person_ingoup+","+start_controler.game_number);
            client.clientWriter.flush();

            tas_number = 6;
            tas.setText(tas_number+"");
            moving_in_total = moving_in_total + tas_number;
            tasing = true;
            six_number.setDisable(true);
        }else{
            JOptionPane.showMessageDialog(null,"نوبت شما نیست!!","message",JOptionPane.ERROR_MESSAGE);
        }
    }
    public void unShut(ActionEvent actionEvent) {

        if(restore_circle!=null&&shuter_x!=0&&shuter_y!=0){
            restore_circle.setId(restore_circle.getId().replace("s","t"));
            restore_circle.setLayoutX(shuter_x+17);
            restore_circle.setLayoutY(shuter_y+17);
            shuter_y=0;
            shuter_x=0;
            unShut.setDisable(true);
        }else{
            JOptionPane.showMessageDialog(null,"شما در حال حاضر نمیتوانید از این کمکی استفاده کنید!!!","message",JOptionPane.ERROR_MESSAGE);
        }
    }
    public void want_number(ActionEvent actionEvent) {
        insert_number.setVisible(true);
    }
    public void enter(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("Enter")){
            if (insert_number.getText().equals("")){
                JOptionPane.showMessageDialog(null,"خالی نمیتوانید وارد کنید!!!","message",JOptionPane.ERROR_MESSAGE);
            }else if(!(insert_number.getText().equals("1")||insert_number.getText().equals("2")||insert_number.getText().equals("3")||
                    insert_number.getText().equals("4")||insert_number.getText().equals("5")||insert_number.getText().equals("6"))){
                JOptionPane.showMessageDialog(null,"عدد از 1 تا 6 وارد کنید","message",JOptionPane.ERROR_MESSAGE);
            }else{

                if((turn%4==1&&your_color.equals("blue"))||(turn%4==2&&your_color.equals("yellow"))||
                        (turn%4==3&&your_color.equals("green"))||(turn%4==0&&your_color.equals("red"))){

                    client.clientWriter.println("hint,"+((how_many_person_in_game*start_controler.getting_money)/10));
                    client.clientWriter.println("turn,"+client.group+","+client.person_ingoup+","+start_controler.game_number);
                    client.clientWriter.flush();

                    tas_number = Integer.parseInt(insert_number.getText());
                    tas.setText(tas_number+"");
                    moving_in_total = moving_in_total + tas_number;
                    tasing = true;
                    want_number.setDisable(false);
                }else{
                    JOptionPane.showMessageDialog(null,"نوبت شما نیست!!","message",JOptionPane.ERROR_MESSAGE);
                }

                want_number.setDisable(true);
                insert_number.setVisible(false);
            }
        }
    }
    public void air(MouseEvent mouseEvent) {
        insert_number.setVisible(false);
    }
    public void yellow_friend(ActionEvent actionEvent) {
        if (your_color.equals("yellow")){
            JOptionPane.showMessageDialog(null,"َشما نمیتوانید با خودتان دوست شوید!!!","message",JOptionPane.ERROR_MESSAGE);
        }else{
            client.clientWriter.println("friend,"+start_controler.game_number+","+client.group+","+1+","+client.person_ingoup+","+your_color);
            client.clientWriter.flush();
        }
    }
    public void green_friend(ActionEvent actionEvent) {
        if (your_color.equals("green")){
            JOptionPane.showMessageDialog(null,"َشما نمیتوانید با خودتان دوست شوید!!!","message",JOptionPane.ERROR_MESSAGE);
        }else{
            client.clientWriter.println("friend,"+start_controler.game_number+","+client.group+","+2+","+client.person_ingoup+","+your_color);
            client.clientWriter.flush();
        }
    }
    public void blue_friend(ActionEvent actionEvent) {
        if (your_color.equals("blue")){
            JOptionPane.showMessageDialog(null,"َشما نمیتوانید با خودتان دوست شوید!!!","message",JOptionPane.ERROR_MESSAGE);
        }else{
            client.clientWriter.println("friend,"+start_controler.game_number+","+client.group+","+0+","+client.person_ingoup+","+your_color);
            client.clientWriter.flush();
        }
    }
    public void red_friend(ActionEvent actionEvent) {
        if (your_color.equals("red")){
            JOptionPane.showMessageDialog(null,"َشما نمیتوانید با خودتان دوست شوید!!!","message",JOptionPane.ERROR_MESSAGE);
        }else{
            client.clientWriter.println("friend,"+start_controler.game_number+","+client.group+","+4+","+client.person_ingoup+","+your_color);
            client.clientWriter.flush();
        }
    }
}
