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
	int acrossSize;
	int downSize;
	ArrayList<Integer> acrossNumber;
	ArrayList<Integer> downNumber;
	ArrayList<String> acrossAnswer;
	ArrayList<String> downAnswer;
	ArrayList<String> acrossQuestion;
	ArrayList<String> downQuestion;
	ArrayList<Pair<String, Boolean>> answers;
	
	public Server(int acrossSize, int downSize, ArrayList<Integer> acrossNumber, ArrayList<Integer> downNumber,
			ArrayList<String> acrossAnswer, ArrayList<String> downAnswer, ArrayList<String> acrossQuestion,
			ArrayList<String> downQuestion)
	{
		this.acrossSize = acrossSize;
		this.downSize = downSize;
		this.acrossNumber = acrossNumber;
		this.downNumber = downNumber;
		this.acrossAnswer = acrossAnswer;
		this.downAnswer = downAnswer;
		this.acrossQuestion = acrossQuestion;
		this.downQuestion = downQuestion;
	}
	
	public static void main(String[] args)
	{
		ArrayList<Integer> acrossNumber = new ArrayList<Integer>();
		ArrayList<Integer> downNumber = new ArrayList<Integer>();
		ArrayList<String> acrossAnswer = new ArrayList<String>();
		ArrayList<String> downAnswer = new ArrayList<String>();
		ArrayList<String> acrossQuestion = new ArrayList<String>();
		ArrayList<String> downQuestion = new ArrayList<String>();
		ArrayList<Pair<String, Boolean>> answers = new ArrayList<Pair<String, Boolean>>();
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
				ArrayList<String> tokens = new ArrayList<String>();
				StringTokenizer tokenizer = new StringTokenizer(s, "|");
				if (tokenizer.countTokens() != 3)
				{
					System.out.println("Malformatted:not 3 token");
					for (int i = 0; i < tokenizer.countTokens(); ++i)
					{
						System.out.println(tokenizer.nextToken());
					}
					acrossNumber.clear();
					acrossAnswer.clear();
					acrossQuestion.clear();
					downNumber.clear();
					downAnswer.clear();
					downQuestion.clear();
					return;
				}
				String num = tokenizer.nextToken();
				int n;
				try
				{
					n = Integer.parseInt(num);
					acrossNumber.add(n);
					System.out.println(n);
				} catch (Exception e)
				{
					System.out.println("Malformatted:not number");
					acrossNumber.clear();
					acrossAnswer.clear();
					acrossQuestion.clear();
					downNumber.clear();
					downAnswer.clear();
					downQuestion.clear();
					return;
				}
				String answer = tokenizer.nextToken();
				for (int i = 0; i < answer.length(); ++i)
				{
					char c = answer.charAt(i);
					if (c < 'a' || c > 'z')
					{
						System.out.println("Malformatted:not letter");
						acrossNumber.clear();
						acrossAnswer.clear();
						acrossQuestion.clear();
						downNumber.clear();
						downAnswer.clear();
						downQuestion.clear();
						return;
					}
				}
				acrossAnswer.add(answer);
				System.out.println(answer);
				String question = tokenizer.nextToken();
				acrossQuestion.add(question);
				System.out.println(question);
				try
				{
					s = reader.readLine();
					
				} catch (IOException e)
				{
					System.out.println("Malformatted: no down");
					acrossNumber.clear();
					acrossAnswer.clear();
					acrossQuestion.clear();
					downNumber.clear();
					downAnswer.clear();
					downQuestion.clear();
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
				ArrayList<String> tokens = new ArrayList<String>();
				StringTokenizer tokenizer = new StringTokenizer(s, "|");
				if (tokenizer.countTokens() != 3)
				{
					System.out.println("Malformatted:not 3 token");
					for (int i = 0; i < tokenizer.countTokens(); ++i)
					{
						System.out.println(tokenizer.nextToken());
					}
					acrossNumber.clear();
					acrossAnswer.clear();
					acrossQuestion.clear();
					downNumber.clear();
					downAnswer.clear();
					downQuestion.clear();
					return;
				}
				String num = tokenizer.nextToken();
				int n;
				try
				{
					n = Integer.parseInt(num);
					downNumber.add(n);
					System.out.println(n);
				} catch (Exception e)
				{
					System.out.println("Malformatted:not number");
					acrossNumber.clear();
					acrossAnswer.clear();
					acrossQuestion.clear();
					downNumber.clear();
					downAnswer.clear();
					downQuestion.clear();
					return;
				}
				String answer = tokenizer.nextToken();
				for (int i = 0; i < answer.length(); ++i)
				{
					char c = answer.charAt(i);
					if (c < 'a' || c > 'z')
					{
						System.out.println("Malformatted:not letter");
						acrossNumber.clear();
						acrossAnswer.clear();
						acrossQuestion.clear();
						downNumber.clear();
						downAnswer.clear();
						downQuestion.clear();
						return;
					}
				}
				downAnswer.add(answer);
				System.out.println(answer);
				String question = tokenizer.nextToken();
				downQuestion.add(question);
				System.out.println(question);
				try
				{
					s = reader.readLine();
				} catch (IOException e)
				{
					System.out.println("Malformatted: no down");
					acrossNumber.clear();
					acrossAnswer.clear();
					acrossQuestion.clear();
					downNumber.clear();
					downAnswer.clear();
					downQuestion.clear();
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
				ArrayList<String> tokens = new ArrayList<String>();
				StringTokenizer tokenizer = new StringTokenizer(s, "|");
				if (tokenizer.countTokens() != 3)
				{
					System.out.println("Malformatted:not 3 token");
					for (int i = 0; i < tokenizer.countTokens(); ++i)
					{
						System.out.println(tokenizer.nextToken());
					}
					acrossNumber.clear();
					acrossAnswer.clear();
					acrossQuestion.clear();
					downNumber.clear();
					downAnswer.clear();
					downQuestion.clear();
					return;
				}
				String num = tokenizer.nextToken();
				int n;
				try
				{
					n = Integer.parseInt(num);
					downNumber.add(n);
				} catch (Exception e)
				{
					System.out.println("Malformatted:not number");
					acrossNumber.clear();
					acrossAnswer.clear();
					acrossQuestion.clear();
					downNumber.clear();
					downAnswer.clear();
					downQuestion.clear();
					return;
				}
				String answer = tokenizer.nextToken();
				for (int i = 0; i < answer.length(); ++i)
				{
					char c = answer.charAt(i);
					if (c < 'a' || c > 'z')
					{
						System.out.println("Malformatted:not letter");
						acrossNumber.clear();
						acrossAnswer.clear();
						acrossQuestion.clear();
						downNumber.clear();
						downAnswer.clear();
						downQuestion.clear();
						return;
					}
				}
				downAnswer.add(answer);
				String question = tokenizer.nextToken();
				downQuestion.add(question);
				try
				{
					s = reader.readLine();
				} catch (IOException e)
				{
					System.out.println("Malformatted: no down");
					acrossNumber.clear();
					acrossAnswer.clear();
					acrossQuestion.clear();
					downNumber.clear();
					downAnswer.clear();
					downQuestion.clear();
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
				ArrayList<String> tokens = new ArrayList<String>();
				StringTokenizer tokenizer = new StringTokenizer(s, "|");
				if (tokenizer.countTokens() != 3)
				{
					System.out.println("Malformatted:not 3 token");
					for (int i = 0; i < tokenizer.countTokens(); ++i)
					{
						System.out.println(tokenizer.nextToken());
					}
					acrossNumber.clear();
					acrossAnswer.clear();
					acrossQuestion.clear();
					downNumber.clear();
					downAnswer.clear();
					downQuestion.clear();
					return;
				}
				String num = tokenizer.nextToken();
				int n;
				try
				{
					n = Integer.parseInt(num);
					acrossNumber.add(n);
				} catch (Exception e)
				{
					System.out.println("Malformatted:not number");
					acrossNumber.clear();
					acrossAnswer.clear();
					acrossQuestion.clear();
					downNumber.clear();
					downAnswer.clear();
					downQuestion.clear();
					return;
				}
				String answer = tokenizer.nextToken();
				for (int i = 0; i < answer.length(); ++i)
				{
					char c = answer.charAt(i);
					if (c < 'a' || c > 'z')
					{
						System.out.println("Malformatted:not letter");
						acrossNumber.clear();
						acrossAnswer.clear();
						acrossQuestion.clear();
						downNumber.clear();
						downAnswer.clear();
						downQuestion.clear();
						return;
					}
				}
				acrossAnswer.add(answer);
				String question = tokenizer.nextToken();
				acrossQuestion.add(question);
				try
				{
					s = reader.readLine();
				} catch (IOException e)
				{
					System.out.println("Malformatted: no down");
					acrossNumber.clear();
					acrossAnswer.clear();
					acrossQuestion.clear();
					downNumber.clear();
					downAnswer.clear();
					downQuestion.clear();
					return;
				}
			}
		} else
		{
			System.out.println("First line is malformatted.");
			return;
		}
		int acrossSize = acrossNumber.size();
		int downSize = downNumber.size();
		for (int i = 0; i < acrossSize; ++i)
		{
			System.out.println(acrossNumber.get(i) + "|" + acrossAnswer.get(i) + "|" + acrossQuestion.get(i));
		}
		for (int i = 0; i < downSize; ++i)
		{
			System.out.println(downNumber.get(i) + "|" + downAnswer.get(i) + "|" + downQuestion.get(i));
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
			acrossNumber.set(i,acrossNumber.get(max));
			acrossNumber.set(max,t);
			String temp = acrossAnswer.get(i);
			acrossAnswer.set(i,acrossAnswer.get(max));
			acrossAnswer.set(max,temp);
			temp = acrossQuestion.get(i);
			acrossQuestion.set(i,acrossQuestion.get(max));
			acrossQuestion.set(max,temp);
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
			downNumber.set(i,downNumber.get(max));
			downNumber.set(max,t);
			String temp = downAnswer.get(i);
			downAnswer.set(i,downAnswer.get(max));
			downAnswer.set(max,temp);
			temp = downQuestion.get(i);
			downQuestion.set(i,downQuestion.get(max));
			downQuestion.set(max,temp);
		}
		for (int i = 0; i < acrossSize; ++i)
		{
			System.out.println(acrossNumber.get(i) + "|" + acrossAnswer.get(i) + "|" + acrossQuestion.get(i));
		}
		for (int i = 0; i < downSize; ++i)
		{
			System.out.println(downNumber.get(i) + "|" + downAnswer.get(i) + "|" + downQuestion.get(i));
		}
//		for (int i = 0; i < acrossSize;)
//		{
//			for (int j = 0; j < downSize;)
//			{
//				
//			}
//		}
	}
}