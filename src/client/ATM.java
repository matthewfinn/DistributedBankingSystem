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

import interfaces.IBank;
import interfaces.IBank.InvalidSession;

public class ATM
{

	private static String serverAddress;
	private static int serverPort;
	private static IBank bankInterface;
	private static long sessionID;
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
			IBank bankInterface = (IBank) registry.lookup("IBank");

		}catch(Exception e)
		{

			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();

		}

		MenuSwitcher(args);
	}


	private static void MenuSwitcher(String args[]) throws NumberFormatException, RemoteException, InvalidSession
	{
		switch (args[2])
		{

		case "login":
			//sessionID=bankInterface.login(args[4], args[5])

			break;

		case "inquiry":
			bankInterface.inquiry(Integer.parseInt(args[3]), sessionID);
			break;

		case "deposit":
			bankInterface.deposit(Integer.parseInt(args[3]), Integer.parseInt(args[4]), sessionID);
			break;

		case "withdraw":
			bankInterface.withdraw(Integer.parseInt(args[3]), Integer.parseInt(args[4]), sessionID);
			break;

		case "statement":
			//bankInterface.getStatement(from, to, sessionID)
			break;

		}
	}
}
