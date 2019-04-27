package classes;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient extends Thread {
	private BufferedReader br;
	private PrintWriter pw;
	private DataOutputStream toServer;
	public ChatClient(String hostname, int port) {
		try {
			//System.out.println("Trying to connect to " + hostname + ":" + port);
			Socket s = new Socket(hostname, port);
			//System.out.println("Connected to " + hostname + ":" + port);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream());
			toServer = new DataOutputStream(s.getOutputStream());
			this.start();
			Scanner sc = new Scanner(System.in);
			while(true) {
				String line = sc.nextLine();
				pw.println("Cote: " + line);
				pw.flush();
			}
			
		} catch (IOException ioe) {
			System.out.println("ioe in ChatClient constructor: " + ioe.getMessage());
		}
	}
	public void run() {
		try {
			while(true) {
				String line = br.readLine();
				if(line.equals("How many players will there be?"))
				{
					Scanner sc = new Scanner(System.in);
					System.out.print("How many players will there be? ");
					String l = sc.nextLine();
					sc.close();
					pw.println(l);
					pw.flush();
				}
				else
				{
					System.out.println(line);
				}
					
			}
		} catch (IOException ioe) {
			System.out.println("ioe in ChatClient.run(): " + ioe.getMessage());
		}
	}
	public static void main(String [] args) {
		System.out.println("Welcome to 201 Crossword!");
		System.out.print("Enter the server hostname: ");
		Scanner scan = new Scanner(System.in);
		String hostname = scan.nextLine();
		System.out.print("Enter the server port: ");
		int port = scan.nextInt();
		ChatClient cc = new ChatClient(hostname, port);
		scan.close();
	}
}