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
	public String pOt="",pTt="";
	private static String fileName;
	public testBoard(String fileName) throws IOException
	{
		
		if(fileName == null)
		{
			
			this.fileName = "src/ziyao.txt";
			System.out.println("here :  "+fileName);
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
        for(int k = 0;k<6;k++)
        {
        	for(int l=0;l<6;l++)
        	{
        		System.out.print(gameBoard[k][l].getScore()+"   ");
        	}
        	System.out.println();
        }
		
		
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
	//	  System.out.print(theCount);
		while((line = bufferNew.readLine())!=null)
		{
			char [] vals = line.toCharArray();
            if (gameBoard == null) {
                size = vals.length;
                
                gameBoard = new Node[theCount][theCount];
               // System.out.println("here:   "+theCount+size);
            }
            row = size;
            cols = count;
            int startCount = 0;
            int y = 0;
            System.out.println("aaaaaaaaaaa:    "+vals.length);
            for (int col = 0; col < vals.length ; col++) {
            	
            	System.out.println(col);
            	System.out.println("value:   "+vals[col]+"   "+   " col: "+col);
    
            	if(col==vals.length-1||(vals[col]!='\t'&&vals[col+1]=='\t'))
            	{
            		System.out.println("bbbb");
            		System.out.println("now is X: "+x+"   y: "+y);
            		gameBoard[x][startCount] = new Node(Character.getNumericValue(vals[col]), x, y,0);
            		
            		y++;
            		startCount++;
            		//System.out.print(gameBoard[x][col]);
            	}
            	
            	else if(vals[col]!='\t'&&vals[col+1]!='\t')
            	{		
            		System.out.println("aaaaaa:   "+vals[col]+vals[col+1]);
            		String newNumber =""+ vals[col]+vals[col+1];
            		System.out.println("WOOOOO:   "+newNumber);
            		System.out.println("now is X: "+x+"   y: "+y);
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
           
         //   System.out.println();
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
	public int [] dx = {0,-1,0,1};
	public int [] dy = {-1,0,1,0};
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
				
				if(node!=null)
				{
					takenNode.setAccumulated(node.getAccumulated()+takenNode.getScore());
				}
				else
				{
					
					takenNode.setAccumulated(takenNode.getScore());
				}
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
				Node minValueNode = minimax(takenNode,false,newCopy, opponentID, depth-1);
				if(node!=null)
				minValueNode.parentNode = new Node(node.getAccumulated(),node.getX(),node.getY(),node.getOwnership());
				
				
				playerOneChildrenList.add(minValueNode);
				
			}

			if(playerOneChildrenList.peek().parentNode!=null)
			{
			playerOneChildrenList.peek().parentNode.setAccumulated(playerOneChildrenList.peek().getAccumulated());
			System.out.println("max x:  "+playerOneChildrenList.peek().parentNode.getX()+"   y:  "+playerOneChildrenList.peek().parentNode.getY()+"   value:  "+playerOneChildrenList.peek().parentNode.getAccumulated());
			return playerOneChildrenList.poll().parentNode;
			}
			else
			{
			if(node==null)
			System.out.println("pulling max value   "+ playerOneChildrenList.peek().getAccumulated()+"  x:  "+playerOneChildrenList.peek().getX()+"  y:  "+playerOneChildrenList.peek().getY());
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
				//maxValueNode.parentNode.setAccumulated(maxValueNode.getAccumulated());
				playerTwoChildrenList.add(maxValueNode);
			}
			playerTwoChildrenList.peek().parentNode.setAccumulated(playerTwoChildrenList.peek().getAccumulated());
			System.out.println("min:   parent x:  "+playerTwoChildrenList.peek().parentNode.getX()+"    parent y:  "+playerTwoChildrenList.peek().parentNode.getY()+"   Value: "+playerTwoChildrenList.peek().getAccumulated());
			return playerTwoChildrenList.poll().parentNode;
		}
		return null;
	
	}

	
	
	
	
	public Node alphaBeta(Node node,  boolean isMaximizing, Node[][] gameBoard,int playerID,int depth) throws InterruptedException {
		// TODO Auto-generated method stub
		//if node is terminal   BASE CASE

		if(depth==0) //node equals terminal node   origin   ***************************
		{
			return node; //return heuristic value accumulated Score
		}
		
		if(isMaximizing == true)//max
		{
			Node[][] copyOne = new Node[6][6];
		    for (int i = 0; i < 6; i++) {
		    	for(int j =0;j<6;j++)
		    	{	
		    		Node temp = new Node(gameBoard[i][j].getScore(),gameBoard[i][j].getX(),gameBoard[i][j].getY(),gameBoard[i][j].getOwnership());
		    	 copyOne[i][j]=temp;
		    	}
		    }
			PriorityQueue<Node> playerOneChildrenList = new PriorityQueue<Node>(Collections.reverseOrder()); //use priorityQueue,acsending or disacsending.
			Queue<Node> waitList = new LinkedList<Node>();
			boolean walkable = false;
			for(int i = 0;i<6;i++)
			{
				for(int j = 0;j<6;j++)
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
				Node[][] newCopy = new Node[6][6];
			    for (int i = 0; i < 6; i++) {
			    	for(int j =0;j<6;j++)
			    	{	
			    		Node temp = new Node(copyOne[i][j].getScore(),copyOne[i][j].getX(),copyOne[i][j].getY(),copyOne[i][j].getOwnership());
			    		newCopy[i][j]=temp;
			    	}
			    }
				Node takenNode =waitList.poll();
				if(node!=null)
				{
					takenNode.setAccumulated(node.getAccumulated()+takenNode.getScore());
					takenNode.parentNode = node;
				}
				else
				{
					
					takenNode.setAccumulated(takenNode.getScore());
				}
				newCopy[takenNode.getX()][takenNode.getY()].setOwnership(playerID);
				boolean connected = false;
				for(int i = 0;i<dx.length;i++)
				{
						int newX = takenNode.getX()+dx[i];
						int newY = takenNode.getY()+dy[i];
						if((newX>=0&&newX<6)&&(newY>=0&&newY<6))
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
							if((newX>=0&&newX<6)&&(newY>=0&&newY<6))
							{
							if(newCopy[newX][newY].getOwnership()!=playerID&&newCopy[newX][newY].getOwnership()!=0)
							{
								this.eatOpponent(newCopy, playerID, newX, newY);
								takenNode.setAccumulated(takenNode.getAccumulated()+newCopy[newX][newY].getScore());
								
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
				if(playerOneChildrenList.isEmpty()==false)
				{
					if(takenNode.getAccumulated()>playerOneChildrenList.peek().getAccumulated())
					{
						Node maxValueNode = minimax(takenNode,true,newCopy, opponentID, depth-1);
						if(maxValueNode!=null)
						{
							playerOneChildrenList.add(maxValueNode);
							continue;
					
						}else
						{
							playerOneChildrenList.add(takenNode);
							continue;
						
						}
					}
					else
					{
						continue;
					}
					
				}else
				{
					Node maxValueNode = minimax(takenNode,true,newCopy, opponentID, depth-1);
					if(maxValueNode!=null)
					{
						playerOneChildrenList.add(maxValueNode);
				
					}else
					{
						playerOneChildrenList.add(takenNode);
					
					}
				}
				
			}
			return playerOneChildrenList.poll();
			
		
		}else
		if(isMaximizing == false)//min
		{
			Node[][] copyOne = new Node[6][6];
		    for (int i = 0; i < 6; i++) {
		    	for(int j =0;j<6;j++)
		    	{	
		    		Node temp = new Node(gameBoard[i][j].getScore(),gameBoard[i][j].getX(),gameBoard[i][j].getY(),gameBoard[i][j].getOwnership());
		    	 copyOne[i][j]=temp;
		    	}
		    }
			PriorityQueue<Node> playerTwoChildrenList = new PriorityQueue<Node>(); 
			Queue<Node> waitList = new LinkedList<Node>();
			boolean walkable = false;
			for(int i = 0;i<6;i++)
			{
				for(int j = 0;j<6;j++)
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
				
				Node[][] newCopy = new Node[6][6];
			    for (int i = 0; i < 6; i++) {
			    	for(int j =0;j<6;j++)
			    	{	
			    		Node temp = new Node(copyOne[i][j].getScore(),copyOne[i][j].getX(),copyOne[i][j].getY(),copyOne[i][j].getOwnership());
			    		newCopy[i][j]=temp;
			    	}
			    }
				Node takenNode = waitList.poll();
				newCopy[takenNode.getX()][takenNode.getY()].setOwnership(playerID);
				boolean connected = false;
				for(int i = 0;i<dx.length;i++)
				{
	
						int newX = takenNode.getX()+dx[i];
						int newY = takenNode.getY()+dy[i];
						if((newX>=0&&newX<6)&&(newY>=0&&newY<6))
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
							if((newX>=0&&newX<6)&&(newY>=0&&newY<6))
							{
							if(newCopy[newX][newY].getOwnership()!=playerID&&newCopy[newX][newY].getOwnership()!=0)
							{
								this.eatOpponent(newCopy, playerID, newX, newY);
								takenNode.setAccumulated(takenNode.getAccumulated()+newCopy[newX][newY].getScore());
					
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
					if(takenNode.getAccumulated()<playerTwoChildrenList.peek().getAccumulated())
					{
						Node maxValueNode = minimax(takenNode,true,newCopy, opponentID, depth-1);
						if(maxValueNode!=null)
						{
							playerTwoChildrenList.add(maxValueNode);
							continue;
					
						}else
						{
							playerTwoChildrenList.add(takenNode);
							continue;
						
						}
					}
					else
					{
						continue;
					}
					
				}else
				{
					Node maxValueNode = minimax(takenNode,true,newCopy, opponentID, depth-1);
					if(maxValueNode!=null)
					{
						playerTwoChildrenList.add(maxValueNode);
				
					}else
					{
						playerTwoChildrenList.add(takenNode);
					
					}
				}
				
			
			}
			return playerTwoChildrenList.poll();
		}
		return null;
	
	}
	public String pOs="";
	public String pTs="";
	public void paintComponent(Graphics g)
	{
	//	System.out.println("Now the cols:  "+cols+"    the rows:  "+row);
		//g.setColor(Color.blue);
		//g.fillRect(50, 50, 10, 10);

	
		for(int x=0;x<gameBoard.length;x++)
		{
			for(int y=0;y<gameBoard.length;y++)
			{
				//System.out.println("ownship:  "+gameBoard[x][y].getOwnership());
				System.out.println("Current Score:   "+gameBoard[y][x].getScore()+"   Confirm current Node:    "+gameBoard[y][x].getOwnership()+"    x:   "+gameBoard[y][x].getX()+"    y:  "+gameBoard[y][x].getY());          
				if(gameBoard[y][x].getOwnership() == 0)
				{
				//	System.out.println("HERE!!!");
					//g.setColor(Color.black);
					//g.fillRect(x*50, y*50, 50, 50);
					g.drawImage(this.getFrame(), x*50, y*50, 50, 50, this);
					//g.drawImage(this.getFrame(), x*50, y*50, this);
					StringBuilder sb = new StringBuilder();
					sb.append("");
				//	System.out.println("the Score:   "+gameBoard[x][y].getScore());
					sb.append(gameBoard[y][x].getScore());
				//	System.out.println("HERE!!!ALSO!");
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
					//g.drawImage(this.getFrameB(),x*50, y*50, this);
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
					//g.drawImage(this.getFrameG(),x*50, y*50, this);
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
		
	}
	
}

