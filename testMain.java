import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;


public class testMain {
	private static String fileName;
	static JMenuItem one;
	static JMenuItem two;
	static JMenuItem three; 
	static JMenuItem four; 
	static JMenuItem five;
	static int mouseX;
	static int mouseY;
	public static void eatOpponent(Node[][] gameBoard,int playerID,int x,int y)
	{
			gameBoard[x][y].setOwnership(playerID);
	}
	public static class AL extends MouseAdapter implements Runnable
	{
		public void mouseClicked(MouseEvent e)
		{
			System.out.println("clicked!");
			mouseX = e.getY()/50-1;
			mouseY = e.getX()/50;
			System.out.println("x:  "+mouseX+"  y: "+mouseY);

			mm.gameBoard[mouseX][mouseY].setOwnership(1);
			oneScore += mm.gameBoard[mouseX][mouseY].getScore();
			boolean pOneconnected = false;
			for(int i = 0;i<dx.length;i++)
			{

					int newX = mouseX+dx[i];
					int newY = mouseY+dy[i];
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
	
						int newX = mouseX+dx[i];
						int newY = mouseY+dy[i];
						if((newX>=0&&newX<6)&&(newY>=0&&newY<6))
						{
						if(mm.gameBoard[newX][newY].getOwnership()!=1&&mm.gameBoard[newX][newY].getOwnership()!=0)
						{
							eatOpponent(mm.gameBoard, 1, newX, newY);
							
							oneScore+=mm.gameBoard[newX][newY].getScore();
							twoScore-=mm.gameBoard[newX][newY].getScore();
						}
						}
					
				}
			}
		
			mm.pOs = Integer.toString(oneScore);
			mm.pTs = Integer.toString(twoScore);
			mm.repaint();

			 Thread two = new Thread()
		        {
		        	
		        	public void run()
		        	{
		        		try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		long tStartT = System.currentTimeMillis();
		        		try {
		        			pTwo = mm.minimax(null, true, mm.gameBoard, 2, 3);
		        		} catch (InterruptedException e1) {
		        			// TODO Auto-generated catch block
		        			e1.printStackTrace();
		        		}
		        		if(pTwo!=null)
		        		{
		        		mm.gameBoard[pTwo.getX()][pTwo.getY()].setOwnership(2);
		        		}
		        		twoScore +=mm.gameBoard[pTwo.getX()][pTwo.getY()].getScore();
		        		boolean pTwoconnected = false;
		        		for(int i = 0;i<dx.length;i++)
		        		{

		        				int newX = pTwo.getX()+dx[i];
		        				int newY = pTwo.getY()+dy[i];
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

		        					int newX = pTwo.getX()+dx[i];
		        					int newY = pTwo.getY()+dy[i];
		        					if((newX>=0&&newX<6)&&(newY>=0&&newY<6))
		        					{
		        					if(mm.gameBoard[newX][newY].getOwnership()!=2&&mm.gameBoard[newX][newY].getOwnership()!=0)
		        					{
		        						eatOpponent(mm.gameBoard, 2, newX, newY);
		        						pTwo.setAccumulated(pTwo.getAccumulated()+mm.gameBoard[newX][newY].getScore());
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
		        		mm.repaint();

		        	}
		        };
		        two.start();

			
		}
	      @Override
	      public void mouseReleased(MouseEvent e) {
	       
	      }
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}

	public static void pTplay()
	{
			}
	static Node pOne;
	static Node pTwo;
	static int playX;
	static int playY;
	static String pOneScore;
	static String pTwoScore;
	static int [] dx = {0,-1,0,1};
	static int [] dy = {-1,0,1,0};

	static JFrame f;
	static testBoard mm;
	static int oneScore = 0;
	static int twoScore = 0;
	public static void main(String[]args) throws IOException,InterruptedException
	{
		f = new JFrame();
		//ArrayList<Node> solution = new ArrayList<Node>();
		JMenuBar menubar = new JMenuBar();
		f.setJMenuBar(menubar);
		
		
		//***********human play **************
		f.addMouseListener(new AL());
		//************************
		
		
		JMenu BoardFile = new JMenu("BoardFile");
		BoardFile.addMenuListener(new MenuListener(){

			@Override
			public void menuSelected(MenuEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == one)
				{
					fileName = "Keren";
					System.out.println(fileName);
				}
				else
				if(e.getSource() == two)
				{
					fileName = "Narvik";
					System.out.println(fileName);
				}
				else
				if(e.getSource()==three)
				{
					fileName = "Sevastopol";
					System.out.println(fileName);
				}
				else
				if(e.getSource()==four)
				{
					fileName = "Smolensk";
					System.out.println(fileName);
				}
				else
				if(e.getSource()==five)
				{
					System.exit(0);
					//fileName = "Westerplatte";
					//System.out.println(fileName);
				}
			}

			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
			
			
			
			
			
			
			
		});
		JMenu algorithm = new JMenu("Strategy");
		menubar.add(BoardFile);
		menubar.add(algorithm);
		one = new JMenuItem("Keren.txt");
		two = new JMenuItem("Narvik.txt");
		three = new JMenuItem("Sevastopol.txt");
		four = new JMenuItem("Smolensk.txt");
		five = new JMenuItem("Westerplatte.txt");
		
		BoardFile.add(one);
		BoardFile.add(two);
		BoardFile.add(three);
		BoardFile.add(four);
		BoardFile.add(five);
		
		JMenuItem strategyOne = new JMenuItem("Alpha vs Alpha");
		JMenuItem strategyTwo = new JMenuItem("MiniMax vs Minimax");
		JMenuItem strategyThree = new JMenuItem("Alpha vs Minimax");
		JMenuItem strategyFour = new JMenuItem("Minimax vs Alpha");
		JMenuItem strategyF = new JMenuItem("Human vs Agent");
		algorithm.add(strategyOne);
		algorithm.add(strategyTwo);
		algorithm.add(strategyThree);
		algorithm.add(strategyFour);
		algorithm.add(strategyF);
		

	
		 mm = new testBoard(fileName);
		f.setTitle("Game War");
		f.add(mm);
		System.out.println("mm:    "+mm.cols+"     row: "+mm.row);
		f.setSize(300, 480);
		f.setBackground(Color.WHITE);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		f.setVisible(true);
		
		
		
	
		
		Node playerOne = null;
		Node playerTwo = null;
	
		
		
	/*
		while(true)
		{
			long tStart = System.currentTimeMillis();
			playerOne = mm.minimax(null, true, mm.gameBoard, 1, 3);
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
			mm.repaint();
			}
			//Thread.sleep(1000);
			
			long tStartT = System.currentTimeMillis();
			playerTwo = mm.minimax(null, true, mm.gameBoard, 2,3);
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
			mm.repaint();
			}
		//	Thread.sleep(1000);
		}
	*/
		//here noti
		
		
	}
	

	

}
