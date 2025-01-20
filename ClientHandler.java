import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler extends Thread {
    Socket connectionSocket;

    public ClientHandler(Socket socket){
        this.connectionSocket=socket;
    }
    BufferedReader inFromClient;
    DataOutputStream outToClient;
    public void unsuccessful_operation(DataOutputStream outToClient,String operation) {
        synchronized (TCP.raport) {
            TCP.raport.setValues_last_10_sec("unsuccessful_operations_count");
            TCP.raport.setValues_last_10_sec("operation_count");
        }
        System.out.println(operation + " ERROR");
        try {
            outToClient.writeBytes("ERROR" + '\n');
        } catch(IOException e) {
            System.err.println("Exception while sending ERROR to client: " + e.getMessage());
        }
    }
    public void successful_operation(DataOutputStream outToClient,String operation,int result) {
        try{
            outToClient.writeBytes(result + "\n");
        }catch(IOException e) {
            System.err.println("Exception when sending successful result to client: " + e.getMessage());
        }
        System.out.println(operation + " Result: " + result);
        synchronized (TCP.raport){
            TCP.raport.setValues_last_10_sec("successful_operations");
            TCP.raport.setValues_last_10_sec("operation_count");
            TCP.raport.sum_last_10_sec+=result;
        }
    }
    public void run(){
        try {
        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        while(true){
                String clientSentence=inFromClient.readLine();
                String [] operation;
                if(clientSentence!=null){
                    operation=clientSentence.split(" ");
                }else{
                    operation=new String[0];
                }
                if(operation.length==3 ){
                    if(Functions.isNumber(operation[1]) && Functions.isNumber(operation[2])){
                     try{
                        int result=Functions.math_operation(operation[0],Integer.parseInt(operation[1]),Integer.parseInt(operation[2]));
                        successful_operation(outToClient,operation[0],result);
                    }catch( IllegalArgumentException | ArithmeticException e ){
                         unsuccessful_operation(outToClient,operation[0]);
                         System.out.println(operation[0] + " " + operation[1] + " " + operation[2] + " -> ERROR");
                        }
                    }else{unsuccessful_operation(outToClient, operation[0]);}
                }
                else {
                    unsuccessful_operation(outToClient, operation[0]);
                }
            }
        }
        catch (IOException e) {
                System.err.println("Exception during communication with the client or the client closed the socket.");
            }
        finally{
            try {
                connectionSocket.close();
                inFromClient.close();
                outToClient.close();
                System.out.println("Socket closed!");
            } catch (java.lang.Exception ex) {
                System.err.println("Exception while closing socket: " + ex.getMessage());
            }

        }
        }

}
