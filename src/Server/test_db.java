package Server;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Techno Service on 6/5/2018.
 */
public class test_db {
    public static  void main(String[] args) throws SQLException {
        Database_Manager db = new Database_Manager();
        db.connect_to_database();
        ArrayList<Users> us = db.account();
        System.out.println(us.get(0).getFriend());

    }
}
