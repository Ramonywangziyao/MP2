package cs440_hw2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameBoard {
	
	public Node[][] grid;
	
	private int playerTurn;
	private  int totalMove;	
	private static int row;
	private static int column;
	
	
	public GameBoard(){
		this.grid = null;
		this.totalMove = 0;
		row = column = 0;
//		timePerMove = new ArrayList<Double>();
	}
	
	public void loadBoard() throws IOException{
		String fileName;
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter the file name");
		fileName = scanner.next();
		FileReader fr = new FileReader(fileName);
		BufferedReader buffer = new BufferedReader (fr);

		String line;
		
		//get rows of game board
		while((line = buffer.readLine())!=null)
		{	
			row++;
		}
		fr.close();
		
		FileReader fr2 = new FileReader(fileName);
		BufferedReader bufferNew = new BufferedReader (fr2);
		
		int i = 0;
		while((line = bufferNew.readLine())!=null && i<row)
		{
			
			String[] temp = line.split("\t");
		
			//initialize grid
            if (grid == null) {
            	column = temp.length;
                grid = new Node[row][column];
            }

            for (int j=0; j<column; j++){
            	grid[i][j] = new Node(new Integer(temp[j]), 0);
            }
            i++;
            
            System.out.println();
		}
		this.print();
	}
	
	public void print(){
		System.out.println("  A\tB\tC\tD\tE\tF");
		for (int i=0; i<row; i++){
			System.out.print(i+1 + " ");
			for (int j=0; j<column; j++){
				System.out.print(grid[i][j].getScore() + "\t");
			}
			System.out.println();
		}
	}
	
	public int getPlayerTurn(){
		return playerTurn;
	}
	
	public int getTotalMove(){
		return totalMove;
	}
	
	public int getPlayerScore(int playerID){
		int temp = 0;
		for (int i=0; i<row; i++){
			for (int j=0; j<column; j++){
				if (grid[i][j].getOwnership() == playerID){
					temp += grid[i][j].getScore();
				}
			}
		}
		return temp;
	}

	public boolean gameOver() {
		for (int i=0; i<row; i++){
			for (int j=0; j<column; j++){
				if (grid[i][j].getOwnership() == 0){
					return false;
				}
			}
		}
		System.out.println("All nodes are occupied! GAME OVER!");
		return false;
	}
}
