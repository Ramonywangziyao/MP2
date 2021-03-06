import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JComponent;


public class testBoard extends JComponent implements ActionListener {
	private Image frame,frameB,frameG;
	public String pOt,pTt;
	private static String fileName;
	static long playeroneNode;
	static long playertwoNode;
	static long counter;
	public String pOs="";
	public String pTs="";
	public int [] dx = {0,-1,0,1};
	public int [] dy = {-1,0,1,0};
	int maxrecordSearchedX = 0;
	int maxrecordSearchedY = 0;
	Node alpha;
	Node beta;
	static double ban,ran,bat,rat;
	public testBoard(String fileName) throws IOException
	{
		pOt = "";
		pTt = "";
		playeroneNode = 0;
		playertwoNode = 0;
		this.alpha = null;
		if(fileName == null)
		{			
			this.fileName = "src/Sevastopol.txt";
		}
		else
		if(fileName.equals("Keren"))
		{
			this.fileName = "src/Keren.txt";
			this.repaint();
		}
		else
		if(fileName.equals("Narvik"))
		{
			this.fileName = "src/Narvik.txt";
			this.repaint();
		}
		else
		if(fileName.equals("Sevastopol"))
		{
			this.fileName = "src/Sevastopol.txt";
			this.repaint();
		}
		else
		if(fileName.equals("Smolensk"))
		{
			this.fileName = "src/Smolensk.txt";
			this.repaint();
		}
		else
		if(fileName.equals("Westerplatte"))
		{
			this.fileName = "src/Westerplatte.txt";
			this.repaint();
		}
		else
		if(fileName.equals("ziyao"))
		{
			this.fileName = "src/ziyao.txt";
			this.repaint();
		}
		ImageIcon img = new ImageIcon("src/realFrame.png");
		frame = img.getImage();
		ImageIcon img2 = new ImageIcon("src/realBlack.png");
		frameB = img2.getImage();
		ImageIcon img3 = new ImageIcon("src/realWhite.png");
		frameG = img3.getImage();
		int totalCount = openFileCount();
		closeFile();
		openFile();
		readFileS(totalCount);
		closeFile();
		
	}
	
	public Image getFrame()
	{
		return frame;
	}
	public Image getFrameB()
	{
		return frameB;
	}
	public Image getFrameG()
	{
		return frameG;
	}
	public void closeFile()
	{
		
	}
	
	
	Node [][] gameBoard = null;
	
	int row;
	int cols;
	BufferedReader bufferNew;
	
	
	public int openFileCount() throws IOException
	{
		FileReader fr = new FileReader(fileName);
		BufferedReader buffer = new BufferedReader (fr);
		String line;
		int x = 0;
		int count = 0;

		//total rows
		while((line = buffer.readLine())!=null)
		{
	
			count++;
		}
		fr.close();
		return count;
	}
	
	public void openFile() throws IOException
	{
		
		FileReader fr2 = new FileReader(fileName);
		bufferNew = new BufferedReader (fr2);
		
	}
	public void readFileS(int count) throws IOException
	{
		int size = 0;
		int x = 0;
		String line;
		int theCount = count;
		while((line = bufferNew.readLine())!=null)
		{
			char [] vals = line.toCharArray();
            if (gameBoard == null) {
                size = vals.length;
                
                gameBoard = new Node[theCount][theCount];
            }
            row = size;
            cols = count;
            int startCount = 0;
            int y = 0;
            for (int col = 0; col < vals.length ; col++) {
            	if(col==vals.length-1||(vals[col]!='\t'&&vals[col+1]=='\t'))
            	{
            		gameBoard[x][startCount] = new Node(Character.getNumericValue(vals[col]), x, y,0);
            		y++;
            		startCount++;
            	}
            	
            	else if(vals[col]!='\t'&&vals[col+1]!='\t')
            	{		
            		String newNumber =""+ vals[col]+vals[col+1];
            		gameBoard[x][startCount] = new Node(Integer.parseInt(newNumber), x, y,0);
            		
            		y++;
            		
            		if(col!=vals.length-1)
            		{
            		col++;
            		}
            		startCount++;
            	}else
            	{
            		row-=1;
            	}
            	}

            x++;
		}	
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}
	public Node[][] getGameBoard()
	{
		return this.gameBoard;
	}
	public void eatOpponent(Node[][] gameBoard,int playerID,int x,int y)
	{
		gameBoard[x][y].setOwnership(playerID);
	}

