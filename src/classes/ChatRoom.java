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
	// private static boolean isFirst;
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
			numOfExistingPlayers=1;
			System.out.println("Connection from: " + s.getInetAddress());
			Lock lock = new ReentrantLock();
			locks.add(lock);
			Condition condition = lock.newCondition();
			conditions.add(condition);
			ServerThread st = new ServerThread(s, this, lock, condition, 0);
			serverThreads.add(st);
			st.sendMessage("How many players will there be? ");
			String num = st.br.readLine();
			numOfTotalPlayers = Integer.parseInt(num);
			System.out.println("Number of players: " + numOfTotalPlayers + ".");
			if (numOfTotalPlayers > 1)
			{
				System.out.println("Waiting for player 2.");
				serverThreads.get(0).sendMessage("Waiting for player 2.");
			}
			System.out.println("Reading random game file.");
			boolean validBoard;
			validBoard = builder.createBoard();
			while (validBoard == false)
			{
				validBoard = builder.createBoard();
			}
			// this.broadcast(num, st);
			// }
			if (numOfTotalPlayers == 2)
			{
//				while (true)
//				{
					Socket s1 = ss.accept(); // blocking
					numOfExistingPlayers=2;
					System.out.println("Connection from: " + s1.getInetAddress());
					Lock lock1 = new ReentrantLock();
					locks.add(lock1);
					Condition condition1 = lock1.newCondition();
					conditions.add(condition1);
					ServerThread st1 = new ServerThread(s1, this, lock1, condition1, 1);
					serverThreads.add(st1);
//					if (numOfExistingPlayers == numOfTotalPlayers)
//					{
//						break;
//					}
//				}
			}
			else if(numOfTotalPlayers == 3)
			{
				Socket s1 = ss.accept(); // blocking
				numOfExistingPlayers=2;
				System.out.println("Connection from: " + s1.getInetAddress());
				Lock lock1 = new ReentrantLock();
				locks.add(lock1);
				Condition condition1 = lock1.newCondition();
				conditions.add(condition1);
				ServerThread st1 = new ServerThread(s1, this, lock1, condition1, 1);
				serverThreads.add(st1);
				Socket s2 = ss.accept(); // blocking
				numOfExistingPlayers=2;
				System.out.println("Connection from: " + s2.getInetAddress());
				Lock lock2 = new ReentrantLock();
				locks.add(lock2);
				Condition condition2 = lock2.newCondition();
				conditions.add(condition2);
				ServerThread st2 = new ServerThread(s2, this, lock2, condition2, 2);
				serverThreads.add(st2);
			}
			System.out.println("Game can now begin.");
			for (ServerThread threads : serverThreads)
			{
				threads.sendMessage("The game is beginning.");
			}
			System.out.println("Sending game board.");
			
//				boolean endGame = false;
//				int index = 0;
//				while(endGame == false)
//				{
			for (ServerThread thread : serverThreads)
			{
				if(thread.index!=0)
				{
					thread.sendBoard();
				}
			}
			locks.get(0).lock();
			conditions.get(0).signal();
			locks.get(0).unlock();
			System.out.println("Player 1��s turn.");
			for (ServerThread thread : serverThreads)
			{
				if(thread.index!=0)
				{
					thread.sendMessage("Player 1��s turn.");
				}
			}
			
//				}
			
		}
		catch (IOException ioe)
		{
			System.out.println("ioe in ChatRoom constructor: " + ioe.getMessage());
		}
	}
	
	public void broadcast(String message, ServerThread st)
	{
		if (message == "wait for 3")
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
				}
				else
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