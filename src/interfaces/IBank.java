package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

import interfaces.IStatement;
import server.Statement;

public interface IBank extends Remote {

public long login(String username, String password) throws RemoteException, InvalidLogin;

public void deposit(int accountnum, int amount, long sessionID) throws RemoteException, InvalidSession;

public void withdraw(int accountnum, int amount, long sessionID) throws RemoteException, InvalidSession;

public int inquiry(int accountnum, long sessionID) throws RemoteException, InvalidSession;

public Statement getStatement(Date from, Date to, long sessionID) throws RemoteException, InvalidSession;

}
