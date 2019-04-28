package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ServerThread extends Thread
{
	private Lock lock;
	private Condition condition;
	private PrintWriter pw;
	protected BufferedReader br;
	private ChatRoom cr;
	// private boolean isFirst;
	protected int index;
	
	public ServerThread(Socket s, ChatRoom cr, Lock lock, Condition condition, int index)
	{
		try
		{
			this.index = index;
			this.cr = cr;
			this.lock = lock;
			this.condition = condition;
			pw = new PrintWriter(s.getOutputStream());
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.start();
		}
		catch (IOException ioe)
		{
			System.out.println("ioe in ServerThread constructor: " + ioe.getMessage());
		}
	}
	
	public void sendMessage(String message)
	{
		pw.println(message);
		pw.flush();
	}
	
	public void sendBoard()
	{
		for (int j = Builder.lowerbound; j <= Builder.upperbound; ++j)
		{
			String row = "";
			for (int i = Builder.righterbound; i <= Builder.lefterbound; ++i)
			{
				if (Builder.grids[i][j].letter == 0)
				{
					row += "  ";
				}
				else
				{
					if (Builder.grids[i][j].index1 != -1)
					{
						row += Builder.answers[Builder.grids[i][j].index1].num;
					}
					else
					{
						row += " ";
					}
					if(Builder.grids[i][j].answered == true)
					{
						row+= Character.toString(Builder.grids[i][j].letter);
					}
					else
					{
						row+="_";
					}
				}
			}
			pw.println(row);
			pw.flush();
			pw.println();
			pw.flush();
		}
		pw.println("Across");
		pw.flush();
		for(int i = 0;i<Builder.acrossSize;++i)
		{
			String line = "";
			line+=Builder.acrossNumber.get(i);
			line+=" ";
			line+=Builder.acrossQuestion.get(i);
			pw.println(line);
			pw.flush();
		}
		pw.println("Down");
		pw.flush();
		for(int i = 0;i<Builder.downSize;++i)
		{
			String line = "";
			line+=Builder.downNumber.get(i);
			line+=" ";
			line+=Builder.downQuestion.get(i);
			pw.println(line);
			pw.flush();
		}
	}
	
	public void run()
	{
		try
		{
			lock.lock();
			if (index == 1)
			{
				sendMessage("There is a game waiting for you.");
				sendMessage("Player 1 has already joined.");
				if (ChatRoom.numOfTotalPlayers == 3)
				{
					cr.broadcast("wait for 3", this);
				}
			}
			else if(index == 2)
			{
				sendMessage("There is a game waiting for you.");
				sendMessage("Player 1 has already joined.");
				sendMessage("Player 2 has already joined.");
			}
			condition.await();
			String line;
			while (true)
			{
				sendBoard();
				boolean validOption1 = false;
				boolean validOption2 = false;
				boolean chooseAcross;
				while(validOption1 == false)
				{
					sendMessage("Would you like to answer a question across (a) or down (d)? ");
					line = br.readLine();
					if(line!=null)
					{
						line = line.trim();
						line = line.toLowerCase();
					}
					else
					{
						sendMessage("That is not a valid option.");
						continue;
					}
					if(line.equals("a"))
					{
						boolean flag = false;
						for(int i = 0;i < Builder.totalSize;++i)
						{
							if(Builder.answers[i].second==true&&Builder.answers[i].answered==false)
							{
								flag = true;
								break;
							}
						}
						if(flag==false)
						{
							sendMessage("That is not a valid option.");
							continue;
						}
						else
						{
							validOption1 = true;
							chooseAcross = true;
						}
					}
					else if(line.equals("b"))
					{
						boolean flag = false;
						for(int i = 0;i < Builder.totalSize;++i)
						{
							if(Builder.answers[i].second==false&&Builder.answers[i].answered==false)
							{
								flag = true;
								break;
							}
						}
						if(flag==false)
						{
							sendMessage("That is not a valid option.");
							continue;
						}
						else
						{
							validOption1 = true;
							chooseAcross = false;
						}
					}
					else
					{
						sendMessage("That is not a valid option.");
					}
				}
				while(validOption2 == false)
				{
					sendMessage("Which number? ");
				}
//				if (line.indexOf("END_OF_MESSAGE") != -1)
//				{
//					cr.singalCLient(lock);
//					sendMessage("You will need to wait until your turn to send again.");
//					
//					condition.await();
//					sendMessage("It is your turn to send message.");
//					line = br.readLine();
//				}
//				cr.broadcast(line, this);
//				line = br.readLine();
			}
		}
		catch (InterruptedException ie)
		{
			
		}
		catch (IOException ioe)
		{
			System.out.println("ioe in ServerThread.run(): " + ioe.getMessage());
		}
		finally
		{
//			lock.unlock();
		}
	}
}