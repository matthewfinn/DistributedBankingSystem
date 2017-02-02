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

import java.awt.List;
import java.io.Serializable;
import java.util.Date;

public interface IStatement  extends Serializable {

	public int getAccountnum();  // returns account number associated with this statement

	public Date getStartDate(); // returns start Date of Statement

	public Date getEndDate(); // returns end Date of Statement

	public String getAccoutName(); // returns name of account holder

	public List  getTransations(); // returns list of Transaction objects that encapsulate details about each transaction

}
