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
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ATM
{

	private static String serverAddress;
	private static int serverPort;


	public static void main (String args[]) throws Exception
	{

		/*
		 * Defines server address and port variables read in from command line
		 */
		serverAddress = args[0];
		serverPort = Integer.parseInt(args[1]);


		try{

			Registry registry = LocateRegistry.getRegistry(serverAddress, serverPort);


		}catch(Exception e){

			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();

		}

		switch (args[2]){

		case "login":
			break;

		case "inquiry":
			break;

		case "deposit":
			break;

		case "withdraw":
			break;

		case "statement":
			break;

		}

		// get user�s input, and perform the operations

	}

}
