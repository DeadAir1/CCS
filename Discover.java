import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class Discover extends Thread {
	int PORT;
	byte[] buf = new byte[1024];
	DatagramPacket packet = new DatagramPacket(buf,buf.length);
	public Discover(int port){
		this.PORT = port;
	}
	public void run(){
		try(DatagramSocket socket = new DatagramSocket(this.PORT)){
			while (true) {
				Arrays.fill(buf, (byte) 0);
				try {
					socket.receive(packet);
				} catch (IOException e) {
					System.err.println("Exception during getting message!");
					continue;
				}
				String val = new String(buf, 0, packet.getLength());
				if(val.equals("CCS DISCOVER")){
					byte[] responseBuf = "CCS FOUND".getBytes();
					InetAddress senderAddress=packet.getAddress();
					int senderPort = packet.getPort();
					DatagramPacket response = 
						new DatagramPacket(responseBuf,responseBuf.length,senderAddress,senderPort);	
					socket.send(response);
				}
			}
		}catch(IOException e){
			System.err.println("Exception in Discover UDP socket closed! ");
	}
}
}