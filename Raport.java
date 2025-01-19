import java.util.HashMap;
import java.util.Map;

public class Raport extends Thread{
    Map<String,Integer> values_from_start=new HashMap<>();
    Map<String,Integer> values_last_10_sec=new HashMap<>();
    int sum_last_10_sec=0;
    int sum_from_start=0;
    public Raport(){
        //Inicjalizacja mapy wartosci za caly czas pracy
        values_from_start.put("connection_count",0);
        values_from_start.put("successful_operations",0);
        values_from_start.put("operation_count",0);
        values_from_start.put("unsuccessful_operations_count",0);
        //Inicjalizacja mapy wartosci za ostatnie 10 sekund
        values_last_10_sec.put("connection_count",0);
        values_last_10_sec.put("successful_operations",0);
        values_last_10_sec.put("operation_count",0);
        values_last_10_sec.put("unsuccessful_operations_count",0);
        System.out.println("Raport started");
    }

    public void setValues_from_start(String key) {
        int value=this.values_from_start.get(key);
        this.values_from_start.put(key,value+1);
    }
    public void setValues_last_10_sec(String key) {
        int value=this.values_from_start.get(key);
        this.values_from_start.put(key,value+1);
    }

    public void run(){
        while(true){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("RAPORT ZA CALY CZAS PRACY");
            for(String key : values_from_start.keySet()){
               int value_from_start=values_from_start.get(key);
               int value_last_10_sec=values_last_10_sec.get(key);
               values_from_start.put(key,value_from_start+value_last_10_sec);
               System.out.println("- " + key + " : " + values_from_start.get(key));
            }
            sum_from_start+=sum_last_10_sec;
            System.out.println("- " + "result_sum" + " : " + sum_from_start);
            System.out.println("RAPORT ZA OSTATNIE 10 SEKUND");
            for(String key : values_last_10_sec.keySet()){
                System.out.println("- " + key + " : " + values_last_10_sec.get(key));
                values_last_10_sec.put(key,0);
            }
            System.out.println("- " + "result_sum" + " : " + sum_last_10_sec);
            sum_last_10_sec=0;
        }
    }


}
