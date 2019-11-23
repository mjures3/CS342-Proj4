import java.io.Serializable;
import java.util.ArrayList;

public class GameInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<String> players;
	String p1Plays;
	String p2Plays;
	boolean has2Players;
	Boolean p1Replay;
	Boolean p2Replay;
	String message;
	
	public GameInfo() {
		players = new ArrayList<String>();
		p1Plays = "";
		p2Plays = "";
		has2Players = false;
		p1Replay = false;
		p2Replay = false;
		message = "";
	}
}