	public Node minimax(Node node,  boolean isMaximizing, Node[][] gameBoard,int playerID,int depth) throws InterruptedException {
		// TODO Auto-generated method stub
		//if node is terminal   BASE CASE

		if(depth==0) //node equals terminal node   origin   ***************************
		{
			
			return node; //return heuristic value accumulated Score
		}
		
		if(isMaximizing == true)//max
		{
			
			Node[][] copyOne = new Node[gameBoard.length][gameBoard.length];
			
			int enescore=0;
			//opponent score
			if(node!=null)
			{	enescore = node.enemy;}
			
		    for (int i = 0; i < gameBoard.length; i++) {
		    	for(int j =0;j<gameBoard.length;j++)
		    	{	
		    		Node temp = new Node(gameBoard[i][j].getScore(),gameBoard[i][j].getX(),gameBoard[i][j].getY(),gameBoard[i][j].getOwnership());
		    		copyOne[i][j]=temp;
		    	}
		    }
		    
		    
			PriorityQueue<Node> playerOneChildrenList = new PriorityQueue<Node>(Collections.reverseOrder()); //use priorityQueue,acsending or disacsending.
			Queue<Node> waitList = new LinkedList<Node>();
			boolean walkable = false;
			for(int i = 0;i<gameBoard.length;i++)
			{
				for(int j = 0;j<gameBoard.length;j++)
				{
					if(copyOne[i][j].getOwnership() == 0)
					{
						waitList.add(new Node(copyOne[i][j].getScore(),i,j,playerID));//everyPosition through the 2D array
						walkable = true;
				//		counter++;
					}
			
				}
			}
			
			if(walkable == false)
			{
				return node;
			}
			
			int waitSize = waitList.size();
			
			
			for(int l = 0;l<waitSize;l++)
			{
				
				Node[][] newCopy = new Node[gameBoard.length][gameBoard.length];
			    for (int i = 0; i < gameBoard.length; i++) {
			    	for(int j =0;j<gameBoard.length;j++)
			    	{	
			    		Node temp = new Node(copyOne[i][j].getScore(),copyOne[i][j].getX(),copyOne[i][j].getY(),copyOne[i][j].getOwnership());
			    		newCopy[i][j]=temp;
			    	}
			    }
			    
				Node takenNode =waitList.poll();
				counter++;
				takenNode.setAccumulated(newCopy[takenNode.getX()][takenNode.getY()].getScore());
				takenNode.enemy = 0;
				newCopy[takenNode.getX()][takenNode.getY()].setOwnership(playerID);
				boolean connected = false;
				for(int i = 0;i<dx.length;i++)
				{
						int newX = takenNode.getX()+dx[i];
						int newY = takenNode.getY()+dy[i];
						if((newX>=0&&newX<gameBoard.length)&&(newY>=0&&newY<gameBoard.length))
						{
						if(newCopy[newX][newY].getOwnership()==playerID)
						{
		
							connected = true;
						}
						}
					
				}
				if(connected == true)
				{

					for(int i = 0;i<dx.length;i++)
					{
		
							int newX = takenNode.getX()+dx[i];
							int newY = takenNode.getY()+dy[i];
							if((newX>=0&&newX<gameBoard.length)&&(newY>=0&&newY<gameBoard.length))
							{
							if(newCopy[newX][newY].getOwnership()!=playerID&&newCopy[newX][newY].getOwnership()!=0)
							{
								this.eatOpponent(newCopy, playerID, newX, newY);
		
								takenNode.setAccumulated(takenNode.getAccumulated()+newCopy[newX][newY].getScore());
						
								enescore-=newCopy[newX][newY].getScore();
								takenNode.enemy = enescore;
							}
							}
						
					}
				}

				if(depth==1)
				{
					takenNode.setAccumulated(node.getAccumulated()+takenNode.getAccumulated());
				}
	

				int opponentID = 0;
				if(playerID == 1)
				{
					opponentID = 2;
				}else if(playerID == 2)
				{
					opponentID = 1;
				}
				Node minValueNode = minimax(takenNode,false,newCopy, opponentID, depth-1);
				counter++;
				if(node!=null)
				{
				minValueNode.parentNode = new Node(node.getAccumulated(),node.getX(),node.getY(),node.getOwnership());
			//	minValueNode.parentNode.ev = minValueNode.getAccumulated()-node.enemy;
				}
			//	else
			//	{
			//		minValueNode.parentNode = minValueNode;
			//		minValueNode.parentNode.ev = minValueNode.ev;
			//	}
					
				
			//	System.out.println("value:  "+minValueNode.getAccumulated()+"   enemy:  "+node.enemy+"    ev:  "+node.ev);
			//	Thread.sleep(50);
				playerOneChildrenList.add(minValueNode);
				
			}

			if(playerOneChildrenList.peek().parentNode!=null||node!=null)
			{
			playerOneChildrenList.peek().parentNode.setAccumulated(playerOneChildrenList.peek().getAccumulated());
			//playerOneChildrenList.peek().parentNode.ev = playerOneChildrenList.peek().ev;
			//System.out.println("max x:  "+playerOneChildrenList.peek().parentNode.getX()+"   y:  "+playerOneChildrenList.peek().parentNode.getY()+"   value:  "+playerOneChildrenList.peek().parentNode.getAccumulated());
			//System.out.println("return ev:  "+playerOneChildrenList.peek().parentNode.ev);
			//Thread.sleep(50);
			return playerOneChildrenList.poll().parentNode;
			}
			else
			{
			//if(node==null)
			//System.out.println("pulling max value   "+ playerOneChildrenList.peek().getAccumulated()+"  x:  "+playerOneChildrenList.peek().getX()+"  y:  "+playerOneChildrenList.peek().getY());
				playerOneChildrenList.peek().nodeExpanded = counter;
				return playerOneChildrenList.poll();
			}
		
		}else
		if(isMaximizing == false)//min
		{
			Node[][] copyOne = new Node[gameBoard.length][gameBoard.length];
			int currentEnescore = node.enemy;
		    for (int i = 0; i < gameBoard.length; i++) {
		    	for(int j =0;j<gameBoard.length;j++)
		    	{	
		    		Node temp = new Node(gameBoard[i][j].getScore(),gameBoard[i][j].getX(),gameBoard[i][j].getY(),gameBoard[i][j].getOwnership());
		    	 copyOne[i][j]=temp;
		    	}
		    }
			PriorityQueue<Node> playerTwoChildrenList = new PriorityQueue<Node>(); 
			Queue<Node> waitList = new LinkedList<Node>();
			boolean walkable = false;
			for(int i = 0;i<gameBoard.length;i++)
			{
				for(int j = 0;j<gameBoard.length;j++)
				{
					if(copyOne[i][j].getOwnership() == 0)
					{
						waitList.add(new Node(copyOne[i][j].getScore(),i,j,playerID));//everyPosition through the 2D array
						walkable = true;
				//		counter++;
					}
			
				}
			}
			
			if(walkable == false)
			{
				return node;
			}
			
			int waitSize = waitList.size();
			for(int l = 0;l<waitSize;l++)
			{
				
				Node[][] newCopy = new Node[gameBoard.length][gameBoard.length];
			    for (int i = 0; i < gameBoard.length; i++) {
			    	for(int j =0;j<gameBoard.length;j++)
			    	{	
			    		Node temp = new Node(copyOne[i][j].getScore(),copyOne[i][j].getX(),copyOne[i][j].getY(),copyOne[i][j].getOwnership());
			    		newCopy[i][j]=temp;
			    	}
			    }
				Node takenNode = waitList.poll();
				counter++;
				newCopy[takenNode.getX()][takenNode.getY()].setOwnership(playerID);
		
				takenNode.setAccumulated(node.getAccumulated());
				takenNode.enemy= currentEnescore+ newCopy[takenNode.getX()][takenNode.getY()].getScore();
				
				boolean connected = false;
				for(int i = 0;i<dx.length;i++)
				{
					
						int newX = takenNode.getX()+dx[i];
						int newY = takenNode.getY()+dy[i];
						if((newX>=0&&newX<gameBoard.length)&&(newY>=0&&newY<gameBoard.length))
						{
						if(newCopy[newX][newY].getOwnership()==playerID)
						{
				
							connected = true;
						}
						}
					
				}
				if(connected == true)
				{
			
					for(int i = 0;i<dx.length;i++)
					{
		
							int newX = takenNode.getX()+dx[i];
							int newY = takenNode.getY()+dy[i];
							if((newX>=0&&newX<gameBoard.length)&&(newY>=0&&newY<gameBoard.length))
							{
							if(newCopy[newX][newY].getOwnership()!=playerID&&newCopy[newX][newY].getOwnership()!=0)
							{
								this.eatOpponent(newCopy, playerID, newX, newY);
								takenNode.setAccumulated(takenNode.getAccumulated()-newCopy[newX][newY].getScore());
							
								takenNode.enemy+=newCopy[newX][newY].getScore();
							}
							}
						
					}
				}
				int opponentID = 0;
				if(playerID == 1)
				{
					opponentID = 2;
				}else if(playerID == 2)
				{
					opponentID = 1;
				}
				
				Node maxValueNode = minimax(takenNode,true,newCopy, opponentID, depth-1);
				counter++;
				maxValueNode.parentNode =  new Node(node.getAccumulated(),node.getX(),node.getY(),node.getOwnership());
				//maxValueNode.parentNode.ev = maxValueNode.ev;
				//maxValueNode.parentNode.setAccumulated(maxValueNode.getAccumulated());
				playerTwoChildrenList.add(maxValueNode);
			}
			playerTwoChildrenList.peek().parentNode.setAccumulated(playerTwoChildrenList.peek().getAccumulated());
			//playerTwoChildrenList.peek().parentNode.ev = playerTwoChildrenList.peek().ev;
			//System.out.println("min:   parent x:  "+playerTwoChildrenList.peek().parentNode.getX()+"    parent y:  "+playerTwoChildrenList.peek().parentNode.getY()+"   Value: "+playerTwoChildrenList.peek().getAccumulated());
			//System.out.println("MIN:::::          EV:   "+playerTwoChildrenList.peek().parentNode.ev);
		//	Thread.sleep(50);
			return playerTwoChildrenList.poll().parentNode;
		}
		return null;
	
	}
	


