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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import business.Account;
import business.Transaction;
import interfaces.IStatement;

public class Statement implements IStatement, Serializable {

	private Account account;
	private Date startDate;
	private Date endDate;


	public Statement(Account acc, Date from, Date to)
	{
		this.account = acc;
		this.startDate =from;
		this.endDate =to;
	}


	@Override
	public int getAccountnum() {
		// TODO Auto-generated method stub
		return account.getAccountNum();
	}

	@Override
	public Date getStartDate() {
		// TODO Auto-generated method stub
		return this.startDate;
	}

	@Override
	public Date getEndDate() {
		// TODO Auto-generated method stub

		return this.endDate;
	}

	@Override
	public String getAccoutName() {
		// TODO Auto-generated method stub
		return account.getAccountName();
	}

	@Override
	public List<Transaction> getTransactions() {
		// TODO Auto-generated method stub
		return account.getTransactions();
	}

	@Override
	public List<Transaction> getTransactionsForPeriod(Date from, Date to)
	{
		List<Transaction> transactionsForPeriod= new ArrayList<Transaction>();

		for (Transaction t: this.getTransactions())
		{

			if(t.getDate().after(from)&& t.getDate().before(to))
			{
				transactionsForPeriod.add(t);
			}
		}
		return transactionsForPeriod;
	}

}
