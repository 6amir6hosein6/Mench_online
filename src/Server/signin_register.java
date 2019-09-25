package Server;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Techno Service on 6/5/2018.
 */
public class signin_register {
    int x=0;
    static ArrayList<ArrayList<game>> online_users = new ArrayList<ArrayList<game>>();
    static connected_users[] users_id = new connected_users[4];
    static int[] person_in_group = {1,1,1,1};
    static int[] ii={0,0,0,0};
    String user_name;

    public void sing_login_game(String massage , Scanner socketReader ,PrintWriter socketWriter,int i) throws SQLException {
        Database_Manager db = new Database_Manager();
        db.connect_to_database();
        ArrayList<Users> users = db.account();
        String[] user_information = massage.split(",");

        if (user_information[0].equals("signin")){
            boolean founded = false;
            for (Users users1 : users){
                if (users1.getUsername().equals(user_information[1])){
                    founded=true;
                    if (users1.getPassword().equals(user_information[2])){
                        socketWriter.println("good,"+users1.getCoin());
                        socketWriter.flush();
                        user_name = user_information[1];
                        Server.number_of_joiend_user++;
                        Server.connected_users.add(new connected_users(Server.number_of_connected_client,Server.number_of_joiend_user,user_information[1]));

                        db.change_can_play(1,user_name);


                    }else{
                        socketWriter.println("Wrong password");
                        socketWriter.flush();
                    }
                }
            }if (founded==false){
                socketWriter.println("no person has found");
                socketWriter.flush();
            }
        }else if(user_information[0].equals("register")){
            int avalible=1;

            for (Users users1 : users) {
                if (users1.getUsername().equals(user_information[1])) {
                    socketWriter.println("This user name have taken!!");
                    socketWriter.flush();
                    avalible=0;
                }
            }if(avalible==1) {
                String username = user_information[1];
                String password = user_information[2];
                String img = user_information[3];
                    db.insert(username,password,img,100,1);
                    socketWriter.println("good");
                    socketWriter.flush();
                }
        }else if (user_information[0].equals("start")){
            System.out.println("you join the group .. . . ..");

            db.make_money_less(Integer.parseInt(user_information[3]),user_name);
            db.change_can_play(2,user_name);


            if (online_users.get(Integer.parseInt(user_information[2])).get(online_users.get(Integer.parseInt(user_information[2])).size()-1).isIs_it_avalible()==false){
                System.out.println("making new group becus pf cancel to joinngng");
                person_in_group[Integer.parseInt(user_information[2])] = 1 ;

                users_id = new connected_users[4];
                online_users.get(Integer.parseInt(user_information[2])).add(new game(users_id,true , 1));
                online_users.get(Integer.parseInt(user_information[2])).get(online_users.get(Integer.parseInt(user_information[2])).size()-1).getConnected_users()[person_in_group[Integer.parseInt(user_information[2])]-1] = new connected_users(Integer.parseInt(user_information[1]),Server.number_of_joiend_user,user_name);
                socketWriter.println(person_in_group[Integer.parseInt(user_information[2])]+","+online_users.get(Integer.parseInt(user_information[2])).size());
                socketWriter.flush();
                ii[Integer.parseInt(user_information[2])]++;


                for (int iii=0;iii<person_in_group[Integer.parseInt(user_information[2])] ;iii++){
                    System.out.println("sent to client with this id : "+(users_id[iii].getId_in_clinets()-1));
                    System.out.println("this value : "+person_in_group[Integer.parseInt(user_information[2])]);
                    Server.serverWriter.get(users_id[iii].getId_in_clinets()-1).println("online,"+person_in_group[Integer.parseInt(user_information[2])]);
                    Server.serverWriter.get(users_id[iii].getId_in_clinets()-1).flush();
                }
                person_in_group[Integer.parseInt(user_information[2])]++;
            } else if(person_in_group[Integer.parseInt(user_information[2])]%4==0){
                socketWriter.println(person_in_group[Integer.parseInt(user_information[2])]+","+online_users.get(Integer.parseInt(user_information[2])).size());
                socketWriter.flush();
                online_users.get(Integer.parseInt(user_information[2])).get(online_users.get(Integer.parseInt(user_information[2])).size()-1).getConnected_users()[person_in_group[Integer.parseInt(user_information[2])]-1] = new connected_users(Integer.parseInt(user_information[1]),Server.number_of_joiend_user,user_name);
                ii[Integer.parseInt(user_information[2])]++;
                for (int iii=0;iii<person_in_group[Integer.parseInt(user_information[2])] ;iii++){
                    System.out.println("sent to client with this id : "+(users_id[iii].getId_in_clinets()-1));
                    System.out.println("this value : "+person_in_group[Integer.parseInt(user_information[2])]);
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[2])).get(online_users.get(Integer.parseInt(user_information[2])).size()-1).getConnected_users()[iii].getId_in_clinets()-1).println("online,"+person_in_group[Integer.parseInt(user_information[2])]);
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[2])).get(online_users.get(Integer.parseInt(user_information[2])).size()-1).getConnected_users()[iii].getId_in_clinets()-1).flush();
                }
                person_in_group[Integer.parseInt(user_information[2])] = 1 ;
                online_users.get(Integer.parseInt(user_information[2])).add(new game(null,true , 1));
                users_id = new connected_users[4];
            }else{
                socketWriter.println(person_in_group[Integer.parseInt(user_information[2])]+","+online_users.get(Integer.parseInt(user_information[2])).size());
                socketWriter.flush();
                online_users.get(Integer.parseInt(user_information[2])).get(online_users.get(Integer.parseInt(user_information[2])).size()-1).setConnected_users(users_id);
                online_users.get(Integer.parseInt(user_information[2])).get(online_users.get(Integer.parseInt(user_information[2])).size()-1).getConnected_users()[person_in_group[Integer.parseInt(user_information[2])]-1] = new connected_users(Integer.parseInt(user_information[1]),Server.number_of_joiend_user,user_name);
                online_users.get(Integer.parseInt(user_information[2])).get(online_users.get(Integer.parseInt(user_information[2])).size()-1).setTurn(1);

                for (int iii=0;iii<person_in_group[Integer.parseInt(user_information[2])] ;iii++){
                    System.out.println("sent to client with this id : "+(users_id[iii].getId_in_clinets()-1));
                    System.out.println("this value : "+person_in_group[Integer.parseInt(user_information[2])]);

                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[2])).get(online_users.get(Integer.parseInt(user_information[2])).size()-1).getConnected_users()[iii].getId_in_clinets()-1).println("online,"+person_in_group[Integer.parseInt(user_information[2])]);
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[2])).get(online_users.get(Integer.parseInt(user_information[2])).size()-1).getConnected_users()[iii].getId_in_clinets()-1).flush();
                }
                person_in_group[Integer.parseInt(user_information[2])]++;
            }



        }else if (user_information[0].equals("profile")){
            System.out.println("you join profile ..... ");

            int number_in_group = Integer.parseInt(user_information[2]);
            try{
                String wanted_user_name = online_users.get(Integer.parseInt(user_information[3])).get(Integer.parseInt(user_information[1])).getConnected_users()[number_in_group].getUser_name();
                System.out.println("game number  : " + user_information[3]);
                System.out.println("group : " + user_information[1]);
                System.out.println("in group ? : " + number_in_group);

                for (Users users2 : users) {
                    if (users2.getUsername().equals(wanted_user_name)) {
                        String username1 = wanted_user_name;
                        String img = users2.getImg();
                        int coin = users2.getCoin();
                        System.out.println(username1 + "," + img + "," + coin);
                        socketWriter.println("profile," + username1 + "," + img + "," + coin);
                        socketWriter.flush();
                        System.out.println("profile message sent!!!");
                    }
                }
            }catch (Exception e){
                socketWriter.println("profile," + "no person" + "," + "nothing" + "," + null);
                socketWriter.flush();
            }

        }else if (user_information[0].equals("profile_myself")){
            System.out.println("you join profile_self ..... ");

            int number_in_group = Integer.parseInt(user_information[2]);

                String wanted_user_name = user_name;


                for (Users users2 : users) {
                    if (users2.getUsername().equals(wanted_user_name)) {
                        String username1 = wanted_user_name;
                        String img = users2.getImg();
                        int coin = users2.getCoin();
                        String friends = users2.getFriend();
                        System.out.println(username1 + "," + img + "," + coin);
                        socketWriter.println("profile_self," + username1 + "," + img + "," + coin + "," + friends);
                        socketWriter.flush();
                        socketWriter.println("profile_self," + username1 + "," + img + "," + coin + "," + friends);
                        socketWriter.flush();
                        System.out.println("profile_self message sent!!!");
                    }
                }

        }else if (user_information[0].equals("position")){
            int group = Integer.parseInt(user_information[1]);
            System.out.println("group : "+group);
            for (int in=0 ;in<4;in++){
                if (online_users.get(Integer.parseInt(user_information[6])).get(group).getConnected_users()[in]!=null){
                    System.out.println("sending for : "+online_users.get(Integer.parseInt(user_information[6])).get(group).getConnected_users()[in].getId_in_clinets());
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[6])).get(group).getConnected_users()[in].getId_in_clinets()-1).println(massage);
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[6])).get(group).getConnected_users()[in].getId_in_clinets()-1).flush();
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[6])).get(group).getConnected_users()[in].getId_in_clinets()-1).println(massage);
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[6])).get(group).getConnected_users()[in].getId_in_clinets()-1).flush();
                }


            }

        }else if (user_information[0].equals("turn")){
            int group = Integer.parseInt(user_information[1]);
            int id_in_group = Integer.parseInt(user_information[2]);
            online_users.get(Integer.parseInt(user_information[3])).get(group).setTurn(online_users.get(Integer.parseInt(user_information[3])).get(group).getTurn()+1);
            if (online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[id_in_group]==null){
                System.out.println("change to one");
                online_users.get(Integer.parseInt(user_information[3])).get(group).setTurn(1);
            }

            System.out.println("turn is : "+online_users.get(Integer.parseInt(user_information[3])).get(group).getTurn());
            for (int in=0 ;in<4;in++){
                if (online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in]!=null){
                    System.out.println("sending for : "+online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in].getId_in_clinets());
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in].getId_in_clinets()-1).println("turn,"+online_users.get(Integer.parseInt(user_information[3])).get(group).getTurn());
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in].getId_in_clinets()-1).flush();
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in].getId_in_clinets()-1).println("turn,"+online_users.get(Integer.parseInt(user_information[3])).get(group).getTurn());
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in].getId_in_clinets()-1).flush();
                }
            }

        }else if (user_information[0].equals("play")){

            int group = Integer.parseInt(user_information[1]);
            System.out.println(online_users.get(Integer.parseInt(user_information[2])).get(group).isIs_it_avalible());
            online_users.get(Integer.parseInt(user_information[2])).get(group).setIs_it_avalible(false);
            System.out.println(online_users.get(Integer.parseInt(user_information[2])).get(group).isIs_it_avalible());
            for (int in=0 ;in<4;in++){
                if (online_users.get(Integer.parseInt(user_information[2])).get(group).getConnected_users()[in]!=null){
                    System.out.println("sending game order to person in game number : " + user_information[2] + " and group : "+group+" with id : "+online_users.get(Integer.parseInt(user_information[2])).get(group).getConnected_users()[in].getId_in_clinets());
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[2])).get(group).getConnected_users()[in].getId_in_clinets()-1).println("play");
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[2])).get(group).getConnected_users()[in].getId_in_clinets()-1).flush();
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[2])).get(group).getConnected_users()[in].getId_in_clinets()-1).println("play");
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[2])).get(group).getConnected_users()[in].getId_in_clinets()-1).flush();
                }
            }
        }else if (user_information[0].equals("end")){
            db.make_money_more(Integer.parseInt(user_information[4]),user_name);
            int group = Integer.parseInt(user_information[1]);
            for (int in=0 ;in<4;in++){
                if (online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in]!=null){
                    System.out.println("sending for : "+online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in].getId_in_clinets());
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in].getId_in_clinets()-1).println(massage);
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in].getId_in_clinets()-1).flush();
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in].getId_in_clinets()-1).println(massage);
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in].getId_in_clinets()-1).flush();
                }
            }
        }else if(user_information[0].equals("online-person")){
            socketWriter.println(""+Server.number_of_joiend_user);
            socketWriter.flush();
            socketWriter.println(""+Server.number_of_joiend_user);
            socketWriter.flush();

        }else if(user_information[0].equals("hint")){
            db.make_money_less(Integer.parseInt(user_information[1]),user_name);

        }else if (user_information[0].equals("shut")){
            int group = Integer.parseInt(user_information[4]);
            int gamenumber = Integer.parseInt(user_information[3]);
            for (int in=0 ;in<4;in++){
                if (online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in]!=null){
                    System.out.println("sending for : "+online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in].getId_in_clinets());
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in].getId_in_clinets()-1).println(massage);
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in].getId_in_clinets()-1).flush();
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in].getId_in_clinets()-1).println(massage);
                    Server.serverWriter.get(online_users.get(Integer.parseInt(user_information[3])).get(group).getConnected_users()[in].getId_in_clinets()-1).flush();
                }
            }
        }else if (user_information[0].equals("friend")){
            int group = Integer.parseInt(user_information[2]);
            int gamenumber = Integer.parseInt(user_information[1]);
            int asking_friend_for_person_in_group = Integer.parseInt(user_information[3]);
            String friend_username = online_users.get(gamenumber).get(group).getConnected_users()[asking_friend_for_person_in_group].getUser_name();
            int i1 =0;
            System.out.println("friend is : "+users.get(0).getFriend());
            System.out.println("friend nae is : "+users.get(0).getUsername());
            for (Users users1 : users) {
                if (users1.getUsername().equals(user_name)){
                    System.out.println(" i : "+i1);
                    System.out.println("user is is is isi : "+users1);
                   if (users1.getFriend()==null){
                        Server.serverWriter.get(online_users.get(gamenumber).get(group).getConnected_users()[asking_friend_for_person_in_group].getId_in_clinets()-1).println(massage+","+user_name);
                        Server.serverWriter.get(online_users.get(gamenumber).get(group).getConnected_users()[asking_friend_for_person_in_group].getId_in_clinets()-1).flush();
                    }else if (users1.getFriend().contains(friend_username)){
                        Server.serverWriter.get(online_users.get(gamenumber).get(group).getConnected_users()[asking_friend_for_person_in_group].getId_in_clinets()-1).println("friend_answer,exist");
                        Server.serverWriter.get(online_users.get(gamenumber).get(group).getConnected_users()[asking_friend_for_person_in_group].getId_in_clinets()-1).flush();
                    }else {
                        Server.serverWriter.get(online_users.get(gamenumber).get(group).getConnected_users()[asking_friend_for_person_in_group].getId_in_clinets()-1).println(massage+","+user_name);
                        Server.serverWriter.get(online_users.get(gamenumber).get(group).getConnected_users()[asking_friend_for_person_in_group].getId_in_clinets()-1).flush();
                    }
                }
                i1++;
            }
        }else if (user_information[0].equals("friend_answer")){
            users = db.account();
            int group = Integer.parseInt(user_information[3]);
            int gamenumber = Integer.parseInt(user_information[2]);
            int answer_friend_for_person_in_group = Integer.parseInt(user_information[4]);
            String answer_friend_username = user_information[5];
            if (user_information[1].equals("accept")){
                for (Users users1:users){
                    int i1=0;
                    if (users1.getUsername().equals(user_name)){
                        if (users1.getFriend()==null){
                            db.add_new_friend(answer_friend_username,user_name);
                            users.get(i1).setFriend(users.get(i1).getFriend()+","+answer_friend_username);
                        }else {
                            db.adding_friend(answer_friend_username,user_name);
                            users.get(i1).setFriend(users.get(i1).getFriend()+","+answer_friend_username);
                        }
                    }
                    i1++;
                }
                for (Users users2:users){
                    int i1=0;
                    if (users2.getUsername().equals(answer_friend_username)){
                        if (users2.getFriend()==null){
                            db.add_new_friend(user_name,answer_friend_username);
                            users.get(i1).setFriend(users.get(i1).getFriend()+","+user_name);
                        }else {
                            db.adding_friend(user_name,answer_friend_username);
                            users.get(i1).setFriend(users.get(i1).getFriend()+","+user_name);
                        }
                    }
                }
                Server.serverWriter.get(online_users.get(gamenumber).get(group).getConnected_users()[answer_friend_for_person_in_group].getId_in_clinets()-1).println("friend_answer,accept");
                Server.serverWriter.get(online_users.get(gamenumber).get(group).getConnected_users()[answer_friend_for_person_in_group].getId_in_clinets()-1).flush();
            }else{
                Server.serverWriter.get(online_users.get(gamenumber).get(group).getConnected_users()[answer_friend_for_person_in_group].getId_in_clinets()-1).println("friend_answer,notaccept");
                Server.serverWriter.get(online_users.get(gamenumber).get(group).getConnected_users()[answer_friend_for_person_in_group].getId_in_clinets()-1).flush();
            }
        }else if (user_information[0].equals("friend_request_play")){
            int game = Integer.parseInt(user_information[2]);
            int group = Integer.parseInt(user_information[3]);
            int person = Integer.parseInt(user_information[4])-1;
            users = db.account();
            for (Users users2:users){
                if (users2.getUsername().equals(user_information[1])){
                    if (users2.getCan_play()==2){
                        Server.serverWriter.get(online_users.get(game).get(group).getConnected_users()[person].getId_in_clinets()-1).println("دوست شما در حال بازی کردن است!!");
                        Server.serverWriter.get(online_users.get(game).get(group).getConnected_users()[person].getId_in_clinets()-1).flush();
                    }else if (users2.getCan_play()==0){
                        Server.serverWriter.get(online_users.get(game).get(group).getConnected_users()[person].getId_in_clinets()-1).println("دوست شما انلاین نیست");
                        Server.serverWriter.get(online_users.get(game).get(group).getConnected_users()[person].getId_in_clinets()-1).flush();
                    }else{
                        int person_in_group_of_friend = 0;
                        for (connected_users connected_users : Server.connected_users ){
                            if (connected_users.getUser_name().equals(user_information[1])){
                                int user_client_id = connected_users.getId_in_clinets();
                                for (int per =0 ; per<4 ; per ++){
                                    if (online_users.get(3).get(group).getConnected_users()[per]==null){
                                        person_in_group_of_friend = per+1;
                                        System.out.println("innnnnnnnnnnnnnnnnn : "+person_in_group_of_friend);
                                        break;
                                    }
                                }
                                Server.serverWriter.get(user_client_id-1).println("friend_play,"+game+","+group+","+person+","+person_in_group_of_friend);
                                Server.serverWriter.get(user_client_id-1).flush();
                            }
                        }
                    }
                }
            }
        }else if (user_information[0].equals("play_friend_request_answer")){
            int game = Integer.parseInt(user_information[2]);
            int group = Integer.parseInt(user_information[3]);
            int person = Integer.parseInt(user_information[4]);
            if (user_information[1].equals("accept")){
                Server.serverWriter.get(online_users.get(game).get(group).getConnected_users()[person].getId_in_clinets()-1).println("دوست شما قبول کرد,play_request_of_friend");
                Server.serverWriter.get(online_users.get(game).get(group).getConnected_users()[person].getId_in_clinets()-1).flush();

                for (int per=0;per<4;per++){
                   if (online_users.get(3).get(group).getConnected_users()[per]==null){
                       System.out.println("joining group in place of "+per);
                       online_users.get(3).get(group).getConnected_users()[per] = new connected_users(Integer.parseInt(user_information[5]),Server.number_of_joiend_user,user_name);


                       for (int iii=0;iii<per+1 ;iii++){
                           System.out.println("this value : "+per);

                           Server.serverWriter.get(online_users.get(game).get(group).getConnected_users()[iii].getId_in_clinets()-1).println("online,"+per);
                           Server.serverWriter.get(online_users.get(game).get(group).getConnected_users()[iii].getId_in_clinets()-1).flush();
                           Server.serverWriter.get(online_users.get(game).get(group).getConnected_users()[iii].getId_in_clinets()-1).println("online,"+per);
                           Server.serverWriter.get(online_users.get(game).get(group).getConnected_users()[iii].getId_in_clinets()-1).flush();

                       }
                       break;
                   }
               }


            }else{
                Server.serverWriter.get(online_users.get(game).get(group).getConnected_users()[person].getId_in_clinets()-1).println("دوست شما قبول نکرد,play_request_of_friend");
                Server.serverWriter.get(online_users.get(game).get(group).getConnected_users()[person].getId_in_clinets()-1).flush();

            }

        }else if (user_information[0].equals("start_friend_play")){

            ArrayList<game> new_player4 = new ArrayList<>();
            connected_users[] con = new connected_users[4];
            new_player4.add(new game(con,true,1));
            signin_register.online_users.add(new_player4);

            socketWriter.println(1+","+online_users.get(Integer.parseInt(user_information[2])).size());
            socketWriter.flush();

            online_users.get(Integer.parseInt(user_information[2])).get(online_users.get(Integer.parseInt(user_information[2])).size()-1).getConnected_users()[1-1] = new connected_users(Integer.parseInt(user_information[1]),Server.number_of_joiend_user,user_name);
            online_users.get(Integer.parseInt(user_information[2])).get(online_users.get(Integer.parseInt(user_information[2])).size()-1).setTurn(1);
            Server.serverWriter.get(online_users.get(3).get(online_users.get(3).size()-1).getConnected_users()[0].getId_in_clinets()-1).println("online,"+1);
            Server.serverWriter.get(online_users.get(3).get(online_users.get(3).size()-1).getConnected_users()[0].getId_in_clinets()-1).flush();
            Server.serverWriter.get(online_users.get(3).get(online_users.get(3).size()-1).getConnected_users()[0].getId_in_clinets()-1).println("online,"+1);
            Server.serverWriter.get(online_users.get(3).get(online_users.get(3).size()-1).getConnected_users()[0].getId_in_clinets()-1).flush();
        }
    }
}