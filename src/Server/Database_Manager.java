package Server;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Techno Service on 5/24/2018.
 */
public class Database_Manager {
    private Connection db_connection;
    public void connect_to_database() throws SQLException {
        db_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mench","root","0021979677");
    }
    public void insert(String username ,String password,String img , int coin , int can_play) throws SQLException {
        Statement statement = db_connection.createStatement();
        statement.execute("insert into user values ('"+username+"','"+password+"','"+img+"',"+coin+","+can_play+",0)");

    }


    public void make_money_less(int coin , String username) throws SQLException {
        Statement statement = db_connection.createStatement();
        statement.execute("UPDATE user SET coin = (SELECT coin where username = '"+username+"')-"+coin+" WHERE username='"+username+"'");
    }
    public void make_money_more(int coin , String username) throws SQLException {
        Statement statement = db_connection.createStatement();
        statement.execute("UPDATE user SET coin = (SELECT coin where username = '"+username+"')+"+coin+" WHERE username='"+username+"'");
    }

    public void change_can_play(int can_paly , String username) throws SQLException {
        Statement statement = db_connection.createStatement();
        statement.execute("UPDATE user SET can_play = "+can_paly+" WHERE username='"+username+"'");
    }

    public void adding_friend(String friend_name , String username) throws SQLException {
        Statement statement = db_connection.createStatement();
        friend_name = "/"+friend_name;
        statement.execute("UPDATE user SET friend = CONCAT(friend, '"+friend_name+"') WHERE username='"+username+"'");

    }
    public void add_new_friend (String friend_name , String username) throws SQLException {
        Statement statement = db_connection.createStatement();
        statement.execute("update user set friend='"+friend_name+"' where username='"+username+"'");
    }
    public ArrayList<Users> account() throws SQLException {
        ArrayList<Users> accounts = new ArrayList<>();
        Statement statement = db_connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user");
        if(resultSet.first()){
            do{
                Users account = new Users(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getInt(6)
                );
                accounts.add(account);
            }while (resultSet.next());
        }
        return accounts;
    }
}
