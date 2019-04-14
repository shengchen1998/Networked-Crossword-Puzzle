package classes;

public class Board
{
	public Grid[][] board;
	
	public Board()
	{
		board = new Grid[20][20];
		for(int i = 0;i < 20;++i)
		{
			for(int j = 0;j < 20;++j)
			{
				board[i][j] = new Grid();
			}
		}
	}
	
	public boolean put(int index, int x, int y)
	{
		
		return false;
	}
	
	public void remove(int index, int x, int y)
	{
		
	}
}
