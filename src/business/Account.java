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

package business;

import java.util.ArrayList;
import java.util.List;

public class Account
{

	private int accountNum;
	private double balance;
	private List<Transaction> transactions;


	public Account(int acm, double bal){

		this.accountNum = acm;
		this.balance = bal;
		this.transactions = new ArrayList<Transaction>();



	}


	public int getAccountNum() {
		return accountNum;
	}


	public double getBalance() {
		return balance;
	}


	public List<Transaction> getTransactions() {
		return transactions;
	}


	public void addTransaction (Transaction tr){

		/**
		 * CREATE NEW TRANSACTION HERE... MODIFY METHOD PARAMS TO SUIT...
		 */
		transactions.add(tr);
	}

	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}

}
