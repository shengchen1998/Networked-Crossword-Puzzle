package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

public class Server
{
	public static Coor[][] coors;
	public static boolean[][] discovered;
	public static Grid[][] grids;
	public static final int SIZE = 20;
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
	public static Stack<Coor> stk;
	
	public boolean check()
	{
		for (int i = 0; i < SIZE; ++i)
		{
			for (int j = 0; j < SIZE; ++j)
			{
				discovered[i][j] = false;
			}
		}
		stk.clear();
		for (int i = 0; i < SIZE; ++i)
		{
			boolean flag = false;
			for (int j = 0; j < SIZE; ++j)
			{
				
				if (grids[i][j].letter != 0)
				{
					stk.push(coors[i][j]);
					flag = true;
					break;
				}
			}
			if (flag)
			{
				break;
			}
		}
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
		for (int j = 0; j < SIZE; ++j)
		{
			for (int i = 0; i < SIZE; ++i)
			{
				if ((grids[i][j].letter != 0) && (discovered[i][j] == false))
				{
					return false;
				}
			}
		}
		return true;
		
	}
//	public void DFS(int x, int y)
//	{
//		discovered[x][y] = true;
//		if (x > 0)
//		{
//			if (grids[x - 1][y].letter != 0)
//			{
//				if (!discovered[x - 1][y])
//				{
//					DFS(x - 1, y);
//				}
//			}
//		}
//		if (x < SIZE - 1)
//		{
//			if (grids[x + 1][y].letter != 0)
//			{
//				if (!discovered[x + 1][y])
//				{
//					DFS(x + 1, y);
//				}
//			}
//		}
//		if (y > 0)
//		{
//			if (grids[x][y - 1].letter != 0)
//			{
//				if (!discovered[x][y - 1])
//				{
//					DFS(x, y - 1);
//				}
//			}
//		}
//		if (y < SIZE - 1)
//		{
//			if (grids[x][y + 1].letter != 0)
//			{
//				if (!discovered[x][y + 1])
//				{
//					DFS(x, y + 1);
//				}
//			}
//		}
//	}
	
