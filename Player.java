

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.swing.JComponent;

public class Player {
	
	public int score;
	public ArrayList nodeExpandPerMove;
	public ArrayList timePerMove;
	public GameBoard board;
	
	private int playerID;
	private int playerMode;
	
	

	public Player(int playerID, int mode){
		score = 0;
		this.playerID = playerID; //(1,2) = (green, blue)
		nodeExpandPerMove = new ArrayList<Integer>();
		timePerMove = new ArrayList<Double>();
		this.setPlayerMode(mode);
	}
	
	public int getTotalNode(){
		int total = 0;
		for (int i=0; i<nodeExpandPerMove.size(); i++){
			total += ((Integer) nodeExpandPerMove.get(i)).intValue();
		}
		return total;
	}
	
	public double getTotalTime(){
		double total = 0;
		for (int i=0; i<timePerMove.size(); i++){
			total += ((double) timePerMove.get(i));
		}
		return total;
	}
	
	public void setPlayerMode(int mode){
		if (mode==0) {
			playerMode = 0; //minimax
		} else if (mode==1) {
			playerMode = 1; //alpha-beta
		} else {
			System.out.println("Invalid mode");
			System.exit(1);
		}
	}
	
	public void choice() throws IOException{
		//TODO mini or alpha strategy
		testBoard newBoard = new testBoard(null);
		if (playerMode==0){
			Node theNode = minimax(null,true,newBoard.getGameBoard(),1,3);
		}
		if (playerMode==1){
			alpha();
		}
	}

	private void alpha() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	//minimax
	
	public Node min(Node one, Node two)
	{
		if(one.getAccumulated()<two.getAccumulated())
		{
			return one;
		}
		else
			return two;
	}
	public Node max(Node one, Node two)
	{
		if(one.getAccumulated()>two.getAccumulated())
		{
			return one;
		}
		else
			return two;	
	}
	public void eatOpponent(Node[][] gameBoard,int playerID,int x,int y)
	{
			gameBoard[x][y].setOwnership(playerID);
	}
	public int [] dx = {0,-1,0,1};
	public int [] dy = {-1,0,1,0};
	//public Queue<Node> playerOneChildrenList = new LinkedList<Node>();
	static class PQsort implements Comparator<Integer> {
		 
		public int compare(Integer one, Integer two) {
			return two - one;
		}
	}
	private Node minimax(Node node,  boolean isMaximizing, Node[][] gameBoard,int playerID,int depth) {
		// TODO Auto-generated method stub
		//if node is terminal   BASE CASE
		if(depth==0) //node equals terminal node   origin   ***************************
		{
			return node; //return heuristic value accumulated Score
		}

		if(isMaximizing == true)//max
		{
			
			//traverse the map to put the node
			PriorityQueue<Node> playerOneChildrenList = new PriorityQueue<Node>(Collections.reverseOrder()); //use priorityQueue,acsending or disacsending.
			Queue<Node> waitList = new LinkedList<Node>();
			for(int i = 0;i<6;i++)
			{
				for(int j = 0;i<6;i++)
				{
					if(gameBoard[i][j].getOwnership() == 0)
					{
						waitList.add(new Node(gameBoard[i][j].getScore(),i,j,playerID));//everyPosition through the 2D array
					//	Node[][] newBoard = gameBoard;
					//	newBoard[i][j].comquared = true;
					//	newBoard[i][j].setOwnership(playerID);
					}
			
				}
			}
			
			for(int l = 0;l<waitList.size();l++)
			{
				Node takenNode =waitList.poll();
				//takenNode.setOwnership(playerID);
				Node [][] boardCopy = gameBoard;
				
				if(node!=null)
				{
					takenNode.setAccumulated(node.getAccumulated()+takenNode.getAccumulated());
					takenNode.parentNode = node;
					//playerOneChildrenList.add(takenNode);
				}
				else
				{
					takenNode.setAccumulated(takenNode.getAccumulated());
					//playerOneChildrenList.add(takenNode);
				}
				boardCopy[takenNode.getX()][takenNode.getY()].setOwnership(playerID);
				//check to comquare or not
				for(int i = 0;i<4;i++)
				{
					if(gameBoard[takenNode.getX()+dx[i]][takenNode.getY()+dy[i]].getOwnership()==playerID)
					{
						for(int j = 0; j < 4; j++)
						{
							if(gameBoard[takenNode.getX()+dx[i]][takenNode.getY()+dy[i]].getOwnership()!=playerID)
							{
								eatOpponent(gameBoard,playerID,takenNode.getX()+dx[i],takenNode.getY()+dy[i]);
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
				
				Node minValueNode = minimax(takenNode,false,boardCopy, opponentID, depth-1);
				playerOneChildrenList.add(minValueNode);
				//Node comparedValue = max(searchedNode,bestValue);
			}
			return playerOneChildrenList.poll();
			
		
		}else
		if(isMaximizing == false)//min
		{
			PriorityQueue<Node> playerTwoChildrenList = new PriorityQueue<Node>(); 
			Queue<Node> waitList = new LinkedList<Node>();
			for(int i = 0;i<6;i++)
			{
				for(int j = 0;i<6;i++)
				{
					if(gameBoard[i][j].getOwnership() == 0)
					{
						waitList.add(new Node(gameBoard[i][j].getScore(),i,j,playerID));//everyPosition through the 2D array
						//gameBoard[i][j].comquared = true;
						//gameBoard[i][j].setOwnership(playerID);
					}
			
				}
			}
			
			for(int l = 0;l<waitList.size();l++)
			{
				Node takenNode = waitList.poll();
				Node [][] boardCopy = gameBoard;
				
				if(node!=null)
				{
					takenNode.setAccumulated(node.getAccumulated()+takenNode.getAccumulated());
					takenNode.parentNode = node;
					//playerTwoChildrenList.add(takenNode);
				}
				else
				{
					takenNode.setAccumulated(takenNode.getAccumulated());
					//playerTwoChildrenList.add(takenNode);
				}
				boardCopy[takenNode.getX()][takenNode.getY()].setOwnership(playerID);
				//check to comquare or not
				for(int i = 0;i<4;i++)
				{
					if(gameBoard[takenNode.getX()+dx[i]][takenNode.getY()+dy[i]].getOwnership()==playerID)
					{
						for(int j = 0; j < 4; j++)
						{
							if(gameBoard[takenNode.getX()+dx[i]][takenNode.getY()+dy[i]].getOwnership()!=playerID)
							{
								eatOpponent(gameBoard,playerID,takenNode.getX()+dx[i],takenNode.getY()+dy[i]);
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
				
				Node maxValueNode = minimax(takenNode,true,boardCopy, opponentID, depth-1);
				playerTwoChildrenList.add(maxValueNode);
			}
			return playerTwoChildrenList.poll();
		}
		return null;
		
		
		
		
	}



	
	
}
