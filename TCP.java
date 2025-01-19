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
    Raport raport=new Raport();
    public void run(){
        //Rozpoczencie raportowania
        raport.start();
        try {
            welcomeSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println("Nie udalo sie odtworzyc server TCP na podanym porcie!");
        }
        while(true){
            try {
                //Oczekiwanie na polaczenie
                connectionSocket = welcomeSocket.accept();
                raport.setValues_last_10_sec("connection_count");
                ClientHandler clientHandler = new ClientHandler(connectionSocket, raport);
                clientHandler.start();
            } catch (IOException e) {
                System.err.println("Pocajfoiwj");
            }
        }

   }


}
