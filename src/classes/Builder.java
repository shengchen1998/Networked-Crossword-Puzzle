package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Builder
{
	private static Coor[][] coors;
	private static boolean[][] discovered;
	public static Grid[][] grids;
	public static final int SIZE = 100;
	public static int acrossSize;
	public static int downSize;
	public static int totalSize;
	public static ArrayList<Integer> acrossNumber;
	public static ArrayList<Integer> downNumber;
	public static ArrayList<String> acrossAnswer;
	public static ArrayList<String> downAnswer;
	public static ArrayList<String> acrossQuestion;
	public static ArrayList<String> downQuestion;
	public static Answer[] answers;
	private static Stack<Coor> stk;
	private static String path;
	private static File directory;
	private static File list[];
	private static int fileCount;
	public Builder()
	{
		grids = new Grid[SIZE][SIZE];
		discovered = new boolean[SIZE][SIZE];
		coors = new Coor[SIZE][SIZE];
		for (int i = 0; i < SIZE; ++i)
		{
			for (int j = 0; j < SIZE; ++j)
			{
				coors[i][j] = new Coor(i, j);
				grids[i][j] = new Grid(i, j);
				discovered[i][j] = false;
			}
		}
		Builder.acrossNumber = new ArrayList<Integer>();
		Builder.downNumber = new ArrayList<Integer>();
		Builder.acrossAnswer = new ArrayList<String>();
		Builder.downAnswer = new ArrayList<String>();
		Builder.acrossQuestion = new ArrayList<String>();
		Builder.downQuestion = new ArrayList<String>();
		Builder.stk = new Stack<Coor>();
		path = "gamedata";
		directory = new File(path);
		list = directory.listFiles();
		fileCount = list.length;
	}
	
	
	public void createBoard()
	{
		int index;
		if (fileCount == 1)
		{
			index = 0;
		} else
		{
			index = (int) (Math.random() * fileCount);
		}
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(list[index]));
		} catch (FileNotFoundException e1)
		{
			System.out.println("Cannot open file " + index);
		}
		String s = null;
		try
		{
			s = reader.readLine();
		} catch (IOException e)
		{
			System.out.println("File is empty.");
		}
		s = s.trim().toLowerCase();
		
		//1st check
		if ((!s.equals("across")) && (!s.equals("down")))
		{
			System.out.println("First line is malformatted.");
			return;
		}
		if (s.trim().equals("across"))
		{
			try
			{
				s = reader.readLine();
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			
			while ((s != null) && (!s.trim().toLowerCase().equals("down")))
			{
				s = s.toLowerCase();
				StringTokenizer tokenizer = new StringTokenizer(s, "|");
				
				//2nd check
				if (tokenizer.countTokens() != 3)
				{
					System.out.println("Malformatted:not 3 token");
					for (int i = 0; i < tokenizer.countTokens(); ++i)
					{
						System.out.println(tokenizer.nextToken());
					}
					clear();
					return;
				}
				String num = tokenizer.nextToken();
				int n;
				
				//3rd check
				try
				{
					n = Integer.parseInt(num);
					Builder.acrossNumber.add(n);
					System.out.println(n);
				} catch (Exception e)
				{
					System.out.println("Malformatted:not number");
					clear();
					return;
				}
				
				//4th check
				String answer = tokenizer.nextToken();
				for (int i = 0; i < answer.length(); ++i)
				{
					char c = answer.charAt(i);
					if (c < 'a' || c > 'z')
					{
						System.out.println("Malformatted:not letter");
						clear();
						return;
					}
				}
				Builder.acrossAnswer.add(answer);
				System.out.println(answer);
				String question = tokenizer.nextToken();
				Builder.acrossQuestion.add(question);
				System.out.println(question);
				
				//5th check
				try
				{
					s = reader.readLine();
					
				} catch (IOException e)
				{
					System.out.println("Malformatted: no down");
					clear();
					return;
				}
			}
			try
			{
				s = reader.readLine();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			while (s != null)
			{
				s = s.toLowerCase();
				StringTokenizer tokenizer = new StringTokenizer(s, "|");
				
				//6th check
				if (tokenizer.countTokens() != 3)
				{
					System.out.println("Malformatted:not 3 token");
					for (int i = 0; i < tokenizer.countTokens(); ++i)
					{
						System.out.println(tokenizer.nextToken());
					}
					clear();
					return;
				}
				String num = tokenizer.nextToken();
				int n;
				
				//7th check
				try
				{
					n = Integer.parseInt(num);
					Builder.downNumber.add(n);
					System.out.println(n);
				} catch (Exception e)
				{
					System.out.println("Malformatted:not number");
					clear();
					return;
				}
				String answer = tokenizer.nextToken();
				
				//8th check
				for (int i = 0; i < answer.length(); ++i)
				{
					char c = answer.charAt(i);
					if (c < 'a' || c > 'z')
					{
						System.out.println("Malformatted:not letter");
						clear();
						return;
					}
				}
				Builder.downAnswer.add(answer);
				System.out.println(answer);
				String question = tokenizer.nextToken();
				Builder.downQuestion.add(question);
				System.out.println(question);
				try
				{
					s = reader.readLine();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		} else if (s.trim().equals("down"))
		{
			try
			{
				s = reader.readLine();
				
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			
			while ((s != null) && (!s.trim().toLowerCase().equals("across")))
			{
				s = s.toLowerCase();
				StringTokenizer tokenizer = new StringTokenizer(s, "|");
				
				//2nd check
				if (tokenizer.countTokens() != 3)
				{
					System.out.println("Malformatted:not 3 token");
					for (int i = 0; i < tokenizer.countTokens(); ++i)
					{
						System.out.println(tokenizer.nextToken());
					}
					clear();
					return;
				}
				String num = tokenizer.nextToken();
				int n;
				
				//3rd check
				try
				{
					n = Integer.parseInt(num);
					Builder.downNumber.add(n);
				} catch (Exception e)
				{
					System.out.println("Malformatted:not number");
					clear();
					return;
				}
				String answer = tokenizer.nextToken();
				
				//4th check
				for (int i = 0; i < answer.length(); ++i)
				{
					char c = answer.charAt(i);
					if (c < 'a' || c > 'z')
					{
						System.out.println("Malformatted:not letter");
						clear();
						return;
					}
				}
				Builder.downAnswer.add(answer);
				String question = tokenizer.nextToken();
				Builder.downQuestion.add(question);
				
				//5th check
				try
				{
					s = reader.readLine();
				} catch (IOException e)
				{
					System.out.println("Malformatted: no across");
					clear();
					return;
				}
			}
			try
			{
				s = reader.readLine();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (s != null)
			{
				s = s.toLowerCase();
//				ArrayList<String> tokens = new ArrayList<String>();
				StringTokenizer tokenizer = new StringTokenizer(s, "|");
				
				//6th check
				if (tokenizer.countTokens() != 3)
				{
					System.out.println("Malformatted:not 3 token");
					for (int i = 0; i < tokenizer.countTokens(); ++i)
					{
						System.out.println(tokenizer.nextToken());
					}
					clear();
					return;
				}
				String num = tokenizer.nextToken();
				int n;
				
				//7th check
				try
				{
					n = Integer.parseInt(num);
					Builder.acrossNumber.add(n);
				} catch (Exception e)
				{
					System.out.println("Malformatted:not number");
					clear();
					return;
				}
				String answer = tokenizer.nextToken();
				
				//8th check
				for (int i = 0; i < answer.length(); ++i)
				{
					char c = answer.charAt(i);
					if (c < 'a' || c > 'z')
					{
						System.out.println("Malformatted:not letter");
						clear();
						return;
					}
				}
				Builder.acrossAnswer.add(answer);
				String question = tokenizer.nextToken();
				Builder.acrossQuestion.add(question);
				try
				{
					s = reader.readLine();
				} catch (IOException e)
				{
					e.printStackTrace();
					return;
				}
			}
		} else
		//9th check
		{
			System.out.println("First line is malformatted.");
			return;
		}
		Builder.acrossSize = acrossNumber.size();
		Builder.downSize = downNumber.size();
		Builder.totalSize = acrossSize + downSize;
		for (int i = 0; i < acrossSize; ++i)
		{
			System.out.println(acrossNumber.get(i) + "|" + acrossAnswer.get(i) + "|" + acrossQuestion.get(i));
		}
		for (int i = 0; i < downSize; ++i)
		{
			System.out.println(downNumber.get(i) + "|" + downAnswer.get(i) + "|" + downQuestion.get(i));
		}
		
		//10th check
		for (int i = 0; i < acrossSize; ++i)
		{
			int num = acrossNumber.get(i);
			for (int j = 0; j < downSize; ++j)
			{
				if (downNumber.get(j) == num)
				{
					if (acrossAnswer.get(i).charAt(0) != downAnswer.get(j).charAt(0))
					{
						System.out.println("Malformatted: first letter does not match.");
						clear();
						return;
					}
				}
			}
		}
		
		for (int i = 0; i < acrossSize - 1; ++i)
		{
			int max = i;
			for (int j = i + 1; j < acrossSize; ++j)
			{
				if (acrossAnswer.get(j).length() > acrossAnswer.get(max).length())
				{
					max = j;
				}
			}
			int t = acrossNumber.get(i);
			acrossNumber.set(i, acrossNumber.get(max));
			acrossNumber.set(max, t);
			String temp = acrossAnswer.get(i);
			acrossAnswer.set(i, acrossAnswer.get(max));
			acrossAnswer.set(max, temp);
			temp = acrossQuestion.get(i);
			acrossQuestion.set(i, acrossQuestion.get(max));
			acrossQuestion.set(max, temp);
		}
		for (int i = 0; i < downSize - 1; ++i)
		{
			int max = i;
			for (int j = i + 1; j < downSize; ++j)
			{
				if (downAnswer.get(j).length() > downAnswer.get(max).length())
				{
					max = j;
				}
			}
			int t = downNumber.get(i);
			downNumber.set(i, downNumber.get(max));
			downNumber.set(max, t);
			String temp = downAnswer.get(i);
			downAnswer.set(i, downAnswer.get(max));
			downAnswer.set(max, temp);
			temp = downQuestion.get(i);
			downQuestion.set(i, downQuestion.get(max));
			downQuestion.set(max, temp);
		}
//		for (int i = 0; i < acrossSize; ++i)
//		{
//			System.out.println(acrossNumber.get(i) + "|" + acrossAnswer.get(i) + "|" + acrossQuestion.get(i));
//		}
//		for (int i = 0; i < downSize; ++i)
//		{
//			System.out.println(downNumber.get(i) + "|" + downAnswer.get(i) + "|" + downQuestion.get(i));
//		}
		Builder.answers = new Answer[totalSize];
		int i = 0;
		int j = 0;
		int count = 0;
		while (!((i == acrossSize) && (j == downSize)))
		{
			if (i == acrossSize)
			{
				answers[count] = (new Answer(downAnswer.get(j), false));
				++j;
			} else if (j == downSize)
			{
				answers[count] = (new Answer(acrossAnswer.get(i), true));
				++i;
			} else if (acrossAnswer.get(i).length() >= downAnswer.get(j).length())
			{
				answers[count] = (new Answer(acrossAnswer.get(i), true));
				++i;
			} else
			{
				answers[count] = (new Answer(downAnswer.get(j), false));
				++j;
			}
			++count;
		}
		for (i = 0; i < totalSize; ++i)
		{
			System.out.println(answers[i].first);
		}
		long before = System.currentTimeMillis();
		try
		{
			this.bt(0);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		long after = System.currentTimeMillis();
		System.out.println("time used: " + (after - before));
	}
	
	private static void clear()
	{
		Builder.acrossNumber.clear();
		Builder.acrossAnswer.clear();
		Builder.acrossQuestion.clear();
		Builder.downNumber.clear();
		Builder.downAnswer.clear();
		Builder.downQuestion.clear();
	}
	private boolean check()//check if the potential arrangement is valid
	{
		for (int i = 0; i < SIZE; ++i)
		{
			for (int j = 0; j < SIZE; ++j)
			{
				discovered[i][j] = false;
			}
		}
		stk.clear();
		int a = answers[0].x;
		int b = answers[0].y;
		stk.push(coors[a][b]);
		while (!stk.empty())
		{
			Coor coor = stk.pop();
			int x = coor.x;
			int y = coor.y;
			discovered[x][y] = true;
			if (x > 0)
			{
				if (grids[x - 1][y].letter != 0)
				{
					if (discovered[x - 1][y] == false)
					{
						stk.push(coors[x - 1][y]);
					}
				}
			}
			if (x < SIZE - 1)
			{
				if (grids[x + 1][y].letter != 0)
				{
					if (discovered[x + 1][y] == false)
					{
						stk.push(coors[x + 1][y]);
					}
				}
			}
			if (y > 0)
			{
				if (grids[x][y - 1].letter != 0)
				{
					if (discovered[x][y - 1] == false)
					{
						stk.push(coors[x][y - 1]);
					}
				}
			}
			if (y < SIZE - 1)
			{
				if (grids[x][y + 1].letter != 0)
				{
					if (discovered[x][y + 1] == false)
					{
						stk.push(coors[x][y + 1]);
					}
				}
			}
		}
		for (int i = 0; i < totalSize; ++i)
		{
			if (discovered[answers[i].x][answers[i].y] == false)
			{
				return false;
			}
		}
		for(int i = 0;i < totalSize-1;++i)
		{
			for(int j = i + 1;j < totalSize;++j)
			{
				if(answers[i].x == answers[j].x&&answers[i].y==answers[j].y)
				{
					int num1 = -1;
					int num2 = -1;
					if(answers[i].second == true)
					{
						for(int k = 0;k < acrossSize;++k)
						{
							if(answers[i].first.equals(acrossAnswer.get(k)))
							{
								num1 = acrossNumber.get(k);
								break;
							}
						}
						for(int k = 0;k < downSize;++k)
						{
							if(answers[j].first.equals(downAnswer.get(k)))
							{
								num2 = downNumber.get(k);
								break;
							}
						}
					}
					else
					{
						for(int k = 0;k < downSize;++k)
						{
							if(answers[i].first.equals(downAnswer.get(k)))
							{
								num1 = downNumber.get(k);
								break;
							}
						}
						for(int k = 0;k < acrossSize;++k)
						{
							if(answers[j].first.equals(acrossAnswer.get(k)))
							{
								num2 = acrossNumber.get(k);
								break;
							}
						}
					}
					if(num1!=num2)
					{
						return false;
					}
				}
			}
		}
		return true;
		
	}
	private boolean put(int index, int x, int y) throws Exception
	{
		String str = answers[index].first;
		int length = str.length();
		if (answers[index].second)
		{
			if (x != 0 && grids[x - 1][y].letter != 0)
			{
				return false;
			}
			if (x + length != SIZE && grids[x + length][y].letter != 0)
			{
				return false;
			}
			boolean joint = false;
			for (int i = 0; i < length; ++i)
			{
				Grid g = grids[x + i][y];
				char c = g.letter;
				if (c != 0 && c != str.charAt(i))
				{
					return false;
				}
				if (y != 0)
				{
					if (grids[x + i][y - 1].across == true)
					{
						return false;
					}
					if (grids[x + i][y - 1].down == true && g.down != true)
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
					if (grids[x + i][y + 1].down == true && g.down != true)
					{
						return false;
					}
				}
				if (c == str.charAt(i))
				{
					joint = true;
				}
			}
			if (index != 0 && joint == false)
			{
				return false;
			}
			for (int i = 0; i < length; ++i)
			{
				Grid g = grids[x + i][y];
				if (g.letter == 0)
				{
					g.letter = str.charAt(i);
				}
				g.across = true;
				g.occurr += 1;
			}
			
		} else
		{
			if (y != 0 && grids[x][y - 1].letter != 0)
			{
				return false;
			}
			if (y + length != SIZE && grids[x][y + length].letter != 0)
			{
				return false;
			}
			boolean joint = false;
			for (int i = 0; i < length; ++i)
			{
				Grid g = grids[x][y + i];
				char c = g.letter;
				if (c != 0 && c != str.charAt(i))
				{
					return false;
				}
				if (x != 0)
				{
					if (grids[x - 1][y + i].down == true)
					{
						return false;
					}
					if (grids[x - 1][y + i].across == true && g.across != true)
					{
						return false;
					}
				}
				if (x != SIZE - 1)
				{
					if (grids[x + 1][y + i].down == true)
					{
						return false;
					}
					if (grids[x + 1][y + i].across == true && g.across != true)
					{
						return false;
					}
				}
				if (c == str.charAt(i))
				{
					joint = true;
				}
			}
			if (index != 0 && joint == false)
			{
				return false;
			}
			for (int i = 0; i < length; ++i)
			{
				Grid g = grids[x][y + i];
				if (g.letter == 0)
				{
					g.letter = str.charAt(i);
				}
				
				g.down = true;
				g.occurr += 1;
			}
		}
		Grid g = grids[x][y];
		if (g.index1 < 0)
		{
			g.index1 = index;
		} else
		{
			g.index2 = index;
		}
		answers[index].used = true;
		answers[index].x = x;
		answers[index].y = y;
		boolean flag = true;
		for (int i = 0; i < totalSize; ++i)
		{
			if (answers[i].used == false)
			{
				flag = false;
				break;
			}
		}
		if (flag)
		{
			if (check())
			{
				for (int j = 0; j < SIZE; ++j)
				{
					for (int i = 0; i < SIZE; ++i)
					{
						if (grids[i][j].letter == 0)
						{
							System.out.print(' ');
						} else
						{
							System.out.print(grids[i][j].letter);
						}
					}
					System.out.println();
				}
				throw new Exception("Founded!");
			}
		}
		return true;
		
	}
	
	private void remove(int index, int x, int y)
	{
		answers[index].used = false;
		answers[index].x = -1;
		answers[index].y = -1;
		String str = Builder.answers[index].first;
		int length = str.length();
		if (answers[index].second)
		{
			for (int i = 0; i < length; ++i)
			{
				Grid g = grids[x + i][y];
				g.across = false;
				g.occurr -= 1;
				if (g.occurr == 0)
				{
					g.letter = 0;
				}
			}
		} else
		{
			for (int i = 0; i < length; ++i)
			{
				Grid g = grids[x][y + i];
				g.down = false;
				g.occurr -= 1;
				if (g.occurr == 0)
				{
					g.letter = 0;
				}
			}
		}
		
		Grid g = grids[x][y];
		if (g.index1 == index)
		{
			g.index1 = -1;
		} else
		{
			g.index2 = -1;
		}
	}
	private void bt(int index) throws Exception
	{
		String str = answers[index].first;
		int l = str.length();
		if (index == 0)
		{
			if (answers[0].second)
			{
				for (int y = SIZE/2; y < SIZE; ++y)
				{
					for (int x = SIZE/2; x < SIZE + 1 - l; ++x)
					{
						if (put(0, x, y))
						{
//							System.out.println("put " + str + " " + x + " " + y);
							for (int i = 1; i < totalSize; ++i)
							{
								if (answers[i].used == false && answers[i].second == false)
								{
//									System.out.println("bt" + i);
									bt(i);
								}
							}
							remove(0, x, y);
						}
						
					}
				}
			} else
			{
				for (int x = SIZE/2; x < SIZE; ++x)
				{
					for (int y = SIZE/2; y < SIZE + 1 - l; ++y)
					{
						if (put(0, x, y))
						{
//							System.out.println("put " + str + " " + x + " " + y);
							for (int i = 1; i < totalSize; ++i)
							{
								if (answers[i].used == false && answers[i].second == true)
								{
//									System.out.println("bt" + i);
									bt(i);
								}
							}
							remove(0, x, y);
						}
					}
				}
			}
			return;
		} else
		{
			if (answers[index].second)
			{
				for (int i = 0; i < totalSize; ++i)
				{
					Answer answer = answers[i];
					if (answers[i].used == true && answers[i].second == false)
					{
						String anstr = answer.first;
						int x = answer.x;
						int y = answer.y;
						for (int j1 = 0; j1 < l; ++j1)
						{
							for (int a = 0; a < anstr.length(); ++a)
							{
								if (anstr.charAt(a) == str.charAt(j1))
								{
									if ((x - j1 >= 0) && (x - j1 < SIZE + 1 - l) && (y + a < SIZE)
											&& (grids[x - j1][y + a].across == false))
									{
										if (put(index, x - j1, y + a))
										{
//											System.out.println(
//													"put " + str + " " + (answer.x - j1) + " " + (answer.y + a));
											for (int b = 1; b < totalSize; ++b)
											{
												if (answers[b].used == false)
												{
													bt(b);
												}
											}
											remove(index, x - j1, y + a);
										}
									}
								}
							}
						}
					}
				}
				
			} else
			{
				for (int i = 0; i < totalSize; ++i)
				{
					Answer answer = answers[i];
					
					if (answer.used == true && answer.second == true)
					{
						String anstr = answer.first;
						int x = answer.x;
						int y = answer.y;
						for (int j1 = 0; j1 < l; ++j1)
						{
							for (int a = 0; a < anstr.length(); ++a)
							{
								if (anstr.charAt(a) == str.charAt(j1))
								{
									if ((y - j1 >= 0) && (y - j1 < SIZE + 1 - l) && (x + a < SIZE)
											&& (grids[x + a][y - j1].down == false))
									{
										if (put(index, x + a, y - j1))
										{
//											System.out.println(
//													"put " + str + " " + (answer.x + a) + " " + (answer.y - j1));
											for (int b = 1; b < totalSize; ++b)
											{
												if (answers[b].used == false)
												{
													bt(b);
												}
											}
											remove(index, x + a, y - j1);
										}
									}
									
								}
							}
						}
					}
					
				}
			}
			return;
		}
	}
}

class Coor
{
	int x;
	int y;
	
	public Coor(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}

class Grid
{
	public int index1;
	public int index2;
	public boolean down;
	public boolean across;
	public char letter;
	public int occurr;
	public int x;
	public int y;
	
	public Grid(int x, int y)
	{
		index1 = -1;
		index2 = -1;
		down = false;
		across = false;
		letter = 0;
		occurr = 0;
		this.x = x;
		this.y = y;
	}
}

class Answer
{
	String first;
	boolean second;
	boolean used;
	int x;
	int y;
	
	public Answer(String first, boolean second)
	{
		this.first = first;
		this.second = second;
		this.used = false;
		this.x = -1;
		this.y = -1;
	}
}