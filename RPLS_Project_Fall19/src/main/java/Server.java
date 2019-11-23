import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

//import Server.ClientThread;
//import Server.TheServer;

public class Server {
	
	int count = 1;
	int portNum;
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	GameInfo info; 
	private Consumer<Serializable> callback;
	
	Server(Consumer<Serializable> call, int pNum){
	
		callback = call;
		portNum = pNum;
		info = new GameInfo();
		server = new TheServer();
		server.start();
		
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(portNum);){
		    System.out.println("Server is waiting for a client!");
			
		    while(true) {
		
				ClientThread c = new ClientThread(mysocket.accept(), count);
				callback.accept("client has connected to server: " + "client #" + count);
				clients.add(c);
				c.start();
				
				//info.message = "new client on server: client #"+count;
				//updateClients("new client on server: client #"+count);
				//updateClients(info);
				
				count++;
				
			    }
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
			}//end of while
		}
	

		class ClientThread extends Thread{
			
		
			Socket connection;
			int count;
			boolean inGame;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
				inGame = false;
			}
			
//			public void updateClients(String data) {
//				for(int i = 0; i < clients.size(); i++) {
//					ClientThread t = clients.get(i);
//					try {
//					 t.out.writeObject(data);
//					}
//					catch(Exception e) {}
//				}
//			}
			
			public void updateClients(GameInfo data) {
				synchronized(data) {
				for(int i = 0; i < clients.size(); i++) {
					System.out.println("Hi");
					//ClientThread t = clients.get(i);
					try {
				     //out.reset();
					 clients.get(i).out.writeObject(data);
					 out.reset();
					}
					catch(Exception e) {}
				}
				}
			}
			
//			public void updatePlayerLobby(ArrayList<ClientThread> list) {
//				for(int i = 0; i < clients.size(); i++) {
//					ClientThread t = clients.get(i);
//					try {
//				     //out.reset();
//					 t.out.writeObject(list);
//					 out.reset();
//					}
//					catch(Exception e) {}
//				}
//			}
			
			public void evalPlay() {
			    if(info.p1Plays.equals(info.p2Plays)) {
			    	info.message = "Round tied";
			    }
				else if(info.p1Plays.equals("rock") && (info.p2Plays.equals("scissor") 
											   ||  info.p2Plays.equals("lizard"))) {
					info.p1Points++;
					info.message = "Player 1 won the round";
				}
				else if(info.p1Plays.equals("scissor") && (info.p2Plays.equals("paper") 
						   					   	        ||  info.p2Plays.equals("lizard"))) {
					info.p1Points++;
					info.message = "Player 1 won the round";
				}
				else if(info.p1Plays.equals("paper") && (info.p2Plays.equals("rock") 
				   	        						 ||  info.p2Plays.equals("spock"))) {
					info.p1Points++;
					info.message = "Player 1 won the round";
				}
				else if(info.p1Plays.equals("lizard") && (info.p2Plays.equals("paper") 
													  ||  info.p2Plays.equals("spock"))) {
					info.p1Points++;
					info.message = "Player 1 won the round";
				}
				else if(info.p1Plays.equals("spock") && (info.p2Plays.equals("rock") 
						  							 ||  info.p2Plays.equals("scissor"))) {
					info.p1Points++;
					info.message = "Player 1 won the round";
				}
			    else {
			    	info.p2Points++;
			    	info.message = "Player 2 won the round";
			    }
			    info.p1Plays = "";
		    	info.p2Plays = "";
		    	updateClients(info);
			}
			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				
				//info.message = "new client on server: client #"+count;
				//updateClients("new client on server: client #"+count);
				info.players.add("client " + count);
				//updatePlayerLobby(clients);
				updateClients(info);
					
				 while(true) {
					    try {
//					    	if(clients.size() == 2) info.has2Players = true;
//							else {
//								info.has2Players = false;
//								continue;
//							}
					    	
					    	String data = in.readObject().toString();
					    	//info = (GameInfo)in.readObject();
					    	callback.accept("client: " + count + " sent: " + data);
					    	//callback.accept(info);
					    	updateClients(info);
					    	
					    	//currently hardcoded: NEEDS fixing
					    	if(count == 1) {
					    		info.p1Plays = data;
					    	}
					    	else info.p2Plays = data;
					    	
					    	//info.message = "client #"+count+" played: "+data;
					    	//updateClients("client #"+count+" played: "+data);
					    	//updateClients(info);
					    	
					    	//MIGHT BE THE PROBLEM
					    	if(!info.p1Plays.isEmpty() && !info.p2Plays.isEmpty()) {
					    		evalPlay();
					    	}
					    }
					    catch(Exception e) {
					    	callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
					    	info.message = "Client #"+count+" has left the server!";
					    	//updateClients("Client #"+count+" has left the server!");
					    	info.players.remove("client " + count);
					    	clients.remove(this);
					    	updateClients(info);
					    	break;
					    }
					}
				}//end of run
			
			
		}//end of client thread
}


