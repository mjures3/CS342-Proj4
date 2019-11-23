import java.io.Serializable;

public class GameInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int p1Points;
	int p2Points;
	String p1Plays;
	String p2Plays;
	Boolean has2Players;
	Boolean p1Replay;
	Boolean p2Replay;
	String message;
	
	public GameInfo() {
		p1Points = 0;
		p2Points = 0;
		p1Plays = "";
		p2Plays = "";
		has2Players = false;
		p1Replay = false;
		p2Replay = false;
		message = "";
	}
}