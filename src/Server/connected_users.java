package Server;

/**
 * Created by Techno Service on 7/1/2018.
 */
public class connected_users {
    private int id_in_clinets=0;
    private int id_in_join=0;
    private String user_name="asd";

    public connected_users(int id_in_clinets, int id_in_join, String user_name) {
        this.id_in_clinets = id_in_clinets;
        this.id_in_join = id_in_join;
        this.user_name = user_name;
    }

    public int getId_in_clinets() {
        return id_in_clinets;
    }

    public void setId_in_clinets(int id_in_clinets) {
        this.id_in_clinets = id_in_clinets;
    }

    public int getId_in_join() {
        return id_in_join;
    }

    public void setId_in_join(int id_in_join) {
        this.id_in_join = id_in_join;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
