package classes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChatRoom
{
	private static int numOfExistingPlayers;
	protected static int numOfTotalPlayers;
	//private static boolean isFirst;
	private Vector<Lock> locks;
	private Vector<Condition> conditions;
	private Vector<ServerThread> serverThreads;
	private Builder builder;
	public ChatRoom(int port)
	{
		try
		{
			numOfTotalPlayers = 0;
			numOfExistingPlayers = 0;
			builder = new Builder();
			System.out.println("Listening on port " + port + ".");
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Waiting for players...");
			serverThreads = new Vector<ServerThread>();
			locks = new Vector<Lock>();
			conditions = new Vector<Condition>();
//			isFirst = true;
//			while (true)
//			{
				Socket s = ss.accept(); // blocking
				++numOfExistingPlayers;
				System.out.println("Connection from: " + s.getInetAddress());
				Lock lock = new ReentrantLock();
				locks.add(lock);
				Condition condition = lock.newCondition();
				conditions.add(condition);
				ServerThread st = new ServerThread(s, this, lock, condition, 0);
				serverThreads.add(st);
				st.sendMessage("How many players will there be? ");
				String num = st.br.readLine();
				numOfTotalPlayers= Integer.parseInt(num);
				System.out.println("Number of players: "+numOfTotalPlayers+".");
				if(numOfTotalPlayers>1)
				{
					System.out.println("Waiting for player 2.");
					serverThreads.get(0).sendMessage("Waiting for player 2.");
				}
				System.out.println("Reading random game file.");
				boolean validBoard;
				validBoard = builder.createBoard();
				while(validBoard==false)
				{
					validBoard = builder.createBoard();
				}
				//this.broadcast(num, st);
			//}
				if(numOfTotalPlayers>1)
				{
					while(true)
					{
						Socket s1 = ss.accept(); // blocking
						++numOfExistingPlayers;
						System.out.println("Connection from: " + s1.getInetAddress());
						Lock lock1 = new ReentrantLock();
						locks.add(lock1);
						Condition condition1 = lock1.newCondition();
						conditions.add(condition1);
						ServerThread st1 = new ServerThread(s1, this, lock1, condition1,numOfExistingPlayers-1 );
						serverThreads.add(st1);
						if(numOfExistingPlayers==numOfTotalPlayers)
						{
							break;
						}
					}
				}
				System.out.println("Game can now begin.");
				for (ServerThread threads : serverThreads)
				{
					threads.sendMessage("The game is beginning.");
				}
				System.out.println("Sending game board.");
				boolean endGame = false;
//				while(endGame == false)
//				{
					for (ServerThread threads : serverThreads)
					{
						threads.sendBoard();
					}
				//}
				
		} catch (IOException ioe)
		{
			System.out.println("ioe in ChatRoom constructor: " + ioe.getMessage());
		}
	}
	
	public void broadcast(String message, ServerThread st)
	{
		if(message == "wait for 3")
		{
			System.out.println("Waiting for player 3.");
			serverThreads.get(0).sendMessage("Waiting for player 3.");
			serverThreads.get(1).sendMessage("Waiting for player 3.");
		}
		else if (message != null)
		{
			System.out.println(message);
			for (ServerThread threads : serverThreads)
			{
				if (st != threads)
				{
					threads.sendMessage(message);
				}
			}
		}
	}
	
	public void singalCLient(Lock lock)
	{
		Condition nextCondition = null;
		Lock nextLock = null;
		for (int i = 0; i < locks.size(); ++i)
		{
			if (locks.get(i) == lock)
			{
				if (i == locks.size() - 1)
				{
					nextLock = locks.get(0);
					nextLock.lock();
					nextCondition = conditions.get(0);
					nextCondition.signal();
					nextLock.unlock();
					break;
				} else
				{
					nextLock = locks.get(i + 1);
					nextLock.lock();
					nextCondition = conditions.get(i + 1);
					nextCondition.signal();
					nextLock.unlock();
					break;
				}
			}
		}
		
	}
	
	public static void main(String[] args)
	{
		ChatRoom cr = new ChatRoom(3456);
	}
}