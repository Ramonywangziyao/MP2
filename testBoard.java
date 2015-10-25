import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	static int playeroneNode;
	static int playertwoNode;
	
	public String pOs="";
	public String pTs="";
	public int [] dx = {0,-1,0,1};
	public int [] dy = {-1,0,1,0};
	int maxrecordSearchedX = 0;
	int maxrecordSearchedY = 0;
	public testBoard(String fileName) throws IOException
	{
		pOt = "";
		pTt = "";
		playeroneNode = 0;
		playertwoNode = 0;
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
				takenNode.setAccumulated(takenNode.getScore());

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
							}
							}
						
					}
				}
	
				if(node!=null)
				{
					takenNode.setAccumulated(node.getAccumulated()+takenNode.getAccumulated()-enescore);
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
				if(node!=null)
				{
				minValueNode.parentNode = new Node(node.getAccumulated(),node.getX(),node.getY(),node.getOwnership());
			//	minValueNode.parentNode.ev = minValueNode.getAccumulated()-node.enemy;
				}
			//	else
			//	{
					minValueNode.parentNode = minValueNode;
			//		minValueNode.parentNode.ev = minValueNode.ev;
			//	}
					
				
			//	System.out.println("value:  "+minValueNode.getAccumulated()+"   enemy:  "+node.enemy+"    ev:  "+node.ev);
			//	Thread.sleep(50);
				playerOneChildrenList.add(minValueNode);
				
			}

			if(playerOneChildrenList.peek().parentNode!=null&&node!=null)
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
				return playerOneChildrenList.poll();
			}
		
		}else
		if(isMaximizing == false)//min
		{
			Node[][] copyOne = new Node[gameBoard.length][gameBoard.length];
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
				newCopy[takenNode.getX()][takenNode.getY()].setOwnership(playerID);
		
				takenNode.setAccumulated(node.getAccumulated());
				takenNode.enemy= newCopy[takenNode.getX()][takenNode.getY()].getScore();
				
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
	

	public Node newAlphaBeta(Node node,  boolean isMaximizing, Node[][] gameBoard,int playerID,int depth,boolean searchOnce) throws InterruptedException 
	{
		// TODO Auto-generated method stub
		//if node is terminal   BASE CASE
		
		if(depth==0) //node equals terminal node   origin   ***************************
		{
			System.out.println("oops");
			return node; //return heuristic value accumulated Score
		}
		
		if(isMaximizing == true)//max
		{
	
			Node[][] copyOne = new Node[gameBoard.length][gameBoard.length];
			int enescore=0;
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
			int count = 0;
		//	System.out.println("MAX:   start from:   x: "+maxrecordSearchedX+"   y:"+maxrecordSearchedY+"   at depth:  "+depth);
			//Thread.sleep(200);
			if(searchOnce == true)
			{	System.out.println("MAX    recod x:  "+maxrecordSearchedX+"    y  "+maxrecordSearchedY);}
			else
			{System.out.println("ALL");}
			for(int i = maxrecordSearchedX;i<gameBoard.length;i++)
			{
				for(int j = maxrecordSearchedY;j<gameBoard.length;j++)
				{
					if(copyOne[i][j].getOwnership() == 0)
					{
						if(searchOnce == true&&maxrecordSearchedX<gameBoard.length&&maxrecordSearchedY<gameBoard.length)
						{
						//	System.out.println("MIN:   do entered one time check:   new x: "+i+"   new y:"+j+"   at depth:  "+depth);
				//			Thread.sleep(200);
						waitList.add(new Node(copyOne[i][j].getScore(),i,j,playerID));//everyPosition through the 2D array
						walkable = true;
						maxrecordSearchedX = i+1;
						maxrecordSearchedY = j+1;
						//playeroneNode++;
						break;
						}else
						{
						waitList.add(new Node(copyOne[i][j].getScore(),i,j,playerID));//everyPosition through the 2D array
						walkable = true;
						}
						//playeroneNode++;
					}
			
				}
				if(searchOnce == true)
				{
					break;
				}
			}
			/*
			for(int i = maxrecordSearchedX;i<gameBoard.length;i++)
			{
				for(int j = maxrecordSearchedY;j<gameBoard.length;j++)
				{
					if(copyOne[i][j].getOwnership() == 0)
					{
						waitList.add(new Node(copyOne[i][j].getScore(),i,j,playerID));//everyPosition through the 2D array
						walkable = true;
						if(searchOnce == true&&maxrecordSearchedX<gameBoard.length&&maxrecordSearchedY<gameBoard.length)
						{
					//	System.out.println("MAX:   do entered one time check:   new x: "+i+"   new y:"+j+"   at depth:  "+depth);
					//	Thread.sleep(200);
						maxrecordSearchedX = i;
						maxrecordSearchedY = j;
						//playeroneNode++;
						break;
						}
					}
			
				}
				if(searchOnce == true)
				{
					break;
				}
			}
			*/
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
				playeroneNode++;

				takenNode.setAccumulated(takenNode.getScore());
			
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
							}
							}
						
					}
				}
	
				if(node!=null)
				{
					takenNode.setAccumulated(node.getAccumulated()+takenNode.getAccumulated()-enescore);
				}
	


				int opponentID = 0;
				if(playerID == 1)
				{
					opponentID = 2;
				}else if(playerID == 2)
				{
					opponentID = 1;
				}
				if(playerOneChildrenList.size()!=0)
				{
					
					//if(tempNode.getAccumulated()<playerTwoChildrenList.peek().getAccumulated())
					//	System.out.println("MAX not empty"+"   at depth:  "+depth);
						Node minValueNode = newAlphaBeta(takenNode,false,newCopy, opponentID, depth-1,true);
					//	System.out.println("MAX:   The try value is :   "+minValueNode.getAccumulated()+"     compare to :  "+playerOneChildrenList.peek().getAccumulated()+"   at depth:  "+depth);
				//		Thread.sleep(200);
						while(minValueNode.getAccumulated()>playerOneChildrenList.peek().getAccumulated())
						{
						//	System.out.println("while");
							playeroneNode++;
							if(node!=null)
								minValueNode.parentNode = new Node(node.getAccumulated(),node.getX(),node.getY(),node.getOwnership());
							playerOneChildrenList.add(minValueNode);
					//		System.out.println("calling min");
							minValueNode = newAlphaBeta(takenNode,false,newCopy, opponentID, depth-1,true);
					
						}
						
						maxrecordSearchedX = 0;
						maxrecordSearchedY = 0;
						continue;
						
					
					
				}
				else
				{
				//	System.out.println("MAX:  first time taking max, expand all"+"   at depth:  "+depth);
				//	Thread.sleep(200);
					Node minValueNode = newAlphaBeta(takenNode,false,newCopy, opponentID, depth-1,false);
					playeroneNode++;
					if(node!=null)
						minValueNode.parentNode = new Node(node.getAccumulated(),node.getX(),node.getY(),node.getOwnership());
					playerOneChildrenList.add(minValueNode);
				}
	
			}

			if(playerOneChildrenList.peek().parentNode!=null&&node!=null)
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
				playerOneChildrenList.peek().nodeExpanded = playeroneNode;
				return playerOneChildrenList.poll();
			}
		
		}else
		if(isMaximizing == false)//min
		{
		//	System.out.println("called!");

			Node[][] copyOne = new Node[gameBoard.length][gameBoard.length];
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
		//	System.out.println("MIN:   start from:   x: "+maxrecordSearchedX+"   y:"+maxrecordSearchedY+"   at depth:  "+depth);
			if(searchOnce == true)
				{System.out.println("MIN    recod x:  "+maxrecordSearchedX+"    y  "+maxrecordSearchedY);}
			else
			{System.out.println("ALL");}
			for(int i = maxrecordSearchedX;i<gameBoard.length;i++)
			{
				for(int j = maxrecordSearchedY;j<gameBoard.length;j++)
				{
					if(copyOne[i][j].getOwnership() == 0)
					{
						if(searchOnce == true&&maxrecordSearchedX<gameBoard.length&&maxrecordSearchedY<gameBoard.length)
						{
						//	System.out.println("MIN:   do entered one time check:   new x: "+i+"   new y:"+j+"   at depth:  "+depth);
				//			Thread.sleep(200);
						waitList.add(new Node(copyOne[i][j].getScore(),i,j,playerID));//everyPosition through the 2D array
						walkable = true;
						maxrecordSearchedX = i;
						maxrecordSearchedY = j;
						//playeroneNode++;
						break;
						}else
						{
						waitList.add(new Node(copyOne[i][j].getScore(),i,j,playerID));//everyPosition through the 2D array
						walkable = true;
						}
						//playeroneNode++;
					}
			
				}
				if(searchOnce == true)
				{
					break;
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
				playeroneNode++;
				newCopy[takenNode.getX()][takenNode.getY()].setOwnership(playerID);
		
				takenNode.setAccumulated(node.getAccumulated());
				takenNode.enemy= newCopy[takenNode.getX()][takenNode.getY()].getScore();
				
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
				
				if(playerTwoChildrenList.size()!=0)
				{
					
					//if(tempNode.getAccumulated()<playerTwoChildrenList.peek().getAccumulated())
				//		System.out.println("MIN not empty"+"   at depth:  "+depth);
						Node maxValueNode = newAlphaBeta(takenNode,true,newCopy, opponentID, depth-1,true);
				//		System.out.println("MIN:   The try value is :   "+maxValueNode.getAccumulated()+"     compare to :  "+playerTwoChildrenList.peek().getAccumulated()+"   at depth:  "+depth);
					//	Thread.sleep(200);
						while(maxValueNode.getAccumulated()<playerTwoChildrenList.peek().getAccumulated())
						{
						playeroneNode++;
						maxValueNode.parentNode = new Node(node.getAccumulated(),node.getX(),node.getY(),node.getOwnership());
						playerTwoChildrenList.add(maxValueNode);
						maxValueNode = newAlphaBeta(takenNode,true,newCopy, opponentID, depth-1,true);
					
						}
						maxrecordSearchedX = 0;
						maxrecordSearchedY = 0;
						continue;
					
				}
				else
				{
				//	System.out.println("MIN:   first time taking min, expand all"+"   at depth:  "+depth);
			//		Thread.sleep(200);
					Node maxValueNode = newAlphaBeta(takenNode,true,newCopy, opponentID, depth-1,false);
					playeroneNode++;
					maxValueNode.parentNode = new Node(node.getAccumulated(),node.getX(),node.getY(),node.getOwnership());
					playerTwoChildrenList.add(maxValueNode);
				}
			
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
		g.drawString("Blue Player Score:  "+pOs,10,gameBoard.length*50+30 );
		g.drawString("Blue Player Decision Time:  "+pOt,10,gameBoard.length*50+55 );
		g.drawString("Red Player Score:  "+pTs,10,gameBoard.length*50+80 );
		g.drawString("Red Player Decision Time:  "+pTt,10,gameBoard.length*50+105 );
		g.drawString("Current Node expanded:  "+playeroneNode,10,gameBoard.length*50+130 );
		
	}
	
}

