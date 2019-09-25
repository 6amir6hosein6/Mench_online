package Server;

import java.util.ArrayList;

/**
 * Created by Techno Service on 6/15/2018.
 */
public class making_game {
    static ArrayList<ArrayList<String>> users = new ArrayList<>();

    public static void make(String username){
        int avalibel=0;
        int group_number=0;
        while (true){
            for (int i =0 ; i<4 ; i++){
                if(users.get(0).get(i).equals("")){
                    users.get(0).add(username);
                    
                }
            }
        }




    }
}
