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
//howya
package client;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import interfaces.IBank;
import interfaces.IBank.InvalidLogin;
import interfaces.IBank.InvalidSession;

public class ATM
{

	private static String serverAddress;
	private static int serverPort;
	private static IBank bankInterface;
	private static long sessionID;
	private static Timer sessionTimer;
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
			MenuSwitcher(args);
			//Naming.rebind("IBank", bankInterface);

		}catch(Exception e)
		{

			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}

		//		for (int i=0; i < args.length; i++){
		//			System.out.println(args[i].toString());
		//		}


	}


	private static void MenuSwitcher(String args[]) throws NumberFormatException, RemoteException, InvalidSession, ParseException, InvalidLogin
	{
		switch (args[2].toLowerCase())
		{

		case "login":
			sessionID=bankInterface.login(args[3], args[4]);
			
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
			}
			else
			{	
				throw new InvalidLogin();
			}
			

		case "inquiry":
			if(sessionID !=0)
			{
				bankInterface.inquiry(Integer.parseInt(args[3]), sessionID);
			}
			else
			{	
				throw new InvalidSession();
			}
			break;

		case "deposit":
			if(sessionID !=0)
			{
				bankInterface.deposit(Integer.parseInt(args[3]), Integer.parseInt(args[4]), sessionID);
			}
			else
			{	
				throw new InvalidSession();
			}
			break;

		case "withdraw":
			if(sessionID !=0)
			{
				bankInterface.withdraw(Integer.parseInt(args[3]), Integer.parseInt(args[4]), sessionID);
			}
			else
			{	
				throw new InvalidSession();
			}
			break;

		case "statement":

			if(sessionID !=0)
			{
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date from = dateFormat.parse(args[4]);
				Date to = dateFormat.parse(args[5]);

				bankInterface.getStatement(Integer.parseInt(args[3]), from, to, sessionID);	
			}
			else
			{	
				throw new InvalidSession();
			}
			
			break;

		}



	}
}
