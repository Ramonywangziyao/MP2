import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;


public class testMain implements Runnable{
	private static String fileName;
	static JMenuItem one;
	static JMenuItem two;
	static JMenuItem three; 
	static JMenuItem four; 
	static JMenuItem five;
	static JMenuItem six;
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
			if(mm.gameBoard[mouseX][mouseY].getOwnership()!=1)
			{
				System.out.println("asdasds");
			mm.gameBoard[mouseX][mouseY].setOwnership(1);
			oneScore += mm.gameBoard[mouseX][mouseY].getScore();
			boolean pOneconnected = false;
			for(int i = 0;i<dx.length;i++)
			{

					int newX = mouseX+dx[i];
					int newY = mouseY+dy[i];
					if((newX>=0&&newX<mm.gameBoard.length)&&(newY>=0&&newY<mm.gameBoard.length))
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
						if((newX>=0&&newX<mm.gameBoard.length)&&(newY>=0&&newY<mm.gameBoard.length))
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
		        			pTwo = mm.minimax(null, true, mm.gameBoard, 2, 5);
		        		} catch (InterruptedException e1) {
		        			// TODO Auto-generated catch block
		        			e1.printStackTrace();
		        		}
		        		if(pTwo!=null)
		        		{
		        		mm.gameBoard[pTwo.getX()][pTwo.getY()].setOwnership(2);
		        		}
		        		try
		        		{
		        		twoScore +=mm.gameBoard[pTwo.getX()][pTwo.getY()].getScore();
		        		}
		        		catch(Exception e)
		        		{
		        			
		        		}
		        		boolean pTwoconnected = false;
		        		for(int i = 0;i<dx.length;i++)
		        		{

		        				int newX = pTwo.getX()+dx[i];
		        				int newY = pTwo.getY()+dy[i];
		        				if((newX>=0&&newX<mm.gameBoard.length)&&(newY>=0&&newY<mm.gameBoard.length))
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
		        					if((newX>=0&&newX<mm.gameBoard.length)&&(newY>=0&&newY<mm.gameBoard.length))
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
			Thread.currentThread().stop();
		}
	      @Override
	      public void mouseReleased(MouseEvent e) {
	       
	      }
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
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
	static JMenuBar menubar;
	static JMenu BoardFile =new JMenu("BoardFile");;
	static JMenu algorithm = new JMenu("Strategy");;
	static JMenuItem strategyOne = new JMenuItem("Alpha vs Alpha");
	static JMenuItem strategyTwo = new JMenuItem("MiniMax vs Minimax");
	static JMenuItem strategyThree = new JMenuItem("Alpha vs Minimax");
	static JMenuItem strategyFour = new JMenuItem("Minimax vs Alpha");
	static JMenuItem strategyF = new JMenuItem("Human vs Agent");
	public static void main(String[]args) throws IOException,InterruptedException
	{
		f = new JFrame();
		//ArrayList<Node> solution = new ArrayList<Node>();
		menubar = new JMenuBar();
		f.setJMenuBar(menubar);
		
		
		//***********human play **************
		//f.addMouseListener(new AL());
		//************************
		menubar.add(BoardFile);
		menubar.add(algorithm);
		one = new JMenuItem("Keren.txt");
	     one.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	System.out.println("entered!!!!!!!!!!!!!");
					fileName = "Keren";
					try {
						mm = new testBoard(fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					f.dispose();
					f = new JFrame();
					//ArrayList<Node> solution = new ArrayList<Node>();
					JMenuBar menubar = new JMenuBar();
					menubar.add(BoardFile);
					menubar.add(algorithm);
					f.setJMenuBar(menubar);
					f.setTitle("Game War");
					f.add(mm);
					System.out.println("mm:    "+mm.cols+"     row: "+mm.row);
					f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+180);
					f.setBackground(Color.WHITE);
					f.setLocationRelativeTo(null);
					f.setResizable(false);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//		f.addMouseListener(new AL());
					
					f.setVisible(true);
					Thread.currentThread().stop();
					mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
	            }

	        });
		two = new JMenuItem("Narvik.txt");
	     two.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	System.out.println("entered!!!!!!!!!!!!!");
					fileName = "Narvik";
					try {
						mm = new testBoard(fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					f.dispose();
					f = new JFrame();
					//ArrayList<Node> solution = new ArrayList<Node>();
					JMenuBar menubar = new JMenuBar();
					menubar.add(BoardFile);
					menubar.add(algorithm);
					f.setJMenuBar(menubar);
					f.setTitle("Game War");
					f.add(mm);
					System.out.println("mm:    "+mm.cols+"     row: "+mm.row);
					f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+180);
					f.setBackground(Color.WHITE);
					f.setLocationRelativeTo(null);
					f.setResizable(false);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//		f.addMouseListener(new AL());
					
					f.setVisible(true);
					Thread.currentThread().stop();
					mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
	            }

	        });
		three = new JMenuItem("Sevastopol.txt");
	     three.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	System.out.println("entered!!!!!!!!!!!!!");
					fileName = "Sevastopol";
					try {
						mm = new testBoard(fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					f.dispose();
					f = new JFrame();
					//ArrayList<Node> solution = new ArrayList<Node>();
					JMenuBar menubar = new JMenuBar();
					menubar.add(BoardFile);
					menubar.add(algorithm);
					f.setJMenuBar(menubar);
					f.setTitle("Game War");
					f.add(mm);
					System.out.println("mm:    "+mm.cols+"     row: "+mm.row);
					f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+180);
					f.setBackground(Color.WHITE);
					f.setLocationRelativeTo(null);
					f.setResizable(false);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//		f.addMouseListener(new AL());
					
					f.setVisible(true);
					Thread.currentThread().stop();
					mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
	            }

	        });
		four = new JMenuItem("Smolensk.txt");
	     four.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	System.out.println("entered!!!!!!!!!!!!!");
					fileName = "Smolensk";
					try {
						mm = new testBoard(fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					f.dispose();
					f = new JFrame();
					//ArrayList<Node> solution = new ArrayList<Node>();
					JMenuBar menubar = new JMenuBar();
					menubar.add(BoardFile);
					menubar.add(algorithm);
					f.setJMenuBar(menubar);
					f.setTitle("Game War");
					f.add(mm);
					System.out.println("mm:    "+mm.cols+"     row: "+mm.row);
					f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+180);
					f.setBackground(Color.WHITE);
					f.setLocationRelativeTo(null);
					f.setResizable(false);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//		f.addMouseListener(new AL());
					
					f.setVisible(true);
					Thread.currentThread().stop();
					mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
	            }

	        });
		five = new JMenuItem("Westerplatte.txt");
	     five.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	System.out.println("entered!!!!!!!!!!!!!");
					fileName = "Westerplatte";
					try {
						mm = new testBoard(fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					f.dispose();
					f = new JFrame();
					//ArrayList<Node> solution = new ArrayList<Node>();
					JMenuBar menubar = new JMenuBar();
					menubar.add(BoardFile);
					menubar.add(algorithm);
					f.setJMenuBar(menubar);
					f.setTitle("Game War");
					f.add(mm);
					System.out.println("mm:    "+mm.cols+"     row: "+mm.row);
					f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+180);
					f.setBackground(Color.WHITE);
					f.setLocationRelativeTo(null);
					f.setResizable(false);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				//	f.addMouseListener(new AL());
					
					f.setVisible(true);
					Thread.currentThread().stop();
					mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
	            }

	        });
	 	six = new JMenuItem("ziyao.txt");
	     six.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	System.out.println("entered!!!!!!!!!!!!!");
					fileName = "ziyao";
					try {
						mm = new testBoard(fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					f.dispose();
					f = new JFrame();
					//ArrayList<Node> solution = new ArrayList<Node>();
					JMenuBar menubar = new JMenuBar();
					menubar.add(BoardFile);
					menubar.add(algorithm);
					f.setJMenuBar(menubar);
					f.setTitle("Game War");
					f.add(mm);
					System.out.println("mm:    "+mm.cols+"     row: "+mm.row);
					f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+180);
					f.setBackground(Color.WHITE);
					f.setLocationRelativeTo(null);
					f.setResizable(false);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					//f.addMouseListener(new AL());
					
					f.setVisible(true);
					Thread.currentThread().stop();
					mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
	            }

	        });
		BoardFile.add(one);
		BoardFile.add(two);
		BoardFile.add(three);
		BoardFile.add(four);
		BoardFile.add(five);
		BoardFile.add(six);

		strategyTwo.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
            	try {
					mm = new testBoard(fileName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	f.dispose();
				f = new JFrame();
				//ArrayList<Node> solution = new ArrayList<Node>();
				JMenuBar menubar = new JMenuBar();
				menubar.add(BoardFile);
				menubar.add(algorithm);
				f.setJMenuBar(menubar);
				f.setTitle("Game War");
				f.add(mm);
				System.out.println("mm:    "+mm.cols+"     row: "+mm.row);
				f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+180);
				f.setBackground(Color.WHITE);
				f.setLocationRelativeTo(null);
				f.setResizable(false);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				//f.addMouseListener(new AL());
				mm.pOs = "";
				mm.pOt = "";
				mm.pTs = "";
				mm.pTt = "";
				f.setVisible(true);
            	minimaxvsminimax newRound = new minimaxvsminimax(mm);
            	newRound.start();
		        	
				
            }
        
        });
		strategyF.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					mm = new testBoard(fileName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				f.dispose();
				f = new JFrame();
				//ArrayList<Node> solution = new ArrayList<Node>();
				JMenuBar menubar = new JMenuBar();
				menubar.add(BoardFile);
				menubar.add(algorithm);
				f.setJMenuBar(menubar);
				f.setTitle("Game War");
				f.add(mm);
				System.out.println("mm:    "+mm.cols+"     row: "+mm.row);
				f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+180);
				f.setBackground(Color.WHITE);
				f.setLocationRelativeTo(null);
				f.setResizable(false);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				//f.addMouseListener(new AL());
				mm.pOs = "";
				mm.pOt = "";
				mm.pTs = "";
				mm.pTt = "";
				f.setVisible(true);
				f.addMouseListener(new AL());
			}
			
		});
		algorithm.add(strategyOne);
		algorithm.add(strategyTwo);
		algorithm.add(strategyThree);
		algorithm.add(strategyFour);
		algorithm.add(strategyF);
		
		

	
		 mm = new testBoard(fileName);
		f.setTitle("Game War");
		f.add(mm);
		System.out.println("mm:    "+mm.cols+"     row: "+mm.row);
		f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+180);
		f.setBackground(Color.WHITE);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		f.setVisible(true);
		
		
		
	
		
		
	
		//here noti
	
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
	
	

}
