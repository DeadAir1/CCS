
public class CCS{
	public static void main(String []args){
		if(args.length != 1){
			System.out.println("Nie poprawna liczba argumentow!");
			return;
		}
		else if (!Functions.isNumber(args[0])) {
			System.out.println("Niepoprawny typ argumentow!");
			return;
		}
		final  int PORT = Integer.parseInt(args[0]);
		Discover discover = new Discover(PORT);
		TCP tcpserver=new TCP(PORT);
		discover.start();
		tcpserver.start();
	}
	

}	

