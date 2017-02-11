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

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable{

	private static final long serialVersionUID = 223815498914869520L;
	private TransactionType type;
	private double amount;
	private Date date;

	public enum TransactionType
	{
		Deposit,
		Withdrawal
	}
	
	public Transaction (TransactionType t, double amt, Date dt)
	{
		this.type = t;
		this.amount = amt;
		this.date =dt;
	}

	@Override
	public String toString(){
		return "Transaction: "+ this.type + this.amount + this.date;

	}

	public TransactionType getType() {
		return type;
	}

	public double getAmount() {
		return amount;
	}

	public Date getDate() {
		return date;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
