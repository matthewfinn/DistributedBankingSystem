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

package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

import server.Statement;

public interface IBank extends Remote {

	public long login(String username, String password) throws RemoteException, InvalidLogin;

	public double deposit(int accountnum, int amount, long sessionID) throws RemoteException, InvalidSession;

	public double withdraw(int accountnum, int amount, long sessionID) throws RemoteException, InvalidSession;

	public double inquiry(int accountnum, long sessionID) throws RemoteException, InvalidSession;

	public Statement getStatement(int accnum, Date from, Date to, long sessionID) throws RemoteException, InvalidSession;

	class InvalidLogin extends Exception{

		private static final long serialVersionUID = 1L;
		private String message ="Error, Invalid Login Details Provided";
		public InvalidLogin(){

			System.out.println(this.message);
		};
		@Override
		public String getMessage()
		{
			return this.message;
		}
	};

	class InvalidSession extends Exception{

		private static final long serialVersionUID = 1L;
		private String message ="Error, Session Is Invalid Or Has Expired";
		public InvalidSession(){
			System.out.println(this.message);
		};
		@Override
		public String getMessage()
		{
			return this.message;
		}

	};

}
