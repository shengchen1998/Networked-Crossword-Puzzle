package classes;

import java.util.ArrayList;

public class Grid
{
	public int index1;
	public int index2;
	public boolean down;
	public boolean across;
	public char letter;
	public int occurr;
	public Grid()
	{
		index1 = -1;
		index2 = -1;
		down = false;
		across = false;
		letter = 0;
		occurr = 0;
	}
}
