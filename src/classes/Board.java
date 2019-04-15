package classes;

public class Board
{
	public static Grid[][] grids;
	public static final int SIZE = 15;
	
	public Board()
	{
		Board.grids = new Grid[SIZE][SIZE];
		for (int i = 0; i < SIZE; ++i)
		{
			for (int j = 0; j < SIZE; ++j)
			{
				Board.grids[i][j] = new Grid();
			}
		}
	}
	
	public boolean check()
	{
		for(int i = 0;i < SIZE;++i)
		{
			for(int j = 0;j < SIZE;++j)
			{
				int size = Board.grids[i][j].start.size();
				if(size ==1)
				{
					int index = Board.grids[i][j].start.get(0);
					boolean across = Server.answers.get(index).getValue();
					int length = Server.answers.get(index).getKey().length();
					boolean joint = false;
					if(across)
					{
						for(int k = 0;k < length;++k)
						{
							if(Board.grids[i+k][j].occurr==2)
							{
								joint = true;
								break;
							}
						}
						if(joint == false)
						{
							return false;
						}
					}
					else
					{

						for(int k = 0;k < length;++k)
						{
							if(Board.grids[i][j+k].occurr==2)
							{
								joint = true;
								break;
							}
						}
						if(joint == false)
						{
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean put(int index, int x, int y)
	{
		String str = Server.answers.get(index).getKey();
		int length = str.length();
		boolean across = Server.answers.get(index).getValue();
		
		if (across)
		{
			if (x != 0 && Board.grids[x - 1][y].letter != 0)
			{
				return false;
			}
			if (x+length != SIZE && Board.grids[x + length][y].letter != 0)
			{
				return false;
			}
			boolean joint = false;
			for (int i = 0; i < length; ++i)
			{
				if (!(Board.grids[x + i][y].letter == 0 || Board.grids[x + i][y].letter == str.charAt(i)))
				{
					return false;
				}
				if (y != 0)
				{
					if (Board.grids[x + i][y - 1].across == true)
					{
						return false;
					}
					if (Board.grids[x + i][y - 1].down == true&&Board.grids[x + i][y].down!=true)
					{
						return false;
					}
				}
				if (y != SIZE - 1)
				{
					if (Board.grids[x + i][y + 1].across == true)
					{
						return false;
					}
					if (Board.grids[x + i][y + 1].down == true&&Board.grids[x + i][y].down!=true)
					{
						return false;
					}
				}
				if (Board.grids[x + i][y].letter == str.charAt(i))
				{
					joint = true;
				}
			}
			if (index == Server.answers.size() - 1 && joint == false)
			{
				return false;
			}
			for (int i = 0; i < length; ++i)
			{
				if (Board.grids[x + i][y].letter == 0)
				{
					Board.grids[x + i][y].letter = str.charAt(i);
				} else
				{
					Board.grids[x + i][y].occurr += 1;
//					for(int a = 0;a < Board.SIZE;++a)
//					{
//						for(int b = 0;b < Board.SIZE;++b)
//						{
//							if(Board.grids[b][a].letter==0)
//							{
//								System.out.print(' ');
//							}
//							else
//							{
//								System.out.print(Board.grids[b][a].letter);
//							}
//						}
//						System.out.println();
//					}
				}
				Board.grids[x + i][y].across = true;
				Board.grids[x + i][y].occurr += 1;
			}
			
		} else
		{
			if (y != 0 && Board.grids[x][y-1].letter != 0)
			{
				return false;
			}
			if (y+length != SIZE && Board.grids[x][y+length].letter != 0)
			{
				return false;
			}
			boolean joint = false;
			for (int i = 0; i < length; ++i)
			{
				if (!(Board.grids[x][y+i].letter == 0 || Board.grids[x][y+1].letter == str.charAt(i)))
				{
					return false;
				}
				if (x != 0)
				{
					if (Board.grids[x-1][y+i].down == true)
					{
						return false;
					}
					if (Board.grids[x -1][y +i].across == true&&Board.grids[x][y+i].across!=true)
					{
						return false;
					}
				}
				if (x != SIZE - 1)
				{
					if (Board.grids[x+1][y+i].down == true)
					{
						return false;
					}
					if (Board.grids[x+1][y +i].across == true&&Board.grids[x][y+i].across!=true)
					{
						return false;
					}
				}
				if (Board.grids[x][y+i].letter == str.charAt(i))
				{
					joint = true;
				}
			}
			if (index == Server.answers.size() - 1 && joint == false)
			{
				return false;
			}
			for (int i = 0; i < length; ++i)
			{
				if (Board.grids[x][y+i].letter == 0)
				{
					Board.grids[x][y+i].letter = str.charAt(i);
				} else
				{
					Board.grids[x][y+i].occurr += 1;
//					for(int a = 0;a < Board.SIZE;++a)
//					{
//						for(int b = 0;b < Board.SIZE;++b)
//						{
//							if(Board.grids[b][a].letter==0)
//							{
//								System.out.print(' ');
//							}
//							else
//							{
//								System.out.print(Board.grids[b][a].letter);
//							}
//						}
//						System.out.println();
//					}
				}
				Board.grids[x][y+i].down = true;
				Board.grids[x + i][y].occurr += 1;
			}
		}
		Board.grids[x][y].start.add(index);
		return true;
		
	}
	
	public void remove(int index, int x, int y)
	{
		String str = Server.answers.get(index).getKey();
		int length = str.length();
		boolean across = Server.answers.get(index).getValue();
		if(across)
		{
			for (int i = 0; i < length; ++i)
			{
				Board.grids[x + i][y].across = false;
				if (Board.grids[x + i][y].occurr != 2)
				{
					Board.grids[x + i][y].letter = 0;
				}
				Board.grids[x + i][y].occurr -= 1;
			}
		}
		else
		{
			for (int i = 0; i < length; ++i)
			{
				Board.grids[x][y+i].down = false;
				if (Board.grids[x][y+i].occurr != 2)
				{
					Board.grids[x][y+i].letter = 0;
				}
				Board.grids[x][y+i].occurr -= 1;
			}
		}
		for(int i = 0;i <Board.grids[x][y].start.size();++i )
		{
			if(Board.grids[x][y].start.get(i)==index)
			{
				Board.grids[x][y].start.remove(i);
			}
		}
		
	}
}
