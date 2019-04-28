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
	private int index;
	
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
			String line = br.readLine();
			while (true)
			{
				if (line.indexOf("END_OF_MESSAGE") != -1)
				{
					cr.singalCLient(lock);
					sendMessage("You will need to wait until your turn to send again.");
					
					condition.await();
					sendMessage("It is your turn to send message.");
					line = br.readLine();
				}
				cr.broadcast(line, this);
				line = br.readLine();
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