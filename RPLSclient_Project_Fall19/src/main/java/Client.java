import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.function.Consumer;

import javafx.application.Platform;



public class Client extends Thread{

	
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	InetAddress ip;
	int portNum;
	GameInfo info;
	
	private Consumer<Serializable> callback;
	
	Client(Consumer<Serializable> call, InetAddress ipAddress, int pNum){
	
		callback = call;
		ip = ipAddress;
		portNum = pNum;
		
	}
	
	public void run() {
		
		try {
		socketClient= new Socket(ip, portNum);
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}
		
		while(true) {
			 
			try {
			info = (GameInfo)in.readObject();
			callback.accept(info);
			}
			catch(Exception e) {}
		}
	
    }
	
	public void send(String data) {
		
		try {
			out.writeObject(data);
			out.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
