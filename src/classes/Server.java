package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javafx.util.Pair;

public class Server
{
	public static Board board;
	public static int acrossSize;
	public static int downSize;
	public static ArrayList<Integer> acrossNumber;
	public static ArrayList<Integer> downNumber;
	public static ArrayList<String> acrossAnswer;
	public static ArrayList<String> downAnswer;
	public static ArrayList<String> acrossQuestion;
	public static ArrayList<String> downQuestion;
	public static ArrayList<Pair<String, Boolean>> answers;
	
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
	
	public static void main(String[] args)
	{
		Server.acrossNumber = new ArrayList<Integer>();
		Server.downNumber = new ArrayList<Integer>();
		Server.acrossAnswer = new ArrayList<String>();
		Server.downAnswer = new ArrayList<String>();
		Server.acrossQuestion = new ArrayList<String>();
		Server.downQuestion = new ArrayList<String>();
		Server.answers = new ArrayList<Pair<String, Boolean>>();
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
		int i = 0;
		int j = 0;
		while (!((i == acrossSize) && (j == downSize)))
		{
			if (i == acrossSize)
			{
				answers.add(new Pair<String,Boolean>(downAnswer.get(j), false));
				++j;
			} else if (j == downSize)
			{
				answers.add(new Pair<String,Boolean>(acrossAnswer.get(i), true));
				++i;
			} else if (acrossAnswer.get(i).length() >= downAnswer.get(j).length())
			{
				answers.add(new Pair<String,Boolean>(acrossAnswer.get(i), true));
				++i;
			} else
			{
				answers.add(new Pair<String,Boolean>(downAnswer.get(j), false));
				++j;
			}
		}
		for (i = 0; i < answers.size(); ++i)
		{
			System.out.println(answers.get(i));
		}
		Server.board = new Board();
		try
		{
			backtrack(0);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public static void backtrack(int index) throws Exception
	{
		if (index == answers.size())
		{
			if(Server.board.check())
			{
				for(int i = 0;i < Board.SIZE;++i)
				{
					for(int j = 0;j < Board.SIZE;++j)
					{
						if(Board.grids[i][j].letter==0)
						{
							System.out.print(' ');
						}
						else
						{
							System.out.print(Board.grids[i][j].letter);
						}
					}
					System.out.println();
				}
				throw new Exception("Founded!");
			}
			return;
		}
		if (answers.get(index).getValue())
		{
			for (int j = 0; j < Board.SIZE ; ++j)
			{
				for (int i = 0; i < Board.SIZE + 1 - (answers.get(index).getKey().length()); ++i)
				{
					if(Server.board.put(index, i, j))
					{
						for(int x = 0;x < Board.SIZE;++x)
						{
							for(int y = 0;y < Board.SIZE;++y)
							{
								if(Board.grids[y][x].letter==0)
								{
									System.out.print(' ');
								}
								else
								{
									System.out.print(Board.grids[y][x].letter);
								}
							}
							System.out.println();
						}
//						System.out.println(answers.get(index).getKey()+" "+i+" "+j);
						backtrack(index+1);
						Server.board.remove(index, i, j);
					}
				}
			}
		}
		else
		{
			for (int j = 0; j < Board.SIZE + 1 - (answers.get(index).getKey().length()); ++j)
			{
				for (int i = 0; i < Board.SIZE; ++i)
				{
					if(Server.board.put(index, i, j))
					{
						for(int x = 0;x < Board.SIZE;++x)
						{
							for(int y = 0;y < Board.SIZE;++y)
							{
								if(Board.grids[y][x].letter==0)
								{
									System.out.print(' ');
								}
								else
								{
									System.out.print(Board.grids[y][x].letter);
								}
							}
							System.out.println();
						}
//						System.out.println(answers.get(index).getKey()+" "+i+" "+j);
						backtrack(index+1);
						Server.board.remove(index, i, j);
					}
				}
			}
		}
	}
}