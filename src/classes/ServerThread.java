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
	private static boolean gameEnd;
	public int score;
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
			gameEnd = false;
			score = 0;
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
					row += "   ";
				}
				else
				{
					if (Builder.grids[i][j].index1 != -1)
					{
						if (Builder.answers[Builder.grids[i][j].index1].num >= 10)
						{
							row += Builder.answers[Builder.grids[i][j].index1].num;
						}
						else
						{
							row += " ";
							row += Builder.answers[Builder.grids[i][j].index1].num;
						}
						
					}
					else
					{
						row += "  ";
					}
					if (Builder.grids[i][j].answered == true)
					{
						row += Character.toString(Builder.grids[i][j].letter);
					}
					else
					{
						row += "_";
					}
				}
			}
			pw.println(row);
			pw.flush();
			pw.println();
			pw.flush();
		}
		boolean acrossFlag = false;
		boolean downFlag = false;
		for (int i = 0; i < Builder.totalSize; ++i)
		{
			if (Builder.answers[i].second == true && Builder.answers[i].answered == false)
			{
				acrossFlag = true;
				break;
			}
		}
		for (int i = 0; i < Builder.totalSize; ++i)
		{
			if (Builder.answers[i].second == false && Builder.answers[i].answered == false)
			{
				downFlag = true;
				break;
			}
		}
		if (acrossFlag == false && downFlag == false)
		{
			gameEnd = true;
			pw.println("Across");
			pw.flush();
			for (int i = 0; i < Builder.acrossSize; ++i)
			{
				String line = "";
				line += Builder.acrossNumber.get(i);
				line += " ";
				line += Builder.acrossQuestion.get(i);
				pw.println(line);
				pw.flush();
			}
			pw.println("Down");
			pw.flush();
			for (int i = 0; i < Builder.downSize; ++i)
			{
				String line = "";
				line += Builder.downNumber.get(i);
				line += " ";
				line += Builder.downQuestion.get(i);
				pw.println(line);
				pw.flush();
			}
		}
		else
		{
			if (acrossFlag == true)
			{
				pw.println("Across");
				pw.flush();
				for (int i = 0; i < Builder.acrossSize; ++i)
				{
					for (int j = 0; j < Builder.totalSize; ++j)
					{
						if (Builder.answers[j].num == Builder.acrossNumber.get(i) && Builder.answers[j].second == true
								&& Builder.answers[j].answered == false)
						{
							String line = "";
							line += Builder.acrossNumber.get(i);
							line += " ";
							line += Builder.acrossQuestion.get(i);
							pw.println(line);
							pw.flush();
						}
					}
				}
			}
			if (downFlag == true)
			{
				pw.println("Down");
				pw.flush();
				for (int i = 0; i < Builder.downSize; ++i)
				{
					for (int j = 0; j < Builder.totalSize; ++j)
					{
						if (Builder.answers[j].num == Builder.downNumber.get(i) && Builder.answers[j].second == false
								&& Builder.answers[j].answered == false)
						{
							String line = "";
							line += Builder.downNumber.get(i);
							line += " ";
							line += Builder.downQuestion.get(i);
							pw.println(line);
							pw.flush();
						}
					}
				}
			}
			
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
			else if (index == 2)
			{
				sendMessage("There is a game waiting for you.");
				sendMessage("Player 1 has already joined.");
				sendMessage("Player 2 has already joined.");
			}
			condition.await();
			String line;
			while (true)
			{
				cr.sendBoard();
				if(gameEnd==true)
				{
					cr.sendFinalScore(this);
					break;
				}
				cr.broadcast("Player " + (index+1) + "'s turn.", this);
				boolean validOption1 = false;
				boolean validOption2 = false;
				boolean chooseAcross = true;
				int number = 0;
				while (validOption1 == false)
				{
					sendMessage("Would you like to answer a question across (a) or down (d)? ");
					line = br.readLine();
					if (line != null)
					{
						line = line.trim();
						line = line.toLowerCase();
					}
					else
					{
						sendMessage("That is not a valid option.");
						continue;
					}
					if (line.equals("a"))
					{
						boolean flag = false;
						for (int i = 0; i < Builder.totalSize; ++i)
						{
							if (Builder.answers[i].second == true && Builder.answers[i].answered == false)
							{
								flag = true;
								break;
							}
						}
						if (flag == false)
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
					else if (line.equals("d"))
					{
						boolean flag = false;
						for (int i = 0; i < Builder.totalSize; ++i)
						{
							if (Builder.answers[i].second == false && Builder.answers[i].answered == false)
							{
								flag = true;
								break;
							}
						}
						if (flag == false)
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
				while (validOption2 == false)
				{
					sendMessage("Which number? ");
					line = br.readLine();
					if (line != null)
					{
						line = line.trim();
					}
					else
					{
						sendMessage("That is not a valid option.");
						continue;
					}
					try
					{
						number = Integer.parseInt(line);
					}
					catch (Exception e)
					{
						sendMessage("That is not a valid option.");
						continue;
					}
					if (chooseAcross == true)
					{
						boolean flag = false;
						for (int i = 0; i < Builder.totalSize; ++i)
						{
							if (Builder.answers[i].second == true && Builder.answers[i].num == number
									&& Builder.answers[i].answered == false)
							{
								flag = true;
								break;
							}
						}
						if (flag == false)
						{
							sendMessage("That is not a valid option.");
							continue;
						}
						else
						{
							validOption2 = true;
						}
					}
					else
					{
						boolean flag = false;
						for (int i = 0; i < Builder.totalSize; ++i)
						{
							if (Builder.answers[i].second == false && Builder.answers[i].num == number
									&& Builder.answers[i].answered == false)
							{
								flag = true;
								break;
							}
						}
						if (flag == false)
						{
							sendMessage("That is not a valid option.");
							continue;
						}
						else
						{
							validOption2 = true;
						}
					}
				}
				if (chooseAcross == true)
				{
					sendMessage("What is your guess for " + number + " across?");
				}
				else
				{
					sendMessage("What is your guess for " + number + " down?");
				}
				
				line = br.readLine();
//				if(line!=null)
//				{
				line = line.trim();
				line = line.toLowerCase();
				if (chooseAcross == true)
				{
					cr.broadcast("Player " + (index + 1) + " guessed \"" + line + "\" for " + number + " across.",
							this);
				}
				else
				{
					cr.broadcast("Player " + (index + 1) + " guessed \"" + line + "\" for " + number + " down.", this);
				}
				
//				}
//				else
//				{
//					sendMessage("That is not a valid input.");
//				}
				if (chooseAcross == true)
				{
					for (int i = 0; i < Builder.acrossSize; ++i)
					{
						if (Builder.acrossNumber.get(i) == number)
						{
							if (Builder.acrossAnswer.get(i).equals(line))
							{
								++score;
								cr.broadcast("That is correct.", this);
								sendMessage("That is correct.");
								for (int j = 0; j < Builder.totalSize; ++j)
								{
									if (Builder.answers[j].second == true && Builder.answers[j].num == number)
									{
										Builder.answers[j].answered = true;
										for (int k = Builder.answers[j].x; k < (Builder.answers[j].x
												+ Builder.answers[j].length); ++k)
										{
											Builder.grids[k][Builder.answers[j].y].answered = true;
										}
									}
								}
								break;
							}
							else
							{
								cr.broadcast("That is incorrect.", this);
								sendMessage("That is incorrect.");
								if (ChatRoom.numOfTotalPlayers != 1)
								{
									cr.signalCLient(lock);
									condition.await();
								}
								break;
							}
						}
					}
				}
				else
				{
					for (int i = 0; i < Builder.downSize; ++i)
					{
						if (Builder.downNumber.get(i) == number)
						{
							if (Builder.downAnswer.get(i).equals(line))
							{
								++score;
								cr.broadcast("That is correct.", this);
								sendMessage("That is correct.");
								for (int j = 0; j < Builder.totalSize; ++j)
								{
									if (Builder.answers[j].second == false && Builder.answers[j].num == number)
									{
										Builder.answers[j].answered = true;
										for (int k = Builder.answers[j].y; k < (Builder.answers[j].y
												+ Builder.answers[j].length); ++k)
										{
											Builder.grids[Builder.answers[j].x][k].answered = true;
										}
									}
								}
								break;
							}
							else
							{
								cr.broadcast("That is incorrect.", this);
								sendMessage("That is incorrect.");
								if (ChatRoom.numOfTotalPlayers != 1)
								{
									cr.signalCLient(lock);
									condition.await();
								}
								break;
							}
						}
					}
				}
				// 如果对了 就update answered信息并且计分
				// 如果错了 condition.await(),然后叫下一个人
				
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