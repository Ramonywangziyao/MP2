package cs440_hw2;

public class Node {
	private int score;
	private int ownership;
	private int x;
	private int y;
	
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y；
	}
	public Node(Integer score, int ownership){
		this.score = score;
		this.ownership = ownership; //(0,1,2) = (free, P1, P2)
	}
	
	public int getScore(){
		return score;
	}
	
	public int getOwnership(){
		return ownership;
	}
	
	public void setOwnership(int playerID){
		ownership = playerID;
	}
}
