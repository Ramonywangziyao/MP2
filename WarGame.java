

import java.io.IOException;
import java.util.Scanner;

public class WarGame {
	
	public static Player player1;
	public static Player player2;
	
	public static void main(String[] args) throws IOException {
		
		
		GameBoard board = new GameBoard();
		GameController controller = new GameController(board, player1, player2);
		board.loadBoard();
		do {
			int gameMode = 0;
			
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please select game mode: (1) AI vs AI    (2) AI vs Human");
			gameMode = scanner.nextInt();
			
			switch (gameMode){
			case 1:  {
				System.out.println("Set strategy for both AI: ");
				System.out.println("(1) Minimax vs. minimax");
				System.out.println("(2) Alpha-beta vs. alpha-beta");
				System.out.println("(3) Minimax vs. alpha-beta (minimax goes first)");
				System.out.println("(4) Alpha-beta vs. minimax (alpha-beta goes first)");
				int strategy = scanner.nextInt();
				if (strategy==1) {
					player1 = new Player(1,0); //green mini
					player2 = new Player(2,0); //blue mini
				}
				if (strategy==2) {
					player1 = new Player(1,1); //green alpha
					player2 = new Player(2,1); //blue alpha
				}
				if (strategy==1) {
					player1 = new Player(1,0); //green mini
					player2 = new Player(2,1); //blue alpha
					//TODO: ALWAYS set player 1 first
				}
				if (strategy==1) {
					player1 = new Player(1,1); //green alpha
					player2 = new Player(2,0); //blue mini
				}
				
				break;
			}
			
			case 2: System.out.println("not implemented");break;
			
			default: System.out.println("invalid");break;
			}
			System.out.println("");
		} while (board.gameOver());
	}

}
