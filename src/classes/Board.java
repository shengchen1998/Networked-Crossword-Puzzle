package classes;

public class Board
{
	public Grid[][] grids;
	public static final int SIZE = 20;
	
	public Board()
	{
		grids = new Grid[SIZE][SIZE];
		for (int i = 0; i < SIZE; ++i)
		{
			for (int j = 0; j < SIZE; ++j)
			{
				grids[i][j] = new Grid();
			}
		}
	}
	
	public boolean check()
	{
		for(int i = 0;i < SIZE;++i)
		{
			for(int j = 0;j < SIZE;++j)
			{
				int size = grids[i][j].start.size();
				if(size ==1)
				{
					int index = grids[i][j].start.get(0);
					boolean across = Server.answers.get(index).getValue();
					int length = Server.answers.get(index).getKey().length();
					boolean joint = false;
					if(across)
					{
						for(int k = 0;k < length;++k)
						{
							if(grids[i+k][j].occurr==2)
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
							if(grids[i][j+k].occurr==2)
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
			if (x != 0 && grids[x - 1][y].letter != 0)
			{
				return false;
			}
			if (x != SIZE - 1 && grids[x + 1][y].letter != 0)
			{
				return false;
			}
			boolean joint = false;
			for (int i = 0; i < length; ++i)
			{
				if (!(grids[x + i][y].letter == 0 || grids[x + i][y].letter == str.charAt(i)))
				{
					return false;
				}
				if (y != 0)
				{
					if (grids[x + i][y - 1].across == true)
					{
						return false;
					}
				}
				if (y != SIZE - 1)
				{
					if (grids[x + i][y + 1].across == true)
					{
						return false;
					}
				}
				if (grids[x + i][y].letter == str.charAt(i))
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
				if (grids[x + i][y].letter == 0)
				{
					grids[x + i][y].letter = str.charAt(i);
				} else
				{
					grids[x + i][y].occurr += 1;
				}
				grids[x + i][y].across = true;
			}
			
		} else
		{
			if (y != 0 && grids[x][y-1].letter != 0)
			{
				return false;
			}
			if (y != SIZE - 1 && grids[x][y+1].letter != 0)
			{
				return false;
			}
			boolean joint = false;
			for (int i = 0; i < length; ++i)
			{
				if (!(grids[x][y+i].letter == 0 || grids[x][y+1].letter == str.charAt(i)))
				{
					return false;
				}
				if (x != 0)
				{
					if (grids[x-1][y+i].down == true)
					{
						return false;
					}
				}
				if (x != SIZE - 1)
				{
					if (grids[x+1][y+i].down == true)
					{
						return false;
					}
				}
				if (grids[x][y+i].letter == str.charAt(i))
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
				if (grids[x][y+i].letter == 0)
				{
					grids[x][y+i].letter = str.charAt(i);
				} else
				{
					grids[x][y+i].occurr += 1;
				}
				grids[x][y+i].down = true;
			}
		}
		grids[x][y].start.add(index);
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
				grids[x + i][y].across = false;
				if (grids[x + i][y].occurr != 2)
				{
					grids[x + i][y].letter = 0;
				}
				grids[x + i][y].occurr -= 1;
			}
		}
		else
		{
			for (int i = 0; i < length; ++i)
			{
				grids[x][y+i].down = false;
				if (grids[x][y+i].occurr != 2)
				{
					grids[x][y+i].letter = 0;
				}
				grids[x][y+i].occurr -= 1;
			}
		}
		for(int i = 0;i <grids[x][y].start.size();++i )
		{
			if(grids[x][y].start.get(i)==index)
			{
				grids[x][y].start.remove(i);
			}
		}
		
	}
}
