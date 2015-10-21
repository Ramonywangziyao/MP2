package cs440_hw2;

import java.util.ArrayList;

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
	
	public void choice(){
		//TODO mini or alpha strategy
		if (playerMode==0){
			minimax();
		}
		if (playerMode==1){
			alpha();
		}
	}

	private void alpha() {
		// TODO Auto-generated method stub
		
	}
	public int min(int one, int two)
	{
		if(one<two)
		{
			return one;
		}
		else
			return two;
	}
	public int max(int one, int two)
	{
		if(one>two)
		{
			return one;
		}
		else
			return two;	
	}
	private Node minimax(Node node,  boolean isMaximizing) {
		// TODO Auto-generated method stub
		//if node is terminal   BASE CASE
		if(node==endNode) //node equals terminal node   origin
		{
			return targetNode; //return heuristic
		}
		if(isMaximizing == true)//max
		{
			
			int bestValue;
			for each child node in Node
			{
				Node searchedNode = minimax(,,false);
				Node comparedValue = max(searchedNode.,bestValue);
			}
			return comparedValue;
			
		
		}else
		if(isMaximizing == false)//min
		{
		
			
			int bestValue;
			for each child node in Node
			{
				int searchedValue = minimax(,,true);
				int comparedValue = min(searchedValue,bestValue);
			}
			return comparedValue;
		
		
		}
		
		
		
		
	}
	
	
	
}
