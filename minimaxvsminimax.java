
public class minimaxvsminimax implements Runnable {
	static int [] dx = {0,-1,0,1};
	static int [] dy = {-1,0,1,0};
	static testBoard mm;
	static int oneScore = 0;
	static int twoScore = 0;
	public minimaxvsminimax(testBoard mm)
	{
		this.mm = mm;
	}
	public static void eatOpponent(Node[][] gameBoard,int playerID,int x,int y)
	{
			gameBoard[x][y].setOwnership(playerID);
	}
	Node playerOne = null;
	Node playerTwo = null;
	public void start()
	{
		
		Thread aT = new Thread()
		{
		
	
		public void run(){
		while(true)
		{
			long tStart = System.currentTimeMillis();
			try {
				playerOne = mm.minimax(null, true, mm.gameBoard, 1, 3);
			} catch (InterruptedException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			//System.out.println("OPTIMAL:   "+playerOne.getAccumulated());
			if(playerOne!=null)
			{
			mm.gameBoard[playerOne.getX()][playerOne.getY()].setOwnership(1);
			System.out.println("x:  "+playerOne.getX()+"    y:  "+playerOne.getY());
			oneScore+=mm.gameBoard[playerOne.getX()][playerOne.getY()].getScore();
			boolean pOneconnected = false;
			for(int i = 0;i<dx.length;i++)
			{

					int newX = playerOne.getX()+dx[i];
					int newY = playerOne.getY()+dy[i];
					if((newX>=0&&newX<6)&&(newY>=0&&newY<6))
					{
					if(mm.gameBoard[newX][newY].getOwnership()==1)
					{
						pOneconnected = true;
					}
					}
				
			}
			if(pOneconnected == true)
			{
				for(int i = 0;i<dx.length;i++)
				{
	
						int newX = playerOne.getX()+dx[i];
						int newY = playerOne.getY()+dy[i];
						if((newX>=0&&newX<6)&&(newY>=0&&newY<6))
						{
						if(mm.gameBoard[newX][newY].getOwnership()!=1&&mm.gameBoard[newX][newY].getOwnership()!=0)
						{
							eatOpponent(mm.gameBoard, 1, newX, newY);
							playerOne.setAccumulated(playerOne.getAccumulated()+mm.gameBoard[newX][newY].getScore());
							oneScore+=mm.gameBoard[newX][newY].getScore();
							twoScore-=mm.gameBoard[newX][newY].getScore();
						}
						}
					
				}
			}
			long tEnd = System.currentTimeMillis();
			long tDelta = tEnd - tStart;
			double elapsedSeconds = tDelta / 1000.0;
			mm.pOt = Double.toString(elapsedSeconds);
			mm.pOs = Integer.toString(oneScore);
			mm.pTs = Integer.toString(twoScore);
			for(int i = 0;i<6;i++)
			{
				for(int j = 0;j<6;j++)
				{
					System.out.print(mm.gameBoard[i][j].getOwnership()+" ");
				}
				System.out.println();
			}
			//Thread.sleep(5000);
			mm.repaint();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			
			long tStartT = System.currentTimeMillis();
			try {
				playerTwo = mm.minimax(null, true, mm.gameBoard, 2,3);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		//	System.out.println("OPTIMAL2:   "+playerTwo.getAccumulated());
			if(playerTwo!=null)
			{
			mm.gameBoard[playerTwo.getX()][playerTwo.getY()].setOwnership(2);
			twoScore +=mm.gameBoard[playerTwo.getX()][playerTwo.getY()].getScore();
			boolean pTwoconnected = false;
			for(int i = 0;i<dx.length;i++)
			{

					int newX = playerTwo.getX()+dx[i];
					int newY = playerTwo.getY()+dy[i];
					if((newX>=0&&newX<6)&&(newY>=0&&newY<6))
					{
					if(mm.gameBoard[newX][newY].getOwnership()==2)
					{
						pTwoconnected = true;
					}
					}
				
			}
			if(pTwoconnected == true)
			{
				for(int i = 0;i<dx.length;i++)
				{
	
						int newX = playerTwo.getX()+dx[i];
						int newY = playerTwo.getY()+dy[i];
						if((newX>=0&&newX<6)&&(newY>=0&&newY<6))
						{
						if(mm.gameBoard[newX][newY].getOwnership()!=2&&mm.gameBoard[newX][newY].getOwnership()!=0)
						{
							eatOpponent(mm.gameBoard, 2, newX, newY);
							playerTwo.setAccumulated(playerTwo.getAccumulated()+mm.gameBoard[newX][newY].getScore());
							twoScore+=mm.gameBoard[newX][newY].getScore();
							oneScore-=mm.gameBoard[newX][newY].getScore();
						}
						}
					
				}
			}
			long tEndT = System.currentTimeMillis();
			long tDelta = tEndT - tStartT;
			double elapsedSeconds = tDelta / 1000.0;
			mm.pTt = Double.toString(elapsedSeconds);
			mm.pOs = Integer.toString(oneScore);
			mm.pTs = Integer.toString(twoScore);
			
			for(int i = 0;i<6;i++)
			{
				for(int j = 0;j<6;j++)
				{
					System.out.print(mm.gameBoard[i][j].getOwnership()+" ");
				}
				System.out.println();
			}
		//	Thread.sleep(5000);
			mm.repaint();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		};
		aT.start();
  		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
