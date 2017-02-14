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

	private static final long serialVersionUID = 1L;
	private List<Account> accounts; /* List to store user account objects */
	private Map<String,String> userDetails; /* Map to store user login details*/
	private static int serverPort; /* Integer value to store port number on which to run ther server */


	public Bank() throws RemoteException
	{
		userDetails= new HashMap<String, String>();
		this.accounts = new ArrayList<Account>();

		/*
		 * Adding new user accounts to the bank upon construction and Bank (Server) obj.
		 */
		Account a1 = new Account(1, 100.00,"Matthew");
		Account a2 = new Account(2, 200.00,"Shane");
		Account a3 = new Account(3, 300.00,"Mark");
		Account a4 = new Account(4, 400.00,"Luke");
		Account a5 =new Account(5, 500.00,"Joe");

		/*
		 * Adding transctions to account 1 for testing purposes
		 */
		a1.addTransaction(new Transaction(TransactionType.Deposit,200.0,new Date()));
		a1.setBalance(a1.getBalance()+200.0);
		a1.addTransaction(new Transaction(TransactionType.Withdrawal,10.0,new Date()));
		a1.setBalance(a1.getBalance()-10.0);

		/*
		 * Adding accounts to list of accounts
		 */
		this.accounts.add(a1);
		this.accounts.add(a2);
		this.accounts.add(a3);
		this.accounts.add(a4);
		this.accounts.add(a5);

		/*
		 * Setting user login details
		 */
		userDetails.put("username", "password");

	}

	public static void main(String args[]) throws Exception
	{
		/*
		 * Reads in port number parameter from cmd
		 */
		serverPort = Integer.parseInt(args[0]);
		try
		{
			Bank bank = new Bank(); /* initialise Bank server */
			Registry registry = LocateRegistry.getRegistry(serverPort); /* Initialises registry */
			registry.bind("Bank", bank); /* Binds bank to registry */
			System.out.println("Server ready");
		}
		catch(Exception e )
		{
			/* Exception thrown if bank server cannot be started */
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


				/* Initialises Session ID if user enters correct login details */
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
	public double deposit(int accnum, int amount, long sessionID) throws RemoteException, InvalidSession {

		Account acc =null;

		for(Account a : accounts){

			if(a.getAccountNum() == accnum){

				acc = a;

				Calendar today = Calendar.getInstance();
				Date todayDate = today.getTime(); /* Gets todays date at current time */

				/* Creates transaction */
				Transaction dep = new Transaction(TransactionType.Deposit, amount, todayDate);

				/* Adjusts account balance */
				acc.setBalance( acc.getBalance() + amount);

				/* Adds new transaction to account */
				acc.addTransaction(dep);

				System.out.println("Sucessfully Deposited: "+dep.toString());
				System.out.println("Balance: "+acc.getBalance());
			}
		}
		return acc.getBalance();

	}
	@Override
	public double withdraw(int accnum, int amount, long sessionID) throws RemoteException, InvalidSession {
		Account acc=null;

		for(Account a : accounts){

			if(a.getAccountNum() == accnum){

				acc = a;

				//Gets todays date
				Calendar today = Calendar.getInstance();
				Date todayDate = today.getTime();

				// creates new transaction
				Transaction wit = new Transaction(TransactionType.Withdrawal, amount, todayDate);

				// adjusts account balance & adds transaction to account
				acc.setBalance(acc.getBalance()-amount);
				acc.addTransaction(wit);

				System.out.println("Sucessfully Withdrew: "+amount);
				System.out.println("Balance: "+acc.getBalance());
			}
		}
		return acc.getBalance();
	}
	@Override
	public double inquiry(int accnum, long sessionID) throws RemoteException, InvalidSession {

		Account acc = null;

		for(Account a : accounts){ //Search accounts to fine one that matches number

			if(a.getAccountNum() == accnum){

				acc = a;
				System.out.println("Balance for account number" + acc.getAccountNum()+" is: "+acc.getBalance());
			}
		}
		return acc.getBalance();
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

				//prints each transaction in new statement
				for(Transaction t :s.getTransactionsForPeriod(from, to))
				{
					System.out.println(t.toString());
				}
			}
		}
		return s;
	}
}