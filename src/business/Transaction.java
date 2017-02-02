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
	private String type;
	private double amount;
	private Date date;


	public Transaction (String t, double amt, Date dt){


	}

	@Override
	public String toString(){
		return this.type + this.amount + this.date;

	}

	public String getType() {
		return type;
	}

	public double getAmount() {
		return amount;
	}

	public Date getDate() {
		return date;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
