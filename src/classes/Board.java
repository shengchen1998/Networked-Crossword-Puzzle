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
			for (int i = 0; i < length; ++i)
			{
				if (!(board[x + i][y].letter == 0 || board[x + i][y].letter == str.charAt(i)))
				{
					return false;
				}
			}
		}
		return false;
	}
	
	public void remove(int index, int x, int y)
	{
		
	}
}