	public Node newAlphaBeta(Node node,  boolean isMaximizing, Node[][] gameBoard,int playerID,int depth,boolean searchOnce) throws InterruptedException {
		// TODO Auto-generated method stub
		//if node is terminal   BASE CASE
	//	System.out.println("depth:   "+depth);
	//	Thread.sleep(300);
		if(depth==0) //node equals terminal node   origin   ***************************
		{

			
			return node; //return heuristic value accumulated Score
		}
		
		if(isMaximizing == true)//max
		{
			
			Node[][] copyOne = new Node[gameBoard.length][gameBoard.length];
			int enescore=0;
			int nodesRx = 0;
			int nodesRy = 0;
			Node temNode = node;
			//opponent score
			if(node!=null)
			{	enescore = node.enemy;
			nodesRx = node.maxrecordSearchedX;
			nodesRy = node.maxrecordSearchedY;}
			
		    for (int i = 0; i < gameBoard.length; i++) {
		    	for(int j =0;j<gameBoard.length;j++)
		    	{	
		    		Node temp = new Node(gameBoard[i][j].getScore(),gameBoard[i][j].getX(),gameBoard[i][j].getY(),gameBoard[i][j].getOwnership());
		    		copyOne[i][j]=temp;
		    	}
		    }
		    
		    
			PriorityQueue<Node> playerOneChildrenList = new PriorityQueue<Node>(Collections.reverseOrder()); //use priorityQueue,acsending or disacsending.
			Queue<Node> waitList = new LinkedList<Node>();
			boolean walkable = false;
			
				for(int i = 0;i<gameBoard.length;i++)
				{
					for(int j =0;j<gameBoard.length;j++)
					{
						if(copyOne[i][j].getOwnership()==0)
						{
						waitList.add(new Node(copyOne[i][j].getScore(),i,j,playerID));//everyPosition through the 2D array
						walkable = true;
				//		counter++;
						}
					}
				}


			if(walkable == false)
			{
				return node;
			}
			
			int waitSize = waitList.size();
			
			
			for(int l = 0;l<waitSize;l++)
			{
				
				Node[][] newCopy = new Node[gameBoard.length][gameBoard.length];
				Node temT = temNode;
			    for (int i = 0; i < gameBoard.length; i++) {
			    	for(int j =0;j<gameBoard.length;j++)
			    	{	
			    		Node temp = new Node(copyOne[i][j].getScore(),copyOne[i][j].getX(),copyOne[i][j].getY(),copyOne[i][j].getOwnership());
			    		newCopy[i][j]=temp;
			    	}
			    }
			    
				Node takenNode =waitList.poll();
				counter++;
				takenNode.setAccumulated(newCopy[takenNode.getX()][takenNode.getY()].getScore());
				takenNode.enemy = 0;
				newCopy[takenNode.getX()][takenNode.getY()].setOwnership(playerID);
				boolean connected = false;
				for(int i = 0;i<dx.length;i++)
				{
						int newX = takenNode.getX()+dx[i];
						int newY = takenNode.getY()+dy[i];
						if((newX>=0&&newX<gameBoard.length)&&(newY>=0&&newY<gameBoard.length))
						{
						if(newCopy[newX][newY].getOwnership()==playerID)
						{
		
							connected = true;
						}
						}
					
				}
				if(connected == true)
				{

					for(int i = 0;i<dx.length;i++)
					{
		
							int newX = takenNode.getX()+dx[i];
							int newY = takenNode.getY()+dy[i];
							if((newX>=0&&newX<gameBoard.length)&&(newY>=0&&newY<gameBoard.length))
							{
							if(newCopy[newX][newY].getOwnership()!=playerID&&newCopy[newX][newY].getOwnership()!=0)
							{
								this.eatOpponent(newCopy, playerID, newX, newY);
		
								takenNode.setAccumulated(takenNode.getAccumulated()+newCopy[newX][newY].getScore());
								enescore-=newCopy[newX][newY].getScore();
								takenNode.enemy = enescore;
							}
							}
						
					}
				}
	
				if(depth==1)
				{
	
					takenNode.setAccumulated(node.getAccumulated()+takenNode.getAccumulated());

				}
	

				int opponentID = 0;
				if(playerID == 1)
				{
					opponentID = 2;
				}else if(playerID == 2)
				{
					opponentID = 1;
				}
				if(playerOneChildrenList.isEmpty()==false)
				{
					//	System.out.println("asdas");
			//		System.out.println("A  depth:   "+depth);
				//	Thread.sleep(300);
						Node minValueNode = newAlphaBeta(takenNode,false,newCopy, opponentID, depth-1,true);
						counter++;
					
						


							if(node!=null)
								minValueNode.parentNode = new Node(node.getAccumulated(),node.getX(),node.getY(),node.getOwnership());
							playerOneChildrenList.add(minValueNode);
							continue;
	
					//	}
					
						
						
					
						
					
					
						
				}
				else
				{
				//	System.out.println("B  depth:   "+depth);
					//Thread.sleep(300);
					Node minValueNode = newAlphaBeta(takenNode,false,newCopy, opponentID, depth-1,false);
					//this.alpha = minValueNode;
					
					counter++;
					//playeroneNode++;
					if(node!=null)
						minValueNode.parentNode = new Node(node.getAccumulated(),node.getX(),node.getY(),node.getOwnership());
					
					playerOneChildrenList.add(minValueNode);
		
						if(depth == 3)
						{
			//				System.out.println("setting alpha");
							//Thread.sleep(300);
				
								this.alpha = minValueNode;
			//			System.out.println("alpha once:    "+this.alpha.getAccumulated());
					//	Thread.sleep(5000);
						}
				}
				
			}

			if(playerOneChildrenList.peek().parentNode!=null||node!=null)
			{
			//	System.out.println("returning:    "+playerOneChildrenList.peek().getAccumulated());
			//	System.out.println(500);
			playerOneChildrenList.peek().parentNode.setAccumulated(playerOneChildrenList.peek().getAccumulated());
			playerOneChildrenList.peek().parentNode.noPoll = playerOneChildrenList.peek().noPoll;

			return playerOneChildrenList.poll().parentNode;
			}
			else
			{
				playerOneChildrenList.peek().nodeExpanded = counter;
				return playerOneChildrenList.poll();
			}
		
		}else
		if(isMaximizing == false)//min
		{
			Node[][] copyOne = new Node[gameBoard.length][gameBoard.length];
			int currentEnescore = node.enemy;
			int nodesRx = node.maxrecordSearchedX;
			int nodesRy = node.maxrecordSearchedY;
			Node temNode = node;
		    for (int i = 0; i < gameBoard.length; i++) {
		    	for(int j =0;j<gameBoard.length;j++)
		    	{	
		    		Node temp = new Node(gameBoard[i][j].getScore(),gameBoard[i][j].getX(),gameBoard[i][j].getY(),gameBoard[i][j].getOwnership());
		    	 copyOne[i][j]=temp;
		    	}
		    }
			PriorityQueue<Node> playerTwoChildrenList = new PriorityQueue<Node>(); 
			Queue<Node> waitList = new LinkedList<Node>();
			boolean walkable = false;
	
				for(int i = 0;i<gameBoard.length;i++)
				{
					for(int j =0;j<gameBoard.length;j++)
					{
						if(copyOne[i][j].getOwnership()==0)
						{
						waitList.add(new Node(copyOne[i][j].getScore(),i,j,playerID));//everyPosition through the 2D array
						walkable = true;
				//		counter++;
						}
					}
				}
	
			if(walkable == false)
			{
				return node;
			}
			
			int waitSize = waitList.size();

			for(int l = 0;l<waitSize;l++)
			{

				Node[][] newCopy = new Node[gameBoard.length][gameBoard.length];
				Node temT = temNode;
			    for (int i = 0; i < gameBoard.length; i++) {
			    	for(int j =0;j<gameBoard.length;j++)
			    	{	
			    		Node temp = new Node(copyOne[i][j].getScore(),copyOne[i][j].getX(),copyOne[i][j].getY(),copyOne[i][j].getOwnership());
			    		newCopy[i][j]=temp;
			    	}
			    }

				Node takenNode = waitList.poll();
				counter++;
				newCopy[takenNode.getX()][takenNode.getY()].setOwnership(playerID);
		
				takenNode.setAccumulated(node.getAccumulated());

				takenNode.enemy= currentEnescore+newCopy[takenNode.getX()][takenNode.getY()].getScore();
				
				boolean connected = false;
				for(int i = 0;i<dx.length;i++)
				{
	
						int newX = takenNode.getX()+dx[i];
						int newY = takenNode.getY()+dy[i];
						if((newX>=0&&newX<gameBoard.length)&&(newY>=0&&newY<gameBoard.length))
						{
						if(newCopy[newX][newY].getOwnership()==playerID)
						{
				
							connected = true;
						}
						}
					
				}
				if(connected == true)
				{
			
					for(int i = 0;i<dx.length;i++)
					{
		
							int newX = takenNode.getX()+dx[i];
							int newY = takenNode.getY()+dy[i];
							if((newX>=0&&newX<gameBoard.length)&&(newY>=0&&newY<gameBoard.length))
							{
							if(newCopy[newX][newY].getOwnership()!=playerID&&newCopy[newX][newY].getOwnership()!=0)
							{
								this.eatOpponent(newCopy, playerID, newX, newY);
								takenNode.setAccumulated(takenNode.getAccumulated()-newCopy[newX][newY].getScore());	
								takenNode.enemy+=newCopy[newX][newY].getScore();
								
							}
							}
						
					}
				}

				int opponentID = 0;
				if(playerID == 1)
				{
					opponentID = 2;
				}else if(playerID == 2)
				{
					opponentID = 1;
				}
				
				if(playerTwoChildrenList.isEmpty()==false)
				{
			//		System.out.println(" C  depth:   "+depth);
				//	Thread.sleep(300);
						Node maxValueNode = newAlphaBeta(takenNode,true,newCopy, opponentID, depth-1,true);
						counter++;
			
						
						//playeroneNode++;
						maxValueNode.parentNode = new Node(node.getAccumulated(),node.getX(),node.getY(),node.getOwnership());
						if(this.alpha!=null)
						{
				//		System.out.println(maxValueNode.getAccumulated()+"         "+this.alpha.getAccumulated());
					//	Thread.sleep(5000);
						if(maxValueNode.getAccumulated()<this.alpha.getAccumulated())
						{
					//		System.out.println("break     l should be "+waitSize+"   now:   "+l);
							playerTwoChildrenList.add(maxValueNode);
							break;
							//node.noPoll = true;
							
						}
						}
						playerTwoChildrenList.add(maxValueNode);
					//	if(maxValueNode.noPoll == true)
					//	{
					//		System.out.println("the discard value:  "+maxValueNode.getAccumulated()+"    compareTo:   "+this.alpha.getAccumulated());
					//		break;
					//	}
						
						
						
						
					
				}
				else
				{
				//	System.out.println(" D  depth:   "+depth);
				//	Thread.sleep(300);
					Node maxValueNode = newAlphaBeta(takenNode,true,newCopy, opponentID, depth-1,false);
					counter++;
					
					maxValueNode.parentNode = new Node(node.getAccumulated(),node.getX(),node.getY(),node.getOwnership());
					if(this.alpha!=null)
					{
					if(maxValueNode.getAccumulated()<=this.alpha.getAccumulated())
					{
					//	System.out.println("break     l should be "+waitSize+"   now:   "+l);
						playerTwoChildrenList.add(maxValueNode);
						break;
						//node.noPoll = true;
						
					}
					}
					playerTwoChildrenList.add(maxValueNode);
				//	if(maxValueNode.noPoll == true)
				//	{
				//		
				//		break;
				//	}
				}
			
			}
			playerTwoChildrenList.peek().parentNode.setAccumulated(playerTwoChildrenList.peek().getAccumulated());

			return playerTwoChildrenList.poll().parentNode;
		}
		return null;
	
	}
	
	
	
	
	
	
	