	public boolean put(int index, int x, int y) throws Exception
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
//		for(int a = 0;a < SIZE;++a)
//		{
//			for(int b = 0;b < SIZE;++b)
//			{
//				if(grids[b][a].letter==0)
//				{
//					System.out.print(' ');
//				}
//				else
//				{
//					System.out.print(grids[b][a].letter);
//				}
//			}
//			System.out.println();
//		}
		Grid g = grids[x][y];
		if (g.index1 < 0)
		{
			g.index1 = index;
		} else
		{
			g.index2 = index;
		}
		answers[index].used = true;
		boolean flag = true;
		for(int i = 0;i< totalSize;++i)
		{
			if(answers[i].used == false)
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
	
	public void remove(int index, int x, int y)
	{
		answers[index].used = false;
		String str = Server.answers[index].first;
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
	
	public static void main(String[] args)
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
		Server.acrossNumber = new ArrayList<Integer>();
		Server.downNumber = new ArrayList<Integer>();
		Server.acrossAnswer = new ArrayList<String>();
		Server.downAnswer = new ArrayList<String>();
		Server.acrossQuestion = new ArrayList<String>();
		Server.downQuestion = new ArrayList<String>();
		Server.stk = new Stack<Coor>();
		String path = "gamedata";
		int fileCount = 0;
		File d = new File(path);
		File list[] = d.listFiles();
		for (int i = 0; i < list.length; i++)
		{
			++fileCount;
		}
//		System.out.println(fileCount);
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
		s = s.toLowerCase();
//		if ((!s.equals("across")) && (!s.equals("down")))
//		{
//			System.out.println("First line is malformatted.");
//			return;
//		}
		if (s.equals("across"))
		{
			try
			{
				s = reader.readLine();
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			while ((s != null) && (!s.toLowerCase().equals("down")))
			{
				s = s.toLowerCase();
//				ArrayList<String> tokens = new ArrayList<String>();
				StringTokenizer tokenizer = new StringTokenizer(s, "|");
				if (tokenizer.countTokens() != 3)
				{
					System.out.println("Malformatted:not 3 token");
					for (int i = 0; i < tokenizer.countTokens(); ++i)
					{
						System.out.println(tokenizer.nextToken());
					}
					Server.acrossNumber.clear();
					Server.acrossAnswer.clear();
					Server.acrossQuestion.clear();
					Server.downNumber.clear();
					Server.downAnswer.clear();
					Server.downQuestion.clear();
					return;
				}
				String num = tokenizer.nextToken();
				int n;
				try
				{
					n = Integer.parseInt(num);
					Server.acrossNumber.add(n);
					System.out.println(n);
				} catch (Exception e)
				{
					System.out.println("Malformatted:not number");
					Server.acrossNumber.clear();
					Server.acrossAnswer.clear();
					Server.acrossQuestion.clear();
					Server.downNumber.clear();
					Server.downAnswer.clear();
					Server.downQuestion.clear();
					return;
				}
				String answer = tokenizer.nextToken();
				for (int i = 0; i < answer.length(); ++i)
				{
					char c = answer.charAt(i);
					if (c < 'a' || c > 'z')
					{
						System.out.println("Malformatted:not letter");
						Server.acrossNumber.clear();
						Server.acrossAnswer.clear();
						Server.acrossQuestion.clear();
						Server.downNumber.clear();
						Server.downAnswer.clear();
						Server.downQuestion.clear();
						return;
					}
				}
				Server.acrossAnswer.add(answer);
				System.out.println(answer);
				String question = tokenizer.nextToken();
				Server.acrossQuestion.add(question);
				System.out.println(question);
				try
				{
					s = reader.readLine();
					
				} catch (IOException e)
				{
					System.out.println("Malformatted: no down");
					Server.acrossNumber.clear();
					Server.acrossAnswer.clear();
					Server.acrossQuestion.clear();
					Server.downNumber.clear();
					Server.downAnswer.clear();
					Server.downQuestion.clear();
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
				if (tokenizer.countTokens() != 3)
				{
					System.out.println("Malformatted:not 3 token");
					for (int i = 0; i < tokenizer.countTokens(); ++i)
					{
						System.out.println(tokenizer.nextToken());
					}
					Server.acrossNumber.clear();
					Server.acrossAnswer.clear();
					Server.acrossQuestion.clear();
					Server.downNumber.clear();
					Server.downAnswer.clear();
					Server.downQuestion.clear();
					return;
				}
				String num = tokenizer.nextToken();
				int n;
				try
				{
					n = Integer.parseInt(num);
					Server.downNumber.add(n);
					System.out.println(n);
				} catch (Exception e)
				{
					System.out.println("Malformatted:not number");
					Server.acrossNumber.clear();
					Server.acrossAnswer.clear();
					Server.acrossQuestion.clear();
					Server.downNumber.clear();
					Server.downAnswer.clear();
					Server.downQuestion.clear();
					return;
				}
				String answer = tokenizer.nextToken();
				for (int i = 0; i < answer.length(); ++i)
				{
					char c = answer.charAt(i);
					if (c < 'a' || c > 'z')
					{
						System.out.println("Malformatted:not letter");
						Server.acrossNumber.clear();
						Server.acrossAnswer.clear();
						Server.acrossQuestion.clear();
						Server.downNumber.clear();
						Server.downAnswer.clear();
						Server.downQuestion.clear();
						return;
					}
				}
				Server.downAnswer.add(answer);
				System.out.println(answer);
				String question = tokenizer.nextToken();
				Server.downQuestion.add(question);
				System.out.println(question);
				try
				{
					s = reader.readLine();
				} catch (IOException e)
				{
					System.out.println("Malformatted: no down");
					Server.acrossNumber.clear();
					Server.acrossAnswer.clear();
					Server.acrossQuestion.clear();
					Server.downNumber.clear();
					Server.downAnswer.clear();
					Server.downQuestion.clear();
					return;
				}
			}
		} else if (s.equals("down"))
		{
			try
			{
				s = reader.readLine();
				
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			while ((s != null) && (!s.toLowerCase().equals("across")))
			{
				s = s.toLowerCase();
//				ArrayList<String> tokens = new ArrayList<String>();
				StringTokenizer tokenizer = new StringTokenizer(s, "|");
				if (tokenizer.countTokens() != 3)
				{
					System.out.println("Malformatted:not 3 token");
					for (int i = 0; i < tokenizer.countTokens(); ++i)
					{
						System.out.println(tokenizer.nextToken());
					}
					Server.acrossNumber.clear();
					Server.acrossAnswer.clear();
					Server.acrossQuestion.clear();
					Server.downNumber.clear();
					Server.downAnswer.clear();
					Server.downQuestion.clear();
					return;
				}
				String num = tokenizer.nextToken();
				int n;
				try
				{
					n = Integer.parseInt(num);
					Server.downNumber.add(n);
				} catch (Exception e)
				{
					System.out.println("Malformatted:not number");
					Server.acrossNumber.clear();
					Server.acrossAnswer.clear();
					Server.acrossQuestion.clear();
					Server.downNumber.clear();
					Server.downAnswer.clear();
					Server.downQuestion.clear();
					return;
				}
				String answer = tokenizer.nextToken();
				for (int i = 0; i < answer.length(); ++i)
				{
					char c = answer.charAt(i);
					if (c < 'a' || c > 'z')
					{
						System.out.println("Malformatted:not letter");
						Server.acrossNumber.clear();
						Server.acrossAnswer.clear();
						Server.acrossQuestion.clear();
						Server.downNumber.clear();
						Server.downAnswer.clear();
						Server.downQuestion.clear();
						return;
					}
				}
				Server.downAnswer.add(answer);
				String question = tokenizer.nextToken();
				Server.downQuestion.add(question);
				try
				{
					s = reader.readLine();
				} catch (IOException e)
				{
					System.out.println("Malformatted: no down");
					Server.acrossNumber.clear();
					Server.acrossAnswer.clear();
					Server.acrossQuestion.clear();
					Server.downNumber.clear();
					Server.downAnswer.clear();
					Server.downQuestion.clear();
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
				if (tokenizer.countTokens() != 3)
				{
					System.out.println("Malformatted:not 3 token");
					for (int i = 0; i < tokenizer.countTokens(); ++i)
					{
						System.out.println(tokenizer.nextToken());
					}
					Server.acrossNumber.clear();
					Server.acrossAnswer.clear();
					Server.acrossQuestion.clear();
					Server.downNumber.clear();
					Server.downAnswer.clear();
					Server.downQuestion.clear();
					return;
				}
				String num = tokenizer.nextToken();
				int n;
				try
				{
					n = Integer.parseInt(num);
					Server.acrossNumber.add(n);
				} catch (Exception e)
				{
					System.out.println("Malformatted:not number");
					Server.acrossNumber.clear();
					Server.acrossAnswer.clear();
					Server.acrossQuestion.clear();
					Server.downNumber.clear();
					Server.downAnswer.clear();
					Server.downQuestion.clear();
					return;
				}
				String answer = tokenizer.nextToken();
				for (int i = 0; i < answer.length(); ++i)
				{
					char c = answer.charAt(i);
					if (c < 'a' || c > 'z')
					{
						System.out.println("Malformatted:not letter");
						Server.acrossNumber.clear();
						Server.acrossAnswer.clear();
						Server.acrossQuestion.clear();
						Server.downNumber.clear();
						Server.downAnswer.clear();
						Server.downQuestion.clear();
						return;
					}
				}
				Server.acrossAnswer.add(answer);
				String question = tokenizer.nextToken();
				Server.acrossQuestion.add(question);
				try
				{
					s = reader.readLine();
				} catch (IOException e)
				{
					System.out.println("Malformatted: no down");
					Server.acrossNumber.clear();
					Server.acrossAnswer.clear();
					Server.acrossQuestion.clear();
					Server.downNumber.clear();
					Server.downAnswer.clear();
					Server.downQuestion.clear();
					return;
				}
			}
		} else
		{
			System.out.println("First line is malformatted.");
			return;
		}
		Server.acrossSize = acrossNumber.size();
		Server.downSize = downNumber.size();
		Server.totalSize = acrossSize + downSize;
		for (int i = 0; i < acrossSize; ++i)
		{
			System.out.println(acrossNumber.get(i) + "|" + acrossAnswer.get(i) + "|" + acrossQuestion.get(i));
		}
		for (int i = 0; i < downSize; ++i)
		{
			System.out.println(downNumber.get(i) + "|" + downAnswer.get(i) + "|" + downQuestion.get(i));
		}
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
						Server.acrossNumber.clear();
						Server.acrossAnswer.clear();
						Server.acrossQuestion.clear();
						Server.downNumber.clear();
						Server.downAnswer.clear();
						Server.downQuestion.clear();
						return;
					}
				}
			}
		}
		
		for (int i = 0; i < acrossSize - 1; ++i)
		{
			int max = i;
			for (int j = i + 1; j < acrossAnswer.size(); ++j)
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
			for (int j = i + 1; j < downAnswer.size(); ++j)
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
		for (int i = 0; i < acrossSize; ++i)
		{
			System.out.println(acrossNumber.get(i) + "|" + acrossAnswer.get(i) + "|" + acrossQuestion.get(i));
		}
		for (int i = 0; i < downSize; ++i)
		{
			System.out.println(downNumber.get(i) + "|" + downAnswer.get(i) + "|" + downQuestion.get(i));
		}
		Server.answers = new Answer[totalSize];
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
//		for (i = 0; i < answers.size(); ++i)
//		{
//			System.out.println(answers.get(i));
//		}
		Server server = new Server();
		long before = System.currentTimeMillis();
		try
		{
			server.bt(0);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		long after = System.currentTimeMillis();
		System.out.println("time used: " + (after - before));
	}
	
	void bt(int index) throws Exception
	{
		String str = answers[index].first;
		int l = str.length();
		if(index==0)
		{
			if (answers[0].second)
			{
				for (int y = 0; y < Server.SIZE; ++y)
				{
					for (int x = 0; x < SIZE + 1 - l; ++x)
					{
						if(put(0,x,y))
						{
							//System.out.println("put "+str+" "+x+" "+y);
							for(int i = 1;i < totalSize;++i)
							{
								if(answers[i].used==false)
								{
									bt(i);
								}
							}
							remove(0,x,y);
						}
						
					}
				}
			}else
			{
				for (int x = 0; x < Server.SIZE; ++x)
				{
					for (int y = 0; y < SIZE + 1 - l; ++y)
					{
						if(put(0,x,y))
						{
							//System.out.println("put "+str+" "+x+" "+y);
							for(int i = 1;i < totalSize;++i)
							{
								if(answers[i].used==false)
								{
									bt(i);
								}
							}
							remove(0,x,y);
						}
					}
				}
			}
			return;
		}
		for(int j1 = 0;j1 < l;++j1)
		{
			if (answers[index].second)
			{
				for (int y = 0; y < SIZE; ++y)
				{
					for (int x = j1; x < SIZE + 1 - l+j1; ++x)
					{
						if(grids[x][y].letter == str.charAt(j1)&&grids[x][y].across == false)
						{
							if(put(index,x-j1,y))
							{
								//System.out.println("put "+str+" "+x+" "+y);
								for(int i = 1;i < totalSize;++i)
								{
									if(answers[i].used==false)
									{
										bt(i);
									}
								}
								remove(index,x-j1,y);
							}
						}
					}
				}
			}else
			{
				for (int x = 0; x < Server.SIZE; ++x)
				{
					for (int y = j1; y < SIZE + 1 - l+j1; ++y)
					{
						if(grids[x][y].letter == str.charAt(j1)&&grids[x][y].down == false)
						{
							if(put(index,x,y-j1))
							{
								//System.out.println("put "+str+" "+x+" "+y);
								for(int i = 1;i < totalSize;++i)
								{
									if(answers[i].used==false)
									{
										bt(i);
									}
								}
								remove(index,x,y-j1);
							}
						}
					}
				}
			}
		}
		return;
	}
	
	void backtrack(int index) throws Exception
	{	
		String str = answers[index].first;
		int length = str.length();
//		System.out.println(str);
		if (answers[index].second)
		{
			for (int j = 0; j < SIZE; ++j)
			{
				for (int i = 0; i < SIZE + 1 - length; ++i)
				{
					if (put(index, i, j))
					{
						for (int k = 0; k < totalSize; ++k)
						{
							if (answers[k].used == false)
							{
								String s = answers[k].first;
								String onBoard = "";
								boolean flag = false;
								for(int a = 0;a <totalSize;++a)
								{
									if(answers[a].used == true)
									{
										onBoard += answers[a].first;
									}
								}
								for(int b = 0;b<s.length();++b)
								{
									for(int a = 0;a < onBoard.length();++a)
									{
										if(s.charAt(b)==onBoard.charAt(a))
										{
											flag = true;
											break;
										}
									}
									if(flag)
									{
										backtrack(k);
										break;
									}
								}
							}
						}
						remove(index, i, j);
					}
					
				}
			}
		} else
		{
			for (int j = 0; j < SIZE + 1 - length; ++j)
			{
				for (int i = 0; i < SIZE; ++i)
				{
					
					if (put(index, i, j))
					{
						for (int k = 0; k < totalSize; ++k)
						{
							if (answers[k].used == false)
							{
								String s = answers[k].first;
								String onBoard = "";
								boolean flag = false;
								for(int a = 0;a <totalSize;++a)
								{
									
									if(answers[a].used == true)
									{
										onBoard += answers[a].first;
									}
								}
								for(int b = 0;b<s.length();++b)
								{
									for(int a = 0;a < onBoard.length();++a)
									{
										if(s.charAt(b)==onBoard.charAt(a))
										{
											flag = true;
											break;
										}
									}
									if(flag)
									{
										backtrack(k);
										break;
									}
								}
							}
						}
						remove(index, i, j);
					}
					
				}
			}
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
	
	public Answer(String first, boolean second)
	{
		this.first = first;
		this.second = second;
		this.used = false;
	}
}
