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

package server;

import java.net.URL;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import business.Account;
import business.Transaction;
import business.Transaction.TransactionType;
import interfaces.IBank;


public class Bank extends UnicastRemoteObject implements IBank {

	private List<Account> accounts; // users accounts
	private Map<String,String> userDetails;
	private static int serverPort;
	

	public Bank() throws RemoteException
	{
		userDetails= new HashMap<String, String>();
		this.accounts = new ArrayList<Account>();
		/**
		 * Adding new user accounts to the bank upon construction and Bank (Server) obj.
		 */
		this.accounts.add(new Account(1, 100.00,"Matthew"));
		this.accounts.add(new Account(2, 200.00,"Shane"));
		this.accounts.add(new Account(3, 300.00,"Mark"));
		this.accounts.add(new Account(4, 400.00,"Luke"));
		this.accounts.add(new Account(5, 500.00,"Joe"));
		userDetails.put("username", "password");

	}

	public static void main(String args[]) throws Exception
	{
		//Reads in port number parameter from cmd
		serverPort = Integer.parseInt(args[0]);
		try
		{
			Bank bank = new Bank();// initialise Bank server
			Registry registry = LocateRegistry.getRegistry(serverPort);
			registry.bind("Bank", bank);		
			System.out.println("Server ready");
		}
		catch(Exception e )
		{
			System.out.println("Error Initialising Bank Server:" +e.getMessage());
			e.printStackTrace();
		}
	}



	@Override
	public long login(String username, String password) throws RemoteException, InvalidLogin {

		long sesID=0;

		if(userDetails.containsKey(username))
		{
			if(userDetails.containsValue(password))
			{
				long min = 1; //assign lower range value
				long max = 1000000; //assign upper range value
				Random random = new Random();


				sesID = min + (long)(random.nextDouble()*(max - min));
				
				
				System.out.println("Logged In Successfully!");
			}
			else
			{
				System.out.println("Invalid Username or Password!");
			}
		}
		else
		{
			System.out.println("Invalid Username or Password!");
		}
		return sesID;
	}

	@Override
	public void deposit(int accnum, int amount, long sessionID) throws RemoteException, InvalidSession {

		Account acc ;

		for(Account a : accounts){

			if(a.getAccountNum() == accnum){

				acc = a;

				Calendar today = Calendar.getInstance();
				today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
				Date todayDate = today.getTime();

				Transaction dep = new Transaction(TransactionType.Deposit, amount, todayDate);
				acc.setBalance(acc.getBalance()+amount);
				acc.addTransaction(dep);
				System.out.println("Sucessfully Deposited: "+dep.toString());
				System.out.println("Balance: "+acc.getBalance());
				break;
			}

		}

	}
	@Override
	public void withdraw(int accnum, int amount, long sessionID) throws RemoteException, InvalidSession {
		Account acc;

		for(Account a : accounts){

			if(a.getAccountNum() == accnum){

				acc = a;

				//Gets todays date
				Calendar today = Calendar.getInstance();
				today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
				Date todayDate = today.getTime();


				Transaction wit = new Transaction(TransactionType.Withdrawal, amount, todayDate);
				acc.setBalance(acc.getBalance()-amount);
				acc.addTransaction(wit);
				System.out.println("Sucessfully Withdrew: "+amount);
				System.out.println("Balance: "+acc.getBalance());
				break;
			}
		}

	}
	@Override
	public int inquiry(int accnum, long sessionID) throws RemoteException, InvalidSession {
		Account acc;

		for(Account a : accounts){

			if(a.getAccountNum() == accnum){

				acc = a;
				System.out.println("Balance for account number" + acc.getAccountNum()+" is €"+acc.getBalance());
				break;
			}
		}

		return 0;
	}
	@Override
	public Statement getStatement(int accnum, Date from, Date to, long sessionID) throws RemoteException, InvalidSession {

		Statement s=null;

		for(Account a : accounts)
		{
			if(a.getAccountNum() == accnum)
			{
				s = new Statement(a,from,to);
				System.out.println("Bank Statement For Account: "+a.getAccountNum() +" From: "+from.toString() +" To: "+ to.toString());
				for(Transaction t :s.getTransactionsForPeriod(from, to))
				{
					System.out.println(t.toString());
				}
			}
		}
		return s;
	}
}