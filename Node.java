

public class Node implements Comparable<Node>{
	private int score;
	private int ownership;
	private int x;
	private int y;
	private int accumulatedScore = 0;
	public boolean comquared = false;
	public Node parentNode;
	
	 public int compareTo(Node arg0) 
     {
         if(this.accumulatedScore < arg0.accumulatedScore)
         {
             return -1;
         }
         else if(this.accumulatedScore> arg0.accumulatedScore)
         {
             return 1;
         }

         return 0;
     }
	
	public int getAccumulated()
	{
		return accumulatedScore;
	}
	public int getAccumulated(int newScore)
	{
		return accumulatedScore+=newScore;
	}
	public void setAccumulated(int newScore)
	{
		accumulatedScore+=newScore;
	}
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public void setX(int value)
	{
		x = value;
	}
	public void setY(int value)
	{
		y = value;
	}

	
	
	public Node(Integer score,int x,int y, int ownership){
		this.score = score;
		this.ownership = ownership; //(0,1,2) = (free, P1, P2)
		this.x = x;
		this.y = y;
	}
	
	public int getScore(){
		return score;
	}
	
	public void setScore(int s)
	{
		score = s;
	}
	public int getOwnership(){
		return ownership;
	}
	
	public void setOwnership(int playerID){
		ownership = playerID;
	}
}
