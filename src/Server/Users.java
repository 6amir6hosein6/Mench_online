package Server;

/**
 * Created by Techno Service on 6/4/2018.
 */
public class Users {
    private String Username;
    private String password;
    private String img;
    private int id;
    private int coin;
    private String friend;
    private int can_play;


    public Users(String name, String password, String img, int coin, String friend , int can_play){
        this.Username = name;
        this.password = password;
        this.coin=coin;
        this.img=img;
        this.friend=friend;
        this.can_play=can_play;
    }
    public String getFriend() {return friend;}

    public void setFriend(String friend) {this.friend = friend;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getCan_play() {
        return can_play;
    }

    public void setCan_play(int can_play) {
        this.can_play = can_play;
    }
}
