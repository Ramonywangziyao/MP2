

public class GameController {
	GameBoard board;
	Player player1;
	Player player2;
	
	public GameController(GameBoard board, Player player1, Player player2){
		this.board = board;
		this.player1 = player1;
		this.player2 = player2;
	}
}
