import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class Discover extends Thread {
	int PORT;
	DatagramSocket socket;
	byte[] buf = new byte[1024];
	DatagramPacket packet = new DatagramPacket(buf,buf.length);
	public Discover(int port){
		this.PORT = port;
	}
	public void run(){
		try{  
			socket = new DatagramSocket(this.PORT); 
			while (true) {
				try {
					socket.receive(packet);
				} catch (IOException e) {
					System.err.println("Błąd przy otrzymaniu wiadomosci");
					Arrays.fill(buf, (byte) 0);
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
					Arrays.fill(buf, (byte) 0);
				}else {	
					Arrays.fill(buf, (byte) 0); 

				}
			}
		}catch(IOException e){
			System.err.println("Program Discover rzucil wyjatek");
		}
		finally {socket.close();}
	}
}