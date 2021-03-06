/**
 *
 * CT414 Distributed Systems & Co-Operative Computing
 * Assignment 1: Distributed Banking Application using Java RMI
 *
 * Student: Shane McInerney
 * ID: 13339141
 *
 * Student: Matthew Finn
 * ID: 13480362
 *
 */
package client;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import business.Transaction;
import interfaces.IBank;
import interfaces.IBank.InvalidLogin;
import interfaces.IBank.InvalidSession;
import server.Statement;

public class ATM
{
	private static String serverAddress;
	private static int serverPort;
	private static IBank bankInterface;
	private static long sessionID;
	private static Timer sessionTimer;
	private static boolean isRunning;
	public static void main (String args[]) throws Exception
	{

		/*
		 * Defines server address and port variables read in from command line
		 */
		serverAddress = args[0];
		serverPort = Integer.parseInt(args[1]);

		try
		{
			Registry registry = LocateRegistry.getRegistry(serverAddress, serverPort);
			bankInterface = (IBank) registry.lookup("Bank");
			isRunning=true;
			do{
				MenuSwitcher();
			}while(isRunning);

		}catch(Exception e)
		{
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}

	private static void MenuSwitcher() throws NumberFormatException, RemoteException, InvalidSession, ParseException, InvalidLogin
	{

		System.out.println("-------------------------------------------------");
		System.out.println("ATM Menu - Enter One Of The Following Choices: \n");
		System.out.println("Login");
		System.out.println("Inquiry");
		System.out.println("Deposit");
		System.out.println("Withdraw");
		System.out.println("Statement");
		System.out.println("Exit \n");

		Scanner scan = new Scanner(System.in);
		System.out.print("Choice: ");
		String choice = scan.nextLine().toLowerCase();
		System.out.println("\n-------------------------------------------------");

		switch (choice)
		{

		case "login":
			System.out.println("LOGIN");

			System.out.println("Enter the username:");
			String un = scan.nextLine();

			System.out.println("Enter the password:");
			String pw = scan.nextLine();

			sessionID=bankInterface.login(un, pw);

			if(sessionID != 0)
			{
				sessionTimer = new Timer();

				sessionTimer.schedule(new TimerTask()
				{
					@Override
					public void run()
					{
						sessionID =0;
					}
				}, 5*60*1000);
				System.out.println("Sucessfully Logged In!");
			}
			else
			{
				scan.close();
				throw new InvalidLogin();

			}
			break;

		case "inquiry":
			if(sessionID !=0)
			{
				System.out.println("INQUIRY");

				System.out.println("Enter Account Number:");
				int accnum = scan.nextInt();

				double bal = bankInterface.inquiry(accnum, sessionID);

				System.out.println("Balance is: "+bal);
				System.out.println("-------------------------------------------------\n");
			}
			else
			{
				scan.close();
				throw new InvalidSession();
			}
			break;

		case "deposit":
			if(sessionID !=0)
			{
				System.out.println("DEPOSIT");

				System.out.println("Enter Account Number: ");
				int accnum = scan.nextInt();

				System.out.println("Enter Amount To Deposit: ");
				int amt = scan.nextInt();

				double bal=bankInterface.deposit(accnum, amt,  sessionID);

				System.out.println("Successfully Deposited: "+amt +" to Account: "+accnum);
				System.out.println("Balance is: "+bal);
				System.out.println("-------------------------------------------------\n");

			}
			else
			{
				scan.close();
				throw new InvalidSession();
			}
			break;


		case "withdraw":
			if(sessionID !=0)
			{
				System.out.println("WITHDRAW");

				System.out.println("Enter Account Number:");
				int accnum = scan.nextInt();

				System.out.println("Enter Amount To Withdraw : €");
				int amt = scan.nextInt();

				double bal=bankInterface.withdraw(accnum, amt, sessionID);

				System.out.println("Successfully Withdrew: "+amt +" from Account: "+accnum);
				System.out.println("Balance is: "+bal);
				System.out.println("-------------------------------------------------\n");

			}
			else
			{
				scan.close();
				throw new InvalidSession();
			}
			break;

		case "statement":

			if(sessionID !=0)
			{
				System.out.println("STATEMENT");

				System.out.println("Enter Account Number:");
				int accnum = scan.nextInt();

				System.out.println("Enter Start Date (dd/mm/yyyy):");
				String f = scan.next();

				System.out.println("Enter End Date (dd/mm/yyyy):");
				String t = scan.next();

				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date from = dateFormat.parse(f);
				Date to = dateFormat.parse(t);

				Statement s = bankInterface.getStatement(accnum, from, to, sessionID);

				System.out.println("Statement for account number"+accnum+" from "+ from.toString()+" to "+ to.toString()+"\n");
				for(Transaction tr:s.getTransactionsForPeriod(from, to))
				{
					System.out.println(tr.toString());
				}
				System.out.println("Balance: €"+bankInterface.inquiry(accnum, sessionID));
				System.out.println("-------------------------------------------------\n");

			}
			else
			{
				scan.close();
				throw new InvalidSession();
			}
			break;

		case "exit":

			if(sessionID !=0)
			{
				sessionID =0;
				isRunning =false;
				scan.close();
			}
			else
			{
				scan.close();
				throw new InvalidSession();
			}
			break;
		}
	}
}

