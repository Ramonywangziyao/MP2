
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
					if(maxValueNode.getAccumulated()<this.alpha.getAccumulated())
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
	
	
	
	