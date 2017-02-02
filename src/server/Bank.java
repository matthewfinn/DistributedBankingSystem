package server;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

import interfaces.IBank;
import server.Statement;
import business.Account;

public class Bank extends UnicastRemoteObject implements IBank {

private List<Account> accounts; // users accounts

public Bank() throws RemoteException
{

}
public void deposit(int account, int amount) throws RemoteException, InvalidSession {

// implementation code

}

public void withdraw(int account, int amount) throws RemoteException, InvalidSession {

// implementation code

}

public int inquiry(int account) throws RemoteException, InvalidSession {

// implementation code

}

public Statement getStatement(Date from, Date to) throws RemoteException, InvalidSession {

// implementation code

}

public static void main(String args[]) throws Exception {

// initialise Bank server - see sample code in the notes for details

}

}