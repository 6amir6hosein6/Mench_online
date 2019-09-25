package Server;

/**
 * Created by Techno Service on 7/1/2018.
 */
public class game {
    private connected_users[] connected_users = new connected_users[4];
    private int turn=1;
    private boolean is_it_avalible;

    public game(connected_users[] connected_users, boolean is_it_avalible,int turn) {
        this.connected_users = connected_users;
        this.is_it_avalible = is_it_avalible;
        this.turn=turn;
    }

    public connected_users[] getConnected_users() {
        return connected_users;
    }
    public void setConnected_users(connected_users[] connected_users) {this.connected_users = connected_users;}
    public boolean isIs_it_avalible() {
        return is_it_avalible;
    }
    public void setIs_it_avalible(boolean is_it_avalible) {
        this.is_it_avalible = is_it_avalible;
    }
    public int getTurn() {
        return turn;
    }
    public void setTurn(int turn) {
        this.turn = turn;
    }
}
