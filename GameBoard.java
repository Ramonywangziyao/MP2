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
					takenNode.setAccumulated(node.getAccumulated()+takenNode.getScore()-node.enemy);
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