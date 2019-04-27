package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ServerThread extends Thread {
	private Lock lock;
	private Condition condition;
	private PrintWriter pw;
	private BufferedReader br;
	private ChatRoom cr;
	//private boolean isFirst;
	private int index;
	public ServerThread(Socket s, ChatRoom cr, Lock lock, Condition condition,int index) {
		try {
			this.index = index;
			this.cr = cr;
			this.lock = lock;
			this.condition = condition;
			pw = new PrintWriter(s.getOutputStream());
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread constructor: " + ioe.getMessage());
		}
	}

	public void sendMessage(String message) {
		pw.println(message);
		pw.flush();
	}
	
	public void run() {
		try {
				lock.lock();
				if(index!=0)
				{
					condition.await();
				}
				else
				{
					sendMessage("How many players will there be? ");
				}
				String line = br.readLine();
				while(true)
				{
					if(line.indexOf("END_OF_MESSAGE")!=-1)
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
		} catch(InterruptedException ie)
		{
			
		}
		catch (IOException ioe) {
			System.out.println("ioe in ServerThread.run(): " + ioe.getMessage());
		}
		finally
		{
//			lock.unlock();
		}
	}
}