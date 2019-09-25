package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Techno Service on 6/4/2018.
 */
public class Server {


    static Scanner serverReader;
    static int i=0;
    static int number_of_connected_client=0;
    static int number_of_joiend_user=0;
    static ArrayList<connected_users> connected_users = new ArrayList<>();
    static ArrayList<PrintWriter> serverWriter = new ArrayList<>();
    public static void main(String[] args) throws IOException {


        ArrayList<Socket> users_socket = new ArrayList<>();

        ArrayList<game> new_player1 = new ArrayList<>();
        new_player1.add(new game(null,true,1));

        ArrayList<game> new_player2 = new ArrayList<>();
        new_player2.add(new game(null,true,1));

        ArrayList<game> new_player3 = new ArrayList<>();
        new_player3.add(new game(null,true,1));



        ArrayList<Thread> connected_user_thread = new ArrayList<>();
        ServerSocket serverSocket = new ServerSocket(3659);

        signin_register.online_users.add(new_player1);
        signin_register.online_users.add(new_player2);
        signin_register.online_users.add(new_player3);


        while (true){
            users_socket.add(serverSocket.accept());

            serverReader = new Scanner(users_socket.get(number_of_connected_client).getInputStream());
            serverWriter.add(new PrintWriter(users_socket.get(number_of_connected_client).getOutputStream()));
            connected_user_thread.add(new client_handler(users_socket.get(number_of_connected_client),serverReader,serverWriter.get(number_of_connected_client)));
            connected_user_thread.get(number_of_connected_client).start();
            System.out.println("client connected");
            serverWriter.get(number_of_connected_client).println(number_of_connected_client);
            serverWriter.get(number_of_connected_client).flush();
            number_of_connected_client++;


        }
    }
}

class client_handler extends Thread{
    Socket socket;
    Scanner serverReader;
    PrintWriter serverWriter;

    signin_register sr = new signin_register();
    public client_handler(Socket socket,Scanner serverReader , PrintWriter serverWriter){
        this.serverReader = serverReader;
        this.socket=socket;
        this.serverWriter=serverWriter;
    }
    @Override
    public void run() {
        int i=1;


        while (true) {
            try {
                System.out.println("request : "+i);
                String message = serverReader.nextLine();
                System.out.println("your message is : "+message);
                sr.sing_login_game(message,serverReader,serverWriter,i-1);

            } catch (SQLException e) {
                e.printStackTrace();
            }


            i++;
        }
    }
}
