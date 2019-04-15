package classes;

public class Board
{
	public Grid[][] board;
	public static final int SIZE = 20;
	
	public Board()
	{
		board = new Grid[SIZE][SIZE];
		for (int i = 0; i < SIZE; ++i)
		{
			for (int j = 0; j < SIZE; ++j)
			{
				board[i][j] = new Grid();
			}
		}
	}
	
	public boolean put(int index, int x, int y)
	{
		String str = Server.answers.get(index).getKey();
		int length = str.length();
		boolean across = Server.answers.get(index).getValue();
		
		if (across)
		{
			if (x != 0 && board[x - 1][y].letter != 0)
			{
				return false;
			}
			if (x != SIZE - 1 && board[x + 1][y].letter != 0)
			{
				return false;
			}
			boolean joint = false;
			for (int i = 0; i < length; ++i)
			{
				if (!(board[x + i][y].letter == 0 || board[x + i][y].letter == str.charAt(i)))
				{
					return false;
				}
				if (y != 0)
				{
					if (board[x + i][y - 1].across == true)
					{
						return false;
					}
				}
				if (y != SIZE - 1)
				{
					if (board[x + i][y - 1].across == true)
					{
						return false;
					}
				}
				if (board[x + i][y].letter == str.charAt(i))
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
				if (board[x + i][y].letter == 0)
				{
					board[x + i][y].letter = str.charAt(i);
				} else
				{
					board[x + i][y].occurr += 1;
				}
				board[x + i][y].across = true;
			}
			
		} else
		{
			if (y != 0 && board[x][y-1].letter != 0)
			{
				return false;
			}
			if (y != SIZE - 1 && board[x][y+1].letter != 0)
			{
				return false;
			}
			boolean joint = false;
			for (int i = 0; i < length; ++i)
			{
				if (!(board[x][y+i].letter == 0 || board[x][y+1].letter == str.charAt(i)))
				{
					return false;
				}
				if (x != 0)
				{
					if (board[x-1][y+i].down == true)
					{
						return false;
					}
				}
				if (x != SIZE - 1)
				{
					if (board[x+1][y+i].down == true)
					{
						return false;
					}
				}
				if (board[x][y+i].letter == str.charAt(i))
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
				if (board[x][y+i].letter == 0)
				{
					board[x][y+i].letter = str.charAt(i);
				} else
				{
					board[x][y+i].occurr += 1;
				}
				board[x][y+i].down = true;
			}
		}
		board[x][y].start.add(index);
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
				board[x + i][y].across = false;
				if (board[x + i][y].occurr != 2)
				{
					board[x + i][y].letter = 0;
				}
				board[x + i][y].occurr -= 1;
			}
		}
		else
		{
			for (int i = 0; i < length; ++i)
			{
				board[x][y+i].down = false;
				if (board[x][y+i].occurr != 2)
				{
					board[x][y+i].letter = 0;
				}
				board[x][y+i].occurr -= 1;
			}
		}
		for(int i = 0;i <board[x][y].start.size();++i )
		{
			if(board[x][y].start.get(i)==index)
			{
				board[x][y].start.remove(i);
			}
		}
		
	}
}
