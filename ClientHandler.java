import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler extends Thread {
    Socket connectionSocket;
    Raport raport;
    public ClientHandler(Socket socket,Raport raport){
        this.connectionSocket=socket;
        this.raport=raport;
    }
    BufferedReader inFromClient;
    DataOutputStream outToClient;
    public void unsuccessful_operation(DataOutputStream outToClient,String operation) throws IOException {
        raport.setValues_last_10_sec("unsuccessful_operations");
        raport.setValues_last_10_sec("operation_count");
        System.out.println(operation  + " ERROR");
        outToClient.writeBytes("ERROR" + '\n');
    }
    public void run(){
        while(true){
            try {
                //Inicjalizacja streamow
                inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                //Czytanie wiadomosci od klienta
                String clientSentence=inFromClient.readLine();
                //Jezeli klient zamknie polaczenie petla konczy dzialanie
                if(clientSentence == null){
                    System.err.println("Polaczenie zakonczone!");
                    connectionSocket.close();
                    break;
                }
                String [] operation=clientSentence.split(" ");
                //Sprawdzanie poprawnoscie przeslanych danych
                if(operation.length==3 ){
                    if(Functions.isNumber(operation[1]) && Functions.isNumber(operation[2])){
                    //Proba wykonania operacji matematycznych
                     try{
                        int result=Functions.math_operation(operation[0],Integer.parseInt(operation[1]),Integer.parseInt(operation[2]));
                        outToClient.writeBytes(Integer.toString(result)+"\n");
                        System.out.println(operation[0] + " Wynnik: " + result);
                        raport.setValues_last_10_sec("successful_operations");
                        raport.setValues_last_10_sec("operation_count");
                        raport.sum_last_10_sec+=result;
                        //Przypadek blednej operacji albo nie dozwolonej matematycznej operacji
                    }catch( IllegalArgumentException | ArithmeticException e ){
                         unsuccessful_operation(outToClient,operation[0]);
                    }
                    }
                }
                else{
                    //Przypadek przeslania nie prawidlowych danych albo nie wystarczajacej ilosci argumetow
                    unsuccessful_operation(outToClient,operation[0]);
                }
            }
            catch (IOException e) {
                System.err.println("hi");
            }
        }
    }
}