	public void paintComponent(Graphics g)
	{
		for(int x=0;x<gameBoard.length;x++)
		{
			for(int y=0;y<gameBoard.length;y++)
			{      
				if(gameBoard[y][x].getOwnership() == 0)
				{
					g.drawImage(this.getFrame(), x*50, y*50, 50, 50, this);
					StringBuilder sb = new StringBuilder();
					sb.append("");
					sb.append(gameBoard[y][x].getScore());
					String score = sb.toString();
					g.setColor(Color.LIGHT_GRAY);
					g.setFont(new Font("Gill Sans",Font.BOLD,14));
					if(score.length()==2)
					{
						g.drawString(score, x*50+16, y*50+30);
					}else
					{
						g.drawString(score, x*50+21, y*50+30);
					}
				}else
				if(gameBoard[y][x].getOwnership() == 1)
				{
					g.drawImage(this.getFrameB(), x*50, y*50, 50, 50, this);
					g.setColor(Color.gray);
					StringBuilder sb = new StringBuilder();
					sb.append("");
					sb.append(gameBoard[y][x].getScore());
					String score = sb.toString();
					g.setFont(new Font("Gill Sans",Font.BOLD,14));
					if(score.length()==2)
					{
						g.drawString(score, x*50+16, y*50+30);
					}else
					{
						g.drawString(score, x*50+21, y*50+30);
					}
					
				}else
				if(gameBoard[y][x].getOwnership() == 2)
				{
					g.drawImage(this.getFrameG(), x*50, y*50, 50, 50, this);
					g.setColor(Color.gray);
					StringBuilder sb = new StringBuilder();
					sb.append("");
					sb.append(gameBoard[y][x].getScore());
					String score = sb.toString();
					g.setFont(new Font("Gill Sans",Font.BOLD,14));
					if(score.length()==2)
					{
						g.drawString(score, x*50+16, y*50+30);
					}else
					{
						g.drawString(score, x*50+21, y*50+30);
					}
					
				}
			}

		}
		g.setColor(Color.BLACK);
		g.fillRect(0, 300, 300, 332);
		g.setColor(Color.GREEN);
		g.drawString("Blue Player Score:  "+pOs,10,gameBoard.length*50+30 );
		g.drawString("Blue Player Decision Time:  "+pOt,10,gameBoard.length*50+55 );
		g.drawString("Blue expanded:  "+playeroneNode,10,gameBoard.length*50+80 );
		g.drawString("Blue Average Node/t:  "+ban,10,gameBoard.length*50+105 );
		g.drawString("Blue Average Time/t:  "+new DecimalFormat("#0.0000").format(bat),10,gameBoard.length*50+130 );
		
		g.fillRect(0, gameBoard.length*50+141, gameBoard.length*50, 3);
		
		g.drawString("Red Player Score:  "+pTs,10,gameBoard.length*50+ 165);
		g.drawString("Red Player Decision Time:  "+pTt,10,gameBoard.length*50+190 );
		
		g.drawString("Red expanded:  "+playertwoNode,10,gameBoard.length*50+215 );
		
		
		g.drawString("Red Average Node/t:  "+ran,10,gameBoard.length*50+240 );
		
		g.drawString("Red Average Time/t:  "+new DecimalFormat("#0.0000").format(rat),10,gameBoard.length*50+265 );
	}
	
	
}

