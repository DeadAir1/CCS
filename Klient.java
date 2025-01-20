import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Arrays;
import java.util.Enumeration;

public class Klient extends Thread {
    public static void main(String[] args)
            throws UnknownHostException, SocketException, IOException, InterruptedException {
    final int PORT = 12340;
    DatagramSocket socket = new DatagramSocket();
    byte buff[] = "CCS DISCOVER".getBytes();
    InetAddress receiverAddress = InetAddress.getByName("localhost");
    int receiverPort = PORT;
    DatagramPacket packetToSend = new DatagramPacket(buff, buff.length, receiverAddress, receiverPort);
    socket.send(packetToSend);
    Arrays.fill(buff, (byte) 0); 
    DatagramPacket receivePacket = new DatagramPacket(buff, buff.length);
    socket.receive(receivePacket);
    String val = new String(buff, 0, receivePacket.getLength());
    System.out.println(val);
        socket.close();
        Socket clientSocket = new Socket(receivePacket.getAddress(), receiverPort);
        DataOutputStream outToServer =
                new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer =
                new BufferedReader(new
                        InputStreamReader(clientSocket.getInputStream()));
        String[] tab={"ADD","SUB","MUL","DIV"};
    while(true) {
        for (int i = 0; i < 4; i++) {
            int val1 =(int)(Math.random() * (10-1)+1);
            int val2 =(int)(Math.random() * ((10-1)+1));
            String line = tab[i]+ " " + val2 + "\n";
            outToServer.writeBytes(line);
            System.out.print("To server -> " + line);
            System.out.println("From server -> " + inFromServer.readLine());

        }socket.close();




    }
    }


    
}




