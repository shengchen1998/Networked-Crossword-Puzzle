package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String s = null;
		try
		{
			s = reader.readLine();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s = s.toLowerCase();
		if ((!s.equals("across")) && (!s.equals("down")))
		{
			return;
		}
		if (s.equals("across"))
		{
			s = reader.readLine();
			s = s.toLowerCase();
			while (s != "down")
			{
				ArrayList<String> tokens = new ArrayList<String>();
				StringTokenizer tokenizer = new StringTokenizer(s, "|");
				String num = tokenizer.nextToken();
				int n;
				try
				{
					n = Integer.parseInt(num);
					acrossNumber.add(n);
				} catch (Exception e)
				{
					System.out.println("Malformatted");
				}
			}
		}
		int line = 1;
//			while ((tempString = reader.readLine()) != null)
//			{
//				System.out.println("line " + line + ": " + tempString);
//				line++;
//			}
		try
		{
			reader.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}