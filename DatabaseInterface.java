package ATMWithJava;

import java.util.List;


public interface DatabaseInterface {
	int authenticateUser(Account accountObject);
	int updateTransaction(Transaction transactionObject);
	int updateObject(Account accountObject);
	List<Transaction> getTransactionList(Transaction transactionObject);
}
