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
	private static int numOfTotalPlayers;
	//private static boolean isFirst;
	private Vector<Lock> locks;
	private Vector<Condition> conditions;
	private Vector<ServerThread> serverThreads;
	
	public ChatRoom(int port)
	{
		try
		{
			numOfExistingPlayers = 0;
			Builder builder = new Builder();
			// builder.createBoard();
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
			//}
		} catch (IOException ioe)
		{
			System.out.println("ioe in ChatRoom constructor: " + ioe.getMessage());
		}
	}
	
	public void broadcast(String message, ServerThread st)
	{
		if(numOfExistingPlayers==1)
		{
			//isFirst = false;
			numOfTotalPlayers= Integer.parseInt(message);
			System.out.println("Number of players: "+numOfTotalPlayers+".");
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