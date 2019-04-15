package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;

import javafx.util.Pair;

public class Server
{
	public static Grid[][] grids;
	public static final int SIZE = 15;
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
	
//	public Server(int acrossSize, int downSize, ArrayList<Integer> acrossNumber, ArrayList<Integer> downNumber,
//			ArrayList<String> acrossAnswer, ArrayList<String> downAnswer, ArrayList<String> acrossQuestion,
//			ArrayList<String> downQuestion)
//	{
////		this.acrossSize = acrossSize;
////		this.downSize = downSize;
////		this.acrossNumber = acrossNumber;
////		this.downNumber = downNumber;
////		this.acrossAnswer = acrossAnswer;
////		this.downAnswer = downAnswer;
////		this.acrossQuestion = acrossQuestion;
////		this.downQuestion = downQuestion;
//	}
	public boolean check()
	{
		for (int i = 0; i < SIZE; ++i)
		{
			for (int j = 0; j < SIZE; ++j)
			{
				Grid g = grids[i][j];
				int size = 0;
				if (g.index1 > 0)
				{
					++size;
				}
				if (g.index2 > 0)
				{
					++size;
				}
				if (size == 1)
				{
					int index = Math.max(g.index1, g.index2);
					boolean across = Server.answers[index].second;
					int length = Server.answers[index].first.length();
					boolean joint = false;
					if (across)
					{
						for (int k = 0; k < length; ++k)
						{
							if (grids[i + k][j].occurr == 2)
							{
								joint = true;
								break;
							}
						}
						if (joint == false)
						{
							return false;
						}
					} else
					{
						
						for (int k = 0; k < length; ++k)
						{
							if (grids[i][j + k].occurr == 2)
							{
								joint = true;
								break;
							}
						}
						if (joint == false)
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
			if (index == totalSize - 1 && joint == false)
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
//					for(int a = 0;a < SIZE;++a)
//					{
//						for(int b = 0;b < SIZE;++b)
//						{
//							if(grids[b][a].letter==0)
//							{
//								System.out.print(' ');
//							}
//							else
//							{
//								System.out.print(grids[b][a].letter);
//							}
//						}
//						System.out.println();
//					}
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
			if (index == totalSize - 1 && joint == false)
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
//					for(int a = 0;a < SIZE;++a)
//					{
//						for(int b = 0;b < SIZE;++b)
//						{
//							if(grids[b][a].letter==0)
//							{
//								System.out.print(' ');
//							}
//							else
//							{
//								System.out.print(grids[b][a].letter);
//							}
//						}
//						System.out.println();
//					}
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
		return true;
		
	}
	
	public void remove(int index, int x, int y)
	{
		String str = Server.answers[index].first;
		int length = str.length();
		if (answers[index].second)
		{
			for (int i = 0; i < length; ++i)
			{
				Grid g = grids[x + i][y];
				g.across = false;
				if (g.occurr != 2)
				{
					g.letter = 0;
				}
				g.occurr -= 1;
			}
		} else
		{
			for (int i = 0; i < length; ++i)
			{
				Grid g = grids[x][y + i];
				g.down = false;
				if (g.occurr != 2)
				{
					g.letter = 0;
				}
				g.occurr -= 1;
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
		for (int i = 0; i < SIZE; ++i)
		{
			for (int j = 0; j < SIZE; ++j)
			{
				grids[i][j] = new Grid();
			}
		}
		Server.acrossNumber = new ArrayList<Integer>();
		Server.downNumber = new ArrayList<Integer>();
		Server.acrossAnswer = new ArrayList<String>();
		Server.downAnswer = new ArrayList<String>();
		Server.acrossQuestion = new ArrayList<String>();
		Server.downQuestion = new ArrayList<String>();
		
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
		Server.totalSize = acrossSize+downSize;
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
			server.backtrack(0);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		long after = System.currentTimeMillis();
		System.out.println("time used: " + (after - before));
	}
	
	void backtrack(int index) throws Exception
	{
		if (index == totalSize)
		{
			if (check())
			{
				for (int i = 0; i < SIZE; ++i)
				{
					for (int j = 0; j < SIZE; ++j)
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
			return;
		}
		int length = answers[index].first.length();
		if (answers[index].second)
		{
			for (int j = 0; j < SIZE; ++j)
			{
				for (int i = 0; i < SIZE + 1 - length; ++i)
				{
					if (put(index, i, j))
					{
//						for(int x = 0;x < SIZE;++x)
//						{
//							for(int y = 0;y < SIZE;++y)
//							{
//								if(grids[y][x].letter==0)
//								{
//									System.out.print(' ');
//								}
//								else
//								{
//									System.out.print(grids[y][x].letter);
//								}
//							}
//							System.out.println();
//						}
//						System.out.println(answers.get(index).getKey()+" "+i+" "+j);
						backtrack(index + 1);
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
//						for(int x = 0;x < SIZE;++x)
//						{
//							for(int y = 0;y < SIZE;++y)
//							{
//								if(grids[y][x].letter==0)
//								{
//									System.out.print(' ');
//								}
//								else
//								{
//									System.out.print(grids[y][x].letter);
//								}
//							}
//							System.out.println();
//						}
//						System.out.println(answers.get(index).getKey()+" "+i+" "+j);
						backtrack(index + 1);
						remove(index, i, j);
					}
				}
			}
		}
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

class Answer
{
	String first;
	boolean second;
	
	public Answer(String first, boolean second)
	{
		this.first = first;
		this.second = second;
	}
}
