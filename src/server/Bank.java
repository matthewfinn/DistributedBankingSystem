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

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import business.Account;
import business.Transaction;
import interfaces.IBank;

public class Bank extends UnicastRemoteObject implements IBank {

	private List<Account> accounts; // users accounts
	private static int serverPort;

	public Bank() throws RemoteException
	{
		this.accounts = new ArrayList<Account>();
		/**
		 * Adding new user accounts to the bank upon construction and Bank (Server) obj.
		 */
		this.accounts.add(new Account(1, 100.00));
		this.accounts.add(new Account(2, 200.00));
		this.accounts.add(new Account(3, 300.00));
		this.accounts.add(new Account(4, 400.00));
		this.accounts.add(new Account(5, 500.00));

	}

	public static void main(String args[]) throws Exception
	{
		//Reads in port number parameter from cmd
		serverPort = Integer.parseInt(args[0]);
		try
		{
			Bank bank = new Bank();// initialise Bank server
			IBank bankIF = (IBank) UnicastRemoteObject.exportObject(bank, 0);
			Registry registry = LocateRegistry.getRegistry(serverPort);
			registry.bind("IBank", bankIF);
			System.setSecurityManager(new RMISecurityManager());
			System.out.println("Server ready");
		}
		catch(Exception e )
		{
			System.out.println("Error Initialising Bank Server");
		}
	}



	@Override
	public long login(String username, String password) throws RemoteException, InvalidLogin {
		// TODO Auto-generated method stub
		return 0;
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

				Transaction dep = new Transaction("deposit", amount, todayDate);
				acc.setBalance(acc.getBalance()+amount);
				acc.addTransaction(dep);
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


				Transaction wit = new Transaction("withdrawal", amount, todayDate);
				acc.setBalance(acc.getBalance()-amount);
				acc.addTransaction(wit);
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
				System.out.println("Balance for account number"+acc.getAccountNum()+" is €"+acc.getBalance());
				break;
			}
		}

		return 0;
	}
	@Override
	public Statement getStatement(int accnum, Date from, Date to, long sessionID) throws RemoteException, InvalidSession {
		Account acc;
		List<Transaction> trs;

		for(Account a : accounts){

			if(a.getAccountNum() == accnum){

				acc = a;
				trs = acc.getTransactions();

				for (Transaction t: trs){

					if(t.getDate().after(from)&& t.getDate().before(to)){
						t.toString();
					}
				}
				break;
			}

		}
		return null;
	}
}