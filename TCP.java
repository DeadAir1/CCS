import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP extends Thread {
    int PORT;
    public TCP (int port){
        this.PORT=port;
    }

    ServerSocket welcomeSocket;
    Socket connectionSocket;
    static final Raport raport=new Raport();
    public void run(){
        try {
            welcomeSocket = new ServerSocket(PORT);
            //Rozpoczencie raportowania
            raport.start();
        } catch (IOException e) {
            System.err.println("Exception during opening welcome socket!");
        }
        while(true){
            try {
                //Oczekiwanie na polaczenie
                connectionSocket = welcomeSocket.accept();
                raport.setValues_last_10_sec("connection_count");
                ClientHandler clientHandler = new ClientHandler(connectionSocket);
                clientHandler.start();
            } catch (IOException e) {
                System.err.println("Unsuccessful connection to server!");
            }
        }

   }


}
