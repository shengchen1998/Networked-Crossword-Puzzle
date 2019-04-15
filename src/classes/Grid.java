package classes;

import java.util.ArrayList;

public class Grid
{
	public ArrayList<Integer> start;
	public boolean down;
	public boolean across;
	public char letter;
	public int occurr;
	public Grid()
	{
		start = new ArrayList<Integer>();
		down = false;
		across = false;
		letter = 0;
		occurr = 0;
	}
}
