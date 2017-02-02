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
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

import business.Account;
import interfaces.IBank;

public class Bank extends UnicastRemoteObject implements IBank {

	private List<Account> accounts; // users accounts

	public Bank() throws RemoteException
	{

	}
	public void deposit(int account, int amount) throws RemoteException/*,  InvalidSession*/ {

		// implementation code

	}

	public void withdraw(int account, int amount) throws RemoteException/*, InvalidSession*/ {

		// implementation code

	}

	public int inquiry(int account) throws RemoteException/*, InvalidSession*/ {
		return account;

		// implementation code

	}

	public Statement getStatement(Date from, Date to) throws RemoteException/*, InvalidSession*/ {
		return null;

		// implementation code

	}

	public static void main(String args[]) throws Exception {

		// initialise Bank server - see sample code in the notes for details

	}
	@Override
	public long login(String username, String password) throws RemoteException, InvalidLogin {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void deposit(int accountnum, int amount, long sessionID) throws RemoteException, InvalidSession {
		// TODO Auto-generated method stub

	}
	@Override
	public void withdraw(int accountnum, int amount, long sessionID) throws RemoteException, InvalidSession {
		// TODO Auto-generated method stub

	}
	@Override
	public int inquiry(int accountnum, long sessionID) throws RemoteException, InvalidSession {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Statement getStatement(Date from, Date to, long sessionID) throws RemoteException, InvalidSession {
		// TODO Auto-generated method stub
		return null;
	}


}